package class186;

// 红黑蛛网，java版
// 一共n个节点，给定n-1条边，所有节点组成一棵树
// 每条边有两种边权，w为重量，c为颜色，颜色只有红和黑
// 考虑点对(x, y)的简单路径，x和y必须不同，假设红边数量r、黑边数量b
// 如果 2 * min(r, b) >= max(r, b)，那么简单路径是合法的
// 合法简单路径的收益 = 路径上所有边的重量乘积
// 注意点对(x, y)和点对(y, x)的简单路径，只能算一次收益
// 打印所有合法简单路径的收益总乘积，结果对 1000000007 取余，不存在任何合法路径打印1
// 2 <= n <= 10^5    1 <= w <= 10^9 + 6
// 测试链接 : https://www.luogu.com.cn/problem/CF833D
// 测试链接 : https://codeforces.com/problemset/problem/833/D
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code02_Cobweb1 {

	public static int MAXN = 200001;
	public static final int MOD = 1000000007;
	public static int n, cntn;

	public static int[] head1 = new int[MAXN];
	public static int[] next1 = new int[MAXN << 1];
	public static int[] to1 = new int[MAXN << 1];
	public static int[] weight1 = new int[MAXN << 1];
	public static int[] color1 = new int[MAXN << 1];
	public static int cnt1;

	public static int[] head2 = new int[MAXN];
	public static int[] next2 = new int[MAXN << 1];
	public static int[] to2 = new int[MAXN << 1];
	public static int[] weight2 = new int[MAXN << 1];
	public static int[] color2 = new int[MAXN << 1];
	public static int cnt2;

	public static boolean[] vis = new boolean[MAXN];
	public static int[] siz = new int[MAXN];

	public static int[] redKey = new int[MAXN];
	public static long[] redPath = new long[MAXN];
	public static int[] blackKey = new int[MAXN];
	public static long[] blackPath = new long[MAXN];
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

	public static void addEdge1(int u, int v, int w, int c) {
		next1[++cnt1] = head1[u];
		to1[cnt1] = v;
		weight1[cnt1] = w;
		color1[cnt1] = c;
		head1[u] = cnt1;
	}

	public static void addEdge2(int u, int v, int w, int c) {
		next2[++cnt2] = head2[u];
		to2[cnt2] = v;
		weight2[cnt2] = w;
		color2[cnt2] = c;
		head2[u] = cnt2;
	}

	public static void rebuild(int u, int fa) {
		int last = 0;
		for (int e = head1[u]; e > 0; e = next1[e]) {
			int v = to1[e];
			int w = weight1[e];
			int c = color1[e];
			if (v != fa) {
				if (last == 0) {
					last = u;
					addEdge2(u, v, w, c);
					addEdge2(v, u, w, c);
				} else {
					int add = ++cntn;
					addEdge2(last, add, 1, -1);
					addEdge2(add, last, 1, -1);
					addEdge2(add, v, w, c);
					addEdge2(v, add, w, c);
					last = add;
				}
				rebuild(v, u);
			}
		}
	}

	public static void getSize(int u, int fa) {
		siz[u] = 1;
		for (int e = head2[u]; e > 0; e = next2[e]) {
			int v = to2[e];
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
			for (int e = head2[u]; e > 0; e = next2[e]) {
				int v = to2[e];
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
		if (l >= r) return;
		int i = l, j = r, pivot = key[(l + r) >> 1];
		while (i <= j) {
			while (key[i] < pivot) i++;
			while (key[j] > pivot) j--;
			if (i <= j) {
				int tmp1 = key[i]; key[i] = key[j]; key[j] = tmp1;
				long tmp2 = path[i]; path[i] = path[j]; path[j] = tmp2;
				i++; j--;
			}
		}
		sort(key, path, l, j);
		sort(key, path, i, r);
	}

	public static int lessThan(int[] arr, int len, int num) {
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

	public static void dfs(int u, int fa, int red, int black, long path) {
		if (u <= n) {
			redKey[++cnta] = 2 * red - black;
			redPath[cnta] = path;
			blackKey[cnta] = 2 * black - red;
			blackPath[cnta] = path;
		}
		for (int e = head2[u]; e > 0; e = next2[e]) {
			int v = to2[e];
			if (v != fa && !vis[e >> 1]) {
				int nextRed = red + (color2[e] == 0 ? 1 : 0);
				int nextBlack = black + (color2[e] == 1 ? 1 : 0);
				dfs(v, u, nextRed, nextBlack, path * weight2[e] % MOD);
			}
		}
	}

	public static void calcAns(int u, int fa, int red, int black, long path) {
		if (u <= n) {
			int r = lessThan(redKey, cnta, black - 2 * red);
			int b = lessThan(blackKey, cnta, red - 2 * black);
			if (r > 0) {
				ans2 = ans2 * power(path, r) % MOD * redPath[r] % MOD;
			}
			if (b > 0) {
				ans2 = ans2 * power(path, b) % MOD * blackPath[b] % MOD;
			}
		}
		for (int e = head2[u]; e > 0; e = next2[e]) {
			int v = to2[e];
			if (v != fa && !vis[e >> 1]) {
				int nextRed = red + (color2[e] == 0 ? 1 : 0);
				int nextBlack = black + (color2[e] == 1 ? 1 : 0);
				calcAns(v, u, nextRed, nextBlack, path * weight2[e] % MOD);
			}
		}
	}

	public static void calc(int edge) {
		cnta = 0;
		int v = to2[edge];
		dfs(v, 0, 0, 0, 1);
		sort(redKey, redPath, 1, cnta);
		sort(blackKey, blackPath, 1, cnta);
		for (int i = 2; i <= cnta; i++) {
			redPath[i] = redPath[i - 1] * redPath[i] % MOD;
			blackPath[i] = blackPath[i - 1] * blackPath[i] % MOD;
		}
		v = to2[edge ^ 1];
		int red = (color2[edge] == 0 ? 1 : 0);
		int black = (color2[edge] == 1 ? 1 : 0);
		calcAns(v, 0, red, black, weight2[edge] % MOD);
	}

	public static void solve(int u) {
		int edge = getCentroidEdge(u, 0);
		if (edge > 0) {
			vis[edge >> 1] = true;
			calc(edge);
			solve(to2[edge]);
			solve(to2[edge ^ 1]);
		}
	}

	public static void prepare(int u, int fa) {
		siz[u] = 1;
		for (int e = head1[u]; e > 0; e = next1[e]) {
			int v = to1[e];
			if (v != fa) {
				prepare(v, u);
				siz[u] += siz[v];
				ans1 = ans1 * power(weight1[e], (long) siz[v] * (n - siz[v])) % MOD;
			}
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		for (int i = 1, u, v, w, c; i < n; i++) {
			u = in.nextInt();
			v = in.nextInt();
			w = in.nextInt();
			c = in.nextInt();
			addEdge1(u, v, w, c);
			addEdge1(v, u, w, c);
		}
		cntn = n;
		cnt2 = 1;
		ans1 = ans2 = 1;
		prepare(1, 0);
		rebuild(1, 0);
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
