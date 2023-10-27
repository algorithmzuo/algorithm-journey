package class070;

// 环形子数组的最大和
// 给定一个数组nums，长度为n
// 假设下标0和下标n-1是连接在一起的
// nums是一个环形数组
// 返回环形数组中子数组的最大累加和
// 测试链接 : https://leetcode.cn/problems/maximum-sum-circular-subarray/
public class Code03_MaximumSumCircularSubarray {

	public static int maxSubarraySumCircular(int[] nums) {
		int n = nums.length, sum = nums[0], maxsum = nums[0], minsum = nums[0];
		for (int i = 1, maxpre = nums[0], minpre = nums[0]; i < n; i++) {
			sum += nums[i];
			maxpre = Math.max(nums[i], nums[i] + maxpre);
			maxsum = Math.max(maxsum, maxpre);
			minpre = Math.min(nums[i], nums[i] + minpre);
			minsum = Math.min(minsum, minpre);
		}
		return sum == minsum ? maxsum : Math.max(maxsum, sum - minsum);
	}

}
