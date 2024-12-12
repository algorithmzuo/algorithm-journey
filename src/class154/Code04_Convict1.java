package class154;

// 断罪者，java版
// 测试链接 : https://www.luogu.com.cn/problem/P4971
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

public class Code04_Convict1 {

	public static int MAXN = 2000001;

	public static long[] num = new long[MAXN];

	public static int[] left = new int[MAXN];

	public static int[] right = new int[MAXN];

	public static int[] dist = new int[MAXN];

	public static int[] father = new int[MAXN];

	public static int t, w, n, m;

	public static long k;

	public static void prepare() {
		for (int i = 1; i <= n; i++) {
			left[i] = right[i] = dist[i] = 0;
			father[i] = i;
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
		if (num[i] < num[j] || (num[i] == num[j] && i > j)) {
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

	public static void reduce(int i, long v) {
		num[i] = Math.max(num[i] - v, 0);
		int l = find(i);
		father[left[i]] = left[i];
		father[right[i]] = right[i];
		int r = merge(left[i], right[i]);
		left[i] = right[i] = dist[i] = 0;
		father[i] = merge(l, r);
	}

	public static long compute() {
		long ans = 0;
		long max = 0;
		for (int i = 1; i <= n; i++) {
			if (father[i] == i) {
				ans += num[i];
				max = Math.max(max, num[i]);
			}
		}
		if (w == 2) {
			ans -= max;
		} else if (w == 3) {
			ans += max;
		}
		return ans;
	}

	public static void main(String[] args) {
		FastReader in = new FastReader(System.in);
		FastWriter out = new FastWriter(System.out);
		t = in.readInt();
		w = in.readInt();
		k = in.readLong();
		for (int i = 1; i <= t; i++) {
			n = in.readInt();
			m = in.readInt();
			prepare();
			for (int j = 1; j <= n; j++) {
				num[j] = in.readLong();
			}
			for (int j = 1, op, a, b; j <= m; j++) {
				op = in.readInt();
				a = in.readInt();
				if (op == 2) {
					reduce(a, num[a]);
				} else if (op == 3) {
					b = in.readInt();
					reduce(find(a), b);
				} else {
					b = in.readInt();
					int l = find(a);
					int r = find(b);
					if (l != r) {
						merge(l, r);
					}
				}
			}
			long ans = compute();
			if (ans == 0) {
				out.write("Gensokyo ");
			} else if (ans > k) {
				out.write("Hell ");
			} else {
				out.write("Heaven ");
			}
			out.println(ans);
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