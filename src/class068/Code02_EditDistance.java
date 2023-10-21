package class068;

// 编辑距离
// 给你两个单词 word1 和 word2
// 请返回将 word1 转换成 word2 所使用的最少代价
// 你可以对一个单词进行如下三种操作：
// 插入一个字符，代价a
// 删除一个字符，代价b
// 替换一个字符，代价c
// 测试链接 : https://leetcode.cn/problems/edit-distance/
public class Code02_EditDistance {

	// 已经展示太多次从递归到动态规划了
	// 直接写动态规划吧
	public int minDistance(String word1, String word2) {
		return editDistance2(word1, word2, 1, 1, 1);
	}

	// a : str1中插入1个字符的代价
	// b : str1中删除1个字符的代价
	// c : str1中改变1个字符的代价
	// 返回从str1转化成str2的最低代价
	public static int editDistance1(String str1, String str2, int a, int b, int c) {
		char[] s1 = str1.toCharArray();
		char[] s2 = str2.toCharArray();
		int n = s1.length;
		int m = s2.length;
		int[][] dp = new int[n + 1][m + 1];
		for (int len1 = 1; len1 <= n; len1++) {
			dp[len1][0] = len1 * b;
		}
		for (int len2 = 1; len2 <= m; len2++) {
			dp[0][len2] = len2 * a;
		}
		for (int len1 = 1; len1 <= n; len1++) {
			for (int len2 = 1; len2 <= m; len2++) {
				if (s1[len1 - 1] == s2[len2 - 1]) {
					dp[len1][len2] = dp[len1 - 1][len2 - 1];
				} else {
					dp[len1][len2] = Math.min(Math.min(dp[len1 - 1][len2] + b, dp[len1][len2 - 1] + a),
							dp[len1 - 1][len2 - 1] + c);
				}
			}
		}
		return dp[n][m];
	}

	// 空间压缩
	public static int editDistance2(String str1, String str2, int a, int b, int c) {
		char[] s1 = str1.toCharArray();
		char[] s2 = str2.toCharArray();
		int n = s1.length;
		int m = s2.length;
		int[] dp = new int[m + 1];
		for (int len2 = 1; len2 <= m; len2++) {
			dp[len2] = len2 * a;
		}
		for (int len1 = 1; len1 <= n; len1++) {
			int tmp1 = (len1 - 1) * b, tmp2;
			dp[0] = len1 * b;
			for (int len2 = 1; len2 <= m; len2++) {
				tmp2 = dp[len2];
				if (s1[len1 - 1] == s2[len2 - 1]) {
					dp[len2] = tmp1;
				} else {
					dp[len2] = Math.min(Math.min(dp[len2] + b, dp[len2 - 1] + a), tmp1 + c);
				}
				tmp1 = tmp2;
			}
		}
		return dp[m];
	}

}
