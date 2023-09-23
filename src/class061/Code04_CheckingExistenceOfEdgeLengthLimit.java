package class061;

import java.util.Arrays;

// 检查边长度限制的路径是否存在
// 给你一个 n 个点组成的无向图边集 edgeList
// 其中 edgeList[i] = [ui, vi, disi] 表示点 ui 和点 vi 之间有一条长度为 disi 的边
// 请注意，两个点之间可能有 超过一条边 。
// 给你一个查询数组queries ，其中 queries[j] = [pj, qj, limitj]
// 你的任务是对于每个查询 queries[j] ，判断是否存在从 pj 到 qj 的路径
// 且这条路径上的每一条边都 严格小于 limitj 。
// 请你返回一个 布尔数组 answer ，其中 answer.length == queries.length
// 当 queries[j] 的查询结果为 true 时， answer 第 j 个值为 true ，否则为 false
// 测试链接 : https://leetcode.cn/problems/checking-existence-of-edge-length-limited-paths/
public class Code04_CheckingExistenceOfEdgeLengthLimit {

	public static boolean[] distanceLimitedPathsExist(int n, int[][] edges, int[][] queries) {
		Arrays.sort(edges, (a, b) -> a[2] - b[2]);
		int m = edges.length;
		int k = queries.length;
		for (int i = 0; i < k; i++) {
			questions[i][0] = queries[i][0];
			questions[i][1] = queries[i][1];
			questions[i][2] = queries[i][2];
			questions[i][3] = i;
		}
		Arrays.sort(questions, 0, k, (a, b) -> a[2] - b[2]);
		build(n);
		boolean[] ans = new boolean[k];
		for (int i = 0, j = 0; i < k; i++) {
			// i : 问题编号
			// j : 边的编号
			for (; j < m && edges[j][2] < questions[i][2]; j++) {
				union(edges[j][0], edges[j][1]);
			}
			ans[questions[i][3]] = isSameSet(questions[i][0], questions[i][1]);
		}
		return ans;
	}

	public static int MAXN = 100001;

	public static int[][] questions = new int[MAXN][4];

	public static int[] father = new int[MAXN];

	public static void build(int n) {
		for (int i = 0; i < n; i++) {
			father[i] = i;
		}
	}

	public static int find(int i) {
		if (i != father[i]) {
			father[i] = find(father[i]);
		}
		return father[i];
	}

	public static boolean isSameSet(int x, int y) {
		return find(x) == find(y);
	}

	public static void union(int x, int y) {
		father[find(x)] = find(y);
	}

}
