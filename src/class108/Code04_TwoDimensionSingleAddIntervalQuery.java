package class108;

// 二维数组上单点增加、范围查询，使用树状数组的模版
// 测试链接 : https://leetcode.cn/problems/range-sum-query-2d-mutable/
public class Code04_TwoDimensionSingleAddIntervalQuery {

	class NumMatrix {

		public int[][] tree;

		public int[][] nums;

		public int n;

		public int m;

		// 入参二维数组下标从0开始
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

		private void add(int x, int y, int v) {
			for (int i = x; i <= n; i += lowbit(i)) {
				for (int j = y; j <= m; j += lowbit(j)) {
					tree[i][j] += v;
				}
			}
		}

		// 从(1,1)到(x,y)这个部分的累加和
		private int sum(int x, int y) {
			int ans = 0;
			for (int i = x; i > 0; i -= lowbit(i)) {
				for (int j = y; j > 0; j -= lowbit(j)) {
					ans += tree[i][j];
				}
			}
			return ans;
		}

		// 实际二维数组的位置是(x,y)
		// 树状数组上的位置是(x+1, y+1)
		// 题目说的是单点更新，转化成单点增加(老值-新值)即可
		// 不要忘了在nums中把老值改成新值
		public void update(int x, int y, int v) {
			add(x + 1, y + 1, v - nums[x + 1][y + 1]);
			nums[x + 1][y + 1] = v;
		}

		// 实际二维数组的位置是(x,y)
		// 树状数组上的位置是(x+1, y+1)
		public int sumRegion(int a, int b, int c, int d) {
			return sum(c + 1, d + 1) - sum(a, d + 1) - sum(c + 1, b) + sum(a, b);
		}

	}

}
