package class187;

// 通道，java版
// 三棵树t1、t2、t3都有n个节点，各自给定n-1条边以及每条边的边权，边权没有负数
// 点对(x, y)，要求两点必须不同，两点在树上的距离就是简单路径上边权的累加和
// 点对(x, y)，在t1、t2、t3的距离，分别记为dis1(x, y)、dis2(x, y)、dis3(x, y)
// 打印最大的dis1(x, y) + dis2(x, y) + dis3(x, y)
// 2 <= n <= 10^5
// 0 <= 边权 <= 10^12
// 测试链接 : https://www.luogu.com.cn/problem/P4220
// 测试链接 : https://loj.ac/p/2339
// 提交以下的code，提交时请把类名改成"Main"
// java实现的逻辑一定是正确的，但是需要把太多递归函数改成迭代才能通过，索性不改了
// 想通过用C++实现，本节课Code05_Passage2文件就是C++的实现
// 两个版本的逻辑完全一样，C++版本可以通过所有测试

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code05_Passage1 {

	public static int MAXN = 100001;
	public static int MAXM = MAXN << 1;
	public static int MAXP = 20;
	public static int MAXT = MAXN * 20;
	public static long INF = 1L << 60;
	public static int n, cntn;

	public static int[] head0 = new int[MAXN];
	public static int[] next0 = new int[MAXN << 1];
	public static int[] to0 = new int[MAXN << 1];
	public static long[] weight0 = new long[MAXN << 1];
	public static int cnt0;

	public static int[] head1 = new int[MAXM];
	public static int[] next1 = new int[MAXM << 1];
	public static int[] to1 = new int[MAXM << 1];
	public static long[] weight1 = new long[MAXM << 1];
	public static int cnt1;

	public static int[] head2 = new int[MAXN];
	public static int[] next2 = new int[MAXN << 1];
	public static int[] to2 = new int[MAXN << 1];
	public static long[] weight2 = new long[MAXN << 1];
	public static int cnt2;

	public static int[] head3 = new int[MAXN];
	public static int[] next3 = new int[MAXN << 1];
	public static int[] to3 = new int[MAXN << 1];
	public static long[] weight3 = new long[MAXN << 1];
	public static int cnt3;

	public static long[] dep2 = new long[MAXN];
	public static long[] dist3 = new long[MAXN];

	public static boolean[] vis = new boolean[MAXM];
	public static int[] siz = new int[MAXM];

	public static int[] dfn = new int[MAXN];
	public static int[] lg2 = new int[MAXN];
	public static int[][] rmq = new int[MAXN][MAXP];
	public static int cntd;

	public static int[] up = new int[MAXN];
	public static int[] root = new int[MAXN];
	public static int[] ls = new int[MAXT];
	public static int[] rs = new int[MAXT];
	public static int[] lx = new int[MAXT];
	public static int[] ly = new int[MAXT];
	public static long[] lxv = new long[MAXT];
	public static long[] lyv = new long[MAXT];
	public static int[] rx = new int[MAXT];
	public static int[] ry = new int[MAXT];
	public static long[] rxv = new long[MAXT];
	public static long[] ryv = new long[MAXT];
	public static int cntt;

	public static long ans;

	public static void addEdge0(int u, int v, long w) {
		next0[++cnt0] = head0[u];
		to0[cnt0] = v;
		weight0[cnt0] = w;
		head0[u] = cnt0;
	}

	public static void addEdge1(int u, int v, long w) {
		next1[++cnt1] = head1[u];
		to1[cnt1] = v;
		weight1[cnt1] = w;
		head1[u] = cnt1;
	}

	public static void addEdge2(int u, int v, long w) {
		next2[++cnt2] = head2[u];
		to2[cnt2] = v;
		weight2[cnt2] = w;
		head2[u] = cnt2;
	}

	public static void addEdge3(int u, int v, long w) {
		next3[++cnt3] = head3[u];
		to3[cnt3] = v;
		weight3[cnt3] = w;
		head3[u] = cnt3;
	}

	public static void dfsTree2(int u, int fa, long dep) {
		dep2[u] = dep;
		for (int e = head2[u]; e > 0; e = next2[e]) {
			int v = to2[e];
			if (v != fa) {
				dfsTree2(v, u, dep + weight2[e]);
			}
		}
	}

	public static void rebuildTree1(int u, int fa) {
		int last = 0;
		for (int e = head0[u]; e > 0; e = next0[e]) {
			int v = to0[e];
			long w = weight0[e];
			if (v != fa) {
				if (last == 0) {
					last = u;
					addEdge1(u, v, w);
					addEdge1(v, u, w);
				} else {
					int add = ++cntn;
					addEdge1(last, add, 0);
					addEdge1(add, last, 0);
					addEdge1(add, v, w);
					addEdge1(v, add, w);
					last = add;
				}
				rebuildTree1(v, u);
			}
		}
	}

	public static void getSize(int u, int fa) {
		siz[u] = 1;
		for (int e = head1[u]; e > 0; e = next1[e]) {
			int v = to1[e];
			if (v != fa && !vis[e >> 1]) {
				getSize(v, u);
				siz[u] += siz[v];
			}
		}
	}

	public static int getCentroidEdge(int u, int fa) {
		getSize(u, fa);
		int total = siz[u];
		int half = total >> 1;
		boolean find = false;
		while (!find) {
			find = true;
			for (int e = head1[u]; e > 0; e = next1[e]) {
				int v = to1[e];
				if (v != fa && !vis[e >> 1] && siz[v] > half) {
					fa = u;
					u = v;
					find = false;
					break;
				}
			}
		}
		int best = 0, edge = 0;
		for (int e = head1[u]; e > 0; e = next1[e]) {
			if (!vis[e >> 1]) {
				int v = to1[e];
				int sub = v == fa ? (total - siz[u]) : siz[v];
				if (sub > best) {
					best = sub;
					edge = e;
				}
			}
		}
		return edge;
	}

	public static void dfsTree1(int u, int fa, long path, int op) {
		if (u <= n) {
			if (up[u] == 0) {
				up[u] = ++cntt;
				root[u] = cntt;
			}
			int cur = up[u];
			int nxt = ++cntt;
			long val = path + dep2[u];
			if (op == 0) {
				ls[cur] = nxt;
				lx[cur] = ly[cur] = u;
				lxv[cur] = lyv[cur] = val;
			} else {
				rs[cur] = nxt;
				rx[cur] = ry[cur] = u;
				rxv[cur] = ryv[cur] = val;
			}
			up[u] = nxt;
		}
		for (int e = head1[u]; e > 0; e = next1[e]) {
			int v = to1[e];
			if (v != fa && !vis[e >> 1]) {
				dfsTree1(v, u, path + weight1[e], op);
			}
		}
	}

	public static void solveTree1(int u) {
		int edge = getCentroidEdge(u, 0);
		if (edge > 0) {
			vis[edge >> 1] = true;
			int v1 = to1[edge];
			int v2 = to1[edge ^ 1];
			dfsTree1(v1, 0, 0, 0);
			dfsTree1(v2, 0, weight1[edge], 1);
			solveTree1(v1);
			solveTree1(v2);
		}
	}

	public static int getUp(int x, int y) {
		return dfn[x] < dfn[y] ? x : y;
	}

	public static void dfsTree3(int u, int fa, long dist) {
		dfn[u] = ++cntd;
		rmq[dfn[u]][0] = fa;
		dist3[u] = dist;
		for (int e = head3[u]; e > 0; e = next3[e]) {
			int v = to3[e];
			if (v != fa) {
				dfsTree3(v, u, dist + weight3[e]);
			}
		}
	}

	public static void rmqTree3() {
		dfsTree3(1, 0, 0);
		for (int i = 2; i <= n; i++) {
			lg2[i] = lg2[i >> 1] + 1;
		}
		for (int pre = 0, cur = 1; cur <= lg2[n]; pre++, cur++) {
			for (int i = 1; i + (1 << cur) - 1 <= n; i++) {
				rmq[i][cur] = getUp(rmq[i][pre], rmq[i + (1 << pre)][pre]);
			}
		}
	}

	public static int lcaTree3(int x, int y) {
		if (x == y) {
			return x;
		}
		x = dfn[x];
		y = dfn[y];
		if (x > y) {
			int tmp = x; x = y; y = tmp;
		}
		x++;
		int k = lg2[y - x + 1];
		return getUp(rmq[x][k], rmq[y - (1 << k) + 1][k]);
	}

	public static long getDist3(int x, int y) {
		return dist3[x] + dist3[y] - dist3[lcaTree3(x, y)] * 2;
	}

	public static long getDist(int x, int y, long xv, long yv) {
		if (x == y) {
			return 0;
		}
		return getDist3(x, y) + xv + yv;
	}

	public static int a, b, c, d;
	public static long av, bv, cv, dv;

	public static void getInfo(int i, int j, int iop, int jop) {
		if (iop == 0) {
			a = lx[i]; b = ly[i]; av = lxv[i]; bv = lyv[i];
		} else {
			a = rx[i]; b = ry[i]; av = rxv[i]; bv = ryv[i];
		}
		if (jop == 0) {
			c = lx[j]; d = ly[j]; cv = lxv[j]; dv = lyv[j];
		} else {
			c = rx[j]; d = ry[j]; cv = rxv[j]; dv = ryv[j];
		}
	}

	public static long bestCross(int i, int j, int iop, int jop) {
		getInfo(i, j, iop, jop);
		long p1 = getDist(a, c, av, cv);
		long p2 = getDist(a, d, av, dv);
		long p3 = getDist(b, c, bv, cv);
		long p4 = getDist(b, d, bv, dv);
		return Math.max(Math.max(p1, p2), Math.max(p3, p4));
	}

	public static int x, y;
	public static long xv, yv;
	public static long bestDist;

	public static void better(int curx, int cury, long curxv, long curyv) {
		if (curx == 0 || cury == 0) {
			return;
		}
		long curDist = getDist(curx, cury, curxv, curyv);
		if (curDist > bestDist) {
			x = curx; y = cury; xv = curxv; yv = curyv;
			bestDist = curDist;
		}
	}

	public static void mergeInfo(int i, int j, int op) {
		getInfo(i, j, op, op);
		x = y = 0;
		xv = yv = 0;
		bestDist = -INF;
		better(a, b, av, bv);
		better(c, d, cv, dv);
		better(a, c, av, cv);
		better(a, d, av, dv);
		better(b, c, bv, cv);
		better(b, d, bv, dv);
		if (op == 0) {
			lx[i] = x; ly[i] = y; lxv[i] = xv; lyv[i] = yv;
		} else {
			rx[i] = x; ry[i] = y; rxv[i] = xv; ryv[i] = yv;
		}
	}

	public static int mergeTree(int i, int j, long add) {
		if (i == 0 || j == 0) {
			return i + j;
		}
		if (ls[i] > 0 && rs[j] > 0) {
			ans = Math.max(ans, bestCross(i, j, 0, 1) + add);
		}
		if (rs[i] > 0 && ls[j] > 0) {
			ans = Math.max(ans, bestCross(i, j, 1, 0) + add);
		}
		mergeInfo(i, j, 0);
		mergeInfo(i, j, 1);
		ls[i] = mergeTree(ls[i], ls[j], add);
		rs[i] = mergeTree(rs[i], rs[j], add);
		return i;
	}

	public static void compute(int u, int fa) {
		for (int e = head2[u]; e > 0; e = next2[e]) {
			int v = to2[e];
			if (v != fa) {
				compute(v, u);
				root[u] = mergeTree(root[u], root[v], -dep2[u] * 2);
			}
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		int u, v;
		long w;
		for (int i = 1; i < n; i++) {
			u = in.nextInt();
			v = in.nextInt();
			w = in.nextLong();
			addEdge0(u, v, w);
			addEdge0(v, u, w);
		}
		for (int i = 1; i < n; i++) {
			u = in.nextInt();
			v = in.nextInt();
			w = in.nextLong();
			addEdge2(u, v, w);
			addEdge2(v, u, w);
		}
		for (int i = 1; i < n; i++) {
			u = in.nextInt();
			v = in.nextInt();
			w = in.nextLong();
			addEdge3(u, v, w);
			addEdge3(v, u, w);
		}
		cntn = n;
		cnt1 = 1;
		ans = -INF;
		dfsTree2(1, 0, 0);
		rebuildTree1(1, 0);
		solveTree1(1);
		rmqTree3();
		compute(1, 0);
		out.println(ans);
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

		long nextLong() throws IOException {
			int c;
			do {
				c = readByte();
			} while (c <= ' ' && c != -1);
			boolean neg = false;
			if (c == '-') {
				neg = true;
				c = readByte();
			}
			long val = 0;
			while (c > ' ' && c != -1) {
				val = val * 10 + (c - '0');
				c = readByte();
			}
			return neg ? -val : val;
		}

	}

}
