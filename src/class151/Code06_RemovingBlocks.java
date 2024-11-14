package class151;

// 砖块消除
// 给定一个长度为n的数组arr，arr[i]为i号砖块的重量
// 选择一个没有消除的砖块进行消除，收益为被消除砖块联通区域的重量之和，比如arr = {3,5,2,1}
// 如果先消除5，那么获得3+5+2+1的收益，arr = {3,X,2,1}
// 如果再消除1，那么获得2+1的收益，arr = {3,X,2,X}
// 如果再消除2，那么获得2的收益，arr = {3,X,X,X}
// 如果再消除3，那么获得3的收益，arr = {X,X,X,X}
// 一共有n!种消除方案，返回所有消除方案的收益总和，答案对 1000000007 取模
// 1 <= n <= 10^5    1 <= arr[i] <= 10^9
// 测试链接 : https://www.luogu.com.cn/problem/AT_agc028_b
// 测试链接 : https://atcoder.jp/contests/agc028/tasks/agc028_b
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code06_RemovingBlocks {

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
