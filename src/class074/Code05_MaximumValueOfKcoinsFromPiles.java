package class074;

import java.util.List;

// 从栈中取出K个硬币的最大面值和
// 一张桌子上总共有 n 个硬币 栈 。每个栈有 正整数 个带面值的硬币
// 每一次操作中，你可以从任意一个栈的 顶部 取出 1 个硬币，从栈中移除它，并放入你的钱包里
// 给你一个列表 piles ，其中 piles[i] 是一个整数数组
// 分别表示第 i 个栈里 从顶到底 的硬币面值。同时给你一个正整数 k
// 请你返回在 恰好 进行 k 次操作的前提下，你钱包里硬币面值之和 最大为多少
// 测试链接 : https://leetcode.cn/problems/maximum-value-of-k-coins-from-piles/
public class Code05_MaximumValueOfKcoinsFromPiles {

	public static int maxValueOfCoins(List<List<Integer>> piles, int k) {
		int[] dp = new int[k + 1];
		for (List<Integer> stack : piles) {
			int n = Math.min(stack.size(), k);
			int[] preSum = new int[n + 1];
			for (int i = 0, sum = 0; i < n; i++) {
				sum += stack.get(i);
				preSum[i + 1] = sum;
			}
			for (int w = k; w > 0; w--) {
				for (int i = 1; i <= Math.min(n, w); i++) {
					dp[w] = Math.max(dp[w], preSum[i] + dp[w - i]);
				}
			}
		}
		return dp[k];
	}

}
