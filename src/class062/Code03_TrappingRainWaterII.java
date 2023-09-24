package class062;

// 二维接雨水
// 给你一个 m * n 的矩阵，其中的值均为非负整数，代表二维高度图每个单元的高度
// 请计算图中形状最多能接多少体积的雨水。
// 测试链接 : https://leetcode.cn/problems/trapping-rain-water-ii/
public class Code03_TrappingRainWaterII {

	public static int MAXN = 201;

	public static int MAXM = 201;

	public static int[][] queue = new int[MAXN * MAXM * 3][2];

	public static int l, r;

	public static int[][] water = new int[MAXN][MAXM];

	// 0:上，1:右，2:下，3:左
	public static int[] move = new int[] { -1, 0, 1, 0, -1 };

	public static int trapRainWater(int[][] height) {
		int n = height.length;
		int m = height[0].length;
		int maxHeight = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				maxHeight = Math.max(maxHeight, height[i][j]);
			}
		}
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				water[i][j] = maxHeight;
			}
		}
		l = r = 0;
		boundaryEnqueue(n, m, height);
		while (l < r) {
			int x = queue[l][0];
			int y = queue[l++][1];
			for (int i = 0; i < 4; i++) {
				int nx = x + move[i];
				int ny = y + move[i + 1];
				if (nx == -1 || nx == n || ny == -1 || ny == m) {
					continue;
				}
				if (water[nx][ny] > water[x][y] && water[nx][ny] > height[nx][ny]) {
					water[nx][ny] = Math.max(water[x][y], height[nx][ny]);
					queue[r][0] = nx;
					queue[r++][1] = ny;
				}
			}
		}
		int ans = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				ans += water[i][j] - height[i][j];
			}
		}
		return ans;
	}

	public static void boundaryEnqueue(int n, int m, int[][] height) {
		for (int j = 0; j < m; j++) {
			if (water[0][j] > height[0][j]) {
				water[0][j] = height[0][j];
				queue[r][0] = 0;
				queue[r++][1] = j;
			}
			if (water[n - 1][j] > height[n - 1][j]) {
				water[n - 1][j] = height[n - 1][j];
				queue[r][0] = n - 1;
				queue[r++][1] = j;
			}
		}
		for (int i = 1; i < n - 1; i++) {
			if (water[i][0] > height[i][0]) {
				water[i][0] = height[i][0];
				queue[r][0] = i;
				queue[r++][1] = 0;
			}
			if (water[i][m - 1] > height[i][m - 1]) {
				water[i][m - 1] = height[i][m - 1];
				queue[r][0] = i;
				queue[r++][1] = m - 1;
			}
		}
	}

}
