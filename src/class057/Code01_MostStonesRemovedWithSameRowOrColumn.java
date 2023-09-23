package class057;

import java.util.HashMap;

// 移除最多的同行或同列石头
// n 块石头放置在二维平面中的一些整数坐标点上。每个坐标点上最多只能有一块石头
// 如果一块石头的 同行或者同列 上有其他石头存在，那么就可以移除这块石头
// 给你一个长度为 n 的数组 stones ，其中 stones[i] = [xi, yi] 表示第 i 块石头的位置
// 返回 可以移除的石子 的最大数量。
// 测试链接 : https://leetcode.cn/problems/most-stones-removed-with-same-row-or-column/
public class Code01_MostStonesRemovedWithSameRowOrColumn {

	// key : 某行
	// value : 第一次遇到的石头编号
	public static HashMap<Integer, Integer> rowFirst = new HashMap<Integer, Integer>();

	public static HashMap<Integer, Integer> colFirst = new HashMap<Integer, Integer>();

	public static int MAXN = 1001;

	public static int[] father = new int[MAXN];

	public static int sets;

	public static void build(int n) {
		rowFirst.clear();
		colFirst.clear();
		for (int i = 0; i < n; i++) {
			father[i] = i;
		}
		sets = n;
	}

	public static int find(int i) {
		if (i != father[i]) {
			father[i] = find(father[i]);
		}
		return father[i];
	}

	public static void union(int x, int y) {
		int fx = find(x);
		int fy = find(y);
		if (fx != fy) {
			father[fx] = fy;
			sets--;
		}
	}

	public static int removeStones(int[][] stones) {
		int n = stones.length;
		build(n);
		for (int i = 0; i < n; i++) {
			int row = stones[i][0];
			int col = stones[i][1];
			if (!rowFirst.containsKey(row)) {
				rowFirst.put(row, i);
			} else {
				union(i, rowFirst.get(row));
			}
			if (!colFirst.containsKey(col)) {
				colFirst.put(col, i);
			} else {
				union(i, colFirst.get(col));
			}
		}
		return n - sets;
	}

}
