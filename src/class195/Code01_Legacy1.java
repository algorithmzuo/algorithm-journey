package class195;

// 遗产，java版
// 测试链接 : https://www.luogu.com.cn/problem/CF786B
// 测试链接 : https://codeforces.com/problemset/problem/786/B
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.PriorityQueue;

public class Code01_Legacy1 {

	public static int MAXN = 100001;
	public static int MAXS = MAXN * 10;
	public static int MAXT = MAXN * 30;
	public static long INF = 1L << 60;
	public static int n, q, s;

	public static int[] head = new int[MAXS];
	public static int[] nxt = new int[MAXT];
	public static int[] to = new int[MAXT];
	public static int[] weight = new int[MAXT];
	public static int cntg;

	public static int[] inArr = new int[MAXN];
	public static int[] outArr = new int[MAXN];

	public static int[] ls = new int[MAXT];
	public static int[] rs = new int[MAXT];
	public static int rootIn, rootOut;
	public static int cntt;

	public static long[] dist = new long[MAXS];
	public static boolean[] vis = new boolean[MAXS];
	public static PriorityQueue<long[]> heap = new PriorityQueue<>((x, y) -> Long.compare(x[1], y[1]));

	public static void addEdge(int u, int v, int w) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		weight[cntg] = w;
		head[u] = cntg;
	}

	public static int buildIn(int l, int r) {
		int rt = ++cntt;
		if (l == r) {
			inArr[l] = rt;
		} else {
			int mid = (l + r) >> 1;
			ls[rt] = buildIn(l, mid);
			rs[rt] = buildIn(mid + 1, r);
			addEdge(ls[rt], rt, 0);
			addEdge(rs[rt], rt, 0);
		}
		return rt;
	}

	public static int buildOut(int l, int r) {
		int rt = ++cntt;
		if (l == r) {
			outArr[l] = rt;
		} else {
			int mid = (l + r) >> 1;
			ls[rt] = buildOut(l, mid);
			rs[rt] = buildOut(mid + 1, r);
			addEdge(rt, ls[rt], 0);
			addEdge(rt, rs[rt], 0);
		}
		return rt;
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

	public static void dijkstra() {
		s = inArr[s];
		for (int i = 1; i <= cntt; i++) {
			dist[i] = INF;
		}
		dist[s] = 0;
		heap.add(new long[] { s, 0 });
		while (!heap.isEmpty()) {
			long[] cur = heap.poll();
			int u = (int) cur[0];
			long d = cur[1];
			if (!vis[u]) {
				vis[u] = true;
				for (int e = head[u]; e > 0; e = nxt[e]) {
					int v = to[e];
					int w = weight[e];
					if (!vis[v] && dist[v] > d + w) {
						dist[v] = d + w;
						heap.add(new long[] { v, dist[v] });
					}
				}
			}
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		q = in.nextInt();
		s = in.nextInt();
		rootIn = buildIn(1, n);
		rootOut = buildOut(1, n);
		for (int i = 1; i <= n; i++) {
			addEdge(inArr[i], outArr[i], 0);
			addEdge(outArr[i], inArr[i], 0);
		}
		for (int i = 1, op, x, y, l, r, w; i <= q; i++) {
			op = in.nextInt();
			if (op == 1) {
				x = in.nextInt();
				y = in.nextInt();
				w = in.nextInt();
				addEdge(inArr[x], outArr[y], w);
			} else if (op == 2) {
				x = in.nextInt();
				l = in.nextInt();
				r = in.nextInt();
				w = in.nextInt();
				xToRange(inArr[x], l, r, w, 1, n, rootOut);
			} else {
				x = in.nextInt();
				l = in.nextInt();
				r = in.nextInt();
				w = in.nextInt();
				rangeToX(l, r, outArr[x], w, 1, n, rootIn);
			}
		}
		dijkstra();
		for (int i = 1; i <= n; i++) {
			out.print(dist[outArr[i]] == INF ? -1 : dist[outArr[i]]);
			out.print(" ");
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
