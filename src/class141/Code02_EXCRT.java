package class141;

// 扩展中国剩余定理模版
// 给出n个同余方程，求满足同余方程的最小正数解x
// 一共n个同余方程，x ≡ ri(% mi)
// 1 <= n <= 10^5
// 0 <= ri、mi <= 10^12
// 所有mi不一定互质
// 所有mi的最小公倍数 <= 10^18
// 测试链接 : https://www.luogu.com.cn/problem/P4777
// 测试链接 : https://www.luogu.com.cn/problem/P1495
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code02_EXCRT {

	public static int MAXN = 100001;

	public static long m[] = new long[MAXN];

	public static long r[] = new long[MAXN];

	// 扩展中国剩余定理模版
	public static long excrt(int n) {
		long tail = 0, lcm = 1, tmp, b, c, x0;
		// ans = lcm * x + tail
		for (int i = 1; i <= n; i++) {
			// ans = m[i] * y + ri
			// lcm * x + m[i] * y = ri - tail
			// a = lcm
			// b = m[i]
			// c = ri - tail
			b = m[i];
			c = ((r[i] - tail) % b + b) % b;
			exgcd(lcm, b);
			if (c % d != 0) {
				return -1;
			}
			// ax + by = gcd(a,b)，特解是，x变量
			// ax + by = c，特解是，x变量 * (c/d)
			// ax + by = c，最小非负特解x0 = (x * (c/d)) % (b/d) 取非负余数
			// 通解 = x0 + (b/d) * n
			x0 = multiply(x, c / d, b / d);
			// ans = lcm * x + tail，带入通解
			// ans = lcm * (x0 + (b/d) * n) + tail
			// ans = lcm * (b/d) * n + lcm * x0 + tail
			// tail' = tail' % lcm'
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
			m[i] = (long) in.nval;
			in.nextToken();
			r[i] = (long) in.nval;
		}
		out.println(excrt(n));
		out.flush();
		out.close();
		br.close();
	}

}
