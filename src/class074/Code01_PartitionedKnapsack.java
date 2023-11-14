package class074;

// 分组背包(模版)
// 自01背包问世之后，小 A 对此深感兴趣
// 一天，小 A 去远游，却发现他的背包不同于 01 背包，他的物品大致可分为 k 组
// 每组中的物品只能选择1件，现在他想知道最大的利用价值是多少
// 测试链接 : https://www.luogu.com.cn/problem/P1757
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

public class Code01_PartitionedKnapsack {

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
			for (int i = 1; i <= n; i++) {
				in.nextToken();
				arr[i][0] = (int) in.nval;
				in.nextToken();
				arr[i][1] = (int) in.nval;
				in.nextToken();
				arr[i][2] = (int) in.nval;
			}
			Arrays.sort(arr, 1, n + 1, (a, b) -> a[2] - b[2]);
			out.println(compute2());
		}
		out.flush();
		out.close();
		br.close();
	}

	// 严格位置依赖的动态规划
	public static int compute1() {
		int teams = 1;
		for (int i = 2; i <= n; i++) {
			if (arr[i - 1][2] != arr[i][2]) {
				teams++;
			}
		}
		int[][] dp = new int[teams + 1][m + 1];
		for (int start = 1, end = 2, k = 1; start <= n; k++) {
			while (end <= n && arr[end][2] == arr[start][2]) {
				end++;
			}
			// arr[start...end-1]是当前组，组号一样
			for (int j = 0; j <= m; j++) {
				dp[k][j] = dp[k - 1][j];
				for (int i = start; i < end; i++) {
					if (j >= arr[i][0]) {
						dp[k][j] = Math.max(dp[k][j], dp[k - 1][j - arr[i][0]] + arr[i][1]);
					}
				}
			}
			// start去往下一组的第一个物品
			// 继续处理剩下的组
			start = end++;
		}
		return dp[teams][m];
	}

	// 空间压缩
	public static int compute2() {
		Arrays.fill(dp, 0, m + 1, 0);
		for (int start = 1, end = 2; start <= n;) {
			while (end <= n && arr[end][2] == arr[start][2]) {
				end++;
			}
			for (int j = m; j >= 0; j--) {
				for (int i = start; i < end; i++) {
					if (j >= arr[i][0]) {
						dp[j] = Math.max(dp[j], arr[i][1] + dp[j - arr[i][0]]);
					}
				}
			}
			start = end++;
		}
		return dp[m];
	}

}
