package class143;

// 背包(两次转圈法)
// 测试链接 : https://www.luogu.com.cn/problem/P9140

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code05_Knapsack {

	public static int MAXN = 100001;

	public static long inf = Long.MIN_VALUE;

	public static int[] v = new int[MAXN];

	public static int[] c = new int[MAXN];

	public static long[] dp = new long[MAXN];

	public static int n, x, y;

	public static int gcd(int a, int b) {
		return b == 0 ? a : gcd(b, a % b);
	}

	public static void compute() {
		Arrays.fill(dp, 0, x, inf);
		dp[0] = 0;
		for (int i = 1; i <= n; i++) {
			for (int j = 0, d = gcd(v[i], x); j < d; j++) {
				for (int cur = j, next, times = 0; times < 2; times += cur == j ? 1 : 0) {
					next = (cur + v[i]) % x;
					if (dp[cur] != inf) {
						dp[next] = Math.max(dp[next], dp[cur] - (long) ((cur + v[i]) / x) * y + c[i]);
					}
					cur = next;
				}
			}
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		n = (int) in.nval;
		in.nextToken();
		int query = (int) in.nval;
		double best = 0, ratio;
		for (int i = 1; i <= n; i++) {
			in.nextToken();
			v[i] = (int) in.nval;
			in.nextToken();
			c[i] = (int) in.nval;
			ratio = (double) c[i] / v[i];
			if (ratio > best) {
				best = ratio;
				x = v[i];
				y = c[i];
			}
		}
		compute();
		long jobv;
		for (int i = 1, v; i <= query; i++) {
			in.nextToken();
			jobv = (long) in.nval;
			v = (int) (jobv % x);
			if (dp[v] == inf) {
				out.println("-1");
			} else {
				out.println(dp[v] + jobv / x * y);
			}
		}
		out.flush();
		out.close();
		br.close();
	}

}
