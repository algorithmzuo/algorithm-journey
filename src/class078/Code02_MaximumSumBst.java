package class078;

// 二叉搜索子树的最大键值和
// 给你一棵以 root 为根的二叉树
// 请你返回 任意 二叉搜索子树的最大键值和
// 二叉搜索树的定义如下：
// 任意节点的左子树中的键值都 小于 此节点的键值
// 任意节点的右子树中的键值都 大于 此节点的键值
// 任意节点的左子树和右子树都是二叉搜索树
// 测试链接 : https://leetcode.cn/problems/maximum-sum-bst-in-binary-tree/
public class Code02_MaximumSumBst {

	// 不要提交这个类
	public static class TreeNode {
		public int val;
		public TreeNode left;
		public TreeNode right;
	}

	// 提交如下的方法
	public static int maxSumBST(TreeNode root) {
		return f(root).maxBstSum;
	}

	public static class Info {
		// 为什么这里的max和min是int类型？
		// 因为题目的数据量规定，
		// 节点值在[-4 * 10^4，4 * 10^4]范围
		// 所以int类型的最小值和最大值就够用了
		// 不需要用long类型
		public int max;
		public int min;
		public int sum;
		public boolean isBst;
		public int maxBstSum;

		public Info(int a, int b, int c, boolean d, int e) {
			max = a;
			min = b;
			sum = c;
			isBst = d;
			maxBstSum = e;
		}
	}

	public static Info f(TreeNode x) {
		if (x == null) {
			return new Info(Integer.MIN_VALUE, Integer.MAX_VALUE, 0, true, 0);
		}
		Info infol = f(x.left);
		Info infor = f(x.right);
		int max = Math.max(x.val, Math.max(infol.max, infor.max));
		int min = Math.min(x.val, Math.min(infol.min, infor.min));
		int sum = infol.sum + infor.sum + x.val;
		boolean isBst = infol.isBst && infor.isBst && infol.max < x.val && x.val < infor.min;
		int maxBstSum = Math.max(infol.maxBstSum, infor.maxBstSum);
		if (isBst) {
			maxBstSum = Math.max(maxBstSum, sum);
		}
		return new Info(max, min, sum, isBst, maxBstSum);
	}

}
