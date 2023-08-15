package code;

// 二叉树打家劫舍问题
// 测试链接 : https://leetcode.cn/problems/house-robber-iii/
public class Video_037_8_HouseRobberIII {

	// 不提交这个类
	public static class TreeNode {
		public int val;
		public TreeNode left;
		public TreeNode right;
	}

	// 提交如下的方法
	public static int rob(TreeNode root) {
		f(root);
		return Math.max(yes, no);
	}

	public static int yes;

	public static int no;

	public static void f(TreeNode root) {
		if (root == null) {
			yes = 0;
			no = 0;
		} else {
			int y = root.val;
			int n = 0;
			rob(root.left);
			y += no;
			n += Math.max(yes, no);
			rob(root.right);
			y += no;
			n += Math.max(yes, no);
			yes = y;
			no = n;
		}
	}

}
