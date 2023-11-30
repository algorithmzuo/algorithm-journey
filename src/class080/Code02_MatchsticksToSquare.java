package class080;

// 火柴拼正方形
// 你将得到一个整数数组 matchsticks
// 其中 matchsticks[i] 是第 i 个火柴棒的长度
// 你要用 所有的火柴棍 拼成一个正方形
// 你 不能折断 任何一根火柴棒，但你可以把它们连在一起，而且每根火柴棒必须 使用一次
// 如果你能拼出正方形，则返回 true ，否则返回 false
// 测试链接 : https://leetcode.cn/problems/matchsticks-to-square/
public class Code02_MatchsticksToSquare {

	public static boolean makesquare(int[] nums) {
		int sum = 0;
		for (int num : nums) {
			sum += num;
		}
		if (sum % 4 != 0) {
			return false;
		}
		int limit = 1 << nums.length;
		int[] dp = new int[limit];
		return f(nums, sum / 4, limit - 1, 0, 4, dp);
	}

	public static boolean f(int[] nums, int len, int status, int cur, int rest, int[] dp) {
		if (rest == 0) {
			return status == 0;
		}
		if (dp[status] != 0) {
			return dp[status] == 1;
		}
		boolean ans = false;
		for (int i = 0; i < nums.length; i++) {
			if ((status & (1 << i)) != 0 && cur + nums[i] <= len) {
				if (cur + nums[i] == len) {
					ans = f(nums, len, status ^ (1 << i), 0, rest - 1, dp);
				} else {
					ans = f(nums, len, status ^ (1 << i), cur + nums[i], rest, dp);
				}
			}
			if (ans) {
				break;
			}
		}
		dp[status] = ans ? 1 : -1;
		return ans;
	}

}
