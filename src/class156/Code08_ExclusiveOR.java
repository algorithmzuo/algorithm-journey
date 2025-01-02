package class156;

// 异或关系
// 测试链接 : https://acm.hdu.edu.cn/showproblem.php?pid=3234
// 测试链接 : https://www.luogu.com.cn/problem/UVA12232
// 测试链接 : https://vjudge.net/problem/UVA-12232
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

public class Code08_ExclusiveOR {

	public static int MAXN = 20002;

	public static int MAXK = 21;

	public static int t, n, m;

	public static boolean conflict;

	public static int cnti;

	public static int[] father = new int[MAXN];

	public static int[] exclu = new int[MAXN];

	public static int[] nums = new int[MAXK];

	public static int[] collect = new int[MAXK];

	public static void prepare() {
		conflict = false;
		cnti = 0;
		for (int i = 0; i <= n; i++) {
			father[i] = i;
			exclu[i] = 0;
		}
	}

	public static int find(int i) {
		if (i != father[i]) {
			int tmp = father[i];
			father[i] = find(tmp);
			exclu[i] ^= exclu[tmp];
		}
		return father[i];
	}

	public static boolean opi(int l, int r, int v) {
		cnti++;
		int lf = find(l), rf = find(r);
		if (lf == rf) {
			if ((exclu[l] ^ exclu[r]) != v) {
				conflict = true;
				return false;
			}
		} else {
			if (lf == n) {
				lf = rf;
				rf = n;
			}
			father[lf] = rf;
			exclu[lf] = exclu[r] ^ exclu[l] ^ v;
		}
		return true;
	}

	public static int opq(int k) {
		int ans = 0, ci = 0, fa;
		for (int i = 1; i <= k; i++) {
			fa = find(nums[i]);
			ans ^= exclu[nums[i]];
			collect[++ci] = fa;
		}
		Arrays.sort(collect, 1, ci + 1);
		for (int l = 1, r = 1; l <= ci; l = ++r) {
			while (r + 1 <= ci && collect[r + 1] == collect[l]) {
				r++;
			}
			if ((r - l + 1) % 2 != 0) {
				if (collect[l] == n) {
					ans ^= exclu[n];
				} else {
					return -1;
				}
			}
		}
		return ans;
	}

	public static void main(String[] args) throws IOException {
		FastReader in = new FastReader();
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		t = 0;
		n = in.nextInt();
		m = in.nextInt();
		while (n != 0 || m != 0) {
			prepare();
			out.println("Case " + (++t) + ":");
			for (int i = 1; i <= m; i++) {
				String op = in.next();
				if (op.equals("I")) {
					int l, r, v;
					in.numbers();
					if (!conflict) {
						if (in.size == 2) {
							l = in.a; r = n; v = in.b;
						} else {
							l = in.a; r = in.b; v = in.c;
						}
						if (!opi(l, r, v)) {
							out.println("The first " + cnti + " facts are conflicting.");
						}
					}
				} else {
					int k = in.nextInt();
					for (int j = 1; j <= k; j++) {
						nums[j] = in.nextInt();
					}
					if (!conflict) {
						int ans = opq(k);
						if (ans == -1) {
							out.println("I don't know.");
						} else {
							out.println(ans);
						}
					}
				}
			}
			out.println();
			n = in.nextInt();
			m = in.nextInt();
		}
		out.flush();
		out.close();
		in.close();
	}

	// 读写工具类
	static class FastReader {
		final private int BUFFER_SIZE = 1 << 16;
		private final InputStream in;
		private final byte[] buffer;
		private int pointer, bytesRead;

		public int a;
		public int b;
		public int c;
		public int size;

		public FastReader() {
			in = System.in;
			buffer = new byte[BUFFER_SIZE];
			pointer = bytesRead = 0;
		}

		private byte read() throws IOException {
			if (pointer >= bytesRead) {
				fillBuffer();
				if (bytesRead == -1) {
					return -1;
				}
			}
			return buffer[pointer++];
		}

		private void fillBuffer() throws IOException {
			bytesRead = in.read(buffer, 0, BUFFER_SIZE);
			pointer = 0;
		}

		private void skipWhiteSpace() throws IOException {
			byte c;
			while ((c = read()) != -1) {
				if (c > ' ') {
					pointer--;
					break;
				}
			}
		}

		private String readLine() throws IOException {
			StringBuilder sb = new StringBuilder();
			while (true) {
				byte c = read();
				if (c == -1 || c == '\n') {
					break;
				}
				if (c == '\r') {
					byte nextc = read();
					if (nextc != '\n') {
						pointer--;
					}
					break;
				}
				sb.append((char) c);
			}
			if (sb.length() == 0 && bytesRead == -1) {
				return null;
			}
			return sb.toString();
		}

		public String next() throws IOException {
			skipWhiteSpace();
			if (bytesRead == -1) {
				return null;
			}
			StringBuilder sb = new StringBuilder();
			byte c = read();
			while (c != -1 && c > ' ') {
				sb.append((char) c);
				c = read();
			}
			return sb.toString();
		}

		public int nextInt() throws IOException {
			skipWhiteSpace();
			if (bytesRead == -1) {
				throw new IOException("No more data to read (EOF)");
			}
			boolean negative = false;
			int result = 0;
			byte c = read();
			if (c == '-') {
				negative = true;
				c = read();
			}
			while (c >= '0' && c <= '9') {
				result = result * 10 + (c - '0');
				c = read();
			}
			if (c != -1 && c > ' ') {
				pointer--;
			}
			return negative ? -result : result;
		}

		public void numbers() throws IOException {
			a = b = c = size = 0;
			String line = readLine();
			if (line == null) {
				return;
			}
			String[] parts = line.trim().split("\\s+");
			if (parts.length == 0) {
				return;
			}
			size = Math.min(parts.length, 3);
			if (size >= 1) {
				a = Integer.parseInt(parts[0]);
			}
			if (size >= 2) {
				b = Integer.parseInt(parts[1]);
			}
			if (size >= 3) {
				c = Integer.parseInt(parts[2]);
			}
		}

		public void close() throws IOException {
			if (in != null) {
				in.close();
			}
		}
	}

}
