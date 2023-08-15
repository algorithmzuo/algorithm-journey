package code;

// 求二叉树的最大、最小深度
public class Video_036_4_DepthOfBinaryTree {

	// 不提交这个类
	public static class TreeNode {
		public int val;
		public TreeNode left;
		public TreeNode right;
	}

	// 测试链接 : https://leetcode.cn/problems/maximum-depth-of-binary-tree/
	public static int maxDepth(TreeNode root) {
		return root == null ? 0 : Math.max(maxDepth(root.left), maxDepth(root.right)) + 1;
	}

	// 测试链接 : https://leetcode.cn/problems/minimum-depth-of-binary-tree/
	public static int minDepth(TreeNode root) {
		if (root == null) {
			return 0;
		}
		if (root.left == null && root.right == null) {
			return 1;
		}
		int ans = Integer.MAX_VALUE;
		if (root.left != null) {
			ans = Math.min(ans, minDepth(root.left));
		}
		if (root.right != null) {
			ans = Math.min(ans, minDepth(root.right));
		}
		return ans + 1;
	}

}
