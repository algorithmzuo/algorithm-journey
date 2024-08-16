package class036;

import java.util.ArrayList;
import java.util.List;

// 二叉树的锯齿形层序遍历
// 测试链接 : Code02_ZigzagLevelOrderTraversal
public class Code02_myZigzagLevelOrderTraversal {

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
	// 用每次处理一层的优化bfs就非常容易实现
	// 如果测试数据量变大了就修改这个值
	public static int MAXN = 2001;

	public static TreeNode[] queue = new TreeNode[MAXN];

	public static int l, r;

	public static List<List<Integer>> zigzagLevelOrder(TreeNode root) {
		List<List<Integer>> ans = new ArrayList<>();
		if(root != null){
			l = r = 0;
			boolean reverse = false;
			queue[r++]  = root;
			while (l < r ){
				List<Integer> subAns = new ArrayList<>();
				int size = r - l;
				for(int i = reverse ? r - 1 : l, step = reverse ? -1 : 1 , count = 0
					; count < size
					; i += step, count++ ){
					TreeNode node = queue[i];
					subAns.add(node.val);
				}
				for(int i = 0; i< size ; i++){
					TreeNode node = queue[l++];
					if(node.left!= null){
						queue[r++] = node.left;
					}
					if(node.right != null){
						queue[r++] = node.right;
					}
				}
				ans.add(subAns);
				reverse = !reverse;
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
		zigzagLevelOrder(root);
	}

}
