package class154;

// 数字序列，java版
// 给定一个长度为n的数组A，要求构造出一个长度为n的递增数组B
// 希望 |A[1] - B[1]| + |A[2] - B[2]| + ... + |A[n] - B[n]| 最小
// 打印这个最小值，然后打印数组B，如果有多个方案，只打印其中的一个
// 1 <= n <= 10^6
// 0 <= A[i] <= 2^32 - 1
// 测试链接 : https://www.luogu.com.cn/problem/P4331
// 提交以下的code，提交时请把类名改成"Main"，一些测试用例通过不了，空间超了
// 这是洛谷平台没有考虑其他语言导致的，同样的逻辑，C++实现就能完全通过
// C++实现的版本，就是Code05_NumberSequence2文件

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.util.InputMismatchException;

public class Code05_NumberSequence1 {

	public static int MAXN = 1000001;

	public static int n;

	public static long[] arr = new long[MAXN];

	// 左偏树需要
	public static int[] left = new int[MAXN];

	public static int[] right = new int[MAXN];

	public static int[] dist = new int[MAXN];

	// 并查集向上的路径
	public static int[] father = new int[MAXN];

	// 集合表达区域的左下标
	public static int[] from = new int[MAXN];

	// 集合表达区域的右下标
	public static int[] to = new int[MAXN];

	// 集合里有几个数字
	public static int[] size = new int[MAXN];

	// 单调栈
	public static int[] stack = new int[MAXN];

	// 构造的数组
	public static long[] ans = new long[MAXN];

	public static void prepare() {
		dist[0] = -1;
		for (int i = 1; i <= n; i++) {
			left[i] = right[i] = dist[i] = 0;
			father[i] = from[i] = to[i] = i;
			size[i] = 1;
		}
	}

	public static int find(int i) {
		father[i] = father[i] == i ? i : find(father[i]);
		return father[i];
	}

	public static int merge(int i, int j) {
		if (i == 0 || j == 0) {
			return i + j;
		}
		int tmp;
		// 维护大根堆
		if (arr[i] < arr[j]) {
			tmp = i;
			i = j;
			j = tmp;
		}
		right[i] = merge(right[i], j);
		if (dist[left[i]] < dist[right[i]]) {
			tmp = left[i];
			left[i] = right[i];
			right[i] = tmp;
		}
		dist[i] = dist[right[i]] + 1;
		father[left[i]] = father[right[i]] = i;
		return i;
	}

	public static int pop(int i) {
		father[left[i]] = left[i];
		father[right[i]] = right[i];
		father[i] = merge(left[i], right[i]);
		left[i] = right[i] = dist[i] = 0;
		return father[i];
	}

	public static long compute() {
		int stackSize = 0;
		for (int i = 1, pre, cur, s; i <= n; i++) {
			while (stackSize > 0) {
				pre = find(stack[stackSize]);
				cur = find(i);
				if (arr[pre] <= arr[cur]) {
					break;
				}
				s = size[pre] + size[cur];
				cur = merge(pre, cur);
				// 大根堆只保留到上中位数
				while (s > (i - from[pre] + 1 + 1) / 2) {
					cur = pop(cur);
					s--;
				}
				from[cur] = from[pre];
				to[cur] = i;
				size[cur] = s;
				stackSize--;
			}
			stack[++stackSize] = i;
		}
		long sum = 0;
		for (int i = 1, cur; i <= stackSize; i++) {
			cur = find(stack[i]);
			for (int j = from[cur]; j <= to[cur]; j++) {
				ans[j] = arr[cur];
				sum += Math.abs(ans[j] - arr[j]);
			}
		}
		return sum;
	}

	public static void main(String[] args) {
		FastReader in = new FastReader(System.in);
		FastWriter out = new FastWriter(System.out);
		n = in.readInt();
		prepare();
		for (int i = 1; i <= n; i++) {
			arr[i] = in.readLong() - i;
		}
		out.println(compute());
		for (int i = 1; i <= n; i++) {
			out.write((ans[i] + i));
			out.write(" ");
		}
		out.writeln();
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
