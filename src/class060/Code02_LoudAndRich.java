package class060;

import java.util.ArrayList;

// 喧闹和富有
// 从 0 到 n - 1 编号，其中每个人都有不同数目的钱，以及不同程度的安静值
// 给你一个数组richer，其中richer[i] = [ai, bi] 表示 
// person ai 比 person bi 更有钱
// 还有一个整数数组 quiet ，其中 quiet[i] 是 person i 的安静值
// richer 中所给出的数据 逻辑自洽
// 也就是说，在 person x 比 person y 更有钱的同时，不会出现
// person y 比 person x 更有钱的情况
// 现在，返回一个整数数组 answer 作为答案，其中 answer[x] = y 的前提是,
// 在所有拥有的钱肯定不少于 person x 的人中，
// person y 是最安静的人（也就是安静值 quiet[y] 最小的人）。
// 测试链接 : https://leetcode.cn/problems/loud-and-rich/
public class Code02_LoudAndRich {

	public static int[] loudAndRich(int[][] richer, int[] quiet) {
		int n = quiet.length;
		ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			graph.add(new ArrayList<>());
		}
		int[] indegree = new int[n];
		for (int[] r : richer) {
			graph.get(r[0]).add(r[1]);
			indegree[r[1]]++;
		}
		int[] queue = new int[n];
		int l = 0;
		int r = 0;
		for (int i = 0; i < n; i++) {
			if (indegree[i] == 0) {
				queue[r++] = i;
			}
		}
		int[] ans = new int[n];
		for (int i = 0; i < n; i++) {
			ans[i] = i;
		}
		while (l < r) {
			int cur = queue[l++];
			for (int next : graph.get(cur)) {
				if (quiet[ans[cur]] < quiet[ans[next]] ) {
					ans[next] = ans[cur];
				}
				if (--indegree[next] == 0) {
					queue[r++] = next;
				}
			}
		}
		return ans;
	}

}
