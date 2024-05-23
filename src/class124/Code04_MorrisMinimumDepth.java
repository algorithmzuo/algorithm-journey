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
		int height = 0;
		int rightLen;
		int ans = Integer.MAX_VALUE;
		while (cur != null) {
			mostRight = cur.left;
			if (mostRight != null) {
				rightLen = 1;
				while (mostRight.right != null && mostRight.right != cur) {
					rightLen++;
					mostRight = mostRight.right;
				}
				if (mostRight.right == null) {
					height++;
					mostRight.right = cur;
					cur = cur.left;
					continue;
				} else {
					if (mostRight.left == null) {
						ans = Math.min(ans, height);
					}
					height -= rightLen;
					mostRight.right = null;
				}
			} else {
				height++;
			}
			cur = cur.right;
		}
		rightLen = 1;
		cur = head;
		while (cur.right != null) {
			rightLen++;
			cur = cur.right;
		}
		if (cur.left == null && cur.right == null) {
			ans = Math.min(ans, rightLen);
		}
		return ans;
	}

}
