package class082;

// 买卖股票的最佳时机含手续费
// 给定一个整数数组 prices，其中 prices[i]表示第 i 天的股票价格
// 整数 fee 代表了交易股票的手续费用
// 你可以无限次地完成交易，但是你每笔交易都需要付手续费
// 如果你已经购买了一个股票，在卖出它之前你就不能再继续购买股票了。
// 返回获得利润的最大值
// 注意：这里的一笔交易指买入持有并卖出股票的整个过程，每笔交易你只需要为支付一次手续费
// 测试链接 : https://leetcode.cn/problems/best-time-to-buy-and-sell-stock-with-transaction-fee/
public class Code05_StackV {

	public static int maxProfit(int[] arr, int fee) {
		int bestBuy = -arr[0] - fee;
		int bestSell = 0;
		for (int i = 1, curBuy, curSell; i < arr.length; i++) {
			curBuy = bestSell - arr[i] - fee;
			curSell = bestBuy + arr[i];
			bestBuy = Math.max(bestBuy, curBuy);
			bestSell = Math.max(bestSell, curSell);
		}
		return bestSell;
	}

}
