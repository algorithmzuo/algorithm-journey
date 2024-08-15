package class140;

// 测试链接 : https://www.luogu.com.cn/problem/P3951

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code05 {

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
