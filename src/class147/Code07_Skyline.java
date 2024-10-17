package class147;

// 不含递增三元组的排列方法数
// 数字从1到n，可以形成很多排列，要求任意从左往右的三个位置，不能出现依次递增的样子
// 返回排列的方法数，答案对 1000000 取模
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

public class Code07_Skyline {

	public static int MOD = 1000000;

	public static int MAXN = 1001;

	public static long[] f = new long[MAXN];

	// 因为取模的数字含有很多因子
	// 无法用费马小定理或者扩展欧几里得求逆元
	// 同时注意到n的范围并不大，直接使用公式4
	public static void build() {
		f[0] = f[1] = 1;
		for (int i = 2; i < MAXN; i++) {
			for (int l = 0, r = i - 1; l < i; l++, r--) {
				f[i] = (f[i] + f[l] * f[r] % MOD) % MOD;
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
			out.println(f[n]);
		}
		out.flush();
		out.close();
		br.close();
	}

}
