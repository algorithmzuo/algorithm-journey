package class076;

// 让字符串成为回文串的最少插入次数
// 给你一个字符串 s
// 每一次操作你都可以在字符串的任意位置插入任意字符
// 请你返回让s成为回文串的最少操作次数
// 测试链接 : https://leetcode.cn/problems/minimum-insertion-steps-to-make-a-string-palindrome/
public class Code01_MinimumInsertionToPalindrome {

	// 记忆化搜索
	public static int minInsertions1(String str) {
		char[] s = str.toCharArray();
		int n = s.length;
		int[][] dp = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = i; j < n; j++) {
				dp[i][j] = -1;
			}
		}
		return f(s, 0, n - 1, dp);
	}

	public static int f(char[] s, int l, int r, int[][] dp) {
		if (dp[l][r] != -1) {
			return dp[l][r];
		}
		int ans;
		if (l == r) {
			ans = 0;
		} else if (l + 1 == r) {
			ans = s[l] == s[l + 1] ? 0 : 1;
		} else {
			ans = Math.min(f(s, l, r - 1, dp), f(s, l + 1, r, dp)) + 1;
			if (s[l] == s[r]) {
				ans = Math.min(ans, f(s, l + 1, r - 1, dp));
			}
		}
		dp[l][r] = ans;
		return ans;
	}

	// 严格位置依赖的动态规划
	public static int minInsertions2(String str) {
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
	public static int minInsertions3(String str) {
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
