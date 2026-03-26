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

	public static int[] source = new int[MAXN];
	public static int[] target = new int[MAXN];

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
	public static int[][] stjump = new int[MAXN][MAXP];
	public static int cntd;

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
		dfn[u] = ++cntd;
		siz[u] = 1;
		stjump[u][0] = fa;
		stout[u][0] = ++cntt;
		addEdge2(source[u], cntt);
		addEdge2(source[fa], cntt);
		stin[u][0] = ++cntt;
		addEdge2(cntt, target[u]);
		addEdge2(cntt, target[fa]);
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
				siz[u] += siz[v];
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
				dfn[u] = ++cntd;
				siz[u] = 1;
				stjump[u][0] = fa;
				stout[u][0] = ++cntt;
				addEdge2(source[u], cntt);
				addEdge2(source[fa], cntt);
				stin[u][0] = ++cntt;
				addEdge2(cntt, target[u]);
				addEdge2(cntt, target[fa]);
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
			} else {
				for (int ei = head1[u]; ei > 0; ei = next1[ei]) {
					int v = to1[ei];
					if (v != fa) {
						siz[u] += siz[v];
					}
				}
			}
		}
	}

	public static boolean isAncestor(int a, int b) {
		return dfn[a] <= dfn[b] && dfn[b] < dfn[a] + siz[a];
	}

	public static int kthAncestor(int x, int k) {
		for (int p = 0; p < MAXP; p++) {
			if (((k >> p) & 1) != 0) {
				x = stjump[x][p];
			}
		}
		return x;
	}

	// 返回x到y的路径上，离y最近的点
	public static int nearest(int x, int y) {
		if (isAncestor(y, x)) {
			return kthAncestor(x, dep[x] - dep[y] - 1);
		} else {
			return stjump[y][0];
		}
	}

	public static void pathOut(int x, int y, int vnode) {
		if (dep[x] < dep[y]) {
			int tmp = x;
			x = y;
			y = tmp;
		}
		addEdge2(source[y], vnode);
		for (int p = MAXP - 1, fx; p >= 0; p--) {
			fx = stjump[x][p];
			if (dep[fx] >= dep[y]) {
				addEdge2(stout[x][p], vnode);
				x = fx;
			}
		}
		if (x == y) {
			return;
		}
		for (int p = MAXP - 1, fx, fy; p >= 0; p--) {
			fx = stjump[x][p];
			fy = stjump[y][p];
			if (fx != fy) {
				addEdge2(stout[x][p], vnode);
				addEdge2(stout[y][p], vnode);
				x = fx;
				y = fy;
			}
		}
		addEdge2(stout[x][0], vnode);
	}

	public static void pathIn(int x, int y, int vnode) {
		if (dep[x] < dep[y]) {
			int tmp = x;
			x = y;
			y = tmp;
		}
		addEdge2(vnode, target[y]);
		for (int p = MAXP - 1, fx; p >= 0; p--) {
			fx = stjump[x][p];
			if (dep[fx] >= dep[y]) {
				addEdge2(vnode, stin[x][p]);
				x = fx;
			}
		}
		if (x == y) {
			return;
		}
		for (int p = MAXP - 1, fx, fy; p >= 0; p--) {
			fx = stjump[x][p];
			fy = stjump[y][p];
			if (fx != fy) {
				addEdge2(vnode, stin[x][p]);
				addEdge2(vnode, stin[y][p]);
				x = fx;
				y = fy;
			}
		}
		addEdge2(vnode, stin[x][0]);
	}

	public static void link(int x, int y) {
		int vnode = ++cntt;
		if (stjump[x][0] != y && stjump[y][0] != x) {
			int a = nearest(x, y);
			int b = nearest(y, x);
			pathOut(a, b, vnode);
			pathIn(a, b, vnode);
		}
		addEdge2(vnode, source[x]);
		addEdge2(vnode, target[x]);
		addEdge2(source[y], vnode);
		addEdge2(target[y], vnode);
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
		cnt1 = cnt2 = cntt = cntd = 0;
		dep[1] = 0;
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		t = in.nextInt();
		for (int c = 1; c <= t; c++) {
			n = in.nextInt();
			cntt = n << 1;
			for (int i = 1; i <= n; i++) {
				source[i] = i;
				target[i] = i + n;
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
