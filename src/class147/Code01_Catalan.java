package class147;

// 卡特兰数模版
// 进栈顺序规定为1、2、3..n，返回有多少种不同的出栈顺序
// 测试题目的数据量很小，得到的卡特兰数没有多大，不需要取模处理
// 但是请假设，当n比较大时，卡特兰数是很大的，答案对 1000000007 取模
// 测试链接 : https://www.luogu.com.cn/problem/P1044
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code01_Catalan {

	public static int MOD = 1000000007;

	public static int MAXN = 1000001;

	// 阶乘余数表
	public static long[] fac = new long[MAXN];

	// 阶乘逆元表
	public static long[] inv1 = new long[MAXN];

	// 连续数逆元表
	public static long[] inv2 = new long[MAXN];

	// 来自讲解099，题目3，生成阶乘余数表、阶乘逆元表
	public static void build1(int n) {
		fac[0] = inv1[0] = 1;
		fac[1] = 1;
		for (int i = 2; i <= n; i++) {
			fac[i] = ((long) i * fac[i - 1]) % MOD;
		}
		inv1[n] = power(fac[n], MOD - 2);
		for (int i = n - 1; i >= 1; i--) {
			inv1[i] = ((long) (i + 1) * inv1[i + 1]) % MOD;
		}
	}

	// 来自讲解099，题目2，生成连续数逆元表
	public static void build2(int n) {
		inv2[1] = 1;
		for (int i = 2; i <= n + 1; i++) {
			inv2[i] = MOD - inv2[MOD % i] * (MOD / i) % MOD;
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
		return (((fac[n] * inv1[k]) % MOD) * inv1[n - k]) % MOD;
	}

	// 公式1
	public static long compute1(int n) {
		build1(2 * n);
		return (c(2 * n, n) - c(2 * n, n - 1) + MOD) % MOD;
	}

	// 公式2
	public static long compute2(int n) {
		build1(2 * n);
		return c(2 * n, n) * power(n + 1, MOD - 2) % MOD;
	}

	// 公式3
	public static long compute3(int n) {
		build2(n);
		long[] f = new long[n + 1];
		f[0] = f[1] = 1;
		for (int i = 2; i <= n; i++) {
			f[i] = f[i - 1] * (4 * i - 2) % MOD * inv2[i + 1] % MOD;
		}
		return f[n];
	}

	// 公式4
	public static long compute4(int n) {
		long[] f = new long[n + 1];
		f[0] = f[1] = 1;
		for (int i = 2; i <= n; i++) {
			for (int l = 0, r = i - 1; l < i; l++, r--) {
				f[i] = (f[i] + f[l] * f[r] % MOD) % MOD;
			}
		}
		return f[n];
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		int n = (int) in.nval;
		out.println(compute1(n));
//		out.println(compute2(n));
//		out.println(compute3(n));
//		out.println(compute4(n));
		out.flush();
		out.close();
		br.close();
	}

}
