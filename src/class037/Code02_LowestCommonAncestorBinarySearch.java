package class037;

// 搜索二叉树上寻找两个节点的最近公共祖先
// 测试链接 : https://leetcode.cn/problems/lowest-common-ancestor-of-a-binary-search-tree/
public class Code02_LowestCommonAncestorBinarySearch {

	// 不提交这个类
	public static class TreeNode {
		public int val;
		public TreeNode left;
		public TreeNode right;
	}

	// 提交如下的方法
	public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
		// root从上到下
		// 如果先遇到了p，说明p是答案
		// 如果先遇到了q，说明q是答案
		// 如果root在p~q的值之间，不用管p和q谁大谁小，只要root在中间，那么此时的root就是答案
		// 如果root在p~q的值的左侧，那么root往右移动
		// 如果root在p~q的值的右侧，那么root往左移动
		while (root.val != p.val && root.val != q.val) {
			if (Math.min(p.val, q.val) < root.val && root.val < Math.max(p.val, q.val)) {
				break;
			}
			root = root.val < Math.min(p.val, q.val) ? root.right : root.left;
		}
		return root;
	}

}
