package class177;

// 糖果公园，java版
// 测试链接 : https://www.luogu.com.cn/problem/P4074
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;

public class Code08_CandyPark1 {

	public static int MAXN = 100001;
	public static int MAXP = 20;
	public static int n, m, q;
	public static int[] v = new int[MAXN];
	public static int[] w = new int[MAXN];
	public static int[] c = new int[MAXN];

	public static int[] head = new int[MAXN];
	public static int[] to = new int[MAXN << 1];
	public static int[] next = new int[MAXN << 1];
	public static int cntg;

	// 每条查询 : l、r、t、lca、id
	public static int[][] query = new int[MAXN][5];
	// 每条修改 : pos、val
	public static int[][] update = new int[MAXN][2];
	public static int cntq, cntu;

	public static int[] dep = new int[MAXN];
	public static int[] seg = new int[MAXN << 1];
	public static int[] st = new int[MAXN];
	public static int[] ed = new int[MAXN];
	public static int[][] stjump = new int[MAXN][MAXP];
	public static int cntd;

	public static int[] bi = new int[MAXN << 1];
	public static boolean[] vis = new boolean[MAXN];
	public static int[] cnt = new int[MAXN];
	public static long curAns;
	public static long[] ans = new long[MAXN];

	public static void addEdge(int u, int v) {
		next[++cntg] = head[u];
		to[cntg] = v;
		head[u] = cntg;
	}

	// 递归版，C++可以通过，java会爆栈，需要改迭代
	public static void dfs1(int u, int fa) {
		dep[u] = dep[fa] + 1;
		seg[++cntd] = u;
		st[u] = cntd;
		stjump[u][0] = fa;
		for (int p = 1; p < MAXP; p++) {
			stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
		}
		for (int e = head[u], v; e > 0; e = next[e]) {
			v = to[e];
			if (v != fa) {
				dfs1(v, u);
			}
		}
		seg[++cntd] = u;
		ed[u] = cntd;
	}

	// 不会改迭代版，去看讲解118，详解了从递归版改迭代版
	public static int[][] ufe = new int[MAXN][3];

	public static int stacksize, u, f, e;

	public static void push(int u, int f, int e) {
		ufe[stacksize][0] = u;
		ufe[stacksize][1] = f;
		ufe[stacksize][2] = e;
		stacksize++;
	}

	public static void pop() {
		--stacksize;
		u = ufe[stacksize][0];
		f = ufe[stacksize][1];
		e = ufe[stacksize][2];
	}

	// dfs1的迭代版
	public static void dfs2() {
		stacksize = 0;
		push(1, 0, -1);
		while (stacksize > 0) {
			pop();
			if (e == -1) {
				dep[u] = dep[f] + 1;
				seg[++cntd] = u;
				st[u] = cntd;
				stjump[u][0] = f;
				for (int p = 1; p < MAXP; p++) {
					stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
				}
				e = head[u];
			} else {
				e = next[e];
			}
			if (e != 0) {
				push(u, f, e);
				if (to[e] != f) {
					push(to[e], u, -1);
				}
			} else {
				seg[++cntd] = u;
				ed[u] = cntd;
			}
		}
	}

	public static int lca(int a, int b) {
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

	// 带修莫队经典排序
	public static class QueryCmp implements Comparator<int[]> {

		@Override
		public int compare(int[] a, int[] b) {
			if (bi[a[0]] != bi[b[0]]) {
				return bi[a[0]] - bi[b[0]];
			}
			if (bi[a[1]] != bi[b[1]]) {
				return bi[a[1]] - bi[b[1]];
			}
			return a[2] - b[2];
		}

	}

	public static void modify(int node) {
		int candy = c[node];
		if (vis[node]) {
			curAns -= (long) v[candy] * w[cnt[candy]--];
		} else {
			curAns += (long) v[candy] * w[++cnt[candy]];
		}
		vis[node] = !vis[node];
	}

	public static void moveTime(int tim) {
		int pos = update[tim][0];
		int oldVal = c[pos];
		int newVal = update[tim][1];
		if (vis[pos]) {
			modify(pos);
			c[pos] = newVal;
			update[tim][1] = oldVal;
			modify(pos);
		} else {
			c[pos] = newVal;
			update[tim][1] = oldVal;
		}
	}

	public static void compute() {
		int winl = 1, winr = 0, wint = 0;
		for (int i = 1; i <= cntq; i++) {
			int jobl = query[i][0];
			int jobr = query[i][1];
			int jobt = query[i][2];
			int lca = query[i][3];
			int id = query[i][4];
			while (winl > jobl) {
				modify(seg[--winl]);
			}
			while (winr < jobr) {
				modify(seg[++winr]);
			}
			while (winl < jobl) {
				modify(seg[winl++]);
			}
			while (winr > jobr) {
				modify(seg[winr--]);
			}
			while (wint < jobt) {
				moveTime(++wint);
			}
			while (wint > jobt) {
				moveTime(wint--);
			}
			if (lca > 0) {
				modify(lca);
			}
			ans[id] = curAns;
			if (lca > 0) {
				modify(lca);
			}
		}
	}

	public static void prapare() {
		int blen = Math.max(1, (int) Math.pow(cntd, 2.0 / 3));
		for (int i = 1; i <= cntd; i++) {
			bi[i] = (i - 1) / blen + 1;
		}
		Arrays.sort(query, 1, cntq + 1, new QueryCmp());
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		q = in.nextInt();
		for (int i = 1; i <= m; i++) {
			v[i] = in.nextInt();
		}
		for (int i = 1; i <= n; i++) {
			w[i] = in.nextInt();
		}
		for (int i = 1, u, v; i < n; i++) {
			u = in.nextInt();
			v = in.nextInt();
			addEdge(u, v);
			addEdge(v, u);
		}
		for (int i = 1; i <= n; i++) {
			c[i] = in.nextInt();
		}
		dfs2();
		for (int i = 1, op, x, y; i <= q; i++) {
			op = in.nextInt();
			x = in.nextInt();
			y = in.nextInt();
			if (op == 0) {
				cntu++;
				update[cntu][0] = x;
				update[cntu][1] = y;
			} else {
				if (st[x] > st[y]) {
					int tmp = x;
					x = y;
					y = tmp;
				}
				int xylca = lca(x, y);
				if (x == xylca) {
					query[++cntq][0] = st[x];
					query[cntq][1] = st[y];
					query[cntq][2] = cntu;
					query[cntq][3] = 0;
					query[cntq][4] = cntq;
				} else {
					query[++cntq][0] = ed[x];
					query[cntq][1] = st[y];
					query[cntq][2] = cntu;
					query[cntq][3] = xylca;
					query[cntq][4] = cntq;
				}
			}
		}
		prapare();
		compute();
		for (int i = 1; i <= cntq; i++) {
			out.println(ans[i]);
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
