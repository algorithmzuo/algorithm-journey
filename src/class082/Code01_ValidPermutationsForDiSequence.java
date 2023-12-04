package class082;

// 测试链接 : https://leetcode.cn/problems/valid-permutations-for-di-sequence/
public class Code01_ValidPermutationsForDiSequence {

	public static int numPermsDISequence1(String s) {
		return f(s.toCharArray(), 0, s.length() + 1, s.length() + 1);
	}

	public static int f(char[] s, int i, int less, int n) {
		int ans = 0;
		if (i == n) {
			ans = 1;
		} else if (i == 0 || s[i - 1] == 'D') {
			for (int nextLess = 0; nextLess < less; nextLess++) {
				ans += f(s, i + 1, nextLess, n);
			}
		} else {
			for (int nextLess = less; nextLess < n - i; nextLess++) {
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
					for (int nextLess = less; nextLess < n - i; nextLess++) {
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
				for (int less = 0; less <= n; less++) {
					dp[i][less] = less - 1 >= 0 ? ((dp[i][less - 1] + dp[i + 1][less - 1]) % mod) : 0;
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
