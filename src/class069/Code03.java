package class069;

// 测试链接 : https://leetcode.cn/problems/knight-probability-in-chessboard/
public class Code03 {
	
	public static double knightProbability(int n, int k, int row, int column) {
		double[][][] dp = new double[n][n][k + 1];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				for (int t = 0; t <= k; t++) {
					dp[i][j][t] = -1;
				}
			}
		}
		return f(n, k, row, column, dp);
	}

	// (r,c) , 还剩rest步去走
	// 走的过程中，出棋盘就算死，走完之后还活的概率
	public static double f(int n, int rest, int r, int c, double[][][] dp) {
		if (r < 0 || r >= n || c < 0 || c >= n) {
			return 0;
		}
		if (dp[r][c][rest] != -1) {
			return dp[r][c][rest];
		}
		double ans = 0;
		if (rest == 0) {
			ans = 1;
		} else {
			ans += (f(n, rest - 1, r - 2, c + 1, dp) / 8);
			ans += (f(n, rest - 1, r - 1, c + 2, dp) / 8);
			ans += (f(n, rest - 1, r + 1, c + 2, dp) / 8);
			ans += (f(n, rest - 1, r + 2, c + 1, dp) / 8);
			ans += (f(n, rest - 1, r + 2, c - 1, dp) / 8);
			ans += (f(n, rest - 1, r + 1, c - 2, dp) / 8);
			ans += (f(n, rest - 1, r - 1, c - 2, dp) / 8);
			ans += (f(n, rest - 1, r - 2, c - 1, dp) / 8);
		}
		dp[r][c][rest] = ans;
		return ans;
	}

}
