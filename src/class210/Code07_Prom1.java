package class210;

// 新生舞会，java版
// 一共k名男生和k名女生，要组成k对舞伴，每对一男一女，每人恰好配对一次
// 给定矩阵a，a[i][j]表示第i名男生和第j名女生配对时的喜爱值
// 给定矩阵b，b[i][j]表示第i名男生和第j名女生配对时的讨厌值
// 一个配对方案的得分 = k对舞伴的喜爱值之和 / k对舞伴的讨厌值之和
// 求所有配对方案中的最大得分，答案四舍五入保留6位小数
// 1 <= k <= 100
// 1 <= 喜爱值、讨厌值 <= 10^4
// 测试链接 : https://www.luogu.com.cn/problem/P3705
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class Code07_Prom1 {

	public static int MAXN = 301;
	public static int MAXM = 20001;
	public static long INF = 1L << 60;
	public static int k, n, s, t;

	public static int[][] a = new int[MAXN][MAXN];
	public static int[][] b = new int[MAXN][MAXN];

	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXM << 1];
	public static int[] to = new int[MAXM << 1];
	public static long[] weight = new long[MAXM << 1];
	public static double[] cost = new double[MAXM << 1];
	public static int cntg;

	public static long[] flow = new long[MAXN];
	public static int[] pre = new int[MAXN];

	public static Queue<Integer> que = new LinkedList<>();
	public static double[] h = new double[MAXN];
	public static int[] update = new int[MAXN];
	public static boolean[] enter = new boolean[MAXN];

	public static PriorityQueue<double[]> heap = new PriorityQueue<>((a, b) -> Double.compare(a[0], b[0]));
	public static double[] dist = new double[MAXN];
	public static boolean[] vis = new boolean[MAXN];

	public static long maxflow;
	public static double mincost;

	public static void addEdge(int u, int v, long w, double c) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		weight[cntg] = w;
		cost[cntg] = c;
		head[u] = cntg;
	}

	public static boolean spfa(int s) {
		for (int i = 1; i <= n; i++) {
			h[i] = INF;
			update[i] = 0;
			enter[i] = false;
		}
		h[s] = 0;
		update[s] = 1;
		enter[s] = true;
		que.clear();
		que.offer(s);
		while (!que.isEmpty()) {
			int u = que.poll();
			enter[u] = false;
			for (int e = head[u]; e > 0; e = nxt[e]) {
				int v = to[e];
				long w = weight[e];
				double c = cost[e];
				if (w > 0 && h[v] > h[u] + c) {
					h[v] = h[u] + c;
					if (!enter[v]) {
						if (++update[v] > n) {
							return true;
						}
						que.offer(v);
						enter[v] = true;
					}
				}
			}
		}
		return false;
	}

	public static void dijkstra(int s) {
		for (int i = 1; i <= n; i++) {
			dist[i] = INF;
			flow[i] = -1;
			vis[i] = false;
		}
		flow[s] = INF;
		dist[s] = 0;
		heap.clear();
		heap.add(new double[] { 0, s });
		while (!heap.isEmpty()) {
			double[] cur = heap.poll();
			double d = cur[0];
			int u = (int) cur[1];
			if (!vis[u]) {
				vis[u] = true;
				for (int e = head[u]; e > 0; e = nxt[e]) {
					int v = to[e];
					long w = weight[e];
					double c = cost[e] + h[u] - h[v];
					if (w > 0 && !vis[v] && dist[v] > d + c) {
						dist[v] = dist[u] + c;
						flow[v] = Math.min(w, flow[u]);
						pre[v] = e;
						heap.add(new double[] { dist[v], v });
					}
				}
			}
		}
	}

	public static boolean ssp(int s, int t) {
		boolean negCycle = spfa(s);
		if (negCycle) {
			return true;
		}
		maxflow = 0;
		mincost = 0;
		dijkstra(s);
		while (flow[t] != -1) {
			maxflow += flow[t];
			for (int u = t; u != s; u = to[pre[u] ^ 1]) {
				weight[pre[u]] -= flow[t];
				weight[pre[u] ^ 1] += flow[t];
				mincost += flow[t] * cost[pre[u]];
			}
			for (int i = 1; i <= n; i++) {
				if (dist[i] != INF) {
					h[i] += dist[i];
				}
			}
			dijkstra(s);
		}
		return false;
	}

	public static boolean check(double x) {
		Arrays.fill(head, 1, n + 1, 0);
		cntg = 1;
		// 男生i的编号为i，女生i的编号为k + i，都是最多被选一次
		for (int i = 1; i <= k; i++) {
			addEdge(s, i, 1, 0);
			addEdge(i, s, 0, 0);
			addEdge(k + i, t, 1, 0);
			addEdge(t, k + i, 0, 0);
		}
		// 男生i选女生j，只能配对一次，注意费用的设置，就是01分数规划
		for (int i = 1; i <= k; i++) {
			for (int j = 1; j <= k; j++) {
				// 方案得分至少为x，等价于，喜爱值之和 - x * 讨厌值之和 >= 0
				// 男生i和女生j配对，对这个差值的贡献为c
				// 正向边的费用取-c，转成用最小费用流求出最大的总贡献
				double c = a[i][j] - x * b[i][j];
				addEdge(i, k + j, 1, -c);
				addEdge(k + j, i, 0, c);
			}
		}
		ssp(s, t);
		// 任意男女都能配对，每人最多配对一次，所以最大流恰好为k
		// 每次增广的流量都是1，所以恰好成功增广k次，最终选出k对舞伴
		// -mincost是完整配对的最大总贡献，-mincost >= 0，说明存在得分至少为x的方案
		return -mincost >= 0;
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		k = in.nextInt();
		for (int i = 1; i <= k; i++) {
			for (int j = 1; j <= k; j++) {
				a[i][j] = in.nextInt();
			}
		}
		for (int i = 1; i <= k; i++) {
			for (int j = 1; j <= k; j++) {
				b[i][j] = in.nextInt();
			}
		}
		n = k << 1;
		s = ++n;
		t = ++n;
		double l = 0, r = 10000;
		for (int i = 1; i <= 60; i++) {
			double x = (l + r) / 2;
			if (check(x)) {
				l = x;
			} else {
				r = x;
			}
		}
		out.printf("%.6f\n", l);
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