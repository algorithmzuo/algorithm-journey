package class210;

// 收集，java版
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

public class Code05_Collecting1 {

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

	// 强连通分量点权和的前缀和
	public static long[] prefix = new long[MAXN];

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
		// 清空然后建立费用流图
		Arrays.fill(head, 1, n + 1, 0);
		cntg = 1;
		for (int i = 1; i <= sccCnt; i++) {
			prefix[i] = prefix[i - 1] + sum[i];
		}
		int start = belong[1];
		long c = prefix[sccCnt] - prefix[start];
		addEdge(s, start, k, c);
		addEdge(start, s, 0, -c);
		for (int i = 1; i <= sccCnt; i++) {
			int out = i + sccCnt;
			// 第一个经过分量i的人取走全部物品，损失为0
			addEdge(i, out, 1, 0);
			addEdge(out, i, 0, 0);
			// 后来的人仍可经过，每人少拿sum[i]个物品
			addEdge(i, out, k, sum[i]);
			addEdge(out, i, 0, -sum[i]);
			// 在分量i停下，跳过编号1到i-1的分量
			c = prefix[i - 1];
			addEdge(out, t, k, c);
			addEdge(t, out, 0, -c);
		}
		for (int i = 1; i <= m; i++) {
			int u = belong[a[i]];
			int v = belong[b[i]];
			if (u != v) {
				// 跨分量边满足 u > v，从u走到v，跳过编号v+1到u-1的分量
				c = prefix[u - 1] - prefix[v];
				addEdge(u + sccCnt, v, k, c);
				addEdge(v, u + sccCnt, 0, -c);
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

	// 本题初始有容量的边费用都是非负的，于是可以跳过spfa，不然会超时
	public static boolean ssp(int s, int t) {
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
		// 分量i的入点为i，出点为i+sccCnt
		n = sccCnt << 1;
		int s = ++n;
		int t = ++n;
		condense(s, t);
		ssp(s, t);
		// 每人先计入所有物品，再减去跳过和重复经过分量造成的总损失
		out.println(k * prefix[sccCnt] - mincost);
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
