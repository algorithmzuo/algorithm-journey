package class073;

// 01背包问题(模版)
// 给定一个正数t，表示背包的容量
// 有m个货物，每个货物可以选择一次
// 每个货物有自己的体积costs[i]和价值values[i]
// 返回在不超过总容量的情况下，怎么挑选货物能达到价值最大
// 返回最大的价值
// 测试链接 : https://www.luogu.com.cn/problem/P1048
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

public class Code01_ZeroOneKnapsackProblem {

	public static int MAXM = 101;

	public static int MAXT = 1001;

	public static int[] costs = new int[MAXM];

	public static int[] values = new int[MAXM];

	public static int[] dp = new int[MAXT];

	public static int t, m;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			t = (int) in.nval;
			in.nextToken();
			m = (int) in.nval;
			for (int i = 0; i < m; i++) {
				in.nextToken();
				costs[i] = (int) in.nval;
				in.nextToken();
				values[i] = (int) in.nval;
			}
			out.println(compute());
		}
		out.flush();
		out.close();
		br.close();
	}

	// dp[i][j] =
	// 1) dp[i-1][j]
	// 2) dp[i-1][j-costs[i]] + values[i]
	public static int compute() {
		Arrays.fill(dp, 1, t + 1, 0);
		for (int i = 0; i < m; i++) {
			for (int j = t; j >= costs[i]; j--) {
				dp[j] = Math.max(dp[j], dp[j - costs[i]] + values[i]);
			}
		}
		int ans = 0;
		for (int j = 1; j <= t; j++) {
			ans = Math.max(ans, dp[j]);
		}
		return ans;
	}

}
