package class144;

// 计算系数
// 多项式为，(ax + by)的k次方，其中a、b、k为常数
// 计算这个多项式展开后，x的n次方 * y的m次方，这一项的系数
// 0 <= k <= 1000
// 0 <= a、b <= 10^6
// n + m == k
// 测试链接 : https://www.luogu.com.cn/problem/P1313
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code02_CalculateCoefficients {

	public static int MAXK = 1001;

	public static int MOD = 10007;

	// fac[i]代表 i! 在 %MOD 意义下的余数
	public static long[] fac = new long[MAXK + 1];

	// inv[i]代表 i! 在 %MOD 意义下的逆元
	public static long[] inv = new long[MAXK + 1];

	public static int a, b, k, n, m;

	// 来自讲解098，乘法快速幂，x的p次方 % MOD
	public static long power(long x, int p) {
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

	// 来自讲解099，生成阶乘表和逆元表
	public static void build() {
		// 阶乘表线性递推
		fac[1] = 1;
		for (int i = 2; i <= MAXK; i++) {
			fac[i] = ((long) i * fac[i - 1]) % MOD;
		}
		// 逆元表线性递推
		inv[MAXK] = power(fac[MAXK], MOD - 2);
		for (int i = MAXK - 1; i >= 0; i--) {
			inv[i] = ((long) (i + 1) * inv[i + 1]) % MOD;
		}
	}

	// 组合公式
	public static long c(int n, int k) {
		return (((fac[n] * inv[k]) % MOD) * inv[n - k]) % MOD;
	}

	public static long compute() {
		build();
		return (((power(a, n) * power(b, m)) % MOD) * c(k, k - n)) % MOD;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken(); a = (int) in.nval;
		in.nextToken(); b = (int) in.nval;
		in.nextToken(); k = (int) in.nval;
		in.nextToken(); n = (int) in.nval;
		in.nextToken(); m = (int) in.nval;
		out.println(compute());
		out.flush();
		out.close();
		br.close();
	}

}
