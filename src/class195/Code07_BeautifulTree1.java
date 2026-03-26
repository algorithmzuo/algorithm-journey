package class195;

// 美丽的树，java版
// 测试链接 : https://www.luogu.com.cn/problem/CF1904F
// 测试链接 : https://codeforces.com/problemset/problem/1904/F
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code07_BeautifulTree1 {

	public static int MAXN = 200001;
	public static int MAXT = MAXN * 41;
	public static int MAXE = MAXN * 201;
	public static int MAXP = 17;
	public static int MAXK = MAXN * MAXP;
	public static int n, m;

	public static int[] head1 = new int[MAXN];
	public static int[] next1 = new int[MAXN << 1];
	public static int[] to1 = new int[MAXN << 1];
	public static int cnt1;

	public static int[] indegree = new int[MAXT];
	public static int[] head2 = new int[MAXT];
	public static int[] next2 = new int[MAXE];
	public static int[] to2 = new int[MAXE];
	public static int cnt2;

	public static int[] dep = new int[MAXN];
	public static int[] dfn = new int[MAXN];
	public static int[] siz = new int[MAXN];
	public static int[] stjump = new int[MAXK];
	public static int cntd;

	public static int[] stout = new int[MAXK];
	public static int[] stin = new int[MAXK];
	public static int cntt;

	public static int[] que = new int[MAXT];
	public static int[] ans = new int[MAXN];

	public static int idx(int u, int p) {
		return u * MAXP + p;
	}

	public static void addEdge1(int u, int v) {
		next1[++cnt1] = head1[u];
		to1[cnt1] = v;
		head1[u] = cnt1;
	}

	public static void addEdge2(int u, int v) {
		indegree[v]++;
		next2[++cnt2] = head2[u];
		to2[cnt2] = v;
		head2[u] = cnt2;
	}

	public static void build(int u, int fa) {
		dep[u] = dep[fa] + 1;
		dfn[u] = ++cntd;
		siz[u] = 1;
		stjump[idx(u, 0)] = fa;
		stout[idx(u, 0)] = ++cntt;
		addEdge2(u, cntt);
		addEdge2(fa, cntt);
		stin[idx(u, 0)] = ++cntt;
		addEdge2(cntt, u);
		addEdge2(cntt, fa);
		for (int p = 1; p < MAXP; p++) {
			stjump[idx(u, p)] = stjump[idx(stjump[idx(u, p - 1)], p - 1)];
			stout[idx(u, p)] = ++cntt;
			addEdge2(stout[idx(u, p - 1)], cntt);
			addEdge2(stout[idx(stjump[idx(u, p - 1)], p - 1)], cntt);
			stin[idx(u, p)] = ++cntt;
			addEdge2(cntt, stin[idx(u, p - 1)]);
			addEdge2(cntt, stin[idx(stjump[idx(u, p - 1)], p - 1)]);
		}
		for (int e = head1[u]; e > 0; e = next1[e]) {
			int v = to1[e];
			if (v != fa) {
				build(v, u);
				siz[u] += siz[v];
			}
		}
	}

	public static boolean isAncestor(int a, int b) {
		return dfn[a] <= dfn[b] && dfn[b] < dfn[a] + siz[a];
	}

	public static int kthAncestor(int x, int k) {
		for (int p = 0; p < MAXP; p++) {
			if (((k >> p) & 1) != 0) {
				x = stjump[idx(x, p)];
			}
		}
		return x;
	}

	public static int nearest(int x, int y) {
		if (isAncestor(y, x)) {
			return kthAncestor(x, dep[x] - dep[y] - 1);
		} else {
			return stjump[idx(y, 0)];
		}
	}

	public static void pathOut(int x, int y, int c) {
		if (dep[x] < dep[y]) {
			int tmp = x;
			x = y;
			y = tmp;
		}
		addEdge2(y, c);
		for (int p = MAXP - 1; p >= 0; p--) {
			if (dep[stjump[idx(x, p)]] >= dep[y]) {
				addEdge2(stout[idx(x, p)], c);
				x = stjump[idx(x, p)];
			}
		}
		if (x == y) {
			return;
		}
		for (int p = MAXP - 1; p >= 0; p--) {
			if (stjump[idx(x, p)] != stjump[idx(y, p)]) {
				addEdge2(stout[idx(x, p)], c);
				addEdge2(stout[idx(y, p)], c);
				x = stjump[idx(x, p)];
				y = stjump[idx(y, p)];
			}
		}
		addEdge2(stout[idx(x, 0)], c);
	}

	public static void pathIn(int x, int y, int c) {
		if (dep[x] < dep[y]) {
			int tmp = x;
			x = y;
			y = tmp;
		}
		addEdge2(c, y);
		for (int p = MAXP - 1; p >= 0; p--) {
			if (dep[stjump[idx(x, p)]] >= dep[y]) {
				addEdge2(c, stin[idx(x, p)]);
				x = stjump[idx(x, p)];
			}
		}
		if (x == y) {
			return;
		}
		for (int p = MAXP - 1; p >= 0; p--) {
			if (stjump[idx(x, p)] != stjump[idx(y, p)]) {
				addEdge2(c, stin[idx(x, p)]);
				addEdge2(c, stin[idx(y, p)]);
				x = stjump[idx(x, p)];
				y = stjump[idx(y, p)];
			}
		}
		addEdge2(c, stin[idx(x, 0)]);
	}

	public static void pathMin(int a, int b, int c) {
		if (a != c) {
			pathIn(a, nearest(a, c), c);
		}
		if (b != c) {
			pathIn(b, nearest(b, c), c);
		}
	}

	public static void pathMax(int a, int b, int c) {
		if (a != c) {
			pathOut(a, nearest(a, c), c);
		}
		if (b != c) {
			pathOut(b, nearest(b, c), c);
		}
	}

	public static boolean topo() {
		int qi = 1, qsiz = 0;
		for (int i = 1; i <= cntt; i++) {
			if (indegree[i] == 0) {
				que[++qsiz] = i;
			}
		}
		int val = 0;
		while (qi <= qsiz) {
			int u = que[qi++];
			if (u >= 1 && u <= n) {
				ans[u] = ++val;
			}
			for (int e = head2[u]; e > 0; e = next2[e]) {
				int v = to2[e];
				if (--indegree[v] == 0) {
					que[++qsiz] = v;
				}
			}
		}
		return qsiz == cntt;
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		cntt = n;
		for (int i = 1, u, v; i < n; i++) {
			u = in.nextInt();
			v = in.nextInt();
			addEdge1(u, v);
			addEdge1(v, u);
		}
		build(1, 1);
		for (int i = 1, op, a, b, c; i <= m; i++) {
			op = in.nextInt();
			a = in.nextInt();
			b = in.nextInt();
			c = in.nextInt();
			if (op == 1) {
				pathMin(a, b, c);
			} else {
				pathMax(a, b, c);
			}
		}
		boolean check = topo();
		if (check) {
			for (int i = 1; i <= n; i++) {
				out.print(ans[i]);
				out.print(" ");
			}
		} else {
			out.println(-1);
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
				if (len <= 0) {
					return -1;
				}
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