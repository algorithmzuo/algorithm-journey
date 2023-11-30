package class081;

import java.util.Arrays;

// 分配重复整数
// 给你一个长度为n的整数数组nums，这个数组中至多有50个不同的值
// 同时你有m个顾客的订单quantity，其中整数quantity[i]是第i位顾客订单的数目
// 请你判断是否能将nums中的整数分配给这些顾客，且满足：
// 第i位顾客恰好有quantity[i]个整数、第i位顾客拿到的整数都是相同的
// 每位顾客都要满足上述两个要求，返回是否能都满足
// 测试链接 : https://leetcode.cn/problems/distribute-repeating-integers/
public class Code04_DistributeRepeatingIntegers {

	// 不能用贪心的例子 :
	// nums = [1,1,2,2,1]
	// quantity = [2,2,1]
	// 时间复杂度O(n * 3的m次方)，空间复杂度O(n * 2的m次方)
	// ppt上有时间复杂度解析
	// 带路径的递归（暴力枚举、回溯）的解法速度更快，但是并不推荐，不再讲述
	// 原因上节课说了（讲解080-状压dp-上，题目3）
	// 同样的数据量，如果出题人刻意构造严苛的数据状况，
	// 带路径的递归（暴力枚举、回溯）就无法通过了，但是状压dp一定可以通过
	public static boolean canDistribute(int[] nums, int[] quantity) {
		Arrays.sort(nums);
		int n = 1;
		for (int i = 1; i < nums.length; i++) {
			if (nums[i - 1] != nums[i]) {
				n++;
			}
		}
		int[] cnt = new int[n];
		int c = 1;
		for (int i = 1, j = 0; i < nums.length; i++) {
			if (nums[i - 1] != nums[i]) {
				cnt[j++] = c;
				c = 1;
			} else {
				c++;
			}
		}
		cnt[n - 1] = c;
		int m = 1 << quantity.length;
		int[] sum = new int[m];
		// 下面这个枚举是生成quantity中的每个子集，所需要数字的个数
		for (int i = 0, v, h; i < quantity.length; i++) {
			v = quantity[i];
			h = 1 << i;
			for (int j = 0; j < h; j++) {
				sum[h | j] = sum[j] + v;
			}
		}
		int[][] dp = new int[n][m];
		return f(cnt, sum, 0, m - 1, dp);
	}

	public static boolean f(int[] cnt, int[] sum, int i, int j, int[][] dp) {
		if (j == 0) {
			return true;
		}
		if (i == cnt.length) {
			return false;
		}
		if (dp[i][j] != 0) {
			return dp[i][j] == 1;
		}
		boolean ans = false;
		int c = cnt[i];
		// 这是整个实现最核心的枚举
		// s枚举了j的所有子集状态
		// 建议记住
		for (int s = j; s > 0; s = (s - 1) & j) {
			if (sum[s] <= c && f(cnt, sum, i + 1, j ^ s, dp)) {
				ans = true;
				break;
			}
		}
		if (!ans) {
			ans = f(cnt, sum, i + 1, j, dp);
		}
		dp[i][j] = ans ? 1 : -1;
		return ans;
	}

}
