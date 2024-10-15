package class147;

// 任意前缀上红大于黑
// 答案对 100 取模
// 测试链接 : https://www.luogu.com.cn/problem/P1722
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code02_RedMore {

	public static int MOD = 100;

	// 因为取模的数字含有很多因子
	// 无法用费马小定理或者扩展欧几里得求逆元
	// 同时注意到n的范围并不大，直接使用公式4
	public static long compute(int n) {
		long[] catalan = new long[n + 1];
		catalan[0] = catalan[1] = 1;
		for (int i = 2; i <= n; i++) {
			for (int l = 0, r = i - 1; l < i; l++, r--) {
				catalan[i] = (catalan[i] + catalan[l] * catalan[r] % MOD) % MOD;
			}
		}
		return catalan[n];
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		int n = (int) in.nval;
		out.println(compute(n));
		out.flush();
		out.close();
		br.close();
	}

}
