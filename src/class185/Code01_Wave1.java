package class185;

// 震波，java版
// 树上有n个点，每个点有点权，给定n-1条边，边权都是1
// 接下来有m条操作，每条操作是如下两种类型中的一种
// 操作 0 x y : 点x出发，距离<=y的所有点，打印点权累加和
// 操作 1 x y : 点x的点权变成y
// 1 <= n、m <= 10^5
// 1 <= 点权 <= 10^4
// 本题要求强制在线，得到操作参数的规则，打开测试链接查看
// 测试链接 : https://www.luogu.com.cn/problem/P6329
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code01_Wave1 {

	public static int MAXN = 100001;
	public static int MAXT = 10000001;
	public static int n, m;
	public static int[] arr = new int[MAXN];

	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXN << 1];
	public static int[] to = new int[MAXN << 1];
	public static int cntg;

	public static int[] fa = new int[MAXN];
	public static int[] dep = new int[MAXN];
	public static int[] siz = new int[MAXN];
	public static int[] son = new int[MAXN];
	public static int[] top = new int[MAXN];

	public static boolean[] vis = new boolean[MAXN];
	public static int[] centfa = new int[MAXN];

	public static int[] addTree = new int[MAXN];
	public static int[] minusTree = new int[MAXN];
	public static int[] ls = new int[MAXT];
	public static int[] rs = new int[MAXT];
	public static int[] sum = new int[MAXT];
	public static int cntt;

	// 讲解118，递归函数改成迭代所需要的栈
	public static int[][] stack = new int[MAXN][4];
	public static int u, f, t, e;
	public static int stacksize;

	public static void push(int u, int f, int t, int e) {
		stack[stacksize][0] = u;
		stack[stacksize][1] = f;
		stack[stacksize][2] = t;
		stack[stacksize][3] = e;
		stacksize++;
	}

	public static void pop() {
		--stacksize;
		u = stack[stacksize][0];
		f = stack[stacksize][1];
		t = stack[stacksize][2];
		e = stack[stacksize][3];
	}

	public static void addEdge(int u, int v) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		head[u] = cntg;
	}

	// 重链剖分收集信息递归版，java会爆栈，C++不会
	public static void dfs1(int u, int f) {
		fa[u] = f;
		dep[u] = dep[f] + 1;
		siz[u] = 1;
		for (int e = head[u], v; e > 0; e = nxt[e]) {
			v = to[e];
			if (v != f) {
				dfs1(v, u);
			}
		}
		for (int ei = head[u], v; ei > 0; ei = nxt[ei]) {
			v = to[ei];
			if (v != f) {
				siz[u] += siz[v];
				if (son[u] == 0 || siz[son[u]] < siz[v]) {
					son[u] = v;
				}
			}
		}
	}

	// 根据重儿子剖分的递归版，java会爆栈，C++不会
	public static void dfs2(int u, int t) {
		top[u] = t;
		if (son[u] == 0) {
			return;
		}
		dfs2(son[u], t);
		for (int e = head[u], v; e > 0; e = nxt[e]) {
			v = to[e];
			if (v != fa[u] && v != son[u]) {
				dfs2(v, v);
			}
		}
	}

	// dfs1改成迭代版
	public static void dfs3(int cur, int father) {
		stacksize = 0;
		push(cur, father, 0, -1);
		while (stacksize > 0) {
			pop();
			if (e == -1) {
				fa[u] = f;
				dep[u] = dep[f] + 1;
				siz[u] = 1;
				e = head[u];
			} else {
				e = nxt[e];
			}
			if (e != 0) {
				push(u, f, 0, e);
				int v = to[e];
				if (v != f) {
					push(v, u, 0, -1);
				}
			} else {
				for (int ei = head[u]; ei > 0; ei = nxt[ei]) {
					int v = to[ei];
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

	// dfs2改成迭代版
	public static void dfs4(int cur, int tag) {
		stacksize = 0;
		push(cur, 0, tag, -1);
		while (stacksize > 0) {
			pop();
			if (e == -1) {
				top[u] = t;
				if (son[u] == 0) {
					continue;
				}
				push(u, 0, t, -2);
				push(son[u], 0, t, -1);
				continue;
			} else if (e == -2) {
				e = head[u];
			} else {
				e = nxt[e];
			}
			if (e != 0) {
				push(u, 0, t, e);
				int v = to[e];
				if (v != fa[u] && v != son[u]) {
					push(v, 0, v, -1);
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
		return dep[x] + dep[y] - (dep[getLca(x, y)] << 1);
	}

	// 找重心需要计算子树大小的递归版，java会爆栈，C++不会
	public static void getSize1(int u, int fa) {
		siz[u] = 1;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (v != fa && !vis[v]) {
				getSize1(v, u);
				siz[u] += siz[v];
			}
		}
	}

	// getSize1的迭代版
	public static void getSize2(int cur, int fa) {
		stacksize = 0;
		push(cur, fa, 0, -1);
		while (stacksize > 0) {
			pop();
			if (e == -1) {
				siz[u] = 1;
				e = head[u];
			} else {
				e = nxt[e];
			}
			if (e != 0) {
				push(u, f, 0, e);
				int v = to[e];
				if (v != f && !vis[v]) {
					push(v, u, 0, -1);
				}
			} else {
				for (int ei = head[u]; ei > 0; ei = nxt[ei]) {
					int v = to[ei];
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
			for (int e = head[u]; e > 0; e = nxt[e]) {
				int v = to[e];
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
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (!vis[v]) {
				centroidTree(getCentroid(v, u), u);
			}
		}
	}

	public static int add(int jobi, int jobv, int l, int r, int i) {
		if (i == 0) {
			i = ++cntt;
		}
		if (l == r) {
			sum[i] += jobv;
		} else {
			int mid = (l + r) >> 1;
			if (jobi <= mid) {
				ls[i] = add(jobi, jobv, l, mid, ls[i]);
			} else {
				rs[i] = add(jobi, jobv, mid + 1, r, rs[i]);
			}
			sum[i] = sum[ls[i]] + sum[rs[i]];
		}
		return i;
	}

	public static int query(int jobl, int jobr, int l, int r, int i) {
		if (i == 0) {
			return 0;
		}
		if (jobl <= l && r <= jobr) {
			return sum[i];
		}
		int mid = (l + r) >> 1;
		int ans = 0;
		if (jobl <= mid) {
			ans += query(jobl, jobr, l, mid, ls[i]);
		}
		if (jobr > mid) {
			ans += query(jobl, jobr, mid + 1, r, rs[i]);
		}
		return ans;
	}

	public static void add(int x, int v) {
		addTree[x] = add(0, v, 0, n - 1, addTree[x]);
		for (int cur = x, fa = centfa[cur]; fa > 0; cur = fa, fa = centfa[cur]) {
			int dist = getDist(x, fa);
			addTree[fa] = add(dist, v, 0, n - 1, addTree[fa]);
			minusTree[cur] = add(dist, v, 0, n - 1, minusTree[cur]);
		}
	}

	public static int query(int x, int k) {
		int ans = query(0, k, 0, n - 1, addTree[x]);
		for (int cur = x, fa = centfa[cur]; fa > 0; cur = fa, fa = centfa[cur]) {
			int dist = getDist(x, fa);
			if (k - dist >= 0) {
				ans += query(0, k - dist, 0, n - 1, addTree[fa]);
				ans -= query(0, k - dist, 0, n - 1, minusTree[cur]);
			}
		}
		return ans;
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		for (int i = 1; i <= n; i++) {
			arr[i] = in.nextInt();
		}
		for (int i = 1, u, v; i < n; i++) {
			u = in.nextInt();
			v = in.nextInt();
			addEdge(u, v);
			addEdge(v, u);
		}
		// dfs1(1, 0);
		// dfs2(1, 1);
		dfs3(1, 0);
		dfs4(1, 1);
		centroidTree(getCentroid(1, 0), 0);
		for (int i = 1; i <= n; i++) {
			add(i, arr[i]);
		}
		int lastAns = 0;
		for (int i = 1, op, x, y; i <= m; i++) {
			op = in.nextInt();
			x = in.nextInt();
			y = in.nextInt();
			x ^= lastAns;
			y ^= lastAns;
			if (op == 0) {
				lastAns = query(x, y);
				out.println(lastAns);
			} else {
				add(x, y - arr[x]);
				arr[x] = y;
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
