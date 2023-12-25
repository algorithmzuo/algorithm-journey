package class062;

import java.util.PriorityQueue;

// 二维接雨水
// 给你一个 m * n 的矩阵，其中的值均为非负整数，代表二维高度图每个单元的高度
// 请计算图中形状最多能接多少体积的雨水。
// 测试链接 : https://leetcode.cn/problems/trapping-rain-water-ii/
public class Code05_TrappingRainWaterII {

	public static int trapRainWater(int[][] height) {
		int[] move = new int[] { -1, 0, 1, 0, -1 };
		int n = height.length;
		int m = height[0].length;
		// 0 : 行
		// 1 : 列
		// 2 : 水线
		PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> a[2] - b[2]);
		boolean[][] visited = new boolean[n][m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (i == 0 || i == n - 1 || j == 0 || j == m - 1) {
					// 边界
					heap.add(new int[] { i, j, height[i][j] });
					visited[i][j] = true;
				} else {
					visited[i][j] = false;
				}
			}
		}
		int ans = 0;
		while (!heap.isEmpty()) {
			int[] record = heap.poll();
			int r = record[0];
			int c = record[1];
			int w = record[2];
			ans += w - height[r][c];
			for (int i = 0, nr, nc; i < 4; i++) {
				nr = r + move[i];
				nc = c + move[i + 1];
				if (nr >= 0 && nr < n && nc >= 0 && nc < m && !visited[nr][nc]) {
					heap.add(new int[] { nr, nc, Math.max(height[nr][nc], w) });
					visited[nr][nc] = true;
				}
			}
		}
		return ans;
	}

}
