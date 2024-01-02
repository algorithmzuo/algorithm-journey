package class095;

// 斐波那契尼姆博弈(齐肯多夫定理)
// 一共有n枚石子，两位玩家定了如下规则进行游戏：
// 先手后手轮流取石子，先手在第一轮可以取走任意的石子
// 接下来的每一轮当前的玩家最少要取走一个石子，最多取走上一次取的数量的2倍
// 当然，玩家取走的数量必须不大于目前场上剩余的石子数量，双方都以最优策略取石子
// 你也看出来了，根据规律先手一定会获胜，但是先手想知道
// 第一轮自己取走至少几颗石子就可以保证获胜了
// 测试链接 : https://www.luogu.com.cn/problem/P6487
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下的code，提交时请把类名改成"Main"，可以直接通过

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code06_FibonacciNim {

	public static long n, a, b, c, ans;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		n = (long) in.nval;
		ans = -1;
		while (n != 1 && n != 2) {
			a = 1;
			b = 2;
			c = 3;
			while (c < n) {
				a = b;
				b = c;
				c = a + b;
			}
			if (n == c) {
				ans = c;
				break;
			} else {
				n -= b;
			}
		}
		if (ans != -1) {
			out.println(ans);
		} else {
			out.println(n);
		}
		out.flush();
		out.close();
		br.close();
	}

}
