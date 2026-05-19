package class199;

// 简单路径数量，java版
// 图中有n个点、n条无向边，没有自环和重边
// 路径的长度为边的数量，计算长度>=1，不同简单路径的数量
// 简单路径a、b、c和c、b、a，认为是同一条，不要重复计算
// 3 <= n <= 2 * 10^5
// 测试链接 : https://www.luogu.com.cn/problem/CF1454E
// 测试链接 : https://codeforces.com/problemset/problem/1454/E
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code02_NumberOfSimplePaths1 {

	public static int MAXN = 200001;
	public static int t, n;

	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXN << 1];
	public static int[] to = new int[MAXN << 1];
	public static int cntg;

	public static int[] dfn = new int[MAXN];
	public static int cntd;

	public static int[] from = new int[MAXN];
	public static boolean[] cycle = new boolean[MAXN];

	public static long[] siz = new long[MAXN];

	public static void addEdge(int u, int v) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		head[u] = cntg;
	}

	public static void dfs(int u, int preEdge) {
		dfn[u] = ++cntd;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (e != (preEdge ^ 1)) {
				if (dfn[v] == 0) {
					from[v] = u;
					dfs(v, e);
				} else if (dfn[u] < dfn[v]) {
					cycle[u] = true;
					for (int i = v; i != u; i = from[i]) {
						cycle[i] = true;
					}
				}
			}
		}
	}

	public static void dpOnTree(int u, int fa) {
		siz[u] = 1;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (v != fa && !cycle[v]) {
				dpOnTree(v, u);
				siz[u] += siz[v];
			}
		}
	}

	public static long compute() {
		dfs(1, 0);
		long ans = 0;
		for (int i = 1; i <= n; i++) {
			if (cycle[i]) {
				dpOnTree(i, 0);
				ans += siz[i] * (siz[i] - 1) + siz[i] * 2 * (n - siz[i]);
			}
		}
		return ans / 2;
	}

	public static void prepare() {
		cntg = 1;
		cntd = 0;
		for (int i = 1; i <= n; i++) {
			head[i] = dfn[i] = 0;
			cycle[i] = false;
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		t = in.nextInt();
		for (int c = 1; c <= t; c++) {
			n = in.nextInt();
			prepare();
			for (int i = 1, u, v; i <= n; i++) {
				u = in.nextInt();
				v = in.nextInt();
				addEdge(u, v);
				addEdge(v, u);
			}
			long ans = compute();
			out.println(ans);
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
