package class138;

// 牛的才艺展示
// 测试链接 : https://www.luogu.com.cn/problem/P4377
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code02_TalentShow {

	public static int MAXN = 251;

	public static int MAXM = 1001;

	public static double NA = Double.NEGATIVE_INFINITY;

	public static double sml = 1e-6;

	public static double[][] arr = new double[MAXN][3];

	public static double[] dp = new double[MAXM];

	public static int n, m;

	public static boolean check(double x) {
		for (int i = 1; i <= n; i++) {
			arr[i][2] = arr[i][1] - x * arr[i][0];
		}
		dp[0] = 0;
		Arrays.fill(dp, 1, m + 1, NA);
		// 进行了空间压缩，注意重量是至少！不是常规的01背包
		for (int i = 1; i <= n; i++) {
			for (int j = m, w; j >= 0; j--) {
				if (dp[j] != NA) {
					w = (int) (j + arr[i][0]);
					if (w >= m) {
						dp[m] = Math.max(dp[m], dp[j] + arr[i][2]);
					} else {
						dp[w] = Math.max(dp[w], dp[j] + arr[i][2]);
					}
				}
			}
		}
		return dp[m] >= 0;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		n = (int) in.nval;
		in.nextToken();
		m = (int) in.nval;
		for (int i = 1; i <= n; i++) {
			in.nextToken();
			arr[i][0] = in.nval;
			in.nextToken();
			arr[i][1] = in.nval;
		}
		double l = 0, r = 0, x;
		for (int i = 1; i <= n; i++) {
			r += arr[i][1];
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
		out.println((int) (ans * 1000));
		out.flush();
		out.close();
		br.close();
	}

}
