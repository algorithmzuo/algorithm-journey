package class070;

// 打家劫舍
// 你是一个专业的小偷，计划偷窃沿街的房屋
// 每间房内都藏有一定的现金，影响你偷窃的唯一制约因素就是相邻的房屋装有相互连通的防盗系统
// 如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警
// 给定一个代表每个房屋存放金额的非负整数数组
// 计算你 不触动警报装置的情况下 ，一夜之内能够偷窃到的最高金额
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
