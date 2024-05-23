package class124;

// Morris遍历求二叉树最小高度
// 测试链接 : https://leetcode.cn/problems/minimum-depth-of-binary-tree/
public class Code04_MorrisMinimumDepth {

	// 不提交这个类
	public class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
	}

	// 提交如下的方法
	public static int minDepth(TreeNode head) {
		if (head == null) {
			return 0;
		}
		TreeNode cur = head;
		TreeNode mostRight = null;
		int curHeight = 0, rightHeight, minHeight = Integer.MAX_VALUE;
		while (cur != null) {
			mostRight = cur.left;
			if (mostRight != null) {
				rightHeight = 1;
				while (mostRight.right != null && mostRight.right != cur) {
					rightHeight++;
					mostRight = mostRight.right;
				}
				if (mostRight.right == null) {
					curHeight++;
					mostRight.right = cur;
					cur = cur.left;
					continue;
				} else {
					if (mostRight.left == null) {
						minHeight = Math.min(minHeight, curHeight);
					}
					curHeight -= rightHeight;
					mostRight.right = null;
				}
			} else {
				curHeight++;
			}
			cur = cur.right;
		}
		rightHeight = 1;
		cur = head;
		while (cur.right != null) {
			rightHeight++;
			cur = cur.right;
		}
		if (cur.left == null && cur.right == null) {
			minHeight = Math.min(minHeight, rightHeight);
		}
		return minHeight;
	}

}
