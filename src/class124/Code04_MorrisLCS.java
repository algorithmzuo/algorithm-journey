package class124;

// 测试链接 : https://leetcode.cn/problems/lowest-common-ancestor-of-a-binary-tree/
public class Code04_MorrisLCS {

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
		TreeNode leftTarget = findFirst(head, o1, o2);
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
					if (findLeft(cur.left, leftTarget)) {
						if (ans == null && findFirst(leftTarget.right, o1, o2) != null) {
							ans = leftTarget;
						}
						leftTarget = cur;
					}
				}
			}
			cur = cur.right;
		}
		return ans != null ? ans : (findFirst(leftTarget.right, o1, o2) != null ? leftTarget : head);
	}

	public static TreeNode findFirst(TreeNode head, TreeNode o1, TreeNode o2) {
		if (head == null) {
			return null;
		}
		TreeNode cur = head;
		TreeNode mostRight = null;
		TreeNode first = null;
		while (cur != null) {
			mostRight = cur.left;
			if (mostRight != null) {
				while (mostRight.right != null && mostRight.right != cur) {
					mostRight = mostRight.right;
				}
				if (mostRight.right == null) {
					if (first == null && (cur == o1 || cur == o2)) {
						first = cur;
					}
					mostRight.right = cur;
					cur = cur.left;
					continue;
				} else {
					mostRight.right = null;
				}
			} else {
				if (first == null && (cur == o1 || cur == o2)) {
					first = cur;
				}
			}
			cur = cur.right;
		}
		return first;
	}

	public static boolean findLeft(TreeNode head, TreeNode target) {
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
