package class164;

// 边的最大编号的最小值，java版
// 图里有n个点，m条无向边，边的编号1~m，没有边权，所有点都连通
// 一共有q条查询，查询的格式如下
// 查询 x y z : 从两个点x和y出发，希望经过的点数量等于z
//              每个点可以重复经过，但是重复经过只计算一次
//              经过边的最大编号，最小是多少
// 3 <= n、m、q <= 10^5
// 3 <= z <= n
// 测试链接 : https://www.luogu.com.cn/problem/AT_agc002_d
// 测试链接 : https://atcoder.jp/contests/agc002/tasks/agc002_d
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

public class Code03_StampRally1 {

	public static int MAXK = 200001;
	public static int MAXM = 100001;
	public static int MAXP = 20;
	public static int n, m, q;
	public static int[][] edge = new int[MAXM][3];
	// 并查集
	public static int[] father = new int[MAXK];

	// Kruskal重构树
	public static int[] head = new int[MAXK];
	public static int[] next = new int[MAXK];
	public static int[] to = new int[MAXK];
	public static int cntg = 0;
	public static int[] nodeKey = new int[MAXK];
	public static int cntu;

	// 树上dfs
	public static int[] leafsiz = new int[MAXK];
	public static int[][] stjump = new int[MAXK][MAXP];

	public static void addEdge(int u, int v) {
		next[++cntg] = head[u];
		to[cntg] = v;
		head[u] = cntg;
	}

	public static int find(int i) {
		if (i != father[i]) {
			father[i] = find(father[i]);
		}
		return father[i];
	}

	public static void kruskalRebuild() {
		for (int i = 1; i <= n; i++) {
			father[i] = i;
		}
		Arrays.sort(edge, 1, m + 1, (a, b) -> a[2] - b[2]);
		cntu = n;
		for (int i = 1, fx, fy; i <= m; i++) {
			fx = find(edge[i][0]);
			fy = find(edge[i][1]);
			if (fx != fy) {
				father[fx] = father[fy] = ++cntu;
				father[cntu] = cntu;
				nodeKey[cntu] = edge[i][2];
				addEdge(cntu, fx);
				addEdge(cntu, fy);
			}
		}
	}

	public static void dfs(int u, int fa) {
		stjump[u][0] = fa;
		for (int p = 1; p < MAXP; p++) {
			stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
		}
		for (int e = head[u]; e > 0; e = next[e]) {
			dfs(to[e], u);
		}
		if (u <= n) {
			leafsiz[u] = 1;
		} else {
			leafsiz[u] = 0;
		}
		for (int e = head[u]; e > 0; e = next[e]) {
			leafsiz[u] += leafsiz[to[e]];
		}
	}

	public static boolean check(int x, int y, int z, int limit) {
		for (int p = MAXP - 1; p >= 0; p--) {
			if (stjump[x][p] > 0 && nodeKey[stjump[x][p]] <= limit) {
				x = stjump[x][p];
			}
		}
		for (int p = MAXP - 1; p >= 0; p--) {
			if (stjump[y][p] > 0 && nodeKey[stjump[y][p]] <= limit) {
				y = stjump[y][p];
			}
		}
		if (x == y) {
			return leafsiz[x] >= z;
		} else {
			return leafsiz[x] + leafsiz[y] >= z;
		}
	}

	public static int query(int x, int y, int z) {
		int l = 1, r = m, mid, ans = 0;
		while (l <= r) {
			mid = (l + r) / 2;
			if (check(x, y, z, mid)) {
				ans = mid;
				r = mid - 1;
			} else {
				l = mid + 1;
			}
		}
		return ans;
	}

	public static void main(String[] args) {
		FastIO io = new FastIO(System.in, System.out);
		n = io.nextInt();
		m = io.nextInt();
		for (int i = 1; i <= m; i++) {
			edge[i][0] = io.nextInt();
			edge[i][1] = io.nextInt();
			edge[i][2] = i;
		}
		kruskalRebuild();
		dfs(cntu, 0);
		q = io.nextInt();
		for (int i = 1, x, y, z; i <= q; i++) {
			x = io.nextInt();
			y = io.nextInt();
			z = io.nextInt();
			io.writelnInt(query(x, y, z));
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
