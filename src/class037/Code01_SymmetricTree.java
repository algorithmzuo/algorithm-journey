package class037;

// 判断是否对称二叉树
// 测试链接 : https://leetcode.cn/problems/symmetric-tree/
public class Code01_SymmetricTree {

	// 不提交这个类
	public static class TreeNode {
		public int val;
		public TreeNode left;
		public TreeNode right;
	}

	// 提交如下的方法
	public boolean isSymmetric(TreeNode root) {
		return mirror(root, root);
	}

	public static boolean mirror(TreeNode head1, TreeNode head2) {
		if (head1 == null && head2 == null) {
			return true;
		}
		if (head1 == null || head2 == null) {
			return false;
		}
		return head1.val == head2.val && mirror(head1.left, head2.right) && mirror(head1.right, head2.left);
	}

}
