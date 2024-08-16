package class036;

// 求完全二叉树的节点个数
// 测试链接 : https://leetcode.cn/problems/count-complete-tree-nodes/
public class Code09_myCountCompleteTreeNodes {

	// 不提交这个类
	public static class TreeNode {
		public int val;
		public TreeNode left;
		public TreeNode right;

		public TreeNode(int val) {
			this.val = val;
		}
	}

	// 提交如下的方法
	public static int countNodes(TreeNode root) {
		if(root == null){
			return 0;
		}
		return f(root, 1, leftMost(root , 1));
	}

	// cur : 当前来到的节点
	// level :  当前来到的节点在第几层
	// h : 整棵树的高度，不是cur这棵子树的高度
	// 求 : cur这棵子树上有多少节点
	public static int f(TreeNode cur, int level, int h) {
		// come to the leaf node
		if(level == h){
			return 1;
		}
		// 右子树可以穿透在最下面的一层
		if(leftMost(cur.right, level +1) == h){
			// 左子树是完美二叉树
			return (1<< (h - level)) + f(cur.right, level + 1,h);
		}else{
			// 右子树是完美二叉树
			return f(cur.left, level+1, h) + (1 << (h- level-1));
		}
	}

	// 当前节点是cur，并且它在level层
	// 返回从cur开始不停往左，能扎到几层

	public static int leftMost (TreeNode node, int level){
		while(node != null){
			level++;
			node = node.left;
		}
		return level - 1;
	}

	public static void main(String[] args) {
		TreeNode root =  new TreeNode(1);
		root.left = new TreeNode(2);
		root.right = new TreeNode(3);
		System.out.println(countNodes(root));
	}
}
