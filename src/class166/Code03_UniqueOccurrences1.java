package class166;

// 独特事件，java版
// 测试链接 : https://www.luogu.com.cn/problem/CF1681F
// 测试链接 : https://codeforces.com/problemset/problem/1681/F
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Code03_UniqueOccurrences1 {

	public static int MAXN = 500001;
	public static int MAXT = 10000001;
	public static int n, m;

	public static int[] x = new int[MAXN];
	public static int[] y = new int[MAXN];
	public static int[] c = new int[MAXN];

	public static int[] father = new int[MAXN];
	public static int[] siz = new int[MAXN];
	public static int[][] rollback = new int[MAXN][2];
	public static int opsize;

	public static int[] headc = new int[MAXN];
	public static int[] nextc = new int[MAXN];
	public static int[] toc = new int[MAXN];
	public static int cntc = 0;

	public static int[] heads = new int[MAXT];
	public static int[] nexts = new int[MAXT];
	public static int[] tos = new int[MAXT];
	public static int cnts = 0;

	public static long ans = 0;

	public static void addEdgeC(int u, int v) {
		nextc[++cntc] = headc[u];
		toc[cntc] = v;
		headc[u] = cntc;
	}

	public static void addEdgeS(int u, int v) {
		nexts[++cnts] = heads[u];
		tos[cnts] = v;
		heads[u] = cnts;
	}

	public static int find(int i) {
		while (i != father[i]) {
			i = father[i];
		}
		return i;
	}

	public static void union(int x, int y) {
		int fx = find(x);
		int fy = find(y);
		if (siz[fx] < siz[fy]) {
			int tmp = fx;
			fx = fy;
			fy = tmp;
		}
		father[fy] = fx;
		siz[fx] += siz[fy];
		rollback[++opsize][0] = fx;
		rollback[opsize][1] = fy;
	}

	public static void undo() {
		int fx = rollback[opsize][0];
		int fy = rollback[opsize--][1];
		father[fy] = fy;
		siz[fx] -= siz[fy];
	}

	public static void add(int jobl, int jobr, int jobv, int l, int r, int i) {
		if (jobl <= l && r <= jobr) {
			addEdgeS(i, jobv);
		} else {
			int mid = (l + r) >> 1;
			if (jobl <= mid) {
				add(jobl, jobr, jobv, l, mid, i << 1);
			}
			if (jobr > mid) {
				add(jobl, jobr, jobv, mid + 1, r, i << 1 | 1);
			}
		}
	}

	public static void dfs(int l, int r, int i) {
		int unionCnt = 0;
		for (int ei = heads[i], fx, fy; ei > 0; ei = nexts[ei]) {
			fx = find(x[tos[ei]]);
			fy = find(y[tos[ei]]);
			if (fx != fy) {
				union(fx, fy);
				unionCnt++;
			}
		}
		if (l == r) {
			for (int ei = headc[l], fx, fy; ei > 0; ei = nextc[ei]) {
				fx = find(x[toc[ei]]);
				fy = find(y[toc[ei]]);
				ans += (long) siz[fx] * siz[fy];
			}
		} else {
			int mid = (l + r) >> 1;
			dfs(l, mid, i << 1);
			dfs(mid + 1, r, i << 1 | 1);
		}
		for (int k = 1; k <= unionCnt; k++) {
			undo();
		}
	}

	public static void main(String[] args) {
		FastIO io = new FastIO(System.in, System.out);
		n = io.nextInt();
		for (int i = 1; i < n; i++) {
			x[i] = io.nextInt();
			y[i] = io.nextInt();
			c[i] = io.nextInt();
		}
		for (int i = 1; i < n; i++) {
			addEdgeC(c[i], i);
			if (c[i] > 1) {
				add(1, c[i] - 1, i, 1, n, 1);
			}
			if (c[i] < n) {
				add(c[i] + 1, n, i, 1, n, 1);
			}
		}
		for (int i = 1; i <= n; i++) {
			father[i] = i;
			siz[i] = 1;
		}
		dfs(1, n, 1);
		io.writelnLong(ans);
		io.flush();
	}

	// 读写工具类
	static class FastIO {
		private final InputStream is;
		private final OutputStream os;
		private final byte[] inbuf = new byte[1 << 16];
		private int lenbuf = 0;
		private int ptrbuf = 0;
		private final StringBuilder outBuf = new StringBuilder();

		public FastIO(InputStream is, OutputStream os) {
			this.is = is;
			this.os = os;
		}

		private int readByte() {
			if (ptrbuf >= lenbuf) {
				ptrbuf = 0;
				try {
					lenbuf = is.read(inbuf);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
				if (lenbuf == -1) {
					return -1;
				}
			}
			return inbuf[ptrbuf++] & 0xff;
		}

		private int skip() {
			int b;
			while ((b = readByte()) != -1) {
				if (b > ' ') {
					return b;
				}
			}
			return -1;
		}

		public int nextInt() {
			int b = skip();
			if (b == -1) {
				throw new RuntimeException("No more integers (EOF)");
			}
			boolean negative = false;
			if (b == '-') {
				negative = true;
				b = readByte();
			}
			int val = 0;
			while (b >= '0' && b <= '9') {
				val = val * 10 + (b - '0');
				b = readByte();
			}
			return negative ? -val : val;
		}

		public void writelnLong(long x) {
			outBuf.append(x).append('\n');
		}

		public void flush() {
			try {
				os.write(outBuf.toString().getBytes());
				os.flush();
				outBuf.setLength(0);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

}
