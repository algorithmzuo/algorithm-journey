package class066;

import java.util.Arrays;

// 解码方法
// 一条包含字母 A-Z 的消息通过以下映射进行了 编码 ：
// 'A' -> "1"
// 'B' -> "2"
// ...
// 'Z' -> "26"
// 要 解码 已编码的消息，所有数字必须基于上述映射的方法，反向映射回字母（可能有多种方法）
// 例如，"11106" 可以映射为："AAJF"、"KJF"
// 注意，消息不能分组为(1 11 06)，因为 "06" 不能映射为 "F"
// 这是由于 "6" 和 "06" 在映射中并不等价
// 给你一个只含数字的 非空 字符串 s ，请计算并返回 解码 方法的 总数
// 题目数据保证答案肯定是一个 32位 的整数
// 测试链接 : https://leetcode.cn/problems/decode-ways/
public class Code03_DecodeWays {

	// 暴力尝试
	public static int numDecodings1(String s) {
		return f1(s.toCharArray(), 0);
	}

	// s : 数字字符串 
	// s[i....]有多少种有效的转化方案
	public static int f1(char[] s, int i) {
		if (i == s.length) {
			return 1;
		}
		int ans;
		if (s[i] == '0') {
			ans = 0;
		} else {
			ans = f1(s, i + 1);
			if (i + 1 < s.length && ((s[i] - '0') * 10 + s[i + 1] - '0') <= 26) {
				ans += f1(s, i + 2);
			}
		}
		return ans;
	}

	// 暴力尝试改记忆化搜索
	public static int numDecodings2(String s) {
		int[] dp = new int[s.length()];
		Arrays.fill(dp, -1);
		return f2(s.toCharArray(), 0, dp);
	}

	public static int f2(char[] s, int i, int[] dp) {
		if (i == s.length) {
			return 1;
		}
		if (dp[i] != -1) {
			return dp[i];
		}
		int ans;
		if (s[i] == '0') {
			ans = 0;
		} else {
			ans = f2(s, i + 1, dp);
			if (i + 1 < s.length && ((s[i] - '0') * 10 + s[i + 1] - '0') <= 26) {
				ans += f2(s, i + 2, dp);
			}
		}
		dp[i] = ans;
		return ans;
	}

	// 严格位置依赖的动态规划
	public static int numDecodings3(String str) {
		char[] s = str.toCharArray();
		int n = s.length;
		int[] dp = new int[n + 1];
		dp[n] = 1;
		for (int i = n - 1; i >= 0; i--) {
			if (s[i] == '0') {
				dp[i] = 0;
			} else {
				dp[i] = dp[i + 1];
				if (i + 1 < s.length && ((s[i] - '0') * 10 + s[i + 1] - '0') <= 26) {
					dp[i] += dp[i + 2];
				}
			}
		}
		return dp[0];
	}

	// 严格位置依赖的动态规划 + 空间压缩
	public static int numDecodings4(String s) {
		// dp[i+1]
		int next = 1;
		// dp[i+2]
		int nextNext = 0;
		for (int i = s.length() - 1, cur; i >= 0; i--) {
			if (s.charAt(i) == '0') {
				cur = 0;
			} else {
				cur = next;
				if (i + 1 < s.length() && ((s.charAt(i) - '0') * 10 + s.charAt(i + 1) - '0') <= 26) {
					cur += nextNext;
				}
			}
			nextNext = next;
			next = cur;
		}
		return next;
	}

}
