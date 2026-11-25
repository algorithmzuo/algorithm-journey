package class210;

// 通信，java版
// 一共有k个哨站，从左到右编号为1到k，第i号哨站的频段为arr[i]
// 每个哨站必须选择以下两种连接方式中的一种
// 方式1，第i号哨站直接连接控制中心，花费为w
// 方式2，第i号哨站选择左侧哨站j连接，花费为|arr[i] - arr[j]|
// 控制中心可以被多个哨站直接连接，每个哨站最多只能被一个右侧哨站直接连接
// 求所有哨站都直接或间接连上控制中心的最小总花费
// 1 <= k <= 1000
// 0 <= w、arr[i] <= 10^9
// 测试链接 : https://www.luogu.com.cn/problem/P5331
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class Code09_Communication1 {

	public static int MAXN = 20001;
	public static int MAXM = 50001;
	public static long INF = 1L << 60;
	public static int k, n;

	public static long[] arr = new long[MAXN];
	public static int[] idx = new int[MAXN];
	public static int[] tmp = new int[MAXN];

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

	public static void merge(int l, int mid, int r) {
		int p1 = l, p2 = mid + 1, ti = 0;
		while (p1 <= mid && p2 <= r) {
			if (arr[idx[p1]] <= arr[idx[p2]]) {
				tmp[++ti] = idx[p1++];
			} else {
				tmp[++ti] = idx[p2++];
			}
		}
		while (p1 <= mid) {
			tmp[++ti] = idx[p1++];
		}
		while (p2 <= r) {
			tmp[++ti] = idx[p2++];
		}
		long lval = arr[tmp[1]];
		int cur = ++n;
		for (int i = l, j = 1; i <= r; i++, j++) {
			int u = tmp[j];
			if (arr[u] != lval) {
				int pre = cur;
				cur = ++n;
				long c = arr[u] - lval;
				// 相邻频段之间双向通行，每个方向各有一条残量反向边
				addEdge(pre, cur, k, c);
				addEdge(cur, pre, 0, -c);
				addEdge(cur, pre, k, c);
				addEdge(pre, cur, 0, -c);
				lval = arr[u];
			}
			// 按原下标判断，右半区进链，左半区出链
			if (u > mid) {
				addEdge(u, cur, 1, 0);
				addEdge(cur, u, 0, 0);
			} else {
				addEdge(cur, k + u, 1, 0);
				addEdge(k + u, cur, 0, 0);
			}
			idx[i] = u;
		}
	}

	public static void cdq(int l, int r) {
		if (l == r) {
			return;
		}
		int mid = (l + r) / 2;
		cdq(l, mid);
		cdq(mid + 1, r);
		merge(l, mid, r);
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		k = in.nextInt();
		int w = in.nextInt();
		n = k << 1;
		int s = ++n;
		int t = ++n;
		cntg = 1;
		for (int i = 1; i <= k; i++) {
			arr[i] = in.nextInt();
			idx[i] = i;
			addEdge(s, i, 1, 0);
			addEdge(i, s, 0, 0);
			addEdge(i, t, 1, w);
			addEdge(t, i, 0, -w);
			addEdge(k + i, t, 1, 0);
			addEdge(t, k + i, 0, 0);
		}
		cdq(1, k);
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
