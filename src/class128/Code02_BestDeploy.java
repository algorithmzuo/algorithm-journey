package class128;

// 最好的部署
// 一共有n台机器，编号1 ~ n，所有机器排成一排
// 你只能一台一台的部署机器，你可以决定部署的顺序，最终所有机器都要部署
// 给定三个数组no[]、one[]、both[]
// no[i] : 如果i号机器部署时，相邻没有机器部署，此时能获得的收益
// one[i] : 如果i号机器部署时，相邻有一台机器部署，此时能获得的收益
// both[i] : 如果i号机器部署时，相邻有两台机器部署，此时能获得的收益
// 第1号机器、第n号机器当然不会有两台相邻的机器
// 返回部署的最大收益
// 1 <= n <= 10^5
// 0 <= no[i]、one[i]、both[i]
// 来自真实大厂笔试，对数器验证
public class Code02_BestDeploy {

	public static int MAXN = 1001;

	public static int[] no = new int[MAXN];

	public static int[] one = new int[MAXN];

	public static int[] both = new int[MAXN];

	public static int n;

	// 区间dp的尝试
	// 时间复杂度O(n^3)
	// 正确但是并不推荐
	public static int best1() {
		int[][] dp = new int[n + 1][n + 1];
		for (int l = 1; l <= n; l++) {
			for (int r = l; r <= n; r++) {
				dp[l][r] = -1;
			}
		}
		return f(1, n, dp);
	}

	// 部署l...r范围上的机器
	// 并且l-1和r+1的机器一定都没有部署
	// 返回部署的最大收益
	public static int f(int l, int r, int[][] dp) {
		if (l == r) {
			return no[l];
		}
		if (dp[l][r] != -1) {
			return dp[l][r];
		}
		int ans = f(l + 1, r, dp) + one[l];
		ans = Math.max(ans, f(l, r - 1, dp) + one[r]);
		for (int i = l + 1; i < r; i++) {
			ans = Math.max(ans, f(l, i - 1, dp) + f(i + 1, r, dp) + both[i]);
		}
		dp[l][r] = ans;
		return ans;
	}

	// 线性dp的尝试
	// 时间复杂度O(n)
	// 推荐
	public static int best2() {
		// dp[i][0] : i号机器的前一台机器没有部署的情况下，部署i...的机器获得的最大收益
		// dp[i][1] : i号机器的前一台机器已经部署的情况下，部署i...的机器获得的最大收益
		int[][] dp = new int[n + 1][2];
		dp[n][0] = no[n];
		dp[n][1] = one[n];
		for (int i = n - 1; i >= 1; i--) {
			dp[i][0] = Math.max(no[i] + dp[i + 1][1], one[i] + dp[i + 1][0]);
			dp[i][1] = Math.max(one[i] + dp[i + 1][1], both[i] + dp[i + 1][0]);
		}
		return dp[1][0];
	}

	// 为了测试
	public static void random(int size, int v) {
		n = size;
		for (int i = 1; i <= n; i++) {
			no[i] = (int) (Math.random() * v);
			one[i] = (int) (Math.random() * v);
			both[i] = (int) (Math.random() * v);
		}
	}

	// 为了测试
	public static void main(String[] args) {
		int maxn = 100;
		int maxv = 100;
		int testTime = 10000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int size = (int) (Math.random() * maxn) + 1;
			random(size, maxv);
			int ans1 = best1();
			int ans2 = best2();
			if (ans1 != ans2) {
				System.out.println("出错了!");
			}
		}
		System.out.println("测试结束");
	}

}
