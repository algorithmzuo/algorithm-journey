package class095;

// 巴什博弈(Bash Game)
// 一共有n颗石子，两个人轮流拿，每次可以拿1~m颗石子
// 拿到最后一颗石子的人获胜，根据n、m返回谁赢
public class Code01_BashGame {

	// 动态规划进行所有尝试
	// 为了验证
	public static int MAXN = 1001;

	public static String[][] dp = new String[MAXN][MAXN];

	public static String bashGame1(int n, int m) {
		if (n == 0) {
			return "后手";
		}
		if (dp[n][m] != null) {
			return dp[n][m];
		}
		String ans = "后手";
		for (int pick = 1; pick <= m; pick++) {
			if (bashGame1(n - pick, m).equals("后手")) {
				// 后续过程的赢家是后续过程的后手
				// 那就表示此时的先手，通过这个后续过程，能赢
				ans = "先手";
				break;
			}
		}
		dp[n][m] = ans;
		return ans;
	}

	// 正式方法
	public static String bashGame2(int n, int m) {
		return n % (m + 1) != 0 ? "先手" : "后手";
	}

	// 为了验证
	public static void main(String[] args) {
		int V = 500; // 需要比MAXN小
		int testTimes = 5000;
		System.out.println("测试开始");
		for (int i = 0; i < testTimes; i++) {
			int n = (int) (Math.random() * V);
			int m = (int) (Math.random() * V) + 1;
			String ans1 = bashGame1(n, m);
			String ans2 = bashGame2(n, m);
			if (!ans1.equals(ans2)) {
				System.out.println("出错了!");
			}
		}
		System.out.println("测试结束");
	}

}
