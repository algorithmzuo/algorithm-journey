package class065;

import java.util.PriorityQueue;

// A*算法
// 过程和Dijskra高度相似，增加了到终点的预估函数
// 只要预估值 <= 客观上的最短距离，就没问题
// 预估函数是一种吸引力：
// 1）合适的吸引力可以提升算法的速度
// 2）吸引力过强会出现错误
// 预估终点距离选择曼哈顿距离
public class Code01_AStarAlgorithm {

	// Dijkstra算法
	// map[i][j] == 0 代表障碍
	// map[i][j] == 1 代表道路
	public static int minDistance1(int[][] map, int startX, int startY, int targetX, int targetY) {
		if (map[startX][startY] == 0 || map[targetX][targetY] == 0) {
			return -1;
		}
		int n = map.length;
		int m = map[0].length;
		int[][] distance = new int[n][m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				distance[i][j] = Integer.MAX_VALUE;
			}
		}
		distance[startX][startY] = 1;
		boolean[][] visited = new boolean[n][m];
		PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> a[2] - b[2]);
		heap.add(new int[] { startX, startY, 1 });
		while (!heap.isEmpty()) {
			int[] cur = heap.poll();
			int row = cur[0];
			int col = cur[1];
			int dis = cur[2];
			if (visited[row][col]) {
				continue;
			}
			visited[row][col] = true;
			if (row == targetX && col == targetY) {
				return dis;
			}
			add1(dis, row - 1, col, n, m, map, visited, distance, heap);
			add1(dis, row + 1, col, n, m, map, visited, distance, heap);
			add1(dis, row, col - 1, n, m, map, visited, distance, heap);
			add1(dis, row, col + 1, n, m, map, visited, distance, heap);
		}
		return -1;
	}

	public static void add1(int pre, int row, int col, int n, int m, int[][] map, boolean[][] visited, int[][] distance,
			PriorityQueue<int[]> heap) {
		if (row >= 0 && row < n && col >= 0 && col < m && map[row][col] == 1 && !visited[row][col]) {
			if (pre + 1 < distance[row][col]) {
				distance[row][col] = pre + 1;
				heap.add(new int[] { row, col, pre + 1 });
			}
		}
	}

	// A*算法
	// map[i][j] == 0 代表障碍
	// map[i][j] == 1 代表道路
	public static int minDistance2(int[][] map, int startX, int startY, int targetX, int targetY) {
		if (map[startX][startY] == 0 || map[targetX][targetY] == 0) {
			return -1;
		}
		int n = map.length;
		int m = map[0].length;
		int[][] distance = new int[n][m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				distance[i][j] = Integer.MAX_VALUE;
			}
		}
		distance[startX][startY] = 1;
		boolean[][] visited = new boolean[n][m];
		PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> (a[2] + a[3]) - (b[2] + b[3]));
		heap.add(new int[] { startX, startY, 1, distance(startX, startY, targetX, targetY) });
		while (!heap.isEmpty()) {
			int[] cur = heap.poll();
			int row = cur[0];
			int col = cur[1];
			int dis = cur[2];
			if (visited[row][col]) {
				continue;
			}
			visited[row][col] = true;
			if (row == targetX && col == targetY) {
				return dis;
			}
			add2(dis, row - 1, col, targetX, targetY, n, m, map, visited, distance, heap);
			add2(dis, row + 1, col, targetX, targetY, n, m, map, visited, distance, heap);
			add2(dis, row, col - 1, targetX, targetY, n, m, map, visited, distance, heap);
			add2(dis, row, col + 1, targetX, targetY, n, m, map, visited, distance, heap);
		}
		return -1;
	}

	public static void add2(int pre, int row, int col, int targetX, int targetY, int n, int m, int[][] map,
			boolean[][] visited, int[][] distance, PriorityQueue<int[]> heap) {
		if (row >= 0 && row < n && col >= 0 && col < m && map[row][col] == 1 && !visited[row][col]) {
			if (pre + 1 < distance[row][col]) {
				distance[row][col] = pre + 1;
				heap.add(new int[] { row, col, pre + 1, distance(row, col, targetX, targetY) });
			}
		}
	}

	// 曼哈顿距离
	public static int distance(int curX, int curY, int targetX, int targetY) {
		return Math.abs(targetX - curX) + Math.abs(targetY - curY);
	}

	// 为了测试
	// map[i][j] == 0 代表障碍
	// map[i][j] == 1 代表道路
	public static int[][] randomMap(int len) {
		int[][] ans = new int[len][len];
		for (int i = 0; i < len; i++) {
			for (int j = 0; j < len; j++) {
				if (Math.random() < 0.2) {
					ans[i][j] = 0;
				} else {
					ans[i][j] = 1;
				}
			}
		}
		return ans;
	}

	public static void main(String[] args) {
		int len = 100;
		int testTime = 10000;
		System.out.println("功能测试开始");
		for (int i = 0; i < testTime; i++) {
			int n = (int) (Math.random() * len) + 2;
			int[][] map = randomMap(n);
			int startX = (int) (Math.random() * n);
			int startY = (int) (Math.random() * n);
			int targetX = (int) (Math.random() * n);
			int targetY = (int) (Math.random() * n);
			int ans1 = minDistance1(map, startX, startY, targetX, targetY);
			int ans2 = minDistance2(map, startX, startY, targetX, targetY);
			if (ans1 != ans2) {
				System.out.println("出错了!");
			}
		}
		System.out.println("功能测试结束");

		System.out.println("性能测试开始");
		int[][] map = randomMap(4000);
		int startX = 0;
		int startY = 0;
		int targetX = 3900;
		int targetY = 3900;
		long start, end;
		start = System.currentTimeMillis();
		int ans1 = minDistance1(map, startX, startY, targetX, targetY);
		end = System.currentTimeMillis();
		System.out.println("Dijskra方法结果: " + ans1 + ", 运行时间(毫秒) : " + (end - start));
		start = System.currentTimeMillis();
		int ans2 = minDistance2(map, startX, startY, targetX, targetY);
		end = System.currentTimeMillis();
		System.out.println("A*方法结果: " + ans2 + ", 运行时间(毫秒) : " + (end - start));
		System.out.println("性能测试结束");
	}

}
