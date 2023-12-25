package class067;

// 最长公共子序列
// 给定两个字符串text1和text2
// 返回这两个字符串的最长 公共子序列 的长度
// 如果不存在公共子序列，返回0
// 两个字符串的 公共子序列 是这两个字符串所共同拥有的子序列
// 测试链接 : https://leetcode.cn/problems/longest-common-subsequence/
public class Code03_LongestCommonSubsequence {

	public static int longestCommonSubsequence1(String str1, String str2) {
		char[] s1 = str1.toCharArray();
		char[] s2 = str2.toCharArray();
		int n = s1.length;
		int m = s2.length;
		return f1(s1, s2, n - 1, m - 1);
	}

	// s1[0....i1]与s2[0....i2]最长公共子序列长度
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

	// 为了避免很多边界讨论
	// 很多时候往往不用下标来定义尝试，而是用长度来定义尝试
	// 因为长度最短是0，而下标越界的话讨论起来就比较麻烦
	public static int longestCommonSubsequence2(String str1, String str2) {
		char[] s1 = str1.toCharArray();
		char[] s2 = str2.toCharArray();
		int n = s1.length;
		int m = s2.length;
		return f2(s1, s2, n, m);
	}

	// s1[前缀长度为len1]对应s2[前缀长度为len2]
	// 最长公共子序列长度
	public static int f2(char[] s1, char[] s2, int len1, int len2) {
		if (len1 == 0 || len2 == 0) {
			return 0;
		}
		int ans;
		if (s1[len1 - 1] == s2[len2 - 1]) {
			ans = f2(s1, s2, len1 - 1, len2 - 1) + 1;
		} else {
			ans = Math.max(f2(s1, s2, len1 - 1, len2), f2(s1, s2, len1, len2 - 1));
		}
		return ans;
	}

	// 记忆化搜索
	public static int longestCommonSubsequence3(String str1, String str2) {
		char[] s1 = str1.toCharArray();
		char[] s2 = str2.toCharArray();
		int n = s1.length;
		int m = s2.length;
		int[][] dp = new int[n + 1][m + 1];
		for (int i = 0; i <= n; i++) {
			for (int j = 0; j <= m; j++) {
				dp[i][j] = -1;
			}
		}
		return f3(s1, s2, n, m, dp);
	}

	public static int f3(char[] s1, char[] s2, int len1, int len2, int[][] dp) {
		if (len1 == 0 || len2 == 0) {
			return 0;
		}
		if (dp[len1][len2] != -1) {
			return dp[len1][len2];
		}
		int ans;
		if (s1[len1 - 1] == s2[len2 - 1]) {
			ans = f3(s1, s2, len1 - 1, len2 - 1, dp) + 1;
		} else {
			ans = Math.max(f3(s1, s2, len1 - 1, len2, dp), f3(s1, s2, len1, len2 - 1, dp));
		}
		dp[len1][len2] = ans;
		return ans;
	}

	// 严格位置依赖的动态规划
	public static int longestCommonSubsequence4(String str1, String str2) {
		char[] s1 = str1.toCharArray();
		char[] s2 = str2.toCharArray();
		int n = s1.length;
		int m = s2.length;
		int[][] dp = new int[n + 1][m + 1];
		for (int len1 = 1; len1 <= n; len1++) {
			for (int len2 = 1; len2 <= m; len2++) {
				if (s1[len1 - 1] == s2[len2 - 1]) {
					dp[len1][len2] = 1 + dp[len1 - 1][len2 - 1];
				} else {
					dp[len1][len2] = Math.max(dp[len1 - 1][len2], dp[len1][len2 - 1]);
				}
			}
		}
		return dp[n][m];
	}

	// 严格位置依赖的动态规划 + 空间压缩
	public static int longestCommonSubsequence5(String str1, String str2) {
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
		int[] dp = new int[m + 1];
		for (int len1 = 1; len1 <= n; len1++) {
			int leftUp = 0, backup;
			for (int len2 = 1; len2 <= m; len2++) {
				backup = dp[len2];
				if (s1[len1 - 1] == s2[len2 - 1]) {
					dp[len2] = 1 + leftUp;
				} else {
					dp[len2] = Math.max(dp[len2], dp[len2 - 1]);
				}
				leftUp = backup;
			}
		}
		return dp[m];
	}

}
