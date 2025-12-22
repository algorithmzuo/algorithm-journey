package class186;

// 暴力写挂，java版
// 两棵树t1、t2都有n个节点，都以1号节点作为树头
// 各自给定n-1条边，以及每条边的边权，边权可能为负数
// 在t1中，点x到树头的距离记为dis1(x)，在t2中，该距离记为dis2(x)
// 点x和点y在t1中的LCA记为lca1(x, y)，在t2中的LCA记为lca2(x, y)
// 点对(x, y)的指标 = dis1(x) + dis1(y) - dis1(lca1(x, y)) - dis2(lca2(x, y))
// 点对(x, y)，要求x <= y，打印这个指标的最大值
// 1 <= n <= 366666
// 测试链接 : https://www.luogu.com.cn/problem/P4565
// 提交以下的code，提交时请把类名改成"Main"
// java实现的逻辑一定是正确的，因为递归爆栈、卡空间，导致无法通过，索性保持最易懂的写法
// 想通过用C++实现，本节课Code03_ViolentWriting2文件就是C++的实现
// 两个版本的逻辑完全一样，C++版本可以通过所有测试

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code03_ViolentWriting1 {

	public static int MAXN = 366667;
	public static int MAXM = MAXN << 1;
	public static int MAXT = 10000001;
	public static long INF = 1L << 50;
	public static int n, cntn;

	public static int[] head1 = new int[MAXN];
	public static int[] next1 = new int[MAXN << 1];
	public static int[] to1 = new int[MAXN << 1];
	public static int[] weight1 = new int[MAXN << 1];
	public static int cnt1;

	public static long[] dis1 = new long[MAXN];

	public static int[] head2 = new int[MAXM];
	public static int[] next2 = new int[MAXM << 1];
	public static int[] to2 = new int[MAXM << 1];
	public static int[] weight2 = new int[MAXM << 1];
	public static int cnt2;

	public static int[] head3 = new int[MAXN];
	public static int[] next3 = new int[MAXN << 1];
	public static int[] to3 = new int[MAXN << 1];
	public static int[] weight3 = new int[MAXN << 1];
	public static int cnt3;

	public static boolean[] vis = new boolean[MAXM];
	public static int[] siz = new int[MAXM];

	public static int[] up = new int[MAXN];
	public static int[] root = new int[MAXN];
	public static int[] ls = new int[MAXT];
	public static int[] rs = new int[MAXT];
	public static long[] lmax = new long[MAXT];
	public static long[] rmax = new long[MAXT];
	public static int cntt;

	public static long ans;

	public static void addEdge1(int u, int v, int w) {
		next1[++cnt1] = head1[u];
		to1[cnt1] = v;
		weight1[cnt1] = w;
		head1[u] = cnt1;
	}

	public static void addEdge2(int u, int v, int w) {
		next2[++cnt2] = head2[u];
		to2[cnt2] = v;
		weight2[cnt2] = w;
		head2[u] = cnt2;
	}

	public static void addEdge3(int u, int v, int w) {
		next3[++cnt3] = head3[u];
		to3[cnt3] = v;
		weight3[cnt3] = w;
		head3[u] = cnt3;
	}

	public static void getDist(int u, int fa, long dist1) {
		dis1[u] = dist1;
		for (int e = head1[u]; e > 0; e = next1[e]) {
			int v = to1[e];
			if (v != fa) {
				getDist(v, u, dist1 + weight1[e]);
			}
		}
	}

	public static void rebuild(int u, int fa) {
		int last = 0;
		for (int e = head1[u]; e > 0; e = next1[e]) {
			int v = to1[e];
			int w = weight1[e];
			if (v != fa) {
				if (last == 0) {
					last = u;
					addEdge2(u, v, w);
					addEdge2(v, u, w);
				} else {
					int add = ++cntn;
					addEdge2(last, add, 0);
					addEdge2(add, last, 0);
					addEdge2(add, v, w);
					addEdge2(v, add, w);
					last = add;
				}
				rebuild(v, u);
			}
		}
	}

	public static void getSize(int u, int fa) {
		siz[u] = 1;
		for (int e = head2[u]; e > 0; e = next2[e]) {
			int v = to2[e];
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
			for (int e = head2[u]; e > 0; e = next2[e]) {
				int v = to2[e];
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

	public static void dfs(int u, int fa, long dist, int op) {
		if (u <= n) {
			if (up[u] == 0) {
				up[u] = ++cntt;
				root[u] = cntt;
			}
			int cur = up[u];
			int nxt = ++cntt;
			if (op == 0) {
				ls[cur] = nxt;
				lmax[cur] = dis1[u] + dist;
			} else {
				rs[cur] = nxt;
				rmax[cur] = dis1[u] + dist;
			}
			up[u] = nxt;
		}
		for (int e = head2[u]; e > 0; e = next2[e]) {
			int v = to2[e];
			if (v != fa && !vis[e >> 1]) {
				dfs(v, u, dist + weight2[e], op);
			}
		}
	}

	public static void solve(int u) {
		int edge = getCentroidEdge(u, 0);
		if (edge > 0) {
			vis[edge >> 1] = true;
			int v1 = to2[edge];
			int v2 = to2[edge ^ 1];
			dfs(v1, 0, 0, 0);
			dfs(v2, 0, weight2[edge], 1);
			solve(v1);
			solve(v2);
		}
	}

	public static int mergeTree(int x, int y, long t) {
		if (x == 0 || y == 0) {
			return x + y;
		}
		ans = Math.max(ans, Math.max(lmax[x] + rmax[y], lmax[y] + rmax[x]) + t * 2);
		lmax[x] = Math.max(lmax[x], lmax[y]);
		rmax[x] = Math.max(rmax[x], rmax[y]);
		ls[x] = mergeTree(ls[x], ls[y], t);
		rs[x] = mergeTree(rs[x], rs[y], t);
		return x;
	}

	public static void compute(int u, int fa, long dist2) {
		ans = Math.max(ans, (dis1[u] - dist2) * 2);
		for (int e = head3[u]; e > 0; e = next3[e]) {
			int v = to3[e];
			if (v != fa) {
				compute(v, u, dist2 + weight3[e]);
				root[u] = mergeTree(root[u], root[v], -dist2);
			}
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		for (int i = 1, u, v, w; i < n; i++) {
			u = in.nextInt();
			v = in.nextInt();
			w = in.nextInt();
			addEdge1(u, v, w);
			addEdge1(v, u, w);
		}
		for (int i = 1, u, v, w; i < n; i++) {
			u = in.nextInt();
			v = in.nextInt();
			w = in.nextInt();
			addEdge3(u, v, w);
			addEdge3(v, u, w);
		}
		for (int i = 1; i < MAXT; i++) {
			lmax[i] = rmax[i] = -INF;
		}
		cntn = n;
		cnt2 = 1;
		ans = -INF;
		getDist(1, 0, 0);
		rebuild(1, 0);
		solve(1);
		compute(1, 0, 0);
		out.println(ans >> 1);
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