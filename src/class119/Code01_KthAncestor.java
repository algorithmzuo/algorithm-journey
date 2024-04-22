package class119;

import java.util.Arrays;

// 树节点的第K个祖先
// 树上有n个节点，编号0 ~ n-1，树的结构用parent数组代表
// 其中parent[i]是节点i的父节点，树的根节点是编号为0
// 树节点i的第k个祖先节点，是从节点i开始往上跳k步所来到的节点
// 实现TreeAncestor类
// TreeAncestor(int n, int[] parent) : 初始化
// getKthAncestor(int i, int k) : 返回节点i的第k个祖先节点，不存在返回-1
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
			if (u == 0) {
				deep[u] = 1;
			} else {
				deep[u] = deep[f] + 1;
			}
			stfa[u][0] = f;
			for (int p = 1; (1 << p) <= deep[u]; p++) {
				stfa[u][p] = stfa[stfa[u][p - 1]][p - 1];
			}
			for (int e = head[u]; e != 0; e = next[e]) {
				dfs(to[e], u);
			}
		}

		public int getKthAncestor(int i, int k) {
			if (deep[i] <= k) {
				return -1;
			}
			int aimDeep = deep[i] - k;
			for (int p = power; p >= 0; p--) {
				if (deep[stfa[i][p]] >= aimDeep) {
					i = stfa[i][p];
				}
			}
			return i;
		}

	}

}
