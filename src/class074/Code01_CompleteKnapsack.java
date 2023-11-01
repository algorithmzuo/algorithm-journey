package class074;

// 完全背包(模版)
// 给定一个正数t，表示背包的容量
// 有m种货物，每种货物可以选择任意个
// 每种货物都有体积costs[i]和价值values[i]
// 返回在不超过总容量的情况下，怎么挑选货物能达到价值最大
// 返回最大的价值
// 测试链接 : https://www.luogu.com.cn/problem/P1616
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

public class Code01_CompleteKnapsack {

	public static int MAXM = 10001;

	public static int MAXT = 10000001;

	public static int[] costs = new int[MAXM];

	public static int[] values = new int[MAXM];

	public static long[] dp = new long[MAXT];

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
	// 2) dp[i][j-costs[i]] + values[i]
	public static long compute() {
		Arrays.fill(dp, 1, t + 1, 0);
		for (int i = 0; i < m; i++) {
			for (int j = costs[i]; j <= t; j++) {
				dp[j] = Math.max(dp[j], dp[j - costs[i]] + values[i]);
			}
		}
		return dp[t];
	}

}
