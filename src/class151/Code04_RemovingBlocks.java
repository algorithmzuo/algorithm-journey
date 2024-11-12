package class151;

// 消除砖头
// 测试链接 : https://www.luogu.com.cn/problem/AT_agc028_b
// 测试链接 : https://atcoder.jp/contests/agc028/tasks/agc028_b
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code04_RemovingBlocks {

	public static int MOD = 1000000007;

	public static int MAXN = 100001;

	// 所有数字
	public static int[] arr = new int[MAXN];

	// 连续数字逆元表
	public static int[] inv = new int[MAXN];

	// sum[i] = (1/1 + 1/2 + 1/3 + ... + 1/i)，%MOD意义下的余数
	public static int[] sum = new int[MAXN];

	public static int n;

	public static void build() {
		inv[1] = 1;
		for (int i = 2; i <= n; i++) {
			inv[i] = (int) (MOD - (long) inv[MOD % i] * (MOD / i) % MOD);
		}
		for (int i = 1; i <= n; i++) {
			sum[i] = (sum[i - 1] + inv[i]) % MOD;
		}
	}

	public static long compute() {
		build();
		long ans = 0;
		for (int i = 1; i <= n; i++) {
			ans = (ans + (long) (sum[i] + sum[n - i + 1] - 1) * arr[i]) % MOD;
		}
		for (int i = 1; i <= n; ++i) {
			ans = ans * i % MOD;
		}
		return ans;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		n = (int) in.nval;
		for (int i = 1; i <= n; i++) {
			in.nextToken();
			arr[i] = (int) in.nval;
		}
		out.println(compute());
		out.flush();
		out.close();
		br.close();
	}

}
