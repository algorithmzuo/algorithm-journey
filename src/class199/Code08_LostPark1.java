package class199;

// 迷失游乐园，java版
// 环的节点数量 <= 20
// 测试链接 : https://www.luogu.com.cn/problem/P2081
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code08_LostPark1 {

	public static int MAXN = 100001;
	public static int n, m;

	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXN << 1];
	public static int[] to = new int[MAXN << 1];
	public static int[] weight = new int[MAXN << 1];
	public static int cntg;

	public static int[] dfn = new int[MAXN];
	public static int cntd;

	public static int[] from = new int[MAXN];
	public static boolean[] cycle = new boolean[MAXN];

	public static int[] son = new int[MAXN];
	public static double[] down = new double[MAXN];
	public static double[] up = new double[MAXN];

	public static void addEdge(int u, int v, int w) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		weight[cntg] = w;
		head[u] = cntg;
	}

	public static void dfs1(int u, int fa) {
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			int w = weight[e];
			if (v != fa) {
				son[u]++;
				dfs1(v, u);
				down[u] += down[v] + w;
			}
		}
		if (son[u] > 0) {
			down[u] /= son[u];
		}
	}

	public static void dfs2(int u, int fa) {
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			int w = weight[e];
			if (v != fa) {
				if (u == 1) {
					if (son[u] == 1) {
						up[v] = w;
					} else {
						up[v] = w + (up[u] + down[u] * son[u] - down[v] - w) / (son[u] - 1);
					}
				} else {
					up[v] = w + (up[u] + down[u] * son[u] - down[v] - w) / son[u];
				}
				dfs2(v, u);
			}
		}
	}

	public static double dpOnTree() {
		dfs1(1, 0);
		dfs2(1, 0);
		double ans = down[1];
		for (int i = 2; i <= n; i++) {
			ans += (down[i] * son[i] + up[i]) / (son[i] + 1);
		}
		return ans / n;
	}

	public static void flag(int u, int preEdge) {
		dfn[u] = ++cntd;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (e != (preEdge ^ 1)) {
				if (dfn[v] == 0) {
					from[v] = u;
					flag(v, e);
				} else if (dfn[u] < dfn[v]) {
					cycle[u] = true;
					for (int i = v; i != u; i = from[i]) {
						cycle[i] = true;
					}
				}
			}
		}
	}

	public static void dfs3(int u, int fa) {
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			int w = weight[e];
			if (v != fa && !cycle[v]) {
				son[u]++;
				dfs3(v, u);
				down[u] += w + down[v];
			}
		}
		if (son[u] > 0) {
			down[u] /= son[u];
		}
	}

	public static void dfs4(int u, int fa) {
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			int w = weight[e];
			if (v != fa && !cycle[v]) {
				if (cycle[u]) {
					up[v] = w + (up[u] * 2 + down[u] * son[u] - down[v] - w) / (son[u] + 1);
				} else {
					up[v] = w + (up[u] + down[u] * son[u] - down[v] - w) / son[u];
				}
				dfs4(v, u);
			}
		}
	}

	public static double around(int u, int fa, int start) {
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			int w = weight[e];
			if (cycle[v] && v != fa) {
				if (v == start) {
					return down[u];
				} else {
					return (around(v, u, start) + w + down[u] * son[u]) / (son[u] + 1);
				}
			}
		}
		return 0;
	}

	public static void compute(int u) {
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			int w = weight[e];
			if (cycle[v]) {
				up[u] += around(v, u, u) + w;
			}
		}
		up[u] /= 2;
	}

	public static double dpOnGraph() {
		flag(1, 0);
		for (int i = 1; i <= n; i++) {
			if (cycle[i]) {
				dfs3(i, 0);
			}
		}
		for (int i = 1; i <= n; i++)
			if (cycle[i]) {
				compute(i);
			}
		for (int i = 1; i <= n; i++) {
			if (cycle[i]) {
				dfs4(i, 0);
			}
		}
		double ans = 0;
		for (int i = 1; i <= n; i++) {
			if (cycle[i]) {
				ans += (down[i] * son[i] + up[i] * 2) / (son[i] + 2);
			} else {
				ans += (down[i] * son[i] + up[i]) / (son[i] + 1);
			}
		}
		return ans / n;
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		cntg = 1;
		for (int i = 1, u, v, w; i <= m; i++) {
			u = in.nextInt();
			v = in.nextInt();
			w = in.nextInt();
			addEdge(u, v, w);
			addEdge(v, u, w);
		}
		if (m == n - 1) {
			out.printf("%.5f\n", dpOnTree());
		} else {
			out.printf("%.5f\n", dpOnGraph());
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
