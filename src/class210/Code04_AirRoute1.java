package class210;

// 航空路线，java版
// 一共c个城市，城市名依次给出，城市按照从西到东分布
// 一共f条航线，给定两端城市名，两座城市可以相互直飞
// 从最西城市出发，去程每次只能向东飞，直到最东端城市
// 从最东城市返程，每次只能向西飞，直到飞回最西端城市
// 整个行程中，除了最西城市之外，每座城市最多访问一次
// 如果有解，先输出最多能经过多少城市，再按旅行顺序输出城市名
// 如果多解，输出任意一种即可，如果无解，输出 No Solution!
// 1 <= c < 100
// 1 <= f <= c * (c - 1) / 2
// 测试链接 : https://www.luogu.com.cn/problem/P2770
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class Code04_AirRoute1 {

	public static int MAXN = 201;
	public static int MAXM = 5001;
	public static int INF = 1000000001;
	public static int n;

	public static String[] name = new String[MAXN];
	public static HashMap<String, Integer> id = new HashMap<>();

	public static int[] ans = new int[MAXN];
	public static int siz;

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

	public static void getPath(int cities) {
		siz = 0;
		// 从西到东取出两条路线，第一条作为去程，第二条逆序后作为返程
		// len1是去程的城市数，也是最东城市的下标
		int len1 = 0;
		for (int trip = 1; trip <= 2; trip++) {
			int u = 1;
			while (u != cities) {
				ans[++siz] = u;
				for (int e = head[cities + u]; e > 0; e = nxt[e]) {
					if (cost[e] == -1 && weight[e ^ 1] > 0) {
						weight[e ^ 1]--;
						weight[e]++;
						u = to[e];
						break;
					}
				}
			}
			// 最东城市，只添加一次
			if (trip == 1) {
				ans[++siz] = cities;
				len1 = siz;
			}
		}
		// 后一条路线逆序，得到返程
		for (int l = len1 + 1, r = siz; l < r; l++, r--) {
			int tmp = ans[l];
			ans[l] = ans[r];
			ans[r] = tmp;
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		int cities = in.nextInt();
		int flights = in.nextInt();
		n = cities << 1;
		int s = 1;
		int t = n;
		cntg = 1;
		for (int i = 1; i <= cities; i++) {
			name[i] = in.nextString();
			id.put(name[i], i);
			int w = (i == 1 || i == cities) ? 2 : 1;
			addEdge(i, cities + i, w, 0);
			addEdge(cities + i, i, 0, 0);
		}
		for (int i = 1; i <= flights; i++) {
			int a = id.get(in.nextString());
			int b = id.get(in.nextString());
			int u = Math.min(a, b);
			int v = Math.max(a, b);
			addEdge(cities + u, v, 2, -1);
			addEdge(v, cities + u, 0, 1);
		}
		ssp(s, t);
		if (maxflow != 2) {
			out.println("No Solution!");
		} else {
			// 去程飞a次，经过a+1座城市，返程飞b次，经过b+1座城市
			// 两程共用最西和最东两座城市，所以城市总数 = (a+1) + (b+1) - 2 = -mincost
			out.println(-mincost);
			getPath(cities);
			for (int i = 1; i <= siz; i++) {
				out.println(name[ans[i]]);
			}
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

		String nextString() throws IOException {
			int c;
			do {
				c = readByte();
			} while (c <= ' ' && c != -1);
			if (c == -1) {
				return null;
			}
			StringBuilder sb = new StringBuilder();
			while (c > ' ' && c != -1) {
				sb.append((char) c);
				c = readByte();
			}
			return sb.toString();
		}

	}

}
