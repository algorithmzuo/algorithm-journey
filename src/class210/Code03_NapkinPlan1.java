package class210;

// 餐巾计划，java版
// 测试链接 : https://www.luogu.com.cn/problem/P1251
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class Code03_NapkinPlan1 {

	public static int MAXN = 5001;
	public static int MAXM = 12001;
	public static long INF = 1L << 60;
	public static int n;

	public static int[] need = new int[MAXN];

	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXM << 1];
	public static int[] to = new int[MAXM << 1];
	public static long[] weight = new long[MAXM << 1];
	public static long[] cost = new long[MAXM << 1];
	public static int cntg;

	public static long[] flow = new long[MAXN];
	public static int[] pre = new int[MAXN];

	public static Queue<Integer> que = new LinkedList<>();
	public static long[] h = new long[MAXN];
	public static int[] update = new int[MAXN];
	public static boolean[] enter = new boolean[MAXN];

	public static PriorityQueue<long[]> heap = new PriorityQueue<>((a, b) -> Long.compare(a[0], b[0]));
	public static long[] dist = new long[MAXN];
	public static boolean[] vis = new boolean[MAXN];

	public static long maxflow, mincost;

	public static void addEdge(int u, int v, long w, long c) {
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
				long c = cost[e];
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
		heap.add(new long[] { 0, s });
		while (!heap.isEmpty()) {
			long[] cur = heap.poll();
			long d = cur[0];
			int u = (int) cur[1];
			if (!vis[u]) {
				vis[u] = true;
				for (int e = head[u]; e > 0; e = nxt[e]) {
					int v = to[e];
					long w = weight[e];
					long c = cost[e] + h[u] - h[v];
					if (w > 0 && !vis[v] && dist[v] > d + c) {
						dist[v] = dist[u] + c;
						flow[v] = Math.min(w, flow[u]);
						pre[v] = e;
						heap.add(new long[] { dist[v], v });
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
		maxflow = mincost = 0;
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

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		int days = in.nextInt();
		n = days << 1;
		int s = ++n;
		int t = ++n;
		cntg = 1;
		long total = 0;
		for (int i = 1; i <= days; i++) {
			need[i] = in.nextInt();
			total += need[i];
		}
		int buy = in.nextInt();
		int fastDays = in.nextInt();
		int fastCost = in.nextInt();
		int slowDays = in.nextInt();
		int slowCost = in.nextInt();
		for (int i = 1; i <= days; i++) {
			int dirty = i;
			int clean = days + i;
			// 第i天用完后，有need[i]块脏餐巾可供后续使用
			addEdge(s, dirty, need[i], 0);
			addEdge(dirty, s, 0, 0);
			// 第i天购买新餐巾，最多只需买当天的需求量
			addEdge(s, clean, need[i], buy);
			addEdge(clean, s, 0, -buy);
			// 满足第i天的需求，最大流时，每天的这条边都恰好流满
			addEdge(clean, t, need[i], 0);
			addEdge(t, clean, 0, 0);
			// 脏餐巾可以免费留到明天，连续走多条这样的边就能延期送洗
			if (i < days) {
				addEdge(dirty, dirty + 1, total, 0);
				addEdge(dirty + 1, dirty, 0, 0);
			}
			// 第i天送快洗，第i+fastDays天可用
			if (fastDays <= days - i) {
				int back = i + fastDays;
				addEdge(dirty, days + back, need[back], fastCost);
				addEdge(days + back, dirty, 0, -fastCost);
			}
			// 第 i 天送慢洗，第 i + slowDays 天可用
			if (slowDays <= days - i) {
				int back = i + slowDays;
				addEdge(dirty, days + back, need[back], slowCost);
				addEdge(days + back, dirty, 0, -slowCost);
			}
		}
		ssp(s, t);
		out.println(mincost);
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
