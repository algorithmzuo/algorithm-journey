package class095;

// 拿蛋糕(巴什博奕+尼姆博弈)
// 有a块草莓蛋糕，有b块芝士蛋糕，两人轮流拿蛋糕
// 每次不管是谁只能选择在草莓蛋糕和芝士蛋糕中拿一种
// 拿的数量在1~m之间随意，谁先拿完最后的蛋糕谁赢
// 返回先手赢还是后手赢
// 来自真实大厂笔试，没有在线测试，对数器验证
public class Code05_PickCakes {

	public static int MAXN = 151;

	public static String[][][] dp = new String[MAXN][MAXN][MAXN];

	// 动态规划方法彻底尝试
	// 为了验证
	public static String win1(int a, int b, int m) {
		if (m >= Math.max(a, b)) {
			return a != b ? "先手" : "后手";
		}
		if (a == b) {
			return "后手";
		}
		if (dp[a][b][m] != null) {
			return dp[a][b][m];
		}
		String ans = "后手";
		for (int pick = 1; pick <= Math.min(a, m); pick++) {
			if (win1(a - pick, b, m).equals("后手")) {
				ans = "先手";
			}
			if (ans.equals("先手")) {
				break;
			}
		}
		for (int pick = 1; pick <= Math.min(b, m); pick++) {
			if (win1(a, b - pick, m).equals("后手")) {
				ans = "先手";
			}
			if (ans.equals("先手")) {
				break;
			}
		}
		dp[a][b][m] = ans;
		return ans;
	}

	// 正式方法
	// 时间复杂度O(1)
	public static String win2(int a, int b, int m) {
		if (m >= Math.max(a, b)) {
			// nim博弈
			return a != b ? "先手" : "后手";
		}
		if (a == b) {
			return "后手";
		}
		// 如果 a != b
		// 关注a和b的差值，
		// 谁最先遇到差值为0谁输
		// 那么这就是巴什博奕
		return (Math.max(a, b) - Math.min(a, b)) % (m + 1) != 0 ? "先手" : "后手";
	}

	public static void main(String[] args) {
		System.out.println("测试开始");
		for (int a = 0; a < MAXN; a++) {
			for (int b = 0; b < MAXN; b++) {
				for (int m = 0; m < MAXN; m++) {
					String ans1 = win1(a, b, m);
					String ans2 = win2(a, b, m);
					if (!ans1.equals(ans2)) {
						System.out.println("出错了！");
					}
				}
			}
		}
		System.out.println("测试结束");
	}

}
