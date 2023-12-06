package class082;

// 买卖股票的最佳时机 III
// 给定一个数组，它的第 i 个元素是一支给定的股票在第 i 天的价格。
// 设计一个算法来计算你所能获取的最大利润。你最多可以完成 两笔 交易
// 注意：你不能同时参与多笔交易，你必须在再次购买前出售掉之前的股票
// 测试链接 : https://leetcode.cn/problems/best-time-to-buy-and-sell-stock-iii
public class Code03_Stock3 {

	// 完全不优化枚举的方法
	// 通过不了，会超时
	public static int maxProfit1(int[] prices) {
		int n = prices.length;
		// dp1[i] : 0...i范围上发生一次交易，不要求在i的时刻卖出，最大利润是多少
		int[] dp1 = new int[n];
		for (int i = 1, min = prices[0]; i < n; i++) {
			min = Math.min(min, prices[i]);
			dp1[i] = Math.max(dp1[i - 1], prices[i] - min);
		}
		// dp2[i] : 0...i范围上发生两次交易，并且第二次交易在i时刻卖出，最大利润是多少
		int[] dp2 = new int[n];
		int ans = 0;
		for (int i = 1; i < n; i++) {
			// 第二次交易一定要在i时刻卖出
			for (int j = 0; j <= i; j++) {
				// 枚举第二次交易的买入时机j <= i
				dp2[i] = Math.max(dp2[i], dp1[j] + prices[i] - prices[j]);
			}
			ans = Math.max(ans, dp2[i]);
		}
		return ans;
	}

	// 观察出优化枚举的方法
	// 引入best数组，需要分析能力
	public static int maxProfit2(int[] prices) {
		int n = prices.length;
		// dp1[i] : 0...i范围上发生一次交易，不要求在i的时刻卖出，最大利润是多少
		int[] dp1 = new int[n];
		for (int i = 1, min = prices[0]; i < n; i++) {
			min = Math.min(min, prices[i]);
			dp1[i] = Math.max(dp1[i - 1], prices[i] - min);
		}
		// best[i] : 0...i范围上，所有的dp1[i]-prices[i]，最大值是多少
		// 这是数组的设置完全是为了替代最后for循环的枚举行为
		int[] best = new int[n];
		best[0] = dp1[0] - prices[0];
		for (int i = 1; i < n; i++) {
			best[i] = Math.max(best[i - 1], dp1[i] - prices[i]);
		}
		// dp2[i] : 0...i范围上发生两次交易，并且第二次交易在i时刻卖出，最大利润是多少
		int[] dp2 = new int[n];
		int ans = 0;
		for (int i = 1; i < n; i++) {
			// 不需要枚举了
			// 因为，best[i]已经揭示了，0...i范围上，所有的dp1[i]-prices[i]，最大值是多少
			dp2[i] = best[i] + prices[i];
			ans = Math.max(ans, dp2[i]);
		}
		return ans;
	}

	// 发现所有更新行为都可以放在一起
	// 并不需要写多个并列的for循环
	// 就是等义改写，不需要分析能力
	public static int maxProfit3(int[] prices) {
		int n = prices.length;
		int[] dp1 = new int[n];
		int[] best = new int[n];
		best[0] = -prices[0];
		int[] dp2 = new int[n];
		int ans = 0;
		for (int i = 1, min = prices[0]; i < n; i++) {
			min = Math.min(min, prices[i]);
			dp1[i] = Math.max(dp1[i - 1], prices[i] - min);
			best[i] = Math.max(best[i - 1], dp1[i] - prices[i]);
			dp2[i] = best[i] + prices[i];
			ans = Math.max(ans, dp2[i]);
		}
		return ans;
	}

	// 发现只需要有限几个变量滚动更新下去就可以了
	// 空间压缩的版本
	// 就是等义改写，不需要分析能力
	public static int maxProfit4(int[] prices) {
		int dp1 = 0;
		int best = -prices[0];
		int ans = 0;
		for (int i = 1, min = prices[0]; i < prices.length; i++) {
			min = Math.min(min, prices[i]);
			dp1 = Math.max(dp1, prices[i] - min);
			best = Math.max(best, dp1 - prices[i]);
			ans = Math.max(ans, best + prices[i]); // ans = Math.max(ans, dp2);
		}
		return ans;
	}

}
