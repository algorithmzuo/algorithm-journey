package class073;

// 01背包(模版)
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

public class Code01_01Knapsack {

	public static int MAXM = 101;

	public static int MAXT = 1001;

	public static int[] cost = new int[MAXM];

	public static int[] val = new int[MAXM];

	public static int[] dp = new int[MAXT];

	public static int t, n;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			t = (int) in.nval;
			in.nextToken();
			n = (int) in.nval;
			for (int i = 1; i <= n; i++) {
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
	// n个物品编号1~n，第i号物品的花费cost[i]、价值val[i]
	// cost、val数组是全局变量，已经把数据读入了
	public static int compute1() {
		int[][] dp = new int[n + 1][t + 1];
		for (int i = 1; i <= n; i++) {
			for (int j = 0; j <= t; j++) {
				// 不要i号物品
				dp[i][j] = dp[i - 1][j];
				if (j - cost[i] >= 0) {
					// 要i号物品
					dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - cost[i]] + val[i]);
				}
			}
		}
		return dp[n][t];
	}

	// 空间压缩
	public static int compute2() {
		Arrays.fill(dp, 0, t + 1, 0);
		for (int i = 1; i <= n; i++) {
			for (int j = t; j >= cost[i]; j--) {
				dp[j] = Math.max(dp[j], dp[j - cost[i]] + val[i]);
			}
		}
		return dp[t];
	}

}
