package class145;

// 游戏(递归版)
// 一共有n个节点，n <= 5000，n为偶数，其中有m个点属于小A，有m个点属于小B，m为n的一半
// 给定n-1条边，节点之间组成一颗树，1号节点是根节点
// 给定长度为n的数组arr，arr[i]的值表示i号节点由谁拥有，0为小A拥有，1为小B拥有
// 游戏有m回合，每回合都有胜负，两人需要选择一个自己拥有、但之前没选过的点，作为本回合当前点
// 小A当前点的子树里有小B当前点，则小A胜；小B当前点的子树里有小A当前点，则小B胜；否则平局
// 返回m回合里能出现k次非平局的游戏方法数，打印k=0..m时的所有答案，对 998244353 取模
// 两场游戏视为不同的定义：当且仅当存在小A拥有的点x，小B在小A选择x的那个回合所选择的点不同
// 测试链接 : https://www.luogu.com.cn/problem/P6478
// 提交以下的code，提交时请把类名改成"Main"，注意dfs是递归函数
// C++的同学可以全部通过，java的同学有时可以全部通过，有时因为递归展开太深而爆栈
// dfs从递归版改迭代版的实现，请看Code05_Game2文件

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Code05_Game1 {

	public static final int MAXN = 5001;

	public static final int MOD = 998244353;

	public static int n, m;

	public static int[] arr = new int[MAXN];

	public static long[] fac = new long[MAXN];

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

	public static long[] backup = new long[MAXN];

	// 反演需要
	public static long[] g = new long[MAXN];

	// 最后答案
	public static long[] f = new long[MAXN];

	public static void build() {
		cnt = 1;
		fac[0] = 1;
		for (int i = 1; i <= n; i++) {
			head[i] = 0;
			fac[i] = fac[i - 1] * i % MOD;
		}
		for (int i = 0; i <= n; i++) {
			c[i][0] = 1;
			for (int j = 1; j <= i; j++) {
				c[i][j] = (c[i - 1][j] + c[i - 1][j - 1]) % MOD;
			}
		}
	}

	public static void addEdge(int u, int v) {
		next[cnt] = head[u];
		to[cnt] = v;
		head[u] = cnt++;
	}

	// 递归版
	public static void dfs(int u, int fa) {
		size[u] = 1;
		belong[u][arr[u]] = 1;
		dp[u][0] = 1;
		// 首先计算不包含头节点的方法数
		for (int e = head[u], v; e > 0; e = next[e]) {
			v = to[e];
			if (v != fa) {
				dfs(v, u);
				// 之前所有子树结合的计算结果，拷贝进backup
				for (int i = 0; i <= Math.min(size[u] / 2, m); i++) {
					backup[i] = dp[u][i];
					dp[u][i] = 0;
				}
				// 树型dp的枚举行为利用子树的节点数做上限进行复杂度优化
				for (int l = 0; l <= Math.min(size[u] / 2, m); l++) {
					for (int r = 0; r <= Math.min(size[v] / 2, m - l); r++) {
						dp[u][l + r] = (dp[u][l + r] + backup[l] * dp[v][r] % MOD) % MOD;
					}
				}
				size[u] += size[v];
				belong[u][0] += belong[v][0];
				belong[u][1] += belong[v][1];
			}
		}
		// 最后计算包含头节点的方法数
		// 更新dp[u][i]，i可以把上限定为min(对手拥有的节点数，m)
		// u为头的子树中，对手有几个节点
		int num = belong[u][arr[u] ^ 1];
		// 不包含头节点的方法数，拷贝到backup
		for (int i = 1; i <= Math.min(num, m); i++) {
			backup[i] = dp[u][i];
		}
		// 计算包含头节点的方法数，累加上
		for (int i = 1; i <= Math.min(num, m); i++) {
			dp[u][i] = (dp[u][i] + backup[i - 1] * (num - i + 1) % MOD) % MOD;
		}
	}

	public static void compute() {
		dfs(1, 0); // dfs是递归版
		for (int i = 0; i <= m; i++) {
			g[i] = dp[1][i] * fac[m - i] % MOD;
		}
		for (int k = 0; k <= m; k++) {
			for (int i = k; i <= m; i++) {
				if (((i - k) & 1) == 0) {
					f[k] = (f[k] + c[i][k] * g[i] % MOD) % MOD;
				} else {
					// -1 和 (MOD-1) 同余
					f[k] = (f[k] + c[i][k] * g[i] % MOD * (MOD - 1) % MOD) % MOD;
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
		for (int k = 0; k <= m; k++) {
			io.println(f[k]);
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