package class058;

// 打砖块
// 有一个 m * n 的二元网格 grid ，其中 1 表示砖块，0 表示空白
// 砖块 稳定（不会掉落）的前提是：
// 一块砖直接连接到网格的顶部，或者
// 至少有一块相邻（4 个方向之一）砖块 稳定 不会掉落时
// 给你一个数组 hits ，这是需要依次消除砖块的位置
// 每当消除 hits[i] = (rowi, coli) 位置上的砖块时，对应位置的砖块（若存在）会消失
// 然后其他的砖块可能因为这一消除操作而 掉落
// 一旦砖块掉落，它会 立即 从网格 grid 中消失（即，它不会落在其他稳定的砖块上）
// 返回一个数组 result ，其中 result[i] 表示第 i 次消除操作对应掉落的砖块数目。
// 注意，消除可能指向是没有砖块的空白位置，如果发生这种情况，则没有砖块掉落。
// 测试链接 : https://leetcode.cn/problems/bricks-falling-when-hit/
public class Code04_BricksFallingWhenHit {

	public static int n, m;

	public static int[][] grid;

	public static int[] hitBricks(int[][] g, int[][] h) {
		grid = g;
		n = g.length;
		m = g[0].length;
		int[] ans = new int[h.length];
		if (n == 1) {
			return ans;
		}
		for (int[] hit : h) {
			grid[hit[0]][hit[1]]--;
		}
		for (int i = 0; i < m; i++) {
			dfs(0, i);
		}
		for (int i = h.length - 1, row, col; i >= 0; i--) {
			row = h[i][0];
			col = h[i][1];
			grid[row][col]++;
			if (worth(row, col)) {
				ans[i] = dfs(row, col) - 1;
			}
		}
		return ans;
	}

	// 从(i,j)格子出发，遇到1就感染成2
	// 统计新增了几个2！
	public static int dfs(int i, int j) {
		if (i < 0 || i == n || j < 0 || j == m || grid[i][j] != 1) {
			return 0;
		}
		grid[i][j] = 2;
		return 1 + dfs(i + 1, j) + dfs(i, j + 1) + dfs(i - 1, j) + dfs(i, j - 1);
	}

	public static boolean worth(int i, int j) {
		return grid[i][j] == 1
				&&
				(i == 0
				|| (i > 0 && grid[i - 1][j] == 2)
				|| (i < n - 1 && grid[i + 1][j] == 2)
				|| (j > 0 && grid[i][j - 1] == 2)
				|| (j < m - 1 && grid[i][j + 1] == 2));
	}

}
