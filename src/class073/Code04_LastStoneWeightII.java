package class073;

// 最后一块石头的重量 II
// 有一堆石头，用整数数组 stones 表示
// 其中 stones[i] 表示第 i 块石头的重量。
// 每一回合，从中选出任意两块石头，然后将它们一起粉碎
// 假设石头的重量分别为 x 和 y，且 x <= y
// 那么粉碎的可能结果如下：
// 如果 x == y，那么两块石头都会被完全粉碎；
// 如果 x != y，那么重量为 x 的石头将会完全粉碎，而重量为 y 的石头新重量为 y-x
// 最后，最多只会剩下一块 石头，返回此石头 最小的可能重量
// 如果没有石头剩下，就返回 0
// 测试链接 : https://leetcode.cn/problems/last-stone-weight-ii/
public class Code04_LastStoneWeightII {

	public static int lastStoneWeightII(int[] nums) {
		int sum = 0;
		for (int num : nums) {
			sum += num;
		}
		// nums中随意选择数字
		// 累加和一定要 <= sum / 2
		// 又尽量接近
		int near = near(nums, sum / 2);
		return sum - near - near;
	}

	// 非负数组nums中，子序列累加和不超过t，但是最接近t的累加和是多少
	// 01背包问题(子集累加和尽量接近t) + 空间压缩
	public static int near(int[] nums, int t) {
		int[] dp = new int[t + 1];
		for (int num : nums) {
			for (int j = t; j >= num; j--) {
				// dp[i][j] = Math.max(dp[i-1][j], dp[i-1][j-nums[i]]+nums[i])
				dp[j] = Math.max(dp[j], dp[j - num] + num);
			}
		}
		return dp[t];
	}

}
