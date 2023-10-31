package class069;

// 矩阵中和能被 K 整除的路径
// 给一个下标从0开始的 n * m 整数矩阵 grid 和一个整数 k
// 从起点(0,0)出发，每步只能往下或者往右，你想要到达终点(m-1, n-1)
// 请你返回路径和能被 k 整除的路径数目
// 由于答案可能很大，返回答案对10^9+7取余的结果
// 测试链接 : https://leetcode.cn/problems/paths-in-matrix-whose-sum-is-divisible-by-k/
public class Code04_PathsDivisibleByK {

	public static int mod = 1000000007;

	public static int numberOfPaths1(int[][] grid, int k) {
		int n = grid.length;
		int m = grid[0].length;
		return f1(grid, n, m, k, 0, 0, 0);
	}

	// 当前来到(i,j)位置，最终一定要走到右下角(n-1,m-1)
	// 从(i,j)出发，最终一定要走到右下角(n-1,m-1)，有多少条路径，累加和%k的余数是r
	public static int f1(int[][] grid, int n, int m, int k, int i, int j, int r) {
		if (i == n - 1 && j == m - 1) {
			return grid[i][j] % k == r ? 1 : 0;
		}
		// 后续需要凑出来的余数need
 		int need = (k + r - (grid[i][j] % k)) % k;
		int ans = 0;
		if (i + 1 < n) {
			ans = f1(grid, n, m, k, i + 1, j, need);
		}
		if (j + 1 < m) {
			ans = (ans + f1(grid, n, m, k, i, j + 1, need)) % mod;
		}
		return ans;
	}

	public static int numberOfPaths2(int[][] grid, int k) {
		int n = grid.length;
		int m = grid[0].length;
		int[][][] dp = new int[n][m][k];
		for (int a = 0; a < n; a++) {
			for (int b = 0; b < m; b++) {
				for (int c = 0; c < k; c++) {
					dp[a][b][c] = -1;
				}
			}
		}
		return f2(grid, n, m, k, 0, 0, 0, dp);
	}

	public static int f2(int[][] grid, int n, int m, int k, int i, int j, int r, int[][][] dp) {
		if (i == n - 1 && j == m - 1) {
			return grid[i][j] % k == r ? 1 : 0;
		}
		if (dp[i][j][r] != -1) {
			return dp[i][j][r];
		}
		int need = (k + r - grid[i][j] % k) % k;
		int ans = 0;
		if (i + 1 < n) {
			ans = f2(grid, n, m, k, i + 1, j, need, dp);
		}
		if (j + 1 < m) {
			ans = (ans + f2(grid, n, m, k, i, j + 1, need, dp)) % mod;
		}
		dp[i][j][r] = ans;
		return ans;
	}

	public static int numberOfPaths3(int[][] grid, int k) {
		int n = grid.length;
		int m = grid[0].length;
		int[][][] dp = new int[n][m][k];
		dp[n - 1][m - 1][grid[n - 1][m - 1] % k] = 1;
		for (int i = n - 2; i >= 0; i--) {
			for (int r = 0; r < k; r++) {
				dp[i][m - 1][r] = dp[i + 1][m - 1][(k + r - grid[i][m - 1] % k) % k];
			}
		}
		for (int j = m - 2; j >= 0; j--) {
			for (int r = 0; r < k; r++) {
				dp[n - 1][j][r] = dp[n - 1][j + 1][(k + r - grid[n - 1][j] % k) % k];
			}
		}
		for (int i = n - 2, need; i >= 0; i--) {
			for (int j = m - 2; j >= 0; j--) {
				for (int r = 0; r < k; r++) {
					need = (k + r - grid[i][j] % k) % k;
					dp[i][j][r] = dp[i + 1][j][need];
					dp[i][j][r] = (dp[i][j][r] + dp[i][j + 1][need]) % mod;
				}
			}
		}
		return dp[0][0][0];
	}

}
