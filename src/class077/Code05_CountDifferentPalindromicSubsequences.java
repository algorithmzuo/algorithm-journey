package class077;

import java.util.Arrays;

// 统计不同回文子序列
// 给你一个字符串s，返回s中不同的非空回文子序列个数
// 由于答案可能很大，请你将答案对10^9+7取余后返回
// 测试链接 : https://leetcode.cn/problems/count-different-palindromic-subsequences/
public class Code05_CountDifferentPalindromicSubsequences {

	public static int countPalindromicSubsequences(String str) {
		int mod = 1000000007;
		char[] s = str.toCharArray();
		int n = s.length;
		int[] right = new int[n];
		int[] left = new int[n];
		int[] last = new int[256];
		Arrays.fill(last, -1);
		for (int i = 0; i < n; i++) {
			left[i] = last[s[i]];
			last[s[i]] = i;
		}
		Arrays.fill(last, n);
		for (int i = n - 1; i >= 0; i--) {
			right[i] = last[s[i]];
			last[s[i]] = i;
		}
		long[][] dp = new long[n][n];
		for (int i = 0; i < n; i++) {
			dp[i][i] = 1;
		}
		for (int i = n - 2, l, r; i >= 0; i--) {
			for (int j = i + 1; j < n; j++) {
				if (s[i] == s[j]) {
					l = Math.min(j, right[i]);
					r = Math.max(i, left[j]);
					if (l > r) {
						dp[i][j] = dp[i + 1][j - 1] * 2 + 2;
					} else if (l == r) {
						dp[i][j] = dp[i + 1][j - 1] * 2 + 1;
					} else {
						dp[i][j] = dp[i + 1][j - 1] * 2 - dp[l + 1][r - 1] + mod;
					}
				} else {
					dp[i][j] = dp[i][j - 1] + dp[i + 1][j] - dp[i + 1][j - 1] + mod;
				}
				dp[i][j] %= mod;
			}
		}
		return (int) dp[0][n - 1];
	}

}
