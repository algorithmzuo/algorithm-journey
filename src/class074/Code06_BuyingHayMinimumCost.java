package class074;

// 购买足量干草的最小花费
// 有n个提供干草的公司，每个公司都有两个信息
// cost[i]代表购买1次产品需要花的钱
// val[i]代表购买1次产品所获得的干草数量
// 每个公司的产品都可以购买任意次
// 你一定要至少购买h数量的干草，返回最少要花多少钱
// 测试链接 : https://www.luogu.com.cn/problem/P2918
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

public class Code06_BuyingHayMinimumCost {

	public static int MAXN = 101;

	public static int MAXM = 55001;

	public static int[] val = new int[MAXN];

	public static int[] cost = new int[MAXN];

	public static int[] dp = new int[MAXM];

	public static int n, h, maxv, m;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			n = (int) in.nval;
			in.nextToken();
			h = (int) in.nval;
			maxv = 0;
			for (int i = 1; i <= n; i++) {
				in.nextToken();
				val[i] = (int) in.nval;
				maxv = Math.max(maxv, val[i]);
				in.nextToken();
				cost[i] = (int) in.nval;
			}
			// 最核心的一句
			// 包含重要分析
			m = h + maxv;
			out.println(compute2());
		}
		out.flush();
		out.close();
		br.close();
	}

	// dp[i][j] : 1...i里挑公司，购买严格j磅干草，需要的最少花费
	// 1) dp[i-1][j]
	// 2) dp[i][j-val[i]] + cost[i]
	// 两种可能性中选最小
	// 但是关于j需要进行一定的扩充，原因视频里讲了
	public static int compute1() {
		int[][] dp = new int[n + 1][m + 1];
		Arrays.fill(dp[0], 1, m + 1, Integer.MAX_VALUE);
		for (int i = 1; i <= n; i++) {
			for (int j = 0; j <= m; j++) {
				dp[i][j] = dp[i - 1][j];
				if (j - val[i] >= 0 && dp[i][j - val[i]] != Integer.MAX_VALUE) {
					dp[i][j] = Math.min(dp[i][j], dp[i][j - val[i]] + cost[i]);
				}
			}
		}
		int ans = Integer.MAX_VALUE;
		// >= h
		// h h+1 h+2 ... m
		for (int j = h; j <= m; j++) {
			ans = Math.min(ans, dp[n][j]);
		}
		return ans;
	}

	// 空间压缩
	public static int compute2() {
		Arrays.fill(dp, 1, m + 1, Integer.MAX_VALUE);
		for (int i = 1; i <= n; i++) {
			for (int j = val[i]; j <= m; j++) {
				if (dp[j - val[i]] != Integer.MAX_VALUE) {
					dp[j] = Math.min(dp[j], dp[j - val[i]] + cost[i]);
				}
			}
		}
		int ans = Integer.MAX_VALUE;
		for (int j = h; j <= m; j++) {
			ans = Math.min(ans, dp[j]);
		}
		return ans;
	}

}
