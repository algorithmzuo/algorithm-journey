package class098;

// 多米诺和托米诺平铺
// 有两种形状的瓷砖，一种是2*1的多米诺形，另一种是形如"L"的托米诺形
// 两种形状都可以旋转，给定整数n，返回可以平铺2*n的面板的方法数量
// 返回对1000000007取模的值
// 测试链接 : https://leetcode.cn/problems/domino-and-tromino-tiling/
public class Code05_DominoTromino {

	// f(1) = 1
	// f(2) = 2
	// f(3) = 5
	// f(4) = 11
	// f(n) = 2 * f(n-1) + f(n-3)
	// 打表或者公式化简都可以发现规律，这里推荐打表找规律
	public static void main(String[] args) {
		for (int i = 1; i <= 9; i++) {
			System.out.println("铺满 2 * " + i + " 的区域方法数 : " + f(i, 0));
		}
	}

	// 暴力方法
	// 为了找规律
	// 如果h==0，返回2*n的区域铺满的方法数
	// 如果h==1，返回1 + 2*n的区域铺满的方法数
	public static int f(int n, int h) {
		if (n == 0) {
			return h == 0 ? 1 : 0;
		}
		if (n == 1) {
			return 1;
		}
		if (h == 1) {
			return f(n - 1, 0) + f(n - 1, 1);
		} else {
			return f(n - 1, 0) + f(n - 2, 0) + 2 * f(n - 2, 1);
		}
	}

	// 正式方法
	// 矩阵快速幂
	// 时间复杂度O(logn)
	public static int numTilings(int n) {
		return f2(n - 1);
	}

	public static int MOD = 1000000007;

	public static int f2(int n) {
		if (n == 0) {
			return 1;
		}
		if (n == 1) {
			return 2;
		}
		if (n == 2) {
			return 5;
		}
		int[][] start = { { 5, 2, 1 } };
		int[][] base = {
				{ 2, 1, 0 },
				{ 0, 0, 1 },
				{ 1, 0, 0 }
				};
		int[][] ans = multiply(start, power(base, n - 2));
		return ans[0][0];
	}

	// 矩阵相乘 + 乘法取模
	// a的列数一定要等于b的行数
	public static int[][] multiply(int[][] a, int[][] b) {
		int n = a.length;
		int m = b[0].length;
		int k = a[0].length;
		int[][] ans = new int[n][m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				for (int c = 0; c < k; c++) {
					ans[i][j] = (int) (((long) a[i][c] * b[c][j] + ans[i][j]) % MOD);
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
