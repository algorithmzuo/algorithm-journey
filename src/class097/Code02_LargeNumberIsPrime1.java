package class097;

// 判断较大的数字是否为质数(Miller-Rabin测试)
// 测试链接 : https://www.luogu.com.cn/problem/U148828
// 如下代码无法通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Random;

// 本文件可以解决10^9范围内数字的质数检查
// 时间复杂度O(s * (logn)的三次方)，很快
// 为什么不能搞定所有long类型的数字检查
// 课上已经做了说明
public class Code02_LargeNumberIsPrime1 {

	// 测试次数
	public static int s = 30;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		int t = Integer.valueOf(br.readLine());
		for (int i = 0; i < t; i++) {
			long n = Long.valueOf(br.readLine());
			out.println(millerRabin(n, s) ? "Yes" : "No");
		}
		out.flush();
		out.close();
		br.close();
	}

	public static boolean millerRabin(long n, int s) {
		if (n <= 2) {
			return n == 2;
		}
		if ((n & 1) == 0) {
			return false;
		}
		for (int i = 0; i < s && i < n; i++) {
			Random rand = new Random();
			long a = Math.abs(rand.nextLong()) % (n - 1) + 1;
			if (witness(a, n)) {
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

	// 快速幂
	public static long power(long n, long p, long mod) {
		long ans = 1;
		while (p > 0) {
			if ((p & 1) == 1) {
				ans = (ans * n) % mod;
			}
			n = (n * n) % mod;
			p >>= 1;
		}
		return ans;
	}

}