package class065;

import java.util.PriorityQueue;

// A*算法模版（对数器验证）
public class Code01_AStarAlgorithm {

	// 0:上，1:右，2:下，3:左
	public static int[] move = new int[] { -1, 0, 1, 0, -1 };

	// Dijkstra算法
	// grid[i][j] == 0 代表障碍
	// grid[i][j] == 1 代表道路
	// 只能走上、下、左、右，不包括斜线方向
	// 返回从(startX, startY)到(targetX, targetY)的最短距离
	public static int minDistance1(int[][] grid, int startX, int startY, int targetX, int targetY) {
		if (grid[startX][startY] == 0 || grid[targetX][targetY] == 0) {
			return -1;
		}
		int n = grid.length;
		int m = grid[0].length;
		int[][] distance = new int[n][m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				distance[i][j] = Integer.MAX_VALUE;
			}
		}
		distance[startX][startY] = 1;
		boolean[][] visited = new boolean[n][m];
		PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> a[2] - b[2]);
		// 0 : 行
		// 1 : 列
		// 2 : 从源点出发到达当前点的距离
		heap.add(new int[] { startX, startY, 1 });
		while (!heap.isEmpty()) {
			int[] cur = heap.poll();
			int x = cur[0];
			int y = cur[1];
			if (visited[x][y]) {
				continue;
			}
			visited[x][y] = true;
			if (x == targetX && y == targetY) {
				return distance[x][y];
			}
			for (int i = 0, nx, ny; i < 4; i++) {
				nx = x + move[i];
				ny = y + move[i + 1];
				if (nx >= 0 && nx < n && ny >= 0 && ny < m && grid[nx][ny] == 1 && !visited[nx][ny]
						&& distance[x][y] + 1 < distance[nx][ny]) {
					distance[nx][ny] = distance[x][y] + 1;
					heap.add(new int[] { nx, ny, distance[x][y] + 1 });
				}
			}
		}
		return -1;
	}

	// A*算法
	// grid[i][j] == 0 代表障碍
	// grid[i][j] == 1 代表道路
	// 只能走上、下、左、右，不包括斜线方向
	// 返回从(startX, startY)到(targetX, targetY)的最短距离
	public static int minDistance2(int[][] grid, int startX, int startY, int targetX, int targetY) {
		if (grid[startX][startY] == 0 || grid[targetX][targetY] == 0) {
			return -1;
		}
		int n = grid.length;
		int m = grid[0].length;
		int[][] distance = new int[n][m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				distance[i][j] = Integer.MAX_VALUE;
			}
		}
		distance[startX][startY] = 1;
		boolean[][] visited = new boolean[n][m];
		// 0 : 行
		// 1 : 列
		// 2 : 从源点出发到达当前点的距离 + 当前点到终点的预估距离
		PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> a[2] - b[2]);
		heap.add(new int[] { startX, startY, 1 + f1(startX, startY, targetX, targetY) });
		while (!heap.isEmpty()) {
			int[] cur = heap.poll();
			int x = cur[0];
			int y = cur[1];
			if (visited[x][y]) {
				continue;
			}
			visited[x][y] = true;
			if (x == targetX && y == targetY) {
				return distance[x][y];
			}
			for (int i = 0, nx, ny; i < 4; i++) {
				nx = x + move[i];
				ny = y + move[i + 1];
				if (nx >= 0 && nx < n && ny >= 0 && ny < m && grid[nx][ny] == 1 && !visited[nx][ny]
						&& distance[x][y] + 1 < distance[nx][ny]) {
					distance[nx][ny] = distance[x][y] + 1;
					heap.add(new int[] { nx, ny, distance[x][y] + 1 + f1(nx, ny, targetX, targetY) });
				}
			}
		}
		return -1;
	}

	// 曼哈顿距离
	public static int f1(int x, int y, int targetX, int targetY) {
		return (Math.abs(targetX - x) + Math.abs(targetY - y));
	}

	// 对角线距离
	public static int f2(int x, int y, int targetX, int targetY) {
		return Math.max(Math.abs(targetX - x), Math.abs(targetY - y));
	}

	// 欧式距离
	public static double f3(int x, int y, int targetX, int targetY) {
		return Math.sqrt(Math.pow(targetX - x, 2) + Math.pow(targetY - y, 2));
	}

	// 为了测试
	public static int[][] randomGrid(int n) {
		int[][] grid = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (Math.random() < 0.3) {
					// 每个格子有30%概率是0
					grid[i][j] = 0;
				} else {
					// 每个格子有70%概率是1
					grid[i][j] = 1;
				}
			}
		}
		return grid;
	}

	// 为了测试
	public static void main(String[] args) {
		int len = 100;
		int testTime = 10000;
		System.out.println("功能测试开始");
		for (int i = 0; i < testTime; i++) {
			int n = (int) (Math.random() * len) + 2;
			int[][] grid = randomGrid(n);
			int startX = (int) (Math.random() * n);
			int startY = (int) (Math.random() * n);
			int targetX = (int) (Math.random() * n);
			int targetY = (int) (Math.random() * n);
			int ans1 = minDistance1(grid, startX, startY, targetX, targetY);
			int ans2 = minDistance2(grid, startX, startY, targetX, targetY);
			if (ans1 != ans2) {
				System.out.println("出错了!");
			}
		}
		System.out.println("功能测试结束");

		System.out.println("性能测试开始");
		int[][] grid = randomGrid(4000);
		int startX = 0;
		int startY = 0;
		int targetX = 3900;
		int targetY = 3900;
		long start, end;
		start = System.currentTimeMillis();
		int ans1 = minDistance1(grid, startX, startY, targetX, targetY);
		end = System.currentTimeMillis();
		System.out.println("运行dijskra算法结果: " + ans1 + ", 运行时间(毫秒) : " + (end - start));
		start = System.currentTimeMillis();
		int ans2 = minDistance2(grid, startX, startY, targetX, targetY);
		end = System.currentTimeMillis();
		System.out.println("运行A*算法结果: " + ans2 + ", 运行时间(毫秒) : " + (end - start));
		System.out.println("性能测试结束");
	}

}
