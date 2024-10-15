package class127;

// 摆盘子的方法
// 一共有n个盘子k种菜，所有盘子排成一排，每个盘子只能放一种菜
// 要求最多连续两个盘子菜品一样，更长的重复出现是不允许的
// 返回摆盘的方法数，答案对 1000000007 取模
// 1 <= n <= 1000
// 1 <= k <= 1000
// 来自真实大厂笔试，对数器验证
public class Code05_WaysOfArrangePlates {

	public static int MOD = 1000000007;

	// 正式方法的尝试思路
	public static int dp1(int n, int k) {
		if (n == 1) {
			return k;
		}
		return (int) ((((long) f1(n - 1, k) + f1(n - 2, k)) * k) % MOD);
	}

	public static int f1(int i, int k) {
		if (i == 0) {
			return 1;
		}
		if (i == 1) {
			return k - 1;
		}
		long p1 = f1(i - 1, k);
		long p2 = f1(i - 2, k);
		return (int) (((p1 + p2) * (k - 1)) % MOD);
	}

	// 正式方法的普通动态规划版本
	// 时间复杂度O(n)
	public static int dp2(int n, int k) {
		if (n == 1) {
			return k;
		}
		int[] dp = new int[n];
		dp[0] = 1;
		dp[1] = k - 1;
		for (int i = 2; i < n; i++) {
			long p1 = dp[i - 1];
			long p2 = dp[i - 2];
			dp[i] = (int) (((p1 + p2) * (k - 1)) % MOD);
		}
		return (int) ((((long) dp[n - 1] + dp[n - 2]) * k) % MOD);
	}

	// 最优解的版本，动态规划 + 矩阵快速幂优化
	// 时间复杂度O(log n)
	// 不会的同学看讲解098
	public static int dp3(int n, int k) {
		if (n == 1) {
			return k;
		}
		int s = k - 1;
		int[][] start = { { 1, s } };
		int[][] base = { { 0, s }, { 1, s } };
		int[][] ans = multiply(start, power(base, n - 2));
		return (int) ((((long) ans[0][0] + ans[0][1]) * k) % MOD);
	}

	public static int[][] multiply(int[][] a, int[][] b) {
		int n = a.length;
		int m = b[0].length;
		int k = a[0].length;
		int[][] ans = new int[n][m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				for (int c = 0; c < k; c++) {
					ans[i][j] = (int) (((long) ans[i][j] + (long) a[i][c] * b[c][j]) % MOD);
				}
			}
		}
		return ans;
	}

	public static int[][] power(int[][] m, int p) {
		int n = m.length;
		int[][] ans = new int[n][n];
		for (int i = 0; i < n; i++) {
			ans[i][i] = 1;
		}
		for (; p != 0; p >>= 1) {
			if ((p & 1) != 0) {
				ans = multiply(ans, m);
			}
			m = multiply(m, m);
		}
		return ans;
	}

	// 暴力方法
	// 为了验证
	public static int right(int n, int k) {
		int[] path = new int[n];
		return dfs(path, 0, k);
	}

	// 暴力方法
	// 为了验证
	public static int dfs(int[] path, int i, int k) {
		if (i == path.length) {
			int len = 1;
			for (int j = 1; j < path.length; j++) {
				if (path[j - 1] == path[j]) {
					len++;
				} else {
					len = 1;
				}
				if (len > 2) {
					return 0;
				}
			}
			return len > 2 ? 0 : 1;
		} else {
			int ans = 0;
			for (int j = 0; j < k; j++) {
				path[i] = j;
				ans += dfs(path, i + 1, k);
			}
			return ans;
		}
	}

	// 对数器
	// 为了验证
	public static void main(String[] args) {
		System.out.println("功能测试开始");
		for (int n = 1; n <= 8; n++) {
			for (int k = 1; k <= 8; k++) {
				int ans1 = dp1(n, k);
				int ans2 = dp2(n, k);
				int ans3 = dp3(n, k);
				int ans4 = right(n, k);
				if (ans1 != ans2 || ans1 != ans3 || ans1 != ans4) {
					System.out.println("出错了!");
				}
			}
		}
		System.out.println("功能测试结束");
		System.out.println();

		System.out.println("性能测试开始");
		int n = 505060123;
		int k = 303060123;
		System.out.println("数据量 : n = " + n + ", k = " + k);

		long start, end;
		start = System.currentTimeMillis();
		System.out.println("dp2方法结果(已经取模) : " + dp2(n, k));
		end = System.currentTimeMillis();
		System.out.println("dp2方法用时(毫秒) : " + (end - start));

		start = System.currentTimeMillis();
		System.out.println("dp3方法结果(已经取模) : " + dp3(n, k));
		end = System.currentTimeMillis();
		System.out.println("dp3方法用时(毫秒) : " + (end - start));
		System.out.println("性能测试结束");
	}

}
