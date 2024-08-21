package class140;

// 青蛙的约会
// 有一个周长为l的环，从环的0位置开始，规定只能沿着顺时针方向不停转圈
// 青蛙A在环的x1位置，每秒跳m个单位，青蛙B在x2位置，每秒跳n个单位
// 只有在某时刻，青蛙A和青蛙B来到环的同一个位置，才算相遇
// 如果两只青蛙相遇不了，打印"Impossible"
// 如果可以相遇，打印两只青蛙至少多久才能相遇
// 1 <= l <= 3 * 10^9
// 1 <= x1、x2、m、n <= 2 * 10^9
// x1 != x2
// 测试链接 : https://www.luogu.com.cn/problem/P1516
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code02_FrogsMeeting {

	// 扩展欧几里得算法
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
		long a, c;
		if (x1 < x2) {
			a = m - n;
			c = x2 - x1;
		} else {
			a = n - m;
			c = x1 - x2;
		}
		if (a < 0) {
			a = -a;
			c = l - c;
		}
		exgcd(a, l);
		if (c % d != 0) {
			out.println("Impossible");
		} else {
			// 解出的特解
			long x0 = x * c / d;
			// 单次幅度
			long xd = l / d;
			// x0调整成>=1的最小正整数，处理办法和上一题一样
			if (x0 < 0) {
				x0 += (1 - x0 + xd - 1) / xd * xd;
			} else {
				x0 -= (x0 - 1) / xd * xd;
			}
			out.println(x0);
		}
		out.flush();
		out.close();
		br.close();
	}

}
