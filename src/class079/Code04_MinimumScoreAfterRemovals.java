package class079;

import java.util.ArrayList;
import java.util.Arrays;

// 从树中删除边的最小分数
// 存在一棵无向连通树，树中有编号从0到n-1的n个节点，以及n-1条边
// 给你一个下标从0开始的整数数组nums长度为n，其中nums[i]表示第i个节点的值
// 另给你一个二维整数数组edges长度为n-1
// 其中 edges[i] = [ai, bi] 表示树中存在一条位于节点 ai 和 bi 之间的边
// 删除树中两条不同的边以形成三个连通组件，对于一种删除边方案，定义如下步骤以计算其分数：
// 分别获取三个组件每个组件中所有节点值的异或值
// 最大 异或值和 最小 异或值的 差值 就是这种删除边方案的分数
// 返回可能的最小分数
// 测试链接 : https://leetcode.cn/problems/minimum-score-after-removals-on-a-tree/
public class Code04_MinimumScoreAfterRemovals {

	public static int MAXN = 1001;

	// 下标为原始节点编号
	public static int[] dfn = new int[MAXN];

	// 下标为dfn序号
	public static int[] xor = new int[MAXN];

	// 下标为dfn序号
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
		int m = edges.length;
		int ans = Integer.MAX_VALUE;
		for (int i = 0, a, b, pre, pos, sum1, sum2, sum3; i < m; i++) {
			a = Math.max(dfn[edges[i][0]], dfn[edges[i][1]]);
			for (int j = i + 1; j < m; j++) {
				b = Math.max(dfn[edges[j][0]], dfn[edges[j][1]]);
				if (a < b) {
					pre = a;
					pos = b;
				} else {
					pre = b;
					pos = a;
				}
				sum1 = xor[pos];
				// xor[1] : 整棵树的异或和
				// 因为头节点是0，一定拥有最小的dfn序号1
				// f函数调用的时候，也是从0节点开始的
				if (pos < pre + size[pre]) {
					sum2 = xor[pre] ^ xor[pos];
					sum3 = xor[1] ^ xor[pre];
				} else {
					sum2 = xor[pre];
					sum3 = xor[1] ^ sum1 ^ sum2;
				}
				ans = Math.min(ans, Math.max(Math.max(sum1, sum2), sum3) - Math.min(Math.min(sum1, sum2), sum3));
			}
		}
		return ans;
	}

	// 当前来到原始编号u，遍历u的整棵树
	public static void f(int[] nums, ArrayList<ArrayList<Integer>> graph, int u) {
		int i = ++dfnCnt;
		dfn[u] = i;
		xor[i] = nums[u];
		size[i] = 1;
		for (int v : graph.get(u)) {
			if (dfn[v] == 0) {
				f(nums, graph, v);
				xor[i] ^= xor[dfn[v]];
				size[i] += size[dfn[v]];
			}
		}
	}

}
