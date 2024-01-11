package class097;

// 判断较大的数字是否为质数(Miller-Rabin模版)
// 测试链接 : https://www.luogu.com.cn/problem/U148828
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下的code，提交时请把类名改成"Main"，可以直接通过

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.BigInteger;

// 为什么不自己写，而要用BigInteger中的isProbablePrime方法
// 课上有说明为什么
public class Code02_LargeNumberIsPrime1 {

	// 测试次数，次数越多失误率越低，但速度也越慢
	// 检查n这个数字是否为质数的时间复杂度O(s * (logn)的三次方)
	public static int s = 10;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		int t = Integer.valueOf(br.readLine());
		for (int i = 0; i < t; i++) {
			BigInteger n = new BigInteger(br.readLine());
			out.println(n.isProbablePrime(s) ? "Yes" : "No");
		}
		out.flush();
		out.close();
		br.close();
	}

}