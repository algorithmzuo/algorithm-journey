package class049;

// 加油站
// 在一条环路上有 n 个加油站，其中第 i 个加油站有汽油 gas[i] 升。
// 你有一辆油箱容量无限的的汽车，从第 i 个加油站开往第 i+1 个加油站需要消耗汽油 cost[i] 升
// 你从其中的一个加油站出发，开始时油箱为空。
// 给定两个整数数组 gas 和 cost ，如果你可以按顺序绕环路行驶一周
// 则返回出发时加油站的编号，否则返回 -1
// 如果存在解，则 保证 它是 唯一 的。
// 测试链接 : https://leetcode.cn/problems/gas-station/
public class Code04_GasStation {

	public static int canCompleteCircuit(int[] gas, int[] cost) {
		int n = gas.length;
		// 车辆尝试从0~n-1出发，看能不能走一圈，l
		// r : 窗口即将进来数字的位置
		// len : 窗口大小
		// sum : 窗口累加和
		for (int l = 0, r = 0, len = 0, sum = 0; l < n; l++) {
			while (sum >= 0) {
				// 当前窗口累加和>=0，尝试扩
				if (len == n) {
					return l;
				}
				// r : 窗口即将进来数字的位置
				r = (l + (len++)) % n;
				sum += gas[r] - cost[r];
			}
			// sum < 0，此时l位置无法转一圈
			len--;
			sum -= gas[l] - cost[l];
		}
		return -1;
	}

}
