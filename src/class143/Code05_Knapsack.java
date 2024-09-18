package class143;

// 背包(两次转圈法)
// 一共有n种物品，第i种物品的体积为v[i]，价值为c[i]，每种物品可以选择任意个，个数不能是负数
// 一共有m条查询，每次查询都会给定jobv，代表体积的要求
// 要求挑选物品的体积和一定要严格是jobv，返回能得到的最大价值和
// 如果没有方案能正好凑满jobv，返回-1
// 1 <= n <= 50
// 1 <= m <= 10^5
// 1 <= v[i] <= 10^5
// 1 <= c[i] <= 10^6
// 10^11 <= jobv <= 10^12
// 测试链接 : https://www.luogu.com.cn/problem/P9140
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

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

	// dp[i] : 总体积为某数，先尽可能用基准物品填入，剩余的体积为i
	// 可以去掉若干基准物品，加入若干其他物品，最终凑齐总体积
	// 能获得的最大补偿是多少
	public static long[] dp = new long[MAXN];

	public static int n, m, x, y;

	public static int gcd(int a, int b) {
		return b == 0 ? a : gcd(b, a % b);
	}

	public static void compute() {
		Arrays.fill(dp, 0, x, inf);
		dp[0] = 0;
		for (int i = 1; i <= n; i++) {
			if (v[i] != x) {
				for (int j = 0, d = gcd(v[i], x); j < d; j++) {
					for (int cur = j, next, circle = 0; circle < 2; circle += cur == j ? 1 : 0) {
						next = (cur + v[i]) % x;
						if (dp[cur] != inf) {
							dp[next] = Math.max(dp[next], dp[cur] - (long) ((cur + v[i]) / x) * y + c[i]);
						}
						cur = next;
					}
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
		m = (int) in.nval;
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
		for (int i = 1, v; i <= m; i++) {
			in.nextToken();
			jobv = (long) in.nval;
			v = (int) (jobv % x);
			if (dp[v] == inf) {
				out.println("-1");
			} else {
				out.println(jobv / x * y + dp[v]);
			}
		}
		out.flush();
		out.close();
		br.close();
	}

}
