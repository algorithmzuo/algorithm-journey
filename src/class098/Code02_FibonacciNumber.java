package class098;

// 求斐波那契数列第n项
// 测试链接 : https://leetcode.cn/problems/fibonacci-number/
// 这个测试的数据量太小，并且不牵扯取模的事情
// 所以矩阵快速幂看不出优势
public class Code02_FibonacciNumber {

	// 时间复杂度O(n)，普通解法，讲解066，题目1
	public static int fib1(int n) {
		if (n == 0) {
			return 0;
		}
		if (n == 1) {
			return 1;
		}
		int lastLast = 0, last = 1;
		for (int i = 2, cur; i <= n; i++) {
			cur = lastLast + last;
			lastLast = last;
			last = cur;
		}
		return last;
	}

	// 时间复杂度O(logn)，矩阵快速幂的解法
	public static int fib2(int n) {
		if (n == 0) {
			return 0;
		}
		if (n == 1) {
			return 1;
		}
		int[][] start = { { 1, 0 } };
		int[][] base = {
				{ 1, 1 },
				{ 1, 0 }
				};
		int[][] ans = multiply(start, power(base, n - 1));
		return ans[0][0];
	}

	// 矩阵相乘
	// a的列数一定要等于b的行数
	public static int[][] multiply(int[][] a, int[][] b) {
		int n = a.length;
		int m = b[0].length;
		int k = a[0].length;
		int[][] ans = new int[n][m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				for (int c = 0; c < k; c++) {
					ans[i][j] += a[i][c] * b[c][j];
				}
			}
		}
		return ans;
	}

	// 矩阵快速幂
	public static int[][] power(int[][] m, int p) {
		int n = m.length;
		int[][] ans = new int[n][n];
		for (int i = 0; i < n; i++) {
			ans[i][i] = 1;
		}
		for (; p != 0; p >>= 1) {
			if ((p & 1) != 0) {
				ans = multiply(ans, m);
			}
			m = multiply(m, m);
		}
		return ans;
	}

}
