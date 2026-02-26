package class194;

// 警察，java版
// 测试链接 : https://www.luogu.com.cn/problem/P4334
// 提交以下的code，提交时请把类名改成"Main"
// 本题卡常导致java的实现无法通过，索性很多递归函数也不改迭代了
// 想通过用C++实现，本节课Code04_Policija2文件就是C++的实现
// 两个版本的逻辑完全一样，C++版本可以通过所有测试

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;

public class Code04_Policija1 {

	public static int MAXN = 100001;
	public static int MAXM = 500001;
	public static int n, m, q, cntn;

	public static int[] head1 = new int[MAXN];
	public static int[] next1 = new int[MAXM << 1];
	public static int[] to1 = new int[MAXM << 1];
	public static int cnt1;

	public static int[] head2 = new int[MAXN << 1];
	public static int[] next2 = new int[MAXM << 2];
	public static int[] to2 = new int[MAXM << 2];
	public static int cnt2;

	public static int[] dfn = new int[MAXN];
	public static int[] low = new int[MAXN];
	public static int cntd;

	public static int[] sta = new int[MAXN];
	public static int cnts;

	public static HashMap<Long, Integer> cutMap = new HashMap<>();

	public static int[] fa = new int[MAXN << 1];
	public static int[] dep = new int[MAXN << 1];
	public static int[] siz = new int[MAXN << 1];
	public static int[] son = new int[MAXN << 1];
	public static int[] top = new int[MAXN << 1];

	public static void addEdge1(int u, int v) {
		next1[++cnt1] = head1[u];
		to1[cnt1] = v;
		head1[u] = cnt1;
	}

	public static void addEdge2(int u, int v) {
		next2[++cnt2] = head2[u];
		to2[cnt2] = v;
		head2[u] = cnt2;
	}

	public static void addCut(int x, int y, int cut) {
		int a = Math.min(x, y);
		int b = Math.max(x, y);
		cutMap.put(((long) a << 32) | b, cut);
	}

	public static int getCut(int x, int y) {
		int a = Math.min(x, y);
		int b = Math.max(x, y);
		Integer ans = cutMap.get(((long) a << 32) | b);
		return ans == null ? 0 : ans;
	}

	public static void tarjan(int u, int preEdge) {
		dfn[u] = low[u] = ++cntd;
		sta[++cnts] = u;
		for (int e = head1[u]; e > 0; e = next1[e]) {
			if ((e ^ 1) == preEdge) {
				continue;
			}
			int v = to1[e];
			if (dfn[v] == 0) {
				tarjan(v, e);
				low[u] = Math.min(low[u], low[v]);
				if (low[v] >= dfn[u]) {
					cntn++;
					if (low[v] > dfn[u]) {
						addCut(u, v, cntn);
					}
					addEdge2(cntn, u);
					addEdge2(u, cntn);
					int pop;
					do {
						pop = sta[cnts--];
						addEdge2(cntn, pop);
						addEdge2(pop, cntn);
					} while (pop != v);
				}
			} else {
				low[u] = Math.min(low[u], dfn[v]);
			}
		}
	}

	public static void dfs1(int u, int f) {
		fa[u] = f;
		dep[u] = dep[f] + 1;
		siz[u] = 1;
		for (int e = head2[u], v; e > 0; e = next2[e]) {
			v = to2[e];
			if (v != f) {
				dfs1(v, u);
				siz[u] += siz[v];
				if (son[u] == 0 || siz[son[u]] < siz[v]) {
					son[u] = v;
				}
			}
		}
	}

	public static void dfs2(int u, int t) {
		top[u] = t;
		if (son[u] == 0) {
			return;
		}
		dfs2(son[u], t);
		for (int e = head2[u], v; e > 0; e = next2[e]) {
			v = to2[e];
			if (v != fa[u] && v != son[u]) {
				dfs2(v, v);
			}
		}
	}

	public static boolean mustPass(int a, int b, int c) {
		while (top[a] != top[b]) {
			if (dep[top[a]] < dep[top[b]]) {
				int tmp = a;
				a = b;
				b = tmp;
			}
			if (top[c] == top[a] && dep[c] <= dep[a]) {
				return true;
			}
			a = fa[top[a]];
		}
		if (dep[a] < dep[b]) {
			int tmp = a;
			a = b;
			b = tmp;
		}
		return top[a] == top[c] && dep[b] <= dep[c] && dep[c] <= dep[a];
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		cntn = n;
		cnt1 = 1;
		for (int i = 1, u, v; i <= m; i++) {
			u = in.nextInt();
			v = in.nextInt();
			addEdge1(u, v);
			addEdge1(v, u);
		}
		tarjan(1, 0);
		dfs1(1, 0);
		dfs2(1, 1);
		q = in.nextInt();
		for (int i = 1, op, a, b, c, d; i <= q; i++) {
			op = in.nextInt();
			if (op == 1) {
				a = in.nextInt();
				b = in.nextInt();
				c = in.nextInt();
				d = in.nextInt();
				int cut = getCut(c, d);
				if (cut == 0) {
					out.println("yes");
				} else {
					out.println(mustPass(a, b, cut) ? "no" : "yes");
				}
			} else {
				a = in.nextInt();
				b = in.nextInt();
				c = in.nextInt();
				out.println(mustPass(a, b, c) ? "no" : "yes");
			}
		}
		out.flush();
		out.close();
	}

	// 读写工具类
	static class FastReader {
		private final byte[] buffer = new byte[1 << 16];
		private int ptr = 0, len = 0;
		private final InputStream in;

		FastReader(InputStream in) {
			this.in = in;
		}

		private int readByte() throws IOException {
			if (ptr >= len) {
				len = in.read(buffer);
				ptr = 0;
				if (len <= 0)
					return -1;
			}
			return buffer[ptr++];
		}

		int nextInt() throws IOException {
			int c;
			do {
				c = readByte();
			} while (c <= ' ' && c != -1);
			boolean neg = false;
			if (c == '-') {
				neg = true;
				c = readByte();
			}
			int val = 0;
			while (c > ' ' && c != -1) {
				val = val * 10 + (c - '0');
				c = readByte();
			}
			return neg ? -val : val;
		}
	}

}
