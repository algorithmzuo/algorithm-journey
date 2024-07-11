package class133;

// 高斯消元处理加法方程组模版
// 测试链接 : https://www.luogu.com.cn/problem/P3389

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code01_GaussAdd {

	public static int MAXN = 101;

	public static double[][] mat = new double[MAXN][MAXN];

	public static int n;

	public static double sml = 1e-7;

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
		if (gauss() == 0) {
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

	public static int gauss() {
		// 矩阵的右下半区全消成0
		for (int row = 1; row <= n; row++) {
			int max = row;
			for (int i = row + 1; i <= n; i++) {
				if (Math.abs(mat[i][row]) > Math.abs(mat[max][row])) {
					max = i;
				}
			}
			swap(row, max);
			if (Math.abs(mat[row][row]) < sml) {
				return 0;
			}
			for (int j = n + 1; j >= row; j--) {
				mat[row][j] /= mat[row][row];
			}
			for (int i = row + 1; i <= n; i++) {
				double rate = mat[i][row] / mat[row][row];
				for (int j = row; j <= n + 1; j++) {
					mat[i][j] -= mat[row][j] * rate;
				}
			}
		}
		// 除了对角线和最后一列，其他格子都消成0
		for (int i = n; i >= 1; i--) {
			for (int j = i + 1; j <= n; j++) {
				mat[i][n + 1] -= mat[i][j] * mat[j][n + 1];
			}
		}
		return 1;
	}

	public static void swap(int a, int b) {
		double[] tmp = mat[a];
		mat[a] = mat[b];
		mat[b] = tmp;
	}

}