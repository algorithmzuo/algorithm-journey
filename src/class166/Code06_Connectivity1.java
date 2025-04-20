package class166;

// 连通性离线处理，java版
// 测试链接 : https://loj.ac/p/121
// 提交以下的code，提交时请把类名改成"Main"
// 测试平台看似支持java语言，其实无法通过，内存过大跳警告，导致验证失败
// 想通过用C++实现，本节课Code06_Connectivity2文件就是C++的实现
// 逻辑完全一样，C++实现可以通过全部测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Code06_Connectivity1 {

	public static int MAXN = 5001;
	public static int MAXM = 500001;
	public static int MAXT = 5000001;
	public static int n, m;

	public static int[] op = new int[MAXM];
	public static int[] a = new int[MAXM];
	public static int[] b = new int[MAXM];

	public static int[][] last = new int[MAXN][MAXN];

	public static int[] father = new int[MAXN];
	public static int[] siz = new int[MAXN];
	public static int[][] rollback = new int[MAXN][2];
	public static int opsize = 0;

	public static int[] head = new int[MAXM << 2];
	public static int[] next = new int[MAXT];
	public static int[] tox = new int[MAXT];
	public static int[] toy = new int[MAXT];
	public static int cnt = 0;

	public static boolean[] ans = new boolean[MAXM];

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

	public static void dfs(int l, int r, int i) {
		int unionCnt = 0;
		for (int ei = head[i], x, y, fx, fy; ei > 0; ei = next[ei]) {
			x = tox[ei];
			y = toy[ei];
			fx = find(x);
			fy = find(y);
			if (fx != fy) {
				union(fx, fy);
				unionCnt++;
			}
		}
		if (l == r) {
			if (op[l] == 2) {
				ans[l] = find(a[l]) == find(b[l]);
			}
		} else {
			int mid = (l + r) / 2;
			dfs(l, mid, i << 1);
			dfs(mid + 1, r, i << 1 | 1);
		}
		for (int j = 1; j <= unionCnt; j++) {
			undo();
		}
	}

	public static void prepare() {
		for (int i = 1; i <= n; i++) {
			father[i] = i;
			siz[i] = 1;
		}
		for (int i = 1, x, y; i <= m; i++) {
			x = a[i];
			y = b[i];
			if (op[i] == 0) {
				last[x][y] = i;
			} else if (op[i] == 1) {
				add(last[x][y], i - 1, x, y, 1, m, 1);
				last[x][y] = 0;
			}
		}
		for (int x = 1; x <= n; x++) {
			for (int y = x + 1; y <= n; y++) {
				if (last[x][y] != 0) {
					add(last[x][y], m, x, y, 1, m, 1);
				}
			}
		}
	}

	public static void main(String[] args) {
		FastIO io = new FastIO(System.in, System.out);
		n = io.nextInt();
		m = io.nextInt();
		for (int i = 1, x, y, t; i <= m; i++) {
			t = io.nextInt();
			x = io.nextInt();
			y = io.nextInt();
			op[i] = t;
			a[i] = Math.min(x, y);
			b[i] = Math.max(x, y);
		}
		prepare();
		dfs(1, m, 1);
		for (int i = 1; i <= m; i++) {
			if (op[i] == 2) {
				if (ans[i]) {
					io.write("Y\n");
				} else {
					io.write("N\n");
				}
			}
		}
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
