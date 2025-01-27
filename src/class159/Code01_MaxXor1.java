package class159;

// 最大异或和，java版
// 非负序列arr的初始长度为n，一共有m条操作，每条操作是如下两种类型中的一种
// A x     : arr的末尾增加数字x，arr的长度n也增加1
// Q l r x : l~r这些位置中，选一个位置p，现在希望
//           arr[p] ^ arr[p+1] ^ .. ^ arr[n] ^ x 这个值最大
//           打印这个最大值
// 1 <= n、m <= 3 * 10^5
// 0 <= arr[i]、x <= 10^7
// 因为练的就是可持久化前缀树，所以就用在线算法，不要使用离线算法
// 测试链接 : https://www.luogu.com.cn/problem/P4735
// 提交以下的code，提交时请把类名改成"Main"
// java实现的逻辑一定是正确的，但是有一些测试用例通过不了
// 因为这道题根据C++的运行时间，制定通过标准，根本没考虑java的用户
// 想通过用C++实现，本节课Code01_MaxXor2文件就是C++的实现
// 两个版本的逻辑完全一样，C++版本可以通过所有测试

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.InputStream;

public class Code01_MaxXor1 {

	public static int MAXN = 600001;

	public static int MAXT = MAXN * 22;

	public static int BIT = 25;

	public static int n, m, eor;

	public static int[] root = new int[MAXN];

	public static int[][] tree = new int[MAXT][2];

	public static int[] pass = new int[MAXT];

	public static int cnt = 0;

	public static int insert(int num, int i) {
		int rt = ++cnt;
		tree[rt][0] = tree[i][0];
		tree[rt][1] = tree[i][1];
		pass[rt] = pass[i] + 1;
		for (int b = BIT, path, pre = rt, cur; b >= 0; b--, pre = cur) {
			path = (num >> b) & 1;
			i = tree[i][path];
			cur = ++cnt;
			tree[cur][0] = tree[i][0];
			tree[cur][1] = tree[i][1];
			pass[cur] = pass[i] + 1;
			tree[pre][path] = cur;
		}
		return rt;
	}

	public static int query(int num, int u, int v) {
		int ans = 0;
		for (int b = BIT, path, best; b >= 0; b--) {
			path = (num >> b) & 1;
			best = path ^ 1;
			if (pass[tree[v][best]] > pass[tree[u][best]]) {
				ans += 1 << b;
				u = tree[u][best];
				v = tree[v][best];
			} else {
				u = tree[u][path];
				v = tree[v][path];
			}
		}
		return ans;
	}

	public static void main(String[] args) throws IOException {
		FastReader in = new FastReader();
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		eor = 0;
		// 其实有空版本，空版本的头节点编号是0
		root[0] = insert(eor, 0);
		for (int i = 1, num; i <= n; i++) {
			num = in.nextInt();
			eor ^= num;
			root[i] = insert(eor, root[i - 1]);
		}
		String op;
		int x, y, z;
		for (int i = 1; i <= m; i++) {
			op = in.next();
			if (op.equals("A")) {
				x = in.nextInt();
				eor ^= x;
				n++;
				root[n] = insert(eor, root[n - 1]);
			} else {
				x = in.nextInt(); // l
				y = in.nextInt(); // r
				z = in.nextInt(); // x
				if (x == 1) {
					out.println(query(eor ^ z, 0, root[y - 1]));
				} else {
					out.println(query(eor ^ z, root[x - 2], root[y - 1]));
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