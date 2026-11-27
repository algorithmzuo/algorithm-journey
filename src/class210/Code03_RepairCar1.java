package class210;

// 修车，java版
// 一共a个技师，同时到达了b辆车，mat[i][j]表示第j号技师修理第i号辆车需要的时间
// 每辆车只能交给一个技师修理，每个技师修完一辆车之后，才能去修下一辆
// 车主等待时间 = 从到达时刻开始，到该车被修好的时刻为止，总共花费的时间
// 你来安排修理，希望车主的平均等待时间最小，打印这个最小值，答案保留小数点后两位
// 2 <= a <= 9
// 1 <= b <= 60
// 1 <= mat[i][j] <= 1000
// 测试链接 : https://www.luogu.com.cn/problem/P2053
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class Code03_RepairCar1 {

	public static int MAXN = 1001;
	public static int MAXM = 50001;
	public static int INF = 1000000001;
	public static int n;

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
		int a = in.nextInt();
		int b = in.nextInt();
		n = b + a * b;
		int s = ++n;
		int t = ++n;
		cntg = 1;
		for (int i = 1; i <= b; i++) {
			addEdge(s, i, 1, 0);
			addEdge(i, s, 0, 0);
			for (int j = 1, time; j <= a; j++) {
				time = in.nextInt();
				// u是j号技师倒数第k个修车位置的编号，从i向u连边，表示i号车安排在这里
				// 因为是倒数第k个，所以当前车主和之后的k-1个车主，都要等这辆车修好
				// 本车的修理时间time，费用c为time * k
				for (int k = 1; k <= b; k++) {
					int u = j * b + k;
					int c = time * k;
					addEdge(i, u, 1, c);
					addEdge(u, i, 0, -c);
				}
			}
		}
		// 每个修车位置向汇点连一条容量为1，费用为0的边，保证每个位置最多安排一辆车
		for (int u = b + 1; u < s; u++) {
			addEdge(u, t, 1, 0);
			addEdge(t, u, 0, 0);
		}
		ssp(s, t);
		double ans = (double) mincost / b;
		out.printf("%.2f\n", ans);
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
