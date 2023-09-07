package class063;

import java.util.PriorityQueue;

// A*算法
// 过程和Dijskra高度相似，增加了到终点的预估函数
// 只要预估值 <= 客观上的最短距离，就没问题
// 预估函数是一种吸引力：
// 1）合适的吸引力可以提升算法的速度
// 2）吸引力过强会出现错误
// 预估终点距离选择曼哈顿距离
public class Code06_AStarAlgorithm {

	// Dijkstra算法
	// map[i][j] == 0 代表障碍
	// map[i][j] == 1 代表道路
	public static int minDistance1(int[][] map, int startX, int startY, int targetX, int targetY) {
		if (map[startX][startY] == 0 || map[targetX][targetY] == 0) {
			return Integer.MAX_VALUE;
		}
		int n = map.length;
		int m = map[0].length;
		PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> a[0] - b[0]);
		boolean[][] closed = new boolean[n][m];
		heap.add(new int[] { 1, startX, startY });
		int ans = Integer.MAX_VALUE;
		while (!heap.isEmpty()) {
			int[] cur = heap.poll();
			int dis = cur[0];
			int row = cur[1];
			int col = cur[2];
			if (closed[row][col]) {
				continue;
			}
			closed[row][col] = true;
			if (row == targetX && col == targetY) {
				ans = dis;
				break;
			}
			add1(dis, row - 1, col, n, m, map, closed, heap);
			add1(dis, row + 1, col, n, m, map, closed, heap);
			add1(dis, row, col - 1, n, m, map, closed, heap);
			add1(dis, row, col + 1, n, m, map, closed, heap);
		}
		return ans;
	}

	public static void add1(int pre, int row, int col, int n, int m, int[][] map, boolean[][] closed,
			PriorityQueue<int[]> heap) {
		if (row >= 0 && row < n && col >= 0 && col < m && map[row][col] == 1 && !closed[row][col]) {
			heap.add(new int[] { pre + 1, row, col });
		}
	}

	// A*算法
	// map[i][j] == 0 代表障碍
	// map[i][j] == 1 代表道路
	public static int minDistance2(int[][] map, int startX, int startY, int targetX, int targetY) {
		if (map[startX][startY] == 0 || map[targetX][targetY] == 0) {
			return Integer.MAX_VALUE;
		}
		int n = map.length;
		int m = map[0].length;
		PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> (a[0] + a[1]) - (b[0] + b[1]));
		boolean[][] closed = new boolean[n][m];
		heap.add(new int[] { 1, distance(startX, startY, targetX, targetY), startX, startY });
		int ans = Integer.MAX_VALUE;
		while (!heap.isEmpty()) {
			int[] cur = heap.poll();
			int fromDistance = cur[0];
			int row = cur[2];
			int col = cur[3];
			if (closed[row][col]) {
				continue;
			}
			closed[row][col] = true;
			if (row == targetX && col == targetY) {
				ans = fromDistance;
				break;
			}
			add2(fromDistance, row - 1, col, targetX, targetY, n, m, map, closed, heap);
			add2(fromDistance, row + 1, col, targetX, targetY, n, m, map, closed, heap);
			add2(fromDistance, row, col - 1, targetX, targetY, n, m, map, closed, heap);
			add2(fromDistance, row, col + 1, targetX, targetY, n, m, map, closed, heap);
		}
		return ans;
	}

	public static void add2(int pre, int row, int col, int targetX, int targetY, int n, int m, int[][] map,
			boolean[][] closed, PriorityQueue<int[]> heap) {
		if (row >= 0 && row < n && col >= 0 && col < m && map[row][col] == 1 && !closed[row][col]) {
			heap.add(new int[] { pre + 1, distance(row, col, targetX, targetY), row, col });
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
