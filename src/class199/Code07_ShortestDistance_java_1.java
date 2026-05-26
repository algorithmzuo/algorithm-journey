package class199;

// 最短距离，巧妙的解法，java版
// 图中有n个点、n条无向边，每条边有边权，图是一棵基环树
// 一共有m条操作，每条操作是如下两种类型中的一种
// 操作 1 x y : 第x号边的边权修改为y
// 操作 2 x y : 查询点x和点y之间的最短距离
// 1 <= n <= 10^5 + 6
// 1 <= m <= 10^5
// 0 <= 边权 <= 5000
// 测试链接 : https://www.luogu.com.cn/problem/P4949
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code07_ShortestDistance_java_1 {

	public static int MAXN = 100007;
	public static int n, m;
	public static int[] u = new int[MAXN];
	public static int[] v = new int[MAXN];
	public static int[] w = new int[MAXN];

	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXN << 1];
	public static int[] to = new int[MAXN << 1];
	public static int cntg;

	public static int[] dfn = new int[MAXN];
	public static int cntd;
	public static int node1, node2, skipEdge;

	public static int[] fa = new int[MAXN];
	public static int[] dep = new int[MAXN];
	public static int[] siz = new int[MAXN];
	public static int[] son = new int[MAXN];
	public static int[] top = new int[MAXN];

	public static int[] tree = new int[MAXN];

	public static void addEdge(int u, int v) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		head[u] = cntg;
	}

	public static void dfs1(int u) {
		dfn[u] = ++cntd;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (dfn[v] == 0) {
				dfs1(v);
			} else if (dfn[u] < dfn[v]) {
				node1 = u;
				node2 = v;
				skipEdge = (e + 1) >> 1;
			}
		}
	}

	public static void dfs2(int u, int f) {
		fa[u] = f;
		dep[u] = dep[f] + 1;
		siz[u] = 1;
		for (int e = head[u], v; e > 0; e = nxt[e]) {
			v = to[e];
			if ((e + 1) >> 1 != skipEdge && v != f) {
				dfs2(v, u);
				siz[u] += siz[v];
				if (son[u] == 0 || siz[son[u]] < siz[v]) {
					son[u] = v;
				}
			}
		}
	}

	public static void dfs3(int u, int t) {
		top[u] = t;
		dfn[u] = ++cntd;
		if (son[u] != 0) {
			dfs3(son[u], t);
		}
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if ((e + 1) >> 1 != skipEdge && v != fa[u] && v != son[u]) {
				dfs3(v, v);
			}
		}
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
		int ans = 0;
		while (i >= 1) {
			ans += tree[i];
			i -= lowbit(i);
		}
		return ans;
	}

	public static int sum(int l, int r) {
		if (l > r) {
			return 0;
		}
		return query(r) - query(l - 1);
	}

	public static void prepare() {
		dfs1(1);
		cntd = 0;
		dfs2(1, 0);
		dfs3(1, 1);
		for (int i = 1; i <= n; i++) {
			if (i != skipEdge) {
				add(Math.max(dfn[u[i]], dfn[v[i]]), w[i]);
			}
		}
	}

	public static void setEdge(int edge, int val) {
		if (edge != skipEdge) {
			add(Math.max(dfn[u[edge]], dfn[v[edge]]), val - w[edge]);
		}
		w[edge] = val;
	}

	public static int jump(int x, int y) {
		int ans = 0;
		while (top[x] != top[y]) {
			if (dep[top[x]] < dep[top[y]]) {
				ans += sum(dfn[top[y]], dfn[y]);
				y = fa[top[y]];
			} else {
				ans += sum(dfn[top[x]], dfn[x]);
				x = fa[top[x]];
			}
		}
		if (dfn[x] < dfn[y]) {
			ans += sum(dfn[x] + 1, dfn[y]);
		} else {
			ans += sum(dfn[y] + 1, dfn[x]);
		}
		return ans;
	}

	public static int getDistance(int x, int y) {
		int p1 = jump(x, y);
		int p2 = jump(x, node1) + w[skipEdge] + jump(node2, y);
		int p3 = jump(x, node2) + w[skipEdge] + jump(node1, y);
		return Math.min(p1, Math.min(p2, p3));
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		for (int i = 1; i <= n; i++) {
			u[i] = in.nextInt();
			v[i] = in.nextInt();
			w[i] = in.nextInt();
			addEdge(u[i], v[i]);
			addEdge(v[i], u[i]);
		}
		prepare();
		for (int i = 1, op, x, y; i <= m; i++) {
			op = in.nextInt();
			x = in.nextInt();
			y = in.nextInt();
			if (op == 1) {
				setEdge(x, y);
			} else {
				out.println(getDistance(x, y));
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