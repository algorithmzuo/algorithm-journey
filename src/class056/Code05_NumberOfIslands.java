package class056;

// 岛屿数量
// 给你一个由 '1'（陆地）和 '0'（水）组成的的二维网格，请你计算网格中岛屿的数量
// 岛屿总是被水包围，并且每座岛屿只能由水平方向和/或竖直方向上相邻的陆地连接形成
// 此外，你可以假设该网格的四条边均被水包围
// 测试链接 : https://leetcode.cn/problems/number-of-islands/
public class Code05_NumberOfIslands {

	// 并查集的做法
	public static int numIslands(char[][] board) {
		int n = board.length;
		int m = board[0].length;
		build(n, m, board);
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (board[i][j] == '1') {
					if (j > 0 && board[i][j - 1] == '1') {
						union(i, j, i, j - 1);
					}
					if (i > 0 && board[i - 1][j] == '1') {
						union(i, j, i - 1, j);
					}
				}
			}
		}
		return sets;
	}

	public static int MAXSIZE = 100001;

	public static int[] father = new int[MAXSIZE];

	public static int cols;

	public static int sets;

	public static void build(int n, int m, char[][] board) {
		cols = m;
		sets = 0;
		for (int a = 0; a < n; a++) {
			for (int b = 0, index; b < m; b++) {
				if (board[a][b] == '1') {
					index = index(a, b);
					father[index] = index;
					sets++;
				}
			}
		}
	}

	public static int index(int a, int b) {
		return a * cols + b;
	}

	public static int find(int i) {
		if (i != father[i]) {
			father[i] = find(father[i]);
		}
		return father[i];
	}

	public static void union(int a, int b, int c, int d) {
		int fx = find(index(a, b));
		int fy = find(index(c, d));
		if (fx != fy) {
			father[fx] = fy;
			sets--;
		}
	}

}
