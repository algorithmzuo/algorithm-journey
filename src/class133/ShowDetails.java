package class133;

// 课上讲述高斯消元解决加法方程组的例子

public class ShowDetails {

	public static int MAXN = 101;

	public static double[][] mat = new double[MAXN][MAXN];

	// 0.0000001 == 1e-7
	// 因为double类型有精度问题，所以认为
	// 如果一个数字绝对值  <  sml，则认为该数字是0
	// 如果一个数字绝对值  >= sml，则认为该数字不是0
	public static double sml = 1e-7;

	// 高斯消元解决加法方程组模版
	// 需要保证变量有n个，表达式也有n个
	public static void gauss(int n) {
		for (int i = 1; i <= n; i++) {
			// 如果想严格区分矛盾、多解、唯一解，一定要这么写
			int max = i;
			for (int j = 1; j <= n; j++) {
				if (j < i && Math.abs(mat[j][j]) >= sml) {
					continue;
				}
				if (Math.abs(mat[j][i]) > Math.abs(mat[max][i])) {
					max = j;
				}
			}
			swap(i, max);
			if (Math.abs(mat[i][i]) >= sml) {
				double tmp = mat[i][i];
				for (int j = i; j <= n + 1; j++) {
					mat[i][j] /= tmp;
				}
				for (int j = 1; j <= n; j++) {
					if (i != j) {
						double rate = mat[j][i] / mat[i][i];
						for (int k = i; k <= n + 1; k++) {
							mat[j][k] -= mat[i][k] * rate;
						}
					}
				}
			}
		}
	}

	public static void swap(int a, int b) {
		double[] tmp = mat[a];
		mat[a] = mat[b];
		mat[b] = tmp;
	}

	public static void print(int n) {
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n + 1; j++) {
				System.out.printf("%.2f  ", mat[i][j]);
			}
			System.out.println();
		}
		System.out.println("========================");
	}

	public static void main(String[] args) {
		// 唯一解
		// 1*x1 + 2*x2 - 1*x3 = 9
		// 2*x1 - 1*x2 + 2*x3 = 7
		// 1*x1 - 2*x2 + 2*x3 = 0
		System.out.println("唯一解");
		mat[1][1] = 1; mat[1][2] = 2; mat[1][3] = -1; mat[1][4] = 9;
		mat[2][1] = 2; mat[2][2] = -1; mat[2][3] = 2; mat[2][4] = 7;
		mat[3][1] = 1; mat[3][2] = -2; mat[3][3] = 2; mat[3][4] = 0;
		gauss(3);
		print(3);

		// 矛盾
		// 1*x1 + 1*x2 = 3
		// 2*x1 + 2*x2 = 7
		System.out.println("矛盾");
		mat[1][1] = 1; mat[1][2] = 1; mat[1][3] = 3;
		mat[2][1] = 2; mat[2][2] = 2; mat[2][3] = 7;
		gauss(2);
		print(2);

		// 多解
		// 1*x1 + 1*x2 = 3
		// 2*x1 + 2*x2 = 6
		System.out.println("多解");
		mat[1][1] = 1; mat[1][2] = 1; mat[1][3] = 3;
		mat[2][1] = 2; mat[2][2] = 2; mat[2][3] = 6;
		gauss(2);
		print(2);

		// 表达式冗余，唯一解
		// 1*x1 + 2*x2 - 1*x3 + 0*x4 = 9
		// 2*x1 + 4*x2 - 2*x3 + 0*x4 = 18
		// 2*x1 - 1*x2 + 2*x3 + 0*x4 = 7
		// 1*x1 - 2*x2 + 2*x3 + 0*x4 = 0
		System.out.println("表达式冗余，唯一解");
		mat[1][1] = 1; mat[1][2] = 2; mat[1][3] = -1; mat[1][4] = 0; mat[1][5] = 9;
		mat[2][1] = 2; mat[2][2] = 4; mat[2][3] = -2; mat[2][4] = 0; mat[2][5] = 18;
		mat[3][1] = 2; mat[3][2] = -1; mat[3][3] = 2; mat[3][4] = 0; mat[3][5] = 7;
		mat[4][1] = 1; mat[4][2] = -2; mat[4][3] = 2; mat[4][4] = 0; mat[4][5] = 0;
		gauss(4);
		print(4);

		// 表达式冗余，矛盾
		// 1*x1 + 2*x2 - 1*x3 = 9
		// 2*x1 + 4*x2 - 2*x3 = 18
		// 2*x1 - 1*x2 + 2*x3 = 7
		// 4*x1 - 2*x2 + 4*x3 = 10
		System.out.println("表达式冗余，矛盾");
		mat[1][1] = 1; mat[1][2] = 2; mat[1][3] = -1; mat[1][4] = 0; mat[1][5] = 9;
		mat[2][1] = 2; mat[2][2] = 4; mat[2][3] = -2; mat[2][4] = 0; mat[2][5] = 18;
		mat[3][1] = 2; mat[3][2] = -1; mat[3][3] = 2; mat[3][4] = 0; mat[3][5] = 7;
		mat[4][1] = 4; mat[4][2] = -2; mat[4][3] = 4; mat[4][4] = 0; mat[4][5] = 10;
		gauss(4);
		print(4);

		// 表达式冗余，多解
		// 1*x1 + 2*x2 - 1*x3 = 9
		// 2*x1 + 4*x2 - 2*x3 = 18
		// 2*x1 - 1*x2 + 2*x3 = 7
		// 4*x1 - 2*x2 + 4*x3 = 14
		System.out.println("表达式冗余，多解");
		mat[1][1] = 1; mat[1][2] = 2; mat[1][3] = -1; mat[1][4] = 0; mat[1][5] = 9;
		mat[2][1] = 2; mat[2][2] = 4; mat[2][3] = -2; mat[2][4] = 0; mat[2][5] = 18;
		mat[3][1] = 2; mat[3][2] = -1; mat[3][3] = 2; mat[3][4] = 0; mat[3][5] = 7;
		mat[4][1] = 4; mat[4][2] = -2; mat[4][3] = 4; mat[4][4] = 0; mat[4][5] = 14;
		gauss(4);
		print(4);

		// 表达式不足，矛盾
		// 1*x1 + 2*x2 - 1*x3 = 5
		// 2*x1 + 4*x2 - 2*x3 = 7
		System.out.println("表达式不足，矛盾");
		mat[1][1] = 1; mat[1][2] = 2; mat[1][3] = -1; mat[1][4] = 5;
		mat[2][1] = 2; mat[2][2] = 4; mat[2][3] = -2; mat[2][4] = 7;
		mat[3][1] = 0; mat[3][2] = 0; mat[3][3] = 0;  mat[3][4] = 0;
		gauss(3);
		print(3);

		// 表达式不足，多解
		// 1*x1 + 2*x2 - 1*x3 = 5
		// 2*x1 + 2*x2 - 1*x3 = 8
		System.out.println("表达式不足，多解");
		mat[1][1] = 1; mat[1][2] = 2; mat[1][3] = -1; mat[1][4] = 5;
		mat[2][1] = 2; mat[2][2] = 2; mat[2][3] = -1; mat[2][4] = 8;
		mat[3][1] = 0; mat[3][2] = 0; mat[3][3] = 0;  mat[3][4] = 0;
		gauss(3);
		print(3);

		// 正确区分矛盾、多解、唯一解
		// 0*x1 + 2*x2 = 3
		// 0*x1 + 0*x2 = 0
		System.out.println("正确区分矛盾、多解、唯一解");
		mat[1][1] = 0; mat[1][2] = 2; mat[1][3] = 3;
		mat[2][1] = 0; mat[2][2] = 0; mat[2][3] = 0;
		gauss(2);
		print(2);

		// 有些主元可以确定值
		// a  + b + c = 5
		// 2a + b + c = 7
		System.out.println("有些主元可以确定值");
		mat[1][1] = 1; mat[1][2] = 1; mat[1][3] = 1; mat[1][4] = 5;
		mat[2][1] = 2; mat[2][2] = 1; mat[2][3] = 1; mat[2][4] = 7;
		mat[3][1] = 0; mat[3][2] = 0; mat[3][3] = 0; mat[3][4] = 0;
		gauss(3);
		print(3);

		// 有些主元还受到自由元的影响
		// a + b = 5
		System.out.println("有些主元还受到自由元的影响");
		mat[1][1] = 1; mat[1][2] = 1; mat[1][3] = 5;
		mat[2][1] = 0; mat[2][2] = 0; mat[2][3] = 0;
		gauss(2);
		print(2);
	}

}