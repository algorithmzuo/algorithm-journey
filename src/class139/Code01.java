package class139;

// 测试链接 : https://www.luogu.com.cn/problem/P4549

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code01 {

	public static int gcd(int a, int b) {
		return b == 0 ? a : gcd(b, a % b);
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		int n = (int) in.nval;
		int ans = 0;
		for (int i = 1; i <= n; i++) {
			in.nextToken();
			ans = gcd(Math.abs((int) in.nval), ans);
		}
		out.println(ans);
		out.flush();
		out.close();
		br.close();
	}

}
