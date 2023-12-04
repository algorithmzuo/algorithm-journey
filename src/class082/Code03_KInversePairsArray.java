package class082;

// 测试链接 : https://leetcode.cn/problems/k-inverse-pairs-array/
public class Code03_KInversePairsArray {

	public static int kInversePairs1(int n, int k) {
		int mod = 1000000007;
		int[][] dp = new int[n + 1][k + 1];
		dp[0][0] = 1;
		for (int i = 1; i <= n; i++) {
			dp[i][0] = 1;
			for (int j = 1; j <= k; j++) {
				if (i > j) {
					for (int p = 0; p <= j; p++) {
						dp[i][j] = (dp[i][j] + dp[i - 1][p]) % mod;
					}
				} else {
					for (int p = j - i + 1; p <= j; p++) {
						dp[i][j] = (dp[i][j] + dp[i - 1][p]) % mod;
					}
				}
			}
		}
		return dp[n][k];
	}

	public static int kInversePairs2(int n, int k) {
		int mod = 1000000007;
		int[][] dp = new int[n + 1][k + 1];
		dp[0][0] = 1;
		for (int i = 1, pre; i <= n; i++) {
			dp[i][0] = 1;
			pre = 1;
			for (int j = 1; j <= k; j++) {
				pre = (pre + dp[i - 1][j]) % mod;
				if (j >= i) {
					pre = (pre - dp[i - 1][j - i] + mod) % mod;
				}
				dp[i][j] = pre;
			}
		}
		return dp[n][k];
	}

}
