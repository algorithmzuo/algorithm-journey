package class063;

import java.util.PriorityQueue;

// 给定一个N*M的二维矩阵，只由字符'O'、'X'、'S'、'E'组成
// 'O'表示这个地方是可通行的平地
// 'X'表示这个地方是不可通行的障碍
// 'S'表示这个地方有一个士兵，全图保证只有一个士兵
// 'E'表示这个地方有一个敌人，全图保证只有一个敌人
// 士兵可以在上、下、左、右四个方向上移动
// 走到相邻的可通行的平地上，走一步耗费a个时间单位
// 士兵从初始地点行动时，不管去哪个方向，都不用耗费转向的代价
// 但是士兵在行动途中，如果需要转向，需要额外再付出b个时间单位
// 返回士兵找到敌人的最少时间
// 如果因为障碍怎么都找不到敌人，返回-1
// 1 <= N,M <= 1000
// 1 <= a,b <= 100000
// 只会有一个士兵、一个敌人
// 没有找到测试链接，对数器验证
public class Code05_SoldierFindEnemy {

	// 暴力dfs
	// 为了验证
	public static int minCost1(char[][] map, int a, int b) {
		int n = map.length;
		int m = map[0].length;
		int startX = 0;
		int startY = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (map[i][j] == 'S') {
					startX = i;
					startY = j;
				}
			}
		}
		boolean[][][] visited = new boolean[n][m][4];
		int p1 = f(map, startX, startY, 0, a, b, visited);
		int p2 = f(map, startX, startY, 1, a, b, visited);
		int p3 = f(map, startX, startY, 2, a, b, visited);
		int p4 = f(map, startX, startY, 3, a, b, visited);
		int ans = Math.min(Math.min(p1, p2), Math.min(p3, p4));
		return ans == Integer.MAX_VALUE ? -1 : (ans - a);
	}

	public static int f(char[][] map, int si, int sj, int d, int a, int b, boolean[][][] visited) {
		if (si < 0 || si == map.length || sj < 0 || sj == map[0].length || map[si][sj] == 'X' || visited[si][sj][d]) {
			return Integer.MAX_VALUE;
		}
		if (map[si][sj] == 'E') {
			return a;
		}
		visited[si][sj][d] = true;
		int p0 = f(map, si - 1, sj, 0, a, b, visited);
		int p1 = f(map, si + 1, sj, 1, a, b, visited);
		int p2 = f(map, si, sj - 1, 2, a, b, visited);
		int p3 = f(map, si, sj + 1, 3, a, b, visited);
		if (d != 0 && p0 != Integer.MAX_VALUE) {
			p0 += b;
		}
		if (d != 1 && p1 != Integer.MAX_VALUE) {
			p1 += b;
		}
		if (d != 2 && p2 != Integer.MAX_VALUE) {
			p2 += b;
		}
		if (d != 3 && p3 != Integer.MAX_VALUE) {
			p3 += b;
		}
		int ans = Math.min(Math.min(p0, p1), Math.min(p2, p3));
		ans = ans == Integer.MAX_VALUE ? ans : (ans + a);
		visited[si][sj][d] = false;
		return ans;
	}

	// 正式方法
	// Dijkstra算法
	public static int minCost2(char[][] map, int a, int b) {
		int n = map.length;
		int m = map[0].length;
		int startX = 0;
		int startY = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (map[i][j] == 'S') {
					startX = i;
					startY = j;
				}
			}
		}
		PriorityQueue<int[]> heap = new PriorityQueue<>((x, y) -> x[3] - y[3]);
		// (startX, startY)
		heap.add(new int[] { startX, startY, 0, 0 });
		heap.add(new int[] { startX, startY, 1, 0 });
		heap.add(new int[] { startX, startY, 2, 0 });
		heap.add(new int[] { startX, startY, 3, 0 });
		// (i,j,朝向)
		boolean[][][] visited = new boolean[n][m][4];
		int ans = -1;
		while (!heap.isEmpty()) {
			int[] cur = heap.poll();
//			int x = cur[0];
//			int y = cur[1];
//			int 朝向 = cur[2];
//			int 代价 = cur[3];
			if (visited[cur[0]][cur[1]][cur[2]]) {
				continue;
			}
			if (map[cur[0]][cur[1]] == 'E') {
				ans = cur[3];
				break;
			}
			visited[cur[0]][cur[1]][cur[2]] = true;
			add(cur[0] - 1, cur[1], 0, cur[2], cur[3], a, b, map, visited, heap);
			add(cur[0] + 1, cur[1], 1, cur[2], cur[3], a, b, map, visited, heap);
			add(cur[0], cur[1] - 1, 2, cur[2], cur[3], a, b, map, visited, heap);
			add(cur[0], cur[1] + 1, 3, cur[2], cur[3], a, b, map, visited, heap);
		}
		return ans;
	}

	// 从(x,y, preD) -> (i,j,d)
	// 走格子的代价a
	// 转向的代价是b
	// preC + a
	public static void add(int i, int j, int d, int preD, int preC, int a, int b, char[][] map, boolean[][][] visited,
			PriorityQueue<int[]> heap) {
		if (i < 0 || i == map.length || j < 0 || j == map[0].length || map[i][j] == 'X' || visited[i][j][d]) {
			return;
		}
		int cost = preC + a;
		if (d != preD) {
			cost += b;
		}
		heap.add(new int[] { i, j, d, cost });
	}

	// 为了测试
	public static char[][] randomMap(int n, int m) {
		char[][] map = new char[n][m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				map[i][j] = Math.random() < 0.5 ? 'O' : 'X';
			}
		}
		int si = (int) (Math.random() * n);
		int sj = (int) (Math.random() * m);
		map[si][sj] = 'S';
		int ei, ej;
		do {
			ei = (int) (Math.random() * n);
			ej = (int) (Math.random() * m);
		} while (ei == si && ej == sj);
		map[ei][ej] = 'E';
		return map;
	}

	public static void main(String[] args) {
		int n = 3;
		int m = 4;
		int v = 10;
		System.out.println("功能测试开始");
		for (int i = 0; i < 2000; i++) {
			char[][] map = randomMap(n, m);
			int a = (int) (Math.random() * v) + 1;
			int b = (int) (Math.random() * v) + 1;
			int ans1 = minCost1(map, a, b);
			int ans2 = minCost2(map, a, b);
			if (ans1 != ans2) {
				System.out.println("出错了");
				System.out.println(ans1);
				System.out.println(ans2);
			}
		}
		System.out.println("功能测试结束");

		System.out.println("性能测试开始");
		n = 1000;
		m = 1000;
		v = 100000;
		int a = (int) (Math.random() * v) + 1;
		int b = (int) (Math.random() * v) + 1;
		char[][] map = randomMap(n, m);
		System.out.println("数据规模 : " + n + " * " + m);
		System.out.println("通行代价 : " + a);
		System.out.println("转向代价 : " + b);
		long start = System.currentTimeMillis();
		minCost2(map, a, b);
		long end = System.currentTimeMillis();
		System.out.println("运行时间 : " + (end - start) + "毫秒");
		System.out.println("功能测试结束");
	}

}