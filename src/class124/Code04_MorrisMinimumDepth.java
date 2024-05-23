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
		// morris遍历中，上一个节点所在的层数
		int preLevel = 0;
		// 树的右边界长度
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
					preLevel++;
					mostRight.right = cur;
					cur = cur.left;
					continue;
				} else {
					if (mostRight.left == null) {
						ans = Math.min(ans, preLevel);
					}
					preLevel -= rightLen;
					mostRight.right = null;
				}
			} else {
				preLevel++;
			}
			cur = cur.right;
		}
		// 不要忘了整棵树的最右节点
		rightLen = 1;
		cur = head;
		while (cur.right != null) {
			rightLen++;
			cur = cur.right;
		}
		// 整棵树的最右节点是叶节点才纳入统计
		if (cur.left == null) {
			ans = Math.min(ans, rightLen);
		}
		return ans;
	}

}
