package class108;

// 二维数组上单点增加、范围查询，使用树状数组的模版
// 测试链接 : https://leetcode.com/problems/range-sum-query-2d-mutable/
public class Code04_IndexTreeTwoDimensionSingleAddIntervalQuery {

	class NumMatrix {

		public int[][] tree;

		public int[][] nums;

		public int n;

		public int m;

		// 注意入参的二维数组下标从0开始
		// 树状数组一定下标从1开始
		public NumMatrix(int[][] matrix) {
			n = matrix.length;
			m = matrix[0].length;
			tree = new int[n + 1][m + 1];
			nums = new int[n + 1][m + 1];
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < m; j++) {
					update(i, j, matrix[i][j]);
				}
			}
		}

		private int lowbit(int i) {
			return i & -i;
		}

		// 实际二维数组的位置是(x,y)
		// 树状数组上的位置是(x+1, y+1)
		// 题目说的是单点更新，转化成单点增加(老值-新值)即可
		// 不要忘了在nums中把老值改成新值即可
		public void update(int x, int y, int val) {
			int add = val - nums[x + 1][y + 1];
			nums[x + 1][y + 1] = val;
			for (int i = x + 1; i <= n; i += lowbit(i)) {
				for (int j = y + 1; j <= m; j += lowbit(j)) {
					tree[i][j] += add;
				}
			}
		}

		// 实际二维数组的位置是(x,y)
		// 树状数组上的位置是(x+1, y+1)
		private int sum(int x, int y) {
			int sum = 0;
			for (int i = x + 1; i > 0; i -= lowbit(i)) {
				for (int j = y + 1; j > 0; j -= lowbit(j)) {
					sum += tree[i][j];
				}
			}
			return sum;
		}

		public int sumRegion(int row1, int col1, int row2, int col2) {
			return sum(row2, col2) - sum(row1 - 1, col2) - sum(row2, col1 - 1) + sum(row1 - 1, col1 - 1);
		}

	}

}
