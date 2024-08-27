package class141;

// 中国剩余定理模版
// 测试链接 : https://www.luogu.com.cn/problem/P1495

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code01_CRT {

	public static int MAXN = 11;

	public static long modular[] = new long[MAXN];

	public static long remainder[] = new long[MAXN];

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

	// 中国剩余定理模版
	public static long crt(int n) {
		long all = 1;
		for (int i = 1; i <= n; i++) {
			all = all * modular[i];
		}
		long ai, ci, ans = 0;
		for (int i = 1; i <= n; i++) {
			// 所有模数的乘积 / 当前的模数，得到ai
			ai = all / modular[i];
			// 扩展欧几里得算法得到的x是%m[i]意义下的逆元，可能是负数
			exgcd(ai, modular[i]);
			// 既然得到的解可能是负数，那么变成正的，就是ai的逆元
			x = (x % all + all) % all;
			// ci = (ri * ai * ai逆元) % all
			ci = multiply(remainder[i], multiply(ai, x, all), all);
			// ans = (ans + ci ) % all
			ans = (ans + ci) % all;
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
			modular[i] = (long) in.nval;
			in.nextToken();
			remainder[i] = (long) in.nval;
		}
		out.println(crt(n));
		out.flush();
		out.close();
		br.close();
	}

}
