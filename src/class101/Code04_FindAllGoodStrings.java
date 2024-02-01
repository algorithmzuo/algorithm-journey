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
		// <=s2的好字符串数量
		int ans = f(s2, e, n, m, 0, 0, 0);
		clear(n, m);
		// 减去<=s1的好字符串数量
		ans = (ans - f(s1, e, n, m, 0, 0, 0) + MOD) % MOD;
		if (kmp(s1, e, n, m) == -1) {
			ans = (ans + 1) % MOD;
		}
		return ans;
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

	// 时间复杂度O(n * m * 2 * 26)
	// s、e、n、m都是固定参数
	// 0...i-1已经做了决策，已经匹配了e[0...j-1]这个部分
	// 当前来到s[i]时，最先该考察的匹配位置是e[j]
	// 之前的决策如果已经比s小了，free == 1
	// 之前的决策如果和s[0..i-1]一样，free == 0
	// 返回后续的所有决策中，不含有e字符串且<=s的决策有多少个，同时长度需要为n
	// 核心 : 利用e字符串的next数组加速匹配
	public static int f(char[] s, char[] e, int n, int m, int i, int j, int free) {
		if (j == m) {
			// 一旦配出了e的整体
			// 说明之前的决策已经违规
			// 后续有效决策数量0
			return 0;
		}
		// 能跑如下代码
		// 之前的决策不含有整个e字符串
		if (i == n) {
			// 说明所有决策已经做完了
			// 并且不含有e字符串
			// 同时决策的每一步都保证了不会比s大
			// 返回1种有效决策(之前做的所有决定)
			return 1;
		}
		if (dp[i][j][free] != -1) {
			return dp[i][j][free];
		}
		char cur = s[i];
		int ans = 0;
		if (free == 0) {
			// 之前的决策和s的状况一样
			// 当前尝试比cur小的字符
			for (char pick = 'a'; pick < cur; pick++) {
				ans = (ans + f(s, e, n, m, i + 1, jump(pick, e, j) + 1, 1)) % MOD;
			}
			// 当前尝试等于cur的字符
			ans = (ans + f(s, e, n, m, i + 1, jump(cur, e, j) + 1, 0)) % MOD;
		} else {
			// 之前的决策已经确定小于s了
			// 当前a~z随便尝试
			for (char pick = 'a'; pick <= 'z'; pick++) {
				ans = (ans + f(s, e, n, m, i + 1, jump(pick, e, j) + 1, 1)) % MOD;
			}
		}
		dp[i][j][free] = ans;
		return ans;
	}

	// 当前字符是pick，一开始匹配e[j]
	// 根据next数组加速匹配，返回匹配出来的位置
	// 如果匹配不出来返回-1
	// 单次调用的时间复杂度O(1)
	public static int jump(char pick, char[] e, int j) {
		while (j >= 0 && pick != e[j]) {
			j = next[j];
		}
		return j;
	}

}
