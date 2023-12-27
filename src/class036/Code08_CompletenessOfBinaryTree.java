package class036;

// 验证完全二叉树
// 测试链接 : https://leetcode.cn/problems/check-completeness-of-a-binary-tree/
public class Code08_CompletenessOfBinaryTree {

    // 不提交这个类
    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;
    }

    // 提交以下的方法
    // 如果测试数据量变大了就修改这个值
    int MAXN = 101, l = 0, r = 0;
    TreeNode[] queue = new TreeNode[MAXN];

    public boolean isCompleteTree(TreeNode root) {
        if (root == null) return true;
        queue[r++] = root;
        // 是否遇到过左右两个孩子不双全的节点
        boolean leaf = false;
        while (l < r) {
            root = queue[l++];
            boolean p1 = root.left == null && root.right != null;// 很明显，这违反complete binary tree
            boolean p2 = leaf && (root.left != null || root.right != null);// 不能再有下一层，因为上一层已经不满了。
            if (p1 || p2) return false;
            if (root.left != null) queue[r++] = root.left;
            if (root.right != null) queue[r++] = root.right;
            if (root.left == null || root.right == null) leaf = true;
        }
        return true;
    }

}
