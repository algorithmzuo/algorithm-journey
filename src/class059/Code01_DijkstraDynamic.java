package class059;

import java.util.ArrayList;
import java.util.PriorityQueue;

// Dijkstra算法模版，leetcode测试
// 测试链接 : https://leetcode.cn/problems/network-delay-time
public class Code01_DijkstraDynamic {

	// 几乎都是动态结构，如果想要全静态结构看第二个实现，洛谷测试的实现
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
		boolean[] used = new boolean[n + 1];
		int num = 0;
		int max = 0;
		while (!heap.isEmpty() && num < n) {
			int[] record = heap.poll();
			int cur = record[0];
			int delay = record[1];
			if (used[cur]) {
				continue;
			}
			used[cur] = true;
			num++;
			max = Math.max(max, delay);
			for (int[] next : graph.get(cur)) {
				if (!used[next[0]]) {
					heap.add(new int[] { next[0], delay + next[1] });
				}
			}
		}
		return num < n ? -1 : max;
	}

}
