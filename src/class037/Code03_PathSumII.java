package class037;

import java.util.ArrayList;
import java.util.List;

// 收集累加和等于aim的所有路径
// 测试链接 : https://leetcode.cn/problems/path-sum-ii/
public class Code03_PathSumII {

	// 不提交这个类
	public static class TreeNode {
		public int val;
		public TreeNode left;
		public TreeNode right;
	}

	// 提交如下的方法
	public static List<List<Integer>> pathSum(TreeNode root, int aim) {
		List<List<Integer>> ans = new ArrayList<>();
		if (root == null) {
			return ans;
		}
		List<Integer> path = new ArrayList<>();
		process(root, aim, 0, path, ans);
		return ans;
	}

	public static void process(TreeNode h, int aim, int sum, List<Integer> path, List<List<Integer>> ans) {
		if (h == null) {
			return;
		}
		if (h.left == null && h.right == null) {
			if (h.val + sum == aim) {
				path.add(h.val);
				copy(path, ans);
				path.remove(path.size() - 1);
			}
		} else {
			path.add(h.val);
			process(h.left, aim, sum + h.val, path, ans);
			process(h.right, aim, sum + h.val, path, ans);
			path.remove(path.size() - 1);
		}
	}

	public static void copy(List<Integer> path, List<List<Integer>> ans) {
		List<Integer> copy = new ArrayList<>();
		for (Integer num : path) {
			copy.add(num);
		}
		ans.add(copy);
	}

}
