package class091;

import java.util.List;
import java.util.TreeSet;

// 最小区间
// 你有k个非递减排列的整数列表
// 找到一个最小区间，使得k个列表中的每个列表至少有一个数包含在其中
// 测试链接 : https://leetcode.cn/problems/smallest-range-covering-elements-from-k-lists/
public class Code02_SmallestRange {

	public static class Node {
		public int v;
		public int i;
		public int j;

		public Node(int a, int b, int c) {
			v = a;
			i = b;
			j = c;
		}
	}

	public static int[] smallestRange(List<List<Integer>> nums) {
		int n = nums.size();
		TreeSet<Node> set = new TreeSet<>((a, b) -> a.v != b.v ? (a.v - b.v) : (a.i - b.i));
		for (int i = 0; i < n; i++) {
			set.add(new Node(nums.get(i).get(0), i, 0));
		}
		int r = Integer.MAX_VALUE;
		int a = 0;
		int b = 0;
		Node max, min;
		while (set.size() == n) {
			max = set.last();
			min = set.pollFirst();
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
