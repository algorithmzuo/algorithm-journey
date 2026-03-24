package class195;

// 道路，java版
// 测试链接 : https://acm.hdu.edu.cn/showproblem.php?pid=5669
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.PriorityQueue;

public class Code03_Road1 {

	public static int MAXN = 50001;
	public static int MAXT = MAXN * 10;
	public static int MAXE = MAXN * 20;
	public static int MAXK = 11;
	public static int INF = 1 << 30;
	public static int t, n, m, k;

	public static int[] head = new int[MAXT];
	public static int[] nxt = new int[MAXE];
	public static int[] to = new int[MAXE];
	public static int[] weight = new int[MAXE];
	public static int cntg;

	public static int[] ls = new int[MAXT];
	public static int[] rs = new int[MAXT];
	public static int rootIn, rootOut;
	public static int cntt;

	public static int[][] dist = new int[MAXT][MAXK];
	public static boolean[][] vis = new boolean[MAXT][MAXK];
	// 编号、次数、花费
	public static PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> a[2] - b[2]);

	public static void addEdge(int u, int v, int w) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		weight[cntg] = w;
		head[u] = cntg;
	}

	public static int buildIn(int l, int r) {
		int rt;
		if (l == r) {
			rt = l;
		} else {
			rt = ++cntt;
			int mid = (l + r) >> 1;
			ls[rt] = buildIn(l, mid);
			rs[rt] = buildIn(mid + 1, r);
			addEdge(ls[rt], rt, 0);
			addEdge(rs[rt], rt, 0);
		}
		return rt;
	}

	public static int buildOut(int l, int r) {
		int rt;
		if (l == r) {
			rt = l;
		} else {
			rt = ++cntt;
			int mid = (l + r) >> 1;
			ls[rt] = buildOut(l, mid);
			rs[rt] = buildOut(mid + 1, r);
			addEdge(rt, ls[rt], 0);
			addEdge(rt, rs[rt], 0);
		}
		return rt;
	}

	public static void rangeToX(int jobl, int jobr, int jobx, int jobw, int l, int r, int i) {
		if (jobl <= l && r <= jobr) {
			addEdge(i, jobx, jobw);
		} else {
			int mid = (l + r) >> 1;
			if (jobl <= mid) {
				rangeToX(jobl, jobr, jobx, jobw, l, mid, ls[i]);
			}
			if (jobr > mid) {
				rangeToX(jobl, jobr, jobx, jobw, mid + 1, r, rs[i]);
			}
		}
	}

	public static void xToRange(int jobx, int jobl, int jobr, int jobw, int l, int r, int i) {
		if (jobl <= l && r <= jobr) {
			addEdge(jobx, i, jobw);
		} else {
			int mid = (l + r) >> 1;
			if (jobl <= mid) {
				xToRange(jobx, jobl, jobr, jobw, l, mid, ls[i]);
			}
			if (jobr > mid) {
				xToRange(jobx, jobl, jobr, jobw, mid + 1, r, rs[i]);
			}
		}
	}

	public static void rangeToRange(int a, int b, int c, int d, int w) {
		int vnode = ++cntt;
		rangeToX(a, b, vnode, w, 1, n, rootIn);
		xToRange(vnode, c, d, 0, 1, n, rootOut);
	}

	public static int dijkstra(int start, int target) {
		for (int i = 1; i <= cntt; i++) {
			for (int j = 0; j <= k; j++) {
				dist[i][j] = INF;
				vis[i][j] = false;
			}
		}
		dist[start][0] = 0;
		heap.add(new int[] { start, 0, 0 });
		while (!heap.isEmpty()) {
			int[] cur = heap.poll();
			int node = cur[0];
			int time = cur[1];
			int cost = cur[2];
			if (!vis[node][time]) {
				vis[node][time] = true;
				if (node == target) {
					return cost;
				}
				for (int e = head[node]; e > 0; e = nxt[e]) {
					int v = to[e];
					int w = weight[e];
					if (!vis[v][time] && dist[v][time] > cost + w) {
						dist[v][time] = cost + w;
						heap.add(new int[] { v, time, dist[v][time] });
					}
					if (time < k && !vis[v][time + 1] && dist[v][time + 1] > cost) {
						dist[v][time + 1] = cost;
						heap.add(new int[] { v, time + 1, dist[v][time + 1] });
					}
				}
			}
		}
		return -1;
	}

	public static void clear() {
		for (int i = 1; i <= cntt; i++) {
			head[i] = 0;
		}
		cntg = cntt = 0;
		heap.clear();
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		t = in.nextInt();
		for (int test = 1; test <= t; test++) {
			n = in.nextInt();
			m = in.nextInt();
			k = in.nextInt();
			cntt = n;
			rootIn = buildIn(1, n);
			rootOut = buildOut(1, n);
			for (int i = 1, a, b, c, d, w; i <= m; i++) {
				a = in.nextInt();
				b = in.nextInt();
				c = in.nextInt();
				d = in.nextInt();
				w = in.nextInt();
				rangeToRange(a, b, c, d, w);
				rangeToRange(c, d, a, b, w);
			}
			int ans = dijkstra(1, n);
			if (ans == -1) {
				out.println("CreationAugust is a sb!");
			} else {
				out.println(ans);
			}
			clear();
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
