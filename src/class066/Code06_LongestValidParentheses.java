package class066;

// 最长有效括号
// 给你一个只包含 '(' 和 ')' 的字符串
// 找出最长有效（格式正确且连续）括号子串的长度。
// 测试链接 : https://leetcode.cn/problems/longest-valid-parentheses/
public class Code06_LongestValidParentheses {

	// 时间复杂度O(n)，n是str字符串的长度
	public static int longestValidParentheses(String str) {
		char[] s = str.toCharArray();
		// dp[0...n-1]
		// dp[i] : 子串必须以i位置的字符结尾的情况下，往左整体有效的最大长度
		int[] dp = new int[s.length];
		int ans = 0;
		for (int i = 1, p; i < s.length; i++) {
			if (s[i] == ')') {
				p = i - dp[i - 1] - 1;
				//  ?         )
				//  p         i
				if (p >= 0 && s[p] == '(') {
					dp[i] = dp[i - 1] + 2 + (p - 1 >= 0 ? dp[p - 1] : 0);
				}
			}
			ans = Math.max(ans, dp[i]);
		}
		return ans;
	}

}
