package class079;

// 移除子树后的二叉树高度
// 给你一棵 二叉树 的根节点 root ，树中有 n 个节点
// 每个节点都可以被分配一个从 1 到 n 且互不相同的值
// 另给你一个长度为 m 的数组 queries
// 你必须在树上执行 m 个 独立 的查询，其中第 i 个查询你需要执行以下操作：
// 从树中 移除 以 queries[i] 的值作为根节点的子树
// 题目所用测试用例保证 queries[i] 不等于根节点的值
// 返回一个长度为 m 的数组 answer
// 其中 answer[i] 是执行第 i 个查询后树的高度
// 注意：
// 查询之间是独立的，所以在每个查询执行后，树会回到其初始状态
// 树的高度是从根到树中某个节点的 最长简单路径中的边数
// 测试链接 : https://leetcode.cn/problems/height-of-binary-tree-after-subtree-removal-queries/
public class Code03_HeightRemovalQueries {

	// 不要提交这个类
	public static class TreeNode {
		public int val;
		public TreeNode left;
		public TreeNode right;
	}

	// 提交如下的方法
	public static final int MAXN = 100010;

	public static int[] dfn = new int[MAXN];

	public static int[] deep = new int[MAXN];

	public static int[] size = new int[MAXN];

	public static int[] maxl = new int[MAXN];

	public static int[] maxr = new int[MAXN];

	public static int n;

	public static int[] treeQueries(TreeNode root, int[] queries) {
		n = 0;
		dfs(root, 0);
		for (int i = 1; i <= n; i++) {
			maxl[i] = Math.max(maxl[i - 1], deep[i]);
		}
		maxr[n + 1] = 0;
		for (int i = n; i >= 1; i--) {
			maxr[i] = Math.max(maxr[i + 1], deep[i]);
		}
		int m = queries.length;
		int[] ans = new int[m];
		for (int i = 0; i < m; i++) {
			int leftMax = maxl[dfn[queries[i]] - 1];
			int rightMax = maxr[dfn[queries[i]] + size[dfn[queries[i]]]];
			ans[i] = Math.max(leftMax, rightMax);
		}
		return ans;
	}

	public static void dfs(TreeNode head, int h) {
		int i = ++n;
		dfn[head.val] = i;
		deep[i] = h;
		size[i] = 1;
		if (head.left != null) {
			dfs(head.left, h + 1);
			size[i] += size[dfn[head.left.val]];
		}
		if (head.right != null) {
			dfs(head.right, h + 1);
			size[i] += size[dfn[head.right.val]];
		}
	}

}
