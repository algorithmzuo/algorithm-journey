package class096;

import java.util.Arrays;

// 两堆石头的巴什博弈
// 有两堆石头，数量分别为a、b
// 两个人轮流拿，每次可以选择其中一堆石头，拿1~m颗
// 拿到最后一颗石子的人获胜，根据a、b、m返回谁赢
// 来自真实大厂笔试，没有在线测试，对数器验证
public class Code03_TwoStonesBashGame {

	public static int MAXN = 101;

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
				// 后续过程的赢家是后续过程的后手
				// 那就表示此时的先手，通过这个后续过程，能赢
				ans = "先手";
			}
			if (ans.equals("先手")) {
				// 后续过程的赢家是后续过程的后手
				// 那就表示此时的先手，通过这个后续过程，能赢
				break;
			}
		}
		for (int pick = 1; pick <= Math.min(b, m); pick++) {
			if (win1(a, b - pick, m).equals("后手")) {
				// 后续过程的赢家是后续过程的后手
				// 那就表示此时的先手，通过这个后续过程，能赢
				ans = "先手";
			}
			if (ans.equals("先手")) {
				break;
			}
		}
		dp[a][b][m] = ans;
		return ans;
	}

	// sg定理
	public static String win2(int a, int b, int m) {
		int n = Math.max(a, b);
		int[] sg = new int[n + 1];
		boolean[] appear = new boolean[m + 1];
		for (int i = 1; i <= n; i++) {
			Arrays.fill(appear, false);
			for (int j = 1; j <= m && i - j >= 0; j++) {
				appear[sg[i - j]] = true;
			}
			for (int s = 0; s <= m; s++) {
				if (!appear[s]) {
					sg[i] = s;
					break;
				}
			}
		}
		return (sg[a] ^ sg[b]) != 0 ? "先手" : "后手";
	}

	// 时间复杂度O(1)的最优解
	// 其实是根据方法2中的sg表观察出来的
	public static String win3(int a, int b, int m) {
		return a % (m + 1) != b % (m + 1) ? "先手" : "后手";
	}

	public static void main(String[] args) {
		System.out.println("测试开始");
		for (int a = 0; a < MAXN; a++) {
			for (int b = 0; b < MAXN; b++) {
				for (int m = 1; m < MAXN; m++) {
					String ans1 = win1(a, b, m);
					String ans2 = win2(a, b, m);
					String ans3 = win3(a, b, m);
					if (!ans1.equals(ans2) || !ans1.equals(ans3)) {
						System.out.println("出错了！");
					}
				}
			}
		}
		System.out.println("测试结束");
	}

}
