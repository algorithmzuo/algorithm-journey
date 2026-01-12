package class188;

// 所有可能的密码串，java版
// 测试链接 : http://poj.org/problem?id=1780
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code05_Password1 {

	public static int MAXN = 1000002;
	public static int n, MOD;

	public static int[] cur = new int[MAXN];
	public static int[] path = new int[MAXN];
	public static int cntp;

	public static int[] sta = new int[MAXN];
	public static int top;

	public static void prepare() {
		MOD = 1;
		for (int i = 1; i <= n - 1; i++) {
			MOD *= 10;
		}
		for (int i = 0; i < MOD; i++) {
			cur[i] = 0;
		}
		cntp = 0;
		top = 0;
	}

	// 递归版
	public static void euler1(int u) {
		while (cur[u] < 10) {
			euler1((u * 10 + (cur[u]++)) % MOD);
		}
		path[++cntp] = u;
	}

	// 迭代版
	public static void euler2(int u) {
		sta[++top] = u;
		while (top > 0) {
			u = sta[top--];
			if (cur[u] < 10) {
				sta[++top] = u;
				sta[++top] = (u * 10 + (cur[u]++)) % MOD;
			} else {
				path[++cntp] = u;
			}
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		while (n != 0) {
			if (n == 1) {
				out.println("0123456789");
			} else {
				prepare();
				euler2(0);
				for (int i = 1; i <= n - 2; i++) {
					out.print("0");
				}
				for (int i = cntp; i >= 1; i--) {
					out.print(path[i] % 10);
				}
				out.println();
			}
			n = in.nextInt();
		}
		out.flush();
		out.close();
	}

	// 读写工具类
	static class FastReader {
		private final byte[] buffer = new byte[1 << 16];
		private int ptr = 0, len = 0;
		private final InputStream in;

		FastReader(InputStream in) {
			this.in = in;
		}

		private int readByte() throws IOException {
			if (ptr >= len) {
				len = in.read(buffer);
				ptr = 0;
				if (len <= 0)
					return -1;
			}
			return buffer[ptr++];
		}

		int nextInt() throws IOException {
			int c;
			do {
				c = readByte();
			} while (c <= ' ' && c != -1);
			boolean neg = false;
			if (c == '-') {
				neg = true;
				c = readByte();
			}
			int val = 0;
			while (c > ' ' && c != -1) {
				val = val * 10 + (c - '0');
				c = readByte();
			}
			return neg ? -val : val;
		}
	}

}
