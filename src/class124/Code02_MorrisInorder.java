package class124;

import java.util.ArrayList;
import java.util.List;

// 测试链接 : https://leetcode.cn/problems/binary-tree-inorder-traversal/
public class Code02_MorrisInorder {

	// 提交时不提交这个类
	public class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
	}

	// 提交如下的方法
	public static List<Integer> inorderTraversal(TreeNode head) {
		List<Integer> ans = new ArrayList<>();
		morrisInorder(head, ans);
		return ans;
	}

	// morris遍历
	// 具体完成中序遍历
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

}
