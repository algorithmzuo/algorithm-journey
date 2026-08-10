package class204;

// QTREE7，java版
// 给定一棵n个节点的树，每个节点有黑白两种颜色，给定初始颜色和点权
// 接下来有q条操作，操作类型如下
// 操作 0 x   : 打印节点x所在的同色连通块中的最大点权
// 操作 1 x   : 翻转节点x的颜色
// 操作 2 x w : 节点x的点权修改为w
// 1 <= n、q <= 10^5
// 测试链接 : https://www.luogu.com.cn/problem/SP16580
// 测试链接 : https://www.spoj.com/problems/QTREE7/
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.TreeMap;

public class Code03_QTREE7_1 {

	public static int MAXN = 100001;
	public static int INF = 1000000001;
	public static int n, q;

	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXN << 1];
	public static int[] to = new int[MAXN << 1];
	public static int cntg;

	public static int[][] fa = new int[2][MAXN];
	public static int[][] ls = new int[2][MAXN];
	public static int[][] rs = new int[2][MAXN];

	public static int[] parent = new int[MAXN];
	public static int[] color = new int[MAXN];
	public static int[] val = new int[MAXN];

	// black.get(x)表示黑色LCT中节点x维护的multiset
	// 保存x每个直接虚儿子的完整子树最大值，以及该最大值的出现次数
	public static HashMap<Integer, TreeMap<Integer, Integer>> black = new HashMap<>();

	// white.get(x)表示白色LCT中节点x维护的multiset
	// 保存x每个直接虚儿子的完整子树最大值，以及该最大值的出现次数
	public static HashMap<Integer, TreeMap<Integer, Integer>> white = new HashMap<>();

	// maxv[c][x]表示颜色c的LCT中，以x为根的辅助Splay汇总的最大点权
	// 包括左右儿子、所有虚子树以及x自身
	public static int[][] maxv = new int[2][MAXN];

	public static void addEdge(int u, int v) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		head[u] = cntg;
	}

	public static void insert(int c, int x, int v) {
		if (c == 0) {
			if (!black.containsKey(x)) {
				black.put(x, new TreeMap<>());
			}
			black.get(x).put(v, black.get(x).getOrDefault(v, 0) + 1);
		} else {
			if (!white.containsKey(x)) {
				white.put(x, new TreeMap<>());
			}
			white.get(x).put(v, white.get(x).getOrDefault(v, 0) + 1);
		}
	}

	public static void remove(int c, int x, int v) {
		if (c == 0) {
			int cnt = black.get(x).get(v);
			if (cnt == 1) {
				black.get(x).remove(v);
			} else {
				black.get(x).put(v, cnt - 1);
			}
		} else {
			int cnt = white.get(x).get(v);
			if (cnt == 1) {
				white.get(x).remove(v);
			} else {
				white.get(x).put(v, cnt - 1);
			}
		}
	}

	public static int getmax(int c, int x) {
		if (c == 0) {
			if (!black.containsKey(x) || black.get(x).isEmpty()) {
				return -INF;
			}
			return black.get(x).lastKey();
		} else {
			if (!white.containsKey(x) || white.get(x).isEmpty()) {
				return -INF;
			}
			return white.get(x).lastKey();
		}
	}

	public static void up(int c, int x) {
		maxv[c][x] = Math.max(val[x], Math.max(getmax(c, x), Math.max(maxv[c][ls[c][x]], maxv[c][rs[c][x]])));
	}

	public static boolean isroot(int c, int x) {
		return ls[c][fa[c][x]] != x && rs[c][fa[c][x]] != x;
	}

	public static int lr(int c, int x) {
		return ls[c][fa[c][x]] == x ? 0 : 1;
	}

	public static void rotate(int c, int x) {
		int f = fa[c][x], g = fa[c][f];
		if (lr(c, x) == 0) {
			ls[c][f] = rs[c][x];
			if (ls[c][f] != 0) {
				fa[c][ls[c][f]] = f;
			}
			rs[c][x] = f;
		} else {
			rs[c][f] = ls[c][x];
			if (rs[c][f] != 0) {
				fa[c][rs[c][f]] = f;
			}
			ls[c][x] = f;
		}
		if (!isroot(c, f)) {
			if (lr(c, f) == 0) {
				ls[c][g] = x;
			} else {
				rs[c][g] = x;
			}
		}
		fa[c][f] = x;
		fa[c][x] = g;
		up(c, f);
		up(c, x);
	}

	public static void splay(int c, int x) {
		while (!isroot(c, x)) {
			int f = fa[c][x];
			if (!isroot(c, f)) {
				if (lr(c, x) == lr(c, f)) {
					rotate(c, f);
				} else {
					rotate(c, x);
				}
			}
			rotate(c, x);
		}
		up(c, x);
	}

	public static void access(int c, int x) {
		for (int y = 0; x != 0; y = x, x = fa[c][x]) {
			splay(c, x);
			// 实儿子变成虚儿子，将它的子树最大值插入
			// 虚儿子变成实儿子，将它的子树最大值删除
			if (rs[c][x] != 0) {
				insert(c, x, maxv[c][rs[c][x]]);
			}
			if (y != 0) {
				remove(c, x, maxv[c][y]);
			}
			rs[c][x] = y;
			up(c, x);
		}
	}

	public static int findroot(int c, int x) {
		access(c, x);
		splay(c, x);
		while (ls[c][x] != 0) {
			x = ls[c][x];
		}
		splay(c, x);
		return x;
	}

	public static void link(int c, int x, int f) {
		if (f == 0) {
			return;
		}
		access(c, f);
		splay(c, f);
		splay(c, x);
		fa[c][x] = f;
		insert(c, f, maxv[c][x]);
		up(c, f);
	}

	public static void cut(int c, int x, int f) {
		access(c, x);
		splay(c, x);
		if (f != 0) {
			int left = ls[c][x];
			fa[c][left] = 0;
			ls[c][x] = 0;
			up(c, x);
		}
	}

	public static int query(int x) {
		int c = color[x];
		int top = findroot(c, x);
		if (color[top] == c) {
			return maxv[c][top];
		}
		return maxv[c][rs[c][top]];
	}

	public static void changeColor(int x) {
		int pre = color[x];
		int cur = pre ^ 1;
		int f = parent[x];
		cut(pre, x, f);
		color[x] = cur;
		link(cur, x, f);
	}

	public static void updateValue(int x, int w) {
		access(0, x);
		splay(0, x);
		access(1, x);
		splay(1, x);
		val[x] = w;
		up(0, x);
		up(1, x);
	}

	public static void dfs(int u, int f) {
		parent[u] = f;
		for (int e = head[u]; e != 0; e = nxt[e]) {
			int v = to[e];
			if (v != f) {
				dfs(v, u);
				link(color[v], v, u);
			}
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		for (int i = 1; i < n; i++) {
			int u = in.nextInt();
			int v = in.nextInt();
			addEdge(u, v);
			addEdge(v, u);
		}
		for (int i = 1; i <= n; i++) {
			color[i] = in.nextInt();
		}
		maxv[0][0] = maxv[1][0] = -INF;
		for (int i = 1, w; i <= n; i++) {
			w = in.nextInt();
			val[i] = maxv[0][i] = maxv[1][i] = w;
		}
		dfs(1, 0);
		q = in.nextInt();
		for (int i = 1, op, x, w; i <= q; i++) {
			op = in.nextInt();
			x = in.nextInt();
			if (op == 0) {
				out.println(query(x));
			} else if (op == 1) {
				changeColor(x);
			} else {
				w = in.nextInt();
				updateValue(x, w);
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
				if (len <= 0) {
					return -1;
				}
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
				val = val * 10 + c - '0';
				c = readByte();
			}
			return neg ? -val : val;
		}

	}

}