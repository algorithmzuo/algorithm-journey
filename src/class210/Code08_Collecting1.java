package class210;

// 收集，java版
// 一共有n个点，m条有向边，图中没有自环，也没有重边
// 第i号边从a[i]指向b[i]，第i号点有arr[i]件物品
// 一共k个人，依次从1号点出发，每步沿着有向边前进，可以随时停下
// 每个点可以反复访问，第一个访问的人拿走全部物品，以后不会再有收益
// 求这k个人最多能收集多少件物品
// 1 <= n、m <= 2 * 10^5
// 1 <= k <= 10
// 1 <= arr[i] <= 10^9
// 测试链接 : https://www.luogu.com.cn/problem/AT_abc214_h
// 测试链接 : https://atcoder.jp/contests/abc214/tasks/abc214_h
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class Code08_Collecting1 {

	public static int MAXN = 500001;
	public static int MAXM = 1000001;
	public static long INF = 1L << 60;
	public static int n, m, k;

	public static int[] arr = new int[MAXN];
	public static int[] a = new int[MAXN];
	public static int[] b = new int[MAXN];

	// tarjan算法求解强连通分量和缩点
	public static int[] dfn = new int[MAXN];
	public static int[] low = new int[MAXN];
	public static int cntd;
	public static int[] sta = new int[MAXN];
	public static int top;
	public static int[] belong = new int[MAXN];
	// 强连通分量的点权和
	public static long[] sum = new long[MAXN];
	public static int sccCnt;

	// 原图和费用流图，使用同一套链式前向星
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

	public static void addEdge(int u, int v) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		head[u] = cntg;
	}

	public static void addEdge(int u, int v, long w, long c) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		weight[cntg] = w;
		cost[cntg] = c;
		head[u] = cntg;
	}

	public static void tarjan(int u) {
		dfn[u] = low[u] = ++cntd;
		sta[++top] = u;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (dfn[v] == 0) {
				tarjan(v);
				low[u] = Math.min(low[u], low[v]);
			} else {
				if (belong[v] == 0) {
					low[u] = Math.min(low[u], dfn[v]);
				}
			}
		}
		if (dfn[u] == low[u]) {
			sccCnt++;
			int pop;
			do {
				pop = sta[top--];
				belong[pop] = sccCnt;
				sum[sccCnt] += arr[pop];
			} while (pop != u);
		}
	}

	public static void condense(int s, int t) {
		Arrays.fill(head, 1, n + 1, 0);
		cntg = 1;
		addEdge(s, belong[1], k, 0);
		addEdge(belong[1], s, 0, 0);
		for (int i = 1; i <= sccCnt; i++) {
			int out = i + sccCnt;
			// 第一个经过的人，拿走所有物品，收益为sum[i]
			addEdge(i, out, 1, -sum[i]);
			addEdge(out, i, 0, sum[i]);
			// 后续的人也可以经过，但是收益为0
			addEdge(i, out, k - 1, 0);
			addEdge(out, i, 0, 0);
			// 每个人都可以在当前分量结束行程
			addEdge(out, t, k, 0);
			addEdge(t, out, 0, 0);
		}
		for (int i = 1; i <= m; i++) {
			int u = belong[a[i]];
			int v = belong[b[i]];
			if (u != v) {
				// 从分量u的出点走到分量v的入点，费用为0
				addEdge(u + sccCnt, v, k, 0);
				addEdge(v, u + sccCnt, 0, 0);
			}
		}
	}

	// 缩点之后转成DAG，利用拓扑序求最短距离作为初始势能，替代spfa
	public static void init(int s) {
		Arrays.fill(h, 1, n + 1, INF);
		h[s] = h[belong[1]] = 0;
		// 分量编号从大到小，对应拓扑序，讲解190讲了
		// 分量的入点是i，出点是out
		for (int i = sccCnt, out = sccCnt << 1; i >= 1; i--, out--) {
			if (h[i] != INF) {
				h[out] = h[i] - sum[i];
				for (int e = head[out]; e > 0; e = nxt[e]) {
					int v = to[e];
					long c = cost[e];
					if (weight[e] > 0) {
						h[v] = Math.min(h[v], h[out] + c);
					}
				}
			}
		}
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

	// 初始时只看剩余容量大于0的边，整张图是DAG，可以利用拓扑序求出初始势能
	// 不要使用spfa，因为本题的数据量大，spfa会超时
	public static boolean ssp(int s, int t) {
		maxflow = mincost = 0;
		init(s);
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
		n = in.nextInt();
		m = in.nextInt();
		k = in.nextInt();
		for (int i = 1; i <= m; i++) {
			a[i] = in.nextInt();
			b[i] = in.nextInt();
			addEdge(a[i], b[i]);
		}
		for (int i = 1; i <= n; i++) {
			arr[i] = in.nextInt();
		}
		for (int i = 1; i <= n; i++) {
			if (dfn[i] == 0) {
				tarjan(i);
			}
		}
		n = sccCnt << 1;
		int s = ++n;
		int t = ++n;
		condense(s, t);
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
