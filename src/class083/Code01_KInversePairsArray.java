package class083;

// K个逆序对数组
// 逆序对的定义如下：
// 对于数组nums的第i个和第j个元素
// 如果满足0<=i<j<nums.length 且 nums[i]>nums[j]，则为一个逆序对
// 给你两个整数n和k，找出所有包含从1到n的数字
// 且恰好拥有k个逆序对的不同的数组的个数
// 由于答案可能很大，只需要返回对10^9+7取余的结果
// 测试链接 : https://leetcode.cn/problems/k-inverse-pairs-array/
public class Code01_KInversePairsArray {

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
		for (int i = 1; i <= n; i++) {
			dp[i][0] = 1;
			for (int j = 1; j <= k; j++) {
				dp[i][j] = (dp[i][j - 1] + dp[i - 1][j]) % mod;
				if (j >= i) {
					dp[i][j] = (dp[i][j] - dp[i - 1][j - i] + mod) % mod;
				}
			}
		}
		return dp[n][k];
	}

	// 和方法2一样的思路，只不过少做了一些数组寻址而已
	// 真正考试时，不需要做这种常数优化，徒增烦恼
	public static int kInversePairs3(int n, int k) {
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
