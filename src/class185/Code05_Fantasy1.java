package class185;

// 幻想乡战略游戏，java版
// 测试链接 : https://www.luogu.com.cn/problem/P3345
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code05_Fantasy1 {

	public static int MAXN = 100001;
	public static int n, m;

	public static int[] headg = new int[MAXN];
	public static int[] nxtg = new int[MAXN << 1];
	public static int[] tog = new int[MAXN << 1];
	public static int[] weightg = new int[MAXN << 1];
	public static int cntg;

	public static int[] headc = new int[MAXN];
	public static int[] nxtc = new int[MAXN << 1];
	public static int[] toc = new int[MAXN << 1];
	public static int[] centc = new int[MAXN << 1];
	public static int cntc;

	public static int[] fa = new int[MAXN];
	public static int[] dep = new int[MAXN];
	public static int[] siz = new int[MAXN];
	public static int[] son = new int[MAXN];
	public static int[] top = new int[MAXN];
	public static int[] dist = new int[MAXN];

	public static boolean[] vis = new boolean[MAXN];
	public static int[] centfa = new int[MAXN];

	public static long[] num = new long[MAXN];
	public static long[] xsum = new long[MAXN];
	public static long[] fsum = new long[MAXN];

	// 讲解118，递归函数改成迭代所需要的栈
	public static int[][] stack = new int[MAXN][5];
	public static int u, f, a, b, e;
	public static int stacksize;

	public static void push(int u, int f, int a, int b, int e) {
		stack[stacksize][0] = u;
		stack[stacksize][1] = f;
		stack[stacksize][2] = a;
		stack[stacksize][3] = b;
		stack[stacksize][4] = e;
		stacksize++;
	}

	public static void pop() {
		--stacksize;
		u = stack[stacksize][0];
		f = stack[stacksize][1];
		a = stack[stacksize][2];
		b = stack[stacksize][3];
		e = stack[stacksize][4];
	}

	public static void addEdgeG(int u, int v, int w) {
		nxtg[++cntg] = headg[u];
		tog[cntg] = v;
		weightg[cntg] = w;
		headg[u] = cntg;
	}

	public static void addEdgeC(int u, int v, int cen) {
		nxtc[++cntc] = headc[u];
		toc[cntc] = v;
		centc[cntc] = cen;
		headc[u] = cntc;
	}

	public static void dfs1(int u, int f, int dis) {
		fa[u] = f;
		dep[u] = dep[f] + 1;
		dist[u] = dis;
		siz[u] = 1;
		for (int e = headg[u]; e > 0; e = nxtg[e]) {
			int v = tog[e];
			int w = weightg[e];
			if (v != f) {
				dfs1(v, u, dis + w);
			}
		}
		for (int ei = headg[u], v; ei > 0; ei = nxtg[ei]) {
			v = tog[ei];
			if (v != f) {
				siz[u] += siz[v];
				if (son[u] == 0 || siz[son[u]] < siz[v]) {
					son[u] = v;
				}
			}
		}
	}

	public static void dfs2(int u, int t) {
		top[u] = t;
		if (son[u] == 0) {
			return;
		}
		dfs2(son[u], t);
		for (int e = headg[u], v; e > 0; e = nxtg[e]) {
			v = tog[e];
			if (v != fa[u] && v != son[u]) {
				dfs2(v, v);
			}
		}
	}

	public static void dfs3(int cur, int father, int distance) {
		stacksize = 0;
		push(cur, father, distance, 0, -1);
		while (stacksize > 0) {
			pop();
			if (e == -1) {
				fa[u] = f;
				dep[u] = dep[f] + 1;
				dist[u] = a;
				siz[u] = 1;
				e = headg[u];
			} else {
				e = nxtg[e];
			}
			if (e != 0) {
				push(u, f, a, 0, e);
				int v = tog[e];
				int w = weightg[e];
				if (v != f) {
					push(v, u, a + w, 0, -1);
				}
			} else {
				for (int ei = headg[u]; ei > 0; ei = nxtg[ei]) {
					int v = tog[ei];
					if (v != f) {
						siz[u] += siz[v];
						if (son[u] == 0 || siz[son[u]] < siz[v]) {
							son[u] = v;
						}
					}
				}
			}
		}
	}

	public static void dfs4(int cur, int tag) {
		stacksize = 0;
		push(cur, 0, 0, tag, -1);
		while (stacksize > 0) {
			pop();
			if (e == -1) {
				top[u] = b;
				if (son[u] == 0) {
					continue;
				}
				push(u, 0, 0, b, -2);
				push(son[u], 0, 0, b, -1);
				continue;
			} else if (e == -2) {
				e = headg[u];
			} else {
				e = nxtg[e];
			}
			if (e != 0) {
				push(u, 0, 0, b, e);
				int v = tog[e];
				if (v != fa[u] && v != son[u]) {
					push(v, 0, 0, v, -1);
				}
			}
		}
	}

	public static int getLca(int a, int b) {
		while (top[a] != top[b]) {
			if (dep[top[a]] <= dep[top[b]]) {
				b = fa[top[b]];
			} else {
				a = fa[top[a]];
			}
		}
		return dep[a] <= dep[b] ? a : b;
	}

	public static int getDist(int x, int y) {
		return dist[x] + dist[y] - (dist[getLca(x, y)] << 1);
	}

	public static void getSize1(int u, int fa) {
		siz[u] = 1;
		for (int e = headg[u]; e > 0; e = nxtg[e]) {
			int v = tog[e];
			if (v != fa && !vis[v]) {
				getSize1(v, u);
				siz[u] += siz[v];
			}
		}
	}

	public static void getSize2(int cur, int fa) {
		stacksize = 0;
		push(cur, fa, 0, 0, -1);
		while (stacksize > 0) {
			pop();
			if (e == -1) {
				siz[u] = 1;
				e = headg[u];
			} else {
				e = nxtg[e];
			}
			if (e != 0) {
				push(u, f, 0, 0, e);
				int v = tog[e];
				if (v != f && !vis[v]) {
					push(v, u, 0, 0, -1);
				}
			} else {
				for (int ei = headg[u]; ei > 0; ei = nxtg[ei]) {
					int v = tog[ei];
					if (v != f && !vis[v]) {
						siz[u] += siz[v];
					}
				}
			}
		}
	}

	public static int getCentroid(int u, int fa) {
		// getSize1(u, fa);
		getSize2(u, fa);
		int half = siz[u] >> 1;
		boolean find = false;
		while (!find) {
			find = true;
			for (int e = headg[u]; e > 0; e = nxtg[e]) {
				int v = tog[e];
				if (v != fa && !vis[v] && siz[v] > half) {
					fa = u;
					u = v;
					find = false;
					break;
				}
			}
		}
		return u;
	}

	public static void centroidTree(int u, int fa) {
		centfa[u] = fa;
		vis[u] = true;
		for (int e = headg[u]; e > 0; e = nxtg[e]) {
			int v = tog[e];
			if (!vis[v]) {
				int nextCent = getCentroid(v, u);
				addEdgeC(u, v, nextCent);
				centroidTree(nextCent, u);
			}
		}
	}

	public static void add(int x, int v) {
		num[x] += v;
		for (int cur = x, fa = centfa[cur]; fa > 0; cur = fa, fa = centfa[cur]) {
			int dist = getDist(x, fa);
			num[fa] += v;
			xsum[fa] += 1L * v * dist;
			fsum[cur] += 1L * v * dist;
		}
	}

	public static long query(int x) {
		long ans = xsum[x];
		for (int cur = x, fa = centfa[cur]; fa > 0; cur = fa, fa = centfa[cur]) {
			int dist = getDist(x, fa);
			ans += (num[fa] - num[cur]) * dist;
			ans += (xsum[fa] - fsum[cur]);
		}
		return ans;
	}

	public static long compute(int u) {
		long ans = query(u);
		for (int e = headc[u]; e > 0; e = nxtc[e]) {
			int v = toc[e];
			if (query(v) < ans) {
				return compute(centc[e]);
			}
		}
		return ans;
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		for (int i = 1, u, v, w; i < n; i++) {
			u = in.nextInt();
			v = in.nextInt();
			w = in.nextInt();
			addEdgeG(u, v, w);
			addEdgeG(v, u, w);
		}
		// dfs1(1, 0, 0);
		// dfs2(1, 1);
		dfs3(1, 0, 0);
		dfs4(1, 1);
		int root = getCentroid(1, 0);
		centroidTree(root, 0);
		for (int i = 1, x, v; i <= m; i++) {
			x = in.nextInt();
			v = in.nextInt();
			add(x, v);
			out.println(compute(root));
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
