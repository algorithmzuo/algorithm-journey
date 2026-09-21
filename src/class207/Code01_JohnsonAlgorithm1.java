package class207;

// Johnson全源最短路，java版
// 测试链接 : https://www.luogu.com.cn/problem/P5905
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
// java的实现，dijkstra需要反向索引堆优化，才能通过，讲解064讲了
// C++的实现，使用普通版本的dijkstra算法，就可以直接通过

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

public class Code01_JohnsonAlgorithm1 {

	public static int MAXN = 10001;
	public static int MAXQ = 5000001;
	public static int INF = 1000000000;
	public static int n, m;

	// 建图
	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXN];
	public static int[] to = new int[MAXN];
	public static int[] weight = new int[MAXN];
	public static int cntg;

	// spfa
	public static int[] h = new int[MAXN];
	public static int[] update = new int[MAXN];
	public static boolean[] enter = new boolean[MAXN];
	public static int[] que = new int[MAXQ];

	// dijkstra，反向索引堆优化
	public static int[] dist = new int[MAXN];
	public static int[] heap = new int[MAXN];
	// where[v] = -1，表示v这个节点，从来没有进入过堆
	// where[v] = -2，表示v这个节点，已经弹出过了
	// where[v] >= 0，表示v这个节点在堆上的位置
	public static int[] where = new int[MAXN];
	public static int heapSize;

	public static void addEdge(int u, int v, int w) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		weight[cntg] = w;
		head[u] = cntg;
	}

	public static void swap(int i, int j) {
		int tmp = heap[i];
		heap[i] = heap[j];
		heap[j] = tmp;
		where[heap[i]] = i;
		where[heap[j]] = j;
	}

	public static void heapInsert(int i) {
		while (dist[heap[i]] < dist[heap[(i - 1) / 2]]) {
			swap(i, (i - 1) / 2);
			i = (i - 1) / 2;
		}
	}

	public static void heapify(int i) {
		int l = i * 2 + 1;
		while (l < heapSize) {
			int best = l + 1 < heapSize && dist[heap[l + 1]] < dist[heap[l]] ? l + 1 : l;
			best = dist[heap[best]] < dist[heap[i]] ? best : i;
			if (best == i) {
				break;
			}
			swap(best, i);
			i = best;
			l = i * 2 + 1;
		}
	}

	public static boolean isEmpty() {
		return heapSize == 0;
	}

	public static int pop() {
		int ans = heap[0];
		swap(0, --heapSize);
		heapify(0);
		where[ans] = -2;
		return ans;
	}

	public static void addOrUpdateOrIgnore(int v, int d) {
		if (where[v] == -2 || dist[v] <= d) {
			return;
		}
		if (where[v] == -1) {
			heap[heapSize] = v;
			where[v] = heapSize++;
		}
		dist[v] = d;
		heapInsert(where[v]);
	}

	// 返回是否发现了负环
	public static boolean spfa(int s) {
		Arrays.fill(h, 1, n + 1, INF);
		h[s] = 0;
		update[s] = 1;
		enter[s] = true;
		int ql = 1, qr = 0;
		que[++qr] = s;
		while (ql <= qr) {
			int u = que[ql++];
			enter[u] = false;
			for (int ei = head[u], v, w; ei > 0; ei = nxt[ei]) {
				v = to[ei];
				w = weight[ei];
				if (h[v] > h[u] + w) {
					h[v] = h[u] + w;
					if (!enter[v]) {
						if (++update[v] > n) {
							return true;
						}
						que[++qr] = v;
						enter[v] = true;
					}
				}
			}
		}
		return false;
	}

	// dijkstra算法，反向索引堆优化，讲解064
	public static void dijkstra(int s) {
		Arrays.fill(dist, 1, n + 1, INF);
		Arrays.fill(where, 1, n + 1, -1);
		heapSize = 0;
		addOrUpdateOrIgnore(s, 0);
		while (!isEmpty()) {
			int u = pop();
			int d = dist[u];
			for (int e = head[u]; e > 0; e = nxt[e]) {
				addOrUpdateOrIgnore(to[e], d + weight[e]);
			}
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		for (int i = 1, u, v, w; i <= m; i++) {
			u = in.nextInt();
			v = in.nextInt();
			w = in.nextInt();
			addEdge(u, v, w);
		}
		int virtualNode = n + 1;
		for (int i = 1; i <= n; i++) {
			addEdge(virtualNode, i, 0);
		}
		if (spfa(virtualNode)) {
			out.println("-1");
			out.flush();
		} else {
			for (int u = 1; u <= n; u++) {
				for (int e = head[u]; e > 0; e = nxt[e]) {
					int v = to[e];
					weight[e] += h[u] - h[v];
				}
			}
			for (int u = 1; u <= n; u++) {
				dijkstra(u);
				long ans = 0;
				for (int v = 1; v <= n; v++) {
					if (dist[v] == INF) {
						ans += 1L * v * INF;
					} else {
						ans += 1L * v * (dist[v] - h[u] + h[v]);
					}
				}
				out.println(ans);
				out.flush();
			}
		}
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
