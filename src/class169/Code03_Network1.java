package class169;

// 网络，java版
// 一共有n个服务器，给定n-1条边，所有服务器连成一棵树
// 某两个服务器之间的路径上，可能接受一条请求，路径上的所有服务器都需要保存该请求的重要度
// 一共有m条操作，每条操作是如下3种类型中的一种，操作依次发生，第i条操作发生的时间为i
// 操作 0 x y v : x号服务器到y号服务器的路径上，增加了一个重要度为v的请求
// 操作 1 t     : 当初时间为t的操作，一定是增加请求的操作，现在这个请求结束了
// 操作 2 x     : 当前时间下，和x号服务器无关的所有请求中，打印最大的重要度
// 关于操作2，如果当前时间下，没有任何请求、或者所有请求都和x号服务器有关，打印-1
// 2 <= n <= 10^5    1 <= m <= 2 * 10^5    1 <= 重要度 <= 10^9
// 测试链接 : https://www.luogu.com.cn/problem/P3250
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code03_Network1 {

	public static int MAXN = 100001;
	public static int MAXM = 200001;
	public static int MAXP = 20;
	public static int INF = 1000000001;
	public static int n, m;

	// 链式前向星
	public static int[] head = new int[MAXN];
	public static int[] next = new int[MAXN << 1];
	public static int[] to = new int[MAXN << 1];
	public static int cntg = 0;

	// 树上点差分 + 树上倍增
	public static int[] fa = new int[MAXN];
	public static int[] dep = new int[MAXN];
	public static int[] siz = new int[MAXN];
	public static int[] dfn = new int[MAXN];
	public static int[][] stjump = new int[MAXN][MAXP];
	public static int cntd;

	// 树状数组
	public static int[] tree = new int[MAXN];

	// 事件编号组成的数组
	public static int[] eid = new int[MAXM];
	// 如果op == 0，添加点x到点y，重要度为v的路径
	// 如果op == 1，删除点x到点y，重要度为v的路径
	// 如果op == 2，查询和x相关的答案，y表示问题的编号
	public static int[] op = new int[MAXM];
	public static int[] x = new int[MAXM];
	public static int[] y = new int[MAXM];
	public static int[] v = new int[MAXM];
	public static int cntq = 0;

	// 整体二分
	public static int[] lset = new int[MAXM];
	public static int[] rset = new int[MAXM];

	public static int[] ans = new int[MAXM];

	public static void addEdge(int u, int v) {
		next[++cntg] = head[u];
		to[cntg] = v;
		head[u] = cntg;
	}

	// 递归版，C++可以通过，java会爆栈
	public static void dfs1(int u, int f) {
		fa[u] = f;
		dep[u] = dep[f] + 1;
		siz[u] = 1;
		dfn[u] = ++cntd;
		stjump[u][0] = f;
		for (int p = 1; p < MAXP; p++) {
			stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
		}
		for (int e = head[u]; e != 0; e = next[e]) {
			if (to[e] != f) {
				dfs1(to[e], u);
			}
		}
		for (int e = head[u]; e != 0; e = next[e]) {
			if (to[e] != f) {
				siz[u] += siz[to[e]];
			}
		}
	}

	// 不会改迭代版，去看讲解118，详解了从递归版改迭代版
	public static int[][] ufe = new int[MAXN][3];

	public static int stacksize, u, f, e;

	public static void push(int u, int f, int e) {
		ufe[stacksize][0] = u;
		ufe[stacksize][1] = f;
		ufe[stacksize][2] = e;
		stacksize++;
	}

	public static void pop() {
		--stacksize;
		u = ufe[stacksize][0];
		f = ufe[stacksize][1];
		e = ufe[stacksize][2];
	}

	// dfs1的迭代版
	public static void dfs2() {
		stacksize = 0;
		push(1, 0, -1);
		while (stacksize > 0) {
			pop();
			if (e == -1) {
				fa[u] = f;
				dep[u] = dep[f] + 1;
				siz[u] = 1;
				dfn[u] = ++cntd;
				stjump[u][0] = f;
				for (int p = 1; p < MAXP; p++) {
					stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
				}
				e = head[u];
			} else {
				e = next[e];
			}
			if (e != 0) {
				push(u, f, e);
				if (to[e] != f) {
					push(to[e], u, -1);
				}
			} else {
				for (int e = head[u]; e != 0; e = next[e]) {
					if (to[e] != f) {
						siz[u] += siz[to[e]];
					}
				}
			}
		}
	}

	public static int lca(int a, int b) {
		if (dep[a] < dep[b]) {
			int tmp = a;
			a = b;
			b = tmp;
		}
		for (int p = MAXP - 1; p >= 0; p--) {
			if (dep[stjump[a][p]] >= dep[b]) {
				a = stjump[a][p];
			}
		}
		if (a == b) {
			return a;
		}
		for (int p = MAXP - 1; p >= 0; p--) {
			if (stjump[a][p] != stjump[b][p]) {
				a = stjump[a][p];
				b = stjump[b][p];
			}
		}
		return stjump[a][0];
	}

	public static int lowbit(int i) {
		return i & -i;
	}

	public static void add(int i, int v) {
		while (i <= n) {
			tree[i] += v;
			i += lowbit(i);
		}
	}

	public static int query(int i) {
		int ret = 0;
		while (i > 0) {
			ret += tree[i];
			i -= lowbit(i);
		}
		return ret;
	}

	// 点x到点y的路径上，每个点增加v个请求数量
	public static void pathAdd(int x, int y, int v) {
		int xylca = lca(x, y);
		int lcafa = fa[xylca];
		add(dfn[x], v);
		add(dfn[y], v);
		add(dfn[xylca], -v);
		if (lcafa != 0) {
			add(dfn[lcafa], -v);
		}
	}

	// 查询和x点相关的请求数量
	public static int pointQuery(int x) {
		return query(dfn[x] + siz[x] - 1) - query(dfn[x] - 1);
	}

	public static void compute(int el, int er, int vl, int vr) {
		if (el > er) {
			return;
		}
		if (vl == vr) {
			for (int i = el; i <= er; i++) {
				int id = eid[i];
				if (op[id] == 2) {
					ans[y[id]] = vl;
				}
			}
		} else {
			int mid = (vl + vr) / 2;
			int lsiz = 0, rsiz = 0, request = 0;
			for (int i = el; i <= er; i++) {
				int id = eid[i];
				if (op[id] == 0) {
					if (v[id] <= mid) {
						lset[++lsiz] = id;
					} else {
						pathAdd(x[id], y[id], 1);
						request++;
						rset[++rsiz] = id;
					}
				} else if (op[id] == 1) {
					if (v[id] <= mid) {
						lset[++lsiz] = id;
					} else {
						pathAdd(x[id], y[id], -1);
						request--;
						rset[++rsiz] = id;
					}
				} else {
					if (pointQuery(x[id]) == request) {
						lset[++lsiz] = id;
					} else {
						rset[++rsiz] = id;
					}
				}
			}
			for (int i = 1; i <= lsiz; i++) {
				eid[el + i - 1] = lset[i];
			}
			for (int i = 1; i <= rsiz; i++) {
				eid[el + lsiz + i - 1] = rset[i];
			}
			for (int i = 1; i <= rsiz; i++) {
				int id = rset[i];
				if (op[id] == 0 && v[id] > mid) {
					pathAdd(x[id], y[id], -1);
				}
				if (op[id] == 1 && v[id] > mid) {
					pathAdd(x[id], y[id], 1);
				}
			}
			compute(el, el + lsiz - 1, vl, mid);
			compute(el + lsiz, er, mid + 1, vr);
		}
	}

	public static void prepare() {
		dfs2();
		for (int i = 1; i <= m; i++) {
			if (op[i] == 1) {
				int pre = x[i];
				x[i] = x[pre];
				y[i] = y[pre];
				v[i] = v[pre];
			}
			if (op[i] == 2) {
				y[i] = ++cntq;
			}
		}
		for (int i = 1; i <= m; i++) {
			eid[i] = i;
		}
	}

	public static void main(String[] args) throws IOException {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		for (int i = 1, u, v; i < n; i++) {
			u = in.nextInt();
			v = in.nextInt();
			addEdge(u, v);
			addEdge(v, u);
		}
		for (int i = 1; i <= m; i++) {
			op[i] = in.nextInt();
			x[i] = in.nextInt();
			if (op[i] == 0) {
				y[i] = in.nextInt();
				v[i] = in.nextInt();
			}
		}
		prepare();
		compute(1, m, 0, INF);
		for (int i = 1; i <= cntq; i++) {
			if (ans[i] == 0) {
				out.println(-1);
			} else {
				out.println(ans[i]);
			}
		}
		out.flush();
		out.close();
	}

	// 读写工具类
	static class FastReader {
		private final byte[] buffer = new byte[1 << 20];
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
