package class089;

import java.util.HashMap;

// 吃掉N个橘子的最少天数
// 厨房里总共有 n 个橘子，你决定每一天选择如下方式之一吃这些橘子
// 1）吃掉一个橘子
// 2) 如果剩余橘子数 n 能被 2 整除，那么你可以吃掉 n/2 个橘子
// 3) 如果剩余橘子数 n 能被 3 整除，那么你可以吃掉 2*(n/3) 个橘子
// 每天你只能从以上 3 种方案中选择一种方案
// 请你返回吃掉所有 n 个橘子的最少天数
// 测试链接 : https://leetcode.cn/problems/minimum-number-of-days-to-eat-n-oranges/
public class Code03_MinimumNumberEatOranges {

	// 所有的答案都填在这个表里
	// 这个表对所有的过程共用
	public static HashMap<Integer, Integer> dp = new HashMap<>();

	public static int minDays(int n) {
		if (n <= 1) {
			return n;
		}
		if (dp.containsKey(n)) {
			return dp.get(n);
		}
		// 1) 吃掉一个橘子
		// 2) 如果n能被2整除，吃掉一半的橘子，剩下一半
		// 3) 如果n能被3正数，吃掉三分之二的橘子，剩下三分之一
		// 因为方法2）和3），是按比例吃橘子，所以必然会非常快
		// 所以，决策如下：
		// 可能性1：为了使用2）方法，先把橘子吃成2的整数倍，然后直接干掉一半，剩下的n/2调用递归
		// 即，n % 2 + 1 + minDays(n/2)
		// 可能性2：为了使用3）方法，先把橘子吃成3的整数倍，然后直接干掉三分之二，剩下的n/3调用递归
		// 即，n % 3 + 1 + minDays(n/3)
		// 至于方法1)，完全是为了这两种可能性服务的，因为能按比例吃，肯定比一个一个吃快(显而易见的贪心)
		int ans = Math.min(n % 2 + 1 + minDays(n / 2), n % 3 + 1 + minDays(n / 3));
		dp.put(n, ans);
		return ans;
	}

}
