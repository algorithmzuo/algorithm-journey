package class067;

// 最长回文子序列
// 给你一个字符串 s ，找出其中最长的回文子序列，并返回该序列的长度
// 测试链接 : https://leetcode.cn/problems/longest-palindromic-subsequence/
public class Code04_LongestPalindromicSubsequence {

	// 最长回文子序列问题可以转化成最长公共子序列问题
	// 不过这里讲述区间动态规划的思路
	// 区间dp还会有单独的视频做详细讲述
	public static int longestPalindromeSubseq1(String str) {
		char[] s = str.toCharArray();
		int n = s.length;
		return f1(s, 0, n - 1);
	}

	public static int f1(char[] s, int l, int r) {
		if (l == r) {
			return 1;
		}
		if (l + 1 == r) {
			return s[l] == s[r] ? 2 : 1;
		}
		if (s[l] == s[r]) {
			return 2 + f1(s, l + 1, r - 1);
		} else {
			return Math.max(f1(s, l + 1, r), f1(s, l, r - 1));
		}
	}

	public static int longestPalindromeSubseq2(String str) {
		char[] s = str.toCharArray();
		int n = s.length;
		int[][] dp = new int[n][n];
		return f2(s, 0, n - 1, dp);
	}

	public static int f2(char[] s, int l, int r, int[][] dp) {
		if (l == r) {
			return 1;
		}
		if (l + 1 == r) {
			return s[l] == s[r] ? 2 : 1;
		}
		if (dp[l][r] != 0) {
			return dp[l][r];
		}
		int ans;
		if (s[l] == s[r]) {
			ans = 2 + f2(s, l + 1, r - 1, dp);
		} else {
			ans = Math.max(f2(s, l + 1, r, dp), f2(s, l, r - 1, dp));
		}
		dp[l][r] = ans;
		return ans;
	}

	public static int longestPalindromeSubseq3(String str) {
		char[] s = str.toCharArray();
		int n = s.length;
		int[][] dp = new int[n][n];
		for (int l = n - 1; l >= 0; l--) {
			dp[l][l] = 1;
			if (l + 1 < n) {
				dp[l][l + 1] = s[l] == s[l + 1] ? 2 : 1;
			}
			for (int r = l + 2; r < n; r++) {
				if (s[l] == s[r]) {
					dp[l][r] = 2 + dp[l + 1][r - 1];
				} else {
					dp[l][r] = Math.max(dp[l + 1][r], dp[l][r - 1]);
				}
			}
		}
		return dp[0][n - 1];
	}

	public static int longestPalindromeSubseq4(String str) {
		char[] s = str.toCharArray();
		int n = s.length;
		int[] dp = new int[n];
		for (int l = n - 1, tmp1 = 0, tmp2; l >= 0; l--) {
			dp[l] = 1;
			if (l + 1 < n) {
				tmp1 = dp[l + 1];
				dp[l + 1] = s[l] == s[l + 1] ? 2 : 1;
			}
			for (int r = l + 2; r < n; r++) {
				tmp2 = dp[r];
				if (s[l] == s[r]) {
					dp[r] = 2 + tmp1;
				} else {
					dp[r] = Math.max(dp[r], dp[r - 1]);
				}
				tmp1 = tmp2;
			}
		}
		return dp[n - 1];
	}

}
