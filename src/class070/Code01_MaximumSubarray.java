package class070;

// 累加和最大子数组和
// 给你一个整数数组 nums
// 请你找出一个具有最大累加和的非空子数组
// 返回其最大累加和
// 测试链接 : https://leetcode.cn/problems/maximum-subarray/
public class Code01_MaximumSubarray {

	public static int maxSubArray1(int[] nums) {
		int n = nums.length;
		int[] dp = new int[n];
		dp[0] = nums[0];
		int ans = nums[0];
		for (int i = 1; i < n; i++) {
			dp[i] = nums[i] + Math.max(0, dp[i - 1]);
			ans = Math.max(ans, dp[i]);
		}
		return ans;
	}

	public static int maxSubArray2(int[] nums) {
		int n = nums.length;
		int ans = nums[0];
		for (int i = 1, pre = nums[0]; i < n; i++) {
			pre = Math.max(nums[i], nums[i] + pre);
			ans = Math.max(ans, pre);
		}
		return ans;
	}

}
