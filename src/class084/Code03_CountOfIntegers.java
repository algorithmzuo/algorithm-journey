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
public class Code03_CountOfIntegers {

	public static int MOD = 1000000007;

	public static int MAXN = 23;

	public static int MAXM = 401;

	public static int[][][] dp = new int[MAXN][MAXM][2];

	public static void build() {
		for (int i = 0; i < len; i++) {
			for (int j = 0; j <= max; j++) {
				dp[i][j][0] = -1;
				dp[i][j][1] = -1;
			}
		}
	}

	public static char[] num;

	public static int min, max, len;

	public static int count(String num1, String num2, int min_sum, int max_sum) {
		min = min_sum;
		max = max_sum;
		num = num2.toCharArray();
		len = num2.length();
		build();
		int ans = f(0, 0, 0);
		num = num1.toCharArray();
		len = num1.length();
		build();
		ans = (ans - f(0, 0, 0) + MOD) % MOD;
		if (check()) {
			ans = (ans + 1) % MOD;
		}
		return ans;
	}

	// 注意：
	// 数字，char[] num
	// 数字长度，int len
	// 累加和最小要求，int min
	// 累加和最大要求，int max
	// 这四个变量都是全局静态变量，所以不用带参数，直接访问即可
	// 递归含义：
	// 从num的高位出发，当前来到i位上
	// 之前决定的数字累加和是sum
	// 之前的决定已经比num小，后续可以自由选择数字，那么free == 1
	// 之前的决定和num一样，后续不可以自由选择数字，那么free == 0
	// 返回有多少种可能性
	public static int f(int i, int sum, int free) {
		if (sum > max) {
			return 0;
		}
		if (sum + (len - i) * 9 < min) {
			return 0;
		}
		if (i == len) {
			return 1;
		}
		if (dp[i][sum][free] != -1) {
			return dp[i][sum][free];
		}
		// cur : num当前位的数字
		int cur = num[i] - '0';
		int ans = 0;
		if (free == 0) {
			// 还不能自由选择
			for (int pick = 0; pick < cur; pick++) {
				ans = (ans + f(i + 1, sum + pick, 1)) % MOD;
			}
			ans = (ans + f(i + 1, sum + cur, 0)) % MOD;
		} else {
			// 可以自由选择
			for (int pick = 0; pick <= 9; pick++) {
				ans = (ans + f(i + 1, sum + pick, 1)) % MOD;
			}
		}
		dp[i][sum][free] = ans;
		return ans;
	}

	public static boolean check() {
		int sum = 0;
		for (char cha : num) {
			sum += cha - '0';
		}
		return sum >= min && sum <= max;
	}

}
