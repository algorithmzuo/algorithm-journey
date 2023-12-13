package class084;

// 统计整数数目
// 给你两个数字字符串 num1 和 num2 ，以及两个整数max_sum和min_sum
// 如果一个整数 x 满足以下条件，我们称它是一个好整数
// num1 <= x <= num2
// min_sum <= digit_sum(x) <= max_sum
// 请你返回好整数的数目
// 答案可能很大请返回答案对10^9 + 7 取余后的结果
// 注意，digit_sum(x)表示x各位数字之和
// 测试链接 : https://leetcode.cn/problems/count-of-integers/
public class Code04_CountOfIntegers {

	public static int MOD = 1000000007;

	public static int MAXN = 23;

	public static int MAXM = 401;

	public static int[][][] dp = new int[MAXN][MAXM][2];

	public static void build() {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j <= m; j++) {
				dp[i][j][0] = -1;
				dp[i][j][1] = -1;
			}
		}
	}

	public static char[] num;

	public static int n, m;

	public static int count(String num1, String num2, int min_sum, int max_sum) {
		num = num2.toCharArray();
		n = num2.length();
		m = max_sum;
		build();
		int ans = f(0, 0, 0, min_sum, max_sum);
		num = num1.toCharArray();
		n = num1.length();
		build();
		ans = (ans - f(0, 0, 0, min_sum, max_sum) + MOD) % MOD;
		if (check(min_sum, max_sum)) {
			ans = (ans + 1) % MOD;
		}
		return ans;
	}

	public static int f(int i, int sum, int less, int min, int max) {
		if (sum > max) {
			return 0;
		}
		if (sum + (n - i) * 9 < min) {
			return 0;
		}
		if (i == n) {
			return 1;
		}
		if (dp[i][sum][less] != -1) {
			return dp[i][sum][less];
		}
		int cur = num[i] - '0';
		int ans = 0;
		if (less == 0) {
			for (int pick = 0; pick < cur; pick++) {
				ans = (ans + f(i + 1, sum + pick, 1, min, max)) % MOD;
			}
			ans = (ans + f(i + 1, sum + cur, 0, min, max)) % MOD;
		} else {
			for (int pick = 0; pick <= 9; pick++) {
				ans = (ans + f(i + 1, sum + pick, 1, min, max)) % MOD;
			}
		}
		dp[i][sum][less] = ans;
		return ans;
	}

	public static boolean check(int min, int max) {
		int sum = 0;
		for (char cha : num) {
			sum += cha - '0';
		}
		return sum >= min && sum <= max;
	}

}
