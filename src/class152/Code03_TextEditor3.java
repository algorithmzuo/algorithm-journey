package class152;

// 文本编辑器，能通过的java版本
// 一开始文本为空，光标在文本开头，也就是1位置，请实现如下6种操作
// Move k     : 将光标移动到第k个字符之后，操作保证光标不会到非法位置
// Insert n s : 在光标处插入长度为n的字符串s，光标位置不变
// Delete n   : 删除光标后的n个字符，光标位置不变，操作保证有足够字符
// Get n      : 输出光标后的n个字符，光标位置不变，操作保证有足够字符
// Prev       : 光标前移一个字符，操作保证光标不会到非法位置
// Next       : 光标后移一个字符，操作保证光标不会到非法位置
// Insert操作时，字符串s中ASCII码在[32,126]范围上的字符一定有n个，其他字符请过滤掉
// 测试链接 : https://www.luogu.com.cn/problem/P4008
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
// 一个能通过的版本，连数组都自己写扩容逻辑，IO彻底重写，看看就好
// 讲解172，讲解块状链表时，本题又讲了一遍，分块的方法，可以通过所有测试用例，更有学习意义

import java.util.Arrays;

public class Code03_TextEditor3 {

	static java.io.InputStream is = System.in;
	static byte[] inbuf = new byte[1024];
	static char[] str = new char[16];
	static int lenbuf, ptrbuf, b;
	static FastWriter out = new FastWriter();

	static byte[] key;
	static int[] lc, rc, sz;
	static double[] priority;
	static int head, no;

	static int create(byte k) {
		if (++no == key.length) {
			key = Arrays.copyOf(key, no << 1);
			lc = Arrays.copyOf(lc, no << 1);
			rc = Arrays.copyOf(rc, no << 1);
			sz = Arrays.copyOf(sz, no << 1);
			priority = Arrays.copyOf(priority, no << 1);
		}
		key[no] = k;
		sz[no] = 1;
		priority[no] = Math.random();
		return no;
	}

	static void up(int i) {
		sz[i] = sz[lc[i]] + sz[rc[i]] + 1;
	}

	static void split(int l, int r, int i, int rk) {
		if (i == 0) {
			rc[l] = lc[r] = 0;
			return;
		}
		if (sz[lc[i]] + 1 <= rk) {
			rc[l] = i;
			split(i, r, rc[i], rk - sz[lc[i]] - 1);
		} else {
			lc[r] = i;
			split(l, i, lc[i], rk);
		}
		up(i);
	}

	static int merge(int l, int r) {
		if (l == 0 || r == 0) {
			return l + r;
		}
		if (priority[l] >= priority[r]) {
			rc[l] = merge(rc[l], r);
			up(l);
			return l;
		} else {
			lc[r] = merge(l, lc[r]);
			up(r);
			return r;
		}
	}

	static void inorder(int i) {
		if (i == 0) {
			return;
		}
		inorder(lc[i]);
		out.write(key[i]);
		inorder(rc[i]);
	}

	public static void main(String[] args) {
		key = new byte[1];
		lc = new int[1];
		rc = new int[1];
		sz = new int[1];
		priority = new double[1];
		int i = 0, n, l, r, lx, mx, rx;
		byte c;
		int op = nextInt();
		while (op-- > 0) {
			switch (next()) {
			case 'M':
				i = nextInt();
				break;
			case 'I':
				split(0, 0, head, i);
				l = rc[0];
				r = lc[0];
				n = nextInt();
				while (n > 0) {
					c = readByte();
					if (c < 32) {
						continue;
					}
					l = merge(l, create(c));
					n--;
				}
				head = merge(l, r);
				break;
			case 'D':
				n = nextInt();
				l = i + 1;
				r = i + n;
				split(0, 0, head, i);
				lx = rc[0];
				mx = lc[0];
				split(0, 0, mx, n);
				mx = rc[0];
				rx = lc[0];
				head = merge(lx, rx);
				break;
			case 'G':
				n = nextInt();
				l = i + 1;
				r = i + n;
				split(0, 0, head, i);
				lx = rc[0];
				mx = lc[0];
				split(0, 0, mx, n);
				mx = rc[0];
				rx = lc[0];
				inorder(mx);
				out.writeln();
				head = merge(merge(lx, mx), rx);
				break;
			case 'P':
				i--;
				break;
			default:
				i++;
				break;
			}
		}
		out.flush();
	}

	static byte readByte() {
		if (ptrbuf == lenbuf) {
			ptrbuf = 0;
			try {
				lenbuf = is.read(inbuf);
			} catch (Exception e) {
			}
			if (lenbuf <= 0)
				return -1;
		}
		return inbuf[ptrbuf++];
	}

	static char next() {
		while ((b = readByte()) < 33)
			;
		int i = 0;
		while (b > 32) {
			str[i++] = (char) b;
			b = readByte();
		}
		return str[0];
	}

	static int nextInt() {
		while ((b = readByte()) < 33)
			;
		int num = b - '0';
		while ((b = readByte()) > 32)
			num = num * 10 + (b - '0');
		return num;
	}

	static class FastWriter {
		java.io.OutputStream out = System.out;
		int tr = 0, BUF_SIZE = 8192;
		byte[] buf = new byte[BUF_SIZE];

		int countDigits(int v) {
			return v >= 100000 ? v >= 10000000 ? v >= 100000000 ? v >= 1000000000 ? 10 : 9 : 8 : v >= 1000000 ? 7 : 6
					: v >= 1000 ? v >= 10000 ? 5 : 4 : v >= 100 ? 3 : v >= 10 ? 2 : 1;
		}

		int countDigits(long v) {
			return v >= 10000000000L ? 10 + countDigits((int) (v / 10000000000L))
					: v >= 1000000000 ? 10 : countDigits((int) v);
		}

		FastWriter write(byte b) {
			buf[tr++] = b;
			if (tr == BUF_SIZE)
				innerflush();
			return this;
		}

		FastWriter write(char c) {
			return write((byte) c);
		}

		FastWriter write(int x) {
			if (x == Integer.MIN_VALUE) {
				return write((long) x);
			}
			if (tr + 12 >= BUF_SIZE)
				innerflush();
			if (x < 0) {
				write((byte) '-');
				x = -x;
			}
			int d = countDigits(x);
			for (int i = tr + d - 1; i >= tr; i--) {
				buf[i] = (byte) ('0' + x % 10);
				x /= 10;
			}
			tr += d;
			return this;
		}

		FastWriter write(long x) {
			if (x == Long.MIN_VALUE) {
				return write("" + x);
			}
			if (tr + 21 >= BUF_SIZE)
				innerflush();
			if (x < 0) {
				write((byte) '-');
				x = -x;
			}
			int d = countDigits(x);
			for (int i = tr + d - 1; i >= tr; i--) {
				buf[i] = (byte) ('0' + x % 10);
				x /= 10;
			}
			tr += d;
			return this;
		}

		FastWriter write(double x, int precision) {
			if (x < 0) {
				write('-');
				x = -x;
			}
			x += Math.pow(10, -precision) / 2;
			write((long) x).write(".");
			x -= (long) x;
			for (int i = 0; i < precision; i++) {
				x *= 10;
				write((char) ('0' + (int) x));
				x -= (int) x;
			}
			return this;
		}

		FastWriter write(String s) {
			for (int i = 0; i < s.length(); i++) {
				buf[tr++] = (byte) s.charAt(i);
				if (tr == BUF_SIZE)
					innerflush();
			}
			return this;
		}

		void print(char c) {
			write(c);
		}

		void print(String s) {
			write(s);
		}

		void print(int x) {
			write(x);
		}

		void print(long x) {
			write(x);
		}

		void print(double x, int precision) {
			write(x, precision);
		}

		void writeln() {
			write((byte) '\n');
		}

		void println(char c) {
			write(c).writeln();
		}

		void println(int x) {
			write(x).writeln();
		}

		void println(long x) {
			write(x).writeln();
		}

		void println(double x, int precision) {
			write(x, precision).writeln();
		}

		void println(String s) {
			write(s).writeln();
		}

		private void innerflush() {
			try {
				out.write(buf, 0, tr);
				tr = 0;
			} catch (Exception e) {
			}
		}

		void flush() {
			innerflush();
			try {
				out.flush();
			} catch (Exception e) {
			}
		}
	}

}