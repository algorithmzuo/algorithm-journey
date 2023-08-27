package class050;

import java.util.Arrays;

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

	public static int[] hitBricks(int[][] g, int[][] h) {
		grid = g;
		for (int i = 0; i < h.length; i++) {
			if (grid[h[i][0]][h[i][1]] == 1) {
				grid[h[i][0]][h[i][1]] = 2;
			}
		}
		build();
		int[] ans = new int[h.length];
		for (int i = h.length - 1; i >= 0; i--) {
			if (grid[h[i][0]][h[i][1]] == 2) {
				grid[h[i][0]][h[i][1]] = 1;
				ans[i] = connect(h[i][0], h[i][1]);
			}
		}
		return ans;
	}

	public static int[][] grid;

	public static int MAXN = 40001;

	public static int[] father = new int[MAXN];

	public static int[] size = new int[MAXN];

	public static boolean[] celling = new boolean[MAXN];

	public static int n, m, connects;

	public static void build() {
		n = grid.length;
		m = grid[0].length;
		connects = 0;
		Arrays.fill(father, 0, n * m, 0);
		Arrays.fill(size, 0, n * m, 0);
		Arrays.fill(celling, 0, n * m, false);
		for (int row = 0; row < n; row++) {
			for (int col = 0; col < m; col++) {
				if (grid[row][col] == 1) {
					addOne(row, col);
				}
			}
		}
		for (int row = 0; row < n; row++) {
			for (int col = 0; col < m; col++) {
				union(row, col, row - 1, col);
				union(row, col, row + 1, col);
				union(row, col, row, col - 1);
				union(row, col, row, col + 1);
			}
		}
	}

	public static void addOne(int row, int col) {
		int index = index(row, col);
		if (row == 0) {
			celling[index] = true;
			connects++;
		}
		father[index] = index;
		size[index] = 1;
	}

	public static int index(int row, int col) {
		return row * m + col;
	}

	public static int connect(int row, int col) {
		addOne(row, col);
		int tmp = connects;
		union(row, col, row - 1, col);
		union(row, col, row + 1, col);
		union(row, col, row, col - 1);
		union(row, col, row, col + 1);
		if (row == 0) {
			return connects - tmp;
		} else {
			return connects == tmp ? 0 : connects - tmp - 1;
		}
	}

	public static int find(int i) {
		if (i != father[i]) {
			father[i] = find(father[i]);
		}
		return father[i];
	}

	public static void union(int r1, int c1, int r2, int c2) {
		if (check(r1, c1) && check(r2, c2)) {
			int f1 = find(index(r1, c1));
			int f2 = find(index(r2, c2));
			if (f1 != f2) {
				father[f1] = f2;
				if (celling[f1] ^ celling[f2]) {
					connects += celling[f1] ? size[f2] : size[f1];
				}
				size[f2] += size[f1];
				celling[f2] |= celling[f1];
			}
		}
	}

	public static boolean check(int row, int col) {
		return row >= 0 && row < n && col >= 0 && col < m && grid[row][col] == 1;
	}

}
