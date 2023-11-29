package class080;

import java.util.Arrays;
import java.util.List;

// 每个人戴不同帽子的方案数
// 总共有 n 个人和 40 种不同的帽子，帽子编号从 1 到 40
// 给你一个整数列表的列表 hats ，其中 hats[i] 是第 i 个人所有喜欢帽子的列表
// 请你给每个人安排一顶他喜欢的帽子，确保每个人戴的帽子跟别人都不一样，并返回方案数
// 由于答案可能很大，请返回它对10^9+7取余后的结果
// 测试链接 : https://leetcode.cn/problems/number-of-ways-to-wear-different-hats-to-each-other
public class Code03_NumberOfWaysWearDifferentHats {

	public static int MOD = 1000000007;

	public static int numberWays(List<List<Integer>> arr) {
		int m = 0;
		for (List<Integer> person : arr) {
			for (int hat : person) {
				m = Math.max(m, hat);
			}
		}
		int n = arr.size();
		int[] hats = new int[m + 1];
		for (int pi = 0; pi < n; pi++) {
			for (int hat : arr.get(pi)) {
				hats[hat] |= 1 << pi;
			}
		}
		int[][] dp = new int[m + 1][1 << n];
		for (int i = 0; i <= m; i++) {
			Arrays.fill(dp[i], -1);
		}
		return f(hats, m, n, 0, 0, dp);
	}

	public static int f(int[] hats, int m, int n, int i, int s, int[][] dp) {
		if (s == (1 << n) - 1) {
			return 1;
		}
		if (i == m + 1) {
			return 0;
		}
		if (dp[i][s] != -1) {
			return dp[i][s];
		}
		int ans = f(hats, m, n, i + 1, s, dp);
		int h = hats[i];
		int rightOne;
		while (h != 0) {
			rightOne = h & -h;
			if ((s & rightOne) == 0) {
				ans = (ans + f(hats, m, n, i + 1, s | rightOne, dp)) % MOD;
			}
			h -= rightOne;
		}
		dp[i][s] = ans;
		return ans;
	}

}
