package class139;

// 修理宝塔
// 一共有编号1~n的宝塔，其中a号和b号宝塔已经修好了
// Yuwgna和Iaka两个人轮流修塔，Yuwgna先手，Iaka后手，谁先修完所有的塔谁赢
// 每次可以选择j+k号或者j-k号塔进行修理，其中j和k是任意两个已经修好的塔
// 也就是输入n、a、b，如果先手赢打印"Yuwgna"，后手赢打印"Iaka"
// 2 <= n <= 2 * 10^4
// 测试链接 : https://acm.hdu.edu.cn/showproblem.php?pid=5512
// 测试链接 : https://vjudge.net/problem/HDU-5512
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code02_Pagodas {

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
