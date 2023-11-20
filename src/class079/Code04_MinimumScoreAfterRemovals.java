package class079;

import java.util.ArrayList;
import java.util.Arrays;

// 测试链接 : https://leetcode.cn/problems/minimum-score-after-removals-on-a-tree/
public class Code04_MinimumScoreAfterRemovals {

	public static int MAXN = 1001;

	public static int[] dfn = new int[MAXN];

	public static int[] xor = new int[MAXN];

	public static int[] size = new int[MAXN];

	public static int cnt;

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
		cnt = 1;
		f(nums, graph, 0);
		int ans = Integer.MAX_VALUE, m = edges.length, cut1, cut2, pre, pos, part1, part2, part3, max, min;
		for (int i = 0, a, b, c, d; i < m; i++) {
			a = edges[i][0];
			b = edges[i][1];
			cut1 = dfn[a] < dfn[b] ? b : a;
			for (int j = i + 1; j < m; j++) {
				c = edges[j][0];
				d = edges[j][1];
				cut2 = dfn[c] < dfn[d] ? d : c;
				pre = dfn[cut1] < dfn[cut2] ? cut1 : cut2;
				pos = pre == cut1 ? cut2 : cut1;
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
		dfn[cur] = cnt++;
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
