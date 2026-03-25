package class195;

// 监狱，java版
// 测试链接 : https://www.luogu.com.cn/problem/P9520
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code08_Jail1 {

	public static int MAXN = 120001;
	public static int MAXT = MAXN * 50;
	public static int MAXE = MAXN * 200;
	public static int MAXP = 18;
	public static int t, n, m;

	public static int[] startArr = new int[MAXN];
	public static int[] endArr = new int[MAXN];

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
	public static int[][] stjump = new int[MAXN][MAXP];
	public static int[][] stout = new int[MAXN][MAXP];
	public static int[][] stin = new int[MAXN][MAXP];
	public static int cntt;

	public static int[] que = new int[MAXT];

	// 讲解118，递归改迭代需要的栈
	public static int[][] ufe = new int[MAXN][3];
	public static int stacksize, u, fa, e;

	public static void push(int u, int fa, int e) {
		ufe[stacksize][0] = u;
		ufe[stacksize][1] = fa;
		ufe[stacksize][2] = e;
		stacksize++;
	}

	public static void pop() {
		--stacksize;
		u = ufe[stacksize][0];
		fa = ufe[stacksize][1];
		e = ufe[stacksize][2];
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

	// 递归版
	public static void build1(int u, int fa) {
		dep[u] = dep[fa] + 1;
		stjump[u][0] = fa;
		stout[u][0] = ++cntt;
		addEdge2(startArr[u], cntt);
		addEdge2(startArr[fa], cntt);
		stin[u][0] = ++cntt;
		addEdge2(cntt, endArr[u]);
		addEdge2(cntt, endArr[fa]);
		for (int p = 1; p < MAXP; p++) {
			stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
			stout[u][p] = ++cntt;
			addEdge2(stout[u][p - 1], cntt);
			addEdge2(stout[stjump[u][p - 1]][p - 1], cntt);
			stin[u][p] = ++cntt;
			addEdge2(cntt, stin[u][p - 1]);
			addEdge2(cntt, stin[stjump[u][p - 1]][p - 1]);
		}
		for (int e = head1[u]; e > 0; e = next1[e]) {
			int v = to1[e];
			if (v != fa) {
				build1(v, u);
			}
		}
	}

	// 迭代版
	public static void build2(int cur, int father) {
		stacksize = 0;
		push(cur, father, -1);
		while (stacksize > 0) {
			pop();
			if (e == -1) {
				dep[u] = dep[fa] + 1;
				stjump[u][0] = fa;
				stout[u][0] = ++cntt;
				addEdge2(startArr[u], cntt);
				addEdge2(startArr[fa], cntt);
				stin[u][0] = ++cntt;
				addEdge2(cntt, endArr[u]);
				addEdge2(cntt, endArr[fa]);
				for (int p = 1; p < MAXP; p++) {
					stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
					stout[u][p] = ++cntt;
					addEdge2(stout[u][p - 1], cntt);
					addEdge2(stout[stjump[u][p - 1]][p - 1], cntt);
					stin[u][p] = ++cntt;
					addEdge2(cntt, stin[u][p - 1]);
					addEdge2(cntt, stin[stjump[u][p - 1]][p - 1]);
				}
				e = head1[u];
			} else {
				e = next1[e];
			}
			if (e != 0) {
				push(u, fa, e);
				if (to1[e] != fa) {
					push(to1[e], u, -1);
				}
			}
		}
	}

	public static int getLca(int x, int y) {
		if (dep[x] < dep[y]) {
			int tmp = x;
			x = y;
			y = tmp;
		}
		for (int p = MAXP - 1; p >= 0; p--) {
			if (dep[stjump[x][p]] >= dep[y]) {
				x = stjump[x][p];
			}
		}
		if (x == y) {
			return x;
		}
		for (int p = MAXP - 1; p >= 0; p--) {
			if (stjump[x][p] != stjump[y][p]) {
				x = stjump[x][p];
				y = stjump[y][p];
			}
		}
		return stjump[x][0];
	}

	public static int kthAncestor(int x, int k) {
		for (int p = MAXP - 1; p >= 0; p--) {
			if (((k >> p) & 1) != 0) {
				x = stjump[x][p];
			}
		}
		return x;
	}

	public static void chainOut(int x, int y, int vnode) {
		addEdge2(startArr[y], vnode);
		for (int p = MAXP - 1; p >= 0; p--) {
			if (dep[stjump[x][p]] >= dep[y]) {
				addEdge2(stout[x][p], vnode);
				x = stjump[x][p];
			}
		}
	}

	public static void chainIn(int x, int y, int vnode) {
		addEdge2(vnode, endArr[y]);
		for (int p = MAXP - 1; p >= 0; p--) {
			if (dep[stjump[x][p]] >= dep[y]) {
				addEdge2(vnode, stin[x][p]);
				x = stjump[x][p];
			}
		}
	}

	public static void chainLink(int x, int y, int vnode) {
		if (dep[x] - dep[y] >= 2) {
			int a = stjump[x][0];
			int b = kthAncestor(x, dep[x] - dep[y] - 1);
			chainOut(a, b, vnode);
			chainIn(a, b, vnode);
		}
	}

	public static void link(int x, int y) {
		int vnode = ++cntt;
		addEdge2(vnode, startArr[x]);
		addEdge2(endArr[y], vnode);
		addEdge2(startArr[y], vnode);
		addEdge2(vnode, endArr[x]);
		int lca = getLca(x, y);
		if (lca == x) {
			chainLink(y, x, vnode);
		} else if (lca == y) {
			chainLink(x, y, vnode);
		} else {
			chainLink(x, lca, vnode);
			chainLink(y, lca, vnode);
			addEdge2(startArr[lca], vnode);
			addEdge2(vnode, endArr[lca]);
		}
	}

	public static boolean topo() {
		int qi = 1, qsiz = 0;
		for (int i = 1; i <= cntt; i++) {
			if (indegree[i] == 0) {
				que[++qsiz] = i;
			}
		}
		while (qi <= qsiz) {
			int u = que[qi++];
			for (int e = head2[u]; e > 0; e = next2[e]) {
				int v = to2[e];
				if (--indegree[v] == 0) {
					que[++qsiz] = v;
				}
			}
		}
		return qsiz == cntt;
	}

	public static void clear() {
		for (int i = 1; i <= n; i++) {
			head1[i] = 0;
		}
		for (int i = 1; i <= cntt; i++) {
			head2[i] = indegree[i] = 0;
		}
		cnt1 = cnt2 = cntt = 0;
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		t = in.nextInt();
		for (int c = 1; c <= t; c++) {
			n = in.nextInt();
			cntt = n << 1;
			for (int i = 1; i <= n; i++) {
				startArr[i] = i;
				endArr[i] = i + n;
			}
			for (int i = 1, u, v; i < n; i++) {
				u = in.nextInt();
				v = in.nextInt();
				addEdge1(u, v);
				addEdge1(v, u);
			}
			m = in.nextInt();
			// build1(1, 1);
			build2(1, 1);
			for (int i = 1, x, y; i <= m; i++) {
				x = in.nextInt();
				y = in.nextInt();
				link(x, y);
			}
			boolean ans = topo();
			out.println(ans ? "Yes" : "No");
			clear();
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
