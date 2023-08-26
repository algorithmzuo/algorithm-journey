package class049;

// 给定一个长度为n的二维数组graph，代表一张图
// graph[i] = {a,b,c,d} 表示i讨厌(a,b,c,d)，讨厌关系为双向的
// 一共有n个人，编号0~n-1
// 讨厌的人不能一起开会
// 返回所有人能不能分成两组开会
// 测试链接 : https://leetcode.cn/problems/is-graph-bipartite/
public class Code03_IsGraphBipartite {

	public static boolean isBipartite(int[][] graph) {
		int n = graph.length;
		build(n);
		for (int[] neighbours : graph) {
			for (int i = 1; i < neighbours.length; i++) {
				union(neighbours[i - 1], neighbours[i]);
			}
		}
		for (int i = 0; i < n; i++) {
			for (int j : graph[i]) {
				if (isSameSet(i, j)) {
					return false;
				}
			}
		}
		return true;
	}

	public static int MAXN = 101;

	public static int[] father = new int[MAXN];

	public static int[] size = new int[MAXN];

	public static int[] stack = new int[MAXN];

	public static void build(int n) {
		for (int i = 0; i < n; i++) {
			father[i] = i;
			size[i] = 1;
		}
	}

	public static int find(int i) {
		int size = 0;
		while (i != father[i]) {
			stack[size++] = i;
			i = father[i];
		}
		while (size > 0) {
			father[stack[--size]] = i;
		}
		return i;
	}

	public static boolean isSameSet(int i, int j) {
		return find(i) == find(j);
	}

	public static void union(int x, int y) {
		int fx = find(x);
		int fy = find(y);
		if (fx != fy) {
			if (size[fx] >= size[fy]) {
				father[fy] = fx;
				size[fx] = size[fx] + size[fy];
			} else {
				father[fx] = fy;
				size[fy] = size[fx] + size[fy];
			}
		}
	}

}
