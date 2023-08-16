package class037;

// 修剪搜索二叉树
// 测试链接 : https://leetcode.cn/problems/trim-a-binary-search-tree/
public class Code06_TrimBinarySearchTree {

	// 不提交这个类
	public static class TreeNode {
		public int val;
		public TreeNode left;
		public TreeNode right;
	}

	// 提交以下的方法
	// [low, high]
	public static TreeNode trimBST(TreeNode cur, int low, int high) {
		if (cur == null) {
			return null;
		}
		if (cur.val < low) {
			return trimBST(cur.right, low, high);
		}
		if (cur.val > high) {
			return trimBST(cur.left, low, high);
		}
		// cur在范围中
		cur.left = trimBST(cur.left, low, high);
		cur.right = trimBST(cur.right, low, high);
		return cur;
	}

}
