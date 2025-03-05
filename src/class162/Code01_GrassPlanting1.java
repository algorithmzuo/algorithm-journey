package class162;

// 边权转化为点权的模版题，java版
// 一共有n个节点，给定n-1条边，节点连成一棵树，初始时所有边的权值为0
// 一共有m条操作，每条操作是如下2种类型中的一种
// 操作 P x y : x到y的路径上，每条边的权值增加1
// 操作 Q x y : x和y保证是直接连接的，查询他们之间的边权
// 1 <= n、m <= 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P3038
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Code01_GrassPlanting1 {

	public static int MAXN = 100001;
	public static int n, m;

	public static int[] head = new int[MAXN];
	public static int[] next = new int[MAXN << 1];
	public static int[] to = new int[MAXN << 1];
	public static int cntg = 0;

	public static int[] fa = new int[MAXN];
	public static int[] dep = new int[MAXN];
	public static int[] siz = new int[MAXN];
	public static int[] son = new int[MAXN];
	public static int[] top = new int[MAXN];
	public static int[] dfn = new int[MAXN];
	public static int cntd = 0;

	public static int[] sum = new int[MAXN << 2];
	public static int[] addTag = new int[MAXN << 2];

	public static void addEdge(int u, int v) {
		next[++cntg] = head[u];
		to[cntg] = v;
		head[u] = cntg;
	}

	public static void dfs1(int u, int f) {
		fa[u] = f;
		dep[u] = dep[f] + 1;
		siz[u] = 1;
		for (int e = head[u], v; e > 0; e = next[e]) {
			v = to[e];
			if (v != f) {
				dfs1(v, u);
			}
		}
		for (int e = head[u], v; e > 0; e = next[e]) {
			v = to[e];
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
		dfn[u] = ++cntd;
		if (son[u] == 0) {
			return;
		}
		dfs2(son[u], t);
		for (int e = head[u], v; e > 0; e = next[e]) {
			v = to[e];
			if (v != fa[u] && v != son[u]) {
				dfs2(v, v);
			}
		}
	}

	// 递归函数不会改迭代版，去看讲解118，详解了从递归版改迭代版
	public static int[][] fse = new int[MAXN][3];

	public static int stacksize, first, second, edge;

	public static void push(int fir, int sec, int edg) {
		fse[stacksize][0] = fir;
		fse[stacksize][1] = sec;
		fse[stacksize][2] = edg;
		stacksize++;
	}

	public static void pop() {
		--stacksize;
		first = fse[stacksize][0];
		second = fse[stacksize][1];
		edge = fse[stacksize][2];
	}

	// dfs1的迭代版
	public static void dfs3() {
		stacksize = 0;
		push(1, 0, -1);
		while (stacksize > 0) {
			pop();
			if (edge == -1) {
				fa[first] = second;
				dep[first] = dep[second] + 1;
				siz[first] = 1;
				edge = head[first];
			} else {
				edge = next[edge];
			}
			if (edge != 0) {
				push(first, second, edge);
				if (to[edge] != second) {
					push(to[edge], first, -1);
				}
			} else {
				for (int e = head[first], v; e > 0; e = next[e]) {
					v = to[e];
					if (v != second) {
						siz[first] += siz[v];
						if (son[first] == 0 || siz[son[first]] < siz[v]) {
							son[first] = v;
						}
					}
				}
			}
		}
	}

	// dfs2的迭代版
	public static void dfs4() {
		stacksize = 0;
		push(1, 1, -1);
		while (stacksize > 0) {
			pop();
			if (edge == -1) {
				top[first] = second;
				dfn[first] = ++cntd;
				if (son[first] == 0) {
					continue;
				}
				push(first, second, -2);
				push(son[first], second, -1);
				continue;
			} else if (edge == -2) {
				edge = head[first];
			} else {
				edge = next[edge];
			}
			if (edge != 0) {
				push(first, second, edge);
				if (to[edge] != fa[first] && to[edge] != son[first]) {
					push(to[edge], to[edge], -1);
				}
			}
		}
	}

	public static void up(int i) {
		sum[i] = sum[i << 1] + sum[i << 1 | 1];
	}

	public static void lazy(int i, int v, int n) {
		sum[i] += v * n;
		addTag[i] += v;
	}

	public static void down(int i, int ln, int rn) {
		if (addTag[i] != 0) {
			lazy(i << 1, addTag[i], ln);
			lazy(i << 1 | 1, addTag[i], rn);
			addTag[i] = 0;
		}
	}

	// 范围增加
	public static void add(int jobl, int jobr, int jobv, int l, int r, int i) {
		if (jobl <= l && r <= jobr) {
			lazy(i, jobv, r - l + 1);
		} else {
			int mid = (l + r) / 2;
			down(i, mid - l + 1, r - mid);
			if (jobl <= mid) {
				add(jobl, jobr, jobv, l, mid, i << 1);
			}
			if (jobr > mid) {
				add(jobl, jobr, jobv, mid + 1, r, i << 1 | 1);
			}
			up(i);
		}
	}

	// 单点查询
	public static int query(int jobi, int l, int r, int i) {
		if (l == r) {
			return sum[i];
		}
		int mid = (l + r) / 2;
		down(i, mid - l + 1, r - mid);
		if (jobi <= mid) {
			return query(jobi, l, mid, i << 1);
		} else {
			return query(jobi, mid + 1, r, i << 1 | 1);
		}
	}

	// x到y的路径上，每条边的边权变成下方点的点权
	// 每条边的边权增加v，就是若干点的点权增加v
	// 但是要注意！x和y的最低公共祖先，不能增加点权！
	public static void pathAdd(int x, int y, int v) {
		while (top[x] != top[y]) {
			if (dep[top[x]] <= dep[top[y]]) {
				add(dfn[top[y]], dfn[y], v, 1, n, 1);
				y = fa[top[y]];
			} else {
				add(dfn[top[x]], dfn[x], v, 1, n, 1);
				x = fa[top[x]];
			}
		}
		// x和y的最低公共祖先，点权不增加！
		add(Math.min(dfn[x], dfn[y]) + 1, Math.max(dfn[x], dfn[y]), v, 1, n, 1);
	}

	// 返回x和y之间这条边的边权
	public static int edgeQuery(int x, int y) {
		int down = Math.max(dfn[x], dfn[y]);
		return query(down, 1, n, 1);
	}

	public static void main(String[] args) {
		Kattio io = new Kattio();
		n = io.nextInt();
		m = io.nextInt();
		for (int i = 1, u, v; i < n; i++) {
			u = io.nextInt();
			v = io.nextInt();
			addEdge(u, v);
			addEdge(v, u);
		}
		dfs3();
		dfs4();
		String op;
		for (int i = 1, x, y; i <= m; i++) {
			op = io.next();
			x = io.nextInt();
			y = io.nextInt();
			if (op.equals("P")) {
				pathAdd(x, y, 1);
			} else {
				io.println(edgeQuery(x, y));
			}
		}
		io.flush();
		io.close();
	}

	// 读写工具类
	public static class Kattio extends PrintWriter {
		private BufferedReader r;
		private StringTokenizer st;

		public Kattio() {
			this(System.in, System.out);
		}

		public Kattio(InputStream i, OutputStream o) {
			super(o);
			r = new BufferedReader(new InputStreamReader(i));
		}

		public Kattio(String intput, String output) throws IOException {
			super(output);
			r = new BufferedReader(new FileReader(intput));
		}

		public String next() {
			try {
				while (st == null || !st.hasMoreTokens())
					st = new StringTokenizer(r.readLine());
				return st.nextToken();
			} catch (Exception e) {
			}
			return null;
		}

		public int nextInt() {
			return Integer.parseInt(next());
		}

		public double nextDouble() {
			return Double.parseDouble(next());
		}

		public long nextLong() {
			return Long.parseLong(next());
		}
	}

}
