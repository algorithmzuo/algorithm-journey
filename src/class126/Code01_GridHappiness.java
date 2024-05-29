package class126;

// 最大化网格幸福感
// 给定四个整数m、n、in、ex，表示m*n的网格，以及in个内向的人，ex个外向的人
// 你来决定网格中应当居住多少人，并为每个人分配一个网格单元，不必让所有人都生活在网格中
// 每个人的幸福感计算如下：
// 内向的人开始时有120幸福感，但每存在一个邻居，他都会失去30幸福感
// 外向的人开始时有40幸福感，但每存在一个邻居，他都会得到20幸福感
// 邻居只包含上、下、左、右四个方向
// 网格幸福感是每个人幸福感的总和，返回最大可能的网格幸福感
// 1 <= m、n <= 5
// 1 <= in、ex <= 6
// 测试链接 : https://leetcode.cn/problems/maximize-grid-happiness/
// 有兴趣的同学可以自己改一下空间压缩的版本
public class Code01_GridHappiness {

	public static int MAXN = 5;

	public static int MAXM = 5;

	public static int MAXP = 7;

	public static int MAXS = (int) Math.pow(3, MAXM);

	public static int n;

	public static int m;

	public static int maxs;

	public static int[][][][][] dp = new int[MAXN][MAXM][MAXS][MAXP][MAXP];

	public static int getMaxGridHappiness(int rows, int cols, int in, int ex) {
		n = Math.max(rows, cols);
		m = Math.min(rows, cols);
		maxs = (int) Math.pow(3, m);
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				for (int s = 0; s < maxs; s++) {
					for (int a = 0; a <= in; a++) {
						for (int b = 0; b <= ex; b++) {
							dp[i][j][s][a][b] = -1;
						}
					}
				}
			}
		}
		return dp(0, 0, 0, in, ex, 1);
	}

	public static int dp(int i, int j, int s, int a, int b, int bit) {
		if (i == n) {
			return 0;
		}
		if (j == m) {
			return dp(i + 1, 0, s, a, b, 1);
		}
		if (dp[i][j][s][a][b] != -1) {
			return dp[i][j][s][a][b];
		}
		// 当前不安排人
		int ans = dp(i, j + 1, set(s, bit, 0), a, b, bit * 3);
		// 当前安排人
		int up = get(s, bit);
		int left = j == 0 ? 0 : get(s, bit / 3);
		int neighbor = 0;
		int pre = 0;
		if (up != 0) {
			neighbor++;
			pre += up == 1 ? -30 : 20;
		}
		if (left != 0) {
			neighbor++;
			pre += left == 1 ? -30 : 20;
		}
		if (a > 0) {
			ans = Math.max(ans, pre + 120 - neighbor * 30 + dp(i, j + 1, set(s, bit, 1), a - 1, b, bit * 3));
		}
		if (b > 0) {
			ans = Math.max(ans, pre + 40 + neighbor * 20 + dp(i, j + 1, set(s, bit, 2), a, b - 1, bit * 3));
		}
		dp[i][j][s][a][b] = ans;
		return ans;
	}

	// s表示当前状态，按照3进制来理解
	// 当前来到第j位，3的j次方是bit
	// 返回s第j位的值
	public static int get(int s, int bit) {
		return s / bit % 3;
	}

	// s表示当前状态，按照3进制来理解
	// 当前来到第j位，3的j次方是bit
	// 把s第j位的值设置成v，返回新状态
	public static int set(int s, int bit, int v) {
		return s - get(s, bit) * bit + v * bit;
	}

}
