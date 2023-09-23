package class052;

import java.util.Arrays;

// 最大矩形
// 给定一个仅包含 0 和 1 、大小为 rows * cols 的二维二进制矩阵
// 找出只包含 1 的最大矩形，并返回其面积
// 测试链接：https://leetcode.cn/problems/maximal-rectangle/
public class Code05_MaximalRectangle {

	public static int MAXN = 201;

	public static int[] height = new int[MAXN];

	public static int[] stack = new int[MAXN];

	public static int r;

	public static int maximalRectangle(char[][] grid) {
		int n = grid.length;
		int m = grid[0].length;
		Arrays.fill(height, 0, m, 0);
		int ans = 0;
		for (int i = 0; i < n; i++) {
			// 来到i行，长方形一定要以i行做底！
			// 加工高度数组(压缩数组)
			for (int j = 0; j < m; j++) {
				height[j] = grid[i][j] == '0' ? 0 : height[j] + 1;
			}
			ans = Math.max(largestRectangleArea(m), ans);
		}
		return ans;
	}

	public static int largestRectangleArea(int m) {
		r = 0;
		int ans = 0, cur, left;
		for (int i = 0; i < m; i++) {
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
			ans = Math.max(ans, height[cur] * (m - left - 1));
		}
		return ans;
	}

}
