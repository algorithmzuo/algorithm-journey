package code;

// 修剪二叉搜索树
// 测试链接 : https://leetcode.cn/problems/trim-a-binary-search-tree/
public class Video_036_7_TrimBinarySearchTree {

	// 不提交这个类
	public static class TreeNode {
		public int val;
		public TreeNode left;
		public TreeNode right;
	}

	// 提交以下的方法
	public static TreeNode trimBST(TreeNode root, int low, int high) {
		if (root == null) {
			return null;
		}
		if (root.val < low) {
			return trimBST(root.right, low, high);
		}
		if (root.val > high) {
			return trimBST(root.left, low, high);
		}
		root.left = trimBST(root.left, low, high);
		root.right = trimBST(root.right, low, high);
		return root;
	}

}
