package class139;

// 均匀生成器
// 如果有两个数字step和mod，那么可以由以下方式生成很多数字
// seed(1) = 0，seed(i+1) = (seed(i) + step) % mod
// 比如，step = 3、mod = 5
// seed(1) = 0，seed(2) = 3，seed(3) = 1，seed(4) = 4，seed(5) = 2
// 如果能产生0 ~ mod-1所有数字，step和mod的组合叫  "Good Choice"
// 如果无法产生0 ~ mod-1所有数字，step和mod的组合叫 "Bad Choice"
// 根据step和mod，打印结果
// 1 <= step、mod <= 10^5
// 测试链接 : http://poj.org/problem?id=1597
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code03_UniformGenerator {

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
