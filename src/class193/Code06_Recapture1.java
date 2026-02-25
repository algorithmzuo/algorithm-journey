package class193;

// 夺回据点，java版
// 测试链接 : https://leetcode.cn/problems/s5kipK/
// 提交以下代码中的Solution类，可以通过所有测试用例

import java.util.ArrayList;
import java.util.List;

public class Code06_Recapture1 {

	class Solution {

		public static int MAXN = 100001;
		public static int MAXM = 100001;
		public static int n, m;

		public static int[] head = new int[MAXN];
		public static int[] nxt = new int[MAXM << 1];
		public static int[] to = new int[MAXM << 1];
		public static int cntg;

		public static int[] dfn = new int[MAXN];
		public static int[] low = new int[MAXN];
		public static int cntd;

		public static int[] sta = new int[MAXN];
		public static int top;

		public static boolean[] cutVertex = new boolean[MAXN];
		public static List<List<Integer>> vbccArr = new ArrayList<>();

		public static void prepare() {
			cntg = cntd = top = 0;
			for (int i = 1; i <= n; i++) {
				head[i] = dfn[i] = low[i] = 0;
				cutVertex[i] = false;
			}
			vbccArr.clear();
		}

		public static void addEdge(int u, int v) {
			nxt[++cntg] = head[u];
			to[cntg] = v;
			head[u] = cntg;
		}

		public static void tarjan(int u, boolean root) {
			dfn[u] = low[u] = ++cntd;
			sta[++top] = u;
			int son = 0;
			for (int e = head[u]; e > 0; e = nxt[e]) {
				int v = to[e];
				if (dfn[v] == 0) {
					son++;
					tarjan(v, false);
					low[u] = Math.min(low[u], low[v]);
					if (low[v] == dfn[u]) {
						if (!root || son >= 2) {
							cutVertex[u] = true;
						}
						ArrayList<Integer> list = new ArrayList<>();
						list.add(u);
						int pop;
						do {
							pop = sta[top--];
							list.add(pop);
						} while (pop != v);
						vbccArr.add(list);
					}
				} else {
					low[u] = Math.min(low[u], dfn[v]);
				}
			}
		}

		public static long minimumCost(int[] cost, int[][] roads) {
			n = cost.length;
			m = roads.length;
			prepare();
			for (int i = 0, u, v; i < m; i++) {
				u = roads[i][0] + 1;
				v = roads[i][1] + 1;
				addEdge(u, v);
				addEdge(v, u);
			}
			for (int i = 1; i <= n; i++) {
				if (dfn[i] == 0) {
					tarjan(i, true);
				}
			}
			long ans = 0;
			if (vbccArr.size() == 1) {
				ans = Long.MAX_VALUE;
				for (int i = 0; i < n; i++) {
					ans = Math.min(ans, cost[i]);
				}
			} else {
				int maxVal = Integer.MIN_VALUE;
				for (List<Integer> vbcc : vbccArr) {
					int cut = 0;
					int val = Integer.MAX_VALUE;
					for (int cur : vbcc) {
						if (cutVertex[cur]) {
							cut++;
						} else {
							val = Math.min(val, cost[cur - 1]);
						}
					}
					if (cut == 1) {
						ans += val;
						maxVal = Math.max(maxVal, val);
					}
				}
				ans -= maxVal;
			}
			return ans;
		}

	}

}
