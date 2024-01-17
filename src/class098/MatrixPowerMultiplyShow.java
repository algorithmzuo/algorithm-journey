package class098;

public class MatrixPowerMultiplyShow {

	public static void main(String[] args) {
		System.out.println("矩阵乘法展示开始");
		int[][] a = { { 1, 3 }, { 4, 2 } };
		int[][] b = { { 2, 3 }, { 3, 2 } };
		//        2  3
		//        3  2
		//
		// 1  3   ?  ?
		// 4  2   ?  ?
		int[][] ans1 = multiply(a, b);
		print(ans1);
		System.out.println("======");
		int[][] c = { { 2, 4, 1 }, { 3, 2, 2 } };
		int[][] d = { { 2, 3, 2 }, { 3, 2, 3 }, { 1, 1, 4 } };
		//           2  3  2
		//           3  2  3
		//           1  1  4
		//
		// 2  4  1   ?  ?  ?
		// 3  2  2   ?  ?  ?
		int[][] ans2 = multiply(c, d);
		print(ans2);
		System.out.println("======");
		int[][] e = { { 3, 1, 2 } };
		int[][] f = { { 1, 2, 1 }, { 3, 2, 1 }, { 4, 2, -2 } };
		//           1  2  1
		//           3  2  1
		//           4  2 -2
		//
		// 3  1  2   ?  ?  ?
		int[][] ans3 = multiply(e, f);
		print(ans3);
		System.out.println("矩阵乘法展示结束");
		System.out.println();
		System.out.println("求斐波那契数列第n项");
		System.out.println("用矩阵乘法解决");
		System.out.println("展示开始");
		f1();
		System.out.println("展示结束");
		System.out.println();
		System.out.println("求斐波那契数列第n项");
		System.out.println("用矩阵快速幂解决");
		System.out.println("展示开始");
		f2();
		System.out.println("展示结束");
		System.out.println();
	}

	// 打印二维矩阵
	public static void print(int[][] m) {
		for (int i = 0; i < m.length; i++) {
			for (int j = 0; j < m[0].length; j++) {
				if (m[i][j] < 10) {
					System.out.print(m[i][j] + "   ");
				} else if (m[i][j] < 100) {
					System.out.print(m[i][j] + "  ");
				} else {
					System.out.print(m[i][j] + " ");
				}
			}
			System.out.println();
		}
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

	// 用矩阵乘法解决
	public static void f1() {
		// 0  1  1  2  3  5  8 13 21 34...
		// 0  1  2  3  4  5  6  7  8  9
		int[][] m = { { 1, 1 }, { 1, 0 } };
		int[][] start = { { 1, 0 } };
		int[][] a = multiply(start, m);
		print(a);
		System.out.println("======");
		int[][] b = multiply(a, m);
		print(b);
		System.out.println("======");
		int[][] c = multiply(b, m);
		print(c);
		System.out.println("======");
		int[][] d = multiply(c, m);
		print(d);
	}

	// 用矩阵快速幂解决
	public static void f2() {
		// 0  1  1  2  3  5  8 13 21 34...
		// 0  1  2  3  4  5  6  7  8  9
		int[][] m = { { 1, 1 }, { 1, 0 } };
		int[][] start = { { 1, 0 } };
		int[][] ans = multiply(start, power(m, 1));
		print(ans);
		System.out.println("======");
		ans = multiply(start, power(m, 2));
		print(ans);
		System.out.println("======");
		ans = multiply(start, power(m, 3));
		print(ans);
		System.out.println("======");
		ans = multiply(start, power(m, 4));
		print(ans);
	}

}
