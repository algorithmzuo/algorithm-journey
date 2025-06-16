package class152;

// 文本编辑器，FHQ-Treap实现区间移动，java版本
// 一开始文本为空，光标在文本开头，也就是1位置，请实现如下6种操作
// Move k     : 将光标移动到第k个字符之后，操作保证光标不会到非法位置
// Insert n s : 在光标处插入长度为n的字符串s，光标位置不变
// Delete n   : 删除光标后的n个字符，光标位置不变，操作保证有足够字符
// Get n      : 输出光标后的n个字符，光标位置不变，操作保证有足够字符
// Prev       : 光标前移一个字符，操作保证光标不会到非法位置
// Next       : 光标后移一个字符，操作保证光标不会到非法位置
// Insert操作时，字符串s中ASCII码在[32,126]范围上的字符一定有n个，其他字符请过滤掉
// 测试链接 : https://www.luogu.com.cn/problem/P4008
// 提交以下的code，提交时请把类名改成"Main"
// java实现的逻辑一定是正确的，但是内存占用过大，无法通过测试用例
// 因为这道题只考虑C++能通过的空间标准，根本没考虑java的用户
// 想通过用C++实现，本节课Code03_TextEditor2文件就是C++的实现
// 两个版本的逻辑完全一样，C++版本可以通过所有测试
// 讲解172，讲解块状链表时，本题又讲了一遍，分块的方法，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code03_TextEditor1 {

	public static int MAXN = 2000001;

	public static int head = 0;

	public static int cnt = 0;

	public static char[] key = new char[MAXN];

	public static int[] left = new int[MAXN];

	public static int[] right = new int[MAXN];

	public static int[] size = new int[MAXN];

	public static double[] priority = new double[MAXN];

	public static char[] ans = new char[MAXN];

	public static int ansi;

	public static void up(int i) {
		size[i] = size[left[i]] + size[right[i]] + 1;
	}

	public static void split(int l, int r, int i, int rank) {
		if (i == 0) {
			right[l] = left[r] = 0;
		} else {
			if (size[left[i]] + 1 <= rank) {
				right[l] = i;
				split(i, r, right[i], rank - size[left[i]] - 1);
			} else {
				left[r] = i;
				split(l, i, left[i], rank);
			}
			up(i);
		}
	}

	public static int merge(int l, int r) {
		if (l == 0 || r == 0) {
			return l + r;
		}
		if (priority[l] >= priority[r]) {
			right[l] = merge(right[l], r);
			up(l);
			return l;
		} else {
			left[r] = merge(l, left[r]);
			up(r);
			return r;
		}
	}

	public static void inorder(int i) {
		if (i != 0) {
			inorder(left[i]);
			ans[++ansi] = key[i];
			inorder(right[i]);
		}
	}

	public static void main(String[] args) throws IOException {
		FastReader in = new FastReader();
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		int n = in.nextInt();
		int pos = 0;
		String op;
		int x;
		for (int i = 1; i <= n; i++) {
			op = in.nextString();
			if (op.equals("Prev")) {
				pos--;
			} else if (op.equals("Next")) {
				pos++;
			} else if (op.equals("Move")) {
				pos = in.nextInt();
			} else if (op.equals("Delete")) {
				x = in.nextInt();
				split(0, 0, head, pos + x);
				int r = left[0];
				int lm = right[0];
				left[0] = right[0] = 0;
				split(0, 0, lm, pos);
				int l = right[0];
				left[0] = right[0] = 0;
				head = merge(l, r);
			} else if (op.equals("Insert")) {
				x = in.nextInt();
				split(0, 0, head, pos);
				int l = right[0];
				int r = left[0];
				left[0] = right[0] = 0;
				for (int j = 1; j <= x; j++) {
					key[++cnt] = in.nextChar();
					size[cnt] = 1;
					priority[cnt] = Math.random();
					l = merge(l, cnt);
				}
				head = merge(l, r);
			} else {
				x = in.nextInt();
				split(0, 0, head, pos + x);
				int r = left[0];
				int lm = right[0];
				left[0] = right[0] = 0;
				split(0, 0, lm, pos);
				int l = right[0];
				int m = left[0];
				left[0] = right[0] = 0;
				ansi = 0;
				inorder(m);
				head = merge(merge(l, m), r);
				for (int j = 1; j <= ansi; j++) {
					out.print((char) ans[j]);
				}
				out.println();
			}
		}
		out.flush();
		out.close();
	}

	// 读写工具类
	static class FastReader {
		final private int BUFFER_SIZE = 1 << 16;
		private final InputStream in;
		private final byte[] buffer;
		private int ptr, len;

		public FastReader() {
			in = System.in;
			buffer = new byte[BUFFER_SIZE];
			ptr = len = 0;
		}

		private boolean hasNextByte() throws IOException {
			if (ptr < len)
				return true;
			ptr = 0;
			len = in.read(buffer);
			return len > 0;
		}

		private byte readByte() throws IOException {
			if (!hasNextByte())
				return -1;
			return buffer[ptr++];
		}

		public char nextChar() throws IOException {
			byte c;
			do {
				c = readByte();
				if (c == -1)
					return 0;
			} while (c < 32 || c > 126);
			return (char) c;
		}

		public String nextString() throws IOException {
			byte b = readByte();
			while (isWhitespace(b)) {
				b = readByte();
			}
			StringBuilder sb = new StringBuilder(1000);
			while (!isWhitespace(b) && b != -1) {
				sb.append((char) b);
				b = readByte();
			}
			return sb.toString();
		}

		public int nextInt() throws IOException {
			int num = 0;
			byte b = readByte();
			while (isWhitespace(b))
				b = readByte();
			boolean minus = false;
			if (b == '-') {
				minus = true;
				b = readByte();
			}
			while (!isWhitespace(b) && b != -1) {
				num = num * 10 + (b - '0');
				b = readByte();
			}
			return minus ? -num : num;
		}

		private boolean isWhitespace(byte b) {
			return b == ' ' || b == '\n' || b == '\r' || b == '\t';
		}
	}

}
