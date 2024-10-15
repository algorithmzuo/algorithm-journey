package class145;

// 集合计数
// 一共有n个不同的数，能构成2^n个不同集合
// 在2^n个集合中挑出若干个集合，至少挑一个
// 希望这若干个集合的交集，正好有k个数
// 返回挑选集合的方案数，答案对 1000000007 取模
// 1 <= n <= 10^6
// 0 <= k <= n
// 测试链接 : https://www.luogu.com.cn/problem/P10596
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code02_SetCounting {

	public static int MAXN = 1000001;

	public static int MOD = 1000000007;

	public static long[] fac = new long[MAXN];

	public static long[] inv = new long[MAXN];

	public static long[] g = new long[MAXN];

	public static int n, k;

	public static void build() {
		fac[0] = inv[0] = 1;
		fac[1] = 1;
		for (int i = 2; i <= n; i++) {
			fac[i] = ((long) i * fac[i - 1]) % MOD;
		}
		inv[n] = power(fac[n], MOD - 2);
		for (int i = n - 1; i >= 1; i--) {
			inv[i] = ((long) (i + 1) * inv[i + 1]) % MOD;
		}
	}

	public static long power(long x, long p) {
		long ans = 1;
		while (p > 0) {
			if ((p & 1) == 1) {
				ans = (ans * x) % MOD;
			}
			x = (x * x) % MOD;
			p >>= 1;
		}
		return ans;
	}

	public static long c(int n, int k) {
		return (((fac[n] * inv[k]) % MOD) * inv[n - k]) % MOD;
	}

	public static long compute() {
		build();
		long tmp = 2;
		for (int i = n; i >= 0; i--) {
			g[i] = tmp;
			tmp = tmp * tmp % MOD;
		}
		for (int i = 0; i <= n; i++) {
			// -1 和 (MOD-1) 同余
			g[i] = (g[i] + MOD - 1) * c(n, i) % MOD;
		}
		long ans = 0;
		for (int i = k; i <= n; i++) {
			if (((i - k) & 1) == 0) {
				ans = (ans + c(i, k) * g[i] % MOD) % MOD;
			} else {
				// -1 和 (MOD-1) 同余
				ans = (ans + c(i, k) * g[i] % MOD * (MOD - 1) % MOD) % MOD;
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
		out.println(compute());
		out.flush();
		out.close();
		br.close();
	}

}
