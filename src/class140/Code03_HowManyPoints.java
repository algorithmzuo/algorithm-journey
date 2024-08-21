package class140;

// 格点连线上有几个格点
// 二维网格中只有x和y的值都为整数的坐标，才叫格点
// 给定两个格点，A在(x1, y1)，B在(x2, y2)
// 返回A和B的连线上，包括A和B在内，一共有几个格点
// -10^9 <= x1、y1、x2、y2 <= 10^9
// 测试链接 : https://lightoj.com/problem/how-many-points
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code03_HowManyPoints {

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
			long x1 = (long) in.nval;
			in.nextToken();
			long y1 = (long) in.nval;
			in.nextToken();
			long x2 = (long) in.nval;
			in.nextToken();
			long y2 = (long) in.nval;
			long ans = gcd(Math.abs(x1 - x2), Math.abs(y1 - y2)) + 1;
			out.println("Case " + t + ": " + ans);
		}
		out.flush();
		out.close();
		br.close();
	}

}
