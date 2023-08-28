package class055;

// 柱状图中最大的矩形
// 给定 n 个非负整数，用来表示柱状图中各个柱子的高度
// 每个柱子彼此相邻，且宽度为 1 。求在该柱状图中，能够勾勒出来的矩形的最大面积
// 测试链接：https://leetcode.cn/problems/largest-rectangle-in-histogram
public class Code05_LargestRectangleInHistogram {

	public static int largestRectangleArea(int[] height) {
		int n = height.length;
		int[] stack = new int[n];
		int r = 0;
		int ans = 0;
		for (int i = 0; i < height.length; i++) {
			while (r != 0 && height[i] <= height[stack[r - 1]]) {
				int j = stack[--r];
				int k = r == 0 ? -1 : stack[r - 1];
				int curArea = (i - k - 1) * height[j];
				ans = Math.max(ans, curArea);
			}
			stack[r++] = i;
		}
		while (r > 0) {
			int j = stack[--r];
			int k = r == 0 ? -1 : stack[r - 1];
			int curArea = (height.length - k - 1) * height[j];
			ans = Math.max(ans, curArea);
		}
		return ans;
	}

}
