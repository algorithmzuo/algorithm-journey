package class140;

// 无法组成的最大值
// 一共有a、b两种面值的硬币，a和b一定互质，每种硬币都有无限个
// 返回a和b无法组成的钱数中，最大值是多少
// 题目的输入保证存在最大的无法组成的钱数
// 1 <= a、b <= 10^9
// 测试链接 : https://www.luogu.com.cn/problem/P3951
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code05_LargestUnattainable {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		long a = (long) in.nval;
		in.nextToken();
		long b = (long) in.nval;
		out.println(a * b - a - b);
		out.flush();
		out.close();
		br.close();
	}

}
