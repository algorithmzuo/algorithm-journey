package class079;

import java.util.HashMap;

// 路径总和 III
// 给定一个二叉树的根节点 root ，和一个整数 targetSum
// 求该二叉树里节点值之和等于 targetSum 的 路径 的数目
// 路径 不需要从根节点开始，也不需要在叶子节点结束
// 但是路径方向必须是向下的（只能从父节点到子节点）
// 测试链接 : https://leetcode.cn/problems/path-sum-iii/
public class Code02_PathSumIII {

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
		return f(root, sum, 0, presum);
	}

	// 返回方法数
	public static int f(TreeNode x, int target, long sum, HashMap<Long, Integer> presum) {
		if (x == null) {
			return 0;
		}
		sum += x.val;
		int ans = 0;
		if (presum.containsKey(sum - target)) {
			ans = presum.get(sum - target);
		}
		if (!presum.containsKey(sum)) {
			presum.put(sum, 1);
		} else {
			presum.put(sum, presum.get(sum) + 1);
		}
		ans += f(x.left, target, sum, presum);
		ans += f(x.right, target, sum, presum);
		if (presum.get(sum) == 1) {
			presum.remove(sum);
		} else {
			presum.put(sum, presum.get(sum) - 1);
		}
		return ans;
	}

}
