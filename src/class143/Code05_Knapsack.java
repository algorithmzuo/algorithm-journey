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

	public static int[] value = new int[MAXN];

	public static int[] cost = new int[MAXN];

	public static long[] dp = new long[MAXN];

	public static int n, x, y;

	public static int gcd(int a, int b) {
		return b == 0 ? a : gcd(b, a % b);
	}

	public static void compute() {
		Arrays.fill(dp, 0, x, inf);
		dp[0] = 0;
		for (int i = 1; i <= n; i++) {
			for (int j = 0, d = gcd(cost[i], x); j < d; j++) {
				for (int cur = j, next, times = 0; times < 2; times += cur == j ? 1 : 0) {
					next = (cur + cost[i]) % x;
					if (dp[cur] != inf) {
						dp[next] = Math.max(dp[next], dp[cur] - (long) ((cur + cost[i]) / x) * y + value[i]);
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
		x = 1;
		y = 0;
		for (int i = 1; i <= n; i++) {
			in.nextToken();
			cost[i] = (int) in.nval;
			in.nextToken();
			value[i] = (int) in.nval;
			if ((long) y * cost[i] < (long) x * value[i]) {
				x = cost[i];
				y = value[i];
			}
		}
		compute();
		long jobv;
		for (int i = 1, cur; i <= query; i++) {
			in.nextToken();
			jobv = (long) in.nval;
			cur = (int) (jobv % x);
			if (dp[cur] == inf) {
				out.println("-1");
			} else {
				out.println(dp[cur] + jobv / x * y);
			}
		}
		out.flush();
		out.close();
		br.close();
	}

}
