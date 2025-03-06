package class138;

// 题目2，牛群的才艺展示，另一种二分的写法
// 思路是不变的，二分的写法多种多样
// 代码中打注释的位置，就是更简单的二分逻辑，其他代码没有变化
// 测试链接 : https://www.luogu.com.cn/problem/P4377
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Other2 {

	public static int MAXN = 251;

	public static int MAXW = 1001;

	public static double NA = -1e9;

	public static int[] weight = new int[MAXN];

	public static int[] talent = new int[MAXN];

	public static double[] value = new double[MAXN];

	public static double[] dp = new double[MAXW];

	public static int n, w;

	public static boolean check(double x) {
		for (int i = 1; i <= n; i++) {
			value[i] = (double) talent[i] - x * weight[i];
		}
		dp[0] = 0;
		Arrays.fill(dp, 1, w + 1, NA);
		for (int i = 1; i <= n; i++) {
			for (int p = w, j; p >= 0; p--) {
				j = (int) (p + weight[i]);
				if (j >= w) {
					dp[w] = Math.max(dp[w], dp[p] + value[i]);
				} else {
					dp[j] = Math.max(dp[j], dp[p] + value[i]);
				}
			}
		}
		return dp[w] >= 0;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		n = (int) in.nval;
		in.nextToken();
		w = (int) in.nval;
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
		out.println((int) (l * 1000));
		out.flush();
		out.close();
		br.close();
	}

}
