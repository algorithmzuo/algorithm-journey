package class076;

// 让字符串成为回文串的最少插入次数
// 给你一个字符串 s
// 每一次操作你都可以在字符串的任意位置插入任意字符
// 请你返回让s成为回文串的最少操作次数
// 测试链接 : https://leetcode.cn/problems/minimum-insertion-steps-to-make-a-string-palindrome/
public class Code01_MinimumInsertionToPalindrome {

	public static int minInsertions1(String str) {
		char[] s = str.toCharArray();
		int n = s.length;
		int[][] dp = new int[n][n];
		for (int l = 0; l < n - 1; l++) {
			dp[l][l + 1] = s[l] == s[l + 1] ? 0 : 1;
		}
		for (int l = n - 3; l >= 0; l--) {
			for (int r = l + 2; r < n; r++) {
				dp[l][r] = Math.min(dp[l][r - 1], dp[l + 1][r]) + 1;
				if (s[l] == s[r]) {
					dp[l][r] = Math.min(dp[l][r], dp[l + 1][r - 1]);
				}
			}
		}
		return dp[0][n - 1];
	}

	// 空间压缩
	public static int minInsertions(String str) {
		char[] s = str.toCharArray();
		int n = s.length;
		if (n < 2) {
			return 0;
		}
		int[] dp = new int[n];
		dp[n - 1] = s[n - 2] == s[n - 1] ? 0 : 1;
		for (int l = n - 3, leftDown, tmp; l >= 0; l--) {
			leftDown = dp[l + 1];
			dp[l + 1] = s[l] == s[l + 1] ? 0 : 1;
			for (int r = l + 2; r < n; r++) {
				tmp = dp[r];
				dp[r] = Math.min(dp[r - 1], dp[r]) + 1;
				if (s[l] == s[r]) {
					dp[r] = Math.min(dp[r], leftDown);
				}
				leftDown = tmp;
			}
		}
		return dp[n - 1];
	}

}
