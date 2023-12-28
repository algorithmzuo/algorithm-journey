package class089;

import java.util.PriorityQueue;

// 连接棒材的最低费用(leetcode测试)
// 你有一些长度为正整数的棍子
// 这些长度以数组sticks的形式给出
// sticks[i]是第i个木棍的长度
// 你可以通过支付x+y的成本将任意两个长度为x和y的棍子连接成一个棍子
// 你必须连接所有的棍子，直到剩下一个棍子
// 返回以这种方式将所有给定的棍子连接成一个棍子的最小成本
// 测试链接 : https://leetcode.cn/problems/minimum-cost-to-connect-sticks/
public class Code06_MinimumCostToConnectSticks1 {

	public static int connectSticks(int[] arr) {
		// 小根堆
		PriorityQueue<Integer> heap = new PriorityQueue<>();
		for (int i = 0; i < arr.length; i++) {
			heap.add(arr[i]);
		}
		int sum = 0;
		int cur = 0;
		while (heap.size() > 1) {
			cur = heap.poll() + heap.poll();
			sum += cur;
			heap.add(cur);
		}
		return sum;
	}

}
