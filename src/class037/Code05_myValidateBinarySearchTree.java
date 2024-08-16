package class037;

// 验证搜索二叉树
// 测试链接 : https://leetcode.cn/problems/validate-binary-search-tree/
public class Code05_myValidateBinarySearchTree {

	// 不提交这个类
	public static class TreeNode {
		public int val;
		public TreeNode left;
		public TreeNode right;
	}

	// 提交以下的方法
	public static int MAXN = 10001;

	public static TreeNode[] stack = new TreeNode[MAXN];

	public static int r;

	// 提交时改名为isValidBST
	public static boolean isValidBST1(TreeNode head) {
		if(head == null){
			return true;
		}
		r = 0;
		TreeNode pre = null;
		// r > 0 => stack is not empty
		while ( r > 0 || head !=null ){
			if(head != null){
				stack[r++] = head;
				head = head.left;
			}else{
				head = stack[--r];
				if(pre.val > head.val){
					return false;
				}
				pre = head;
				head = head.right;
			}
		}
		return true;

	}

	public static int  MIN,MAX;

	// 提交时改名为isValidBST
	public static boolean isValidBST2(TreeNode head) {
		if(head == null){
			MIN = Integer.MAX_VALUE;
			MAX = Integer.MIN_VALUE;
			return true;
		}
		boolean isleftValid = isValidBST2(head.left);
		int leftMin = MIN;
		int leftMax = MAX;
		boolean isRightValid = isValidBST2(head.right);
		int rightMin = MIN;
		int rightMax = MAX;
		MIN = Math.min(Math.min(leftMin, rightMin), head.val);
		MAX = Math.max(Math.max(leftMax, rightMax) , head.val);
		return isleftValid && isRightValid && leftMax < head.val && head.val < rightMin;
	}

}
