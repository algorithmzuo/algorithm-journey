package class138;

// 题目5，最佳团体，另一种二分的写法
// 思路是不变的，二分的写法多种多样
// 代码中打注释的位置，就是更简单的二分逻辑，其他代码没有变化
// 测试链接 : https://www.luogu.com.cn/problem/P4322
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Other5 {

	public static int MAXN = 3001;

	public static int LIMIT = 10000;

	public static double NA = -1e9;

	public static int[] head = new int[MAXN];

	public static int[] next = new int[MAXN];

	public static int[] to = new int[MAXN];

	public static int edgeCnt;

	public static int[] cost = new int[MAXN];

	public static int[] strength = new int[MAXN];

	public static int[] dfn = new int[MAXN];

	public static int dfnCnt;

	public static double[] value = new double[MAXN];

	public static int[] size = new int[MAXN];

	public static double[][] dp = new double[MAXN][MAXN];

	public static int k, n;

	public static void prepare() {
		edgeCnt = 1;
		dfnCnt = 0;
		Arrays.fill(head, 1, n + 1, 0);
	}

	public static void addEdge(int u, int v) {
		next[edgeCnt] = head[u];
		to[edgeCnt] = v;
		head[u] = edgeCnt++;
	}

	public static int dfs(int u) {
		int i = ++dfnCnt;
		dfn[u] = i;
		size[i] = 1;
		for (int e = head[u], v; e != 0; e = next[e]) {
			v = to[e];
			size[i] += dfs(v);
		}
		return size[i];
	}

	public static boolean check(double x) {
		for (int i = 0; i <= n; i++) {
			value[dfn[i]] = (double) strength[i] - x * cost[i];
		}
		for (int j = 1; j <= k; j++) {
			dp[dfnCnt + 1][j] = NA;
		}
		for (int i = dfnCnt; i >= 2; i--) {
			for (int j = 1; j <= k; j++) {
				dp[i][j] = Math.max(dp[i + size[i]][j], value[i] + dp[i + 1][j - 1]);
			}
		}
		return dp[2][k] >= 0;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		k = (int) in.nval;
		in.nextToken();
		n = (int) in.nval;
		prepare();
		for (int i = 1; i <= n; i++) {
			in.nextToken();
			cost[i] = (int) in.nval;
			in.nextToken();
			strength[i] = (int) in.nval;
			in.nextToken();
			addEdge((int) in.nval, i);
		}
		dfs(0);
		double l = 0, r = LIMIT, x;
		// 二分进行60次，足够达到题目要求的精度
		// 二分完成后，l就是答案
		for (int i = 1; i <= 60; i++) {
			x = (l + r) / 2;
			if (check(x)) {
				l = x;
			} else {
				r = x;
			}
		}
		out.printf("%.3f\n", l);
		out.flush();
		out.close();
		br.close();
	}

}
