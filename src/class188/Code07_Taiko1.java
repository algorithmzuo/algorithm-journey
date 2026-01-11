package class188;

// 太鼓达人，java版
// 测试链接 : https://loj.ac/p/10110
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code07_Taiko1 {

	public static int MAXN = 3001;
	public static int n, MOD;

	public static int[] cur = new int[MAXN];
	public static int[] path = new int[MAXN];
	public static int cntp;

	public static void euler(int u) {
		while (cur[u] < 2) {
			euler((u * 2 + (cur[u]++)) % MOD);
		}
		path[++cntp] = u;
	}

	public static void prepare() {
		MOD = 1;
		for (int i = 1; i <= n - 1; i++) {
			MOD <<= 1;
		}
		for (int i = 0; i < MOD; i++) {
			cur[i] = 0;
		}
		cntp = 0;
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		prepare();
		euler(0);
		out.print((MOD << 1) + " ");
		for (int i = 1; i <= n - 2; i++) {
			out.print("0");
		}
		for (int i = cntp; i >= n; i--) {
			out.print(path[i] % 2);
		}
		out.println();
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
