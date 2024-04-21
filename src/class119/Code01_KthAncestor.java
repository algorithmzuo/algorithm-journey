package class119;

import java.util.Arrays;

// 测试链接 : https://leetcode.cn/problems/kth-ancestor-of-a-tree-node/
public class Code01_KthAncestor {

	class TreeAncestor {

		public static int MAXN = 50001;

		public static int LIMIT = 16;

		public static int step;

		public static int cnt;

		public static int[] head = new int[MAXN];

		public static int[] next = new int[MAXN];

		public static int[] to = new int[MAXN];

		public static int[][] stfa = new int[MAXN][LIMIT];

		public static int[] deep = new int[MAXN];

		public TreeAncestor(int n, int[] parent) {
			step = log2(n);
			cnt = 1;
			Arrays.fill(head, 0, n, 0);
			deep[0] = 0;
			for (int i = 1; i < parent.length; i++) {
				addEdge(parent[i], i);
			}
			dfs(0, 0);
		}

		public static int log2(int n) {
			int ans = 0;
			while ((1 << ans) <= (n >> 1)) {
				ans++;
			}
			return ans;
		}

		public static void addEdge(int u, int v) {
			next[cnt] = head[u];
			to[cnt] = v;
			head[u] = cnt++;
		}

		public static void dfs(int u, int f) {
			deep[u] = deep[f] + 1;
			stfa[u][0] = f;
			for (int s = 1; (1 << s) <= deep[u]; s++) {
				stfa[u][s] = stfa[stfa[u][s - 1]][s - 1];
			}
			for (int e = head[u]; e != 0; e = next[e]) {
				dfs(to[e], u);
			}
		}

		public int getKthAncestor(int node, int k) {
			if (deep[node] <= k) {
				return -1;
			}
			int aimDeep = deep[node] - k;
			for (int s = step; s >= 0; s--) {
				if (deep[stfa[node][s]] >= aimDeep) {
					node = stfa[node][s];
				}
			}
			return node;
		}

	}

}
