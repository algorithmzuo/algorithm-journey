package class138;

// 01分数规划模版题
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

public class Code01_DroppingTests {

	public static int MAXN = 1001;

	public static double sml = 1e-5;

	public static double[][] arr = new double[MAXN][3];

	public static int n, k;

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
			double l = 0, r = 0, m;
			for (int i = 1; i <= n; i++) {
				r += arr[i][0];
			}
			double ans = 0;
			while (l < r && r - l >= sml) {
				m = (l + r) / 2;
				if (check(m)) {
					ans = m;
					l = m + sml;
				} else {
					r = m - sml;
				}
			}
			out.println((int) (100 * (ans + 0.005)));
			in.nextToken();
			n = (int) in.nval;
			in.nextToken();
			k = (int) in.nval;
		}
		out.flush();
		out.close();
		br.close();
	}

	public static boolean check(double x) {
		for (int i = 1; i <= n; i++) {
			arr[i][2] = arr[i][0] - x * arr[i][1];
		}
		Arrays.sort(arr, 1, n + 1, new MyComparator());
		double f = 0;
		for (int i = 1; i <= k; i++) {
			f += arr[i][2];
		}
		return f >= 0;
	}

	// poj平台java版本较老，不支持Lambda表达式方式的比较器，需要自己定义
	public static class MyComparator implements Comparator<double[]> {

		@Override
		public int compare(double[] o1, double[] o2) {
			return o1[2] >= o2[2] ? -1 : 1;
		}

	}

}
