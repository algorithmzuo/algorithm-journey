package class073;

// 目标和
// 给你一个非负整数数组 nums 和一个整数 target 。
// 向数组中的每个整数前添加 '+' 或 '-' ，然后串联起所有整数
// 可以构造一个表达式
// 例如nums=[2, 1]，可以在2之前添加'+' ，在1之前添加'-'
// 然后串联起来得到表达式 "+2-1" 。
// 返回可以通过上述方法构造的，运算结果等于 target 的不同表达式的数目
// 测试链接 : https://leetcode.cn/problems/target-sum/
public class Code03_TargetSum {

	// 普通尝试变成动态规划
	public static int findTargetSumWays1(int[] nums, int target) {
		int sum = 0;
		for (int num : nums) {
			sum += num;
		}
		int min = -sum;
		int max = sum;
		if (target < min || target > max) {
			return 0;
		}
		// dp[i][j]:
		// 1) dp[i-1][j]
		// 2) dp[i-1][j-nums[i]]
		// 3) dp[i-1][j+nums[i]]
		int n = nums.length;
		int m = max - min + 1;
		int[][] dp = new int[n][m];
		dp[0][nums[0] - min] += 1;
		dp[0][-nums[0] - min] += 1;
		for (int i = 1; i < n; i++) {
			for (int j = min; j <= max; j++) {
				if (j - nums[i] - min >= 0) {
					dp[i][j - min] = dp[i - 1][j - nums[i] - min];
				}
				if (j + nums[i] - min < m) {
					dp[i][j - min] += dp[i - 1][j + nums[i] - min];
				}
			}
		}
		return dp[n - 1][target - min];
	}

	// 转化为01动态规划
	// 优化1:
	// 你可以认为nums中都是非负数
	// 因为即便是nums中有负数，比如[3,-4,2]
	// 因为你能在每个数前面用+或者-号
	// 所以[3,-4,2]其实和[3,4,2]达成一样的效果
	// 那么我们就全把nums变成非负数，不会影响结果的
	// 优化2:
	// 如果nums都是非负数，并且所有数的累加和是sum
	// 那么如果target>sum，很明显没有任何方法可以达到target，可以直接返回0
	// 优化3:
	// nums内部的数组，不管怎么+和-，最终的结果都一定不会改变奇偶性
	// 所以，如果所有数的累加和是sum，
	// 并且与target的奇偶性不一样，没有任何方法可以达到target，可以直接返回0
	// 优化4:
	// 比如说给定一个数组, nums = [1, 2, 3, 4, 5] 并且 target = 3
	// 其中一个方案是 : +1 -2 +3 -4 +5 = 3
	// 该方案中取了正的集合为P = {1，3，5}
	// 该方案中取了负的集合为N = {2，4}
	// 所以任何一种方案，都一定有 sum(P) - sum(N) = target
	// 现在我们来处理一下这个等式，把左右两边都加上sum(P) + sum(N)，那么就会变成如下：
	// sum(P) - sum(N) + sum(P) + sum(N) = target + sum(P) + sum(N)
	// 2 * sum(P) = target + 数组所有数的累加和
	// sum(P) = (target + 数组所有数的累加和) / 2
	// 也就是说，任何一个集合，只要累加和是(target + 数组所有数的累加和) / 2
	// 那么就一定对应一种target的方式
	// 也就是说，比如非负数组nums，target = 7, 而所有数累加和是11
	// 求有多少方法组成7，其实就是求有多少种达到累加和(7+11)/2=9的方法
	// 优化5:
	// 二维动态规划的空间压缩技巧
	public static int findTargetSumWays2(int[] nums, int target) {
		int sum = 0;
		for (int n : nums) {
			sum += n;
		}
		return sum < target || ((target & 1) ^ (sum & 1)) != 0 ? 0 : subset(nums, (target + sum) >> 1);
	}

	// 求非负数组nums有多少个子集累加和是sum
	// 严格位置依赖动态规划 + 空间压缩
	// dp[i][j] :
	// 1) dp[i-1][j]
	// 2) dp[i-1][j-nums[i]]
	public static int subset(int[] nums, int sum) {
		if (sum < 0) {
			return 0;
		}
		int[] dp = new int[sum + 1];
		dp[0] = 1;
		for (int num : nums) {
			for (int i = sum; i >= num; i--) {
				dp[i] += dp[i - num];
			}
		}
		return dp[sum];
	}

}
