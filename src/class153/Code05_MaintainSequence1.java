package class153;

// 维护数列(java版)
// 初始时给定一个数列，实现如下六种操作
// INSERT posi tot ...  : 在第posi个数字之后，插入长度为tot的数组，由...代表
// DELETE posi tot      : 从第posi个数字开始，删除长度为tot的部分
// MAKE-SAME posi tot c : 从第posi个数字开始，长度为tot的部分，值都设置成c
// REVERSE posi tot     : 从第posi个数字开始，翻转长度为tot的部分
// GET-SUM posi tot     : 从第posi个数字开始，查询长度为tot的部分的累加和
// MAX-SUM              : 查询整个数列中，非空子数组的最大累加和
// 任何时刻输入保证至少有一个数字在数列中，并且所有操作都合法
// 插入数字总数很多，但是任何时刻数列中最多有5 * 10^5个数，使用总空间要和该数量有关
// 测试链接 : https://www.luogu.com.cn/problem/P2042
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.util.InputMismatchException;

public class Code05_MaintainSequence1 {

	public static int MAXN = 500005;

	public static int INF = 1000000001;

	public static int head = 0;

	public static int[] arr = new int[MAXN];

	public static int[] num = new int[MAXN];

	public static int[] father = new int[MAXN];

	public static int[] left = new int[MAXN];

	public static int[] right = new int[MAXN];

	public static int[] size = new int[MAXN];

	// 这个数组里拿空间编号
	public static int[] space = new int[MAXN];

	public static int si;

	// 维护区间累加和信息
	public static int[] sum = new int[MAXN];

	// 维护区间子数组最大累加和信息，要求不能为空
	public static int[] all = new int[MAXN];

	// 维护区间前缀最大累加和信息，可以空
	public static int[] pre = new int[MAXN];

	// 维护区间后缀最大累加和信息，可以空
	public static int[] suf = new int[MAXN];

	// 懒更新信息，区间是否重新设了值
	public static boolean[] update = new boolean[MAXN];

	// 懒更新信息，如果区间重新设了值，设置成了什么
	public static int[] change = new int[MAXN];

	// 懒更新信息，区间是否发生了翻转
	public static boolean[] reverse = new boolean[MAXN];

	public static void up(int i) {
		int l = left[i], r = right[i];
		size[i] = size[l] + size[r] + 1;
		sum[i] = sum[l] + sum[r] + num[i];
		all[i] = Math.max(Math.max(all[l], all[r]), suf[l] + num[i] + pre[r]);
		pre[i] = Math.max(pre[l], sum[l] + num[i] + pre[r]);
		suf[i] = Math.max(suf[r], suf[l] + num[i] + sum[r]);
	}

	public static int lr(int i) {
		return right[father[i]] == i ? 1 : 0;
	}

	public static void rotate(int i) {
		int f = father[i], g = father[f], soni = lr(i), sonf = lr(f);
		if (soni == 1) {
			right[f] = left[i];
			if (right[f] != 0) {
				father[right[f]] = f;
			}
			left[i] = f;
		} else {
			left[f] = right[i];
			if (left[f] != 0) {
				father[left[f]] = f;
			}
			right[i] = f;
		}
		if (g != 0) {
			if (sonf == 1) {
				right[g] = i;
			} else {
				left[g] = i;
			}
		}
		father[f] = i;
		father[i] = g;
		up(f);
		up(i);
	}

	public static void splay(int i, int goal) {
		int f = father[i], g = father[f];
		while (f != goal) {
			if (g != goal) {
				if (lr(i) == lr(f)) {
					rotate(f);
				} else {
					rotate(i);
				}
			}
			rotate(i);
			f = father[i];
			g = father[f];
		}
		if (goal == 0) {
			head = i;
		}
	}

	public static void setValue(int i, int val) {
		if (i != 0) {
			update[i] = true;
			change[i] = val;
			num[i] = val;
			sum[i] = size[i] * val;
			all[i] = Math.max(sum[i], val);
			pre[i] = Math.max(sum[i], 0);
			suf[i] = Math.max(sum[i], 0);
		}
	}

	public static void setReverse(int i) {
		if (i != 0) {
			int tmp = pre[i];
			pre[i] = suf[i];
			suf[i] = tmp;
			reverse[i] = !reverse[i];
		}
	}

	public static void down(int i) {
		if (update[i]) {
			setValue(left[i], change[i]);
			setValue(right[i], change[i]);
			update[i] = false;
		}
		if (reverse[i]) {
			int tmp = left[i];
			left[i] = right[i];
			right[i] = tmp;
			setReverse(left[i]);
			setReverse(right[i]);
			reverse[i] = false;
		}
	}

	public static int init(int val) {
		int i = space[si--];
		size[i] = 1;
		num[i] = sum[i] = all[i] = val;
		pre[i] = suf[i] = Math.max(val, 0);
		father[i] = left[i] = right[i] = 0;
		update[i] = reverse[i] = false;
		return i;
	}

	public static int build(int l, int r) {
		int mid = (l + r) / 2;
		int root = init(arr[mid]);
		if (l < mid) {
			left[root] = build(l, mid - 1);
			father[left[root]] = root;
		}
		if (mid < r) {
			right[root] = build(mid + 1, r);
			father[right[root]] = root;
		}
		up(root);
		return root;
	}

	public static int find(int rank) {
		int i = head;
		while (i != 0) {
			down(i);
			if (size[left[i]] + 1 == rank) {
				return i;
			} else if (size[left[i]] >= rank) {
				i = left[i];
			} else {
				rank -= size[left[i]] + 1;
				i = right[i];
			}
		}
		return 0;
	}

	public static void insert(int rank, int n) {
		if (rank == 0) {
			head = build(1, n);
		} else {
			int l = find(rank);
			int r = find(rank + 1);
			splay(l, 0);
			splay(r, l);
			left[r] = build(1, n);
			father[left[r]] = r;
			up(r);
			up(l);
		}
	}

	public static void recycle(int i) {
		if (i != 0) {
			space[++si] = i;
			recycle(left[i]);
			recycle(right[i]);
		}
	}

	public static void delete(int rank, int n) {
		int l = find(rank - 1);
		int r = find(rank + n);
		splay(l, 0);
		splay(r, l);
		recycle(left[r]);
		left[r] = 0;
		up(r);
		up(l);
	}

	public static void reset(int rank, int n, int val) {
		int l = find(rank - 1);
		int r = find(rank + n);
		splay(l, 0);
		splay(r, l);
		setValue(left[r], val);
		up(r);
		up(l);
	}

	public static void reverse(int rank, int n) {
		int l = find(rank - 1);
		int r = find(rank + n);
		splay(l, 0);
		splay(r, l);
		setReverse(left[r]);
		up(r);
		up(l);
	}

	public static int querySum(int rank, int n) {
		int l = find(rank - 1);
		int r = find(rank + n);
		splay(l, 0);
		splay(r, l);
		return sum[left[r]];
	}

	public static int queryMax() {
		return all[head];
	}

	public static void main(String[] args) {
		FastReader in = new FastReader(System.in);
		FastWriter out = new FastWriter(System.out);
		int n = in.readInt();
		int m = in.readInt();
		// 所有可用空间编号，进入space数组
		si = MAXN - 1;
		for (int i = 1; i <= si; i++) {
			space[i] = i;
		}
		// 这里很重要，一方面准备好最左和最右的准备值
		// 另一方面设置all[0] = 极小值
		// 表示没有范围时，子数组最大累加和为极小值，因为不能为空
		arr[1] = arr[n + 2] = all[0] = -INF;
		for (int i = 1, j = 2; i <= n; i++, j++) {
			arr[j] = in.readInt();
		}
		// 建立初始树
		insert(0, n + 2);
		String op;
		for (int i = 1, posi, tot, c; i <= m; i++) {
			op = in.readString();
			if (op.equals("MAX-SUM")) {
				out.println(queryMax());
			} else {
				// 因为有最左的准备值，所以位置要后移一位
				posi = in.readInt() + 1;
				tot = in.readInt();
				if (op.equals("INSERT")) {
					for (int j = 1; j <= tot; j++) {
						arr[j] = in.readInt();
					}
					insert(posi, tot);
				} else if (op.equals("DELETE")) {
					delete(posi, tot);
				} else if (op.equals("MAKE-SAME")) {
					c = in.readInt();
					reset(posi, tot, c);
				} else if (op.equals("REVERSE")) {
					reverse(posi, tot);
				} else {
					out.println(querySum(posi, tot));
				}
			}
		}
		out.flush();
		out.close();
	}

	// 快读
	public static class FastReader {
		InputStream is;
		private byte[] inbuf = new byte[1024];
		public int lenbuf = 0;
		public int ptrbuf = 0;

		public FastReader(final InputStream is) {
			this.is = is;
		}

		public String readString() {
			char cur;
			do {
				cur = (char) readByte();
			} while (cur == ' ' || cur == '\n');
			StringBuilder builder = new StringBuilder();
			while (cur != ' ' && cur != '\n') {
				builder.append(cur);
				cur = (char) readByte();
			}
			return builder.toString();
		}

		public int readByte() {
			if (lenbuf == -1) {
				throw new InputMismatchException();
			}
			if (ptrbuf >= lenbuf) {
				ptrbuf = 0;
				try {
					lenbuf = is.read(inbuf);
				} catch (IOException e) {
					throw new InputMismatchException();
				}
				if (lenbuf <= 0) {
					return -1;
				}
			}
			return inbuf[ptrbuf++];
		}

		public int readInt() {
			return (int) readLong();
		}

		public long readLong() {
			long num = 0;
			int b;
			boolean minus = false;
			while ((b = readByte()) != -1 && !((b >= '0' && b <= '9') || b == '-'))
				;
			if (b == '-') {
				minus = true;
				b = readByte();
			}

			while (true) {
				if (b >= '0' && b <= '9') {
					num = num * 10 + (b - '0');
				} else {
					return minus ? -num : num;
				}
				b = readByte();
			}
		}
	}

	// 快写
	public static class FastWriter {
		private static final int BUF_SIZE = 1 << 13;
		private final byte[] buf = new byte[BUF_SIZE];
		private OutputStream out;
		private Writer writer;
		private int ptr = 0;

		public FastWriter(Writer writer) {
			this.writer = new BufferedWriter(writer);
			out = new ByteArrayOutputStream();
		}

		public FastWriter(OutputStream os) {
			this.out = os;
		}

		public FastWriter(String path) {
			try {
				this.out = new FileOutputStream(path);
			} catch (FileNotFoundException e) {
				throw new RuntimeException("FastWriter");
			}
		}

		public FastWriter write(byte b) {
			buf[ptr++] = b;
			if (ptr == BUF_SIZE) {
				innerflush();
			}
			return this;
		}

		public FastWriter write(String s) {
			s.chars().forEach(c -> {
				buf[ptr++] = (byte) c;
				if (ptr == BUF_SIZE) {
					innerflush();
				}
			});
			return this;
		}

		private static int countDigits(long l) {
			if (l >= 1000000000000000000L) {
				return 19;
			}
			if (l >= 100000000000000000L) {
				return 18;
			}
			if (l >= 10000000000000000L) {
				return 17;
			}
			if (l >= 1000000000000000L) {
				return 16;
			}
			if (l >= 100000000000000L) {
				return 15;
			}
			if (l >= 10000000000000L) {
				return 14;
			}
			if (l >= 1000000000000L) {
				return 13;
			}
			if (l >= 100000000000L) {
				return 12;
			}
			if (l >= 10000000000L) {
				return 11;
			}
			if (l >= 1000000000L) {
				return 10;
			}
			if (l >= 100000000L) {
				return 9;
			}
			if (l >= 10000000L) {
				return 8;
			}
			if (l >= 1000000L) {
				return 7;
			}
			if (l >= 100000L) {
				return 6;
			}
			if (l >= 10000L) {
				return 5;
			}
			if (l >= 1000L) {
				return 4;
			}
			if (l >= 100L) {
				return 3;
			}
			if (l >= 10L) {
				return 2;
			}
			return 1;
		}

		public FastWriter write(long x) {
			if (x == Long.MIN_VALUE) {
				return write("" + x);
			}
			if (ptr + 21 >= BUF_SIZE) {
				innerflush();
			}
			if (x < 0) {
				write((byte) '-');
				x = -x;
			}
			int d = countDigits(x);
			for (int i = ptr + d - 1; i >= ptr; i--) {
				buf[i] = (byte) ('0' + x % 10);
				x /= 10;
			}
			ptr += d;
			return this;
		}

		public FastWriter writeln(long x) {
			return write(x).writeln();
		}

		public FastWriter writeln() {
			return write((byte) '\n');
		}

		private void innerflush() {
			try {
				out.write(buf, 0, ptr);
				ptr = 0;
			} catch (IOException e) {
				throw new RuntimeException("innerflush");
			}
		}

		public void flush() {
			innerflush();
			try {
				if (writer != null) {
					writer.write(((ByteArrayOutputStream) out).toString());
					out = new ByteArrayOutputStream();
					writer.flush();
				} else {
					out.flush();
				}
			} catch (IOException e) {
				throw new RuntimeException("flush");
			}
		}

		public FastWriter println(long x) {
			return writeln(x);
		}

		public void close() {
			flush();
			try {
				out.close();
			} catch (Exception e) {
			}
		}

	}

}
