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
						if (rightCheck(cur.left, left)) {
							if (findFirst(left.right, o1, o2) != null) {
								ans = left;
							} else {
								left = cur;
							}
						}
					}
				}
			}
			cur = cur.right;
		}
		return ans != null ? ans : left;
	}

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

	public static boolean rightCheck(TreeNode head, TreeNode target) {
		TreeNode tail = reverse(head);
		TreeNode cur = tail;
		boolean ans = false;
		while (cur != null) {
			if (cur == target) {
				ans = true;
			}
			cur = cur.right;
		}
		reverse(tail);
		return ans;
	}

	public static TreeNode reverse(TreeNode from) {
		TreeNode pre = null;
		TreeNode next = null;
		while (from != null) {
			next = from.right;
			from.right = pre;
			pre = from;
			from = next;
		}
		return pre;
	}

}
