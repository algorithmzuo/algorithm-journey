package class210;

// 交换棋子，java版
// 测试链接 : https://www.luogu.com.cn/problem/P3159
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class Code03_SwapPieces1 {

	public static int MAXK = 21;
	public static int MAXN = 2001;
	public static int MAXM = 5001;
	public static int INF = 1000000001;
	public static int n;

	public static char[][] start = new char[MAXK][MAXK];
	public static char[][] target = new char[MAXK][MAXK];

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
		int rows = in.nextInt();
		int cols = in.nextInt();
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				start[i][j] = in.nextChar();
			}
		}
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				target[i][j] = in.nextChar();
			}
		}
		int cells = rows * cols;
		n = cells * 3;
		int s = ++n;
		int t = ++n;
		cntg = 1;
		// give是黑变白的格数，take是白变黑的格数
		int give = 0, take = 0;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				// 格子编号 u 同时作为入点，mid 是中心点，outNode 是出点
				int u = i * cols + j + 1;
				int mid = cells + u;
				int outNode = 2 * cells + u;
				// 颜色相同，黑棋移入和移出的次数相等，各自最多 times / 2 次
				int times = in.nextChar() - '0';
				int inCap = times / 2;
				int outCap = times / 2;
				if (start[i][j] != target[i][j]) {
					if (start[i][j] == '0') {
						// 黑变白，移出比移入多一次
						give++;
						outCap = (times + 1) / 2;
						addEdge(s, mid, 1, 0);
						addEdge(mid, s, 0, 0);
					} else {
						// 白变黑，移入比移出多一次
						take++;
						inCap = (times + 1) / 2;
						addEdge(mid, t, 1, 0);
						addEdge(t, mid, 0, 0);
					}
				}
				addEdge(u, mid, inCap, 0);
				addEdge(mid, u, 0, 0);
				addEdge(mid, outNode, outCap, 0);
				addEdge(outNode, mid, 0, 0);
				// 黑棋从本格出点走到八邻格的入点，每走一步对应一次交换
				for (int di = -1; di <= 1; di++) {
					for (int dj = -1; dj <= 1; dj++) {
						int x = i + di;
						int y = j + dj;
						if ((di == 0 && dj == 0) || x < 0 || x >= rows || y < 0 || y >= cols) {
							continue;
						}
						int v = x * cols + y + 1;
						addEdge(outNode, v, cells, 1);
						addEdge(v, outNode, 0, -1);
					}
				}
			}
		}
		// 初末黑棋总数相等，且所有需要移出的黑棋都能送达，才有解
		if (give != take) {
			out.println(-1);
		} else {
			ssp(s, t);
			out.println(maxflow == give ? mincost : -1);
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

		char nextChar() throws IOException {
			int c;
			do {
				c = readByte();
			} while (c <= ' ' && c != -1);
			return (char) c;
		}

	}

}
