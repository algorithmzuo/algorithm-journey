package class062;

import java.util.ArrayDeque;

// 到达角落需要移除障碍物的最小数目
// 给你一个下标从 0 开始的二维整数数组 grid ，数组大小为 m x n
// 每个单元格都是两个值之一：
// 0 表示一个 空 单元格，
// 1 表示一个可以移除的 障碍物
// 你可以向上、下、左、右移动，从一个空单元格移动到另一个空单元格。
// 现在你需要从左上角 (0, 0) 移动到右下角 (m - 1, n - 1) 
// 返回需要移除的障碍物的最小数目
// 测试链接 : https://leetcode.cn/problems/minimum-obstacle-removal-to-reach-corner/
public class Code03_MinimumObstacleRemovalToReachCorner {
    //todo 这个题采用01dfs的方法。需要双端队列来调整最优路径，路径没有阻碍的就从头加入，路径有阻碍就从尾加入
	public static int minimumObstacles(int[][] grid) {
		int[] move = { -1, 0, 1, 0, -1 };
		int m = grid.length;
		int n = grid[0].length;
		int[][] distance = new int[m][n];
        //todo 设置所有距离都是不可达
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				distance[i][j] = Integer.MAX_VALUE;
			}
		}
        //todo 构建双端队列，队列中存放x y坐标
		ArrayDeque<int[]> deque = new ArrayDeque<>();
        //todo 当前节点从头入队，距离设置为0
		deque.addFirst(new int[] { 0, 0 });
		distance[0][0] = 0;
		while (!deque.isEmpty()) {
            //todo 从头弹出
			int[] record = deque.pollFirst();
			int x = record[0];
			int y = record[1];
            //todo 如果找到末尾节点，返回距离
			if (x == m - 1 && y == n - 1) {
				return distance[x][y];
			}
            //todo 遍历四个方向，如果找到距离更小的点，判断当前点是否为0，为0则从头加入，否则从尾加入。
			for (int i = 0; i < 4; i++) {
				int nx = x + move[i], ny = y + move[i + 1];
				if (nx >= 0 && nx < m && ny >= 0 && ny < n &&
						distance[x][y] + grid[nx][ny] < distance[nx][ny]) {
					distance[nx][ny] = distance[x][y] + grid[nx][ny];
					if (grid[nx][ny] == 0) {
						deque.addFirst(new int[] { nx, ny });
					} else {
						deque.addLast(new int[] { nx, ny });
					}
				}
			}
		}
		return -1;
	}

}
