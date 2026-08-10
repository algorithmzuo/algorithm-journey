package class204;

// QTREE6，java版
// 给定一棵n个节点的树，每个节点有黑白两种颜色，初始所有节点都是黑色
// 接下来有q条操作，操作类型如下
// 操作 0 x : 打印节点x所在的同色连通块大小
// 操作 1 x : 翻转节点x的颜色
// 1 <= n、q <= 10^5
// 测试链接 : https://www.luogu.com.cn/problem/SP16549
// 测试链接 : https://www.spoj.com/problems/QTREE6/
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code02_QTREE6_1 {

	public static int MAXN = 100001;
	public static int n, q;

	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXN << 1];
	public static int[] to = new int[MAXN << 1];
	public static int cntg;

	// 分别维护黑色和白色两棵LCT，第一维表示颜色
	// 固定父边(x, parent[x])属于color[x]对应的LCT
	public static int[][] fa = new int[2][MAXN];
	public static int[][] ls = new int[2][MAXN];
	public static int[][] rs = new int[2][MAXN];

	// 原树固定以1为根，每条父边(x, parent[x])按照儿子x的颜色归属某棵LCT
	// 节点x翻色时只需移动它和固定父亲之间的边，不需要遍历所有儿子
	// 从而避免菊花图中单次修改退化为O(n)
	public static int[] parent = new int[MAXN];

	// 节点颜色，黑色为0，白色为1
	public static int[] color = new int[MAXN];

	// vir[c][x]表示颜色c的LCT中，x的所有直接虚儿子所代表的完整子树里
	// 颜色为c的节点总量
	public static int[][] vir = new int[2][MAXN];

	// sum[c][x]表示颜色c的LCT中，以x为根的辅助Splay及其挂载虚子树中
	// 颜色为c的节点总量
	// 包括左右儿子的sum、x的虚子树贡献，以及x自身是否为颜色c的贡献
	public static int[][] sum = new int[2][MAXN];

	public static void addEdge(int u, int v) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		head[u] = cntg;
	}

	public static void up(int c, int x) {
		sum[c][x] = sum[c][ls[c][x]] + sum[c][rs[c][x]] + vir[c][x] + (color[x] == c ? 1 : 0);
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
			vir[c][x] += sum[c][rs[c][x]];
			vir[c][x] -= sum[c][y];
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

	// 连接固定父边(x, f)，x是子，f是父，连接后x作为f的虚儿子
	public static void link(int c, int x, int f) {
		if (f == 0) {
			return;
		}
		access(c, f);
		splay(c, f);
		splay(c, x);
		fa[c][x] = f;
		vir[c][f] += sum[c][x];
		up(c, f);
	}

	// 删除固定父边(x, f)，x是子，f是父
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
			return sum[c][top];
		} else {
			return sum[c][rs[c][top]];
		}
	}

	public static void changeColor(int x) {
		int pre = color[x];
		int cur = pre ^ 1;
		int f = parent[x];
		cut(pre, x, f);
		color[x] = cur;
		link(cur, x, f);
	}

	public static void dfs(int u, int f) {
		parent[u] = f;
		sum[0][u] = 1;
		for (int e = head[u]; e != 0; e = nxt[e]) {
			int v = to[e];
			if (v != f) {
				dfs(v, u);
				link(0, v, u);
			}
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		for (int i = 1, u, v; i < n; i++) {
			u = in.nextInt();
			v = in.nextInt();
			addEdge(u, v);
			addEdge(v, u);
		}
		dfs(1, 0);
		q = in.nextInt();
		for (int i = 1, op, x; i <= q; i++) {
			op = in.nextInt();
			x = in.nextInt();
			if (op == 0) {
				out.println(query(x));
			} else {
				changeColor(x);
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