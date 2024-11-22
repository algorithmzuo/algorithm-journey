package class152;

// 可持久化平衡树，FHQ-Treap实现，不用词频压缩，java版
// 认为一开始是0版本的树，为空树，实现如下操作，操作一共发生n次
// v 1 x : 基于v版本的树，增加一个x，生成新版本的树
// v 2 x : 基于v版本的树，删除一个x，生成新版本的树
// v 3 x : 基于v版本的树，查询x的排名，生成新版本的树状况=v版本状况
// v 4 x : 基于v版本的树，查询数据中排名为x的数，生成新版本的树状况=v版本状况
// v 5 x : 基于v版本的树，查询x的前驱，生成新版本的树状况=v版本状况
// v 6 x : 基于v版本的树，查询x的后继，生成新版本的树状况=v版本状况
// 不管什么操作，都基于某个v版本，操作完成后得到新版本的树，但v版本不会变化
// 如果x的前驱不存在，返回-2^31 + 1，如果x的后继不存在，返回+2^31 - 1
// 1 <= n <= 5 * 10^5
// -10^9 <= x <= +10^9
// 测试链接 : https://www.luogu.com.cn/problem/P3835
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

public class Code05_PersistentFHQTreap1 {

	public static int MAXN = 500001;

	public static int MAXM = MAXN * 50;

	public static int cnt = 0;

	public static int[] head = new int[MAXN];

	public static int[] key = new int[MAXM];

	public static int[] left = new int[MAXM];

	public static int[] right = new int[MAXM];

	public static int[] size = new int[MAXM];

	public static double[] priority = new double[MAXM];

	public static int copy(int i) {
		key[++cnt] = key[i];
		left[cnt] = left[i];
		right[cnt] = right[i];
		size[cnt] = size[i];
		priority[cnt] = priority[i];
		return cnt;
	}

	public static void up(int i) {
		size[i] = size[left[i]] + size[right[i]] + 1;
	}

	public static void split(int l, int r, int i, int num) {
		if (i == 0) {
			right[l] = left[r] = 0;
		} else {
			i = copy(i);
			if (key[i] <= num) {
				right[l] = i;
				split(i, r, right[i], num);
			} else {
				left[r] = i;
				split(l, i, left[i], num);
			}
			up(i);
		}
	}

	public static int merge(int l, int r) {
		if (l == 0 || r == 0) {
			return l + r;
		}
		if (priority[l] >= priority[r]) {
			l = copy(l);
			right[l] = merge(right[l], r);
			up(l);
			return l;
		} else {
			r = copy(r);
			left[r] = merge(l, left[r]);
			up(r);
			return r;
		}
	}

	// v : 新生成的版本编号
	// i : 基于的历史版本，树的头节点编号
	// num : 加入的数字
	public static void add(int v, int i, int num) {
		split(0, 0, i, num);
		int l = right[0];
		int r = left[0];
		// 后续可能基于0版本，去生成新版本的树，所以一定要清空，保证0版本始终是空树
		left[0] = right[0] = 0;
		key[++cnt] = num;
		size[cnt] = 1;
		priority[cnt] = Math.random();
		head[v] = merge(merge(l, cnt), r);
	}

	// v : 新生成的版本编号
	// i : 基于的历史版本，树的头节点编号
	// num : 加入的数字
	public static void remove(int v, int i, int num) {
		split(0, 0, i, num);
		int lm = right[0];
		int r = left[0];
		split(0, 0, lm, num - 1);
		int l = right[0];
		int m = left[0];
		// 后续可能基于0版本，去生成新版本的树，所以一定要清空，保证0版本始终是空树
		left[0] = right[0] = 0;
		head[v] = merge(merge(l, merge(left[m], right[m])), r);
	}

	public static int small(int i, int num) {
		if (i == 0) {
			return 0;
		}
		if (key[i] >= num) {
			return small(left[i], num);
		} else {
			return size[left[i]] + 1 + small(right[i], num);
		}
	}

	public static int index(int i, int x) {
		if (size[left[i]] >= x) {
			return index(left[i], x);
		} else if (size[left[i]] + 1 < x) {
			return index(right[i], x - size[left[i]] - 1);
		} else {
			return key[i];
		}
	}

	public static int pre(int i, int num) {
		if (i == 0) {
			return Integer.MIN_VALUE + 1;
		}
		if (key[i] >= num) {
			return pre(left[i], num);
		} else {
			return Math.max(key[i], pre(right[i], num));
		}
	}

	public static int post(int i, int num) {
		if (i == 0) {
			return Integer.MAX_VALUE;
		}
		if (key[i] <= num) {
			return post(right[i], num);
		} else {
			return Math.min(key[i], post(left[i], num));
		}
	}

	public static void main(String[] args) {
		FastReader in = new FastReader(System.in);
		FastWriter out = new FastWriter(System.out);
		int n = in.readInt();
		for (int i = 1, version, op, x; i <= n; i++) {
			version = in.readInt();
			op = in.readInt();
			x = in.readInt();
			if (op == 1) {
				add(i, head[version], x);
			} else if (op == 2) {
				remove(i, head[version], x);
			} else {
				head[i] = head[version];
				if (op == 3) {
					out.println(small(head[i], x) + 1);
				} else if (op == 4) {
					out.println(index(head[i], x));
				} else if (op == 5) {
					out.println(pre(head[i], x));
				} else {
					out.println(post(head[i], x));
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
