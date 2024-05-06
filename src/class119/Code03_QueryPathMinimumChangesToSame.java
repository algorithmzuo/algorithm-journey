package class119;

import java.util.Arrays;

// 边权相等的最小修改次数
// 一棵树有n个节点，编号0 ~ n-1，每条边(u,v,w)表示从u到v有一条权重为w的边
// 一共有m条查询，每条查询(a,b)表示，a到b的最短路径中把所有边变成一种值需要修改几条边
// 返回每条查询的查询结果
// 1 <= n <= 10^4
// 1 <= m <= 2 * 10^4
// 0 <= u、v、a、b < n
// 1 <= w <= 26
// 测试链接 : https://leetcode.cn/problems/minimum-edge-weight-equilibrium-queries-in-a-tree/
public class Code03_QueryPathMinimumChangesToSame {

	public static int MAXN = 10001;

	public static int MAXM = 20001;

	public static int MAXW = 26;

	// 链式前向星建图
	public static int[] headEdge = new int[MAXN];

	public static int[] edgeNext = new int[MAXN << 1];

	public static int[] edgeTo = new int[MAXN << 1];

	public static int[] edgeValue = new int[MAXN << 1];

	public static int tcnt;

	// weightCnt[i][w] : 从头节点到i的路径中，权值为w的边有几条
	public static int[][] weightCnt = new int[MAXN][MAXW + 1];

	// 以下所有的结构都是为了tarjan算法做准备
	public static int[] headQuery = new int[MAXN];

	public static int[] queryNext = new int[MAXM << 1];

	public static int[] queryTo = new int[MAXM << 1];

	public static int[] queryIndex = new int[MAXM << 1];

	public static int qcnt;

	public static boolean[] visited = new boolean[MAXN];

	public static int[] father = new int[MAXN];

	public static int[] lca = new int[MAXM];

	public static int[] minOperationsQueries(int n, int[][] edges, int[][] queries) {
		build(n);
		for (int[] edge : edges) {
			addEdge(edge[0], edge[1], edge[2]);
			addEdge(edge[1], edge[0], edge[2]);
		}
		// 从头节点到每个节点的边权值词频统计
		dfs(0, 0, -1);
		int m = queries.length;
		for (int i = 0; i < m; i++) {
			addQuery(queries[i][0], queries[i][1], i);
			addQuery(queries[i][1], queries[i][0], i);
		}
		// 得到每个查询的最低公共祖先
		tarjan(0, -1);
		int[] ans = new int[m];
		for (int i = 0, a, b, c; i < m; i++) {
			a = queries[i][0];
			b = queries[i][1];
			c = lca[i];
			int allCnt = 0; // 从a到b的路，所有权值的边一共多少条
			int maxCnt = 0; // 从a到b的路，权值重复最多的次数
			for (int w = 1, wcnt; w <= MAXW; w++) { // 所有权值枚举一遍
				wcnt = weightCnt[a][w] + weightCnt[b][w] - 2 * weightCnt[c][w];
				maxCnt = Math.max(maxCnt, wcnt);
				allCnt += wcnt;
			}
			ans[i] = allCnt - maxCnt;
		}
		return ans;
	}

	public static void build(int n) {
		tcnt = qcnt = 1;
		Arrays.fill(headEdge, 0, n, 0);
		Arrays.fill(headQuery, 0, n, 0);
		Arrays.fill(visited, 0, n, false);
		for (int i = 0; i < n; i++) {
			father[i] = i;
		}
	}

	public static void addEdge(int u, int v, int w) {
		edgeNext[tcnt] = headEdge[u];
		edgeTo[tcnt] = v;
		edgeValue[tcnt] = w;
		headEdge[u] = tcnt++;
	}

	// 当前来到u节点，父亲节点f，从f到u权重为w
	// 统计从头节点到u节点，每种权值的边有多少条
	// 信息存放在weightCnt[u][1..26]里
	public static void dfs(int u, int w, int f) {
		if (u == 0) {
			Arrays.fill(weightCnt[u], 0);
		} else {
			for (int i = 1; i <= MAXW; i++) {
				weightCnt[u][i] = weightCnt[f][i];
			}
			weightCnt[u][w]++;
		}
		for (int e = headEdge[u]; e != 0; e = edgeNext[e]) {
			if (edgeTo[e] != f) {
				dfs(edgeTo[e], edgeValue[e], u);
			}
		}
	}

	public static void addQuery(int u, int v, int i) {
		queryNext[qcnt] = headQuery[u];
		queryTo[qcnt] = v;
		queryIndex[qcnt] = i;
		headQuery[u] = qcnt++;
	}

	// tarjan算法批量查询两点的最低公共祖先
	public static void tarjan(int u, int f) {
		visited[u] = true;
		for (int e = headEdge[u]; e != 0; e = edgeNext[e]) {
			if (edgeTo[e] != f) {
				tarjan(edgeTo[e], u);
			}
		}
		for (int e = headQuery[u], v; e != 0; e = queryNext[e]) {
			v = queryTo[e];
			if (visited[v]) {
				lca[queryIndex[e]] = find(v);
			}
		}
		father[u] = f;
	}

	public static int find(int i) {
		if (i != father[i]) {
			father[i] = find(father[i]);
		}
		return father[i];
	}

}
