package class154;

// 断罪者，删除任意编号节点，java版
// 给定t，w，k，表示一共有t个人，死亡方式都为w，地狱阈值都为k，w和k含义稍后解释
// 每个人都给定n和m，表示这人一生有n件错事，有m次领悟
// 这个人的n件错事，给定对应的n个罪恶值，然后给定m次领悟，领悟类型如下
// 2 a   : 第a件错事的罪恶值变成0
// 3 a b : 第a件错事所在的集合中，最大罪恶值的错事，罪恶值减少b
//         如果减少后罪恶值变成负数，认为这件错事的罪恶值变为0
//         如果集合中，两件错事都是最大的罪恶值，取编号较小的错事
// 4 a b : 第a件错事所在的集合与第b件错事所在的集合合并
// 一个错事集合的罪恶值 = 这个集合中的最大罪恶值，只取一个
// 一个人的罪恶值 = 这个人所有错事集合的罪恶值累加起来
// 然后根据死亡方式w，对每个人的罪恶值做最后调整，然后打印这个人的下场
// 如果w==1，不调整
// 如果w==2，人的罪恶值 -= 错事集合的罪恶值中的最大值
// 如果w==3，人的罪恶值 += 错事集合的罪恶值中的最大值
// 如果一个人的罪恶值 == 0，打印"Gensokyo 0"
// 如果一个人的罪恶值  > k，打印"Hell "，然后打印罪恶值
// 如果一个人的罪恶值 <= k，打印"Heaven "，然后打印罪恶值
// 一共有t个人，所以最终会有t次打印
// 1 <= t <= 30
// 1 <= n <= 2 * 10^6
// 错事罪恶值可能很大，输入保证每个人的罪恶值用long类型不溢出
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

public class Code02_Convict1 {

	public static int MAXN = 2000001;

	public static int t, w, n, m;

	public static long k;

	public static long[] num = new long[MAXN];

	// up[i]表示节点i在左偏树结构上的父亲节点
	public static int[] up = new int[MAXN];

	public static int[] left = new int[MAXN];

	public static int[] right = new int[MAXN];

	public static int[] dist = new int[MAXN];

	// father[i]表示并查集里节点i的路径信息
	public static int[] father = new int[MAXN];

	public static void prepare() {
		dist[0] = -1;
		for (int i = 1; i <= n; i++) {
			up[i] = left[i] = right[i] = dist[i] = 0;
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
		// 维护大根堆，如果值一样，编号小的节点做头
		if (num[i] < num[j] || (num[i] == num[j] && i > j)) {
			tmp = i;
			i = j;
			j = tmp;
		}
		right[i] = merge(right[i], j);
		// 设置up信息
		up[right[i]] = i;
		if (dist[left[i]] < dist[right[i]]) {
			tmp = left[i];
			left[i] = right[i];
			right[i] = tmp;
		}
		dist[i] = dist[right[i]] + 1;
		father[left[i]] = father[right[i]] = i;
		return i;
	}

	// 节点i是所在左偏树的任意节点，删除节点i，返回整棵树的头节点编号
	public static int remove(int i) {
		int h = find(i);
		father[left[i]] = left[i];
		father[right[i]] = right[i];
		int s = merge(left[i], right[i]);
		int f = up[i];
		father[i] = s;
		up[s] = f;
		if (h != i) {
			father[s] = h;
			if (left[f] == i) {
				left[f] = s;
			} else {
				right[f] = s;
			}
			for (int d = dist[s], tmp; dist[f] > d + 1; f = up[f], d++) {
				dist[f] = d + 1;
				if (dist[left[f]] < dist[right[f]]) {
					tmp = left[f]; left[f] = right[f]; right[f] = tmp;
				}
			}
		}
		up[i] = left[i] = right[i] = dist[i] = 0;
		return father[s];
	}

	public static void reduce(int i, long v) {
		int h = remove(i);
		num[i] = Math.max(num[i] - v, 0);
		father[h] = father[i] = merge(h, i);
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