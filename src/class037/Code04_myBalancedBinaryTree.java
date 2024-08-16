package class037;

import java.util.HashMap;
import java.util.Map;

// 验证平衡二叉树
// 测试链接 : https://leetcode.cn/problems/balanced-binary-tree/
public class Code04_myBalancedBinaryTree {

	// 不提交这个类
	public static class TreeNode {
		public int val;
		public TreeNode left;
		public TreeNode right;
	}

	// 提交如下的方法

	public static boolean isBalanced(TreeNode root) {
		if(root == null) {
			return true;
		}
		return isBalanced(root.left) && isBalanced(root.right) && Math.abs(height(root.left) - height(root.right)) <= 1;

	}

	// 一旦发现不平衡，返回什么高度已经不重要了
	public static int height(TreeNode cur) {
		if(cur == null){
			return 0;
		}
		return Math.max(height(cur.left) , height(cur.right)) +1;
	}

}
