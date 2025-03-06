package class138;

// 题目1，01分数规划模版题，另一种二分的写法
// 思路是不变的，二分的写法多种多样
// 代码中打注释的位置，就是更简单的二分逻辑，其他代码没有变化
// 测试链接 : https://www.luogu.com.cn/problem/P10505
// 测试链接 : http://poj.org/problem?id=2976
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;
import java.util.Comparator;

public class Other1 {

	public static int MAXN = 1001;

	public static double[][] arr = new double[MAXN][3];

	public static int n, k;

	public static boolean check(double x) {
		for (int i = 1; i <= n; i++) {
			arr[i][2] = arr[i][0] - x * arr[i][1];
		}
		Arrays.sort(arr, 1, n + 1, new MyComparator());
		double sum = 0;
		for (int i = 1; i <= k; i++) {
			sum += arr[i][2];
		}
		return sum >= 0;
	}

	public static class MyComparator implements Comparator<double[]> {

		@Override
		public int compare(double[] o1, double[] o2) {
			return o1[2] >= o2[2] ? -1 : 1;
		}

	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		n = (int) in.nval;
		in.nextToken();
		k = (int) in.nval;
		while (n != 0 || k != 0) {
			k = n - k;
			for (int i = 1; i <= n; i++) {
				in.nextToken();
				arr[i][0] = in.nval;
			}
			for (int i = 1; i <= n; i++) {
				in.nextToken();
				arr[i][1] = in.nval;
			}
			double l = 0, r = 0, x;
			for (int i = 1; i <= n; i++) {
				r += arr[i][0];
			}
			// 二分进行60次，足够达到题目要求的精度
			// 二分完成后，l就是答案
			for (int i = 1; i <= 60; i++) {
				x = (l + r) / 2;
				if (check(x)) {
					l = x;
				} else {
					r = x;
				}
			}
			out.println((int) (100 * (l + 0.005)));
			in.nextToken();
			n = (int) in.nval;
			in.nextToken();
			k = (int) in.nval;
		}
		out.flush();
		out.close();
		br.close();
	}

}
