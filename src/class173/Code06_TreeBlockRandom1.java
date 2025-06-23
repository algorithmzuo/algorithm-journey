package class173;

// 树上分块模版题，重链剖分 + 随机撒点，java版
// 测试链接 : https://www.luogu.com.cn/problem/P3603
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code06_TreeBlockRandom1 {

	public static int MAXN = 100001;
	public static int MAXB = 301;
	public static int MAXV = 30001;
	public static int n, m, f, k;
	public static int[] arr = new int[MAXN];

	// 链式前向星
	public static int[] head = new int[MAXN];
	public static int[] next = new int[MAXN << 1];
	public static int[] to = new int[MAXN << 1];
	public static int cntg = 0;

	// 重链剖分
	public static int[] fa = new int[MAXN];
	public static int[] dep = new int[MAXN];
	public static int[] siz = new int[MAXN];
	public static int[] son = new int[MAXN];
	public static int[] top = new int[MAXN];

	// 随机撒点
	public static int bnum;
	public static int[] tag = new int[MAXN];
	public static int[] spe = new int[MAXN];
	public static int[] up = new int[MAXN];
	public static boolean[] vis = new boolean[MAXN];
	public static BitSet[][] bitSet = new BitSet[MAXB][MAXB];

	public static BitSet tmp = new BitSet();
	public static BitSet ans = new BitSet();

	static class BitSet {

		int len;

		public int[] set;

		public BitSet() {
			len = (MAXV + 31) / 32;
			set = new int[len];
		}

		public void clear() {
			for (int i = 0; i < len; i++) {
				set[i] = 0;
			}
		}

		public void setOne(int v) {
			set[v / 32] |= 1 << (v % 32);
		}

		public void or(BitSet obj) {
			for (int i = 0; i < len; i++) {
				set[i] |= obj.set[i];
			}
		}

		public int getOnes() {
			int ans = 0;
			for (int x : set) {
				ans += Integer.bitCount(x);
			}
			return ans;
		}

		public int mex() {
			for (int i = 0, inv; i < len; i++) {
				inv = ~set[i];
				if (inv != 0) {
					return i * 32 + Integer.numberOfTrailingZeros(inv);
				}
			}
			return -1;
		}

	}

	public static void addEdge(int u, int v) {
		next[++cntg] = head[u];
		to[cntg] = v;
		head[u] = cntg;
	}

	public static void dfs1(int u, int f) {
		fa[u] = f;
		dep[u] = dep[f] + 1;
		siz[u] = 1;
		for (int e = head[u], v; e > 0; e = next[e]) {
			v = to[e];
			if (v != f) {
				dfs1(v, u);
			}
		}
		for (int e = head[u], v; e > 0; e = next[e]) {
			v = to[e];
			if (v != f) {
				siz[u] += siz[v];
				if (son[u] == 0 || siz[son[u]] < siz[v]) {
					son[u] = v;
				}
			}
		}
	}

	public static void dfs2(int u, int t) {
		top[u] = t;
		if (son[u] == 0) {
			return;
		}
		dfs2(son[u], t);
		for (int e = head[u], v; e > 0; e = next[e]) {
			v = to[e];
			if (v != fa[u] && v != son[u]) {
				dfs2(v, v);
			}
		}
	}

	// 不会改迭代版，去看讲解118，详解了从递归版改迭代版
	public static int[][] fse = new int[MAXN][3];

	public static int stacksize, first, second, edge;

	public static void push(int fir, int sec, int edg) {
		fse[stacksize][0] = fir;
		fse[stacksize][1] = sec;
		fse[stacksize][2] = edg;
		stacksize++;
	}

	public static void pop() {
		--stacksize;
		first = fse[stacksize][0];
		second = fse[stacksize][1];
		edge = fse[stacksize][2];
	}

	// dfs1的迭代版
	public static void dfs3() {
		stacksize = 0;
		push(1, 0, -1);
		while (stacksize > 0) {
			pop();
			if (edge == -1) {
				fa[first] = second;
				dep[first] = dep[second] + 1;
				siz[first] = 1;
				edge = head[first];
			} else {
				edge = next[edge];
			}
			if (edge != 0) {
				push(first, second, edge);
				if (to[edge] != second) {
					push(to[edge], first, -1);
				}
			} else {
				for (int e = head[first], v; e > 0; e = next[e]) {
					v = to[e];
					if (v != second) {
						siz[first] += siz[v];
						if (son[first] == 0 || siz[son[first]] < siz[v]) {
							son[first] = v;
						}
					}
				}
			}
		}
	}

	// dfs2的迭代版
	public static void dfs4() {
		stacksize = 0;
		push(1, 1, -1);
		while (stacksize > 0) {
			pop();
			if (edge == -1) {
				top[first] = second;
				if (son[first] == 0) {
					continue;
				}
				push(first, second, -2);
				push(son[first], second, -1);
				continue;
			} else if (edge == -2) {
				edge = head[first];
			} else {
				edge = next[edge];
			}
			if (edge != 0) {
				push(first, second, edge);
				if (to[edge] != fa[first] && to[edge] != son[first]) {
					push(to[edge], to[edge], -1);
				}
			}
		}
	}

	public static int lca(int a, int b) {
		while (top[a] != top[b]) {
			if (dep[top[a]] <= dep[top[b]]) {
				b = fa[top[b]];
			} else {
				a = fa[top[a]];
			}
		}
		return dep[a] <= dep[b] ? a : b;
	}

	public static void query(int x, int xylca) {
		while (spe[x] == 0 && x != xylca) {
			ans.setOne(arr[x]);
			x = fa[x];
		}
		int backup = x;
		while (up[x] > 0 && dep[up[x]] > dep[xylca]) {
			x = up[x];
		}
		ans.or(bitSet[spe[backup]][spe[x]]);
		while (x != xylca) {
			ans.setOne(arr[x]);
			x = fa[x];
		}
	}

	public static void updateAns(int x, int y) {
		int xylca = lca(x, y);
		ans.setOne(arr[xylca]);
		query(x, xylca);
		query(y, xylca);
	}

	public static void prepare() {
		dfs3();
		dfs4();
		int blen = (int) Math.sqrt(20.0 * n);
		bnum = (n + blen - 1) / blen;
		for (int i = 1, pick; i <= bnum; i++) {
			do {
				pick = (int) (Math.random() * n) + 1;
			} while (vis[pick]);
			vis[pick] = true;
			tag[i] = pick;
			spe[pick] = i;
		}
		for (int i = 0; i <= bnum; i++) {
			for (int j = 0; j <= bnum; j++) {
				bitSet[i][j] = new BitSet();
			}
		}
		for (int i = 1, cur; i <= bnum; i++) {
			cur = tag[i];
			tmp.clear();
			do {
				tmp.setOne(arr[cur]);
				if (cur != tag[i] && spe[cur] > 0) {
					bitSet[i][spe[cur]].or(tmp);
					if (up[tag[i]] == 0) {
						up[tag[i]] = cur;
					}
				}
				cur = fa[cur];
			} while (cur != 0);
		}
	}

	public static void main(String[] args) throws IOException {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		f = in.nextInt();
		for (int i = 1; i <= n; i++) {
			arr[i] = in.nextInt();
		}
		for (int i = 1, u, v; i < n; i++) {
			u = in.nextInt();
			v = in.nextInt();
			addEdge(u, v);
			addEdge(v, u);
		}
		prepare();
		for (int i = 1, last = 0; i <= m; i++) {
			ans.clear();
			k = in.nextInt();
			for (int j = 1, x, y; j <= k; j++) {
				x = in.nextInt();
				y = in.nextInt();
				if (f > 0) {
					x = x ^ last;
					y = y ^ last;
				}
				updateAns(x, y);
			}
			int ans1 = ans.getOnes();
			int ans2 = ans.mex();
			out.println(ans1 + " " + ans2);
			last = ans1 + ans2;
		}
		out.flush();
		out.close();
	}

	// 读写工具类
	static class FastReader {
		private final byte[] buffer = new byte[1 << 20];
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
