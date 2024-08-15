package class140;

// 测试链接 : https://www.luogu.com.cn/problem/P1516

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code03 {

	// 扩展欧几里得算法
	public static long d, x, y;

	public static void exgcd(long a, long b) {
		if (b == 0) {
			d = a;
			x = 1;
			y = 0;
		} else {
			exgcd(b, a % b);
			long tmp = x;
			x = y;
			y = tmp - a / b * y;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		long x1 = (long) in.nval;
		in.nextToken();
		long x2 = (long) in.nval;
		in.nextToken();
		long m = (long) in.nval;
		in.nextToken();
		long n = (long) in.nval;
		in.nextToken();
		long l = (long) in.nval;
		long a = n - m;
		long c = x1 - x2;
		if (a < 0) {
			a = -a;
			c = -c + l;
		}
		exgcd(a, l);
		if (c % d == 0) {
			out.println(((x * c / d) % (l / d) + (l / d)) % (l / d));
		} else {
			out.println("Impossible");
		}
		out.flush();
		out.close();
		br.close();
	}

}
