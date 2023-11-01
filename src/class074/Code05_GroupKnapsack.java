package class074;

// 分组背包(模版)
// 自 01背包问世之后，小 A 对此深感兴趣
// 一天，小 A 去远游，却发现他的背包不同于 01 背包，他的物品大致可分为 k 组
// 每组中的物品只能选择1件，现在他想知道最大的利用价值是多少
// 测试链接 : www.luogu.com.cn/problem/P1757
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下的所有代码，并把主类名改成"Main"，可以直接通过

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code05_GroupKnapsack {

	public static int MAXN = 1001;

	public static int MAXM = 1001;

	// arr[i][0] 重量
	// arr[i][1] 价值
	// arr[i][2] 组号
	public static int[][] arr = new int[MAXN][3];

	public static int[] dp = new int[MAXM];

	public static int m, n;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			m = (int) in.nval;
			in.nextToken();
			n = (int) in.nval;
			for (int i = 0; i < n; i++) {
				in.nextToken();
				arr[i][0] = (int) in.nval;
				in.nextToken();
				arr[i][1] = (int) in.nval;
				in.nextToken();
				arr[i][2] = (int) in.nval;
			}
			Arrays.sort(arr, 0, n, (a, b) -> a[2] - b[2]);
			out.println(compute());

		}
		out.flush();
		out.close();
		br.close();
	}

	public static int compute() {
		Arrays.fill(dp, 0, m + 1, 0);
		for (int start = 0, end = 1; start < n;) {
			while (end < n && arr[end][2] == arr[start][2]) {
				end++;
			}
			for (int r = m; r >= 0; r--) {
				for (int i = start; i < end; i++) {
					if (r >= arr[i][0]) {
						dp[r] = Math.max(dp[r], arr[i][1] + dp[r - arr[i][0]]);
					}
				}
			}
			start = end++;
		}
		return dp[m];
	}

}
