package class068;

// 交错字符串
// 给定三个字符串 s1、s2、s3，请你帮忙验证 s3 是否是由 s1 和 s2 交错 组成的
// 测试链接 : https://leetcode.cn/problems/interleaving-string/
public class Code03_InterleavingString {

	// 已经展示太多次从递归到动态规划了
	// 直接写动态规划吧
	public static boolean isInterleave1(String str1, String str2, String str3) {
		if (str1.length() + str2.length() != str3.length()) {
			return false;
		}
		char[] s1 = str1.toCharArray();
		char[] s2 = str2.toCharArray();
		char[] s3 = str3.toCharArray();
		int n = s1.length;
		int m = s2.length;
		boolean[][] dp = new boolean[n + 1][m + 1];
		dp[0][0] = true;
		for (int len1 = 1; len1 <= n; len1++) {
			if (s1[len1 - 1] != s3[len1 - 1]) {
				break;
			}
			dp[len1][0] = true;
		}
		for (int len2 = 1; len2 <= m; len2++) {
			if (s2[len2 - 1] != s3[len2 - 1]) {
				break;
			}
			dp[0][len2] = true;
		}
		for (int len1 = 1; len1 <= n; len1++) {
			for (int len2 = 1; len2 <= m; len2++) {
				dp[len1][len2] = (s1[len1 - 1] == s3[len1 + len2 - 1] && dp[len1 - 1][len2])
						|| (s2[len2 - 1] == s3[len1 + len2 - 1] && dp[len1][len2 - 1]);
			}
		}
		return dp[n][m];
	}

	// 空间压缩
	public static boolean isInterleave2(String str1, String str2, String str3) {
		if (str1.length() + str2.length() != str3.length()) {
			return false;
		}
		char[] s1 = str1.toCharArray();
		char[] s2 = str2.toCharArray();
		char[] s3 = str3.toCharArray();
		int n = s1.length;
		int m = s2.length;
		boolean[] dp = new boolean[m + 1];
		dp[0] = true;
		for (int len2 = 1; len2 <= m; len2++) {
			if (s2[len2 - 1] != s3[len2 - 1]) {
				break;
			}
			dp[len2] = true;
		}
		for (int len1 = 1; len1 <= n; len1++) {
			dp[0] = s1[len1 - 1] == s3[len1 - 1] && dp[0];
			for (int len2 = 1; len2 <= m; len2++) {
				dp[len2] = (s1[len1 - 1] == s3[len1 + len2 - 1] && dp[len2])
						|| (s2[len2 - 1] == s3[len1 + len2 - 1] && dp[len2 - 1]);
			}
		}
		return dp[m];
	}

}
