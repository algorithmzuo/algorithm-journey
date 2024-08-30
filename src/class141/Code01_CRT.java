package class141;

// 中国剩余定理模版
// 给出n个同余方程，求满足同余方程的最小正数解x
// 一共n个同余方程，x ≡ ri(% mi)
// 1 <= n <= 10
// 0 <= ri、mi <= 10^5
// 所有mi一定互质
// 所有mi整体乘积 <= 10^18
// 测试链接 : https://www.luogu.com.cn/problem/P1495
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code01_CRT {

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
			// ai = lcm / m[i]
			ai = lcm / m[i];
			// ai逆元，在%m[i]意义下的逆元
			exgcd(ai, m[i]);
			// ci = (ri * ai * ai逆元) % lcm
			ci = multiply(r[i], multiply(ai, x, lcm), lcm);
			ans = (ans + ci) % lcm;
		}
		return ans;
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
			m[i] = (long) in.nval;
			in.nextToken();
			r[i] = (long) in.nval;
		}
		out.println(crt(n));
		out.flush();
		out.close();
		br.close();
	}

}
