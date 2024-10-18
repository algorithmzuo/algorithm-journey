package class147;

// 树屋阶梯
// 地面高度是0，想搭建一个阶梯，要求每一个台阶上升1的高度，最终到达高度n
// 有无穷多任意规格的矩形材料，但是必须选择n个矩形，希望能搭建出阶梯的样子
// 返回搭建阶梯的不同方法数，答案可能很大，不取模！就打印真实答案
// 1 <= n <= 500
// 测试链接 : https://www.luogu.com.cn/problem/P2532
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.math.BigInteger;

public class Code08_TreehouseLadder {

	// 这里用公式2
	// java同学使用BigInteger即可
	// C++同学需要自己实现高精度乘法
	public static BigInteger compute(int n) {
		BigInteger a = new BigInteger("1");
		BigInteger b = new BigInteger("1");
		BigInteger cur;
		for (int i = 1; i <= 2 * n; i++) {
			cur = new BigInteger(String.valueOf(i));
			a = a.multiply(cur);
			if (i <= n) {
				b = b.multiply(cur);
			}
		}
		return a.divide(b.multiply(b)).divide(new BigInteger(String.valueOf(n + 1)));
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
