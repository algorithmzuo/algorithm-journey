package class166;

// 线段树分治模版题，java版
// 一共有n个节点，一共有m条操作，每条操作是如下三种类型中的一种
// 操作 0 x y : 点x和点y之间一定没有边，现在增加一条边
// 操作 1 x y : 点x和点y之间一定存在边，现在删除这条边
// 操作 2 x y : 查询点x和点y是否联通
// 1 <= n <= 5000
// 1 <= m <= 500000
// 不强制在线，可以离线处理
// 测试链接 : https://loj.ac/p/121
// 提交以下的code，提交时类名改成"Main"，多提交几次，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Code01_SegmentTreeDivideConquer1 {

	// 点的数量最大值
	public static int MAXN = 5001;
	// 操作数量最大值
	public static int MAXM = 500001;
	// 任务数量最大值
	public static int MAXT = 5000001;

	public static int n, m;

	// 操作类型op、端点u、端点v
	public static int[] op = new int[MAXM];
	public static int[] u = new int[MAXM];
	public static int[] v = new int[MAXM];

	// last[x][y] : 点x和点y的边，上次出现的时间点
	public static int[][] last = new int[MAXN][MAXN];

	// 可撤销并查集
	public static int[] father = new int[MAXN];
	public static int[] siz = new int[MAXN];
	public static int[][] rollback = new int[MAXN][2];
	public static int opsize = 0;

	// 线段树每个区间拥有哪些任务的列表，链式前向星表示
	public static int[] head = new int[MAXM << 2];
	public static int[] next = new int[MAXT];
	public static int[] tox = new int[MAXT];
	public static int[] toy = new int[MAXT];
	public static int cnt = 0;

	// ans[i]为第i条操作的答案，只有查询操作才有答案
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
				ans[l] = find(u[l]) == find(v[l]);
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
		for (int i = 1, t, x, y; i <= m; i++) {
			t = op[i];
			x = u[i];
			y = v[i];
			if (t == 0) {
				last[x][y] = i;
			} else if (t == 1) {
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
		for (int i = 1, t, x, y; i <= m; i++) {
			t = io.nextInt();
			x = io.nextInt();
			y = io.nextInt();
			op[i] = t;
			u[i] = Math.min(x, y);
			v[i] = Math.max(x, y);
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
