package class186;

// 暴力写挂，java版
// 测试链接 : https://www.luogu.com.cn/problem/P4565

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

public class Code03_ViolentWriting1 {

	public static int MAXN = 1000001;
	public static int MAXS = 10000001;
	public static int MAXT = 10000001;
	public static long INF = 1L << 50;
	public static int n, cntn;

	public static int[] head1 = new int[MAXN];
	public static int[] next1 = new int[MAXN << 1];
	public static int[] to1 = new int[MAXN << 1];
	public static int[] weight1 = new int[MAXN << 1];
	public static int cnt1;

	public static int[] head2 = new int[MAXN];
	public static int[] next2 = new int[MAXN << 1];
	public static int[] to2 = new int[MAXN << 1];
	public static int[] weight2 = new int[MAXN << 1];
	public static int cnt2;

	public static long[] dis1 = new long[MAXN];

	public static int[] sonCnt = new int[MAXN];
	public static int[] heads = new int[MAXN];
	public static int[] nexts = new int[MAXS];
	public static int[] sons = new int[MAXS];
	public static int[] weights = new int[MAXS];
	public static int cnts;

	public static boolean[] vis = new boolean[MAXN];
	public static int[] siz = new int[MAXN];

	public static long[] mx0 = new long[MAXT];
	public static long[] mx1 = new long[MAXT];
	public static int[] ch0 = new int[MAXT];
	public static int[] ch1 = new int[MAXT];

	public static int[] lastPtr = new int[MAXN];
	public static int[] rt = new int[MAXN];
	public static int totT;

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

	public static void addSon(int u, int v, int w) {
		sonCnt[u]++;
		nexts[++cnts] = heads[u];
		sons[cnts] = v;
		weights[cnts] = w;
		heads[u] = cnts;
	}

	public static void getDist(int u, int fa, long dist) {
		dis1[u] = dist;
		for (int e = head1[u]; e > 0; e = next1[e]) {
			int v = to1[e];
			if (v != fa) {
				getDist(v, u, dist + weight1[e]);
			}
		}
	}

	public static void buildSon(int u, int fa) {
		for (int e = head1[u]; e > 0; e = next1[e]) {
			int v = to1[e];
			if (v != fa) {
				addSon(u, v, weight1[e]);
				buildSon(v, u);
			}
		}
	}

	public static void rebuildTree() {
		buildSon(1, 0);
		cnt1 = 1;
		for (int i = 1; i <= cntn; i++) {
			head1[i] = 0;
		}
		for (int u = 1; u <= cntn; u++) {
			if (sonCnt[u] <= 2) {
				for (int e = heads[u]; e > 0; e = nexts[e]) {
					int v = sons[e];
					int w = weights[e];
					addEdge1(u, v, w);
					addEdge1(v, u, w);
				}
			} else {
				int node1 = ++cntn;
				int node2 = ++cntn;
				addEdge1(u, node1, 0);
				addEdge1(node1, u, 0);
				addEdge1(u, node2, 0);
				addEdge1(node2, u, 0);
				boolean add1 = true;
				for (int e = heads[u]; e > 0; e = nexts[e]) {
					int v = sons[e];
					int w = weights[e];
					if (add1) {
						addSon(node1, v, w);
					} else {
						addSon(node2, v, w);
					}
					add1 = !add1;
				}
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
						best = cur;
						edge = e;
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
			++totT;
			if (lastPtr[u] == 0) {
				rt[u] = lastPtr[u] = totT;
				++totT;
			}
			if (op == 0) {
				ch0[lastPtr[u]] = totT;
				mx0[lastPtr[u]] = dis1[u] + dist;
			} else {
				ch1[lastPtr[u]] = totT;
				mx1[lastPtr[u]] = dis1[u] + dist;
			}
			lastPtr[u] = totT;
		}
		for (int e = head1[u]; e > 0; e = next1[e]) {
			int v = to1[e];
			if (v != fa && !vis[e >> 1]) {
				dfs(v, u, dist + weight1[e], op);
			}
		}
	}

	public static void solve(int u) {
		int edge = getCentroidEdge(u, 0);
		if (edge > 0) {
			vis[edge >> 1] = true;
			int v1 = to1[edge];
			int v2 = to1[edge ^ 1];
			dfs(v1, 0, 0, 0);
			dfs(v2, 0, weight1[edge], 1);
			solve(v1);
			solve(v2);
		}
	}

	public static int mergeTree(int x, int y, long t) {
		if (x == 0 || y == 0) {
			return x + y;
		}
		long cand1 = mx0[x] + mx1[y];
		long cand2 = mx0[y] + mx1[x];
		ans = Math.max(ans, Math.max(cand1, cand2) + 2L * t);
		mx0[x] = Math.max(mx0[x], mx0[y]);
		mx1[x] = Math.max(mx1[x], mx1[y]);
		ch0[x] = mergeTree(ch0[x], ch0[y], t);
		ch1[x] = mergeTree(ch1[x], ch1[y], t);
		return x;
	}

	public static void compute(int u, int fa, long dist2) {
		ans = Math.max(ans, 2L * (dis1[u] - dist2));
		for (int e = head2[u]; e > 0; e = next2[e]) {
			int v = to2[e];
			if (v != fa) {
				compute(v, u, dist2 + weight2[e]);
				rt[u] = mergeTree(rt[u], rt[v], -dist2);
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
			addEdge2(u, v, w);
			addEdge2(v, u, w);
		}
		cntn = n;
		Arrays.fill(mx0, -INF);
		Arrays.fill(mx1, -INF);
		ans = -INF;
		getDist(1, 0, 0);
		rebuildTree();
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