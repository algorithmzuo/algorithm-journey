package class139;

// 测试链接 : https://acm.hdu.edu.cn/showproblem.php?pid=5512
// 测试链接 : https://vjudge.net/problem/HDU-5512

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code02 {

	public static int gcd(int a, int b) {
		return b == 0 ? a : gcd(b, a % b);
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		int cases = (int) in.nval;
		for (int t = 1, n, a, b; t <= cases; t++) {
			in.nextToken();
			n = (int) in.nval;
			in.nextToken();
			a = (int) in.nval;
			in.nextToken();
			b = (int) in.nval;
			out.print("Case #" + t + ": ");
			if (((n / gcd(a, b)) & 1) == 1) {
				out.println("Yuwgna");
			} else {
				out.println("Iaka");
			}
		}
		out.flush();
		out.close();
		br.close();
	}

}
