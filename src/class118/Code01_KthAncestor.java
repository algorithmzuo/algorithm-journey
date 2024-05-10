package class118;

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

		// 根据节点个数n，计算出2的几次方就够用了
		public static int power;

		public static int log2(int n) {
			int ans = 0;
			while ((1 << ans) <= (n >> 1)) {
				ans++;
			}
			return ans;
		}

		// 链式前向星建图
		public static int[] head = new int[MAXN];

		public static int[] next = new int[MAXN];

		public static int[] to = new int[MAXN];

		public static int cnt;

		// deep[i] : 节点i在第几层
		public static int[] deep = new int[MAXN];

		// stjump[i][p] : 节点i往上跳2的p次方步，到达的节点编号
		public static int[][] stjump = new int[MAXN][LIMIT];

		public TreeAncestor(int n, int[] parent) {
			power = log2(n);
			cnt = 1;
			Arrays.fill(head, 0, n, 0);
			for (int i = 1; i < parent.length; i++) {
				addEdge(parent[i], i);
			}
			dfs(0, 0);
		}

		public static void addEdge(int u, int v) {
			next[cnt] = head[u];
			to[cnt] = v;
			head[u] = cnt++;
		}

		// 当前来到i节点，i节点父亲节点是f
		public static void dfs(int i, int f) {
			if (i == 0) {
				deep[i] = 1;
			} else {
				deep[i] = deep[f] + 1;
			}
			stjump[i][0] = f;
			for (int p = 1; p <= power; p++) {
				stjump[i][p] = stjump[stjump[i][p - 1]][p - 1];
			}
			for (int e = head[i]; e != 0; e = next[e]) {
				dfs(to[e], i);
			}
		}

		public int getKthAncestor(int i, int k) {
			if (deep[i] <= k) {
				return -1;
			}
			// s是想要去往的层数
			int s = deep[i] - k;
			for (int p = power; p >= 0; p--) {
				if (deep[stjump[i][p]] >= s) {
					i = stjump[i][p];
				}
			}
			return i;
		}

	}

}
