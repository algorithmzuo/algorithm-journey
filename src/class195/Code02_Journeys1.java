package class195;

// 旅程，java版
// 一共n个点，给定起点p，一共m次操作，格式如下
// 操作 a b c d : a~b范围每个点与c~d范围每个点之间，都增加一条无向边
// 所有操作完成后，计算起点p到每个点经过的最少边数，题目保证整体连通
// 1 <= n <= 5 * 10^5
// 1 <= m <= 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P6348
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayDeque;

public class Code02_Journeys1 {

	public static int MAXN = 500001;
	public static int MAXT = MAXN * 10;
	public static int MAXE = MAXN * 20;
	public static int INF = 1 << 30;
	public static int n, m, p;

	public static int[] head = new int[MAXT];
	public static int[] nxt = new int[MAXE];
	public static int[] to = new int[MAXE];
	public static int[] weight = new int[MAXE];
	public static int cntg;

	public static int[] ls = new int[MAXT];
	public static int[] rs = new int[MAXT];
	public static int rootOut, rootIn;
	public static int cntt;

	public static int[] dist = new int[MAXT];
	public static ArrayDeque<Integer> deq = new ArrayDeque<>();

	public static void addEdge(int u, int v, int w) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		weight[cntg] = w;
		head[u] = cntg;
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
			addEdge(ls[rt], rt, 0);
			addEdge(rs[rt], rt, 0);
		}
		return rt;
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

	public static void rangeToRange(int a, int b, int c, int d) {
		int vnode = ++cntt;
		rangeToX(a, b, vnode, 1, 1, n, rootOut);
		xToRange(vnode, c, d, 0, 1, n, rootIn);
	}

	public static void bfs01() {
		for (int i = 1; i <= cntt; i++) {
			dist[i] = INF;
		}
		dist[p] = 0;
		deq.addFirst(p);
		while (!deq.isEmpty()) {
			int u = deq.pollFirst();
			for (int e = head[u]; e > 0; e = nxt[e]) {
				int v = to[e];
				int w = weight[e];
				if (dist[v] > dist[u] + w) {
					dist[v] = dist[u] + w;
					if (w == 0) {
						deq.addFirst(v);
					} else {
						deq.addLast(v);
					}
				}
			}
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		p = in.nextInt();
		cntt = n;
		rootOut = buildOut(1, n);
		rootIn = buildIn(1, n);
		for (int i = 1, a, b, c, d; i <= m; i++) {
			a = in.nextInt();
			b = in.nextInt();
			c = in.nextInt();
			d = in.nextInt();
			rangeToRange(a, b, c, d);
			rangeToRange(c, d, a, b);
		}
		bfs01();
		for (int i = 1; i <= n; i++) {
			out.println(dist[i]);
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
