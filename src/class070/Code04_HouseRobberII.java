package class070;

// 环形数组中不能选相邻元素的最大累加和
// 给定一个数组nums，长度为n
// nums是一个环形数组，下标0和下标n-1是连在一起的
// 可以随意选择数字，但是不能选择相邻的数字
// 返回能得到的最大累加和
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
