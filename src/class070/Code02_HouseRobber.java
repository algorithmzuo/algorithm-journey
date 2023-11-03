package class070;

// 数组中不能选相邻元素的最大累加和
// 给定一个数组，可以随意选择数字
// 但是不能选择相邻的数字，返回能得到的最大累加和
// 测试链接 : https://leetcode.cn/problems/house-robber/
public class Code02_HouseRobber {

	public static int rob1(int[] nums) {
		int n = nums.length;
		if (n == 1) {
			return nums[0];
		}
		if (n == 2) {
			return Math.max(nums[0], nums[1]);
		}
		int[] dp = new int[n];
		dp[0] = nums[0];
		dp[1] = Math.max(nums[0], nums[1]);
		for (int i = 2; i < n; i++) {
			dp[i] = Math.max(dp[i - 1], nums[i] + Math.max(0, dp[i - 2]));
		}
		return dp[n - 1];
	}

	public static int rob2(int[] nums) {
		int n = nums.length;
		if (n == 1) {
			return nums[0];
		}
		if (n == 2) {
			return Math.max(nums[0], nums[1]);
		}
		int lastLast = nums[0];
		int last = Math.max(nums[0], nums[1]);
		for (int i = 2, cur; i < n; i++) {
			cur = Math.max(last, nums[i] + Math.max(0, lastLast));
			lastLast = last;
			last = cur;
		}
		return last;
	}

}
