package class138;

// 01分数规划模版题
// 给定n个数据，每个数据有(a, b)两个值，都为整数，并且都是非负的
// 请舍弃掉k个数据，希望让剩下数据做到，所有a的和 / 所有b的和，这个比值尽量大
// 如果剩下数据所有b的和为0，认为无意义
// 最后，将该比值 * 100，小数部分四舍五入的整数结果返回
// 1 <= n <= 100
// 0 <= a、b <= 10^9
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

public class Code01_DroppingTests {

	public static int MAXN = 1001;

	public static double sml = 1e-6;

	// arr[i][0] = i号数据的a
	// arr[i][1] = i号数据的b
	// arr[i][2] = i号数据的结余，a - x * b
	public static double[][] arr = new double[MAXN][3];

	public static int n, k;

	public static boolean check(double x) {
		// x固定的情况下，得到所有数据的结余
		for (int i = 1; i <= n; i++) {
			arr[i][2] = arr[i][0] - x * arr[i][1];
		}
		// 结余从大到小排序
		Arrays.sort(arr, 1, n + 1, new MyComparator());
		// 最大k个结余的累加和
		double sum = 0;
		for (int i = 1; i <= k; i++) {
			sum += arr[i][2];
		}
		return sum >= 0;
	}

	// poj平台java版本较老，不支持Lambda表达式方式的比较器，需要自己定义
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
			// 题目要求舍弃掉k个，其实就是挑选n-k个
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
			double ans = 0;
			while (l < r && r - l >= sml) {
				x = (l + r) / 2;
				if (check(x)) {
					ans = x;
					l = x + sml;
				} else {
					r = x - sml;
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

}
