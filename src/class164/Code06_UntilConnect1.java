package class164;

// 加边直到连通，java版
// 测试链接 : https://www.luogu.com.cn/problem/CF1706E
// 测试链接 : https://codeforces.com/problemset/problem/1706/E
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

public class Code06_UntilConnect1 {

	public static int MAXN = 100001;
	public static int MAXK = 200001;
	public static int MAXM = 200001;
	public static int MAXH = 20;
	public static int t, n, m, q;
	public static int[][] edge = new int[MAXM][3];

	public static int[] head = new int[MAXK];
	public static int[] next = new int[MAXK];
	public static int[] to = new int[MAXK];
	public static int cntg;

	public static int[] father = new int[MAXK];
	public static int[] nodeKey = new int[MAXK];
	public static int cntu;

	public static int[] dep = new int[MAXK];
	public static int[] dfn = new int[MAXK];
	public static int[] seg = new int[MAXK];
	public static int[][] stjump = new int[MAXK][MAXH];
	public static int cntd;

	public static int[] lg2 = new int[MAXN];
	public static int[][] stmax = new int[MAXN][MAXH];
	public static int[][] stmin = new int[MAXN][MAXH];

	public static void clear() {
		cntg = cntd = 0;
		Arrays.fill(head, 1, n * 2, 0);
	}

	public static int find(int i) {
		if (i != father[i]) {
			father[i] = find(father[i]);
		}
		return father[i];
	}

	public static void addEdge(int u, int v) {
		next[++cntg] = head[u];
		to[cntg] = v;
		head[u] = cntg;
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
		dep[u] = dep[fa] + 1;
		dfn[u] = ++cntd;
		seg[cntd] = u;
		stjump[u][0] = fa;
		for (int p = 1; p < MAXH; p++) {
			stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
		}
		for (int e = head[u]; e > 0; e = next[e]) {
			dfs(to[e], u);
		}
	}

	public static void buildst() {
		lg2[0] = -1;
		for (int i = 1; i <= n; i++) {
			lg2[i] = lg2[i >> 1] + 1;
			stmax[i][0] = dfn[i];
			stmin[i][0] = dfn[i];
		}
		for (int p = 1; p <= lg2[n]; p++) {
			for (int i = 1; i + (1 << p) - 1 <= n; i++) {
				stmax[i][p] = Math.max(stmax[i][p - 1], stmax[i + (1 << (p - 1))][p - 1]);
				stmin[i][p] = Math.min(stmin[i][p - 1], stmin[i + (1 << (p - 1))][p - 1]);
			}
		}
	}

	public static int dfnmin(int l, int r) {
		int p = lg2[r - l + 1];
		int ans = Math.min(stmin[l][p], stmin[r - (1 << p) + 1][p]);
		return ans;
	}

	public static int dfnmax(int l, int r) {
		int p = lg2[r - l + 1];
		int ans = Math.max(stmax[l][p], stmax[r - (1 << p) + 1][p]);
		return ans;
	}

	public static int lca(int a, int b) {
		if (dep[a] < dep[b]) {
			int tmp = a;
			a = b;
			b = tmp;
		}
		for (int p = MAXH - 1; p >= 0; p--) {
			if (dep[stjump[a][p]] >= dep[b]) {
				a = stjump[a][p];
			}
		}
		if (a == b) {
			return a;
		}
		for (int p = MAXH - 1; p >= 0; p--) {
			if (stjump[a][p] != stjump[b][p]) {
				a = stjump[a][p];
				b = stjump[b][p];
			}
		}
		return stjump[a][0];
	}

	public static int query(int l, int r) {
		int x = seg[dfnmin(l, r)];
		int y = seg[dfnmax(l, r)];
		return nodeKey[lca(x, y)];
	}

	public static void main(String[] args) {
		FastIO io = new FastIO(System.in, System.out);
		t = io.nextInt();
		for (int test = 1; test <= t; test++) {
			n = io.nextInt();
			m = io.nextInt();
			q = io.nextInt();
			for (int i = 1; i <= m; i++) {
				edge[i][0] = io.nextInt();
				edge[i][1] = io.nextInt();
				edge[i][2] = i;
			}
			clear();
			kruskalRebuild();
			dfs(cntu, 0);
			buildst();
			for (int i = 1, l, r; i <= q; i++) {
				l = io.nextInt();
				r = io.nextInt();
				if (l == r) {
					io.write("0 ");
				} else {
					io.write(query(l, r) + " ");
				}
			}
			io.write("\n");
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
