package code;

// 验证搜索二叉树树
// 测试链接 : https://leetcode.cn/problems/validate-binary-search-tree/
public class Video_036_6_ValidateBinarySearchTree {

	// 不提交这个类
	public static class TreeNode {
		public int val;
		public TreeNode left;
		public TreeNode right;
	}

	// 提交以下的方法
	public static long min, max;

	public static boolean isValidBST(TreeNode head) {
		if (head == null) {
			min = Long.MAX_VALUE;
			max = Long.MIN_VALUE;
			return true;
		}
		boolean leftBalanced = isValidBST(head.left);
		long lmin = min;
		long lmax = max;
		boolean rightBalanced = isValidBST(head.right);
		long rmin = min;
		long rmax = max;
		min = Math.min(Math.min(lmin, rmin), head.val);
		max = Math.max(Math.max(lmax, rmax), head.val);
		return leftBalanced && rightBalanced && lmax < head.val && head.val < rmin;
	}

}
