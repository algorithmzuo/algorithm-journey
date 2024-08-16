package class036;

// 二叉树的最大特殊宽度
// 测试链接 : https://leetcode.cn/problems/maximum-width-of-binary-tree/
public class Code03_myWidthOfBinaryTree {

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
	public static int MAXN = 3001;

	public static int[] iq = new int[MAXN];
	public  static TreeNode[] nq = new TreeNode[MAXN];
	static int r ;
	static int l;


	public static int widthOfBinaryTree(TreeNode root) {
		int ans = 0;
		if(root != null){
			l = r = 0;
			nq[r] = root;
			iq[r++] =  1;
			while(l < r){
				int size = r - l;
				ans = Math.max(ans, iq[r -1 ] - iq[l] +1);
				for (int i = 0; i < size; i++) {

					TreeNode node = nq[l];
					int curId = iq[l++];

					if(root.left != null){
						nq[r] = node.left;
						iq[r++] =  curId * 2;
					}
					if(root.right != null){
						nq[r] = node.right;
						iq[r++] = curId * 2 + 1;
					}

				}
			}

		}
		return ans;
	}

	public static void main(String[] args) {
		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(2);
//		root.right = new TreeNode(3);
//		root.right.left = new TreeNode(4);
//		root.right.right = new TreeNode(5);
		System.out.println(widthOfBinaryTree(root));
	}

}
