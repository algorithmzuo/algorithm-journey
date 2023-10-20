package class068;

// 不同的子序列
// 给你两个字符串 s 和 t ，统计并返回在 s 的 子序列 中 t 出现的个数
// 测试链接 : https://leetcode.cn/problems/distinct-subsequences/
public class Code01_DistinctSubsequences {

	// 已经展示太多次从递归到动态规划了
	// 直接写动态规划吧
	public static int numDistinct1(String str, String target) {
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

	// 空间压缩
	public static int numDistinct2(String str, String target) {
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
