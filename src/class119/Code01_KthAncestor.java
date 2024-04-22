package class119;

import java.util.Arrays;

// 测试链接 : https://leetcode.cn/problems/kth-ancestor-of-a-tree-node/
public class Code01_KthAncestor {

	class TreeAncestor {

		public static int MAXN = 50001;

		public static int LIMIT = 16;

		public static int power;

		public static int cnt;

		public static int[] head = new int[MAXN];

		public static int[] next = new int[MAXN];

		public static int[] to = new int[MAXN];

		public static int[][] stfa = new int[MAXN][LIMIT];

		public static int[] deep = new int[MAXN];

		public TreeAncestor(int n, int[] parent) {
			power = log2(n);
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
			for (int p = 1; (1 << p) <= deep[u]; p++) {
				stfa[u][p] = stfa[stfa[u][p - 1]][p - 1];
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
			for (int p = power; p >= 0; p--) {
				if (deep[stfa[node][p]] >= aimDeep) {
					node = stfa[node][p];
				}
			}
			return node;
		}

	}

}
