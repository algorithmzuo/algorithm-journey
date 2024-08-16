package class036;

// 二叉树按层序列化和反序列化
// 测试链接 : https://leetcode.cn/problems/serialize-and-deserialize-binary-tree/
public class Code06_myLevelorderSerializeAndDeserialize {

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
	public static class Codec {

		public int MAXN = 10001;
		public TreeNode[] queue = new TreeNode[MAXN];

		public String serialize(TreeNode root) {
			StringBuilder str = new StringBuilder();
			if (root != null) {
				int l = 0;
				int r = 0;
				queue[r++] = root;
				str.append(root.val).append(",");
				while (l < r) {
					int size = r - l;
					for (int i = 0; i < size; i++) {
						TreeNode cur = queue[l++];

						if (cur.left == null) {
							str.append("#,");
						} else {
							str.append(cur.left.val).append(",");
							queue[r++] = cur.left;
						}

						if (cur.right == null) {
							str.append("#,");
						} else {
							str.append(cur.right.val).append(",");
							queue[r++] = cur.right;
						}
					}
				}
			}
			return str.toString();
		}

		public TreeNode deserialize(String data) {
			if(data.equals("")){
				return null;
			}
			String[] vals = data.split(",");
			int cnt = 0 ;
			int l = 0;
			int r = 0;
			TreeNode root = generateNode(vals[cnt++]);
			queue[r++] = root;
			while(l < r){
				TreeNode curNode = queue[l++];
				curNode.left = generateNode(vals[cnt++]);
				if(curNode.left != null){
					queue[r++] =  curNode.left;
				}

				curNode.right = generateNode(vals[cnt++]);
				if(curNode.right !=null){
					queue[r++] = curNode.right;
				}
			}
			return root;
		}

		public TreeNode generateNode(String str){
			return str.equals("#")? null : new TreeNode(Integer.parseInt(str));
		}
	}


	public static void main(String[] args) {
		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(2);
		Codec c = new Codec();
		String str= c.serialize(root);

		System.out.println(c.deserialize(str));
	}

}


