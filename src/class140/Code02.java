package class140;

// 测试链接 : https://lightoj.com/problem/how-many-points

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code02 {

	public static long gcd(long a, long b) {
		return b == 0 ? a : gcd(b, a % b);
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		int cases = (int) in.nval;
		for (int i = 1; i <= cases; i++) {
			in.nextToken(); long x1 = (long) in.nval;
			in.nextToken(); long y1 = (long) in.nval;
			in.nextToken(); long x2 = (long) in.nval;
			in.nextToken(); long y2 = (long) in.nval;
			long ans = gcd(Math.abs(x1 - x2), Math.abs(y1 - y2)) + 1;
			out.println("Case " + i + ": " + ans);
		}
		out.flush();
		out.close();
		br.close();
	}

}
