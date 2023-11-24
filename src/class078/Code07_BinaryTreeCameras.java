package class078;

// 监控二叉树
// 给定一个二叉树，我们在树的节点上安装摄像头
// 节点上的每个摄影头都可以监视其父对象、自身及其直接子对象
// 计算监控树的所有节点所需的最小摄像头数量
// 测试链接 : https://leetcode.cn/problems/binary-tree-cameras/
public class Code07_BinaryTreeCameras {

	// 不要提交这个类
	public static class TreeNode {
		public int val;
		public TreeNode left;
		public TreeNode right;
	}

	// 提交如下的方法
	// 遍历过程中一旦需要放置相机，ans++
	public static int ans;

	public int minCameraCover(TreeNode root) {
		ans = 0;
		if (f(root) == 0) {
			ans++;
		}
		return ans;
	}

	// 返回值含义
	// 0: 表示头节点是无覆盖状态。头节点下方节点都被覆盖。
	// 1: 表示头节点是覆盖状态，没摄像头。头节点下方节点都被覆盖。
	// 2: 表示头节点是覆盖状态，有摄像头。头节点下方节点都被覆盖。
	private int f(TreeNode node) {
		if (node == null) {
			return 1;
		}
		int left = f(node.left);
		int right = f(node.right);
		if (left == 0 || right == 0) {
			ans++;
			return 2;
		}
		if (left == 1 && right == 1) {
			return 0;
		}
		return 1;
	}

}
