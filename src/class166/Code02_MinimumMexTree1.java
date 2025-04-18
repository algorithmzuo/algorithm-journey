package class166;

// 最小mex生成树，java版
// 测试链接 : https://www.luogu.com.cn/problem/P5631
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Code02_MinimumMexTree1 {

	public static int MAXN = 1000001;
	public static int MAXV = 100001;
	public static int MAXT = 30000001;
	public static int n, m, v;

	public static int[] father = new int[MAXN];
	public static int[] siz = new int[MAXN];
	public static int[][] rollback = new int[MAXN][2];
	public static int opsize;

	public static int[] head = new int[MAXV << 2];
	public static int[] next = new int[MAXT];
	public static int[] tox = new int[MAXT];
	public static int[] toy = new int[MAXT];
	public static int cnt = 0;

	public static int part;

	public static void addEdge(int i, int x, int y) {
		next[++cnt] = head[i];
		tox[cnt] = x;
		toy[cnt] = y;
		head[i] = cnt;
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

	public static void add(int jobl, int jobr, int jobx, int joby, int l, int r, int i) {
		if (jobl <= l && r <= jobr) {
			addEdge(i, jobx, joby);
		} else {
			int mid = (l + r) >> 1;
			if (jobl <= mid) {
				add(jobl, jobr, jobx, joby, l, mid, i << 1);
			}
			if (jobr > mid) {
				add(jobl, jobr, jobx, joby, mid + 1, r, i << 1 | 1);
			}
		}
	}

	public static int dfs(int l, int r, int i) {
		int unionCnt = 0;
		for (int ei = head[i], fx, fy; ei > 0; ei = next[ei]) {
			fx = find(tox[ei]);
			fy = find(toy[ei]);
			if (fx != fy) {
				union(fx, fy);
				part--;
				unionCnt++;
			}
		}
		int ans = -1;
		if (l == r) {
			if (part == 1) {
				ans = l;
			}
		} else {
			int mid = (l + r) >> 1;
			ans = dfs(l, mid, i << 1);
			if (ans == -1) {
				ans = dfs(mid + 1, r, i << 1 | 1);
			}
		}
		for (int k = 1; k <= unionCnt; k++) {
			undo();
			part++;
		}
		return ans;
	}

	public static void main(String[] args) {
		FastIO io = new FastIO(System.in, System.out);
		n = io.nextInt();
		m = io.nextInt();
		v = MAXV;
		for (int i = 1; i <= n; i++) {
			father[i] = i;
			siz[i] = 1;
		}
		for (int i = 1, x, y, w; i <= m; i++) {
			x = io.nextInt();
			y = io.nextInt();
			w = io.nextInt();
			if (w > 0) {
				add(0, w - 1, x, y, 0, v, 1);
			}
			add(w + 1, v, x, y, 0, v, 1);
		}
		part = n;
		io.writelnInt(dfs(0, v, 1));
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

		public void write(String s) {
			outBuf.append(s);
		}

		public void writeInt(int x) {
			outBuf.append(x);
		}

		public void writelnInt(int x) {
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