package class194;

// 边的查询，java版
// 测试链接 : https://www.luogu.com.cn/problem/CF1763F
// 测试链接 : https://codeforces.com/problemset/problem/1763/F
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code03_EdgeQueries1 {

	public static int MAXN = 200001;
	public static int MAXM = 200001;
	public static int MAXP = 20;
	public static int n, m, q, cntn;
	public static int[] a = new int[MAXM];
	public static int[] b = new int[MAXM];

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
	public static int top;

	public static int[] dep = new int[MAXN << 1];
	public static int[][] stjump = new int[MAXN << 1][MAXP];

	public static int[] vbccSiz = new int[MAXN << 1];
	public static int[] edgeCnt = new int[MAXN << 1];

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

	public static void tarjan(int u) {
		dfn[u] = low[u] = ++cntd;
		sta[++top] = u;
		for (int e = head1[u]; e > 0; e = next1[e]) {
			int v = to1[e];
			if (dfn[v] == 0) {
				tarjan(v);
				low[u] = Math.min(low[u], low[v]);
				if (low[v] >= dfn[u]) {
					cntn++;
					vbccSiz[cntn] = 1;
					addEdge2(cntn, u);
					addEdge2(u, cntn);
					int pop;
					do {
						pop = sta[top--];
						vbccSiz[cntn]++;
						addEdge2(cntn, pop);
						addEdge2(pop, cntn);
					} while (pop != v);
				}
			} else {
				low[u] = Math.min(low[u], dfn[v]);
			}
		}
	}

	public static void dfs(int u, int fa) {
		dep[u] = dep[fa] + 1;
		stjump[u][0] = fa;
		for (int p = 1; p < MAXP; p++) {
			stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
		}
		for (int e = head2[u]; e > 0; e = next2[e]) {
			int v = to2[e];
			if (v != fa) {
				dfs(v, u);
			}
		}
	}

	public static int getLca(int a, int b) {
		if (dep[a] < dep[b]) {
			int tmp = a;
			a = b;
			b = tmp;
		}
		for (int p = MAXP - 1; p >= 0; p--) {
			if (dep[stjump[a][p]] >= dep[b]) {
				a = stjump[a][p];
			}
		}
		if (a == b) {
			return a;
		}
		for (int p = MAXP - 1; p >= 0; p--) {
			if (stjump[a][p] != stjump[b][p]) {
				a = stjump[a][p];
				b = stjump[b][p];
			}
		}
		return stjump[a][0];
	}

	public static void dfsEdgeCnt(int u, int fa) {
		edgeCnt[u] += edgeCnt[fa];
		for (int e = head2[u]; e > 0; e = next2[e]) {
			int v = to2[e];
			if (v != fa) {
				dfsEdgeCnt(v, u);
			}
		}
	}

	public static void buildEdgeCnt() {
		for (int i = 1; i <= m; i++) {
			int fa = stjump[a[i]][0];
			int fb = stjump[b[i]][0];
			if (fa == fb || stjump[fa][0] == b[i]) {
				if (vbccSiz[fa] > 2) {
					edgeCnt[fa]++;
				}
			} else {
				if (vbccSiz[fb] > 2) {
					edgeCnt[fb]++;
				}
			}
		}
		dfsEdgeCnt(1, 0);
	}

	public static int query(int x, int y) {
		int xylca = getLca(x, y);
		int lcafa = stjump[xylca][0];
		return edgeCnt[x] + edgeCnt[y] - edgeCnt[xylca] - edgeCnt[lcafa];
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		cntn = n;
		for (int i = 1; i <= m; i++) {
			a[i] = in.nextInt();
			b[i] = in.nextInt();
			addEdge1(a[i], b[i]);
			addEdge1(b[i], a[i]);
		}
		tarjan(1);
		dfs(1, 0);
		buildEdgeCnt();
		q = in.nextInt();
		for (int i = 1, x, y; i <= q; i++) {
			x = in.nextInt();
			y = in.nextInt();
			out.println(query(x, y));
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
