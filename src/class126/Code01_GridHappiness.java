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
		return f(0, 0, 0, in, ex, 1);
	}

	// 当前来到i行j列的格子
	// s表示轮廓线的状态，可以得到左侧格子放了什么人，上侧格子放了什么人
	// 内向的人还有a个，外向的人还有b个
	// 返回最大的幸福感
	// 注意 : bit等于3的j次方，bit不是关键可变参数，因为bit的值被j的值决定
	public static int f(int i, int j, int s, int a, int b, int bit) {
		if (i == n) {
			return 0;
		}
		if (j == m) {
			return f(i + 1, 0, s, a, b, 1);
		}
		if (dp[i][j][s][a][b] != -1) {
			return dp[i][j][s][a][b];
		}
		// 当前格子不安置人
		int ans = f(i, j + 1, set(s, bit, 0), a, b, bit * 3);
		// 上方邻居的状态
		int up = get(s, bit);
		// 左方邻居的状态
		int left = j == 0 ? 0 : get(s, bit / 3);
		// 邻居人数
		int neighbor = 0;
		// 如果放置人，之前得到的幸福感要如何变化
		int pre = 0;
		if (up != 0) {
			neighbor++;
			// 上邻居是内向的人，幸福感要减30；是外向的人，幸福感要加20
			pre += up == 1 ? -30 : 20;
		}
		if (left != 0) {
			neighbor++;
			// 左邻居是内向的人，幸福感要减30；是外向的人，幸福感要加20
			pre += left == 1 ? -30 : 20;
		}
		if (a > 0) {
			// 当前格子决定放内向的人
			ans = Math.max(ans, pre + 120 - neighbor * 30 + f(i, j + 1, set(s, bit, 1), a - 1, b, bit * 3));
		}
		if (b > 0) {
			// 当前格子决定放外向的人
			ans = Math.max(ans, pre + 40 + neighbor * 20 + f(i, j + 1, set(s, bit, 2), a, b - 1, bit * 3));
		}
		dp[i][j][s][a][b] = ans;
		return ans;
	}

	// s表示当前状态，按照3进制来理解
	// 当前来到第j号格，3的j次方是bit
	// 返回s第j号格的值
	public static int get(int s, int bit) {
		return s / bit % 3;
	}

	// s表示当前状态，按照3进制来理解
	// 当前来到第j号格，3的j次方是bit
	// 把s第j号格的值设置成v，返回新状态
	public static int set(int s, int bit, int v) {
		return s - get(s, bit) * bit + v * bit;
	}

}
