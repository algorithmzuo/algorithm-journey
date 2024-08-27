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

	public static long a[] = new long[MAXN];

	public static long b[] = new long[MAXN];

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
			all = all * a[i];
		}
		long ai, ci, ans = 0;
		for (int i = 1; i <= n; i++) {
			ai = all / a[i];
			exgcd(ai, a[i]);
			// 扩展欧几里得得到的解可能是负数
			x = (x % all + all) % all;
			// ci = (ai * ai逆元) % all
			ci = multiply(ai, x, all);
			// ans = (ans + (ci * bi) % all ) % all
			ans = (ans + multiply(ci, b[i], all)) % all;
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
			a[i] = (long) in.nval;
			in.nextToken();
			b[i] = (long) in.nval;
		}
		out.println(crt(n));
		out.flush();
		out.close();
		br.close();
	}

}
