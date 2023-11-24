package class078;

import java.util.HashMap;

// 路径总和 III
// 给定一个二叉树的根节点 root ，和一个整数 targetSum
// 求该二叉树里节点值之和等于 targetSum 的 路径 的数目
// 路径 不需要从根节点开始，也不需要在叶子节点结束
// 但是路径方向必须是向下的（只能从父节点到子节点）
// 测试链接 : https://leetcode.cn/problems/path-sum-iii/
public class Code07_PathSumIII {

	// 不要提交这个类
	public static class TreeNode {
		public int val;
		public TreeNode left;
		public TreeNode right;
	}

	// 提交如下的方法
	public static int pathSum(TreeNode root, int sum) {
		HashMap<Long, Integer> presum = new HashMap<>();
		presum.put(0L, 1);
		ans = 0;
		f(root, sum, 0, presum);
		return ans;
	}

	public static int ans;

	// sum : 从头节点出发，来到x的时候，上方累加和是多少
	// 路径必须以x作为结尾，路径累加和是target的路径数量，累加到全局变量ans上
	public static void f(TreeNode x, int target, long sum, HashMap<Long, Integer> presum) {
		if (x != null) {
			sum += x.val; // 从头节点出发一路走到x的整体累加和
			ans += presum.getOrDefault(sum - target, 0);
			presum.put(sum, presum.getOrDefault(sum, 0) + 1);
			f(x.left, target, sum, presum);
			f(x.right, target, sum, presum);
			presum.put(sum, presum.get(sum) - 1);
		}
	}

}
