package class083;

// 自由之路
// 题目描述比较多，打开链接查看
// 测试链接 : https://leetcode.cn/problems/freedom-trail/
public class Code03_FreedomTrail {

	// 为了让所有语言的同学都可以理解
	// 不会使用任何java语言自带的数据结构
	// 只使用最简单的数组结构
	public static int MAXN = 101;

	public static int MAXC = 26;

	public static int[] ring = new int[MAXN];

	public static int[] key = new int[MAXN];

	public static int[] size = new int[MAXC];

	public static int[][] where = new int[MAXC][MAXN];

	public static int[][] dp = new int[MAXN][MAXN];

	public static int n, m;

	public static void build(String r, String k) {
		for (int i = 0; i < MAXC; i++) {
			size[i] = 0;
		}
		n = r.length();
		m = k.length();
		for (int i = 0, v; i < n; i++) {
			v = r.charAt(i) - 'a';
			where[v][size[v]++] = i;
			ring[i] = v;
		}
		for (int i = 0; i < m; i++) {
			key[i] = k.charAt(i) - 'a';
		}
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				dp[i][j] = -1;
			}
		}
	}

	public static int findRotateSteps(String r, String k) {
		build(r, k);
		return f(0, 0);
	}

	public static int f(int i, int j) {
		if (j == m) {
			return 0;
		}
		if (dp[i][j] != -1) {
			return dp[i][j];
		}
		int ans;
		if (ring[i] == key[j]) {
			ans = 1 + f(i, j + 1);
		} else {
			int jump1 = clock(i, key[j]);
			int jump2 = counterClock(i, key[j]);
			int p1 = (jump1 > i ? (jump1 - i) : (n - i + jump1)) + f(jump1, j);
			int p2 = (i > jump2 ? (i - jump2) : (i + n - jump2)) + f(jump2, j);
			ans = Math.min(p1, p2);
		}
		dp[i][j] = ans;
		return ans;
	}

	public static int clock(int i, int v) {
		int l = 0;
		int r = size[v] - 1, m;
		int[] sorted = where[v];
		int find = -1;
		while (l <= r) {
			m = (l + r) / 2;
			if (sorted[m] > i) {
				find = m;
				r = m - 1;
			} else {
				l = m + 1;
			}
		}
		return find != -1 ? sorted[find] : sorted[0];
	}

	public static int counterClock(int i, int v) {
		int l = 0;
		int r = size[v] - 1, m;
		int[] sorted = where[v];
		int find = -1;
		while (l <= r) {
			m = (l + r) / 2;
			if (sorted[m] < i) {
				find = m;
				l = m + 1;
			} else {
				r = m - 1;
			}
		}
		return find != -1 ? sorted[find] : sorted[size[v] - 1];
	}

}
