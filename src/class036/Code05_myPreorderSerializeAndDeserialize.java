package class036;

import java.util.Arrays;

// 二叉树先序序列化和反序列化
// 测试链接 : https://leetcode.cn/problems/serialize-and-deserialize-binary-tree/
public class Code05_myPreorderSerializeAndDeserialize {

	// 不提交这个类
	public static class TreeNode {
		public int val;
		public TreeNode left;
		public TreeNode right;

		public TreeNode(int v) {
			val = v;
		}
	}

    // 二叉树可以通过先序、后序或者按层遍历的方式序列化和反序列化
    // 但是，二叉树无法通过中序遍历的方式实现序列化和反序列化
    // 因为不同的两棵树，可能得到同样的中序序列，即便补了空位置也可能一样。
    // 比如如下两棵树
    //         __2
    //        /
    //       1
    //       和
    //       1__
    //          \
    //           2
    // 补足空位置的中序遍历结果都是{ null, 1, null, 2, null}
	// 提交这个类
	public static class Codec {
		static StringBuilder str;
		public  String serialize(TreeNode root) {
			str = new StringBuilder();
			f(root);
			return str.toString();
		}

		public void f(TreeNode node){
			if(node == null){
				str.append("#,");
			}else {
				str.append(node.val).append(",");
				f(node.left);
				f(node.right);
			}
		}


		public TreeNode deserialize(String data) {
			if(data.equals("")){
				return null;
			}
			return g(data.split(","));
		}

		// 当前数组消费到哪了
		int cnt = 0;
		TreeNode g(String[] vals) {
			String val = vals[cnt++];
			if(val.equals("#")){
				return null;
			}else{
				TreeNode head = new TreeNode(Integer.parseInt(val));
				head.left = g(vals);
				head.right = g(vals);
				return head;
			}

		}

	}

	public static void main(String[] args) {
		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(2);
		root.right = new TreeNode(3);
		Codec c = new Codec();
		System.out.println(c.serialize(root));
		String str = c.serialize(root);
		System.out.println(str.split(",").length);
		Arrays.stream(str.split(",")).forEach(System.out::println);

	}

}
