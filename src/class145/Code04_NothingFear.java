package class145;

// 已经没有什么好害怕的了
// 给定两个长度为n的数组，a[i]表示第i个糖果的能量，b[i]表示第i个药片的能量
// 所有能量数值都不相同，每一个糖果要选一个药片进行配对
// 如果配对之后，糖果能量 > 药片能量，称为糖果大的配对
// 如果配对之后，糖果能量 < 药片能量，称为药片大的配对
// 希望做到，糖果大的配对数量 = 药片大的配对数量 + k
// 返回配对方法数，答案对 1000000009 取模
// 举例，a = [5, 35, 15, 45]，b = [40, 20, 10, 30]，k = 2，返回4，因为有4种配对方法
// (5-40，35-20，15-10，45-30)、(5-40，35-30，15-10，45-20)
// (5-20，35-30，15-10，45-40)、(5-30，35-20，15-10，45-40)
// 1 <= n <= 2000
// 0 <= k <= n
// 测试链接 : https://www.luogu.com.cn/problem/P4859
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code04_NothingFear {

	public static int MAXN = 2001;

	public static int MOD = 1000000009;

	public static int n, k;

	public static int[] a = new int[MAXN];

	public static int[] b = new int[MAXN];

	public static long[] fac = new long[MAXN];

	public static long[][] c = new long[MAXN][MAXN];

	public static long[] small = new long[MAXN];

	public static long[][] dp = new long[MAXN][MAXN];

	public static long[] g = new long[MAXN];

	public static void build() {
		fac[0] = 1;
		for (int i = 1; i <= n; i++) {
			fac[i] = fac[i - 1] * i % MOD;
		}
		for (int i = 0; i <= n; i++) {
			c[i][0] = 1;
			for (int j = 1; j <= i; j++) {
				c[i][j] = (c[i - 1][j] + c[i - 1][j - 1]) % MOD;
			}
		}
	}

	public static long compute() {
		build();
		Arrays.sort(a, 1, n + 1);
		Arrays.sort(b, 1, n + 1);
		for (int i = 1, cnt = 0; i <= n; i++) {
			while (cnt + 1 <= n && b[cnt + 1] < a[i]) {
				cnt++;
			}
			small[i] = cnt;
		}
		dp[0][0] = 1;
		for (int i = 1; i <= n; i++) {
			dp[i][0] = dp[i - 1][0];
			for (int j = 1; j <= i; j++) {
				dp[i][j] = (dp[i - 1][j] + dp[i - 1][j - 1] * (small[i] - j + 1) % MOD) % MOD;
			}
		}
		for (int i = 0; i <= n; i++) {
			g[i] = fac[n - i] * dp[n][i] % MOD;
		}
		long ans = 0;
		for (int i = k; i <= n; i++) {
			if (((i - k) & 1) == 0) {
				ans = (ans + c[i][k] * g[i] % MOD) % MOD;
			} else {
				// -1 和 (MOD-1) 同余
				ans = (ans + c[i][k] * g[i] % MOD * (MOD - 1) % MOD) % MOD;
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
		k = (int) in.nval;
		for (int i = 1; i <= n; i++) {
			in.nextToken();
			a[i] = (int) in.nval;
		}
		for (int i = 1; i <= n; i++) {
			in.nextToken();
			b[i] = (int) in.nval;
		}
		if (((n + k) & 1) == 0) {
			k = (n + k) / 2;
			out.println(compute());
		} else {
			out.println(0);
		}
		out.flush();
		out.close();
		br.close();
	}

}
