package class138;

// 最佳团体
// 给定一棵树，节点编号0~n，0编号一定是整棵树的头
// 给定每条边(a,b)，表示a的父节点是b
// 每个节点代表一个人，每个人有招募花费和战斗值
// 当你招募了某人，那么该人及其上方所有祖先节点都需要招募
// 一共可以招募包括0号点在内的k+1个人，希望让
// 战斗值之和 / 招募花费之和，这个比值尽量大
// 答案只需保留三位小数，更大的精度舍弃
// 1 <= k <= n <= 2500
// 0 <= 招募花费、战斗值 <= 10^4
// 测试链接 : https://www.luogu.com.cn/problem/P4322
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code05_BestTeam {

	public static int MAXN = 3001;

	public static int LIMIT = 10000;

	public static double NA = -1e9;

	public static double sml = 1e-6;

	// 招募花费
	public static int[] cost = new int[MAXN];

	// 战斗值
	public static int[] strength = new int[MAXN];

	// (战斗值 - x*招募花费)的值
	public static double[] value = new double[MAXN];

	// 链式前向星
	public static int[] head = new int[MAXN];

	public static int[] next = new int[MAXN];

	public static int[] to = new int[MAXN];

	public static int edgeCnt;

	// 树型dp
	public static int[] size = new int[MAXN];

	public static int[] dfn = new int[MAXN];

	public static int dfnCnt;

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

	public static void dfs(int u) {
		dfn[++dfnCnt] = u;
		size[u] = 1;
		for (int e = head[u], v; e != 0; e = next[e]) {
			v = to[e];
			dfs(v);
			size[u] += size[v];
		}
	}

	// 核心逻辑来自，讲解079，题目5，选课问题，重点介绍的最优解
	public static boolean check(double x) {
		for (int i = 1; i <= dfnCnt + 1; i++) {
			for (int j = 1; j <= k; j++) {
				dp[i][j] = NA;
			}
		}
		for (int i = 1; i <= n; i++) {
			value[i] = (double) strength[i] - x * cost[i];
		}
		for (int i = dfnCnt; i >= 1; i--) {
			for (int j = 1; j <= k; j++) {
				dp[i][j] = Math.max(dp[i + 1][j - 1] + value[dfn[i]], dp[i + size[dfn[i]]][j]);
			}
		}
		return dp[1][k] >= 0;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		k = (int) in.nval + 1;
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
		double l = 0, r = LIMIT, x, ans = 0;
		while (l < r && r - l >= sml) {
			x = (l + r) / 2;
			if (check(x)) {
				ans = x;
				l = x + sml;
			} else {
				r = x - sml;
			}
		}
		out.printf("%.3f\n", ans);
		out.flush();
		out.close();
		br.close();
	}

}
