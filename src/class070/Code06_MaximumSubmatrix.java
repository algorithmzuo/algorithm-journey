package class070;

import java.util.Arrays;

// 子矩阵最大累加和问题
// 给定一个二维数组grid，找到其中子矩阵的最大累加和
// 返回拥有最大累加和的子矩阵左上角和右下角坐标
// 如果有多个子矩阵都有最大累加和，返回任何一个都可以
// 测试链接 : https://leetcode.cn/problems/max-submatrix-lcci/
public class Code06_MaximumSubmatrix {

	// 如果行和列的规模都是n
	// 时间复杂度O(n^3)是这道题的极限了
	public static int[] getMaxMatrix(int[][] grid) {
		int n = grid.length;
		int m = grid[0].length;
		int max = grid[0][0];
		int a = 0, b = 0, c = 0, d = 0;
		int[] nums = new int[m];
		for (int up = 0; up < n; up++) {
			Arrays.fill(nums, 0);
			for (int down = up; down < n; down++) {
				for (int left = 0, right = 0, pre = Integer.MIN_VALUE; right < m; right++) {
					nums[right] += grid[down][right];
					if (pre >= 0) {
						pre += nums[right];
					} else {
						pre = nums[right];
						left = right;
					}
					if (pre > max) {
						max = pre;
						a = up;
						b = left;
						c = down;
						d = right;
					}
				}
			}
		}
		return new int[] { a, b, c, d };
	}

}
