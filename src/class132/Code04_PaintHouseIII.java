package class132;

// 粉刷房子III
// 房子有n个，从左到右排列，编号1..n，颜色有c种，编号1..c
// 给定数组house，house[i]表示房子的颜色，如果house[i]为0说明房子没有涂色
// 你必须给每个没有涂色的房子涂上颜色，如果有颜色的房子不能改变颜色
// 给定二维数组cost，cost[i][v]表示如果i号房涂成v号颜色，需要花费多少钱
// 相邻的、拥有同一种颜色的房子为1个街区
// 比如如果所有房子的颜色为: {1, 1, 2, 3, 2, 2}，那么一共4个街区
// 最终所有的房子涂完颜色，一定要形成t个街区，返回最少的花费
// 1 <= t <= n <= 100
// 1 <= c <= 20
// 0 <= house[i] <= c
// 1 <= cost[i][v] <= 10^4
// 测试链接 : https://leetcode.cn/problems/paint-house-iii/
public class Code04_PaintHouseIII {

	public static int NA = Integer.MAX_VALUE;

	public static int MAXN = 101;

	public static int MAXT = 101;

	public static int MAXC = 21;

	public static int[] house = new int[MAXN];

	public static int[][] cost = new int[MAXN][MAXC];

	public static int n, t, c;

	// 原始测试页面的数据描述非常绕，一律转化成课上描述的形式
	// 房子编号从1开始，颜色编号从1开始，颜色0代表没有涂色
	// build方法就是转化逻辑
	public static void build(int[] houses, int[][] costs, int hsize, int csize, int tsize) {
		n = hsize;
		t = tsize;
		c = csize;
		for (int i = 1; i <= n; i++) {
			house[i] = houses[i - 1];
		}
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= c; j++) {
				cost[i][j] = costs[i - 1][j - 1];
			}
		}
	}

	// 递归 + 记忆化搜索，不优化枚举
	// 时间复杂度O(n * t * c平方)
	public static int minCost1(int[] houses, int[][] costs, int hsize, int csize, int tsize) {
		build(houses, costs, hsize, csize, tsize);
		t++;
		int[][][] dp = new int[n + 1][t + 1][c + 1];
		for (int i = 0; i <= n; i++) {
			for (int j = 0; j <= t; j++) {
				for (int v = 0; v <= c; v++) {
					dp[i][j][v] = -1;
				}
			}
		}
		int ans = f1(n, t, 0, dp);
		return ans == NA ? -1 : ans;
	}

	// 1...i+1范围的房子必须凑齐j个街区，i+1号房子的颜色已经涂成了v
	// 返回最少的花费，如果做不到返回NA
	public static int f1(int i, int j, int v, int[][][] dp) {
		if (j == 0) {
			return NA;
		}
		if (i == 0) {
			return j == 1 ? 0 : NA;
		}
		if (dp[i][j][v] != -1) {
			return dp[i][j][v];
		}
		int ans = NA;
		if (house[i] != 0) {
			if (house[i] == v) {
				ans = f1(i - 1, j, house[i], dp);
			} else {
				ans = f1(i - 1, j - 1, house[i], dp);
			}
		} else {
			for (int cur = 1, next; cur <= c; cur++) {
				if (cur == v) {
					next = f1(i - 1, j, cur, dp);
				} else {
					next = f1(i - 1, j - 1, cur, dp);
				}
				if (next != NA) {
					ans = Math.min(ans, next + cost[i][cur]);
				}
			}
		}
		dp[i][j][v] = ans;
		return ans;
	}

	// 严格位置依赖的动态规划，不优化枚举
	// 时间复杂度O(n * t * c平方)
	public static int minCost2(int[] houses, int[][] costs, int hsize, int csize, int tsize) {
		build(houses, costs, hsize, csize, tsize);
		t++;
		int[][][] dp = new int[n + 1][t + 1][c + 1];
		for (int i = 0; i <= n; i++) {
			for (int v = 0; v <= c; v++) {
				dp[i][0][v] = NA;
			}
		}
		for (int j = 1; j <= t; j++) {
			for (int v = 0; v <= c; v++) {
				dp[0][j][v] = j == 1 ? 0 : NA;
			}
		}
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= t; j++) {
				for (int v = 0; v <= c; v++) {
					int ans = NA;
					if (house[i] != 0) {
						if (house[i] == v) {
							ans = dp[i - 1][j][house[i]];
						} else {
							ans = dp[i - 1][j - 1][house[i]];
						}
					} else {
						for (int cur = 1, next; cur <= c; cur++) {
							if (cur == v) {
								next = dp[i - 1][j][cur];
							} else {
								next = dp[i - 1][j - 1][cur];
							}
							if (next != NA) {
								ans = Math.min(ans, next + cost[i][cur]);
							}
						}
					}
					dp[i][j][v] = ans;
				}
			}
		}
		int ans = dp[n][t][0];
		return ans == NA ? -1 : ans;
	}

	// 空间压缩版本，不优化枚举
	// 时间复杂度O(n * t * c平方)
	public static int minCost3(int[] houses, int[][] costs, int hsize, int csize, int tsize) {
		build(houses, costs, hsize, csize, tsize);
		t++;
		int[][] memo = new int[t + 1][c + 1];
		int[][] dp = new int[t + 1][c + 1];
		for (int v = 0; v <= c; v++) {
			memo[0][v] = NA;
			dp[0][v] = NA;
		}
		for (int j = 1; j <= t; j++) {
			for (int v = 0; v <= c; v++) {
				memo[j][v] = j == 1 ? 0 : NA;
			}
		}
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= t; j++) {
				for (int v = 0; v <= c; v++) {
					int ans = NA;
					if (house[i] != 0) {
						if (house[i] == v) {
							ans = memo[j][house[i]];
						} else {
							ans = memo[j - 1][house[i]];
						}
					} else {
						for (int cur = 1, next; cur <= c; cur++) {
							if (cur == v) {
								next = memo[j][cur];
							} else {
								next = memo[j - 1][cur];
							}
							if (next != NA) {
								ans = Math.min(ans, next + cost[i][cur]);
							}
						}
					}
					dp[j][v] = ans;
				}
			}
			int[][] tmp = memo;
			memo = dp;
			dp = tmp;
		}
		int ans = memo[t][0];
		return ans == NA ? -1 : ans;
	}

	// 最优解
	// 优化枚举 + 空间压缩
	// 时间复杂度O(n * t * c)
	public static int minCost4(int[] houses, int[][] costs, int hsize, int csize, int tsize) {
		build(houses, costs, hsize, csize, tsize);
		t++;
		int[][] memo = new int[t + 1][c + 1];
		int[][] dp = new int[t + 1][c + 1];
		for (int v = 0; v <= c; v++) {
			memo[0][v] = NA;
			dp[0][v] = NA;
		}
		for (int j = 1; j <= t; j++) {
			for (int v = 0; v <= c; v++) {
				memo[j][v] = j == 1 ? 0 : NA;
			}
		}
		int[] pre = new int[c + 2];
		int[] suf = new int[c + 2];
		pre[0] = suf[c + 1] = NA;
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= t; j++) {
				// 预处理结构优化前缀枚举
				for (int v = 1; v <= c; v++) {
					pre[v] = pre[v - 1];
					if (memo[j - 1][v] != NA) {
						pre[v] = Math.min(pre[v], memo[j - 1][v] + cost[i][v]);
					}
				}
				// 预处理结构优化后缀枚举
				for (int v = c; v >= 1; v--) {
					suf[v] = suf[v + 1];
					if (memo[j - 1][v] != NA) {
						suf[v] = Math.min(suf[v], memo[j - 1][v] + cost[i][v]);
					}
				}
				// 实际去填dp表
				for (int v = 0; v <= c; v++) {
					int ans = NA;
					if (house[i] != 0) {
						if (house[i] == v) {
							ans = memo[j][house[i]];
						} else {
							ans = memo[j - 1][house[i]];
						}
					} else {
						if (v == 0) {
							ans = suf[1];
						} else {
							ans = Math.min(pre[v - 1], suf[v + 1]);
							if (memo[j][v] != NA) {
								ans = Math.min(ans, memo[j][v] + cost[i][v]);
							}
						}
					}
					dp[j][v] = ans;
				}
			}
			int[][] tmp = memo;
			memo = dp;
			dp = tmp;
		}
		int ans = memo[t][0];
		return ans == NA ? -1 : ans;
	}

}
