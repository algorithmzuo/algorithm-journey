package class055;

// 最大矩形
// 给定一个仅包含 0 和 1 、大小为 rows * cols 的二维二进制矩阵
// 找出只包含 1 的最大矩形，并返回其面积
// 测试链接：https://leetcode.cn/problems/maximal-rectangle/
public class Code06_MaximalRectangle {

	public static int maximalRectangle(char[][] map) {
		int n = map.length;
		int m = map[0].length;
		int[] height = new int[m];
		int ans = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				height[j] = map[i][j] == '0' ? 0 : height[j] + 1;
			}
			ans = Math.max(largestRectangleArea(height), ans);
		}
		return ans;
	}

	public static int MAXN = 201;

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
