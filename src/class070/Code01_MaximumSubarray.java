package class070;

// 子数组最大累加和
// 给你一个整数数组 nums
// 返回非空子数组的最大累加和
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

	// 如下代码为扩展问题
	// 子数组中找到拥有最大累加和的子数组
	// 并返回如下三个信息:
	// 1) 最大累加和子数组的开头left
	// 2) 最大累加和子数组的结尾right
	// 3) 最大累加和子数组的累加和sum
	public static int left;

	public static int right;

	public static int sum;

	public static void maxSubArray3(int[] nums) {
		left = right = 0;
		sum = nums[0];
		for (int i = 1, l = 0, pre = nums[0]; i < nums.length; i++) {
			if (pre >= 0) {
				pre += nums[i];
			} else {
				pre = nums[i];
				l = i;
			}
			if (pre > sum) {
				sum = pre;
				left = l;
				right = i;
			}
		}
	}

}
