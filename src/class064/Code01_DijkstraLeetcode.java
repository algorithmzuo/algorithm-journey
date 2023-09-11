package class064;

import java.util.ArrayList;
import java.util.PriorityQueue;

// Dijkstra算法模版（Leetcode），动态空间实现
// 网络延迟时间
// 有 n 个网络节点，标记为 1 到 n
// 给你一个列表 times，表示信号经过 有向 边的传递时间
// times[i] = (ui, vi, wi)，其中 ui 是源节点
// vi 是目标节点， wi 是一个信号从源节点传递到目标节点的时间
// 现在，从某个节点 K 发出一个信号。需要多久才能使所有节点都收到信号
// 如果不能使所有节点收到信号，返回 -1
// 测试链接 : https://leetcode.cn/problems/network-delay-time
public class Code01_DijkstraLeetcode {

	public static int networkDelayTime(int[][] times, int n, int k) {
		ArrayList<ArrayList<int[]>> graph = new ArrayList<>();
		for (int i = 0; i <= n; i++) {
			graph.add(new ArrayList<>());
		}
		for (int[] delay : times) {
			graph.get(delay[0]).add(new int[] { delay[1], delay[2] });
		}
		PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> a[1] - b[1]);
		heap.add(new int[] { k, 0 });
		boolean[] visited = new boolean[n + 1];
		int num = 0;
		int max = 0;
		while (!heap.isEmpty() && num < n) {
			int[] record = heap.poll();
			int cur = record[0];
			int delay = record[1];
			if (visited[cur]) {
				continue;
			}
			visited[cur] = true;
			num++;
			max = Math.max(max, delay);
			for (int[] next : graph.get(cur)) {
				if (!visited[next[0]]) {
					heap.add(new int[] { next[0], delay + next[1] });
				}
			}
		}
		return num < n ? -1 : max;
	}

}
