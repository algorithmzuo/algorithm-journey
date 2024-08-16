package class037;

// 搜索二叉树上寻找两个节点的最近公共祖先
// 测试链接 : https://leetcode.cn/problems/lowest-common-ancestor-of-a-binary-search-tree/
public class Code02_myLowestCommonAncestorBinarySearch {

	// 不提交这个类
	public static class TreeNode {
		public int val;
		public TreeNode left;
		public TreeNode right;
	}

	// 提交如下的方法
	public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
		// 1- if encounter node is p or q break the while loop and return current root
		while(root != p && root != q){
			if(Math.min(p.val, q.val) < root.val && root.val < Math.max(p.val, q.val)){
				break;
			}
			root = root.val < Math.min(p.val, q.val) ? root.right : root.left;
		}
		return root;
	}

}
