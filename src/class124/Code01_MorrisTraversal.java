package class124;

import java.util.ArrayList;
import java.util.List;

// Morris遍历及其三种序
// 测试链接 : https://leetcode.cn/problems/binary-tree-preorder-traversal/
// 测试链接 : https://leetcode.cn/problems/binary-tree-inorder-traversal/
// 测试链接 : https://leetcode.cn/problems/binary-tree-postorder-traversal/
public class Code01_MorrisTraversal {

	public class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
	}

	// Morris遍历
	public static void morris(TreeNode head) {
		TreeNode cur = head;
		TreeNode mostRight = null;
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
				}
			}
			cur = cur.right;
		}
	}

	// Morris遍历实现先序遍历
	// 测试链接 : https://leetcode.cn/problems/binary-tree-preorder-traversal/
	// 提交preorderTraversal方法，可以直接通过
	public static List<Integer> preorderTraversal(TreeNode head) {
		List<Integer> ans = new ArrayList<>();
		morrisPreorder(head, ans);
		return ans;
	}

	public static void morrisPreorder(TreeNode head, List<Integer> ans) {
		TreeNode cur = head;
		TreeNode mostRight = null;
		while (cur != null) {
			mostRight = cur.left;
			if (mostRight != null) {
				while (mostRight.right != null && mostRight.right != cur) {
					mostRight = mostRight.right;
				}
				if (mostRight.right == null) {
					ans.add(cur.val);
					mostRight.right = cur;
					cur = cur.left;
					continue;
				} else {
					mostRight.right = null;
				}
			} else {
				ans.add(cur.val);
			}
			cur = cur.right;
		}
	}

	// Morris遍历实现中序遍历
	// 测试链接 : https://leetcode.cn/problems/binary-tree-inorder-traversal/
	// 提交inorderTraversal方法，可以直接通过
	public static List<Integer> inorderTraversal(TreeNode head) {
		List<Integer> ans = new ArrayList<>();
		morrisInorder(head, ans);
		return ans;
	}

	public static void morrisInorder(TreeNode head, List<Integer> ans) {
		TreeNode cur = head;
		TreeNode mostRight = null;
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
				}
			}
			ans.add(cur.val);
			cur = cur.right;
		}
	}

	// Morris遍历实现后序遍历
	// 测试链接 : https://leetcode.cn/problems/binary-tree-postorder-traversal/
	// 提交postorderTraversal方法，可以直接通过
	public static List<Integer> postorderTraversal(TreeNode head) {
		List<Integer> ans = new ArrayList<>();
		morrisPostorder(head, ans);
		return ans;
	}

	public static void morrisPostorder(TreeNode head, List<Integer> ans) {
		TreeNode cur = head;
		TreeNode mostRight = null;
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
					collect(cur.left, ans);
				}
			}
			cur = cur.right;
		}
		collect(head, ans);
	}

	public static void collect(TreeNode head, List<Integer> ans) {
		TreeNode tail = reverse(head);
		TreeNode cur = tail;
		while (cur != null) {
			ans.add(cur.val);
			cur = cur.right;
		}
		reverse(tail);
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
