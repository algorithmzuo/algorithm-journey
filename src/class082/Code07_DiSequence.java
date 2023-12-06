package class082;

// DI序列的有效排列
// 给定一个长度为n的字符串s，其中s[i]是:
// "D"意味着减少，"I"意味着增加
// 有效排列是对有n+1个在[0,n]范围内的整数的一个排列perm，使得对所有的i：
// 如果 s[i] == 'D'，那么 perm[i] > perm[i+1]
// 如果 s[i] == 'I'，那么 perm[i] < perm[i+1]
// 返回有效排列的perm的数量
// 因为答案可能很大，所以请返回你的答案对10^9+7取余
// 测试链接 : https://leetcode.cn/problems/valid-permutations-for-di-sequence/
public class Code07_DiSequence {

	public static int numPermsDISequence1(String s) {
		return f(s.toCharArray(), 0, s.length() + 1, s.length() + 1);
	}

	// 猜法很妙！
	// 一共有n个数字，位置范围0~n-1
	// 当前来到i位置，i-1位置的数字已经确定，i位置的数字还没确定
	// i-1位置和i位置的关系，是s[i-1] : D、I
	// 0~i-1范围上是已经使用过的数字，i个
	// 还没有使用过的数字中，比i-1位置的数字小的，有less个
	// 还没有使用过的数字中，比i-1位置的数字大的，有n - i - less个
	// 返回后续还有多少种有效的排列
	public static int f(char[] s, int i, int less, int n) {
		int ans = 0;
		if (i == n) {
			ans = 1;
		} else if (i == 0 || s[i - 1] == 'D') {
			for (int nextLess = 0; nextLess < less; nextLess++) {
				ans += f(s, i + 1, nextLess, n);
			}
		} else {
			for (int nextLess = less, k = 1; k <= n - i - less; k++, nextLess++) {
				ans += f(s, i + 1, nextLess, n);
			}
		}
		return ans;
	}

	public static int numPermsDISequence2(String str) {
		int mod = 1000000007;
		char[] s = str.toCharArray();
		int n = s.length + 1;
		int[][] dp = new int[n + 1][n + 1];
		for (int less = 0; less <= n; less++) {
			dp[n][less] = 1;
		}
		for (int i = n - 1; i >= 0; i--) {
			for (int less = 0; less <= n; less++) {
				if (i == 0 || s[i - 1] == 'D') {
					for (int nextLess = 0; nextLess < less; nextLess++) {
						dp[i][less] = (dp[i][less] + dp[i + 1][nextLess]) % mod;
					}
				} else {
					for (int nextLess = less, k = 1; k <= n - i - less; k++, nextLess++) {
						dp[i][less] = (dp[i][less] + dp[i + 1][nextLess]) % mod;
					}
				}
			}
		}
		return dp[0][n];
	}

	// 通过观察方法2，得到优化枚举的方法
	public static int numPermsDISequence3(String str) {
		int mod = 1000000007;
		char[] s = str.toCharArray();
		int n = s.length + 1;
		int[][] dp = new int[n + 1][n + 1];
		for (int less = 0; less <= n; less++) {
			dp[n][less] = 1;
		}
		for (int i = n - 1; i >= 0; i--) {
			if (i == 0 || s[i - 1] == 'D') {
				dp[i][1] = dp[i + 1][0];
				for (int less = 2; less <= n; less++) {
					dp[i][less] = (dp[i][less - 1] + dp[i + 1][less - 1]) % mod;
				}
			} else {
				dp[i][n - i - 1] = dp[i + 1][n - i - 1];
				for (int less = n - i - 2; less >= 0; less--) {
					dp[i][less] = (dp[i][less + 1] + dp[i + 1][less]) % mod;
				}
			}
		}
		return dp[0][n];
	}

}
