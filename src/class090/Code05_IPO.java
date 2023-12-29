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

	public static class Project {
		public int p; // 纯利润
		public int c; // 需要的启动金

		public Project(int profit, int cost) {
			p = profit;
			c = cost;
		}
	}

	public static int findMaximizedCapital(int k, int w, int[] profit, int[] cost) {
		int n = profit.length;
		// 需要的启动金小根堆
		// 代表被锁住的项目
		PriorityQueue<Project> heap1 = new PriorityQueue<>((a, b) -> a.c - b.c);
		// 利润大根堆
		// 代表被解锁的项目
		PriorityQueue<Project> heap2 = new PriorityQueue<>((a, b) -> b.p - a.p);
		for (int i = 0; i < n; i++) {
			heap1.add(new Project(profit[i], cost[i]));
		}
		while (k > 0) {
			while (!heap1.isEmpty() && heap1.peek().c <= w) {
				heap2.add(heap1.poll());
			}
			if (heap2.isEmpty()) {
				break;
			}
			w += heap2.poll().p;
			k--;
		}
		return w;
	}

}
