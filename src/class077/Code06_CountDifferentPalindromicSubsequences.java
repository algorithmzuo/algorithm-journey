package class077;

import java.util.Arrays;

// 统计不同回文子序列
// 给你一个字符串s，返回s中不同的非空回文子序列个数
// 由于答案可能很大，请你将答案对10^9+7取余后返回
// 测试链接 : https://leetcode.cn/problems/count-different-palindromic-subsequences/
public class Code06_CountDifferentPalindromicSubsequences {

	// 时间复杂度O(n^2)
	public static int countPalindromicSubsequences(String str) {
		int mod = 1000000007;
		char[] s = str.toCharArray();
		int n = s.length;
		int[] last = new int[256];
		// left[i] : i位置的左边和s[i]字符相等且最近的位置在哪，不存在就是-1
		int[] left = new int[n];
		Arrays.fill(last, -1);
		for (int i = 0; i < n; i++) {
			left[i] = last[s[i]];
			last[s[i]] = i;
		}
		// right[i] : i位置的右边和s[i]字符相等且最近的位置在哪，不存在就是n
		int[] right = new int[n];
		Arrays.fill(last, n);
		for (int i = n - 1; i >= 0; i--) {
			right[i] = last[s[i]];
			last[s[i]] = i;
		}
		// dp[i][j] : i...j范围上有多少不同的回文子序列
		// 如果i>j，那么认为是无效范围dp[i][j] = 0
		long[][] dp = new long[n][n];
		for (int i = 0; i < n; i++) {
			dp[i][i] = 1;
		}
		for (int i = n - 2, l, r; i >= 0; i--) {
			for (int j = i + 1; j < n; j++) {
				if (s[i] != s[j]) {
					// a ..... b
					// i       j
					// 因为要取模，所以只要发生减操作就+mod，讲解041同余原理
					dp[i][j] = dp[i][j - 1] + dp[i + 1][j] - dp[i + 1][j - 1] + mod;
				} else {
					// s[i] == s[j]
					// a......a
					// i      j
					l = right[i];
					r = left[j];
					if (l > r) {
						// i...j的内部没有s[i]字符
						// a....a
						// i    j
						// (i+1..j-1) + a(i+1..j-1)a + a + aa
						dp[i][j] = dp[i + 1][j - 1] * 2 + 2;
					} else if (l == r) {
						// i...j的内部有一个s[i]字符
						// a.....a......a
						// i     lr     j
						// (i+1..j-1) + a(i+1..j-1)a + aa
						dp[i][j] = dp[i + 1][j - 1] * 2 + 1;
					} else {
						// i...j的内部不只一个s[i]字符
						// a...a....这内部可能还有a但是不重要....a...a
						// i   l                             r   j
						// 因为要取模，所以只要发生减操作就+mod，讲解041同余原理
						dp[i][j] = dp[i + 1][j - 1] * 2 - dp[l + 1][r - 1] + mod;
					}
				}
				dp[i][j] %= mod;
			}
		}
		return (int) dp[0][n - 1];
	}

}
