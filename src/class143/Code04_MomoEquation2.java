package class143;

// 墨墨的等式(两次转圈法)
// 一共有n种物品，第i种物品价值是v[i]，每种物品可以选择任意个，个数不能是负数
// 那么各种物品组合在一起可以形成很多价值累加和
// 请问在[l...r]范围上，其中有多少个数是能被组成的价值累加和
// 0 <= n <= 12
// 0 <= v[i] <= 5 * 10^5
// 1 <= l <= r <= 10^12
// 测试链接 : https://www.luogu.com.cn/problem/P2371

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code04_MomoEquation2 {

	public static int MAXN = 500001;

	public static long inf = Long.MAX_VALUE;

	public static int[] v = new int[MAXN];

	public static long[] dp = new long[MAXN];

	public static int n, x;

	public static long l, r;

	public static int gcd(int a, int b) {
		return b == 0 ? a : gcd(b, a % b);
	}

	public static long compute() {
		Arrays.sort(v, 1, n + 1);
		int size = 0;
		for (int i = 1; i <= n; i++) {
			if (v[i] != 0) {
				v[++size] = v[i];
			}
		}
		if (size == 0) {
			return 0;
		}
		x = v[1];
		Arrays.fill(dp, 0, x, inf);
		dp[0] = 0;
		for (int i = 2, d; i <= size; i++) {
			d = gcd(v[i], x);
			for (int j = 0; j < d; j++) {
				for (int cur = j, next, circle = 0; circle < 2; circle += cur == j ? 1 : 0) {
					next = (cur + v[i]) % x;
					if (dp[cur] != inf) {
						dp[next] = Math.min(dp[next], dp[cur] + v[i]);
					}
					cur = next;
				}
			}
		}
		long ans = 0;
		for (int i = 0; i < x; i++) {
			if (r >= dp[i]) {
				ans += Math.max(0, (r - dp[i]) / x + 1);
			}
			if (l >= dp[i]) {
				ans -= Math.max(0, (l - dp[i]) / x + 1);
			}
		}
		return ans;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		n = (int) in.nval;
		in.nextToken();
		l = (long) in.nval - 1;
		in.nextToken();
		r = (long) in.nval;
		for (int i = 1; i <= n; i++) {
			in.nextToken();
			v[i] = (int) in.nval;
		}
		out.println(compute());
		out.flush();
		out.close();
		br.close();
	}

}
