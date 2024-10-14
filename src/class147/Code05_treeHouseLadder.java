package class147;

// 树屋阶梯
// 测试链接 : https://www.luogu.com.cn/problem/P2532
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.math.BigInteger;

public class Code05_treeHouseLadder {

	// 这里用公式1
	// java同学使用BigInteger即可
	// C++同学需要自己实现高精度乘法了
	public static BigInteger compute(int n) {
		BigInteger a = new BigInteger("1");
		BigInteger b = new BigInteger("1");
		BigInteger c = new BigInteger("1");
		BigInteger d = new BigInteger("1");
		BigInteger cur;
		for (int i = 1; i <= 2 * n; i++) {
			cur = new BigInteger(String.valueOf(i));
			a = a.multiply(cur);
			if (i <= n) {
				b = b.multiply(cur);
			}
			if (i <= n - 1) {
				c = c.multiply(cur);
			}
			if (i <= n + 1) {
				d = d.multiply(cur);
			}
		}
		return a.divide(b.multiply(b)).subtract(a.divide(c.multiply(d)));
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		int n = (int) in.nval;
		BigInteger ans = compute(n);
		out.println(ans.toString());
		out.flush();
		out.close();
		br.close();
	}

}
