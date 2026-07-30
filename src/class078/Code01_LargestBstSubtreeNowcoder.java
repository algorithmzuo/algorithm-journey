package class078;

// 最大BST子树，牛客测试
// 因为题目1是leetcode收费题，所以补充一个牛客的测试链接
// 题意完全一样，但是需要根据牛客给的数据格式，建立二叉树
// 测试链接 : https://www.nowcoder.com/practice/380d49d7f99242709ab4b91c36bf2acc
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code01_LargestBstSubtreeNowcoder {

	static class TreeNode {
		public int val;
		public TreeNode left;
		public TreeNode right;

		public TreeNode(int v) {
			val = v;
		}
	}

	public static int largestBSTSubtree(TreeNode root) {
		return f(root).maxBstSize;
	}

	public static class Info {
		public long max;
		public long min;
		public boolean isBst;
		public int maxBstSize;

		public Info(long a, long b, boolean c, int d) {
			max = a;
			min = b;
			isBst = c;
			maxBstSize = d;
		}
	}

	public static Info f(TreeNode x) {
		if (x == null) {
			return new Info(Long.MIN_VALUE, Long.MAX_VALUE, true, 0);
		}
		Info infol = f(x.left);
		Info infor = f(x.right);
		long max = Math.max(x.val, Math.max(infol.max, infor.max));
		long min = Math.min(x.val, Math.min(infol.min, infor.min));
		boolean isBst = infol.isBst && infor.isBst && infol.max < x.val && x.val < infor.min;
		int maxBSTSize;
		if (isBst) {
			maxBSTSize = infol.maxBstSize + infor.maxBstSize + 1;
		} else {
			maxBSTSize = Math.max(infol.maxBstSize, infor.maxBstSize);
		}
		return new Info(max, min, isBst, maxBSTSize);
	}

	// 根据牛客的题目描述，读入数据、建二叉树、调用方法、打印答案
	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		int n = in.nextInt();
		int root = in.nextInt();
		TreeNode[] arr = new TreeNode[n + 1];
		for (int i = 1; i <= n; i++) {
			arr[i] = new TreeNode(i);
		}
		for (int i = 1, fa, l, r; i <= n; i++) {
			fa = in.nextInt();
			l = in.nextInt();
			r = in.nextInt();
			if (l != 0) {
				arr[fa].left = arr[l];
			}
			if (r != 0) {
				arr[fa].right = arr[r];
			}
		}
		out.println(largestBSTSubtree(arr[root]));
		out.flush();
		out.close();
	}

	// 读写工具类
	static class FastReader {
		private final byte[] buffer = new byte[1 << 16];
		private int ptr = 0, len = 0;
		private final InputStream in;

		FastReader(InputStream in) {
			this.in = in;
		}

		private int readByte() throws IOException {
			if (ptr >= len) {
				len = in.read(buffer);
				ptr = 0;
				if (len <= 0) {
					return -1;
				}
			}
			return buffer[ptr++];
		}

		int nextInt() throws IOException {
			int c;
			do {
				c = readByte();
			} while (c <= ' ' && c != -1);
			boolean neg = false;
			if (c == '-') {
				neg = true;
				c = readByte();
			}
			int val = 0;
			while (c > ' ' && c != -1) {
				val = val * 10 + (c - '0');
				c = readByte();
			}
			return neg ? -val : val;
		}

	}

}
