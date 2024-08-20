package class139;

// 同余方程
// 求关于x的同余方程 ax ≡ 1(mod b) 的最小正整数解
// 题目保证一定有解，也就是a和b互质
// 2 <= a、b <= 2 * 10^9
// 测试链接 : https://www.luogu.com.cn/problem/P1082
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code04_CongruenceEquation {

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
		long a = (long) in.nval;
		in.nextToken();
		long b = (long) in.nval;
		exgcd(a, b);
		out.println((x % b + b) % b);
		out.flush();
		out.close();
		br.close();
	}

}
