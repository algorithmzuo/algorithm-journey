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

public class Code03_UnboundedKnapsack {

	public static int MAXM = 10001;

	public static int MAXT = 10000001;

	public static int[] cost = new int[MAXM];

	public static int[] val = new int[MAXM];

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
			for (int i = 1; i <= m; i++) {
				in.nextToken();
				cost[i] = (int) in.nval;
				in.nextToken();
				val[i] = (int) in.nval;
			}
			out.println(compute2());
		}
		out.flush();
		out.close();
		br.close();
	}

	// 严格位置依赖的动态规划
	// 会空间不够，导致无法通过全部测试用例
	public static long compute1() {
		// dp[0][.....] = 0
		int[][] dp = new int[m + 1][t + 1];
		for (int i = 1; i <= m; i++) {
			for (int j = 0; j <= t; j++) {
				dp[i][j] = dp[i - 1][j];
				if (j - cost[i] >= 0) {
					dp[i][j] = Math.max(dp[i][j], dp[i][j - cost[i]] + val[i]);
				}
			}
		}
		return dp[m][t];
	}

	// 空间压缩
	// 可以通过全部测试用例
	public static long compute2() {
		Arrays.fill(dp, 1, t + 1, 0);
		for (int i = 1; i <= m; i++) {
			for (int j = cost[i]; j <= t; j++) {
				dp[j] = Math.max(dp[j], dp[j - cost[i]] + val[i]);
			}
		}
		return dp[t];
	}

}
