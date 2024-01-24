package class099;

// 最大公约数为1的子序列数量
// 给你一个数组，返回有多少个子序列的最大公约数是1
// 结果可能很大对1000000007取模
// 测试链接 : https://www.luogu.com.cn/problem/CF803F
// 1 <= n <= 10^5
// 1 <= nums[i] <= 10^5
// 扩展问题
// 最大公约数为k的子序列数量
// 给定一个长度为n的正数数组nums，还有正数k
// 返回有多少子序列的最大公约数为k
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下的code，提交时请把类名改成"Main"，可以直接通过

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code04_NumberOfSubsetGcdK {

	public static int MOD = 1000000007;

	public static int LIMIT = 100000;

	public static long[] dp = new long[LIMIT + 1];

	public static long[] cnt = new long[LIMIT + 1];

	public static long[] pow2 = new long[LIMIT + 1];

	public static void build() {
		pow2[0] = 1;
		for (int i = 1; i <= LIMIT; i++) {
			pow2[i] = (pow2[i - 1] * 2) % MOD;
		}
	}

	public static void main(String[] args) throws IOException {
		build();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			int n = (int) in.nval;
			for (int i = 1; i <= n; i++) {
				in.nextToken();
				cnt[(int) in.nval]++;
			}
			out.println(compute());
		}
		out.flush();
		out.close();
		br.close();
	}

	// 时间复杂度O(n * logn)
	public static long compute() {
		for (int i = LIMIT; i >= 1; i--) {
			long counts = 0;
			for (int j = i; j <= LIMIT; j += i) {
				counts = (counts + cnt[j]) % MOD;
			}
			dp[i] = (pow2[(int) counts] - 1 + MOD) % MOD;
			for (int j = 2 * i; j <= LIMIT; j += i) {
				dp[i] = (dp[i] - dp[j] + MOD) % MOD;
			}
		}
		return dp[1];
	}

}