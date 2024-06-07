package class128;

// 增加限制的最长公共子序列问题
// 给定两个字符串s1和s2，s1长度为n，s2长度为m
// 返回s1和s2的最长公共子序列长度
// 注意：
// 两个字符串都只由小写字母组成
// 1 <= n <= 10^6
// 1 <= m <= 10^3
// 状态设计优化的经典题，对数器验证
public class Code03_AddLimitLcs {

	// 为了测试
	public static void main(String[] args) {
		int n = 100;
		int m = 100;
		int testTime = 10000;
		System.out.println("功能测试开始");
		for (int i = 0; i < testTime; i++) {
			int size1 = (int) (Math.random() * n) + 1;
			int size2 = (int) (Math.random() * m) + 1;
			String str1 = randomString(size1);
			String str2 = randomString(size2);
			int ans1 = lcs1(str1, str2);
			int ans2 = lcs2(str1, str2);
			if (ans1 != ans2) {
				System.out.println("出错了!");
			}
		}
		System.out.println("功能测试结束");
		System.out.println();

		System.out.println("性能测试开始");
		n = 1000000;
		m = 1000;
		System.out.println("n = " + n);
		System.out.println("m = " + m);
		String str1 = randomString(n);
		String str2 = randomString(m);
		long start, end;
		start = System.currentTimeMillis();
		lcs1(str1, str2);
		end = System.currentTimeMillis();
		System.out.println("lcs1方法运行时间 : " + (end - start) + " 毫秒");
		start = System.currentTimeMillis();
		lcs2(str1, str2);
		end = System.currentTimeMillis();
		System.out.println("lcs2方法运行时间 : " + (end - start) + " 毫秒");
		System.out.println("性能测试结束");
	}

	// 为了测试
	// 随机生成长度为n只有小写字母构成的字符串
	public static String randomString(int n) {
		char[] ans = new char[n];
		for (int i = 0; i < n; i++) {
			ans[i] = (char) ((int) (Math.random() * 26) + 'a');
		}
		return String.valueOf(ans);
	}

	// 经典动态规划的版本
	// 来自讲解067，题目3，最经典的方法4
	// 不利用任何限制，时间复杂度O(n*m)
	public static int lcs1(String str1, String str2) {
		char[] s1 = str1.toCharArray();
		char[] s2 = str2.toCharArray();
		int n = s1.length;
		int m = s2.length;
		int[][] dp = new int[n + 1][m + 1];
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= m; j++) {
				if (s1[i - 1] == s2[j - 1]) {
					dp[i][j] = 1 + dp[i - 1][j - 1];
				} else {
					dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
				}
			}
		}
		return dp[n][m];
	}

	// 利用限制条件做优化的版本
	// 时间复杂度O(26*n + m^2)
	public static int MAXN = 1000005;

	public static int MAXM = 1005;

	public static char[] s1;

	public static char[] s2;

	public static int n, m;

	public static int[] right = new int[26];

	public static int[][] next = new int[MAXN][26];

	public static int[][] dp = new int[MAXM][MAXM];

	public static int NA = Integer.MAX_VALUE;

	public static int lcs2(String str1, String str2) {
		s1 = str1.toCharArray();
		s2 = str2.toCharArray();
		n = s1.length;
		m = s2.length;
		build();
		int ans = 0;
		for (int j = m; j >= 1; j--) {
			if (f(m, j) != NA) {
				ans = j;
				break;
			}
		}
		return ans;
	}

	public static void build() {
		for (int j = 0; j < 26; j++) {
			right[j] = NA;
		}
		for (int i = n; i >= 0; i--) {
			for (int j = 0; j < 26; j++) {
				next[i][j] = right[j];
			}
			if (i > 0) {
				// s1的i长度，对应的字符是s1[i-1]
				right[s1[i - 1] - 'a'] = i;
			}
		}
		for (int i = 0; i <= m; i++) {
			for (int j = 0; j <= m; j++) {
				dp[i][j] = -1;
			}
		}
	}

	// 长度为i的s2前缀串，想和s1字符串形成长度为j的公共子序列
	// 返回至少需要多长的s1前缀串才能做到
	// 如果怎么都做不到，返回NA
	public static int f(int i, int j) {
		if (i < j) {
			return NA;
		}
		if (j == 0) {
			return 0;
		}
		if (dp[i][j] != -1) {
			return dp[i][j];
		}
		// s2前缀串长度为i，最后字符s2[i-1]
		int cha = s2[i - 1] - 'a';
		int ans = f(i - 1, j);
		int pre = f(i - 1, j - 1);
		if (pre != NA) {
			ans = Math.min(ans, next[pre][cha]);
		}
		dp[i][j] = ans;
		return ans;
	}

}
