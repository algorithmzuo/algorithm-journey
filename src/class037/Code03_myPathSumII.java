package class037;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

// 收集累加和等于aim的所有路径
// 测试链接 : https://leetcode.cn/problems/path-sum-ii/
public class Code03_myPathSumII {

	// 不提交这个类
	public static class TreeNode {
		public int val;
		public TreeNode left;
		public TreeNode right;
	}


	List<List<Integer>> paths;
	List<Integer> path;
	// 提交如下的方法
	public  List<List<Integer>> pathSum(TreeNode root, int target) {
		paths = new ArrayList<>();
		path = new ArrayList<>();
		if(root != null){
			process(root, 0, target);
		}
		return paths;
	}

	/**
	 *
	 * @param node - current Node
	 * @param sum - sum of previous path
	 * @param target
	 */
	public void process(TreeNode node, int sum, int target){
		if(node.left == null && node.right ==null ){
			if(sum + node.val == target){
				path.add(node.val);
				copyResult(path);
				path.remove(path.size() -1 );
			}
		}
		path.add(node.val);
		if(node.left != null){
			process(node.left, sum+node.val, target);
		}

		if(node.right != null){
			process(node.right, sum + node.val , target);
		}
		path.remove(path.size() -1);


	}

	public void copyResult(List<Integer> path){
		List<Integer> resPath = new ArrayList<>(path);
		paths.add(resPath);
	}





}
