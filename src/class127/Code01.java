package class127;

// 摘樱桃
// 测试链接 : https://leetcode.cn/problems/cherry-pickup/
// 这里只讲述核心思路
// 有兴趣同学自己改出严格位置依赖的动态规划
// 有兴趣同学自己改出空间压缩版本的动态规划
// 之前的课大量涉及，这里不再赘述
public class Code01 {

	public static int cherryPickup(int[][] grid) {
		int n = grid.length;
		int[][][] dp = new int[n][n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				for (int k = 0; k < n; k++) {
					dp[i][j][k] = -2;
				}
			}
		}
		int ans = f(grid, n, 0, 0, 0, dp);
		return ans == -1 ? 0 : ans;
	}

	public static int f(int[][] grid, int n, int a, int b, int c, int[][][] dp) {
		int d = a + b - c;
		if (a == n || b == n || c == n || d == n || grid[a][b] == -1 || grid[c][d] == -1) {
			return -1;
		}
		if (a == n - 1 && b == n - 1) {
			return grid[a][b];
		}
		if (dp[a][b][c] != -2) {
			return dp[a][b][c];
		}
		int get = a == c && b == d ? grid[a][b] : (grid[a][b] + grid[c][d]);
		int next = f(grid, n, a + 1, b, c + 1, dp);
		next = Math.max(next, f(grid, n, a + 1, b, c, dp));
		next = Math.max(next, f(grid, n, a, b + 1, c + 1, dp));
		next = Math.max(next, f(grid, n, a, b + 1, c, dp));
		int ans = -1;
		if (next != -1) {
			ans = get + next;
		}
		dp[a][b][c] = ans;
		return ans;
	}

}
