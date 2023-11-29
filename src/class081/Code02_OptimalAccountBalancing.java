package class081;

import java.util.Arrays;
import java.util.HashMap;

// 最优账单平衡
// 给你一个表示交易的数组 transactions
// 其中 transactions[i] = [fromi, toi, amounti]
// 表示 ID = fromi 的人给 ID = toi 的人共计 amounti
// 请你计算并返回还清所有债务的最小交易笔数
// 测试链接 : https://leetcode.cn/problems/optimal-account-balancing/
public class Code02_OptimalAccountBalancing {

	public static int minTransfers(int[][] transactions) {
		int[] debt = debts(transactions);
		int n = debt.length;
		int sum = 0;
		for (int num : debt) {
			sum += num;
		}
		int[] dp = new int[1 << n];
		Arrays.fill(dp, -1);
		return n - f(debt, (1 << n) - 1, sum, n, dp);
	}

	public static int f(int[] debt, int set, int sum, int n, int[] dp) {
		if (dp[set] != -1) {
			return dp[set];
		}
		int ans = 0;
		if ((set & (set - 1)) != 0) {
			int value = 0;
			int max = 0;
			for (int i = 0; i < n; i++) {
				value = debt[i];
				if ((set & (1 << i)) != 0) {
					max = Math.max(max, f(debt, set ^ (1 << i), sum - value, n, dp));
				}
			}
			ans = sum == 0 ? max + 1 : max;
		}
		dp[set] = ans;
		return ans;
	}

	public static int[] debts(int[][] transactions) {
		HashMap<Integer, Integer> map = new HashMap<>();
		for (int[] tran : transactions) {
			map.put(tran[0], map.getOrDefault(tran[0], 0) + tran[2]);
			map.put(tran[1], map.getOrDefault(tran[1], 0) - tran[2]);
		}
		int n = 0;
		for (int value : map.values()) {
			if (value != 0) {
				n++;
			}
		}
		int[] debt = new int[n];
		int index = 0;
		for (int value : map.values()) {
			if (value != 0) {
				debt[index++] = value;
			}
		}
		return debt;
	}

}
