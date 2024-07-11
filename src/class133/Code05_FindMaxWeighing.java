package class133;

// 有一次错误称重求最重物品
// 测试链接 : https://www.luogu.com.cn/problem/P5027

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code05_FindMaxWeighing {

	public static int MAXN = 102;

	public static int[][] data = new int[MAXN][MAXN];

	public static double[][] mat = new double[MAXN][MAXN];

	public static int n;

	public static double sml = 1e-7;

	public static void swap(int[][] arr, int i, int j) {
		int[] tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}

	public static void swap(double[][] arr, int i, int j) {
		double[] tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}

	// 高斯消元处理加法方程组模版
	public static int gauss() {
		for (int row = 1; row <= n; row++) {
			int max = row;
			for (int i = row + 1; i <= n; i++) {
				if (Math.abs(mat[i][row]) > Math.abs(mat[max][row])) {
					max = i;
				}
			}
			swap(mat, row, max);
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
		for (int i = n; i >= 1; i--) {
			for (int j = i + 1; j <= n; j++) {
				mat[i][n + 1] -= mat[i][j] * mat[j][n + 1];
			}
		}
		return 1;
	}

	// 如果计算结果无效返回0
	// 如果计算结果有效返回最重三角形的编号
	public static int check() {
		if (gauss() == 0) {
			return 0;
		}
		double maxv = Double.MIN_VALUE;
		int maxt = 0;
		int ans = 0;
		for (int i = n; i >= 1; i--) {
			if (mat[i][n + 1] <= 0 || mat[i][n + 1] != (int) mat[i][n + 1]) {
				return 0;
			}
			if (maxv < mat[i][n + 1]) {
				maxv = mat[i][n + 1];
				maxt = 1;
				ans = i;
			} else if (maxv == mat[i][n + 1]) {
				maxt++;
			}
		}
		if (maxt > 1) {
			return 0;
		}
		return ans;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		n = (int) in.nval;
		for (int i = 1, m; i <= n + 1; i++) {
			in.nextToken();
			m = (int) in.nval;
			for (int j = 1, cur; j <= m; j++) {
				in.nextToken();
				cur = (int) in.nval;
				data[i][cur] = 1;
			}
			in.nextToken();
			data[i][n + 1] = (int) in.nval;
		}
		int ans = 0;
		int times = 0;
		for (int k = 1; k <= n + 1; k++) {
			swap(data, k, n + 1);
			for (int i = 1; i <= n; i++) {
				for (int j = 1; j <= n + 1; j++) {
					mat[i][j] = data[i][j];
				}
			}
			swap(data, k, n + 1);
			int cur = check();
			if (cur != 0) {
				times++;
				ans = cur;
			}
		}
		if (times == 0 || times > 1) {
			out.println("illegal");
		} else {
			out.println(ans);
		}
		out.flush();
		out.close();
		br.close();
	}

}
