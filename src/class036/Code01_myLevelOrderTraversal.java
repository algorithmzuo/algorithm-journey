package class036;

import java.util.*;

// 二叉树的层序遍历
// 测试链接 : https://leetcode.cn/problems/binary-tree-level-order-traversal/
public class Code01_myLevelOrderTraversal {

	// 不提交这个类
	public static class TreeNode {
		public int val;
		public TreeNode left;
		public TreeNode right;

		public TreeNode(int val) {
			this.val = val;
		}
	}



	// 如果测试数据量变大了就修改这个值
	public static int MAX_SIZE = 2001;
	public static TreeNode[] queue = new TreeNode[MAX_SIZE];
	public static int left;
	public static int right;


	// 提交时把方法名改为levelOrder，此方法为每次处理一层的优化bfs，此题推荐
	public static List<List<Integer>> levelOrder(TreeNode root) {
		List<List<Integer>> ans = new ArrayList<>();
		if(root!= null){
			left = right = 0;
			queue[right++] = root;
			while(left < right){
				List<Integer> subAns = new ArrayList<>();
				int size = right - left;
				for (int i = 0; i < size; i++) {
					TreeNode node = queue[left++];
					if(node.left != null){
						queue[right++] = node.left;
					}
					if(node.right != null){
						queue[right++] = node.right;
					}
				}
				ans.add(subAns);
			}
		}
		return ans;
	}

	public static void main(String[] args) {
		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(2);
		root.right = new TreeNode(3);
		root.right.left = new TreeNode(4);
		root.right.right = new TreeNode(5);
		levelOrder(root);
	}

}
