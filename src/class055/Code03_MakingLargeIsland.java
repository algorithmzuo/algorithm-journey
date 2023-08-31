package class055;

// 最大人工岛
// 给你一个大小为 n * n 二进制矩阵 grid 。最多 只能将一格 0 变成 1 。
// 返回执行此操作后，grid 中最大的岛屿面积是多少？
// 岛屿 由一组上、下、左、右四个方向相连的 1 形成
// 测试链接 : https://leetcode.cn/problems/making-a-large-island/
public class Code03_MakingLargeIsland {

	public static int largestIsland(int[][] grid) {
		int n = grid.length;
		int m = grid[0].length;
		int id = 2;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (grid[i][j] == 1) {
					dfs(grid, i, j, id++, n, m);
				}
			}
		}
		int[] sizes = new int[id];
		int ans = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (grid[i][j] > 1) {
					ans = Math.max(ans, ++sizes[grid[i][j]]);
				}
			}
		}
		boolean[] visited = new boolean[id];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (grid[i][j] == 0) {
					int up = i - 1 >= 0 ? grid[i - 1][j] : 0;
					int down = i + 1 < n ? grid[i + 1][j] : 0;
					int left = j - 1 >= 0 ? grid[i][j - 1] : 0;
					int right = j + 1 < m ? grid[i][j + 1] : 0;
					int merge = 1 + sizes[up];
					visited[up] = true;
					if (!visited[down]) {
						merge += sizes[down];
						visited[down] = true;
					}
					if (!visited[left]) {
						merge += sizes[left];
						visited[left] = true;
					}
					if (!visited[right]) {
						merge += sizes[right];
						visited[right] = true;
					}
					ans = Math.max(ans, merge);
					visited[up] = false;
					visited[down] = false;
					visited[left] = false;
					visited[right] = false;
				}
			}
		}
		return ans;
	}

	public static void dfs(int[][] grid, int i, int j, int v, int n, int m) {
		if (i < 0 || i == n || j < 0 || j == m || grid[i][j] != 1) {
			return;
		}
		grid[i][j] = v;
		dfs(grid, i - 1, j, v, n, m);
		dfs(grid, i + 1, j, v, n, m);
		dfs(grid, i, j - 1, v, n, m);
		dfs(grid, i, j + 1, v, n, m);
	}

}
