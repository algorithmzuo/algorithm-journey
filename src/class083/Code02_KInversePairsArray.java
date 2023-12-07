package class083;

// K个逆序对数组
// 逆序对的定义如下：
// 对于数组nums的第i个和第j个元素
// 如果满足0<=i<j<nums.length 且 nums[i]>nums[j]，则为一个逆序对
// 给你两个整数n和k，找出所有包含从1到n的数字
// 且恰好拥有k个逆序对的不同的数组的个数
// 由于答案可能很大，只需要返回对10^9+7取余的结果
// 测试链接 : https://leetcode.cn/problems/k-inverse-pairs-array/
public class Code02_KInversePairsArray {

	// 最普通的动态规划
	// 不优化枚举
	public static int kInversePairs1(int n, int k) {
		int mod = 1000000007;
		// dp[i][j] : 1、2、3...i这些数字，形成的排列一定要有j个逆序对，请问这样的排列有几种
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
					// i <= j
					for (int p = j - i + 1; p <= j; p++) {
						dp[i][j] = (dp[i][j] + dp[i - 1][p]) % mod;
					}
				}
			}
		}
		return dp[n][k];
	}

	// 根据观察方法1优化枚举
	// 最优解
	// 其实可以进一步空间压缩
	// 有兴趣的同学自己试试吧
	public static int kInversePairs2(int n, int k) {
		int mod = 1000000007;
		int[][] dp = new int[n + 1][k + 1];
		dp[0][0] = 1;
		// window : 窗口的累加和
		for (int i = 1, window; i <= n; i++) {
			dp[i][0] = 1;
			window = 1;
			for (int j = 1; j <= k; j++) {
				if (i > j) {
					window = (window + dp[i - 1][j]) % mod;
				} else {
					// i <= j
					window = ((window + dp[i - 1][j]) % mod - dp[i - 1][j - i] + mod) % mod;
				}
				dp[i][j] = window;
			}
		}
		return dp[n][k];
	}

}
