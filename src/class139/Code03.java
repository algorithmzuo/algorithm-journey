package class139;

// 测试链接 : http://poj.org/problem?id=1597

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code03 {

	public static int gcd(int a, int b) {
		return b == 0 ? a : gcd(b, a % b);
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			int step = (int) in.nval;
			in.nextToken();
			int mod = (int) in.nval;
			out.print(String.format("%10d", step) + String.format("%10d", mod) + "    ");
			out.println(gcd(step, mod) == 1 ? "Good Choice" : "Bad Choice");
			out.println(" ");
		}
		out.flush();
		out.close();
		br.close();
	}

}
