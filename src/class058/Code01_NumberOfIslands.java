package class058;

// 岛屿数量
// 给你一个由 '1'（陆地）和 '0'（水）组成的的二维网格，请你计算网格中岛屿的数量
// 岛屿总是被水包围，并且每座岛屿只能由水平方向和/或竖直方向上相邻的陆地连接形成
// 此外，你可以假设该网格的四条边均被水包围
// 测试链接 : https://leetcode.cn/problems/number-of-islands/
public class Code01_NumberOfIslands {

	// 洪水填充的做法
	// board : n * m
	// O(n*m)最优解！
	public static int numIslands(char[][] board) {
		int n = board.length;
		int m = board[0].length;
		int islands = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (board[i][j] == '1') {
					islands++;
					dfs(board, n, m, i, j);
				}
			}
		}
		return islands;
	}

	public static void dfs(char[][] board, int n, int m, int i, int j) {
		if (i < 0 || i == n || j < 0 || j == m || board[i][j] != '1') {
			return;
		}
		// board[i][j] = '1'
		board[i][j] = 0;
		dfs(board, n, m, i - 1, j);
		dfs(board, n, m, i + 1, j);
		dfs(board, n, m, i, j - 1);
		dfs(board, n, m, i, j + 1);
	}

}
