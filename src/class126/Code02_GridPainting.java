package class126;

// 测试链接 : https://leetcode.cn/problems/painting-a-grid-with-three-different-colors/
// 有兴趣的同学可以自己改一下空间压缩的版本
public class Code02_GridPainting {

	public static int MAXN = 1001;

	public static int MAXM = 5;

	public static int MAXS = (int) Math.pow(3, MAXM);

	public static int n;

	public static int m;

	public static int maxs;

	public static int[][][] dp = new int[MAXN][MAXM][MAXS];

	public static int[] first = new int[MAXS];

	public static int size;

	public static int MOD = 1000000007;

	public static int colorTheGrid(int cols, int rows) {
		build(cols, rows);
		int ans = 0;
		for (int i = 0; i < size; i++) {
			ans = (ans + dp(1, 0, first[i], 1)) % MOD;
		}
		return ans;
	}

	public static void build(int c, int r) {
		n = r;
		m = c;
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

	public static void dfs(int j, int s, int bit) {
		if (j == m) {
			first[size++] = s;
		} else {
			int left = j == 0 ? -1 : ((s / (bit / 3)) % 3);
			if (left != 0) {
				dfs(j + 1, s, bit * 3);
			}
			if (left != 1) {
				dfs(j + 1, s + bit, bit * 3);
			}
			if (left != 2) {
				dfs(j + 1, s + (bit << 1), bit * 3);
			}
		}
	}

	public static int dp(int i, int j, int s, int bit) {
		if (i == n) {
			return 1;
		}
		if (j == m) {
			return dp(i + 1, 0, s, 1);
		}
		if (dp[i][j][s] != -1) {
			return dp[i][j][s];
		}
		int up = (s / bit) % 3;
		int left = j == 0 ? -1 : ((s / (bit / 3)) % 3);
		s -= up * bit;
		int ans = 0;
		if (up != 0 && left != 0) {
			ans = (ans + dp(i, j + 1, s, bit * 3)) % MOD;
		}
		if (up != 1 && left != 1) {
			ans = (ans + dp(i, j + 1, s + bit, bit * 3)) % MOD;
		}
		if (up != 2 && left != 2) {
			ans = (ans + dp(i, j + 1, s + (bit << 1), bit * 3)) % MOD;
		}
		s += up * bit;
		dp[i][j][s] = ans;
		return ans;
	}

}
