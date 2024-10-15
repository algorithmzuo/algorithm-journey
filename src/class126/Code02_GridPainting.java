package class126;

// 用三种不同颜色为网格涂色
// 给你两个整数m和n，表示m*n的网格，其中每个单元格最开始是白色
// 请你用红、绿、蓝三种颜色为每个单元格涂色，所有单元格都需要被涂色
// 要求相邻单元格的颜色一定要不同
// 返回网格涂色的方法数，答案对 1000000007 取模
// 1 <= m <= 5
// 1 <= n <= 1000
// 测试链接 : https://leetcode.cn/problems/painting-a-grid-with-three-different-colors/
// 有兴趣的同学可以自己改一下空间压缩的版本
public class Code02_GridPainting {

	public static int MAXN = 1001;

	public static int MAXM = 5;

	public static int MAXS = (int) Math.pow(3, MAXM);

	public static int MOD = 1000000007;

	public static int n;

	public static int m;

	public static int maxs;

	public static int[][][] dp = new int[MAXN][MAXM][MAXS];

	public static int[] first = new int[MAXS];

	public static int size;

	public static int colorTheGrid(int rows, int cols) {
		build(rows, cols);
		int ans = 0;
		for (int i = 0; i < size; i++) {
			ans = (ans + f(1, 0, first[i], 1)) % MOD;
		}
		return ans;
	}

	public static void build(int rows, int cols) {
		n = Math.max(rows, cols);
		m = Math.min(rows, cols);
		maxs = (int) Math.pow(3, m);
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				for (int s = 0; s < maxs; s++) {
					dp[i][j][s] = -1;
				}
			}
		}
		size = 0;
		dfs(0, 0, 1);
	}

	// 取得所有第一行的有效状态
	public static void dfs(int j, int s, int bit) {
		if (j == m) {
			first[size++] = s;
		} else {
			int left = j == 0 ? -1 : get(s, bit / 3);
			if (left != 0) {
				dfs(j + 1, set(s, bit, 0), bit * 3);
			}
			if (left != 1) {
				dfs(j + 1, set(s, bit, 1), bit * 3);
			}
			if (left != 2) {
				dfs(j + 1, set(s, bit, 2), bit * 3);
			}
		}
	}

	public static int f(int i, int j, int s, int bit) {
		if (i == n) {
			return 1;
		}
		if (j == m) {
			return f(i + 1, 0, s, 1);
		}
		if (dp[i][j][s] != -1) {
			return dp[i][j][s];
		}
		// 上方的颜色
		int up = get(s, bit);
		// 左侧的颜色，-1代表左侧没有格子
		int left = j == 0 ? -1 : get(s, bit / 3);
		int ans = 0;
		if (up != 0 && left != 0) {
			ans = (ans + f(i, j + 1, set(s, bit, 0), bit * 3)) % MOD;
		}
		if (up != 1 && left != 1) {
			ans = (ans + f(i, j + 1, set(s, bit, 1), bit * 3)) % MOD;
		}
		if (up != 2 && left != 2) {
			ans = (ans + f(i, j + 1, set(s, bit, 2), bit * 3)) % MOD;
		}
		dp[i][j][s] = ans;
		return ans;
	}

	public static int get(int s, int bit) {
		return s / bit % 3;
	}

	public static int set(int s, int bit, int v) {
		return s - get(s, bit) * bit + v * bit;
	}

}
