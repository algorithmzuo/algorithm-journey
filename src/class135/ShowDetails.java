package class135;

// 课上讲述高斯消元解决同余方程组的例子

public class ShowDetails {

	// 保证模的数字一定为质数
	// 题目一定会保证这一点
	public static int MOD = 7;

	public static int MAXN = 101;

	public static int[][] mat = new int[MAXN][MAXN];

	// 逆元表
	// 逆元线性递推公式
	// 如果不会，去看讲解099 - 除法同余
	public static int[] inv = new int[MOD];

	public static void inv() {
		inv[1] = 1;
		for (int i = 2; i < MOD; i++) {
			inv[i] = (int) (MOD - (long) inv[MOD % i] * (MOD / i) % MOD);
		}
	}

	// 求a和b的最大公约数，保证a和b都不等于0
	public static int gcd(int a, int b) {
		return b == 0 ? a : gcd(b, a % b);
	}

	// 高斯消元解决同余方程组模版
	// 保证初始系数都是非负数，如果系数a是负数，转化为非负数，a = (a % mod + mod) % mod
	public static void gauss(int n) {
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {
				if (j < i && mat[j][j] != 0) {
					continue;
				}
				if (mat[j][i] != 0) {
					swap(i, j);
					break;
				}
			}
			if (mat[i][i] != 0) {
				for (int j = 1; j <= n; j++) {
					if (i != j && mat[j][i] != 0) {
						int gcd = gcd(mat[j][i], mat[i][i]);
						int a = mat[i][i] / gcd;
						int b = mat[j][i] / gcd;
						if (j < i && mat[j][j] != 0) {
							for (int k = j; k < i; k++) {
								mat[j][k] = (mat[j][k] * a) % MOD;
							}
						}
						for (int k = i; k <= n + 1; k++) {
							mat[j][k] = ((mat[j][k] * a - mat[i][k] * b) % MOD + MOD) % MOD;
						}
					}
				}
			}
		}
		for (int i = 1; i <= n; i++) {
			if (mat[i][i] != 0) {
				// 检查是否有自由元影响当前的主元
				boolean flag = false;
				for (int j = i + 1; j <= n; j++) {
					if (mat[i][j] != 0) {
						flag = true;
						break;
					}
				}
				// 如果当前主元不受自由元影响，那么可以确定当前主元的值
				// 否则不如保留影响，正确显示主元和自由元的关系
				if (!flag) {
					// 本来应该是，mat[i][n + 1] = mat[i][n + 1] / mat[i][i]
					// 但是在模意义下应该求逆元
					// (a / b) % MOD = (a * b的逆元) % MOD
					// 如果不会，去看讲解099 - 除法同余
					mat[i][n + 1] = (mat[i][n + 1] * inv[mat[i][i]]) % MOD;
				}
			}
		}
	}

	public static void swap(int a, int b) {
		int[] tmp = mat[a];
		mat[a] = mat[b];
		mat[b] = tmp;
	}

	public static void print(int n) {
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n + 1; j++) {
				System.out.print(mat[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println("========================");
	}

	public static void main(String[] args) {
		// 逆元表建立好
		inv();
		System.out.println("课上图解的例子，有唯一解");
		// 6*x1 + 2*x2 + 3*x3 同余 6
		// 1*x1 + 5*x2 + 2*x3 同余 5
		// 0*x1 + 3*x2 + 4*x3 同余 2
		mat[1][1] = 6; mat[1][2] = 2; mat[1][3] = 3; mat[1][4] = 6;
		mat[2][1] = 1; mat[2][2] = 5; mat[2][3] = 2; mat[2][4] = 5;
		mat[3][1] = 0; mat[3][2] = 3; mat[3][3] = 4; mat[3][4] = 2;
		gauss(3);
		print(3);

		System.out.println("表达式存在矛盾的例子");
		// 1*x1 + 2*x2 + 3*x3 同余 2
		// 2*x1 + 4*x2 + 6*x3 同余 5
		// 0*x1 + 3*x2 + 4*x3 同余 2
		mat[1][1] = 1; mat[1][2] = 2; mat[1][3] = 3; mat[1][4] = 2;
		mat[2][1] = 2; mat[2][2] = 4; mat[2][3] = 6; mat[2][4] = 5;
		mat[3][1] = 0; mat[3][2] = 3; mat[3][3] = 4; mat[3][4] = 2;
		gauss(3);
		print(3);

		System.out.println("表达式存在多解的例子");
		// 1*x1 + 2*x2 + 3*x3 同余 2
		// 2*x1 + 4*x2 + 6*x3 同余 4
		// 0*x1 + 3*x2 + 4*x3 同余 2
		mat[1][1] = 1; mat[1][2] = 2; mat[1][3] = 3; mat[1][4] = 2;
		mat[2][1] = 2; mat[2][2] = 4; mat[2][3] = 6; mat[2][4] = 4;
		mat[3][1] = 0; mat[3][2] = 3; mat[3][3] = 4; mat[3][4] = 2;
		gauss(3);
		print(3);

		System.out.println("注意下面这个多解的例子");
		// 1*x1 + 1*x2 + 1*x3 同余 3
		// 0*x1 + 2*x2 + 3*x3 同余 5
		// 2*x1 + 2*x2 + 2*x3 同余 6
		mat[1][1] = 1; mat[1][2] = 1; mat[1][3] = 1; mat[1][4] = 3;
		mat[2][1] = 0; mat[2][2] = 2; mat[2][3] = 3; mat[2][4] = 5;
		mat[3][1] = 2; mat[3][2] = 2; mat[3][3] = 2; mat[3][4] = 6;
		gauss(3);
		print(3);
	}

}