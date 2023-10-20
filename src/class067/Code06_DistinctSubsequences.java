package class067;

// 不同的子序列
// 给你两个字符串 s 和 t ，统计并返回在 s 的 子序列 中 t 出现的个数
// 测试链接 : https://leetcode.cn/problems/distinct-subsequences/
public class Code06_DistinctSubsequences {

	// 为了节省很多边界讨论
	// 很多时候往往不用下标来定义尝试
	// 而是用长度来定义尝试
	public static int numDistinct1(String str, String target) {
		char[] s = str.toCharArray();
		char[] t = target.toCharArray();
		int n = s.length;
		int m = t.length;
		return f1(s, t, n, m);
	}

	public static int f1(char[] s, char[] t, int slen, int tlen) {
		if (tlen == 0) {
			return 1;
		}
		if (slen == 0) {
			return 0;
		}
		int ans = f1(s, t, slen - 1, tlen);
		if (s[slen - 1] == t[tlen - 1]) {
			ans += f1(s, t, slen - 1, tlen - 1);
		}
		return ans;
	}

	public static int numDistinct2(String str, String target) {
		char[] s = str.toCharArray();
		char[] t = target.toCharArray();
		int n = s.length;
		int m = t.length;
		int[][] dp = new int[n + 1][m + 1];
		for (int i = 0; i <= n; i++) {
			for (int j = 0; j <= m; j++) {
				dp[i][j] = -1;
			}
		}
		return f2(s, t, n, m, dp);
	}

	public static int f2(char[] s, char[] t, int slen, int tlen, int[][] dp) {
		if (tlen == 0) {
			return 1;
		}
		if (slen == 0) {
			return 0;
		}
		if (dp[slen][tlen] != -1) {
			return dp[slen][tlen];
		}
		int ans = f2(s, t, slen - 1, tlen, dp);
		if (s[slen - 1] == t[tlen - 1]) {
			ans += f2(s, t, slen - 1, tlen - 1, dp);
		}
		dp[slen][tlen] = ans;
		return ans;
	}

	public static int numDistinct3(String str, String target) {
		char[] s = str.toCharArray();
		char[] t = target.toCharArray();
		int n = s.length;
		int m = t.length;
		int[][] dp = new int[n + 1][m + 1];
		for (int i = 0; i <= n; i++) {
			dp[i][0] = 1;
		}
		for (int slen = 1; slen <= n; slen++) {
			for (int tlen = 1; tlen <= m; tlen++) {
				dp[slen][tlen] = dp[slen - 1][tlen];
				if (s[slen - 1] == t[tlen - 1]) {
					dp[slen][tlen] += dp[slen - 1][tlen - 1];
				}
			}
		}
		return dp[n][m];
	}

	public static int numDistinct4(String str, String target) {
		char[] s = str.toCharArray();
		char[] t = target.toCharArray();
		int n = s.length;
		int m = t.length;
		int[] dp = new int[m + 1];
		dp[0] = 1;
		for (int slen = 1; slen <= n; slen++) {
			for (int tlen = m; tlen >= 1; tlen--) {
				if (s[slen - 1] == t[tlen - 1]) {
					dp[tlen] += dp[tlen - 1];
				}
			}
		}
		return dp[m];
	}

}
