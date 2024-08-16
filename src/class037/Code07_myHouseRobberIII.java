package class037;

// 二叉树打家劫舍问题
// 测试链接 : https://leetcode.cn/problems/house-robber-iii/
public class Code07_myHouseRobberIII {

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

	// 全局变量，完成了X子树的遍历，返回之后
	// yes => X子树在偷头节点的情况下，最大的收益
	public static int yes;

	// 全局变量，完成了X子树的遍历，返回之后
	// no => X子树在不偷头节点的情况下，最大的收益
	public static int no;

	public static void f(TreeNode node) {
		if(node == null){
			yes = 0;
			no = 0;
			return;
		}
		f(node.left);
		int ln = no;
		int ly = yes;
		f(node.right);
		int rn = no;
		int ry = yes;
		yes = node.val + ln + rn;
		no = Math.max(ln,ly) + Math.max(rn,ry);

	}

}
