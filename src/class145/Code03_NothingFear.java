package class145;

// 已经没有什么好害怕的了
// 测试链接 : https://www.luogu.com.cn/problem/P4859

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code03_NothingFear {

	public static int MAXN = 2001;

	public static int MOD = 1000000009;

	public static int n, k;

	public static int[] a = new int[MAXN];

	public static int[] b = new int[MAXN];

	public static long[] fac = new long[MAXN];

	public static long[] inv = new long[MAXN];

	public static long[] near = new long[MAXN];

	public static long[][] g = new long[MAXN][MAXN];

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

	public static long c(int all, int pick) {
		return (((fac[all] * inv[pick]) % MOD) * inv[all - pick]) % MOD;
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
				ans = (ans + (c(i, k) * ((fac[n - i] * g[n][i]) % MOD))) % MOD;
			} else {
				ans = (ans + ((((MOD - 1) * c(i, k)) % MOD) * ((fac[n - i] * g[n][i]) % MOD))) % MOD;
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
