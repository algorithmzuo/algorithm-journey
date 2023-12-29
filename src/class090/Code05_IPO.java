package class090;

import java.util.PriorityQueue;

// IPO
// 给你n个项目，对于每个项目i
// 它都有一个纯利润profits[i]
// 和启动该项目需要的最小资本capital[i]
// 最初你的资本为w，当你完成一个项目时，你将获得纯利润，添加到你的总资本中
// 总而言之，从给定项目中选择最多k个不同项目的列表
// 以最大化最终资本，并输出最终可获得的最多资本
// 测试链接 : https://leetcode.cn/problems/ipo/
public class Code05_IPO {

	public static int findMaximizedCapital(int k, int w, int[] profit, int[] cost) {
		int n = profit.length;
		PriorityQueue<int[]> heap1 = new PriorityQueue<>((a, b) -> a[1] - b[1]);
		PriorityQueue<Integer> heap2 = new PriorityQueue<>((a, b) -> b.compareTo(a));
		for (int i = 0; i < n; i++) {
			heap1.add(new int[] { profit[i], cost[i] });
		}
		while (k-- > 0) {
			while (!heap1.isEmpty() && heap1.peek()[1] <= w) {
				heap2.add(heap1.poll()[0]);
			}
			if (heap2.isEmpty()) {
				break;
			}
			w += heap2.poll();
		}
		return w;
	}

}
