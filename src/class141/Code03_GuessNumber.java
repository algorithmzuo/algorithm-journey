package class141;

// 猜数字
// 给定两个长度为n数组，一组为r1，r2，r3...，另一组为m1，m2，m3...
// 其中第二组数字两两互质，求最小正数解x
// 要求x满足，mi | (x - ri)，即(x - ri)是mi的整数倍
// 1 <= n <= 10
// -10^9 <= ri <= +10^9
// 1 <= mi <= 6 * 10^3
// 所有mi的乘积 <= 10^18
// 测试链接 : https://www.luogu.com.cn/problem/P3868
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code03_GuessNumber {

	public static int MAXN = 11;

	public static long m[] = new long[MAXN];

	public static long r[] = new long[MAXN];

	// 中国剩余定理模版
	public static long crt(int n) {
		long lcm = 1;
		for (int i = 1; i <= n; i++) {
			lcm = lcm * m[i];
		}
		long ai, ci, ans = 0;
		for (int i = 1; i <= n; i++) {
			ai = lcm / m[i];
			exgcd(ai, m[i]);
			ci = multiply(r[i], multiply(ai, x, lcm), lcm);
			ans = (ans + ci) % lcm;
		}
		return ans;
	}

	// 扩展中国剩余定理模版
	public static long excrt(int n) {
		long tail = 0, lcm = 1, tmp, b, c, x0;
		for (int i = 1; i <= n; i++) {
			b = m[i];
			c = ((r[i] - tail) % b + b) % b;
			exgcd(lcm, b);
			if (c % d != 0) {
				return -1;
			}
			x0 = multiply(x, c / d, b / d);
			tmp = lcm * (b / d);
			tail = (tail + multiply(x0, lcm, tmp)) % tmp;
			lcm = tmp;
		}
		return tail;
	}

	// 讲解139 - 扩展欧几里得算法
	public static long d, x, y, px, py;

	public static void exgcd(long a, long b) {
		if (b == 0) {
			d = a;
			x = 1;
			y = 0;
		} else {
			exgcd(b, a % b);
			px = x;
			py = y;
			x = py;
			y = px - py * (a / b);
		}
	}

	// 讲解033 - 位运算实现乘法
	// a*b过程每一步都%mod，这么写是防止溢出，也叫龟速乘
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

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		int n = (int) in.nval;
		for (int i = 1; i <= n; i++) {
			in.nextToken();
			r[i] = (long) in.nval;
		}
		for (int i = 1; i <= n; i++) {
			in.nextToken();
			m[i] = (long) in.nval;
		}
		// 题目输入的余数可能为负所以转化成非负数
		for (int i = 1; i <= n; i++) {
			r[i] = (r[i] % m[i] + m[i]) % m[i];
		}
		// out.println(crt(n)); // 中国剩余定理解决
		out.println(excrt(n)); // 扩展中国剩余定理解决
		out.flush();
		out.close();
		br.close();
	}

}
