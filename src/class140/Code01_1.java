package class140;

// 测试链接 : https://www.luogu.com.cn/problem/P5656
// 如下实现是正确的，但是洛谷平台对空间卡的很严，只有使用C++能全部通过
// java的版本就是无法完全通过的，空间会超过限制，主要是IO空间占用大
// 这是洛谷平台没有照顾各种语言的实现所导致的
// 在真正笔试、比赛时，一定是兼顾各种语言的，该实现是一定正确的
// C++版本就是Code01_2文件
// C++版本和java版本逻辑完全一样，但只有C++版本可以通过所有测试用例

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
		for (int t = 1; t <= cases; t++) {
			in.nextToken();
			long a = (long) in.nval;
			in.nextToken();
			long b = (long) in.nval;
			in.nextToken();
			long c = (long) in.nval;
			exgcd(a, b);
			if (c % d == 0) { // 有整数解
				x *= c / d;
				y *= c / d;
				long xd = b / d;
				long yd = a / d;
				if (x < 0) {
					// x要想增长到>=1且最小的值，差几个xd，算出来就是k的值
					// 那应该是(1-x)/xd，结果向上取整
					long k = (1 - x + xd - 1) / xd;
					x += xd * k;
					y -= yd * k;
				} else {
					// x要想减少到>=1且最小的值，差几个xd，算出来就是k的值，向下取整
					long k = (x - 1) / xd;
					x -= xd * k;
					y += yd * k;
				}
				if (y > 0) { // 有正整数解
					// y减少到>=1的最小值，能减几次，就是正整数解的个数
					out.print(((y - 1) / yd + 1) + " ");
					// x的最小正整数
					out.print(x + " ");
					// y的最小正整数
					out.print(((y - 1) % yd + 1) + " ");
					// x的最大正整数
					out.print((x + (y - 1) / yd * xd) + " ");
					// y的最大正整数
					out.println(y);
				} else { // 无正整数解
					out.print(x + " ");
					out.println(y + yd * ((1 - y + yd - 1) / yd));
				}
			} else { // 无整数解
				out.println(-1);
			}
		}
		out.flush();
		out.close();
		br.close();
	}

}
