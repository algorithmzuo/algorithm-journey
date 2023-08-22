package class037;

// 普通二叉树上寻找两个节点的最近公共祖先
// 测试链接 : https://leetcode.cn/problems/lowest-common-ancestor-of-a-binary-tree/
public class Code01_LowestCommonAncestor {

	// 不提交这个类
	public static class TreeNode {
		public int val;
		public TreeNode left;
		public TreeNode right;
	}

	// 提交如下的方法
	public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
		if (root == null || root == p || root == q) {
			// 遇到空，或者p，或者q，直接返回
			return root;
		}
		TreeNode l = lowestCommonAncestor(root.left, p, q);
		TreeNode r = lowestCommonAncestor(root.right, p, q);
		if (l != null && r != null) {
			// 左树也搜到，右树也搜到，返回root
			return root;
		}
		if (l == null && r == null) {
			// 都没搜到返回空
			return null;
		}
		// l和r一个为空，一个不为空
		// 返回不空的那个
		return l != null ? l : r;
	}

}
