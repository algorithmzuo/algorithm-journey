package class140;

// 测试链接 : https://www.luogu.com.cn/problem/P5656

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code01_1 {

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
		int cases = (int) in.nval;
		for (int i = 1; i <= cases; i++) {
			in.nextToken();
			long a = (long) in.nval;
			in.nextToken();
			long b = (long) in.nval;
			in.nextToken();
			long c = (long) in.nval;
			exgcd(a, b);
			if (c % d == 0) {
				x *= c / d;
				y *= c / d;
				long xd = b / d;
				long yd = a / d;
				if (x < 0) {
					// x要想增长到>=1且最小的值，差几个xd，算出来，应该是(1 - x) / xd，向上取整
					// a/b 向上取整可以写成，(a + b - 1) / b
					// 所以(1 - x) / xd，向上取整为，(1 - x + xd - 1) / xd
					// 化简为(xd - x) / xd
					long k = (xd - x) / xd;
					x += xd * k;
					y -= yd * k;
				} else {
					// x要想减少到>=1且最小的值，差几个xd，算出来，自然向下取整
					long k = (x - 1) / xd;
					x -= xd * k;
					y += yd * k;
				}
				if (y > 0) {
					// 有正整数解
					out.print(((y - 1) / yd + 1) + " ");
					out.print(x + " ");
					out.print(((y - 1) % yd + 1) + " ");
					out.print((x + (y - 1) / yd * xd) + " ");
					out.println(y);
				} else {
					// 无正整数解
					out.print(x + " ");
					// (1 - y) / yd，向上取整为
					// ((1 - y) + yd - 1) / yd，化简成，(yd - y) / yd
					out.println(y + yd * ((yd - y) / yd));
				}
			} else {
				// 无整数解
				out.println(-1);
			}
		}
		out.flush();
		out.close();
		br.close();
	}

}
