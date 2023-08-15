package code;

// 求完全二叉树的节点个数
// 测试链接 : https://leetcode.cn/problems/count-complete-tree-nodes/
public class Video_036_9_CountCompleteTreeNodes {

	// 不提交这个类
	public static class TreeNode {
		public int val;
		public TreeNode left;
		public TreeNode right;
	}

	// 提交如下的方法
	public static int countNodes(TreeNode head) {
		if (head == null) {
			return 0;
		}
		return bs(head, 1, mostLeft(head, 1));
	}

	public static int bs(TreeNode node, int Level, int h) {
		if (Level == h) {
			return 1;
		}
		if (mostLeft(node.right, Level + 1) == h) {
			return (1 << (h - Level)) + bs(node.right, Level + 1, h);
		} else {
			return (1 << (h - Level - 1)) + bs(node.left, Level + 1, h);
		}
	}

	public static int mostLeft(TreeNode node, int level) {
		while (node != null) {
			level++;
			node = node.left;
		}
		return level - 1;
	}

}
