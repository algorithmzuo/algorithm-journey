package class091;

import java.util.List;
import java.util.TreeSet;

// 最小区间
// 你有k个非递减排列的整数列表
// 找到一个最小区间，使得k个列表中的每个列表至少有一个数包含在其中
// 测试链接 : https://leetcode.cn/problems/smallest-range-covering-elements-from-k-lists/
public class Code02_SmallestRange {

	public static class Node {
		public int v; // 值
		public int i; // 当前值来自哪个数组
		public int j; // 当前值来自i号数组的什么位置

		public Node(int a, int b, int c) {
			v = a;
			i = b;
			j = c;
		}
	}

	// 时间复杂度O(n*logk)
	// n是数字总数，k是数组数量
	public static int[] smallestRange(List<List<Integer>> nums) {
		int k = nums.size();
		// 根据值排序
		// 为什么排序的时候i要参与
		// 因为有序表中比较相等的样本只会保留一个
		// 为了值一样的元素都保留，于是i要参与排序
		// 在有序表中的所有元素i必然都不同
		TreeSet<Node> set = new TreeSet<>((a, b) -> a.v != b.v ? (a.v - b.v) : (a.i - b.i));
		for (int i = 0; i < k; i++) {
			set.add(new Node(nums.get(i).get(0), i, 0));
		}
		int r = Integer.MAX_VALUE; // 记录最窄区间的宽度
		int a = 0; // 记录最窄区间的开头
		int b = 0; // 记录最窄区间的结尾
		Node max, min;
		while (set.size() == k) {
			max = set.last(); // 在有序表中，值最大的记录
			min = set.pollFirst(); // 在有序表中，值最小的记录，并弹出
			if (max.v - min.v < r) {
				r = max.v - min.v;
				a = min.v;
				b = max.v;
			}
			if (min.j + 1 < nums.get(min.i).size()) {
				set.add(new Node(nums.get(min.i).get(min.j + 1), min.i, min.j + 1));
			}
		}
		return new int[] { a, b };
	}

}
