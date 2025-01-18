package class159;

// 最大异或和，java版
// 因为练的就是可持久化前缀树，所以就用在线算法
// 测试链接 : https://www.luogu.com.cn/problem/P4735
// 提交以下的code，提交时请把类名改成"Main"
// java实现的逻辑一定是正确的，但是通过不了所有测试用例
// 因为这道题根据C++的运行时间，制定通过标准，根本没考虑java的用户
// 想通过用C++实现，本节课Code01_MaxExclusiveOr2文件就是C++的实现
// 两个版本的逻辑完全一样，C++版本可以通过所有测试

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.InputStream;

public class Code01_MaxExclusiveOr1 {

	public static int MAXN = 600001;

	public static int MAXT = MAXN * 22;

	public static int BIT = 25;

	public static int n, m;

	public static int[] root = new int[MAXN];

	public static int[][] next = new int[MAXT][2];

	public static int[] size = new int[MAXT];

	public static int cnt = 0;

	public static int insert(int num, int bit, int i) {
		int rt = ++cnt;
		next[rt][0] = next[i][0];
		next[rt][1] = next[i][1];
		size[rt] = size[i] + 1;
		if (bit >= 0) {
			int cur = (num >> bit) & 1;
			next[rt][cur] = insert(num, bit - 1, next[rt][cur]);
		}
		return rt;
	}

	public static int query(int num, int bit, int u, int v) {
		if (bit < 0) {
			return 0;
		}
		int cur = (num >> bit) & 1;
		int opp = cur ^ 1;
		if (size[next[v][opp]] > size[next[u][opp]]) {
			return (1 << bit) + query(num, bit - 1, next[u][opp], next[v][opp]);
		} else {
			return query(num, bit - 1, next[u][cur], next[v][cur]);
		}
	}

	public static void main(String[] args) throws IOException {
		FastReader in = new FastReader();
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		root[0] = insert(0, BIT, 0);
		int eor = 0;
		for (int i = 1, num; i <= n; i++) {
			num = in.nextInt();
			eor ^= num;
			root[i] = insert(eor, BIT, root[i - 1]);
		}
		String op;
		int x, y, z;
		for (int i = 1; i <= m; i++) {
			op = in.next();
			if (op.equals("A")) {
				x = in.nextInt();
				eor ^= x;
				n++;
				root[n] = insert(eor, 25, root[n - 1]);
			} else {
				x = in.nextInt();
				y = in.nextInt();
				z = in.nextInt();
				if (x == 1) {
					out.println(query(eor ^ z, BIT, 0, root[y - 1]));
				} else {
					out.println(query(eor ^ z, BIT, root[x - 2], root[y - 1]));
				}
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

		public boolean hasNext() throws IOException {
			while (hasNextByte()) {
				byte b = buffer[ptr];
				if (!isWhitespace(b))
					return true;
				ptr++;
			}
			return false;
		}

		public String next() throws IOException {
			byte c;
			do {
				c = readByte();
				if (c == -1)
					return null;
			} while (c <= ' ');
			StringBuilder sb = new StringBuilder();
			while (c > ' ') {
				sb.append((char) c);
				c = readByte();
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

		public double nextDouble() throws IOException {
			double num = 0, div = 1;
			byte b = readByte();
			while (isWhitespace(b))
				b = readByte();
			boolean minus = false;
			if (b == '-') {
				minus = true;
				b = readByte();
			}
			while (!isWhitespace(b) && b != '.' && b != -1) {
				num = num * 10 + (b - '0');
				b = readByte();
			}
			if (b == '.') {
				b = readByte();
				while (!isWhitespace(b) && b != -1) {
					num += (b - '0') / (div *= 10);
					b = readByte();
				}
			}
			return minus ? -num : num;
		}

		private boolean isWhitespace(byte b) {
			return b == ' ' || b == '\n' || b == '\r' || b == '\t';
		}
	}

}