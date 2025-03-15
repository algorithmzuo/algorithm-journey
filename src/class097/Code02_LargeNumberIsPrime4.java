package class097;

// Miller-Rabin测试，java版，不用BigInteger也能通过的实现
// 这个文件课上没有讲，课上讲的是，java中的long是64位
// 所以 long * long 需要128位才能不溢出，于是直接用BigInteger中自带的方法了
// 但是
// 如果a和b都是long类型，其实 a * b 的过程，用位运算去实现，中间结果都 % mod 即可
// 这样就不需要使用BigInteger
// 讲解033，位运算实现乘法，增加 每一步 % mod 的逻辑即可
// 重点看一下本文件中的 multiply 方法，就是位运算实现乘法的改写
// C++的同学也可以用这种方式来实现，也不需要定义128位的long类型
// 测试链接 : https://www.luogu.com.cn/problem/U148828
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code02_LargeNumberIsPrime4 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		int t = Integer.valueOf(br.readLine());
		for (int i = 0; i < t; i++) {
			long n = Long.valueOf(br.readLine());
			out.println(millerRabin(n) ? "Yes" : "No");
		}
		out.flush();
		out.close();
		br.close();
	}

	public static long[] p = { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37 };

	public static boolean millerRabin(long n) {
		if (n <= 2) {
			return n == 2;
		}
		if ((n & 1) == 0) {
			return false;
		}
		for (int i = 0; i < p.length && p[i] < n; i++) {
			if (witness(p[i], n)) {
				return false;
			}
		}
		return true;
	}

	public static boolean witness(long a, long n) {
		long u = n - 1;
		int t = 0;
		while ((u & 1) == 0) {
			t++;
			u >>= 1;
		}
		long x1 = power(a, u, n), x2;
		for (int i = 1; i <= t; i++) {
			x2 = power(x1, 2, n);
			if (x2 == 1 && x1 != 1 && x1 != n - 1) {
				return true;
			}
			x1 = x2;
		}
		if (x1 != 1) {
			return true;
		}
		return false;
	}

	// 返回，n的p次方 % mod
	// 乘的每一步都用multiply方法，不用语言自带的乘法
	public static long power(long n, long p, long mod) {
		long ans = 1;
		while (p > 0) {
			if ((p & 1) == 1) {
				ans = multiply(ans, n, mod);
			}
			n = multiply(n, n, mod);
			p >>= 1;
		}
		return ans;
	}

	// a * b的过程，用位运算实现，让每一个中间结果都 % mod
	// 这个原理来自，讲解033，位运算实现乘法
	// 这么写可以防止溢出，这也叫龟速乘
	public static long multiply(long a, long b, long mod) {
		a = (a % mod + mod) % mod;
		b = (b % mod + mod) % mod;
		long ans = 0;
		while (b != 0) {
			if ((b & 1) != 0) {
				ans = (ans + a) % mod;
			}
			a = (a + a) % mod;
			b >>= 1;
		}
		return ans;
	}

}