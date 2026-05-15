package class199;

// 路径数量，拓扑排序找环，java版
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

	public static int[] degree = new int[MAXN];
	public static int[] que = new int[MAXN];
	public static boolean[] inCycle = new boolean[MAXN];

	public static boolean[] vis = new boolean[MAXN];
	public static long[] siz = new long[MAXN];

	public static void addEdge(int u, int v) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		head[u] = cntg;
	}

	public static void topo() {
		int ql = 1, qr = 0;
		for (int i = 1; i <= n; i++) {
			if (degree[i] == 1) {
				que[++qr] = i;
			}
			inCycle[i] = true;
		}
		while (ql <= qr) {
			int u = que[ql++];
			inCycle[u] = false;
			for (int e = head[u]; e > 0; e = nxt[e]) {
				int v = to[e];
				if (--degree[v] == 1) {
					que[++qr] = v;
				}
			}
		}
	}

	public static void dfs(int u) {
		vis[u] = true;
		siz[u] = 1;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (!vis[v] && !inCycle[v]) {
				dfs(v);
				siz[u] += siz[v];
			}
		}
	}

	public static long compute() {
		topo();
		long ans = 0;
		for (int i = 1; i <= n; i++) {
			if (inCycle[i]) {
				dfs(i);
				ans += siz[i] * (siz[i] - 1) + siz[i] * 2 * (n - siz[i]);
			}
		}
		return ans / 2;
	}

	public static void prepare() {
		cntg = 0;
		for (int i = 1; i <= n; i++) {
			head[i] = degree[i] = 0;
			vis[i] = false;
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
				degree[u]++;
				degree[v]++;
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
