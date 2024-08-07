package class138;

// 牛群的才艺展示
// 一共有n只牛，每只牛有重量和才艺两个属性值
// 要求一定要选若干只牛，使得总重量不少于w
// 并且选出的牛，希望让，才艺的和 / 重量的和，这个比值尽量大
// 返回该比值 * 1000的整数结果，小数部分舍弃
// 1 <= n <= 250
// 1 <= w <= 1000
// 1 <= 牛的重量 <= 10^6
// 1 <= 牛的才艺 <= 10^3
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

	// 重量
	public static int[] weight = new int[MAXN];

	// 才艺
	public static int[] talent = new int[MAXN];

	// (才艺 - x * 重量)的结余
	public static double[] value = new double[MAXN];

	public static double[] dp = new double[MAXM];

	public static int n, m;

	public static boolean check(double x) {
		for (int i = 1; i <= n; i++) {
			value[i] = (double) talent[i] - x * weight[i];
		}
		dp[0] = 0;
		Arrays.fill(dp, 1, m + 1, NA);
		// 动态规划进行了空间压缩，并且注意本题重量的要求是至少！
		for (int i = 1; i <= n; i++) {
			for (int j = m, w; j >= 0; j--) {
				if (dp[j] != NA) {
					w = (int) (j + weight[i]);
					if (w >= m) {
						dp[m] = Math.max(dp[m], dp[j] + value[i]);
					} else {
						dp[w] = Math.max(dp[w], dp[j] + value[i]);
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
			weight[i] = (int) in.nval;
			in.nextToken();
			talent[i] = (int) in.nval;
		}
		double l = 0, r = 0, x;
		for (int i = 1; i <= n; i++) {
			r += talent[i];
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
