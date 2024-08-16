package class036;

// 验证完全二叉树
// 测试链接 : https://leetcode.cn/problems/check-completeness-of-a-binary-tree/
public class Code08_myCompletenessOfBinaryTree {

	// 不提交这个类
	public static class TreeNode {
		public int val;
		public TreeNode left;
		public TreeNode right;

		public TreeNode(int val) {
			this.val = val;
		}
	}

	// 提交以下的方法
	// 如果测试数据量变大了就修改这个值
	public static int MAXN = 101;

	public static TreeNode[] queue = new TreeNode[MAXN];

	public static int l, r;

	public static boolean isCompleteTree(TreeNode root) {
		if(root == null){
			return true;
		}
		l = r = 0;
		queue[r++] = root;
		// 是否遇到过左右两边不全的节点， 是的话=》 true
		boolean isLeaf = false;
		while(l< r){
			TreeNode node = queue[l++];
			if((node.left == null && node.right != null) || (isLeaf && (node.left!= null || node.right !=null)) ){
				return false;
			}

			if(node.left !=null){
				queue[r++] = node.left;
			}

			if(node.right != null){
				queue[r++] = node.right;
			}

			if(node.left == null || node.right == null){
				isLeaf = true;
			}
		}
		return true;
	}

	public static void main(String[] args) {
		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(2);
		root.right = new TreeNode(3);
		root.left.left = new TreeNode(4);
		System.out.println(isCompleteTree(root));
	}

}
