package class079;

import java.util.ArrayList;
import java.util.Arrays;

// 从树中删除边的最小分数
// 存在一棵无向连通树，树中有编号从0到n-1的n个节点，以及n-1条边
// 给你一个下标从0开始的整数数组nums长度为n，其中nums[i]表示第i个节点的值
// 另给你一个二维整数数组edges长度为n-1
// 其中 edges[i] = [ai, bi] 表示树中存在一条位于节点 ai 和 bi 之间的边
// 删除树中两条不同的边以形成三个连通组件
// 对于一种删除边方案，定义如下步骤以计算其分数：
// 分别获取三个组件每个组件中所有节点值的异或值
// 最大 异或值和 最小 异或值的 差值 就是这一种删除边方案的分数
// 测试链接 : https://leetcode.cn/problems/minimum-score-after-removals-on-a-tree/
public class Code04_MinimumScoreAfterRemovals {

	public static int MAXN = 1001;

	public static int[] dfn = new int[MAXN];

	public static int[] xor = new int[MAXN];

	public static int[] size = new int[MAXN];

	public static int dfnCnt;

	public static int minimumScore(int[] nums, int[][] edges) {
		int n = nums.length;
		ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			graph.add(new ArrayList<>());
		}
		for (int[] edge : edges) {
			graph.get(edge[0]).add(edge[1]);
			graph.get(edge[1]).add(edge[0]);
		}
		Arrays.fill(dfn, 0, n, 0);
		dfnCnt = 0;
		f(nums, graph, 0);
		int ans = Integer.MAX_VALUE, m = edges.length, h1, h2, pre, pos, part1, part2, part3, max, min;
		for (int i = 0, a, b, c, d; i < m; i++) {
			a = edges[i][0];
			b = edges[i][1];
			h1 = dfn[a] > dfn[b] ? a : b;
			for (int j = i + 1; j < m; j++) {
				c = edges[j][0];
				d = edges[j][1];
				h2 = dfn[c] > dfn[d] ? c : d;
				if (dfn[h1] < dfn[h2]) {
					pre = h1;
					pos = h2;
				} else {
					pre = h2;
					pos = h1;
				}
				part1 = xor[pos];
				if (dfn[pos] < dfn[pre] + size[pre]) {
					part2 = xor[pre] ^ xor[pos];
					part3 = xor[0] ^ xor[pre];
				} else {
					part2 = xor[pre];
					part3 = xor[0] ^ part1 ^ part2;
				}
				max = Math.max(Math.max(part1, part2), part3);
				min = Math.min(Math.min(part1, part2), part3);
				ans = Math.min(ans, max - min);
			}
		}
		return ans;
	}

	public static void f(int[] nums, ArrayList<ArrayList<Integer>> graph, int cur) {
		dfn[cur] = ++dfnCnt;
		xor[cur] = nums[cur];
		size[cur] = 1;
		for (int next : graph.get(cur)) {
			if (dfn[next] == 0) {
				f(nums, graph, next);
				xor[cur] ^= xor[next];
				size[cur] += size[next];
			}
		}
	}

}
