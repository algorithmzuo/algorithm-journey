package class185;

// 成都七中，java版
// 测试链接 : https://www.luogu.com.cn/problem/P5311

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code06_ChengDu1 {

	public static int MAXN = 100001;
	public static int n, m;
	public static int[] color = new int[MAXN];

	public static int[] headg = new int[MAXN];
	public static int[] nxtg = new int[MAXN << 1];
	public static int[] tog = new int[MAXN << 1];
	public static int cntg;

	public static int[] headq = new int[MAXN];
	public static int[] nxtq = new int[MAXN << 1];
	public static int[] ql = new int[MAXN << 1];
	public static int[] qr = new int[MAXN << 1];
	public static int[] qid = new int[MAXN << 1];
	public static int cntq;

	public static boolean[] vis = new boolean[MAXN];
	public static int[] siz = new int[MAXN];

	public static int[] headc = new int[MAXN];
	public static int[] nxtc = new int[MAXN << 1];
	public static int[] toc = new int[MAXN << 1];
	public static int cntc;

	public static int[] tree = new int[MAXN];

	public static int[] ans = new int[MAXN];

	public static void addEdgeG(int u, int v) {
		nxtg[++cntg] = headg[u];
		tog[cntg] = v;
		headg[u] = cntg;
	}

	public static void addQuery(int u, int l, int r, int id) {
		nxtq[++cntq] = headq[u];
		ql[cntq] = l;
		qr[cntq] = r;
		qid[cntq] = id;
		headq[u] = cntq;
	}

	public static void addEdgeC(int u, int v) {
		nxtc[++cntc] = headc[u];
		toc[cntc] = v;
		headc[u] = cntc;
	}

	public static int lowbit(int i) {
		return i & -i;
	}

	public static void add(int i, int v) {
		if (i <= 0) {
			return;
		}
		while (i <= n) {
			tree[i] += v;
			i += lowbit(i);
		}
	}

	public static int sum(int i) {
		int ret = 0;
		while (i > 0) {
			ret += tree[i];
			i -= lowbit(i);
		}
		return ret;
	}

	public static void getSize(int u, int fa) {
		siz[u] = 1;
		for (int e = headg[u]; e > 0; e = nxtg[e]) {
			int v = tog[e];
			if (v != fa && !vis[v]) {
				getSize(v, u);
				siz[u] += siz[v];
			}
		}
	}

	public static int getCentroid(int u, int fa) {
		getSize(u, fa);
		int half = siz[u] >> 1;
		boolean find = false;
		while (!find) {
			find = true;
			for (int e = headg[u]; e > 0; e = nxtg[e]) {
				int v = tog[e];
				if (v != fa && !vis[v] && siz[v] > half) {
					fa = u;
					u = v;
					find = false;
					break;
				}
			}
		}
		return u;
	}

	public static void centroidTree(int u, int fa) {
		vis[u] = true;
		for (int e = headg[u]; e > 0; e = nxtg[e]) {
			int v = tog[e];
			if (!vis[v]) {
				int nextCent = getCentroid(v, u);
				addEdgeC(u, nextCent);
				centroidTree(getCentroid(v, u), u);
			}
		}
	}

	public static void collect(int u) {

	}

	public static void compute(int u) {
		vis[u] = true;
		collect(u);
		for (int e = headc[u]; e > 0; e = nxtc[e]) {
			compute(toc[e]);
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		for (int i = 1; i <= n; i++) {
			color[i] = in.nextInt();
		}
		for (int i = 1, u, v; i < n; i++) {
			u = in.nextInt();
			v = in.nextInt();
			addEdgeG(u, v);
			addEdgeG(v, u);
		}
		for (int i = 1, l, r, x; i <= m; i++) {
			l = in.nextInt();
			r = in.nextInt();
			x = in.nextInt();
			addQuery(x, l, r, i);
		}

		for (int i = 1; i <= m; i++) {
			out.println(ans[i]);
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
