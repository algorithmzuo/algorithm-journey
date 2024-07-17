package class133;

// 高斯消元解决加法方程组模版(区分是否有唯一解)
// 一共有n个变量，给定n个加法方程，构成一个加法方程组
// 如果方程组存在矛盾或者无法确定唯一解，打印"No Solution"
// 如果方程组存在唯一解，打印每个变量的值，保留小数点后两位
// 1 <= n <= 100
// 测试链接 : https://www.luogu.com.cn/problem/P3389
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code01_GaussAdd {

	public static int MAXN = 102;

	public static double[][] mat = new double[MAXN][MAXN];

	public static int n;

	// 0.0000001 == 1e-7
	// 因为double类型有精度问题，所以认为
	// 如果一个数字绝对值 <  sml，则认为该数字是0
	// 如果一个数字绝对值 >= sml，则认为该数字不是0
	public static double sml = 1e-7;

	// 需要保证变量有n个，表达式也有n个
	public static int gauss(int n) {
		for (int i = 1; i <= n; i++) {
			// 本题一旦没有唯一解，直接打印"No Solution"，于是可以这么写
			int max = i;
			for (int j = i + 1; j <= n; j++) {
				if (Math.abs(mat[j][i]) > Math.abs(mat[max][i])) {
					max = j;
				}
			}
			swap(i, max);
			if (Math.abs(mat[i][i]) < sml) {
				return 0;
			}
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
		return 1;
	}

	public static void swap(int a, int b) {
		double[] tmp = mat[a];
		mat[a] = mat[b];
		mat[b] = tmp;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		n = (int) in.nval;
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n + 1; j++) {
				in.nextToken();
				mat[i][j] = (double) in.nval;
			}
		}
		int sign = gauss(n);
		if (sign == 0) {
			out.println("No Solution");
		} else {
			for (int i = 1; i <= n; i++) {
				out.printf("%.2f\n", mat[i][n + 1]);
			}
		}
		out.flush();
		out.close();
		br.close();
	}

}