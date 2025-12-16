package class186;

// 红黑蛛网，java版
// 测试链接 : https://www.luogu.com.cn/problem/CF833D
// 测试链接 : https://codeforces.com/problemset/problem/833/D
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code02_Cobweb1 {

	public static int MAXN = 400001;
	public static int MAXS = 4000001;
	public static final int MOD = 1000000007;
	public static int n, realNode;

	public static int[] headg = new int[MAXN];
	public static int[] nextg = new int[MAXN << 1];
	public static int[] tog = new int[MAXN << 1];
	public static int[] weightg = new int[MAXN << 1];
	public static int[] colorg = new int[MAXN << 1];
	public static int cntg;

	public static int[] sonCnt = new int[MAXN];
	public static int[] heads = new int[MAXN];
	public static int[] nexts = new int[MAXS];
	public static int[] sons = new int[MAXS];
	public static int[] weights = new int[MAXS];
	public static int[] colors = new int[MAXS];
	public static int cnts;

	public static boolean[] vis = new boolean[MAXN];
	public static int[] siz = new int[MAXN];

	public static int[] rkey = new int[MAXN];
	public static long[] rpath = new long[MAXN];
	public static int[] bkey = new int[MAXN];
	public static long[] bpath = new long[MAXN];
	public static int cnta;

	public static long ans1, ans2;

	public static long power(long x, long p) {
		long ans = 1;
		while (p != 0) {
			if ((p & 1) != 0) {
				ans = ans * x % MOD;
			}
			p >>= 1;
			x = x * x % MOD;
		}
		return ans;
	}

	public static void addEdge(int u, int v, int w, int c) {
		nextg[++cntg] = headg[u];
		tog[cntg] = v;
		weightg[cntg] = w;
		colorg[cntg] = c;
		headg[u] = cntg;
	}

	public static void addSon(int u, int v, int w, int c) {
		sonCnt[u]++;
		nexts[++cnts] = heads[u];
		sons[cnts] = v;
		weights[cnts] = w;
		colors[cnts] = c;
		heads[u] = cnts;
	}

	public static void buildSon(int u, int fa) {
		for (int e = headg[u]; e > 0; e = nextg[e]) {
			int v = tog[e];
			if (v != fa) {
				addSon(u, v, weightg[e], colorg[e]);
				buildSon(v, u);
			}
		}
	}

	public static void rebuildTree() {
		buildSon(1, 0);
		cntg = 1;
		for (int u = 1; u <= n; u++) {
			headg[u] = 0;
		}
		for (int u = 1; u <= n; u++) {
			if (sonCnt[u] <= 2) {
				for (int e = heads[u]; e > 0; e = nexts[e]) {
					int v = sons[e];
					int w = weights[e];
					int c = colors[e];
					addEdge(u, v, w, c);
					addEdge(v, u, w, c);
				}
			} else {
				int addNode1 = ++n;
				int addNode2 = ++n;
				addEdge(u, addNode1, 1, -1);
				addEdge(addNode1, u, 1, -1);
				addEdge(u, addNode2, 1, -1);
				addEdge(addNode2, u, 1, -1);
				boolean add1 = true;
				for (int e = heads[u]; e > 0; e = nexts[e]) {
					int v = sons[e];
					int w = weights[e];
					int c = colors[e];
					if (add1) {
						addSon(addNode1, v, w, c);
					} else {
						addSon(addNode2, v, w, c);
					}
					add1 = !add1;
				}
			}
		}
	}

	public static void getSize(int u, int fa) {
		siz[u] = 1;
		for (int e = headg[u]; e > 0; e = nextg[e]) {
			int v = tog[e];
			if (v != fa && !vis[e >> 1]) {
				getSize(v, u);
				siz[u] += siz[v];
			}
		}
	}

	public static int getCentroidEdge(int u, int fa) {
		getSize(u, fa);
		int total = siz[u];
		int edge = 0;
		int best = total;
		while (u > 0) {
			int nextu = 0, nextfa = 0;
			for (int e = headg[u]; e > 0; e = nextg[e]) {
				int v = tog[e];
				if (v != fa && !vis[e >> 1]) {
					int cur = Math.max(total - siz[v], siz[v]);
					if (cur < best) {
						edge = e;
						best = cur;
						nextfa = u;
						nextu = v;
					}
				}
			}
			fa = nextfa;
			u = nextu;
		}
		return edge;
	}

	public static void sort(int[] key, long[] path, int l, int r) {
		if (l >= r)
			return;
		int i = l, j = r, pivot = key[(l + r) >> 1];
		while (i <= j) {
			while (key[i] < pivot)
				i++;
			while (key[j] > pivot)
				j--;
			if (i <= j) {
				int tmp1 = key[i];
				key[i] = key[j];
				key[j] = tmp1;
				long tmp2 = path[i];
				path[i] = path[j];
				path[j] = tmp2;
				i++;
				j--;
			}
		}
		sort(key, path, l, j);
		sort(key, path, i, r);
	}

	public static int less(int[] arr, int len, int num) {
		int l = 1, r = len, mid, ans = 0;
		while (l <= r) {
			mid = (l + r) >> 1;
			if (arr[mid] < num) {
				ans = mid;
				l = mid + 1;
			} else {
				r = mid - 1;
			}
		}
		return ans;
	}

	public static void collect(int u, int fa, int red, int black, long path) {
		if (u <= realNode) {
			rkey[++cnta] = 2 * red - black;
			rpath[cnta] = path;
			bkey[cnta] = 2 * black - red;
			bpath[cnta] = path;
		}
		for (int e = headg[u]; e > 0; e = nextg[e]) {
			int v = tog[e];
			if (v != fa && !vis[e >> 1]) {
				int nextRed = red + (colorg[e] == 0 ? 1 : 0);
				int nextBlack = black + (colorg[e] == 1 ? 1 : 0);
				collect(v, u, nextRed, nextBlack, path * weightg[e] % MOD);
			}
		}
	}

	public static void dfs(int u, int fa, int red, int black, long path) {
		if (u <= realNode) {
			int t = less(rkey, cnta, black - 2 * red);
			if (t > 0) {
				ans2 = ans2 * power(path, t) % MOD * rpath[t] % MOD;
			}
			t = less(bkey, cnta, red - 2 * black);
			if (t > 0) {
				ans2 = ans2 * power(path, t) % MOD * bpath[t] % MOD;
			}
		}
		for (int e = headg[u]; e > 0; e = nextg[e]) {
			int v = tog[e];
			if (v != fa && !vis[e >> 1]) {
				int nextRed = red + (colorg[e] == 0 ? 1 : 0);
				int nextBlack = black + (colorg[e] == 1 ? 1 : 0);
				dfs(v, u, nextRed, nextBlack, path * weightg[e] % MOD);
			}
		}
	}

	public static void calc(int edge) {
		cnta = 0;
		int v = tog[edge];
		collect(v, 0, 0, 0, 1);
		sort(rkey, rpath, 1, cnta);
		sort(bkey, bpath, 1, cnta);
		for (int i = 2; i <= cnta; i++) {
			rpath[i] = rpath[i - 1] * rpath[i] % MOD;
			bpath[i] = bpath[i - 1] * bpath[i] % MOD;
		}
		v = tog[edge ^ 1];
		int red = (colorg[edge] == 0 ? 1 : 0);
		int black = (colorg[edge] == 1 ? 1 : 0);
		dfs(v, 0, red, black, weightg[edge] % MOD);
	}

	public static void solve(int u) {
		int edge = getCentroidEdge(u, 0);
		if (edge > 0) {
			vis[edge >> 1] = true;
			calc(edge);
			solve(tog[edge]);
			solve(tog[edge ^ 1]);
		}
	}

	public static void prepare(int u, int fa) {
		siz[u] = 1;
		for (int e = headg[u]; e > 0; e = nextg[e]) {
			int v = tog[e];
			if (v != fa) {
				prepare(v, u);
				siz[u] += siz[v];
				ans1 = ans1 * power(weightg[e], (long) siz[v] * (n - siz[v])) % MOD;
			}
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		realNode = n;
		ans1 = ans2 = 1;
		for (int i = 1, u, v, w, c; i < n; i++) {
			u = in.nextInt();
			v = in.nextInt();
			w = in.nextInt();
			c = in.nextInt();
			addEdge(u, v, w, c);
			addEdge(v, u, w, c);
		}
		prepare(1, 0);
		rebuildTree();
		solve(1);
		long ans = ans1 * power(ans2, MOD - 2) % MOD;
		out.println(ans);
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
