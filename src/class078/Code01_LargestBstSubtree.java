package class078;

// 最大BST子树
// 给定一个二叉树，找到其中最大的二叉搜索树（BST）子树，并返回该子树的大小
// 其中，最大指的是子树节点数最多的
// 二叉搜索树（BST）中的所有节点都具备以下属性：
// 左子树的值小于其父（根）节点的值
// 右子树的值大于其父（根）节点的值
// 注意：子树必须包含其所有后代
// 测试链接 : https://leetcode.cn/problems/largest-bst-subtree/
public class Code01_LargestBstSubtree {

	// 不要提交这个类
	public static class TreeNode {
		public int val;
		public TreeNode left;
		public TreeNode right;
	}

	// 提交如下的方法
	public static int largestBSTSubtree(TreeNode root) {
		return f(root).maxBstSize;
	}

	public static class Info {
		public long max;
		public long min;
		public boolean isBst;
		public int maxBstSize;

		public Info(long a, long b, boolean c, int d) {
			max = a;
			min = b;
			isBst = c;
			maxBstSize = d;
		}
	}

	public static Info f(TreeNode x) {
		if (x == null) {
			return new Info(Long.MIN_VALUE, Long.MAX_VALUE, true, 0);
		}
		Info infol = f(x.left);
		Info infor = f(x.right);
		// 左 4信息
		// 右 4信息
		// x 整合出4信息返回
		long max = Math.max(x.val, Math.max(infol.max, infor.max));
		long min = Math.min(x.val, Math.min(infol.min, infor.min));
		boolean isBst = infol.isBst && infor.isBst && infol.max < x.val && x.val < infor.min;
		int maxBSTSize;
		if (isBst) {
			maxBSTSize = infol.maxBstSize + infor.maxBstSize + 1;
		} else {
			maxBSTSize = Math.max(infol.maxBstSize, infor.maxBstSize);
		}
		return new Info(max, min, isBst, maxBSTSize);
	}

}
