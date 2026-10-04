package class036;

// 二叉树按层序列化和反序列化
// 测试链接 : https://leetcode.cn/problems/serialize-and-deserialize-binary-tree/
public class Code06_LevelorderSerializeAndDeserialize {

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
	// 按层序列化
	public class Codec {

		// leetcode后来改了本题的规则
		// 不能用静态变量
		// 所以改成private变量就能通过了
		private int MAXN = 10001;
		private TreeNode[] queue = new TreeNode[MAXN];
		private int l = 0;
		private int r = 0;

		public String serialize(TreeNode root) {
			StringBuilder builder = new StringBuilder();
			if (root != null) {
				builder.append(root.val + ",");
				l = 0;
				r = 0;
				queue[r++] = root;
				while (l < r) {
					root = queue[l++];
					if (root.left != null) {
						builder.append(root.left.val + ",");
						queue[r++] = root.left;
					} else {
						builder.append("#,");
					}
					if (root.right != null) {
						builder.append(root.right.val + ",");
						queue[r++] = root.right;
					} else {
						builder.append("#,");
					}
				}
			}
			return builder.toString();
		}

		public TreeNode deserialize(String data) {
			if (data.equals("")) {
				return null;
			}
			String[] nodes = data.split(",");
			int index = 0;
			TreeNode root = generate(nodes[index++]);
			l = 0;
			r = 0;
			queue[r++] = root;
			while (l < r) {
				TreeNode cur = queue[l++];
				cur.left = generate(nodes[index++]);
				cur.right = generate(nodes[index++]);
				if (cur.left != null) {
					queue[r++] = cur.left;
				}
				if (cur.right != null) {
					queue[r++] = cur.right;
				}
			}
			return root;
		}

		private TreeNode generate(String val) {
			return val.equals("#") ? null : new TreeNode(Integer.valueOf(val));
		}

	}

}
