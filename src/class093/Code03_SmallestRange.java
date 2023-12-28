package class093;

import java.util.List;
import java.util.TreeSet;

// 最小区间
// 你有k个非递减排列的整数列表
// 找到一个最小区间，使得k个列表中的每个列表至少有一个数包含在其中
// 测试链接 : https://leetcode.cn/problems/smallest-range-covering-elements-from-k-lists/
public class Code03_SmallestRange {

	public static int[] smallestRange(List<List<Integer>> nums) {
		int n = nums.size();
		// 0 : 值
		// 1 : 哪个数组
		// 2 : 哪个下标
		TreeSet<int[]> set = new TreeSet<>((a, b) -> a[0] != b[0] ? (a[0] - b[0]) : (a[1] - b[1]));
		for (int i = 0; i < n; i++) {
			set.add(new int[] { nums.get(i).get(0), i, 0 });
		}
		int r = Integer.MAX_VALUE;
		int a = 0;
		int b = 0;
		int[] max, min;
		while (set.size() == n) {
			max = set.last();
			min = set.pollFirst();
			if (max[0] - min[0] < r) {
				r = max[0] - min[0];
				a = min[0];
				b = max[0];
			}
			if (min[2] < nums.get(min[1]).size() - 1) {
				set.add(new int[] { nums.get(min[1]).get(min[2] + 1), min[1], min[2] + 1 });
			}
		}
		return new int[] { a, b };
	}

}
