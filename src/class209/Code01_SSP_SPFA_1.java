package class209;

// 费用流启发算法，SSP + SPFA，java版
// 题目保证不会出现有负圈的费用流
// 有负圈的费用流问题，后续的上下界流再讲述
// 测试链接 : https://www.luogu.com.cn/problem/P3381
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Queue;

public class Code01_SSP_SPFA_1 {

	public static int MAXN = 5001;
	public static int MAXM = 50001;
	public static int INF = 1000000001;
	public static int n, m;

	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXM << 1];
	public static int[] to = new int[MAXM << 1];
	public static int[] weight = new int[MAXM << 1];
	public static int[] cost = new int[MAXM << 1];
	public static int cntg;

	// ssp算法，单路增广，所需的瓶颈流量和前驱边
	public static int[] flow = new int[MAXN];
	public static int[] pre = new int[MAXN];

	// ssp算法，计算费用最小的增广路，spfa算法
	public static Queue<Integer> que = new LinkedList<>();
	public static int[] dist = new int[MAXN];
	public static int[] update = new int[MAXN];
	public static boolean[] enter = new boolean[MAXN];

	public static int maxflow, mincost;

	public static void addEdge(int u, int v, int w, int c) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		weight[cntg] = w;
		cost[cntg] = c;
		head[u] = cntg;
	}

	// 返回是否发现从s可达的负费用环
	public static boolean spfa(int s) {
		for (int i = 1; i <= n; i++) {
			dist[i] = INF;
			update[i] = 0;
			enter[i] = false;
			flow[i] = -1;
		}
		dist[s] = 0;
		update[s] = 1;
		enter[s] = true;
		flow[s] = INF;
		que.clear();
		que.offer(s);
		while (!que.isEmpty()) {
			int u = que.poll();
			enter[u] = false;
			for (int e = head[u], v, w, c; e > 0; e = nxt[e]) {
				v = to[e];
				w = weight[e];
				c = cost[e];
				if (w > 0 && dist[v] > dist[u] + c) {
					dist[v] = dist[u] + c;
					flow[v] = Math.min(w, flow[u]);
					pre[v] = e;
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

	// 返回是否发现从s可达的负费用环
	public static boolean ssp(int s, int t) {
		boolean negCycle = spfa(s);
		if (negCycle) {
			return true;
		}
		maxflow = mincost = 0;
		while (flow[t] != -1) {
			maxflow += flow[t];
			for (int u = t; u != s; u = to[pre[u] ^ 1]) {
				weight[pre[u]] -= flow[t];
				weight[pre[u] ^ 1] += flow[t];
				mincost += flow[t] * cost[pre[u]];
			}
			// 初始残量网络无负费用环，每次沿费用最短路增广后仍无负费用环
			spfa(s);
		}
		return false;
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		int s = in.nextInt();
		int t = in.nextInt();
		cntg = 1;
		for (int i = 1, u, v, w, c; i <= m; i++) {
			u = in.nextInt();
			v = in.nextInt();
			w = in.nextInt();
			c = in.nextInt();
			addEdge(u, v, w, c);
			addEdge(v, u, 0, -c);
		}
		if (ssp(s, t)) {
			out.println(-1);
		} else {
			out.println(maxflow + " " + mincost);
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
