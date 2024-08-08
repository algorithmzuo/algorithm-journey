package class138;

// 最佳团体
// 给定一棵树，节点编号0~n，0号节点是整棵树的头
// 编号1~n的节点，每个节点都有招募花费和战斗值，0号节点这两个值都是0
// 给定每条边(a,b)，表示节点a的父节点是b，有些节点的父节点是0节点
// 当招募了某个节点，那么该节点及其上方的所有祖先节点都需要招募
// 除了0号节点之外，一共可以招募k个人，希望让
// 战斗值之和 / 招募花费之和，这个比值尽量大，答案只需保留三位小数，更大的精度舍弃
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

	// 链式前向星
	public static int[] head = new int[MAXN];

	public static int[] next = new int[MAXN];

	public static int[] to = new int[MAXN];

	public static int edgeCnt;

	// 招募花费，下标为节点原始编号
	public static int[] cost = new int[MAXN];

	// 战斗值，下标为节点原始编号
	public static int[] strength = new int[MAXN];

	// dfn[a] = b，表示原始a号节点的dfn编号为b
	public static int[] dfn = new int[MAXN];

	// dfn序计数
	public static int dfnCnt;

	// (战斗值 - x * 招募花费)的结余，下标为节点dfn编号
	public static double[] value = new double[MAXN];

	// 子树大小，下标为节点dfn编号
	public static int[] size = new int[MAXN];

	// 树型dp
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

	// 记录节点u的dfn编号 + 返回u这棵子树的节点数
	// 如下实现和讲解079，题目5，选课问题的最优解，几乎完全一样
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

	// 根据x的值，计算节点的结余值，在树上选k+1个点
	// 一定要形成有效结构，返回最大的结余和
	// 核心逻辑完全来自讲解079，题目5，选课问题，重点介绍的最优解
	public static boolean check(double x) {
		// value[节点i的dfn编号] = 节点i的战斗值 - x * 节点i的招募花费
		for (int i = 0; i <= n; i++) {
			value[dfn[i]] = (double) strength[i] - x * cost[i];
		}
		// 越界位置认为都是无效解
		for (int j = 1; j <= k; j++) {
			dp[dfnCnt + 1][j] = NA;
		}
		// 讲解079题目5的最优解逻辑
		for (int i = dfnCnt; i >= 2; i--) {
			for (int j = 1; j <= k; j++) {
				dp[i][j] = Math.max(dp[i + size[i]][j], value[i] + dp[i + 1][j - 1]);
			}
		}
		// 原始的0号节点，dfn编号是1，其他节点的dfn编号从2开始
		// 0号节点的战斗值和招募花费都是0，其他节点一共招募k个
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
