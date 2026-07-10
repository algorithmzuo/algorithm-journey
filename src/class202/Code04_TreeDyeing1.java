package class202;

// 树点涂色，java版
// 一共n个节点，所有节点组成一棵树，规定1号节点是根
// 初始所有节点的颜色都不同，路径中的颜色数量叫做路径权值
// 接下来有m条操作，操作有三种类型，具体格式如下
// 操作 1 x   : 根到点x的路径上，所有点染上新的颜色
// 操作 2 x y : 查询点x到点y的路径权值
// 操作 3 x   : x为头的子树中，选择一个点，希望该点到根节点的
//              路径权值最大，打印这个最大值
// 1 <= n、m <= 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P3703
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code04_TreeDyeing1 {

	public static int MAXN = 100001;
	public static int n, m;

	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXN << 1];
	public static int[] to = new int[MAXN << 1];
	public static int cntg;

	// 树链剖分
	public static int[] pa = new int[MAXN];
	public static int[] dep = new int[MAXN];
	public static int[] siz = new int[MAXN];
	public static int[] son = new int[MAXN];
	public static int[] top = new int[MAXN];
	public static int[] dfn = new int[MAXN];
	public static int[] seg = new int[MAXN];
	public static int cntd;

	// 辅助splay
	public static int[] fa = new int[MAXN];
	public static int[] ls = new int[MAXN];
	public static int[] rs = new int[MAXN];
	public static int[] mostl = new int[MAXN];

	// 线段树
	public static int[] maxCnt = new int[MAXN << 2];
	public static int[] addTag = new int[MAXN << 2];

	// 递归改迭代需要的栈
	public static int[] stau = new int[MAXN];
	public static int[] staf = new int[MAXN];
	public static int[] stat = new int[MAXN];
	public static int[] stae = new int[MAXN];
	public static int stacksize, u, f, t, e;

	public static void push(int u, int f, int t, int e) {
		stau[stacksize] = u;
		staf[stacksize] = f;
		stat[stacksize] = t;
		stae[stacksize] = e;
		stacksize++;
	}

	public static void pop() {
		--stacksize;
		u = stau[stacksize];
		f = staf[stacksize];
		t = stat[stacksize];
		e = stae[stacksize];
	}

	public static void addEdge(int u, int v) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		head[u] = cntg;
	}

	// 递归版
	public static void dfs1(int u, int f) {
		pa[u] = f;
		dep[u] = dep[f] + 1;
		siz[u] = 1;
		for (int e = head[u], v; e != 0; e = nxt[e]) {
			v = to[e];
			if (v != f) {
				dfs1(v, u);
				siz[u] += siz[v];
				if (siz[v] > siz[son[u]]) {
					son[u] = v;
				}
			}
		}
	}

	// 递归版
	public static void dfs2(int u, int t) {
		top[u] = t;
		dfn[u] = ++cntd;
		seg[cntd] = u;
		if (son[u] != 0) {
			dfs2(son[u], t);
		}
		for (int e = head[u], v; e != 0; e = nxt[e]) {
			v = to[e];
			if (v != pa[u] && v != son[u]) {
				dfs2(v, v);
			}
		}
	}

	// 树剖dfs1的迭代版
	public static void dfs3(int cur, int father) {
		stacksize = 0;
		push(cur, father, 0, -1);
		while (stacksize > 0) {
			pop();
			if (e == -1) {
				pa[u] = f;
				dep[u] = dep[f] + 1;
				siz[u] = 1;
				e = head[u];
			} else {
				e = nxt[e];
			}
			if (e != 0) {
				push(u, f, 0, e);
				if (to[e] != f) {
					push(to[e], u, 0, -1);
				}
			} else {
				for (int ei = head[u], v; ei != 0; ei = nxt[ei]) {
					v = to[ei];
					if (v != f) {
						siz[u] += siz[v];
						if (siz[v] > siz[son[u]]) {
							son[u] = v;
						}
					}
				}
			}
		}
	}

	// 树剖dfs2的迭代版
	public static void dfs4(int cur, int curt) {
		stacksize = 0;
		push(cur, 0, curt, -1);
		while (stacksize > 0) {
			pop();
			if (e == -1) {
				top[u] = t;
				dfn[u] = ++cntd;
				seg[cntd] = u;
				if (son[u] != 0) {
					push(u, 0, t, -2);
					push(son[u], 0, t, -1);
				}
				continue;
			} else if (e == -2) {
				e = head[u];
			} else {
				e = nxt[e];
			}
			if (e != 0) {
				push(u, 0, t, e);
				if (to[e] != pa[u] && to[e] != son[u]) {
					push(to[e], 0, to[e], -1);
				}
			}
		}
	}

	public static int lca(int x, int y) {
		while (top[x] != top[y]) {
			if (dep[top[x]] < dep[top[y]]) {
				y = pa[top[y]];
			} else {
				x = pa[top[x]];
			}
		}
		return dep[x] < dep[y] ? x : y;
	}

	public static void up(int x) {
		mostl[x] = ls[x] == 0 ? x : mostl[ls[x]];
	}

	public static boolean isroot(int x) {
		return ls[fa[x]] != x && rs[fa[x]] != x;
	}

	public static int lr(int x) {
		return ls[fa[x]] == x ? 0 : 1;
	}

	public static void rotate(int x) {
		int f = fa[x], g = fa[f];
		if (lr(x) == 0) {
			ls[f] = rs[x];
			if (ls[f] != 0) {
				fa[ls[f]] = f;
			}
			rs[x] = f;
		} else {
			rs[f] = ls[x];
			if (rs[f] != 0) {
				fa[rs[f]] = f;
			}
			ls[x] = f;
		}
		if (!isroot(f)) {
			if (lr(f) == 0) {
				ls[g] = x;
			} else {
				rs[g] = x;
			}
		}
		fa[f] = x;
		fa[x] = g;
		up(f);
		up(x);
	}

	public static void splay(int x) {
		while (!isroot(x)) {
			int f = fa[x];
			if (!isroot(f)) {
				if (lr(x) == lr(f)) {
					rotate(f);
				} else {
					rotate(x);
				}
			}
			rotate(x);
		}
	}

	public static void build(int l, int r, int i) {
		if (l == r) {
			maxCnt[i] = dep[seg[l]];
		} else {
			int mid = (l + r) >> 1;
			build(l, mid, i << 1);
			build(mid + 1, r, i << 1 | 1);
			upSeg(i);
		}
	}

	public static void upSeg(int i) {
		maxCnt[i] = Math.max(maxCnt[i << 1], maxCnt[i << 1 | 1]);
	}

	public static void lazy(int i, int v) {
		maxCnt[i] += v;
		addTag[i] += v;
	}

	public static void down(int i) {
		if (addTag[i] != 0) {
			lazy(i << 1, addTag[i]);
			lazy(i << 1 | 1, addTag[i]);
			addTag[i] = 0;
		}
	}

	public static void add(int jobl, int jobr, int jobv, int l, int r, int i) {
		if (jobl <= l && r <= jobr) {
			lazy(i, jobv);
		} else {
			down(i);
			int mid = (l + r) >> 1;
			if (jobl <= mid) {
				add(jobl, jobr, jobv, l, mid, i << 1);
			}
			if (jobr > mid) {
				add(jobl, jobr, jobv, mid + 1, r, i << 1 | 1);
			}
			upSeg(i);
		}
	}

	public static int query(int jobl, int jobr, int l, int r, int i) {
		if (jobl <= l && r <= jobr) {
			return maxCnt[i];
		}
		down(i);
		int mid = (l + r) >> 1;
		int ans = 0;
		if (jobl <= mid) {
			ans = Math.max(ans, query(jobl, jobr, l, mid, i << 1));
		}
		if (jobr > mid) {
			ans = Math.max(ans, query(jobl, jobr, mid + 1, r, i << 1 | 1));
		}
		return ans;
	}

	// 以x为根的原树子树中，所有点到根的颜色数量整体加v
	public static void add(int x, int v) {
		add(dfn[x], dfn[x] + siz[x] - 1, v, 1, n, 1);
	}

	// 根节点到x的路径，涂新颜色，改写access方法
	public static void access(int x) {
		for (int y = 0; x != 0; y = x, x = fa[x]) {
			splay(x);
			if (rs[x] != 0) {
				add(mostl[rs[x]], 1);
			}
			if (y != 0) {
				add(mostl[y], -1);
			}
			rs[x] = y;
			up(x);
		}
	}

	public static int pathQuery(int x, int y) {
		int xylca = lca(x, y);
		int a = query(dfn[x], dfn[x], 1, n, 1);
		int b = query(dfn[y], dfn[y], 1, n, 1);
		int c = query(dfn[xylca], dfn[xylca], 1, n, 1);
		return a + b - (2 * c) + 1;
	}

	public static int subtreeQuery(int x) {
		return query(dfn[x], dfn[x] + siz[x] - 1, 1, n, 1);
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		for (int i = 1, x, y; i < n; i++) {
			x = in.nextInt();
			y = in.nextInt();
			addEdge(x, y);
			addEdge(y, x);
		}
//		dfs1(1, 0);
//		dfs2(1, 1);
		dfs3(1, 0);
		dfs4(1, 1);
		build(1, n, 1);
		for (int i = 1; i <= n; i++) {
			fa[i] = pa[i];
			mostl[i] = i;
		}
		for (int i = 1, op, x, y; i <= m; i++) {
			op = in.nextInt();
			x = in.nextInt();
			if (op == 1) {
				access(x);
			} else if (op == 2) {
				y = in.nextInt();
				out.println(pathQuery(x, y));
			} else {
				out.println(subtreeQuery(x));
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

	}

}