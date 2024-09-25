package class145;

// 已经没有什么好害怕的了
// 给定两个长度为n的数组，a[i]表示i糖果的能量，b[i]表示i药片的能量
// 要求一个糖果要和一个药片配对，你可以决定配对的方式
// 如果配对之后，糖果能量 > 药片能量，这个配对，叫糖果大的配对
// 如果配对之后，糖果能量 < 药片能量，这个配对，叫药片大的配对
// 希望做到，糖果大的配对数 = 药片大的配对数 + k，返回配对方法数，答案对 1000000009 取余
// 举例，a = [5, 35, 15, 45]，b = [40, 20, 10, 30]，k = 2，有4种配对方法
// (5-40，35-20，15-10，45-30) (5-40，45-20，15-10，35-30)
// (45-40，5-20，15-10，35-30) (45-40，35-20，15-10，5-30)
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

	public static long[] near = new long[MAXN];

	public static long[][] g = new long[MAXN][MAXN];

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
		if ((n + k) % 2 != 0) {
			return 0;
		}
		k = (n + k) / 2;
		build();
		Arrays.sort(a, 1, n + 1);
		Arrays.sort(b, 1, n + 1);
		for (int i = 1, find = 0; i <= n; i++) {
			while (find + 1 <= n && b[find + 1] < a[i]) {
				find++;
			}
			near[i] = find;
		}
		g[0][0] = 1;
		for (int i = 1; i <= n; i++) {
			g[i][0] = g[i - 1][0];
			for (int j = 1; j <= i; j++) {
				g[i][j] = (g[i - 1][j] + g[i - 1][j - 1] * Math.max(0, near[i] - j + 1) % MOD) % MOD;
			}
		}
		long ans = 0;
		for (int i = k; i <= n; i++) {
			if ((i - k) % 2 == 0) {
				ans = (ans + (c[i][k] * ((fac[n - i] * g[n][i]) % MOD))) % MOD;
			} else {
				// -1 和 (MOD-1) 同余
				ans = (ans + ((((MOD - 1) * c[i][k]) % MOD) * ((fac[n - i] * g[n][i]) % MOD))) % MOD;
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
		out.println(compute());
		out.flush();
		out.close();
		br.close();
	}

}
