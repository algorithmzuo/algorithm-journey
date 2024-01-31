package class101;

// 找到所有好字符串
// 给你两个长度为n的字符串s1和s2，以及一个字符串evil
// 好字符串的定义为 : 长度为n，字典序大于等于s1，字典序小于等于s2，且不包含evil字符串
// 返回好字符串的数量
// 由于答案很大返回对1000000007取余的结果
// 测试链接 : https://leetcode.cn/problems/find-all-good-strings/
public class Code04_FindAllGoodStrings {

	public static int MOD = 1000000007;

	public static int MAXN = 501;

	public static int MAXM = 51;

	public static int[][][] dp = new int[MAXN][MAXM][2];

	public static int[] next = new int[MAXM];

	public static void clear(int n, int m) {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				dp[i][j][0] = -1;
				dp[i][j][1] = -1;
			}
		}
	}

	public static int findGoodStrings(int n, String str1, String str2, String evil) {
		char[] s1 = str1.toCharArray();
		char[] s2 = str2.toCharArray();
		char[] e = evil.toCharArray();
		int m = e.length;
		nextArray(e, m);
		clear(n, m);
		int ans = f(s2, e, n, m, 0, 0, 0);
		clear(n, m);
		ans = (ans - f(s1, e, n, m, 0, 0, 0) + MOD) % MOD;
		if (kmp(s1, e, n, m) == -1) {
			ans = (ans + 1) % MOD;
		}
		return ans;
	}

	public static int f(char[] s, char[] e, int n, int m, int i, int j, int free) {
		if (j == m) {
			return 0;
		}
		if (i == n) {
			return 1;
		}
		if (dp[i][j][free] != -1) {
			return dp[i][j][free];
		}
		int ans = 0, k;
		for (char cur = 'a'; cur <= (free == 0 ? (s[i] - 1) : 'z'); cur++) {
			k = j;
			while (k != -1 && cur != e[k]) {
				k = next[k];
			}
			ans = (ans + f(s, e, n, m, i + 1, k + 1, 1)) % MOD;
		}
		if (free == 0) {
			k = j;
			while (k != -1 && s[i] != e[k]) {
				k = next[k];
			}
			ans = (ans + f(s, e, n, m, i + 1, k + 1, 0)) % MOD;
		}
		dp[i][j][free] = ans;
		return ans;
	}

	public static void nextArray(char[] e, int m) {
		next[0] = -1;
		next[1] = 0;
		int i = 2, cn = 0;
		while (i < m) {
			if (e[i - 1] == e[cn]) {
				next[i++] = ++cn;
			} else if (cn > 0) {
				cn = next[cn];
			} else {
				next[i++] = 0;
			}
		}
	}

	public static int kmp(char[] s, char[] e, int n, int m) {
		int x = 0, y = 0;
		while (x < n && y < m) {
			if (s[x] == e[y]) {
				x++;
				y++;
			} else if (y == 0) {
				x++;
			} else {
				y = next[y];
			}
		}
		return y == m ? x - y : -1;
	}

}
