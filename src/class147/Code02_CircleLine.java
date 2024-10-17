package class147;

// 圆上连线
// 圆上有2n个点，这些点成对连接起来，形成n条线段，任意两条线段不能相交，返回连接的方法数
// 注意！答案不对 10^9 + 7 取模！而是对 10^8 + 7 取模！
// 1 <= n <= 2999
// 测试链接 : https://www.luogu.com.cn/problem/P1976
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code02_CircleLine {

	public static int MOD = 100000007;

	public static int MAXN = 1000001;

	public static long[] fac = new long[MAXN];

	public static long[] inv = new long[MAXN];

	public static void build(int n) {
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

	// 这里用公式1
	public static long compute(int n) {
		build(2 * n);
		return (c(2 * n, n) - c(2 * n, n - 1) + MOD) % MOD;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		int n = (int) in.nval;
		out.println(compute(n));
		out.flush();
		out.close();
		br.close();
	}

}
