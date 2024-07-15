package class133;

// 球形空间的中心点
// 如果在n维空间中，那么表达一个点的位置，需要n个坐标的值
// 现在给定n+1个点，每个点都有n个坐标的值，代表在n维空间中的位置
// 假设这n+1个点都在n维空间的球面上，请返回球心的位置
// 球心的位置当然也是n个坐标的值，打印出来
// 在n维空间中，计算任意两点的距离，请用经典的欧式距离
// 1 <= n <= 10
// 坐标信息精确到小数点后6位，绝对值都不超过20000
// 测试链接 : https://www.luogu.com.cn/problem/P4035
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code03_SphereCenter {

	public static int MAXN = 12;

	public static double[][] data = new double[MAXN][MAXN];

	public static double[][] mat = new double[MAXN][MAXN];

	public static int n;

	public static double sml = 1e-7;

	// 高斯消元解决加法方程组模版
	public static void gauss(int n) {
		for (int i = 1; i <= n; i++) {
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

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		n = (int) in.nval;
		for (int i = 1; i <= n + 1; i++) {
			for (int j = 1; j <= n; j++) {
				in.nextToken();
				data[i][j] = (double) in.nval;
			}
		}
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {
				mat[i][j] = 2 * (data[i][j] - data[i + 1][j]);
				mat[i][n + 1] += data[i][j] * data[i][j] - data[i + 1][j] * data[i + 1][j];
			}
		}
		gauss(n);
		for (int i = 1; i <= n; i++) {
			out.printf("%.3f ", mat[i][n + 1]);
		}
		out.println();
		out.flush();
		out.close();
		br.close();
	}

}
