package class125;

// 需要改写成三进制状态压缩 + 轮廓线dp
// 测试链接 : https://leetcode.cn/problems/painting-a-grid-with-three-different-colors/
public class Code05_PaintingGrid {

	public static int MAXN = 1001;

	public static int MAXM = 5;

	public static int[][][] dp = new int[MAXN][MAXM][1 << (MAXM << 1)];

	public static int MOD = 1000000007;

	public static int n, m;

	public static int colorTheGrid(int cols, int rows) {
		m = cols;
		n = rows;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				for (int s = 0; s < 1 << (m << 1); s++) {
					dp[i][j][s] = -1;
				}
			}
		}
		return dp(0, 0, 0);
	}

	public static int dp(int i, int j, int s) {
		if (i == n) {
			return 1;
		}
		if (j == m) {
			return dp(i + 1, 0, s);
		}
		if (dp[i][j][s] != -1) {
			return dp[i][j][s];
		}
		int up = (s >> (j * 2)) & 3;
		int left = j == 0 ? 0 : ((s >> ((j - 1) << 1)) & 3);
		int ans = 0;
		if (up != 1 && left != 1) {
			ans += dp(i, j + 1, (s ^ (up << (j << 1))) | (1 << (j << 1)));
			ans %= MOD;
		}
		if (up != 2 && left != 2) {
			ans += dp(i, j + 1, (s ^ (up << (j << 1))) | (2 << (j << 1)));
			ans %= MOD;
		}
		if (up != 3 && left != 3) {
			ans += dp(i, j + 1, (s ^ (up << (j << 1))) | (3 << (j << 1)));
			ans %= MOD;
		}
		dp[i][j][s] = ans;
		return ans;
	}

}
