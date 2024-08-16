package class140;

// 测试链接 : http://poj.org/problem?id=1265

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code04 {

	public static long gcd(long a, long b) {
		return b == 0 ? a : gcd(b, a % b);
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		int cases = (int) in.nval;
		for (int t = 1; t <= cases; t++) {
			in.nextToken();
			int n = (int) in.nval;
			int edges = 0;
			double area = 0;
			for (int i = 1, x = 0, y = 0, xm, ym; i <= n; i++) {
				in.nextToken();
				xm = (int) in.nval;
				in.nextToken();
				ym = (int) in.nval;
				edges += gcd(Math.abs(xm), Math.abs(ym));
				area += x * (y + ym) - (x + xm) * y;
				x += xm;
				y += ym;
			}
			// pick定理
			int inners = (int) (area / 2) + 1 - edges / 2;
			out.println("Scenario #" + t + ":");
			out.print(inners + " ");
			out.print(edges + " ");
			out.printf("%.1f", area / 2);
			out.println();
			out.println();
		}
		out.flush();
		out.close();
		br.close();
	}

}
