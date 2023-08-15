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
		return f(head, 1, mostLeft(head, 1));
	}

	public static int f(TreeNode cur, int Level, int h) {
		if (Level == h) {
			return 1;
		}
		if (mostLeft(cur.right, Level + 1) == h) {
			return (1 << (h - Level)) + f(cur.right, Level + 1, h);
		} else {
			return (1 << (h - Level - 1)) + f(cur.left, Level + 1, h);
		}
	}

	public static int mostLeft(TreeNode cur, int level) {
		while (cur != null) {
			level++;
			cur = cur.left;
		}
		return level - 1;
	}

}
