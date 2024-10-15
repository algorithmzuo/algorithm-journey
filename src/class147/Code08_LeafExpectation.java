package class147;

// 叶子节点数的期望
// 测试链接 : https://www.luogu.com.cn/problem/P3978
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code08_LeafExpectation {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		double n = in.nval;
		out.printf("%.12f", n * (n + 1) / (2 * (2 * n - 1)));
		out.flush();
		out.close();
		br.close();
	}

}
