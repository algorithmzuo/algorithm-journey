package class068;

// 不同的子序列
// 给你两个字符串s和t ，统计并返回在s的子序列中t出现的个数
// 答案对 1000000007 取模
// 测试链接 : https://leetcode.cn/problems/distinct-subsequences/
public class Code01_DistinctSubsequences {

	// 已经展示太多次从递归到动态规划了
	// 直接写动态规划吧
	public static int numDistinct1(String str, String target) {
		char[] s = str.toCharArray();
		char[] t = target.toCharArray();
		int n = s.length;
		int m = t.length;
		// dp[i][j] :
		// s[前缀长度为i]的所有子序列中，有多少个子序列等于t[前缀长度为j]
		int[][] dp = new int[n + 1][m + 1];
		for (int i = 0; i <= n; i++) {
			dp[i][0] = 1;
		}
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= m; j++) {
				dp[i][j] = dp[i - 1][j];
				if (s[i - 1] == t[j - 1]) {
					dp[i][j] += dp[i - 1][j - 1];
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
		for (int i = 1; i <= n; i++) {
			for (int j = m; j >= 1; j--) {
				if (s[i - 1] == t[j - 1]) {
					dp[j] += dp[j - 1];
				}
			}
		}
		return dp[m];
	}

	// 本题说了要取模，所以增加取模的逻辑
	public static int numDistinct3(String str, String target) {
		int mod = 1000000007;
		char[] s = str.toCharArray();
		char[] t = target.toCharArray();
		int n = s.length;
		int m = t.length;
		int[] dp = new int[m + 1];
		dp[0] = 1;
		for (int i = 1; i <= n; i++) {
			for (int j = m; j >= 1; j--) {
				if (s[i - 1] == t[j - 1]) {
					dp[j] = (dp[j] + dp[j - 1]) % mod;
				}
			}
		}
		return dp[m];
	}

}
