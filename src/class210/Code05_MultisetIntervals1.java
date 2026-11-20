package class210;

// 最长k可重区间集，java版
// 测试链接 : https://www.luogu.com.cn/problem/P3358
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class Code05_MultisetIntervals1 {

	public static int MAXN = 2001;
	public static int MAXM = 2001;
	public static int INF = 1000000001;
	public static int n;

	public static int[] l = new int[MAXN];
	public static int[] r = new int[MAXN];
	public static int[] sortv = new int[MAXN << 1];
	public static int cntv;

	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXM << 1];
	public static int[] to = new int[MAXM << 1];
	public static int[] weight = new int[MAXM << 1];
	public static int[] cost = new int[MAXM << 1];
	public static int cntg;

	public static int[] flow = new int[MAXN];
	public static int[] pre = new int[MAXN];

	public static Queue<Integer> que = new LinkedList<>();
	public static int[] h = new int[MAXN];
	public static int[] update = new int[MAXN];
	public static boolean[] enter = new boolean[MAXN];

	public static PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> Integer.compare(a[0], b[0]));
	public static int[] dist = new int[MAXN];
	public static boolean[] vis = new boolean[MAXN];

	public static int maxflow, mincost;

	public static void addEdge(int u, int v, int w, int c) {
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
			for (int e = head[u], v, w, c; e > 0; e = nxt[e]) {
				v = to[e];
				w = weight[e];
				c = cost[e];
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
		heap.add(new int[] { 0, s });
		while (!heap.isEmpty()) {
			int[] cur = heap.poll();
			int d = cur[0];
			int u = cur[1];
			if (!vis[u]) {
				vis[u] = true;
				for (int e = head[u], v, w, c; e > 0; e = nxt[e]) {
					v = to[e];
					w = weight[e];
					c = cost[e] + h[u] - h[v];
					if (w > 0 && !vis[v] && dist[v] > d + c) {
						dist[v] = dist[u] + c;
						flow[v] = Math.min(w, flow[u]);
						pre[v] = e;
						heap.add(new int[] { dist[v], v });
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

	public static void build(int intervals) {
		for (int i = 1; i <= intervals; i++) {
			sortv[++cntv] = l[i];
			sortv[++cntv] = r[i];
		}
		Arrays.sort(sortv, 1, cntv + 1);
		int len = 1;
		for (int i = 2; i <= cntv; i++) {
			if (sortv[len] != sortv[i]) {
				sortv[++len] = sortv[i];
			}
		}
		cntv = len;
	}

	public static int kth(int num) {
		int l = 1, r = cntv, mid;
		while (l <= r) {
			mid = (l + r) / 2;
			if (sortv[mid] == num) {
				return mid;
			} else if (sortv[mid] < num) {
				l = mid + 1;
			} else {
				r = mid - 1;
			}
		}
		return -1;
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		int intervals = in.nextInt();
		int k = in.nextInt();
		for (int i = 1; i <= intervals; i++) {
			l[i] = in.nextInt();
			r[i] = in.nextInt();
		}
		build(intervals);
		n = cntv;
		int s = ++n;
		int t = ++n;
		cntg = 1;
		// 最多送k单位流，每单位流对应一组互不重叠的区间
		addEdge(s, 1, k, 0);
		addEdge(1, s, 0, 0);
		addEdge(cntv, t, k, 0);
		addEdge(t, cntv, 0, 0);
		// 沿数轴向右走，不选区间时费用为0
		for (int i = 1; i < cntv; i++) {
			addEdge(i, i + 1, k, 0);
			addEdge(i + 1, i, 0, 0);
		}
		// 每个输入区间单独连一条容量为1的边，即使端点相同也分别连边
		for (int i = 1; i <= intervals; i++) {
			int u = kth(l[i]);
			int v = kth(r[i]);
			int c = r[i] - l[i];
			addEdge(u, v, 1, -c);
			addEdge(v, u, 0, c);
		}
		ssp(s, t);
		out.println(-mincost);
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
