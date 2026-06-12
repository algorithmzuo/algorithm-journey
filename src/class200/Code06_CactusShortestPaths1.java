package class200;

// 仙人掌最短路，java版
// 给定n个点、m条边的仙人掌图，每条边有边权，没有自环，没有重边
// 一共q条查询，每条查询格式为 x y，查询点x和点y之间的最短路距离
// 1 <= n、q <= 10^4
// 1 <= m <= 2 * 10^4
// 1 <= 边权 <= 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P5236
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code06_CactusShortestPaths1 {

	public static int MAXN = 50001;
	public static int n, m, q, cntn;

	// 原图
	public static int[] head1 = new int[MAXN];
	public static int[] next1 = new int[MAXN];
	public static int[] to1 = new int[MAXN];
	public static int[] weight1 = new int[MAXN];
	public static int cnt1;

	// 圆方树，单向边
	public static int[] head2 = new int[MAXN];
	public static int[] next2 = new int[MAXN];
	public static int[] to2 = new int[MAXN];
	public static int[] weight2 = new int[MAXN];
	public static int cnt2;

	// tarjan算法求圆方树
	public static int[] dfn = new int[MAXN];
	public static int[] low = new int[MAXN];
	public static int cntd;
	public static int[] sta = new int[MAXN];
	public static int stasiz;

	// 环的信息
	public static int[] fromWeight = new int[MAXN];
	public static int[] cycleLen = new int[MAXN];
	public static int[] cycleSum = new int[MAXN];

	// 树链剖分
	public static int[] fa = new int[MAXN];
	public static int[] dep = new int[MAXN];
	public static int[] siz = new int[MAXN];
	public static int[] len = new int[MAXN];
	public static int[] son = new int[MAXN];
	public static int[] top = new int[MAXN];

	public static void addEdge1(int u, int v, int w) {
		next1[++cnt1] = head1[u];
		to1[cnt1] = v;
		weight1[cnt1] = w;
		head1[u] = cnt1;
	}

	public static void addEdge2(int u, int v, int w) {
		next2[++cnt2] = head2[u];
		to2[cnt2] = v;
		weight2[cnt2] = w;
		head2[u] = cnt2;
	}

	public static void cycleLink(int u, int v) {
		cntn++;
		cycleSum[cntn] = fromWeight[u];
		addEdge2(u, cntn, 0);
		int tmp = stasiz;
		int pop;
		do {
			pop = sta[tmp--];
			cycleLen[pop] = cycleSum[cntn];
			cycleSum[cntn] += fromWeight[pop];
		} while (pop != v);
		do {
			pop = sta[stasiz--];
			addEdge2(cntn, pop, Math.min(cycleLen[pop], cycleSum[cntn] - cycleLen[pop]));
		} while (pop != v);
	}

	public static void tarjan(int u, int preEdge) {
		dfn[u] = low[u] = ++cntd;
		sta[++stasiz] = u;
		for (int e = head1[u]; e > 0; e = next1[e]) {
			if ((e ^ 1) == preEdge) {
				continue;
			}
			int v = to1[e];
			int w = weight1[e];
			if (dfn[v] == 0) {
				tarjan(v, e);
				fromWeight[v] = w;
				if (low[v] < dfn[u]) {
					low[u] = Math.min(low[u], low[v]);
				} else if (low[v] > dfn[u]) {
					stasiz--;
					addEdge2(u, v, w);
				} else {
					cycleLink(u, v);
				}
			} else if (dfn[v] < dfn[u]) {
				fromWeight[v] = w;
				low[u] = Math.min(low[u], dfn[v]);
			}
		}
	}

	public static void dfs1(int u, int f, int l) {
		fa[u] = f;
		dep[u] = dep[f] + 1;
		siz[u] = 1;
		len[u] = l;
		for (int e = head2[u], v; e > 0; e = next2[e]) {
			v = to2[e];
			// 因为是单向边，所以不用判断 v != f，但这里保留模版写法
			if (v != f) {
				dfs1(v, u, len[u] + weight2[e]);
				siz[u] += siz[v];
				if (son[u] == 0 || siz[son[u]] < siz[v]) {
					son[u] = v;
				}
			}
		}
	}

	public static void dfs2(int u, int t) {
		top[u] = t;
		if (son[u] != 0) {
			dfs2(son[u], t);
		}
		for (int e = head2[u]; e > 0; e = next2[e]) {
			int v = to2[e];
			// 因为是单向边，所以不用判断 v != fa[u]，但这里保留模版写法
			if (v != fa[u] && v != son[u]) {
				dfs2(v, v);
			}
		}
	}

	public static int lca(int a, int b) {
		while (top[a] != top[b]) {
			if (dep[top[a]] <= dep[top[b]]) {
				b = fa[top[b]];
			} else {
				a = fa[top[a]];
			}
		}
		return dep[a] <= dep[b] ? a : b;
	}

	public static int find(int x, int square) {
		int ans = 0;
		while (top[x] != top[square]) {
			ans = top[x];
			x = fa[top[x]];
		}
		return x == square ? ans : son[square];
	}

	public static int query(int x, int y) {
		int ans = 0;
		int xylca = lca(x, y);
		if (xylca <= n) {
			ans = len[x] + len[y] - (len[xylca] << 1);
		} else {
			int fx = find(x, xylca);
			int fy = find(y, xylca);
			ans = len[x] + len[y] - len[fx] - len[fy];
			int small = Math.min(cycleLen[fx], cycleLen[fy]);
			int big = Math.max(cycleLen[fx], cycleLen[fy]);
			ans += Math.min(big - small, cycleSum[xylca] - (big - small));
		}
		return ans;
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		q = in.nextInt();
		cntn = n;
		cnt1 = 1;
		for (int i = 1, u, v, w; i <= m; i++) {
			u = in.nextInt();
			v = in.nextInt();
			w = in.nextInt();
			addEdge1(u, v, w);
			addEdge1(v, u, w);
		}
		for (int i = 1; i <= n; i++) {
			if (dfn[i] == 0) {
				tarjan(i, 0);
			}
		}
		dfs1(1, 0, 0);
		dfs2(1, 1);
		for (int i = 1, x, y; i <= q; i++) {
			x = in.nextInt();
			y = in.nextInt();
			out.println(query(x, y));
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
