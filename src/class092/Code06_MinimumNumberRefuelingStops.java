package class092;

import java.util.PriorityQueue;

// 最低加油次数
// 汽车从起点出发驶向目的地，该目的地位于出发位置东面target英里处
// 沿途有加油站，用数组stations表示，其中 stations[i] = [positioni, fueli]
// 表示第i个加油站位于出发位置东面positioni英里处，并且有fueli升汽油
// 假设汽车油箱的容量是无限的，其中最初有startFuel升燃料
// 它每行驶1英里就会用掉1升汽油
// 当汽车到达加油站时，它可能停下来加油，将所有汽油从加油站转移到汽车中
// 为了到达目的地，汽车所必要的最低加油次数是多少？
// 如果无法到达目的地，则返回-1
// 注意：如果汽车到达加油站时剩余燃料为0，它仍然可以在那里加油
// 如果汽车到达目的地时剩余燃料为 0，仍然认为它已经到达目的地
// 测试链接 : https://leetcode.cn/problems/minimum-number-of-refueling-stops/
public class Code06_MinimumNumberRefuelingStops {

	public static int minRefuelStops(int target, int startFuel, int[][] stations) {
		if (startFuel >= target) {
			return 0;
		}
		// 大根堆
		PriorityQueue<Integer> heap = new PriorityQueue<>((a, b) -> b - a);
		// 包括初始油量 + 沿途加的油，能让你达到什么位置，to
		int to = startFuel;
		int cnt = 0;
		for (int[] station : stations) {
			int position = station[0];
			int fuel = station[1];
			if (to < position) {
				while (!heap.isEmpty() && to < position) {
					to += heap.poll();
					cnt++;
					if (to >= target) {
						return cnt;
					}
				}
				if (to < position) {
					return -1;
				}
			}
			heap.add(fuel);
		}
		// 代码能走到这里，说明还没到达target
		// 如果还有油，看看能不能冲到target
		while (!heap.isEmpty()) {
			to += heap.poll();
			cnt++;
			if (to >= target) {
				return cnt;
			}
		}
		return -1;
	}

}
