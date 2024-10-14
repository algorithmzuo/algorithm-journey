package class147;

// 划分三角形
// 答案对 1000000 取余
// 1 <= n <= 1000
// 测试链接 : https://www.luogu.com.cn/problem/SP7897
// 测试链接 : https://www.spoj.com/problems/SKYLINE
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code04_SplitTriangle {

	public static int MOD = 1000000;

	public static int MAXN = 1001;

	public static long[] catalan = new long[MAXN];

	// 因为取余的数字含有很多因子
	// 无法用费马小定理或者扩展欧几里得求逆元
	// 所以使用公式4
	public static void build() {
		catalan[0] = catalan[1] = 1;
		for (int i = 2; i < MAXN; i++) {
			for (int l = 0, r = i - 1; l < i; l++, r--) {
				catalan[i] = (catalan[i] + catalan[l] * catalan[r] % MOD) % MOD;
			}
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		build();
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			int n = (int) in.nval;
			if (n == 0) {
				break;
			}
			out.println(catalan[n]);
		}
		out.flush();
		out.close();
		br.close();
	}

}
