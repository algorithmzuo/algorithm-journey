package class124;

import java.util.ArrayList;
import java.util.List;

// Morris遍历实现先序和中序遍历
// 测试链接 : https://leetcode.cn/problems/binary-tree-preorder-traversal/
// 测试链接 : https://leetcode.cn/problems/binary-tree-inorder-traversal/
public class Code01_MorrisPreorderInorder {

	// 不提交这个类
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
			if (mostRight != null) { // cur有左树
				// 找到左树最右节点
				// 注意左树最右节点的右指针可能指向空，也可能指向cur
				while (mostRight.right != null && mostRight.right != cur) {
					mostRight = mostRight.right;
				}
				// 判断左树最右节点的右指针状态
				if (mostRight.right == null) { // 第一次到达
					ans.add(cur.val);
					mostRight.right = cur;
					cur = cur.left;
					continue;
				} else { // 第二次到达
					mostRight.right = null;
				}
			} else { // cur无左树
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
				}
			}
			ans.add(cur.val);
			cur = cur.right;
		}
	}

}
