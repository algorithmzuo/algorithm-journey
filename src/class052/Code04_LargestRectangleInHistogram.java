package class052;

// 柱状图中最大的矩形
// 给定 n 个非负整数，用来表示柱状图中各个柱子的高度
// 每个柱子彼此相邻，且宽度为 1 。求在该柱状图中，能够勾勒出来的矩形的最大面积
// 测试链接：https://leetcode.cn/problems/largest-rectangle-in-histogram
public class Code04_LargestRectangleInHistogram {

	public static int MAXN = 100001;

	public static int[] stack = new int[MAXN];

	public static int r;

	public static int largestRectangleArea(int[] height) {
		int n = height.length;
		r = 0;
		int ans = 0, cur, left;
		for (int i = 0; i < n; i++) {
			// i -> arr[i]
			while (r > 0 && height[stack[r - 1]] >= height[i]) {
				cur = stack[--r];
				left = r == 0 ? -1 : stack[r - 1];
				ans = Math.max(ans, height[cur] * (i - left - 1));
			}
			stack[r++] = i;
		}
		while (r > 0) {
			cur = stack[--r];
			left = r == 0 ? -1 : stack[r - 1];
			ans = Math.max(ans, height[cur] * (n - left - 1));
		}
		return ans;
	}

}
