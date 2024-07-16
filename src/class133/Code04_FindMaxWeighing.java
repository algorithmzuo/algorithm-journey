package class133;

// 有一次错误称重求最重物品
// 一共有n个物品，编号1~n，定义合法方案如下：
// 1，每个物品的重量都是确定的
// 2，每个物品的重量一定都是正整数
// 3，最重的物品有且仅有1个
// 每次称重格式类似：3 2 5 6 10，代表本次称重涉3个物品，编号为2、5、6，总重量10
// 一共有n+1条称重数据，称重数据整体有效的条件为：
// 错误的称重数据有且仅有1条，只有排除这条错误称重，才能求出一种合法方案
// 如果称重数据有效，打印最重三角形的编号
// 如果称重数据无效，打印"illegal"
// 1 <= m <= n <= 100
// 测试链接 : https://www.luogu.com.cn/problem/P5027
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code04_FindMaxWeighing {

	public static int MAXN = 102;

	public static int[][] data = new int[MAXN][MAXN];

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

	// 如果计算结果不是合法方案返回0
	// 如果计算结果是合法方案返回最重三角形的编号
	public static int check() {
		gauss(n);
		double maxv = Double.MIN_VALUE;
		int maxt = 0;
		int ans = 0;
		for (int i = 1; i <= n; i++) {
			if (mat[i][i] == 0) {
				return 0;
			}
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
			swapData(k, n + 1);
			for (int i = 1; i <= n; i++) {
				for (int j = 1; j <= n + 1; j++) {
					mat[i][j] = data[i][j];
				}
			}
			swapData(k, n + 1);
			int cur = check();
			if (cur != 0) {
				times++;
				ans = cur;
			}
		}
		if (times != 1) {
			out.println("illegal");
		} else {
			out.println(ans);
		}
		out.flush();
		out.close();
		br.close();
	}

	public static void swapData(int i, int j) {
		int[] tmp = data[i];
		data[i] = data[j];
		data[j] = tmp;
	}

}
