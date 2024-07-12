package class133;

// 高斯消元处理加法方程组模版(区分是否有唯一解)
// 测试链接 : https://www.luogu.com.cn/problem/P3389

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code01_GaussAdd1 {

	public static int MAXN = 101;

	public static double[][] mat = new double[MAXN][MAXN];

	public static int n;

	public static double sml = 1e-7;

	public static int gauss() {
		for (int i = 1; i <= n; i++) {
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
		int sign = gauss();
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