package class187;

// 通道，java版
// 测试链接 : https://www.luogu.com.cn/problem/P4220
// 测试链接 : https://loj.ac/p/2339
// 提交以下的code，提交时请把类名改成"Main"
// java实现的逻辑一定是正确的，但是需要把太多递归函数改成迭代才能通过，索性不改了
// 想通过用C++实现，本节课Code04_Passage2文件就是C++的实现
// 两个版本的逻辑完全一样，C++版本可以通过所有测试

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code04_Passage1 {

	public static int MAXN = 100001;
	public static int MAXM = MAXN << 1;
	public static int MAXH = 20;
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

	public static int[] dfn = new int[MAXN];
	public static int[] lg2 = new int[MAXN];
	public static int[][] rmq = new int[MAXN][MAXH];
	public static int cntd;

	public static long[] dist2 = new long[MAXN];
	public static long[] dist3 = new long[MAXN];

	public static boolean[] vis = new boolean[MAXM];
	public static int[] siz = new int[MAXM];

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

	public static int getUp(int x, int y) {
		return dfn[x] <= dfn[y] ? x : y;
	}

	public static void dfsTree2(int u, int fa, long dis2) {
		dfn[u] = ++cntd;
		rmq[dfn[u]][0] = fa;
		dist2[u] = dis2;
		for (int e = head2[u]; e > 0; e = next2[e]) {
			int v = to2[e];
			if (v != fa) {
				dfsTree2(v, u, dis2 + weight2[e]);
			}
		}
	}

	public static void rmqTree2() {
		dfsTree2(1, 0, 0);
		for (int i = 2; i <= n; i++) {
			lg2[i] = lg2[i >> 1] + 1;
		}
		for (int pre = 0, cur = 1; cur <= lg2[n]; pre++, cur++) {
			for (int i = 1; i + (1 << cur) - 1 <= n; i++) {
				rmq[i][cur] = getUp(rmq[i][pre], rmq[i + (1 << pre)][pre]);
			}
		}
	}

	public static int lcaTree2(int x, int y) {
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

	public static void dfsTree3(int u, int fa, long dist) {
		dist3[u] = dist;
		for (int e = head3[u]; e > 0; e = next3[e]) {
			int v = to3[e];
			if (v != fa) {
				dfsTree3(v, u, dist + weight3[e]);
			}
		}
	}

	public static void rebuild(int u, int fa) {
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
				rebuild(v, u);
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
		int edge = 0;
		int best = total;
		while (u > 0) {
			int nextu = 0, nextfa = 0;
			for (int e = head1[u]; e > 0; e = next1[e]) {
				int v = to1[e];
				if (v != fa && !vis[e >> 1]) {
					int cur = Math.max(total - siz[v], siz[v]);
					if (cur < best) {
						edge = e;
						best = cur;
						nextfa = u;
						nextu = v;
					}
				}
			}
			fa = nextfa;
			u = nextu;
		}
		return edge;
	}

	public static void dfsTree1(int u, int fa, long dis1, int op) {
		if (u <= n) {
			if (up[u] == 0) {
				up[u] = ++cntt;
				root[u] = cntt;
			}
			int cur = up[u];
			int nxt = ++cntt;
			long val = dis1 + dist3[u];
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
				dfsTree1(v, u, dis1 + weight1[e], op);
			}
		}
	}

	public static void solve(int u) {
		int edge = getCentroidEdge(u, 0);
		if (edge > 0) {
			vis[edge >> 1] = true;
			int v1 = to1[edge];
			int v2 = to1[edge ^ 1];
			dfsTree1(v1, 0, 0, 0);
			dfsTree1(v2, 0, weight1[edge], 1);
			solve(v1);
			solve(v2);
		}
	}

	public static long getDist(int x, int y, long xv, long yv) {
		if (x == y) {
			return 0;
		}
		return dist2[x] + dist2[y] - dist2[lcaTree2(x, y)] * 2 + xv + yv;
	}

	public static long bestCross(int x1, int y1, long x1v, long y1v, int x2, int y2, long x2v, long y2v) {
		long p1 = getDist(x1, x2, x1v, x2v);
		long p2 = getDist(x1, y2, x1v, y2v);
		long p3 = getDist(y1, x2, y1v, x2v);
		long p4 = getDist(y1, y2, y1v, y2v);
		return Math.max(Math.max(p1, p2), Math.max(p3, p4));
	}

	public static int x, y;
	public static long xv, yv, dist;

	public static void bestDiam(int curx, int cury, long curxv, long curyv) {
		long curDist = getDist(curx, cury, curxv, curyv);
		if (curDist > dist) {
			dist = curDist; x = curx; y = cury; xv = curxv; yv = curyv;
		}
	}

	public static void mergeInfo(int a, int b, int op) {
		int ax, ay, bx, by;
		long axv, ayv, bxv, byv;
		if (op == 0) {
			if (lx[b] == 0) {
				return;
			}
			if (lx[a] == 0) {
				lx[a] = lx[b]; ly[a] = ly[b]; lxv[a] = lxv[b]; lyv[a] = lyv[b];
				return;
			}
			ax = lx[a]; ay = ly[a]; axv = lxv[a]; ayv = lyv[a];
			bx = lx[b]; by = ly[b]; bxv = lxv[b]; byv = lyv[b];
		} else {
			if (rx[b] == 0) {
				return;
			}
			if (rx[a] == 0) {
				rx[a] = rx[b]; ry[a] = ry[b]; rxv[a] = rxv[b]; ryv[a] = ryv[b];
				return;
			}
			ax = rx[a]; ay = ry[a]; axv = rxv[a]; ayv = ryv[a];
			bx = rx[b]; by = ry[b]; bxv = rxv[b]; byv = ryv[b];
		}
		dist = -INF;
		bestDiam(ax, ay, axv, ayv);
		bestDiam(bx, by, bxv, byv);
		bestDiam(ax, bx, axv, bxv);
		bestDiam(ax, by, axv, byv);
		bestDiam(ay, bx, ayv, bxv);
		bestDiam(ay, by, ayv, byv);
		if (op == 0) {
			lx[a] = x; ly[a] = y; lxv[a] = xv; lyv[a] = yv;
		} else {
			rx[a] = x; ry[a] = y; rxv[a] = xv; ryv[a] = yv;
		}
	}

	public static int mergeTree(int a, int b, long add) {
		if (a == 0 || b == 0) {
			return a + b;
		}
		if (lx[a] > 0 && rx[b] > 0) {
			ans = Math.max(ans, bestCross(lx[a], ly[a], lxv[a], lyv[a], rx[b], ry[b], rxv[b], ryv[b]) + add);
		}
		if (rx[a] > 0 && lx[b] > 0) {
			ans = Math.max(ans, bestCross(rx[a], ry[a], rxv[a], ryv[a], lx[b], ly[b], lxv[b], lyv[b]) + add);
		}
		mergeInfo(a, b, 0);
		mergeInfo(a, b, 1);
		ls[a] = mergeTree(ls[a], ls[b], add);
		rs[a] = mergeTree(rs[a], rs[b], add);
		return a;
	}

	public static void compute(int u, int fa) {
		for (int e = head3[u]; e > 0; e = next3[e]) {
			int v = to3[e];
			if (v != fa) {
				compute(v, u);
				root[u] = mergeTree(root[u], root[v], -dist3[u] * 2);
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
		rmqTree2();
		dfsTree3(1, 0, 0);
		cntn = n;
		cnt1 = 1;
		rebuild(1, 0);
		solve(1);
		ans = -INF;
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
