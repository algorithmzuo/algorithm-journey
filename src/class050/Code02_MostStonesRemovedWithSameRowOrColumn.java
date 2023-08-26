package class050;

import java.util.HashMap;

// 移除最多的同行或同列石头
// n 块石头放置在二维平面中的一些整数坐标点上。每个坐标点上最多只能有一块石头
// 如果一块石头的 同行或者同列 上有其他石头存在，那么就可以移除这块石头
// 给你一个长度为 n 的数组 stones ，其中 stones[i] = [xi, yi] 表示第 i 块石头的位置
// 返回 可以移除的石子 的最大数量。
// 测试链接 : https://leetcode.cn/problems/most-stones-removed-with-same-row-or-column/
public class Code02_MostStonesRemovedWithSameRowOrColumn {

	public static int removeStones(int[][] stones) {
		int n = stones.length;
		HashMap<Integer, Integer> rowFirst = new HashMap<Integer, Integer>();
		HashMap<Integer, Integer> colFirst = new HashMap<Integer, Integer>();
		build(n);
		for (int i = 0; i < n; i++) {
			int x = stones[i][0];
			int y = stones[i][1];
			if (!rowFirst.containsKey(x)) {
				rowFirst.put(x, i);
			} else {
				union(i, rowFirst.get(x));
			}
			if (!colFirst.containsKey(y)) {
				colFirst.put(y, i);
			} else {
				union(i, colFirst.get(y));
			}
		}
		return n - sets;
	}

	public static int MAXN = 1001;

	public static int[] father = new int[MAXN];

	public static int[] size = new int[MAXN];

	public static int[] stack = new int[MAXN];

	public static int sets;

	public static void build(int n) {
		for (int i = 0; i < n; i++) {
			father[i] = i;
			size[i] = 1;
		}
		sets = n;
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
			sets--;
		}
	}

}
