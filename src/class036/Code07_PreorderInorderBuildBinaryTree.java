package class036;

import java.util.HashMap;
import java.util.Map;

// 利用先序与中序遍历序列构造二叉树
// 测试链接 : https://leetcode.cn/problems/construct-binary-tree-from-preorder-and-inorder-traversal/
public class Code07_PreorderInorderBuildBinaryTree {

    // 不提交这个类
    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        public TreeNode(int v) {
            val = v;
        }
    }

    Map<Integer, Integer> inMap = new HashMap<>();

    public TreeNode buildTree(int[] pre, int[] ind) {
        if (pre == null || ind == null || pre.length == 0 || ind.length == 0) return null;
        for (int i = 0; i < ind.length; i++) inMap.put(ind[i], i);
        return helper(pre, 0, pre.length - 1, ind, 0, ind.length - 1);
    }

    TreeNode helper(int[] pre, int preStart, int preEnd, int[] in, int inStart, int inEnd) {
        if (preStart > preEnd || inStart > inEnd) return null;
        int rootVal = pre[preStart];
        TreeNode root = new TreeNode(rootVal);
        int idx = inMap.get(rootVal);
        int leftSubtreeSize = idx - inStart;
        root.left = helper(pre, preStart + 1, preStart + leftSubtreeSize, in, inStart, idx - 1);
        root.right = helper(pre, preStart + leftSubtreeSize + 1, preEnd, in, idx + 1, inEnd);
        return root;
    }
}

