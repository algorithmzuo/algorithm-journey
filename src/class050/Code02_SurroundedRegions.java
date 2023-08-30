package class050;

// 被围绕的区域
// 给你一个 m x n 的矩阵 board ，由若干字符 'X' 和 'O' ，找到所有被 'X' 围绕的区域
// 并将这些区域里所有的 'O' 用 'X' 填充。
// 测试链接 : https://leetcode.cn/problems/surrounded-regions/
public class Code02_SurroundedRegions {

	public static void solve(char[][] board) {
		int n = board.length;
		int m = board[0].length;
		for (int j = 0; j < m; j++) {
			if (board[0][j] == 'O') {
				fill(board, n, m, 0, j);
			}
			if (board[n - 1][j] == 'O') {
				fill(board, n, m, n - 1, j);
			}
		}
		for (int i = 1; i < n - 1; i++) {
			if (board[i][0] == 'O') {
				fill(board, n, m, i, 0);
			}
			if (board[i][m - 1] == 'O') {
				fill(board, n, m, i, m - 1);
			}
		}
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (board[i][j] == 'O') {
					board[i][j] = 'X';
				}
				if (board[i][j] == 'F') {
					board[i][j] = 'O';
				}
			}
		}
	}

	public static void fill(char[][] board, int n, int m, int i, int j) {
		if (i == -1 || i == n || j == -1 || j == m || board[i][j] != 'O') {
			return;
		}
		board[i][j] = 'F';
		fill(board, n, m, i + 1, j);
		fill(board, n, m, i - 1, j);
		fill(board, n, m, i, j + 1);
		fill(board, n, m, i, j - 1);
	}

}
