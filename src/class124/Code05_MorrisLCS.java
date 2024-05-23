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
		if (preOrder(o1.left, o1, o2) != null || preOrder(o1.right, o1, o2) != null) {
			return o1;
		}
		if (preOrder(o2.left, o1, o2) != null || preOrder(o2.right, o1, o2) != null) {
			return o2;
		}
		TreeNode left = preOrder(head, o1, o2);
		TreeNode cur = head;
		TreeNode mostRight = null;
		TreeNode lca = null;
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
				} else { // 第二次来到cur
					mostRight.right = null;
					if (lca == null) {
						// 检查left是否在cur左树的右边界上
						if (rightCheck(cur.left, left)) {
							// 检查left看看右树里是否有o2
							if (preOrder(left.right, o1, o2) != null) {
								lca = left;
							}
							left = cur;
							// 为什么此时检查的是left而不是cur
							// 因为cur右树上的某些右指针可能没有恢复回来
							// 需要等右指针恢复回来之后检查才不出错
							// 所以此时检查的是left而不是cur
							// 课上已经重点图解了
						}
					}
				}
			}
			cur = cur.right;
		}
		// 如果morris遍历结束了还没有收集到答案
		// 此时最后一个left还没有验证，它一定是答案
		return lca != null ? lca : left;
	}

	// 以head为头的树进行先序遍历，o1和o2谁先被找到就返回谁
	public static TreeNode preOrder(TreeNode head, TreeNode o1, TreeNode o2) {
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
