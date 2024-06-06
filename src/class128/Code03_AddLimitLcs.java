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

	public static int NA = Integer.MAX_VALUE;

	// 利用限制条件做优化的版本
	// 时间复杂度O(26*n + m^2)
	public static int lcs2(String str1, String str2) {
		char[] s1 = str1.toCharArray();
		char[] s2 = str2.toCharArray();
		int n = s1.length;
		int m = s2.length;
		int[] right = new int[26];
		for (int j = 0; j < 26; j++) {
			right[j] = NA;
		}
		int[][] next = new int[n + 1][26];
		for (int i = n; i >= 0; i--) {
			for (int j = 0; j < 26; j++) {
				next[i][j] = right[j];
			}
			if (i > 0) {
				right[s1[i - 1] - 'a'] = i;
			}
		}
		int[][] dp = new int[m + 1][m + 1];
		for (int i = 1, cha; i <= m; i++) {
			cha = s2[i - 1] - 'a';
			for (int j = 1; j <= i; j++) {
				dp[i][j] = NA;
				if (i - 1 >= j) {
					dp[i][j] = dp[i - 1][j];
				}
				if (dp[i - 1][j - 1] != NA) {
					dp[i][j] = Math.min(dp[i][j], next[dp[i - 1][j - 1]][cha]);
				}
			}
		}
		int ans = 0;
		for (int j = m; j >= 1; j--) {
			if (dp[m][j] != NA) {
				ans = j;
				break;
			}
		}
		return ans;
	}

}
