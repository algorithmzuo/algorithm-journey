package class119;

import java.util.Arrays;

// 边权相等的最小修改次数
// 一棵树有n个节点，编号0 ~ n-1，每条边(u,v,w)表示从u到v有一条权重为w的边
// 一共有m条查询，每条查询(a,b)表示，a到b的最短路径中把所有边变成一种值需要修改几条边
// 返回每条查询的查询结果
// 1 <= n <= 10^4，1 <= m <= 2 * 10^4
// 0 <= u,v,a,b < n，1 <= w <= 26
// 测试链接 : https://leetcode.cn/problems/minimum-edge-weight-equilibrium-queries-in-a-tree/
public class Code03_QueryPathMinimumChangesToSame {

	public static int MAXN = 10001;

	public static int MAXM = 20001;

	public static int MAXW = 26;

	public static int[] treeHead = new int[MAXN];

	public static int[] treeNext = new int[MAXN << 1];

	public static int[] treeTo = new int[MAXN << 1];

	public static int[] treeValue = new int[MAXN << 1];

	public static int tcnt;

	public static int[] queryHead = new int[MAXN];

	public static int[] queryNext = new int[MAXM << 1];

	public static int[] queryTo = new int[MAXM << 1];

	public static int[] queryIndex = new int[MAXM << 1];

	public static int qcnt;

	public static int[][] cnts = new int[MAXN][MAXW + 1];

	public static boolean[] visited = new boolean[MAXN];

	public static int[] father = new int[MAXN];

	public static int[] lca = new int[MAXM];

	public int[] minOperationsQueries(int n, int[][] edges, int[][] queries) {
		build(n);
		for (int[] edge : edges) {
			addEdge(edge[0], edge[1], edge[2]);
			addEdge(edge[1], edge[0], edge[2]);
		}
		int m = queries.length;
		for (int i = 0; i < m; i++) {
			addQuery(queries[i][0], queries[i][1], i);
			addQuery(queries[i][1], queries[i][0], i);
		}
		tarjan(0, 0, -1);
		int[] ans = new int[m];
		for (int i = 0; i < m; i++) {
			int allCnt = 0, maxCnt = 0, pathCnt;
			for (int j = 1; j <= MAXW; j++) {
				// 特别重要的结论
				// 很多题可以用到
				pathCnt = cnts[queries[i][0]][j] + cnts[queries[i][1]][j] - 2 * cnts[lca[i]][j];
				maxCnt = Math.max(maxCnt, pathCnt);
				allCnt += pathCnt;
			}
			ans[i] = allCnt - maxCnt;
		}
		return ans;
	}

	public static void build(int n) {
		tcnt = qcnt = 1;
		Arrays.fill(treeHead, 0, n, 0);
		Arrays.fill(queryHead, 0, n, 0);
		Arrays.fill(visited, 0, n, false);
		for (int i = 0; i < n; i++) {
			father[i] = i;
		}
	}

	public static void addEdge(int u, int v, int w) {
		treeNext[tcnt] = treeHead[u];
		treeTo[tcnt] = v;
		treeValue[tcnt] = w;
		treeHead[u] = tcnt++;
	}

	public static void addQuery(int u, int v, int i) {
		queryNext[qcnt] = queryHead[u];
		queryTo[qcnt] = v;
		queryIndex[qcnt] = i;
		queryHead[u] = qcnt++;
	}

	public static int find(int i) {
		if (i != father[i]) {
			father[i] = find(father[i]);
		}
		return father[i];
	}

	public void tarjan(int u, int w, int f) {
		visited[u] = true;
		if (u == 0) {
			Arrays.fill(cnts[u], 0);
		} else {
			for (int i = 1; i <= MAXW; i++) {
				cnts[u][i] = cnts[f][i];
			}
			cnts[u][w]++;
		}
		for (int e = treeHead[u]; e != 0; e = treeNext[e]) {
			if (treeTo[e] != f) {
				tarjan(treeTo[e], treeValue[e], u);
			}
		}
		for (int e = queryHead[u], v; e != 0; e = queryNext[e]) {
			v = queryTo[e];
			if (visited[v]) {
				lca[queryIndex[e]] = find(v);
			}
		}
		father[u] = f;
	}

}
