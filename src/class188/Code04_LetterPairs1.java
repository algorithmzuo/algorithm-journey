package class188;

// 无序字母对，java版
// 测试链接 : https://www.luogu.com.cn/problem/P1341
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code04_LetterPairs1 {

	public static int MAXN = 52;
	public static int MAXP = 2001;
	public static int[][] graph = new int[MAXN][MAXN];
	public static int[] deg = new int[MAXN];
	public static int[] cur = new int[MAXN];

	public static int[] path = new int[MAXP];
	public static int cntp;

	public static int getInt(char c) {
		return c <= 'Z' ? (c - 'A') : (c - 'a' + 26);
	}

	public static char getChar(int v) {
		return (char) (v < 26 ? ('A' + v) : ('a' + v - 26));
	}

	public static int getStart() {
		int odd = 0;
		for (int i = 0; i < MAXN; i++) {
			if ((deg[i] & 1) == 1) {
				odd++;
			}
		}
		if (odd != 0 && odd != 2) {
			return -1;
		}
		for (int i = 0; i < MAXN; i++) {
			if (odd != 0 && (deg[i] & 1) == 1) {
				return i;
			}
			if (odd == 0 && deg[i] > 0) {
				return i;
			}
		}
		return -1;
	}

	public static void euler(int u) {
		for (int v = cur[u]; v < MAXN; v = cur[u]) {
			cur[u]++;
			if (graph[u][v] > 0) {
				graph[u][v]--;
				graph[v][u]--;
				euler(v);
			}
		}
		path[++cntp] = u;
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		int n = in.nextInt();
		char a, b;
		int u, v;
		for (int i = 1; i <= n; i++) {
			a = in.nextChar();
			b = in.nextChar();
			u = getInt(a);
			v = getInt(b);
			graph[u][v]++;
			graph[v][u]++;
			deg[u]++;
			deg[v]++;
		}
		int start = getStart();
		if (start == -1) {
			out.println("No Solution");
		} else {
			euler(start);
			if (cntp != n + 1) {
				out.println("No Solution");
			} else {
				for (int i = cntp; i >= 1; i--) {
					out.print(getChar(path[i]));
				}
				out.println();
			}
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

		char nextChar() throws IOException {
			int c;
			do {
				c = readByte();
				if (c == -1)
					return 0;
			} while (!((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z')));
			return (char) c;
		}

	}

}
