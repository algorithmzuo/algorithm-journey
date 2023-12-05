package class082;

// 买卖股票的最佳时机 III
// 给定一个数组，它的第 i 个元素是一支给定的股票在第 i 天的价格。
// 设计一个算法来计算你所能获取的最大利润。你最多可以完成 两笔 交易
// 注意：你不能同时参与多笔交易，你必须在再次购买前出售掉之前的股票
// 测试链接 : https://leetcode.cn/problems/best-time-to-buy-and-sell-stock-iii
public class Code03_Stock3 {

	public static int maxProfit(int[] prices) {
		int finishOnce = 0;
		int finishOnceMinusBuy = -prices[0];
		int ans = 0, min = prices[0];
		for (int i = 1; i < prices.length; i++) {
			min = Math.min(min, prices[i]);
			ans = Math.max(ans, finishOnceMinusBuy + prices[i]);
			finishOnce = Math.max(finishOnce, prices[i] - min);
			finishOnceMinusBuy = Math.max(finishOnceMinusBuy, finishOnce - prices[i]);
		}
		return ans;
	}

}
