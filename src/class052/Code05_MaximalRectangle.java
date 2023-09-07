package class052;

import java.util.Arrays;

// 最大矩形
// 给定一个仅包含 0 和 1 、大小为 rows * cols 的二维二进制矩阵
// 找出只包含 1 的最大矩形，并返回其面积
// 测试链接：https://leetcode.cn/problems/maximal-rectangle/
public class Code05_MaximalRectangle {

	public static int maximalRectangle(char[][] map) {
		int n = map.length;
		int m = map[0].length;
		Arrays.fill(height, 0, m, 0);
		int ans = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				height[j] = map[i][j] == '0' ? 0 : height[j] + 1;
			}
			ans = Math.max(largestRectangleArea(m), ans);
		}
		return ans;
	}

	public static int MAXN = 201;

	public static int[] height = new int[MAXN];

	public static int[] stack = new int[MAXN];

	public static int r;

	public static int largestRectangleArea(int m) {
		r = 0;
		int ans = 0, cur, left;
		for (int i = 0; i < m; i++) {
			while (r > 0 && height[i] <= height[stack[r - 1]]) {
				cur = stack[--r];
				left = r == 0 ? -1 : stack[r - 1];
				ans = Math.max(ans, (i - left - 1) * height[cur]);
			}
			stack[r++] = i;
		}
		while (r > 0) {
			cur = stack[--r];
			left = r == 0 ? -1 : stack[r - 1];
			ans = Math.max(ans, (m - left - 1) * height[cur]);
		}
		return ans;
	}

}
