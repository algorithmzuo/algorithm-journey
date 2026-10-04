package class036;

import java.util.LinkedList;
import java.util.Queue;

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

		public String serialize(TreeNode root) {
			StringBuilder builder = new StringBuilder();
			if (root != null) {
				builder.append(root.val + ",");
				// 修改了课上代码，改用自带的队列结构
				Queue<TreeNode> que = new LinkedList<>();
				que.offer(root);
				while (!que.isEmpty()) {
					root = que.poll();
					if (root.left != null) {
						builder.append(root.left.val + ",");
						que.offer(root.left);
					} else {
						builder.append("#,");
					}
					if (root.right != null) {
						builder.append(root.right.val + ",");
						que.offer(root.right);
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
			// 修改了课上代码，改用自带的队列结构
			Queue<TreeNode> que = new LinkedList<>();
			que.offer(root);
			while (!que.isEmpty()) {
				TreeNode cur = que.poll();
				cur.left = generate(nodes[index++]);
				cur.right = generate(nodes[index++]);
				if (cur.left != null) {
					que.offer(cur.left);
				}
				if (cur.right != null) {
					que.offer(cur.right);
				}
			}
			return root;
		}

		private TreeNode generate(String val) {
			return val.equals("#") ? null : new TreeNode(Integer.valueOf(val));
		}

	}

}
