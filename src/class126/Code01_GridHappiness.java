package class126;

// 测试链接 : https://leetcode.cn/problems/maximize-grid-happiness/
// 有兴趣的同学可以自己改一下空间压缩的版本
public class Code01_GridHappiness {

	public static int MAXN = 5;

	public static int MAXM = 5;

	public static int MAXS = (int) Math.pow(3, MAXM);

	public static int MAXP = 7;

	public static int n;

	public static int m;

	public static int maxs;

	public static int[][][][][] dp = new int[MAXN][MAXM][MAXS][MAXP][MAXP];

	public static int getMaxGridHappiness(int cols, int rows, int in, int ex) {
		n = rows;
		m = cols;
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
		int up = (s / bit) % 3;
		int left = j == 0 ? 0 : ((s / (bit / 3)) % 3);
		s -= up * bit;
		// 当前不安排人
		int ans = dp(i, j + 1, s, a, b, bit * 3);
		// 当前安排人
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
			ans = Math.max(ans, pre + 120 - neighbor * 30 + dp(i, j + 1, s + bit, a - 1, b, bit * 3));
		}
		if (b > 0) {
			ans = Math.max(ans, pre + 40 + neighbor * 20 + dp(i, j + 1, s + (bit << 1), a, b - 1, bit * 3));
		}
		s += up * bit;
		dp[i][j][s][a][b] = ans;
		return ans;
	}

}
