package class122;

import java.util.Arrays;

// 最小化旅行的价格总和(tarjan方法求lca)
// 有n个节点形成一棵树，每个节点上有点权，再给定很多路径
// 每条路径有开始点和结束点，路径代价就是从开始点到结束点的点权和
// 所有路径的代价总和就是旅行的价格总和
// 你可以选择把某些点的点权减少一半，来降低旅行的价格总和
// 但是要求选择的点不能相邻
// 返回旅行的价格总和最少能是多少
// 测试链接 : https://leetcode.cn/problems/minimize-the-total-price-of-the-trips/
public class Code03_MinimizePriceOfTrips2 {

	// 题目给定点的编号从0号点开始，代码中调整成从1号点开始
	public static int minimumTotalPrice(int n, int[][] es, int[] ps, int[][] ts) {
		build(n);
		for (int i = 0, j = 1; i < n; i++, j++) {
			price[j] = ps[i];
		}
		for (int[] edge : es) {
			addEdge(edge[0] + 1, edge[1] + 1);
			addEdge(edge[1] + 1, edge[0] + 1);
		}
		int m = ts.length;
		for (int i = 0, j = 1; i < m; i++, j++) {
			addQuery(ts[i][0] + 1, ts[i][1] + 1, j);
			addQuery(ts[i][1] + 1, ts[i][0] + 1, j);
		}
		tarjan(1, 0);
		for (int i = 0, j = 1, u, v, lca, lcafather; i < m; i++, j++) {
			u = ts[i][0] + 1;
			v = ts[i][1] + 1;
			lca = ans[j];
			lcafather = father[lca];
			num[u]++;
			num[v]++;
			num[lca]--;
			num[lcafather]--;
		}
		dfs(1, 0);
		dp(1, 0);
		return Math.min(no, yes);
	}

	public static int MAXN = 51;

	public static int MAXM = 101;

	public static int[] price = new int[MAXN];

	public static int[] num = new int[MAXN];

	public static int[] headEdge = new int[MAXN];

	public static int[] edgeNext = new int[MAXN << 1];

	public static int[] edgeTo = new int[MAXN << 1];

	public static int tcnt;

	public static int[] headQuery = new int[MAXN];

	public static int[] queryNext = new int[MAXM << 1];

	public static int[] queryTo = new int[MAXM << 1];

	public static int[] queryIndex = new int[MAXM << 1];

	public static int qcnt;

	public static boolean[] visited = new boolean[MAXN];

	public static int[] unionfind = new int[MAXN];

	public static int[] father = new int[MAXN];

	public static int[] ans = new int[MAXM];

	public static void build(int n) {
		Arrays.fill(num, 1, n + 1, 0);
		tcnt = qcnt = 1;
		Arrays.fill(headEdge, 1, n + 1, 0);
		Arrays.fill(headQuery, 1, n + 1, 0);
		Arrays.fill(visited, 1, n + 1, false);
		for (int i = 1; i <= n; i++) {
			unionfind[i] = i;
		}
	}

	public static void addEdge(int u, int v) {
		edgeNext[tcnt] = headEdge[u];
		edgeTo[tcnt] = v;
		headEdge[u] = tcnt++;
	}

	public static void addQuery(int u, int v, int i) {
		queryNext[qcnt] = headQuery[u];
		queryTo[qcnt] = v;
		queryIndex[qcnt] = i;
		headQuery[u] = qcnt++;
	}

	public static int find(int i) {
		if (i != unionfind[i]) {
			unionfind[i] = find(unionfind[i]);
		}
		return unionfind[i];
	}

	public static void tarjan(int u, int f) {
		visited[u] = true;
		for (int e = headEdge[u], v; e != 0; e = edgeNext[e]) {
			v = edgeTo[e];
			if (v != f) {
				tarjan(v, u);
			}
		}
		for (int e = headQuery[u], v; e != 0; e = queryNext[e]) {
			v = queryTo[e];
			if (visited[v]) {
				ans[queryIndex[e]] = find(v);
			}
		}
		unionfind[u] = f;
		father[u] = f;
	}

	public static void dfs(int u, int f) {
		for (int e = headEdge[u], v; e != 0; e = edgeNext[e]) {
			v = edgeTo[e];
			if (v != f) {
				dfs(v, u);
			}
		}
		for (int e = headEdge[u], v; e != 0; e = edgeNext[e]) {
			v = edgeTo[e];
			if (v != f) {
				num[u] += num[v];
			}
		}
	}

	public static int no, yes;

	public static void dp(int u, int f) {
		int n = price[u] * num[u];
		int y = (price[u] / 2) * num[u];
		for (int e = headEdge[u], v; e != 0; e = edgeNext[e]) {
			v = edgeTo[e];
			if (v != f) {
				dp(v, u);
				n += Math.min(no, yes);
				y += no;
			}
		}
		no = n;
		yes = y;
	}

}