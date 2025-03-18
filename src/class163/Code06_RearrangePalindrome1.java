package class163;

// 最长重排回文路径，java版
// 测试链接 : https://www.luogu.com.cn/problem/CF741D
// 测试链接 : https://codeforces.com/problemset/problem/741/D
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code06_RearrangePalindrome1 {

	public static int MAXN = 500001;
	// 字符种类最多22种
	public static int MAXV = 22;
	public static int n;

	// 链式前向星
	public static int[] head = new int[MAXN];
	public static int[] next = new int[MAXN];
	public static int[] to = new int[MAXN];
	public static int[] weight = new int[MAXN];
	public static int cnt = 0;

	// 树链剖分
	public static int[] siz = new int[MAXN];
	public static int[] dep = new int[MAXN];
	public static int[] eor = new int[MAXN];
	public static int[] son = new int[MAXN];

	// 统计答案
	public static int[] maxdep = new int[1 << MAXV];
	public static int[] ans = new int[MAXN];

	public static void addEdge(int u, int v, int w) {
		next[++cnt] = head[u];
		to[cnt] = v;
		weight[cnt] = w;
		head[u] = cnt;
	}

	public static void dfs1(int u, int d, int x) {
		siz[u] = 1;
		dep[u] = d;
		eor[u] = x;
		for (int e = head[u]; e > 0; e = next[e]) {
			dfs1(to[e], d + 1, x ^ (1 << weight[e]));
		}
		for (int e = head[u], v; e > 0; e = next[e]) {
			v = to[e];
			siz[u] += siz[v];
			if (son[u] == 0 || siz[son[u]] < siz[v]) {
				son[u] = v;
			}
		}
	}

	public static void effect(int u) {
		maxdep[eor[u]] = Math.max(maxdep[eor[u]], dep[u]);
		for (int e = head[u]; e > 0; e = next[e]) {
			effect(to[e]);
		}
	}

	public static void cancle(int u) {
		maxdep[eor[u]] = 0;
		for (int e = head[u]; e > 0; e = next[e]) {
			cancle(to[e]);
		}
	}

	public static int cross(int u, int lcaDep) {
		int ans = 0;
		if (maxdep[eor[u]] != 0) {
			ans = Math.max(ans, maxdep[eor[u]] + dep[u] - lcaDep * 2);
		}
		for (int i = 0; i < MAXV; i++) {
			if (maxdep[eor[u] ^ (1 << i)] != 0) {
				ans = Math.max(ans, maxdep[eor[u] ^ (1 << i)] + dep[u] - lcaDep * 2);
			}
		}
		for (int e = head[u]; e > 0; e = next[e]) {
			ans = Math.max(ans, cross(to[e], lcaDep));
		}
		return ans;
	}

	public static void dfs2(int u, int keep) {
		for (int e = head[u], v; e > 0; e = next[e]) {
			v = to[e];
			if (v != son[u]) {
				dfs2(v, 0);
			}
		}
		if (son[u] != 0) {
			dfs2(son[u], 1);
		}
		for (int e = head[u]; e > 0; e = next[e]) {
			ans[u] = Math.max(ans[u], ans[to[e]]);
		}
		if (maxdep[eor[u]] != 0) {
			ans[u] = Math.max(ans[u], maxdep[eor[u]] - dep[u]);
		}
		for (int i = 0; i < MAXV; i++) {
			if (maxdep[eor[u] ^ (1 << i)] != 0) {
				ans[u] = Math.max(ans[u], maxdep[eor[u] ^ (1 << i)] - dep[u]);
			}
		}
		maxdep[eor[u]] = Math.max(maxdep[eor[u]], dep[u]);
		for (int e = head[u], v; e > 0; e = next[e]) {
			v = to[e];
			if (v != son[u]) {
				ans[u] = Math.max(ans[u], cross(v, dep[u]));
				effect(v);
			}
		}
		if (keep == 0) {
			cancle(u);
		}
	}

	public static void main(String[] args) throws IOException {
		FastReader in = new FastReader();
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		for (int i = 2, fth, edg; i <= n; i++) {
			fth = in.nextInt();
			edg = in.nextChar() - 'a';
			addEdge(fth, i, edg);
		}
		dfs1(1, 1, 0);
		dfs2(1, 1);
		for (int i = 1; i <= n; i++) {
			out.print(ans[i] + " ");
		}
		out.println();
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
