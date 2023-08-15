package code;

// 二叉树先序序列化和反序列化
// 测试链接 : https://leetcode.cn/problems/serialize-and-deserialize-binary-tree/
public class Video_036_6_PreorderSerializeAndDeserialize {

	// 不提交这个类
	public static class TreeNode {
		public int val;
		public TreeNode left;
		public TreeNode right;

		public TreeNode(int v) {
			val = v;
		}
	}

	// 提交这个类
	// 先序序列化
	public class Codec {

		public String serialize(TreeNode root) {
			StringBuilder builder = new StringBuilder();
			serialize(root, builder);
			return builder.toString();
		}

		void serialize(TreeNode root, StringBuilder builder) {
			if (root == null) {
				builder.append("#,");
			} else {
				builder.append(root.val + ",");
				serialize(root.left, builder);
				serialize(root.right, builder);
			}
		}

		public TreeNode deserialize(String data) {
			String[] vals = data.split(",");
			cnt = 0;
			return deserialize(vals);
		}

		public static int cnt;

		TreeNode deserialize(String[] vals) {
			String cur = vals[cnt++];
			if (cur.equals("#")) {
				return null;
			} else {
				TreeNode head = new TreeNode(Integer.valueOf(cur));
				head.left = deserialize(vals);
				head.right = deserialize(vals);
				return head;
			}
		}

	}

}
