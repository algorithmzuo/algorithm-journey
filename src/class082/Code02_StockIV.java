package class082;

// 买卖股票的最佳时机IV
// 给你一个整数数组prices和一个整数k
// 其中prices[i]是某支给定的股票在第i天的价格
// 设计一个算法来计算你所能获取的最大利润
// 你最多可以完成k笔交易，但是每次只能买1股
// 注意：你不能同时参与多笔交易，你必须在再次购买前出售掉之前的那一股
// 测试链接 : https://leetcode.cn/problems/best-time-to-buy-and-sell-stock-iv/
public class Code02_StockIV {

	// 就是股票问题2
	// 测试链接 : https://leetcode.cn/problems/best-time-to-buy-and-sell-stock-ii/
	public static int maxProfit(int[] prices) {
		int ans = 0;
		for (int i = 1; i < prices.length; i++) {
			ans += Math.max(prices[i] - prices[i - 1], 0);
		}
		return ans;
	}

	public static int maxProfit1(int k, int[] prices) {
		int n = prices.length;
		if (k >= n / 2) {
			return maxProfit(prices);
		}
		int[][] dp = new int[k + 1][n];
		for (int i = 1; i <= k; i++) {
			for (int j = 1; j < n; j++) {
				dp[i][j] = dp[i][j - 1];
				for (int p = 0; p < j; p++) {
					dp[i][j] = Math.max(dp[i][j], dp[i - 1][p] + prices[j] - prices[p]);
				}
			}
		}
		return dp[k][n - 1];
	}

	public static int maxProfit2(int k, int[] prices) {
		int n = prices.length;
		if (k >= n / 2) {
			return maxProfit(prices);
		}
		int[][] dp = new int[k + 1][n];
		for (int i = 1, best; i <= k; i++) {
			best = dp[i - 1][0] - prices[0];
			for (int j = 1; j < n; j++) {
				dp[i][j] = Math.max(dp[i][j - 1], best + prices[j]);
				best = Math.max(best, dp[i - 1][j] - prices[j]);
			}
		}
		return dp[k][n - 1];
	}

	public static int maxProfit3(int k, int[] prices) {
		int n = prices.length;
		if (k >= n / 2) {
			return maxProfit(prices);
		}
		int[] dp = new int[n];
		for (int i = 1, best, tmp; i <= k; i++) {
			best = dp[0] - prices[0];
			for (int j = 1; j < n; j++) {
				tmp = dp[j];
				dp[j] = Math.max(dp[j - 1], best + prices[j]);
				best = Math.max(best, tmp - prices[j]);
			}
		}
		return dp[n - 1];
	}

}
