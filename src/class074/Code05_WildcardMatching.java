package class074;

// 通配符匹配（和题目4高度相似，只是边界条件不同而已，而且更简单）
// 给你字符串s、字符串p
// s中一定不含有'?'、'*'字符，p中可能含有'?'、'*'字符
// '?' 表示可以变成任意字符，数量1个
// '*' 表示可以匹配任何字符串
// 请实现一个支持 '?' 和 '*' 的通配符匹配
// 返回p的整个字符串能不能匹配出s的整个字符串
// 测试链接 : https://leetcode.cn/problems/wildcard-matching/
public class Code05_WildcardMatching {

	// 暴力递归
	public static boolean isMatch1(String str, String pat) {
		char[] s = str.toCharArray();
		char[] p = pat.toCharArray();
		return f1(s, p, 0, 0);
	}

	// s[i....]能不能被p[j....]完全匹配出来
	public static boolean f1(char[] s, char[] p, int i, int j) {
		if (i == s.length) {
			// s没了
			if (j == p.length) {
				// 如果p也没了，返回true
				return true;
			} else {
				// 如果p[j]是*，可以消掉，然后看看p[j+1....]是不是都能消掉
				return p[j] == '*' && f1(s, p, i, j + 1);
			}
		} else if (j == p.length) {
			// s有
			// p没了
			return false;
		} else {
			if (p[j] != '*') {
				// s[i....]
				// p[j....]
				// 如果p[j]不是*，那么当前的字符必须能匹配：(s[i] == p[j] || p[j] == '?')
				// 同时，后续也必须匹配上：process1(s, p, i + 1, j + 1);
				return (s[i] == p[j] || p[j] == '?') && f1(s, p, i + 1, j + 1);
			} else {
				// s[i....]
				// p[j....]
				// 如果p[j]是*
				// 选择1: 反正当前p[j]是*，必然可以搞定s[i]，那么继续 : process1(s, p, i + 1, j)
				// 选择2: 虽然当前p[j]是*，但就是不让它搞定s[i]，那么继续 : process1(s, p, i, j + 1)
				// 两种选择有一个能走通，答案就是true；如果都搞不定，答案就是false
				return f1(s, p, i + 1, j) || f1(s, p, i, j + 1);
			}
		}
	}

	// 记忆化搜索
	public static boolean isMatch2(String str, String pat) {
		char[] s = str.toCharArray();
		char[] p = pat.toCharArray();
		int n = s.length;
		int m = p.length;
		// dp[i][j] == 0，表示没算过
		// dp[i][j] == 1，表示算过，答案是true
		// dp[i][j] == 2，表示算过，答案是false
		int[][] dp = new int[n + 1][m + 1];
		return f2(s, p, 0, 0, dp);
	}

	public static boolean f2(char[] s, char[] p, int i, int j, int[][] dp) {
		if (dp[i][j] != 0) {
			return dp[i][j] == 1;
		}
		boolean ans;
		if (i == s.length) {
			if (j == p.length) {
				ans = true;
			} else {
				ans = p[j] == '*' && f2(s, p, i, j + 1, dp);
			}
		} else if (j == p.length) {
			ans = false;
		} else {
			if (p[j] != '*') {
				ans = (s[i] == p[j] || p[j] == '?') && f2(s, p, i + 1, j + 1, dp);
			} else {
				ans = f2(s, p, i + 1, j, dp) || f2(s, p, i, j + 1, dp);
			}
		}
		dp[i][j] = ans ? 1 : 2;
		return ans;
	}

	// 严格位置依赖的动态规划
	public static boolean isMatch3(String str, String pat) {
		char[] s = str.toCharArray();
		char[] p = pat.toCharArray();
		int n = s.length;
		int m = p.length;
		boolean[][] dp = new boolean[n + 1][m + 1];
		dp[n][m] = true;
		for (int j = m - 1; j >= 0 && p[j] == '*'; j--) {
			dp[n][j] = true;
		}
		for (int i = n - 1; i >= 0; i--) {
			for (int j = m - 1; j >= 0; j--) {
				if (p[j] != '*') {
					dp[i][j] = (s[i] == p[j] || p[j] == '?') && dp[i + 1][j + 1];
				} else {
					dp[i][j] = dp[i + 1][j] || dp[i][j + 1];
				}
			}
		}
		return dp[0][0];
	}

}
