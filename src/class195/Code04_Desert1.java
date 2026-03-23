package class195;

// 沙漠，java版
// 测试链接 : https://www.luogu.com.cn/problem/P3588
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code04_Desert1 {

	public static int MAXN = 100001;
	public static int MAXT = MAXN * 5;
	public static int MAXE = MAXN * 20;
	public static int LIMIT = 1000000000;
	public static int n, s, m;

	public static int[] head = new int[MAXT];
	public static int[] nxt = new int[MAXE];
	public static int[] to = new int[MAXE];
	public static int[] weight = new int[MAXE];
	public static int cntg;

	public static int[] idArr = new int[MAXN];
	public static int[] ls = new int[MAXT];
	public static int[] rs = new int[MAXT];
	public static int root;
	public static int cntt;

	public static int[] val = new int[MAXT];
	public static int[] indegree = new int[MAXT];
	public static int[] dist = new int[MAXT];
	public static int[] que = new int[MAXT];

	public static void addEdge(int u, int v, int w) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		weight[cntg] = w;
		head[u] = cntg;
		indegree[v]++;
	}

	public static int build(int l, int r) {
		int rt = ++cntt;
		if (l == r) {
			idArr[l] = rt;
		} else {
			int mid = (l + r) >> 1;
			ls[rt] = build(l, mid);
			rs[rt] = build(mid + 1, r);
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

	public static boolean topo() {
		int qi = 1, qsiz = 0;
		for (int i = 1; i <= cntt; i++) {
			if (indegree[i] == 0) {
				que[++qsiz] = i;
			}
			dist[i] = val[i] > 0 ? val[i] : LIMIT;
		}
		while (qi <= qsiz) {
			int u = que[qi++];
			for (int e = head[u]; e > 0; e = nxt[e]) {
				int v = to[e];
				int w = weight[e];
				if (dist[v] > dist[u] + w) {
					dist[v] = dist[u] + w;
					if (val[v] != 0 && dist[v] < val[v]) {
						return false;
					}
				}
				if (--indegree[v] == 0) {
					que[++qsiz] = v;
				}
			}
		}
		if (qsiz != cntt) {
			return false;
		}
		for (int i = 1; i <= cntt; i++) {
			if (dist[i] < 1) {
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		s = in.nextInt();
		m = in.nextInt();
		root = build(1, n);
		for (int i = 1; i <= s; i++) {
			int x = in.nextInt();
			int v = in.nextInt();
			val[idArr[x]] = v;
		}
		for (int i = 1; i <= m; i++) {
			int l = in.nextInt();
			int r = in.nextInt();
			int k = in.nextInt();
			int vnode = ++cntt;
			for (int j = 1; j <= k; j++) {
				int x = in.nextInt();
				addEdge(idArr[x], vnode, 0);
				if (l < x) {
					xToRange(vnode, l, x - 1, -1, 1, n, root);
				}
				l = x + 1;
			}
			if (l <= r) {
				xToRange(vnode, l, r, -1, 1, n, root);
			}
		}
		boolean check = topo();
		if (check) {
			out.println("TAK");
			for (int i = 1; i <= n; i++) {
				out.print(dist[idArr[i]] + " ");
			}
			out.println();
		} else {
			out.println("NIE");
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
