package class097;

// 乘法逆元(逆元线性递推公式)
// 给定n、p，求1∼n中所有整数在模p意义下的乘法逆元
// 1 <= n <= 3 * 10^6
// n < p < 20000528
// 测试链接 : https://www.luogu.com.cn/problem/P3811
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下的code，提交时请把类名改成"Main"，可以直接通过

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

// 如下代码可以通过全部测试用例
public class Code01_InverseElement2 {

	public static int MAXN = 3000001;

	public static int[] inv = new int[MAXN];

	public static int n, p;

	public static void build(int n) {
		inv[1] = 1;
		for (int i = 2; i <= n; i++) {
			inv[i] = (int) (p - (long) inv[p % i] * (p / i) % p);
		}
	}

	public static void main(String[] args) throws IOException {
		StreamTokenizer in = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
		PrintWriter out = new PrintWriter(System.out);
		in.nextToken();
		n = (int) in.nval;
		in.nextToken();
		p = (int) in.nval;
		build(n);
		for (int i = 1; i <= n; i++) {
			out.println(inv[i]);
		}
		out.flush();
	}

}
