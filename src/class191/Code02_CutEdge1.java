package class191;

// 割边模版题2，java版
// 给定一张无向图，一共n个点、m条边，点的编号0~n-1
// 找出图中所有的割边，返回每条割边的两个端点
// 请保证原图即使有重边，答案依然正确
// 1 <= n、m <= 10^5
// 测试链接 : https://leetcode.cn/problems/critical-connections-in-a-network/
// 提交以下代码中的Solution类，可以通过所有测试用例

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Code02_CutEdge1 {

	class Solution {

		public static int MAXN = 100001;
		public static int MAXM = 100001;
		public static int n, m;
		public static int[] a = new int[MAXM];
		public static int[] b = new int[MAXM];

		public static int[] head = new int[MAXN];
		public static int[] nxt = new int[MAXM << 1];
		public static int[] to = new int[MAXM << 1];
		public static int cntg;

		public static int[] dfn = new int[MAXN];
		public static int[] low = new int[MAXN];
		public static int cntd;

		public static boolean[] cutEdge = new boolean[MAXM];

		public static void prepare() {
			cntg = 1;
			cntd = 0;
			for (int i = 1; i <= n; i++) {
				head[i] = dfn[i] = low[i] = 0;
			}
			for (int i = 1; i <= m; i++) {
				cutEdge[i] = false;
			}
		}

		public static void addEdge(int u, int v) {
			nxt[++cntg] = head[u];
			to[cntg] = v;
			head[u] = cntg;
		}

		public static void tarjan(int u, int preEdge) {
			dfn[u] = low[u] = ++cntd;
			for (int e = head[u]; e > 0; e = nxt[e]) {
				if ((e ^ 1) == preEdge) {
					continue;
				}
				int v = to[e];
				if (dfn[v] == 0) {
					tarjan(v, e);
					low[u] = Math.min(low[u], low[v]);
					if (low[v] > dfn[u]) {
						cutEdge[e >> 1] = true;
					}
				} else {
					low[u] = Math.min(low[u], dfn[v]);
				}
			}
		}

		public static List<List<Integer>> criticalConnections(int nodeCnt, List<List<Integer>> connections) {
			n = nodeCnt;
			m = connections.size();
			prepare();
			for (int i = 1, j = 0; i <= m; i++, j++) {
				a[i] = connections.get(j).get(0) + 1;
				b[i] = connections.get(j).get(1) + 1;
				addEdge(a[i], b[i]);
				addEdge(b[i], a[i]);
			}
			for (int i = 1; i <= n; i++) {
				if (dfn[i] == 0) {
					tarjan(i, 0);
				}
			}
			List<List<Integer>> ans = new ArrayList<>();
			for (int i = 1; i <= m; i++) {
				if (cutEdge[i]) {
					ans.add(Arrays.asList(a[i] - 1, b[i] - 1));
				}
			}
			return ans;
		}

	}

}
