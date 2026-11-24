package class210;

// 餐巾计划，java版
// 餐厅连续营业openDays天，第i天需要need[i]个干净餐巾，餐巾用完就变脏了
// 每天都可以买新餐巾，每个花费buyCost，买来当天就能使用
// 每天结束时，每个脏餐巾可以送去快洗或慢洗，也可以留到以后再洗
// 快洗每个花费fastCost，第i天送洗，第 i + fastDays 天可以使用
// 慢洗每个花费slowCost，第i天送洗，第 i + slowDays 天可以使用
// 求满足每天餐巾需求的最小总花费
// 1 <= openDays <= 2000    1 <= need[i] <= 10^7
// 1 <= buyCost、slowCost、fastCost <= 10^4
// slowCost < fastCost      fastDays < slowDays
// 测试链接 : https://www.luogu.com.cn/problem/P1251
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class Code05_NapkinPlan1 {

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
		int openDays = in.nextInt();
		for (int i = 1; i <= openDays; i++) {
			need[i] = in.nextInt();
		}
		int buyCost = in.nextInt();
		int fastDays = in.nextInt();
		int fastCost = in.nextInt();
		int slowDays = in.nextInt();
		int slowCost = in.nextInt();
		n = openDays << 1;
		int s = ++n;
		int t = ++n;
		cntg = 1;
		for (int i = 1; i <= openDays; i++) {
			// dirty和clean是最重要的概念，表示 脏餐巾处理点 和 干净餐巾拿取点
			// dirty是第i天的脏餐巾处理点，当天用脏的和以前留下的，都可以从这里送洗或继续留存
			// clean是第i天的干净餐巾拿取点，当天新买的和洗好的，都在这里供当天使用
			int dirty = i;
			int clean = i + openDays;
			// 第i天用完后，有need[i]个脏餐巾可供后续使用
			addEdge(s, dirty, need[i], 0);
			addEdge(dirty, s, 0, 0);
			// 第i天购买新餐巾，最多只需买当天的需求量
			addEdge(s, clean, need[i], buyCost);
			addEdge(clean, s, 0, -buyCost);
			// 最大流时，这条边的流量恰好为need[i]，表示第i天的餐巾需求全部得到满足
			addEdge(clean, t, need[i], 0);
			addEdge(t, clean, 0, 0);
			// 当天结束时，可以把任意数量的脏餐巾免费留到明天，以后再送洗
			if (i < openDays) {
				addEdge(dirty, dirty + 1, INF, 0);
				addEdge(dirty + 1, dirty, 0, 0);
			}
			// 第i天送快洗，第 i + fastDays 天可用
			int back = i + fastDays;
			if (back <= openDays) {
				addEdge(dirty, back + openDays, need[back], fastCost);
				addEdge(back + openDays, dirty, 0, -fastCost);
			}
			// 第i天送慢洗，第 i + slowDays 天可用
			back = i + slowDays;
			if (back <= openDays) {
				addEdge(dirty, back + openDays, need[back], slowCost);
				addEdge(back + openDays, dirty, 0, -slowCost);
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
