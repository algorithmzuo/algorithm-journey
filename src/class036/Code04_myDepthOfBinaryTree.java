package class036;

import java.util.LinkedList;
import java.util.Queue;

// 求二叉树的最大、最小深度
public class Code04_myDepthOfBinaryTree {

	// 不提交这个类
	public static class TreeNode {
		public int val;
		public TreeNode left;
		public TreeNode right;
	}

	// 测试链接 : https://leetcode.cn/problems/maximum-depth-of-binary-tree/
	public static int maxDepth(TreeNode root) {
		return root == null ? 0 : Math.max(maxDepth(root.left), maxDepth(root.right)) + 1;
	}

	// recursive
	// 测试链接 : https://leetcode.cn/problems/minimum-depth-of-binary-tree/
	public int minDepth(TreeNode root) {
		if(root == null){
			return 0;
		}

		if(root.left == null && root.right == null){
			return 1;
		}

		int leftMinDepth = Integer.MAX_VALUE;
		int rightMinDepth = Integer.MAX_VALUE;
		if(root.left != null){
			leftMinDepth = Math.min(leftMinDepth, minDepth(root.left));
		}

		if(root.right != null){
			rightMinDepth = Math.min(rightMinDepth, minDepth(root.right));
		}
		return Math.min(leftMinDepth, rightMinDepth) + 1;
	}

	//non-recursive
	public int minDepth1(TreeNode root){
		if(root == null){
			return 0;
		}

		int depth = 1;
		Queue<TreeNode> queue =  new LinkedList<>();
		queue.offer(root);
		while(!queue.isEmpty()){
			int size = queue.size();
			for (int i = 0; i < size; i++) {
				TreeNode curNode = queue.poll();

				// 处理上层节点
				if(curNode.left == null && curNode.right ==null){
					return depth;
				}

				// 准备下层节点
				if(curNode.left != null){
					queue.offer(curNode.left);
				}
				if(curNode.right != null){
					queue.offer(curNode.right);
				}
			}
			depth++;
		}
		return depth;

	}

}
