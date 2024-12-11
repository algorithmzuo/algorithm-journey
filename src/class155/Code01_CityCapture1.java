package class155;

// 城池攻占，java版
// 不和并查集结合，和懒更新结合，
// 输入保证，如果城市a管辖城市b，必有a < b
// 测试链接 : https://www.luogu.com.cn/problem/P3261
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

public class Code01_CityCapture1 {

	public static int MAXN = 300001;

	public static int n, m;

	public static long[] defend = new long[MAXN];

	public static int[] belong = new int[MAXN];

	public static int[] op = new int[MAXN];

	public static long[] gain = new long[MAXN];

	public static int[] deep = new int[MAXN];

	public static int[] top = new int[MAXN];

	public static int[] sacrifice = new int[MAXN];

	public static long[] attack = new long[MAXN];

	public static int[] born = new int[MAXN];

	public static int[] die = new int[MAXN];

	public static int[] left = new int[MAXN];

	public static int[] right = new int[MAXN];

	public static int[] dist = new int[MAXN];

	public static long[] mul = new long[MAXN];

	public static long[] add = new long[MAXN];

	public static void prepare() {
		for (int i = 1; i <= n; i++) {
			sacrifice[i] = top[i] = 0;
		}
		for (int i = 1; i <= m; i++) {
			left[i] = right[i] = dist[i] = 0;
			mul[i] = 1;
			add[i] = 0;
		}
	}

	public static void down(int i) {
		if (mul[i] != 1 || add[i] != 0) {
			int l = left[i];
			int r = right[i];
			if (l != 0) {
				attack[l] = attack[l] * mul[i] + add[i];
				mul[l] *= mul[i];
				add[l] = add[l] * mul[i] + add[i];
			}
			if (r != 0) {
				attack[r] = attack[r] * mul[i] + add[i];
				mul[r] *= mul[i];
				add[r] = add[r] * mul[i] + add[i];
			}
			mul[i] = 1;
			add[i] = 0;
		}
	}

	public static int merge(int i, int j) {
		if (i == 0 || j == 0) {
			return i + j;
		}
		int tmp;
		if (attack[i] > attack[j]) {
			tmp = i;
			i = j;
			j = tmp;
		}
		down(i);
		down(j);
		right[i] = merge(right[i], j);
		if (dist[left[i]] < dist[right[i]]) {
			tmp = left[i];
			left[i] = right[i];
			right[i] = tmp;
		}
		dist[i] = dist[right[i]] + 1;
		return i;
	}

	public static int pop(int i) {
		down(i);
		int ans = merge(left[i], right[i]);
		left[i] = right[i] = 0;
		return ans;
	}

	public static void upgrade(int i, int t, long v) {
		if (t == 0) {
			add[i] += v;
			attack[i] += v;
		} else {
			mul[i] *= v;
			add[i] *= v;
			attack[i] *= v;
		}
	}

	public static void compute() {
		deep[1] = 1;
		for (int i = 2; i <= n; i++) {
			deep[i] = deep[belong[i]] + 1;
		}
		for (int i = 1; i <= m; i++) {
			if (top[born[i]] == 0) {
				top[born[i]] = i;
			} else {
				top[born[i]] = merge(top[born[i]], i);
			}
		}
		for (int i = n; i >= 1; i--) {
			while (top[i] != 0 && attack[top[i]] < defend[i]) {
				die[top[i]] = i;
				sacrifice[i]++;
				top[i] = pop(top[i]);
			}
			if (top[i] != 0) {
				upgrade(top[i], op[i], gain[i]);
				if (top[belong[i]] == 0) {
					top[belong[i]] = top[i];
				} else {
					top[belong[i]] = merge(top[belong[i]], top[i]);
				}
			}
		}
	}

	public static void main(String[] args) {
		FastReader in = new FastReader(System.in);
		FastWriter out = new FastWriter(System.out);
		n = in.readInt();
		m = in.readInt();
		prepare();
		for (int i = 1; i <= n; i++) {
			defend[i] = in.readLong();
		}
		for (int i = 2; i <= n; i++) {
			belong[i] = in.readInt();
			op[i] = in.readInt();
			gain[i] = in.readLong();
		}
		for (int i = 1; i <= m; i++) {
			attack[i] = in.readLong();
			born[i] = in.readInt();
		}
		compute();
		for (int i = 1; i <= n; i++) {
			out.println(sacrifice[i]);
		}
		for (int i = 1; i <= m; i++) {
			out.println(deep[born[i]] - deep[die[i]]);
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
