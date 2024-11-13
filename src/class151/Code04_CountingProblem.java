package class151;

// 序列计数
// 有一个概念叫，最左端最大值位置，表示一段范围上
// 如果最大值有一个，那么最大值所在的位置，就是最左端最大值位置
// 如果最大值有多个，最左的那个所在的位置，就是最左端最大值位置
// 给定一个长度为n的数组A，那么必然存在等长的数组B
// 两者在任意范围上，最左端最大值位置是相同的，不仅存在数组B，而且是无限多的
// 现在要求，数组B中的每个值都在[1,m]范围，返回有多少个这样的数组，答案对 1000000007 取模
// 2 <= n、m <= 2 * 10^5    1 <= A[i] <= m    n * m <= 10^6
// 测试链接 : https://www.luogu.com.cn/problem/CF1748E
// 测试链接 : https://codeforces.com/problemset/problem/1748/E
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code04_CountingProblem {

	public static int MOD = 1000000007;

	public static int MAXN = 1000001;

	public static int[] arr = new int[MAXN];

	public static int[] left = new int[MAXN];

	public static int[] right = new int[MAXN];

	public static int[] stack = new int[MAXN];

	public static int n, m;

	public static void build() {
		for (int i = 1, top = 0, pos; i <= n; i++) {
			pos = top;
			while (pos > 0 && arr[stack[pos]] < arr[i]) {
				pos--;
			}
			if (pos > 0) {
				right[stack[pos]] = i;
			}
			if (pos < top) {
				left[i] = stack[pos + 1];
			}
			stack[++pos] = i;
			top = pos;
		}
	}

	public static void dfs(int i, long[][] dp) {
		for (int j = 1; j <= m; j++) {
			dp[i][j] = 1;
		}
		if (left[i] != 0) {
			dfs(left[i], dp);
			for (int j = 1; j <= m; j++) {
				dp[i][j] = dp[i][j] * dp[left[i]][j - 1] % MOD;
			}
		}
		if (right[i] != 0) {
			dfs(right[i], dp);
			for (int j = 1; j <= m; j++) {
				dp[i][j] = dp[i][j] * dp[right[i]][j] % MOD;
			}
		}
		for (int j = 2; j <= m; j++) {
			dp[i][j] = (dp[i][j] + dp[i][j - 1]) % MOD;
		}
	}

	public static void clear() {
		Arrays.fill(left, 1, n + 1, 0);
		Arrays.fill(right, 1, n + 1, 0);
	}

	public static long compute() {
		build();
		long[][] dp = new long[n + 1][m + 1];
		dfs(stack[1], dp);
		clear();
		return dp[stack[1]][m];
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		int cases = (int) in.nval;
		for (int t = 1; t <= cases; t++) {
			in.nextToken();
			n = (int) in.nval;
			in.nextToken();
			m = (int) in.nval;
			for (int i = 1; i <= n; i++) {
				in.nextToken();
				arr[i] = (int) in.nval;
			}
			out.println(compute());
		}
		out.flush();
		out.close();
		br.close();
	}

}
