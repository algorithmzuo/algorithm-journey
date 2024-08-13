package class139;

// 测试链接 : https://www.luogu.com.cn/problem/P2054

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code05 {

	// 扩展欧几里得算法
	public static long x, y;

	public static void exgcd(long a, long b) {
		long n = 0, m = 1, pn = 1, pm = 0, tmp, q, r;
		while (b != 0) {
			q = a / b;
			r = a % b;
			a = b;
			b = r;
			tmp = n;
			n = pn - q * n;
			pn = tmp;
			tmp = m;
			m = pm - q * m;
			pm = tmp;
		}
		x = pn;
		y = pm;
	}

	// 原理来自，讲解033，位运算实现乘法
	// a * b的过程自己实现，每一个中间过程都 % mod
	// 这么写目的是防止溢出
	public static long multiply(long a, long b, long mod) {
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

	// 原理来自，讲解098，乘法快速幂
	// 计算a的b次方，最终 % mod 的结果
	public static long power(long a, long b, long mod) {
		long ans = 1;
		while (b > 0) {
			if ((b & 1) == 1) {
				ans = multiply(ans, a, mod);
			}
			a = multiply(a, a, mod);
			b >>= 1;
		}
		return ans;
	}

	public static long compute(long n, long m, long l) {
		long mod = n + 1;
		exgcd(power(2, m, mod), mod);
		x = (x % mod + mod) % mod;
		return multiply(x, l, mod);
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		long n = (long) in.nval;
		in.nextToken();
		long m = (long) in.nval;
		in.nextToken();
		long l = (long) in.nval;
		out.println(compute(n, m, l));
		out.flush();
		out.close();
		br.close();
	}

}
