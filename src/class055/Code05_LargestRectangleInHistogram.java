package class055;

// 柱状图中最大的矩形
// 给定 n 个非负整数，用来表示柱状图中各个柱子的高度
// 每个柱子彼此相邻，且宽度为 1 。求在该柱状图中，能够勾勒出来的矩形的最大面积
// 测试链接：https://leetcode.cn/problems/largest-rectangle-in-histogram
public class Code05_LargestRectangleInHistogram {

	public static int MAXN = 100001;

	public static int[] stack = new int[MAXN];

	public static int largestRectangleArea(int[] height) {
		int n = height.length;
		int size = 0;
		int ans = 0, cur, left;
		for (int i = 0; i < n; i++) {
			while (size != 0 && height[i] <= height[stack[size - 1]]) {
				cur = stack[--size];
				left = size == 0 ? -1 : stack[size - 1];
				ans = Math.max(ans, (i - left - 1) * height[cur]);
			}
			stack[size++] = i;
		}
		while (size > 0) {
			cur = stack[--size];
			left = size == 0 ? -1 : stack[size - 1];
			ans = Math.max(ans, (n - left - 1) * height[cur]);
		}
		return ans;
	}

}
