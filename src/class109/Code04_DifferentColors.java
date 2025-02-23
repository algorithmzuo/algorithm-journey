package class109;

// HH的项链
// 一共有n个位置，每个位置颜色给定，i位置的颜色是arr[i]
// 一共有m个查询，question[i] = {li, ri}
// 表示第i条查询想查arr[li..ri]范围上一共有多少种不同颜色
// 返回每条查询的答案
// 1 <= n、m、arr[i] <= 10^6
// 1 <= li <= ri <= n
// 测试链接 : https://www.luogu.com.cn/problem/P1972
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
// 代码逻辑和课上讲的完全一致，但是重写了读写工具类，增加了io效率

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.Arrays;

public class Code04_DifferentColors {

	public static int MAXN = 1000001;

	public static int[] arr = new int[MAXN];

	public static int[][] query = new int[MAXN][3];

	public static int[] ans = new int[MAXN];

	public static int[] map = new int[MAXN];

	public static int[] tree = new int[MAXN];

	public static int n, m;

	public static int lowbit(int i) {
		return i & -i;
	}

	public static void add(int i, int v) {
		while (i <= n) {
			tree[i] += v;
			i += lowbit(i);
		}
	}

	public static int sum(int i) {
		int ans = 0;
		while (i > 0) {
			ans += tree[i];
			i -= lowbit(i);
		}
		return ans;
	}

	public static int range(int l, int r) {
		return sum(r) - sum(l - 1);
	}

	public static void compute() {
		Arrays.sort(query, 1, m + 1, (a, b) -> a[1] - b[1]);
		for (int s = 1, q = 1, l, r, i; q <= m; q++) {
			r = query[q][1];
			for (; s <= r; s++) {
				int color = arr[s];
				if (map[color] != 0) {
					add(map[color], -1);
				}
				add(s, 1);
				map[color] = s;
			}
			l = query[q][0];
			i = query[q][2];
			ans[i] = range(l, r);
		}
	}

	public static void main(String[] args) throws IOException {
		FastReader in = new FastReader();
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		for (int i = 1; i <= n; i++) {
			arr[i] = in.nextInt();
		}
		m = in.nextInt();
		for (int i = 1; i <= m; i++) {
			query[i][0] = in.nextInt();
			query[i][1] = in.nextInt();
			query[i][2] = i;
		}
		compute();
		for (int i = 1; i <= m; i++) {
			out.write(ans[i] + "\n");
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
