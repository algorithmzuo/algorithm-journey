package class098;

// 乘法快速幂模版
// 求a的b次方，对p取模的结果
// 测试链接 : https://www.luogu.com.cn/problem/P1226
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下的code，提交时请把类名改成"Main"，可以直接通过

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code01_QuickPower {

	public static long a, b, p;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		a = (int) in.nval;
		in.nextToken();
		b = (int) in.nval;
		in.nextToken();
		p = (int) in.nval;
		out.println(a + "^" + b + " mod " + p + "=" + power());
		out.flush();
		out.close();
		br.close();
	}

	public static int power() {
		long ans = 1;
		while (b > 0) {
			if ((b & 1) == 1) {
				ans = (ans * a) % p;
			}
			a = (a * a) % p;
			b >>= 1;
		}
		return (int) ans;
	}

}
