package class094;

import java.util.Arrays;
import java.util.PriorityQueue;

// 雇佣K名工人的最低成本
// 有n名工人，给定两个数组quality和wage
// 其中quality[i]表示第i名工人的工作质量，其最低期望工资为wage[i]
// 现在我们想雇佣k名工人组成一个工资组
// 在雇佣一组k名工人时，我们必须按照下述规则向他们支付工资：
// 对工资组中的每名工人，应当按其工作质量与同组其他工人的工作质量的比例来支付工资
// 工资组中的每名工人至少应当得到他们的最低期望工资
// 给定整数k，返回组成满足上述条件的付费群体所需的最小金额
// 测试链接 : https://leetcode.cn/problems/minimum-cost-to-hire-k-workers/
public class Code04_MinimumCostToHireWorkers {

	public static class Employee {
		public double ratio; // 薪水 / 质量的比例
		public int quality;

		public Employee(double r, int q) {
			ratio = r;
			quality = q;
		}
	}

	public static double mincostToHireWorkers(int[] quality, int[] wage, int k) {
		int n = quality.length;
		Employee[] employees = new Employee[n];
		for (int i = 0; i < n; i++) {
			employees[i] = new Employee((double) wage[i] / quality[i], quality[i]);
		}
		// 根据比例排序，比例小的在前，比例大的在后
		Arrays.sort(employees, (a, b) -> a.ratio <= b.ratio ? -1 : 1);
		// 大根堆，用来收集最小的前k个质量数值
		PriorityQueue<Integer> heap = new PriorityQueue<Integer>((a, b) -> b - a);
		// 堆里，最小的前k个质量数值，总和是多少
		int qualitySum = 0;
		double ans = Double.MAX_VALUE;
		for (int i = 0, curQuality; i < n; i++) {
			curQuality = employees[i].quality;
			if (heap.size() < k) { // 堆没满
				qualitySum += curQuality;
				heap.add(curQuality);
				if (heap.size() == k) {
					ans = Math.min(ans, qualitySum * employees[i].ratio);
				}
			} else { // 堆满了
				if (heap.peek() > curQuality) {
					qualitySum += curQuality - heap.poll();
					heap.add(curQuality);
					ans = Math.min(ans, qualitySum * employees[i].ratio);
				}
			}
		}
		return ans;
	}

}
