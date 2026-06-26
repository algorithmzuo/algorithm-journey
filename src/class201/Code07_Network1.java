package class201;

// 网络，java版
// 无向图中有n个点，点有点权，有m条边，边有颜色，颜色总数量为C，颜色的数值0 ~ C-1
// 无向图保证没有重边和自环，并且始终满足如下两个性质
// 性质1，对于任意节点连出去的边中，相同颜色的边不超过两条
// 性质2，图中不存在同色的环，指相同颜色的边构成的环
// 一共有q条操作，类型如下
// 操作 0 x y   : 点x的点权变成y
// 操作 2 c x y : 颜色c的边构成的图中，打印x到y路径上的点权最大值，不连通打印-1
// 操作 1 x y c : 点x到点y的直接边，颜色改成c，下面是具体说明
// 若不存在直接边，不修改边的颜色，打印 "No such edge."
// 若修改后不满足性质1，不修改边的颜色，打印 "Error 1.”
// 若修改后不满足性质2，不修改边的颜色，打印 "Error 2."
// 若同时不满足性质1和性质2，优先打印 "Error 1."
// 若上述情况都没出现，那么修改边的颜色，打印 "Success."
// 1 <= n <= 10^4
// C <= 10
// 1 <= m、q <= 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P2173
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;

public class Code07_Network1 {

	public static int MAXN = 100001;
	public static int n, m, C, q;

	public static int[] fa = new int[MAXN];
	public static int[] ls = new int[MAXN];
	public static int[] rs = new int[MAXN];
	public static int[] sta = new int[MAXN];
	public static boolean[] rev = new boolean[MAXN];

	public static int[] arr = new int[MAXN];
	public static int[] maxv = new int[MAXN];

	public static int[] nodeDegree = new int[MAXN];
	public static HashMap<Long, Integer> edgeColor = new HashMap<>();

	public static void up(int x) {
		maxv[x] = Math.max(Math.max(maxv[ls[x]], maxv[rs[x]]), arr[x]);
	}

	public static boolean isroot(int x) {
		return ls[fa[x]] != x && rs[fa[x]] != x;
	}

	public static int lr(int x) {
		return ls[fa[x]] == x ? 0 : 1;
	}

	public static void reverse(int x) {
		if (x != 0) {
			int tmp = ls[x];
			ls[x] = rs[x];
			rs[x] = tmp;
			rev[x] = !rev[x];
		}
	}

	public static void down(int x) {
		if (rev[x]) {
			reverse(ls[x]);
			reverse(rs[x]);
			rev[x] = false;
		}
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
		int siz = 0;
		sta[++siz] = x;
		for (int y = x; !isroot(y); y = fa[y]) {
			sta[++siz] = fa[y];
		}
		while (siz != 0) {
			down(sta[siz--]);
		}
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

	public static void access(int x) {
		for (int y = 0; x != 0; y = x, x = fa[x]) {
			splay(x);
			rs[x] = y;
			up(x);
		}
	}

	public static void makeroot(int x) {
		access(x);
		splay(x);
		reverse(x);
	}

	public static int findroot(int x) {
		access(x);
		splay(x);
		down(x);
		while (ls[x] != 0) {
			x = ls[x];
			down(x);
		}
		splay(x);
		return x;
	}

	public static void split(int x, int y) {
		makeroot(x);
		access(y);
		splay(y);
	}

	public static void link(int x, int y) {
		makeroot(x);
		if (findroot(y) != x) {
			fa[x] = y;
		}
	}

	public static void cut(int x, int y) {
		makeroot(x);
		if (findroot(y) == x && fa[y] == x && ls[y] == 0 && rs[x] == y) {
			fa[y] = rs[x] = 0;
			up(x);
		}
	}

	public static int node(int c, int x) {
		return c * n + x;
	}

	public static long edge(int x, int y) {
		int a = Math.min(x, y);
		int b = Math.max(x, y);
		return (1L * a) << 32 | b;
	}

	public static void connect(int c, int x, int y) {
		int u = node(c, x);
		int v = node(c, y);
		nodeDegree[u]++;
		nodeDegree[v]++;
		link(u, v);
		edgeColor.put(edge(x, y), c);
	}

	public static void disconnect(int c, int x, int y) {
		int u = node(c, x);
		int v = node(c, y);
		nodeDegree[u]--;
		nodeDegree[v]--;
		cut(u, v);
		edgeColor.remove(edge(x, y));
	}

	public static void updateNode(int x, int v) {
		for (int c = 0; c < C; c++) {
			int cur = node(c, x);
			splay(cur);
			arr[cur] = v;
			up(cur);
		}
	}

	public static int updateEdge(int x, int y, int c) {
		Integer pre = edgeColor.get(edge(x, y));
		if (pre == null) {
			return 1;
		}
		int p = pre;
		if (p == c) {
			return 4;
		}
		if (nodeDegree[node(c, x)] >= 2 || nodeDegree[node(c, y)] >= 2) {
			return 2;
		}
		if (findroot(node(c, x)) == findroot(node(c, y))) {
			return 3;
		}
		disconnect(p, x, y);
		connect(c, x, y);
		return 4;
	}

	public static int query(int c, int x, int y) {
		int u = node(c, x);
		int v = node(c, y);
		if (findroot(u) != findroot(v)) {
			return -1;
		}
		split(u, v);
		return maxv[v];
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		C = in.nextInt();
		q = in.nextInt();
		for (int i = 1, v; i <= n; i++) {
			v = in.nextInt();
			for (int c = 0; c < C; c++) {
				int cur = node(c, i);
				arr[cur] = v;
				maxv[cur] = v;
			}
		}
		for (int i = 1, x, y, c; i <= m; i++) {
			x = in.nextInt();
			y = in.nextInt();
			c = in.nextInt();
			connect(c, x, y);
		}
		for (int i = 1, op, x, y, c; i <= q; i++) {
			op = in.nextInt();
			if (op == 0) {
				x = in.nextInt();
				y = in.nextInt();
				updateNode(x, y);
			} else if (op == 1) {
				x = in.nextInt();
				y = in.nextInt();
				c = in.nextInt();
				int ans = updateEdge(x, y, c);
				if (ans == 1) {
					out.println("No such edge.");
				}
				if (ans == 2) {
					out.println("Error 1.");
				}
				if (ans == 3) {
					out.println("Error 2.");
				}
				if (ans == 4) {
					out.println("Success.");
				}
			} else {
				c = in.nextInt();
				x = in.nextInt();
				y = in.nextInt();
				out.println(query(c, x, y));
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