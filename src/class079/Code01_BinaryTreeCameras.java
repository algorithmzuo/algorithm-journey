package class079;

// 监控二叉树
// 给定一个二叉树，我们在树的节点上安装摄像头
// 节点上的每个摄影头都可以监视其父对象、自身及其直接子对象
// 计算监控树的所有节点所需的最小摄像头数量
// 测试链接 : https://leetcode.cn/problems/binary-tree-cameras/
public class Code01_BinaryTreeCameras {

	// 不要提交这个类
	public static class TreeNode {
		public int val;
		public TreeNode left;
		public TreeNode right;
	}

	// 提交如下的方法
	// 提交时改名
	public static int minCameraCover1(TreeNode root) {
		Info info = f1(root);
		return (int) Math.min(info.uncovered + 1, Math.min(info.coveredNoCamera, info.coveredHasCamera));
	}

	public static class Info {
		// 潜台词：x是头节点，x下方的点都被覆盖的情况下
		// uncovered：x没有被覆盖，x为头的树至少需要几个相机
		// coveredNoCamera：x被相机覆盖，但是x没相机，x为头的树至少需要几个相机
		// coveredHasCamera：x被相机覆盖了，并且x上放了相机，x为头的树至少需要几个相机
		public long uncovered;
		public long coveredNoCamera;
		public long coveredHasCamera;

		public Info(long a, long b, long c) {
			uncovered = a;
			coveredNoCamera = b;
			coveredHasCamera = c;
		}
	}

	public static Info f1(TreeNode x) {
		if (x == null) {
			return new Info(Integer.MAX_VALUE, 0, Integer.MAX_VALUE);
		}
		Info left = f1(x.left);
		Info right = f1(x.right);
		long uncovered = left.coveredNoCamera + right.coveredNoCamera;
		long coveredNoCamera = Math.min(left.coveredHasCamera + right.coveredHasCamera,
				Math.min(left.coveredHasCamera + right.coveredNoCamera, left.coveredNoCamera + right.coveredHasCamera));
		long coveredHasCamera = Math.min(left.uncovered, Math.min(left.coveredNoCamera, left.coveredHasCamera))
				+ Math.min(right.uncovered, Math.min(right.coveredNoCamera, right.coveredHasCamera)) + 1;
		return new Info(uncovered, coveredNoCamera, coveredHasCamera);
	}

	// 提交如下的方法
	// 提交时改名
	public static int ans = 0;

	public int minCameraCover2(TreeNode root) {
		ans = 0;
		if (f2(root) == 0) {
			ans++;
		}
		return ans;
	}

	// 返回值含义
	// 0: 表示节点是无覆盖状态
	// 1: 表示节点是覆盖状态
	// 2: 表示节点是有摄像头状态
	private int f2(TreeNode node) {
		if (node == null) {
			return 1;
		}
		int left = f2(node.left);
		int right = f2(node.right);
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
