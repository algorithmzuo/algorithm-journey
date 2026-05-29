package class200;

// 仙人掌最短路，java版
// 测试链接 : https://www.luogu.com.cn/problem/P5236
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code01_CactusShortestPaths1 {

	public static int MAXN = 10001;
	public static int MAXM = 20001;
	public static int MAXT = MAXN << 1;
	public static int n, m, q, cntn;

	// 原图的链式前向星
	public static int[] head1 = new int[MAXN];
	public static int[] next1 = new int[MAXM << 1];
	public static int[] to1 = new int[MAXM << 1];
	public static int[] weight1 = new int[MAXM << 1];
	public static int cnt1;

	// 圆方树的链式前向星，连接单向边即可
	public static int[] head2 = new int[MAXT];
	public static int[] next2 = new int[MAXT];
	public static int[] to2 = new int[MAXT];
	public static int[] weight2 = new int[MAXT];
	public static int cnt2;

	// tarjan算法求圆方树
	public static int[] dfn = new int[MAXN];
	public static int[] low = new int[MAXN];
	public static int cntd;
	public static int[] sta = new int[MAXN];
	public static int stasiz;

	// 每个环的信息
	public static int[] fromWeight = new int[MAXN];
	public static int[] cycleDist = new int[MAXN];
	public static int[] cycleSum = new int[MAXT];

	// 圆方树的树链剖分
	public static int[] fa = new int[MAXT];
	public static int[] dep = new int[MAXT];
	public static int[] siz = new int[MAXT];
	public static int[] dist = new int[MAXT];
	public static int[] son = new int[MAXT];
	public static int[] top = new int[MAXT];

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
				low[u] = Math.min(low[u], low[v]);
				fromWeight[v] = w;
				if (low[v] == dfn[u]) {
					cycleLink(u, v);
				} else if (low[v] > dfn[u]) {
					stasiz--;
					addEdge2(u, v, w);
				}
			} else if (dfn[v] < dfn[u]) {
				low[u] = Math.min(low[u], dfn[v]);
				fromWeight[v] = w;
			}
		}
	}

	public static void cycleLink(int u, int v) {
		cntn++;
		cycleSum[cntn] = fromWeight[u];
		addEdge2(u, cntn, 0);
		int tmp = stasiz;
		int pop;
		do {
			pop = sta[tmp--];
			cycleDist[pop] = cycleSum[cntn];
			cycleSum[cntn] += fromWeight[pop];
		} while (pop != v);
		do {
			pop = sta[stasiz--];
			addEdge2(cntn, pop, Math.min(cycleDist[pop], cycleSum[cntn] - cycleDist[pop]));
		} while (pop != v);
	}

	public static void dfs1(int u, int f, int dis) {
		fa[u] = f;
		dep[u] = dep[f] + 1;
		siz[u] = 1;
		dist[u] = dis;
		for (int e = head2[u], v; e > 0; e = next2[e]) {
			v = to2[e];
			if (v != f) {
				dfs1(v, u, dist[u] + weight2[e]);
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
			if (v != fa[u] && v != son[u]) {
				dfs2(v, v);
			}
		}
	}

	public static int find(int cur, int lca) {
		int ans = 0;
		while (top[cur] != top[lca]) {
			ans = top[cur];
			cur = fa[top[cur]];
		}
		return cur == lca ? ans : son[lca];
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

	public static int query(int x, int y) {
		int ans = 0;
		int xylca = lca(x, y);
		if (xylca <= n) {
			ans = dist[x] + dist[y] - (dist[xylca] << 1);
		} else {
			int fx = find(x, xylca);
			int fy = find(y, xylca);
			ans = dist[x] + dist[y] - dist[fx] - dist[fy];
			int small = Math.min(cycleDist[fx], cycleDist[fy]);
			int big = Math.max(cycleDist[fx], cycleDist[fy]);
			ans += Math.min(big - small, cycleSum[xylca] + small - big);
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
