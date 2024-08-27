package class141;

// 扩展中国剩余定理模版
// 测试链接 : https://www.luogu.com.cn/problem/P1495
// 测试链接 : https://www.luogu.com.cn/problem/P4777

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

	// 扩展中国剩余定理模版
	public static long excrt(int n) {
		long m1 = m[1], r1 = r[1], m2, r2, c, tmp;
		for (int i = 2; i <= n; i++) {
			m2 = m[i];
			r2 = r[i];
			exgcd(m1, m2);
			c = ((r2 - r1) % m2 + m2) % m2;
			if (c % d != 0) {
				return -1;
			}
			x = multiply(x, c / d, m2 / d);
			tmp = r1 + x * m1;
			m1 = m2 / d * m1;
			r1 = (tmp % m1 + m1) % m1;
		}
		return r1;
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
