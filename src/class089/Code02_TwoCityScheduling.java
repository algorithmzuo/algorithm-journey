package class089;

import java.util.Arrays;

// 两地调度
// 公司计划面试2n个人，给定一个数组 costs
// 其中costs[i]=[aCosti, bCosti]
// 表示第i人飞往a市的费用为aCosti，飞往b市的费用为bCosti
// 返回将每个人都飞到a、b中某座城市的最低费用
// 要求每个城市都有n人抵达
// 测试链接 : https://leetcode.cn/problems/two-city-scheduling/
public class Code02_TwoCityScheduling {

	public static int twoCitySchedCost(int[][] costs) {
		int n = costs.length;
		int[] arr = new int[n];
		int sum = 0;
		for (int i = 0; i < n; i++) {
			arr[i] = costs[i][1] - costs[i][0];
			sum += costs[i][0];
		}
		Arrays.sort(arr);
		int m = n / 2;
		for (int i = 0; i < m; i++) {
			sum += arr[i];
		}
		return sum;
	}

}
