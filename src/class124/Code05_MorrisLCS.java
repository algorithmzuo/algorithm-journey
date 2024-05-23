package class124;

// Morris遍历求两个节点的最低公共祖先
// 测试链接 : https://leetcode.cn/problems/lowest-common-ancestor-of-a-binary-tree/
public class Code05_MorrisLCS {

	// 不提交这个类
	public class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
	}

	// 提交以下的方法
	public static TreeNode lowestCommonAncestor(TreeNode head, TreeNode o1, TreeNode o2) {
		if (findFirst(o1.left, o1, o2) != null || findFirst(o1.right, o1, o2) != null) {
			return o1;
		}
		if (findFirst(o2.left, o1, o2) != null || findFirst(o2.right, o1, o2) != null) {
			return o2;
		}
		TreeNode left = findFirst(head, o1, o2);
		TreeNode cur = head;
		TreeNode mostRight = null;
		TreeNode ans = null;
		while (cur != null) {
			mostRight = cur.left;
			if (mostRight != null) {
				while (mostRight.right != null && mostRight.right != cur) {
					mostRight = mostRight.right;
				}
				if (mostRight.right == null) {
					mostRight.right = cur;
					cur = cur.left;
					continue;
				} else {
					mostRight.right = null;
					if (ans == null) {
						// left在cur左树的右边界上
						if (rightCheck(cur.left, left)) {
							// 检查left的右树里是否有o2
							if (findFirst(left.right, o1, o2) != null) {
								ans = left;
							}
							left = cur;
							// 为什么检查left而不检查cur
							// 因为cur的右指针可能没有恢复回来
							// 等右指针恢复回来之后再检查
							// 所以检查left而不检查cur
						}
					}
				}
			}
			cur = cur.right;
		}
		return ans != null ? ans : left;
	}

	// 以head为头的树进行Morris遍历，o1和o2谁先被找到就返回谁
	public static TreeNode findFirst(TreeNode head, TreeNode o1, TreeNode o2) {
		if (head == null) {
			return null;
		}
		TreeNode cur = head;
		TreeNode mostRight = null;
		TreeNode ans = null;
		while (cur != null) {
			mostRight = cur.left;
			if (mostRight != null) {
				while (mostRight.right != null && mostRight.right != cur) {
					mostRight = mostRight.right;
				}
				if (mostRight.right == null) {
					if (ans == null && (cur == o1 || cur == o2)) {
						ans = cur;
					}
					mostRight.right = cur;
					cur = cur.left;
					continue;
				} else {
					mostRight.right = null;
				}
			} else {
				if (ans == null && (cur == o1 || cur == o2)) {
					ans = cur;
				}
			}
			cur = cur.right;
		}
		return ans;
	}

	// 以head为头的树遍历右边界，返回是否找到了target
	public static boolean rightCheck(TreeNode head, TreeNode target) {
		while (head != null) {
			if (head == target) {
				return true;
			}
			head = head.right;
		}
		return false;
	}

}
