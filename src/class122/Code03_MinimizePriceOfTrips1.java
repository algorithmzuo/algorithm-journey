package class122;

import java.util.Arrays;

// 最小化旅行的价格总和(倍增方法求lca)
// 有n个节点形成一棵树，每个节点上有点权，再给定很多路径
// 每条路径有开始点和结束点，路径代价就是从开始点到结束点的点权和
// 所有路径的代价总和就是旅行的价格总和
// 你可以选择把某些点的点权减少一半，来降低旅行的价格总和
// 但是要求选择的点不能相邻
// 返回旅行的价格总和最少能是多少
// 测试链接 : https://leetcode.cn/problems/minimize-the-total-price-of-the-trips/
public class Code03_MinimizePriceOfTrips1 {

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
		dfs1(1, 0);
		int u, v, lca, lcafather;
		for (int[] trip : ts) {
			u = trip[0] + 1;
			v = trip[1] + 1;
			lca = lca(u, v);
			lcafather = stjump[lca][0];
			num[u]++;
			num[v]++;
			num[lca]--;
			num[lcafather]--;
		}
		dfs2(1, 0);
		dp(1, 0);
		return Math.min(no, yes);
	}

	public static int MAXN = 51;

	public static int LIMIT = 6;

	public static int power;

	public static int log2(int n) {
		int ans = 0;
		while ((1 << ans) <= (n >> 1)) {
			ans++;
		}
		return ans;
	}

	public static int[] price = new int[MAXN];

	public static int[] num = new int[MAXN];

	public static int[] head = new int[MAXN];

	public static int[] next = new int[MAXN << 1];

	public static int[] to = new int[MAXN << 1];

	public static int cnt;

	public static int[] deep = new int[MAXN];

	public static int[][] stjump = new int[MAXN][LIMIT];

	public static void build(int n) {
		power = log2(n);
		Arrays.fill(num, 1, n + 1, 0);
		cnt = 1;
		Arrays.fill(head, 1, n + 1, 0);
	}

	public static void addEdge(int u, int v) {
		next[cnt] = head[u];
		to[cnt] = v;
		head[u] = cnt++;
	}

	public static void dfs1(int u, int f) {
		deep[u] = deep[f] + 1;
		stjump[u][0] = f;
		for (int p = 1; p <= power; p++) {
			stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
		}
		for (int e = head[u]; e != 0; e = next[e]) {
			if (to[e] != f) {
				dfs1(to[e], u);
			}
		}
	}

	public static int lca(int a, int b) {
		if (deep[a] < deep[b]) {
			int tmp = a;
			a = b;
			b = tmp;
		}
		for (int p = power; p >= 0; p--) {
			if (deep[stjump[a][p]] >= deep[b]) {
				a = stjump[a][p];
			}
		}
		if (a == b) {
			return a;
		}
		for (int p = power; p >= 0; p--) {
			if (stjump[a][p] != stjump[b][p]) {
				a = stjump[a][p];
				b = stjump[b][p];
			}
		}
		return stjump[a][0];
	}

	public static void dfs2(int u, int f) {
		for (int e = head[u], v; e != 0; e = next[e]) {
			v = to[e];
			if (v != f) {
				dfs2(v, u);
			}
		}
		for (int e = head[u], v; e != 0; e = next[e]) {
			v = to[e];
			if (v != f) {
				num[u] += num[v];
			}
		}
	}

	public static int no, yes;

	public static void dp(int u, int f) {
		int n = price[u] * num[u];
		int y = (price[u] / 2) * num[u];
		for (int e = head[u], v; e != 0; e = next[e]) {
			v = to[e];
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