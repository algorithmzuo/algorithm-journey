package class147;

// 任意前缀上红多于黑
// 有n个红和n个黑，要组成2n长度的数列，保证任意前缀上，红的数量 >= 黑的数量
// 返回有多少种排列方法，答案对 100 取模
// 1 <= n <= 100
// 测试链接 : https://www.luogu.com.cn/problem/P1722
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code03_RedMore {

	public static int MOD = 100;

	// 因为取模的数字含有很多因子
	// 无法用费马小定理或者扩展欧几里得求逆元
	// 同时注意到n的范围并不大，直接使用公式4
	public static long compute(int n) {
		long[] f = new long[n + 1];
		f[0] = f[1] = 1;
		for (int i = 2; i <= n; i++) {
			for (int l = 0, r = i - 1; l < i; l++, r--) {
				f[i] = (f[i] + f[l] * f[r] % MOD) % MOD;
			}
		}
		return f[n];
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
