package class145;

// 游戏(递归版)
// 测试链接 : https://www.luogu.com.cn/problem/P6478
// 提交以下的code，提交时请把类名改成"Main"
// C++这么写能全部通过，java有时候能全部通过，有时候若干测试点会出错
// 这是因为递归展开太多层爆栈了，所以最稳妥的方式是改成迭代版
// 迭代版就是本节课Code05_Game2文件

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Code05_Game1 {

	public static final int MAXN = 5001;

	public static final int MOD = 998244353;

	public static int[] arr = new int[MAXN];

	// 阶乘表
	public static long[] fac = new long[MAXN];

	// 组合结果表
	public static long[][] c = new long[MAXN][MAXN];

	// 链式前向星需要
	public static int[] head = new int[MAXN];

	public static int[] next = new int[MAXN << 1];

	public static int[] to = new int[MAXN << 1];

	public static int cnt;

	// dfs需要
	public static int[] size = new int[MAXN];

	public static int[][] belong = new int[MAXN][2];

	public static long[][] dp = new long[MAXN][MAXN];

	public static long[] tmp = new long[MAXN];

	// 反演需要
	public static long[] g = new long[MAXN];

	public static long[] f = new long[MAXN];

	public static int n, m;

	public static void build() {
		fac[0] = 1;
		for (int i = 1; i <= n; i++) {
			fac[i] = fac[i - 1] * i % MOD;
		}
		for (int i = 0; i <= n; i++) {
			c[i][0] = 1;
			for (int j = 1; j <= i; j++) {
				c[i][j] = (c[i - 1][j] + c[i - 1][j - 1]) % MOD;
			}
		}
		cnt = 1;
		Arrays.fill(head, 1, n + 1, 0);
	}

	public static void addEdge(int u, int v) {
		next[cnt] = head[u];
		to[cnt] = v;
		head[u] = cnt++;
	}

	public static void dfs(int u, int fa) {
		size[u] = 1;
		belong[u][arr[u]] = 1;
		dp[u][0] = 1;
		for (int e = head[u], v; e > 0; e = next[e]) {
			v = to[e];
			if (v != fa) {
				dfs(v, u);
				Arrays.fill(tmp, 0, Math.min(size[u] + size[v], m) + 1, 0);
				for (int i = 0; i <= Math.min(size[u], m); i++) {
					for (int j = 0; j <= Math.min(size[v], m - i); j++) {
						tmp[i + j] = (tmp[i + j] + dp[u][i] * dp[v][j] % MOD) % MOD;
					}
				}
				size[u] += size[v];
				belong[u][0] += belong[v][0];
				belong[u][1] += belong[v][1];
				for (int i = 0; i <= Math.min(size[u], m); i++) {
					dp[u][i] = tmp[i];
				}
			}
		}
		for (int i = belong[u][arr[u] ^ 1]; i >= 0; i--) {
			dp[u][i + 1] = (dp[u][i + 1] + dp[u][i] * (belong[u][arr[u] ^ 1] - i) % MOD) % MOD;
		}
	}

	public static void compute() {
		dfs(1, 0);
		for (int i = 0; i <= m; i++) {
			g[i] = dp[1][i] * fac[m - i] % MOD;
		}
		for (int i = 0; i <= m; i++) {
			for (int j = i; j <= m; j++) {
				if ((j - i) % 2 == 1) {
					f[i] = (f[i] - c[j][i] * g[j] % MOD + MOD) % MOD;
				} else {
					f[i] = (f[i] + c[j][i] * g[j] % MOD) % MOD;
				}
			}
		}
	}

	public static void main(String[] args) {
		Kattio io = new Kattio();
		n = io.nextInt();
		m = n >> 1;
		build();
		String str = io.next();
		for (int i = 1; i <= n; i++) {
			arr[i] = str.charAt(i - 1) - '0';
		}
		for (int i = 1, u, v; i < n; i++) {
			u = io.nextInt();
			v = io.nextInt();
			addEdge(u, v);
			addEdge(v, u);
		}
		compute();
		for (int i = 0; i <= m; i++) {
			io.println(f[i]);
		}
		io.flush();
		io.close();
	}

	// Kattio类IO效率很好，但还是不如StreamTokenizer
	// 只有StreamTokenizer无法正确处理时，才考虑使用这个类
	// 参考链接 : https://oi-wiki.org/lang/java-pro/
	public static class Kattio extends PrintWriter {
		private BufferedReader r;
		private StringTokenizer st;

		public Kattio() {
			this(System.in, System.out);
		}

		public Kattio(InputStream i, OutputStream o) {
			super(o);
			r = new BufferedReader(new InputStreamReader(i));
		}

		public Kattio(String intput, String output) throws IOException {
			super(output);
			r = new BufferedReader(new FileReader(intput));
		}

		public String next() {
			try {
				while (st == null || !st.hasMoreTokens())
					st = new StringTokenizer(r.readLine());
				return st.nextToken();
			} catch (Exception e) {
			}
			return null;
		}

		public int nextInt() {
			return Integer.parseInt(next());
		}

		public double nextDouble() {
			return Double.parseDouble(next());
		}

		public long nextLong() {
			return Long.parseLong(next());
		}
	}

}