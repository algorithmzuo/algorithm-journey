package class124;

import java.util.ArrayList;
import java.util.List;

// Morris遍历实现后序遍历
// 测试链接 : https://leetcode.cn/problems/binary-tree-postorder-traversal/
public class Code02_MorrisPostorder {

	// 不提交这个类
	public class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
	}

	// 提交如下的方法
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
			if (mostRight != null) { // cur有左树
				// 找到左树最右节点
				// 注意左树最右节点的右指针可能指向空，也可能指向cur
				while (mostRight.right != null && mostRight.right != cur) {
					mostRight = mostRight.right;
				}
				// 判断左树最右节点的右指针状态
				if (mostRight.right == null) { // 第一次到达
					mostRight.right = cur;
					cur = cur.left;
					continue;
				} else { // 第二次到达
					mostRight.right = null;
					collect(cur.left, ans);
				}
			}
			cur = cur.right;
		}
		collect(head, ans);
	}

	// 以head为头的子树，树的右边界逆序收集
	public static void collect(TreeNode head, List<Integer> ans) {
		TreeNode tail = reverse(head);
		TreeNode cur = tail;
		while (cur != null) {
			ans.add(cur.val);
			cur = cur.right;
		}
		reverse(tail);
	}

	// 从from出发，类似单链表翻转，去翻转right指针的方向
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
