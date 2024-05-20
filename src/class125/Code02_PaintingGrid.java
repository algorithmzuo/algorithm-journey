package class125;

// 测试链接 : https://leetcode.cn/problems/painting-a-grid-with-three-different-colors/
public class Code02_PaintingGrid {

	public static final int mod = 1000000007;

	public static int colorTheGrid(int m, int n) {
		int status = 1 << (m << 1);
		int[][][] dp = new int[n][m][status];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				for (int s = 0; s < status; s++) {
					dp[i][j][s] = -1;
				}
			}
		}
		return dp(0, 0, 0, n, m, dp);
	}

	public static int dp(int i, int j, int s, int n, int m, int[][][] dp) {
		if (i == n) {
			return 1;
		}
		if (j == m) {
			return dp(i + 1, 0, s, n, m, dp);
		}
		if (dp[i][j][s] != -1) {
			return dp[i][j][s];
		}
		int up = (s >> (j * 2)) & 3;
		int left = j == 0 ? 0 : ((s >> ((j - 1) << 1)) & 3);
		int ans = 0;
		if (up != 1 && left != 1) {
			ans += dp(i, j + 1, (s ^ (up << (j << 1))) | (1 << (j << 1)), n, m, dp);
			ans %= mod;
		}
		if (up != 2 && left != 2) {
			ans += dp(i, j + 1, (s ^ (up << (j << 1))) | (2 << (j << 1)), n, m, dp);
			ans %= mod;
		}
		if (up != 3 && left != 3) {
			ans += dp(i, j + 1, (s ^ (up << (j << 1))) | (3 << (j << 1)), n, m, dp);
			ans %= mod;
		}
		dp[i][j][s] = ans;
		return ans;
	}

}
