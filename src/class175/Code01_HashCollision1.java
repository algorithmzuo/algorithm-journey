package class175;

// 哈希冲突，java版
// 给定一个长度为n的数组arr，接下来有m条操作，操作格式如下
// 操作 A x y : 下标 % x == y，符合要求的下标，把对应的值累加起来，打印结果
// 操作 C x y : arr[x]的值改成y
// 1 <= n、m <= 1.5 * 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P3396
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code01_HashCollision1 {

	public static int MAXN = 150001;
	public static int MAXB = 401;
	public static int n, m, blen;
	public static int[] arr = new int[MAXN];
	public static long[][] dp = new long[MAXB][MAXB];

	public static long query(int x, int y) {
		if (x <= blen) {
			return dp[x][y];
		}
		long ans = 0;
		for (int i = y; i <= n; i += x) {
			ans += arr[i];
		}
		return ans;
	}

	public static void update(int x, int y) {
		int delta = y - arr[x];
		arr[x] = y;
		for (int mod = 1; mod <= blen; mod++) {
			dp[mod][x % mod] += delta;
		}
	}

	public static void prepare() {
		blen = (int) Math.sqrt(n);
		for (int x = 1; x <= blen; x++) {
			for (int i = 1; i <= n; i++) {
				dp[x][i % x] += arr[i];
			}
		}
	}

	public static void main(String[] args) throws IOException {
		FastReader in = new FastReader();
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		for (int i = 1; i <= n; i++) {
			arr[i] = in.nextInt();
		}
		prepare();
		char op;
		int x, y;
		for (int i = 1; i <= m; i++) {
			op = in.nextChar();
			x = in.nextInt();
			y = in.nextInt();
			if (op == 'A') {
				out.println(query(x, y));
			} else {
				update(x, y);
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
			} while (c <= ' ');
			char ans = 0;
			while (c > ' ') {
				ans = (char) c;
				c = readByte();
			}
			return ans;
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
