package class210;

// 近似排列，java版
// 给定数组长度n和q条限制，数组中每个数都是1到n之间的整数，可以重复
// 数组下标从1开始，每条限制的格式 op l r v，含义如下
// 如果op为1，要求下标区间[l, r]的每个数 >= v
// 如果op为2，要求下标区间[l, r]的每个数 <= v
// 设c[i]为数字i在数组中出现的次数，数组的代价为c[1]到c[n]的平方和
// 求满足所有限制的数组的最小代价，如果无解，输出-1
// 1 <= n <= 50
// 0 <= q <= 100
// 测试链接 : https://www.luogu.com.cn/problem/CF863F
// 测试链接 : https://codeforces.com/problemset/problem/863/F
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class Code02_AlmostPermutation1 {

	public static int MAXN = 201;
	public static int MAXM = 10001;
	public static int INF = 1000000001;
	public static int n;

	public static int[] low = new int[MAXN];
	public static int[] high = new int[MAXN];

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

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		int len = in.nextInt();
		int q = in.nextInt();
		n = len << 1;
		int s = ++n;
		int t = ++n;
		cntg = 1;
		for (int i = 1; i <= len; i++) {
			low[i] = 1;
			high[i] = len;
		}
		for (int i = 1, op, l, r, v; i <= q; i++) {
			op = in.nextInt();
			l = in.nextInt();
			r = in.nextInt();
			v = in.nextInt();
			for (int j = l; j <= r; j++) {
				if (op == 1) {
					low[j] = Math.max(low[j], v);
				} else {
					high[j] = Math.min(high[j], v);
				}
			}
		}
		for (int i = 1; i <= len; i++) {
			addEdge(s, i, 1, 0);
			addEdge(i, s, 0, 0);
			for (int v = low[i]; v <= high[i]; v++) {
				addEdge(i, len + v, 1, 0);
				addEdge(len + v, i, 0, 0);
			}
		}
		// 数字v第k次出现时，代价从(k-1)*(k-1)增加到k*k，新增代价为2*k - 1
		// 因此数字v向汇点连接len条平行边，每条容量为1，费用依次为1, 3, 5 ..
		// 如果数字v出现x次，最小费用流的求解过程会选最便宜的x条边，费用恰好为x*x
		for (int v = 1; v <= len; v++) {
			for (int k = 1, c = 1; k <= len; k++, c += 2) {
				addEdge(len + v, t, 1, c);
				addEdge(t, len + v, 0, -c);
			}
		}
		ssp(s, t);
		if (maxflow == len) {
			out.println(mincost);
		} else {
			out.println(-1);
		}
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
