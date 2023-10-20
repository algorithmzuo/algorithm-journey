package class067;

// 最长公共子序列
// 给定两个字符串text1和text2
// 返回这两个字符串的最长 公共子序列 的长度
// 如果不存在公共子序列，返回0
// 两个字符串的 公共子序列 是这两个字符串所共同拥有的子序列
// 测试链接 : https://leetcode.cn/problems/longest-common-subsequence/
public class Code04_LongestCommonSubsequence {

	public static int longestCommonSubsequence1(String str1, String str2) {
		char[] s1 = str1.toCharArray();
		char[] s2 = str2.toCharArray();
		int n = s1.length;
		int m = s2.length;
		return f1(s1, s2, n - 1, m - 1);
	}

	public static int f1(char[] s1, char[] s2, int i1, int i2) {
		if (i1 < 0 || i2 < 0) {
			return 0;
		}
		int p1 = f1(s1, s2, i1 - 1, i2 - 1);
		int p2 = f1(s1, s2, i1 - 1, i2);
		int p3 = f1(s1, s2, i1, i2 - 1);
		int p4 = s1[i1] == s2[i2] ? (p1 + 1) : 0;
		return Math.max(Math.max(p1, p2), Math.max(p3, p4));
	}

	public static int longestCommonSubsequence2(String str1, String str2) {
		char[] s1 = str1.toCharArray();
		char[] s2 = str2.toCharArray();
		int n = s1.length;
		int m = s2.length;
		int[][] dp = new int[n][m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				dp[i][j] = -1;
			}
		}
		return f2(s1, s2, n - 1, m - 1, dp);
	}

	public static int f2(char[] s1, char[] s2, int i1, int i2, int[][] dp) {
		if (i1 < 0 || i2 < 0) {
			return 0;
		}
		if (dp[i1][i2] != -1) {
			return dp[i1][i2];
		}
		int ans;
		if (s1[i1] == s2[i2]) {
			ans = f2(s1, s2, i1 - 1, i2 - 1, dp) + 1;
		} else {
			ans = Math.max(f2(s1, s2, i1 - 1, i2, dp), f2(s1, s2, i1, i2 - 1, dp));
		}
		dp[i1][i2] = ans;
		return ans;
	}

	public static int longestCommonSubsequence3(String str1, String str2) {
		char[] s1 = str1.toCharArray();
		char[] s2 = str2.toCharArray();
		int n = s1.length;
		int m = s2.length;
		int[][] dp = new int[n][m];
		for (int i1 = 0; i1 < n; i1++) {
			for (int i2 = 0; i2 < m; i2++) {
				if (s1[i1] == s2[i2]) {
					dp[i1][i2] = 1 + (i1 > 0 && i2 > 0 ? dp[i1 - 1][i2 - 1] : 0);
				} else {
					dp[i1][i2] = Math.max(i1 > 0 ? dp[i1 - 1][i2] : 0, i2 > 0 ? dp[i1][i2 - 1] : 0);
				}
			}
		}
		return dp[n - 1][m - 1];
	}

	public static int longestCommonSubsequence4(String str1, String str2) {
		char[] s1, s2;
		if (str1.length() >= str2.length()) {
			s1 = str1.toCharArray();
			s2 = str2.toCharArray();
		} else {
			s1 = str2.toCharArray();
			s2 = str1.toCharArray();
		}
		int n = s1.length;
		int m = s2.length;
		int[] dp = new int[m];
		for (int i1 = 0, tmp1 = 0, tmp2; i1 < n; i1++) {
			for (int i2 = 0; i2 < m; i2++) {
				tmp2 = dp[i2];
				if (s1[i1] == s2[i2]) {
					dp[i2] = 1 + (i1 > 0 && i2 > 0 ? tmp1 : 0);
				} else {
					dp[i2] = Math.max(i1 > 0 ? dp[i2] : 0, i2 > 0 ? dp[i2 - 1] : 0);
				}
				tmp1 = tmp2;
			}
		}
		return dp[m - 1];
	}

}
