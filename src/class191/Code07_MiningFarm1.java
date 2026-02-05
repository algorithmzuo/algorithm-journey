package class191;

// 矿场搭建，java版
// 测试链接 : https://www.luogu.com.cn/problem/P3225
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Code07_MiningFarm1 {

	public static int MAXN = 1001;
	public static int MAXM = 1001;
	public static int t, n, m;

	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXM << 1];
	public static int[] to = new int[MAXM << 1];
	public static int cntg;

	public static int[] dfn = new int[MAXN];
	public static int[] low = new int[MAXN];
	public static int cntd;

	public static int[] sta = new int[MAXN];
	public static int top;

	public static boolean[] cutVertex = new boolean[MAXN];
	public static List<List<Integer>> vbccArr = new ArrayList<>();

	public static long ans1, ans2;

	public static void prepare() {
		cntg = cntd = top = 0;
		for (int i = 1; i <= n; i++) {
			head[i] = dfn[i] = low[i] = 0;
			cutVertex[i] = false;
		}
		vbccArr.clear();
		n = 0;
		ans1 = 0;
		ans2 = 1;
	}

	public static void addEdge(int u, int v) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		head[u] = cntg;
	}

	public static void tarjan(int u, boolean root) {
		dfn[u] = low[u] = ++cntd;
		sta[++top] = u;
		if (root && head[u] == 0) {
			ArrayList<Integer> list = new ArrayList<>();
			list.add(u);
			vbccArr.add(list);
			return;
		}
		int son = 0;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (dfn[v] == 0) {
				son++;
				tarjan(v, false);
				low[u] = Math.min(low[u], low[v]);
				if (low[v] >= dfn[u]) {
					if (!root || son >= 2) {
						cutVertex[u] = true;
					}
					ArrayList<Integer> list = new ArrayList<>();
					int pop;
					do {
						pop = sta[top--];
						list.add(pop);
					} while (pop != v);
					list.add(u);
					vbccArr.add(list);
				}
			} else {
				low[u] = Math.min(low[u], dfn[v]);
			}
		}
	}

	public static void compute() {
		for (int i = 0; i < vbccArr.size(); i++) {
			int siz = vbccArr.get(i).size(), cut = 0;
			for (int node : vbccArr.get(i)) {
				if (cutVertex[node]) {
					cut++;
				}
			}
			if (cut == 0) {
				ans1 += 2;
				ans2 = ans2 * siz * (siz - 1) / 2;
			} else if (cut == 1) {
				ans1 += 1;
				ans2 = ans2 * (siz - 1);
			}
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		t = 0;
		m = in.nextInt();
		while (m != 0) {
			prepare();
			for (int i = 1, u, v; i <= m; i++) {
				u = in.nextInt();
				v = in.nextInt();
				addEdge(u, v);
				addEdge(v, u);
				n = Math.max(n, u);
				n = Math.max(n, v);
			}
			tarjan(1, true);
			compute();
			out.println("Case " + (++t) + ": " + ans1 + " " + ans2);
			m = in.nextInt();
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
