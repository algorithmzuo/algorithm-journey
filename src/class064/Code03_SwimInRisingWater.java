package class064;

import java.util.PriorityQueue;

// 水位上升的泳池中游泳
// 在一个 n * n 的整数矩阵 grid 中，
// 每一个方格的值 grid[i][j] 表示位置 (i, j) 的平台高度。
// 当开始下雨时，在时间为 t 时，水池中的水位为 t 。
// 你可以从一个平台游向四周相邻的任意一个平台，但是前提是此时水位必须同时淹没这两个平台。
// 假定你可以瞬间移动无限距离，也就是默认在方格内部游动是不耗时的。
// 当然，在你游泳的时候你必须待在坐标方格里面。
// 你从坐标方格的左上平台 (0，0) 出发。
// 返回 你到达坐标方格的右下平台 (n-1, n-1) 所需的最少时间 。
// 测试链接 ：https://leetcode.cn/problems/swim-in-rising-water
public class Code03_SwimInRisingWater {

	public static int swimInWater(int[][] grid) {
		int n = grid.length;
		int m = grid[0].length;
		PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> a[2] - b[2]);
		boolean[][] visited = new boolean[n][m];
		heap.add(new int[] { 0, 0, grid[0][0] });
		int ans = 0;
		while (!heap.isEmpty()) {
			int r = heap.peek()[0];
			int c = heap.peek()[1];
			int v = heap.peek()[2];
			heap.poll();
			if (visited[r][c]) {
				continue;
			}
			visited[r][c] = true;
			if (r == n - 1 && c == m - 1) {
				ans = v;
				break;
			}
			add(grid, heap, visited, r - 1, c, v);
			add(grid, heap, visited, r + 1, c, v);
			add(grid, heap, visited, r, c - 1, v);
			add(grid, heap, visited, r, c + 1, v);
		}
		return ans;
	}

	public static void add(int[][] grid, PriorityQueue<int[]> heap, boolean[][] visited, int r, int c, int preV) {
		if (r >= 0 && r < grid.length && c >= 0 && c < grid[0].length && !visited[r][c]) {
			heap.add(new int[] { r, c, preV + Math.max(0, grid[r][c] - preV) });
		}
	}

}
