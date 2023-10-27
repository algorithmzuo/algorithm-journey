package class070;

// 打家劫舍 II
// 你是一个专业的小偷，计划偷窃沿街的房屋，每间房内都藏有一定的现金
// 这个地方所有的房屋都 围成一圈，这意味着第一个房屋和最后一个房屋是紧挨着的
// 相邻的房屋装有相互连通的防盗系统
// 如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警
// 给定一个代表每个房屋存放金额的非负整数数组
// 计算你 在不触动警报装置的情况下 ，今晚能够偷窃到的最高金额
// 测试链接 : https://leetcode.cn/problems/house-robber-ii/
public class Code04_HouseRobberII {

	public static int rob(int[] nums) {
		int n = nums.length;
		if (n == 1) {
			return nums[0];
		}
		return Math.max(best(nums, 1, n - 1), best(nums, 0, n - 2));
	}

	public static int best(int[] nums, int l, int r) {
		if (l == r) {
			return nums[l];
		}
		if (l + 1 == r) {
			return Math.max(nums[l], nums[r]);
		}
		int lastLast = nums[l];
		int last = Math.max(nums[l], nums[l + 1]);
		for (int i = l + 2, cur; i <= r; i++) {
			cur = Math.max(last, nums[i] + Math.max(0, lastLast));
			lastLast = last;
			last = cur;
		}
		return last;
	}

}
