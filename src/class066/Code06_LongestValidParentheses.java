package class066;

// 最长有效括号
// 给你一个只包含 '(' 和 ')' 的字符串
// 找出最长有效（格式正确且连续）括号子串的长度。
// 测试链接 : https://leetcode.cn/problems/longest-valid-parentheses/
public class Code06_LongestValidParentheses {

	public static int longestValidParentheses(String str) {
		char[] s = str.toCharArray();
		int[] dp = new int[s.length];
		int ans = 0;
		for (int i = 1, pre; i < s.length; i++) {
			if (s[i] == ')') {
				pre = i - dp[i - 1] - 1;
				if (pre >= 0 && s[pre] == '(') {
					dp[i] = dp[i - 1] + 2 + (pre > 0 ? dp[pre - 1] : 0);
				}
			}
			ans = Math.max(ans, dp[i]);
		}
		return ans;
	}

}
