package class210;

// 通信，java版
// 一共有k个哨站，从左到右编号为1到k，第i号哨站的频段为arr[i]
// 每个哨站i需要选择以下二者之一
// 方式1，直接连接到控制中心，花费为w
// 方式2，连接左侧的某个哨站j，j < i，花费为|arr[i] - arr[j]|
// 控制中心可以被多个哨站直接连接，每个哨站最多接受一个右侧哨站的直接连接
// 求最小的总花费
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
		// 建立 左侧频率 <= 右侧频率 的连接
		long curVal = arr[idx[l]];
		int curNode = ++n;
		long newVal;
		int newNode;
		long c;
		for (int p1 = l, p2 = mid + 1; p2 <= r; p2++) {
			while (p1 <= mid && arr[idx[p1]] <= arr[idx[p2]]) {
				newVal = arr[idx[p1]];
				if (newVal != curVal) {
					newNode = ++n;
					c = newVal - curVal;
					addEdge(newNode, curNode, k, c);
					addEdge(curNode, newNode, 0, -c);
					curVal = newVal;
					curNode = newNode;
				}
				addEdge(curNode, idx[p1] + k, 1, 0);
				addEdge(idx[p1] + k, curNode, 0, 0);
				p1++;
			}
			// 左侧频率 <= 当前右侧哨站的频率，真的存在这个部分，才去连边
			if (p1 > l) {
				c = arr[idx[p2]] - curVal;
				addEdge(idx[p2], curNode, 1, c);
				addEdge(curNode, idx[p2], 0, -c);
			}
		}
		// 建立 左侧频率 > 右侧频率 的连接
		curVal = arr[idx[mid]];
		curNode = ++n;
		for (int p1 = mid, p2 = r; p2 > mid; p2--) {
			while (p1 >= l && arr[idx[p1]] > arr[idx[p2]]) {
				newVal = arr[idx[p1]];
				if (newVal != curVal) {
					newNode = ++n;
					c = curVal - newVal;
					addEdge(newNode, curNode, k, c);
					addEdge(curNode, newNode, 0, -c);
					curVal = newVal;
					curNode = newNode;
				}
				addEdge(curNode, idx[p1] + k, 1, 0);
				addEdge(idx[p1] + k, curNode, 0, 0);
				p1--;
			}
			// 左侧频率 > 当前右侧哨站的频率，真的存在这个部分，才去连边
			if (p1 < mid) {
				c = curVal - arr[idx[p2]];
				addEdge(idx[p2], curNode, 1, c);
				addEdge(curNode, idx[p2], 0, -c);
			}
		}
		// 归并
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
		for (int i = l, j = 1; i <= r; i++, j++) {
			idx[i] = tmp[j];
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
		// 节点i，代表哨站i做的选择，我要连接谁
		// 节点i + k，代表哨站i提供的接收名额，谁可以连接我
		for (int i = 1; i <= k; i++) {
			arr[i] = in.nextInt();
			idx[i] = i;
			// 最大流时，s到i恰好流过1单位，表示哨站i恰好选择一个连接目标
			addEdge(s, i, 1, 0);
			addEdge(i, s, 0, 0);
			// 哨站i可以做出直接连控制中心的选择，费用为w
			addEdge(i, t, 1, w);
			addEdge(t, i, 0, -w);
			// i + k到t的容量为1，表示最多有一个右侧哨站能直接连接到哨站i
			addEdge(i + k, t, 1, 0);
			addEdge(t, i + k, 0, 0);
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
