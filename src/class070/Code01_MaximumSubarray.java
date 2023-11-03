package class070;

// 子数组最大累加和
// 给你一个整数数组 nums
// 返回非空子数组的最大累加和
// 测试链接 : https://leetcode.cn/problems/maximum-subarray/
public class Code01_MaximumSubarray {

	// 动态规划
	public static int maxSubArray1(int[] nums) {
		int n = nums.length;
		// dp[i] : 子数组必须以i位置的数做结尾，往左能延伸出来的最大累加和
		int[] dp = new int[n];
		dp[0] = nums[0];
		int ans = nums[0];
		for (int i = 1; i < n; i++) {
			dp[i] = Math.max(nums[i], dp[i - 1] + nums[i]);
			ans = Math.max(ans, dp[i]);
		}
		return ans;
	}

	// 空间压缩
	public static int maxSubArray2(int[] nums) {
		int n = nums.length;
		int ans = nums[0];
		for (int i = 1, pre = nums[0]; i < n; i++) {
			pre = Math.max(nums[i], pre + nums[i]);
			ans = Math.max(ans, pre);
		}
		return ans;
	}

	// 如下代码为附加问题的实现
	// 子数组中找到拥有最大累加和的子数组
	// 并返回如下三个信息:
	// 1) 最大累加和子数组的开头left
	// 2) 最大累加和子数组的结尾right
	// 3) 最大累加和子数组的累加和sum
	// 如果不止一个子数组拥有最大累加和，那么找到哪一个都可以
	public static int left;

	public static int right;

	public static int sum;

	// 找到拥有最大累加和的子数组
	// 更新好全局变量left、right、sum
	// 上游调用函数可以直接使用这三个变量
	// 相当于返回了三个值
	public static void extra(int[] nums) {
		sum = Integer.MIN_VALUE;
		for (int l = 0, r = 0, pre = Integer.MIN_VALUE; r < nums.length; r++) {
			if (pre >= 0) {
				// 吸收前面的累加和有利可图
				// 那就不换开头
				pre += nums[r];
			} else {
				// 吸收前面的累加和已经无利可图
				// 那就换开头
				pre = nums[r];
				l = r;
			}
			if (pre > sum) {
				sum = pre;
				left = l;
				right = r;
			}
		}
	}

}
