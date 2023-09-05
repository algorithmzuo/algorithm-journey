package class059;

import java.util.ArrayList;
import java.util.PriorityQueue;

// 电动车游城市
// 小明的电动车电量充满时可行驶距离为 cnt，每行驶 1 单位距离消耗 1 单位电量，且花费 1 单位时间
// 小明想选择电动车作为代步工具。地图上共有 N 个景点，景点编号为 0 ~ N-1
// 他将地图信息以 [城市 A 编号,城市 B 编号,两城市间距离] 格式整理在在二维数组 paths，
// 表示城市 A、B 间存在双向通路。
// 初始状态，电动车电量为 0。每个城市都设有充电桩，
// charge[i] 表示第 i 个城市每充 1 单位电量需要花费的单位时间。
// 请返回小明最少需要花费多少单位时间从起点城市 start 抵达终点城市 end
// 测试链接 : https://leetcode.cn/problems/DFPeFJ/
public class Code03_VisitCityMinCost {

	public static int electricCarPlan(int[][] paths, int cnt, int start, int end, int[] charge) {
		int n = charge.length;
		ArrayList<ArrayList<int[]>> graph = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			graph.add(new ArrayList<>());
		}
		for (int[] path : paths) {
			graph.get(path[0]).add(new int[] { path[1], path[2] });
			graph.get(path[1]).add(new int[] { path[0], path[2] });
		}
		boolean[][] visited = new boolean[n][cnt + 1];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j <= cnt; j++) {
				visited[i][j] = false;
			}
		}
		PriorityQueue<int[]> heap = new PriorityQueue<int[]>((x, y) -> (x[0] - y[0]));
		// 花费时间、当前点、电量
		heap.add(new int[] { 0, start, 0 });
		while (!heap.isEmpty()) {
			int[] record = heap.poll();
			int cost = record[0];
			int cur = record[1];
			int power = record[2];
			if (visited[cur][power]) {
				continue;
			}
			if (cur == end) {
				return cost;
			}
			visited[cur][power] = true;
			if (power < cnt) {
				if (!visited[cur][power + 1]) {
					heap.add(new int[] { cost + charge[cur], cur, power + 1 });
				}
			}
			for (int[] edge : graph.get(cur)) {
				int nextCity = edge[0];
				int nextCost = cost + edge[1];
				int restPower = power - edge[1];
				if (restPower >= 0 && !visited[nextCity][restPower]) {
					heap.add(new int[] { nextCost, nextCity, restPower });
				}
			}
		}
		return -1;
	}

}
