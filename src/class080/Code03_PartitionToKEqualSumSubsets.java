package class080;

import java.util.Arrays;

// 划分为k个相等的子集
// 给定一个整数数组  nums 和一个正整数 k，
// 找出是否有可能把这个数组分成 k 个非空子集，其总和都相等。
// 测试链接 : https://leetcode.cn/problems/partition-to-k-equal-sum-subsets/
public class Code03_PartitionToKEqualSumSubsets {

	// 状压dp的解法
	// 这是最正式的解
	public static boolean canPartitionKSubsets(int[] nums, int k) {
		int sum = 0;
		for (int num : nums) {
			sum += num;
		}
		if (sum % k != 0) {
			return false;
		}
		return f1(nums, 0, 0, 0, sum / k, k, new int[1 << nums.length]);
	}

	public static boolean f1(int[] nums, int status, int sum, int sets, int limit, int k, int[] dp) {
		if (sets == k) {
			return true;
		}
		if (dp[status] != 0) {
			return dp[status] == 1;
		}
		boolean ans = false;
		for (int i = 0; i < nums.length; i++) {
			if ((status & (1 << i)) == 0 && sum + nums[i] <= limit) {
				if (sum + nums[i] == limit) {
					ans = f1(nums, status | (1 << i), 0, sets + 1, limit, k, dp);
				} else {
					ans = f1(nums, status | (1 << i), sum + nums[i], sets, limit, k, dp);
				}
				if (ans) {
					break;
				}
			}
		}
		dp[status] = ans ? 1 : -1;
		return ans;
	}

	// 带路径的递归，根本改不成动态规划，纯纯的暴力递归
	// 但是利用疯狂的剪枝策略，可以做到非常好的效率
	// 但这并不是正式的解，如果数据设计的很苛刻，是通过不了的
	public static boolean canPartitionKSubsets2(int[] nums, int k) {
		int sum = 0;
		for (int num : nums) {
			sum += num;
		}
		if (sum % k != 0) {
			return false;
		}
		Arrays.sort(nums);
		return f2(new int[k], sum / k, nums, nums.length - 1);
	}

	public static boolean f2(int[] group, int target, int[] nums, int index) {
		if (index < 0) {
			return true;
		}
		int num = nums[index];
		int len = group.length;
		for (int i = 0; i < len; i++) {
			if (group[i] + num <= target) {
				group[i] += num;
				if (f2(group, target, nums, index - 1)) {
					return true;
				}
				group[i] -= num;
				if (group[i] == 0) {
					return false;
				}
				while (i + 1 < group.length && group[i] == group[i + 1]) {
					i++;
				}
			}
		}
		return false;
	}

}
