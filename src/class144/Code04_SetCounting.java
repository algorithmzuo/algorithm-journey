package class144;

// 集合计数
// 测试链接 : https://www.luogu.com.cn/problem/P10596

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code04_SetCounting {

	public static int MAXN = 1000001;

	public static int MOD = 1000000007;

	public static long[] fac = new long[MAXN];

	public static long[] inv = new long[MAXN];

	public static long[] f = new long[MAXN];

	public static int n, k;

	public static void build() {
		// 一般情况下，不用计算0!的余数表和逆元表
		// 但是这道题需要，所以单独设置一下
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

	public static long c(int all, int pick) {
		return (((fac[all] * inv[pick]) % MOD) * inv[all - pick]) % MOD;
	}

	public static long compute() {
		build();
		long tmp = 2;
		for (int i = n; i >= k; i--) {
			f[i] = (tmp + MOD - 1) % MOD;
			tmp = (tmp * tmp) % MOD;
		}
		for (int i = n; i >= k; i--) {
			f[i] = (c(n, i) * f[i]) % MOD;
		}
		long g = 0;
		for (int i = k; i <= n; i++) {
			if (((i - k) & 1) == 0) {
				g = (g + ((c(i, k) * f[i]) % MOD)) % MOD;
			} else {
				g = (g + (((((long) (MOD - 1) * c(i, k)) % MOD) * f[i]) % MOD)) % MOD;
			}
		}
		return g;
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
