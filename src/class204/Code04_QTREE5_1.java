package class204;

// QTREE5，java版
// 给定一棵n个节点的树，每个节点有黑白两种颜色，初始所有节点都是黑色
// 接下来有q条操作，操作类型如下
// 操作 0 x : 翻转节点x的颜色
// 操作 1 x : 打印节点x到最近白色节点的距离，不存在白色节点打印-1
// 1 <= n、q <= 10^5
// 测试链接 : https://www.luogu.com.cn/problem/SP2939
// 测试链接 : https://www.spoj.com/problems/QTREE5/
// 提交以下的code，提交时请把类名改成"Main"
// 本题卡常数时间，java的实现无法通过，手撸有序表也会超时
// 想通过用C++实现，本节课Code04_QTREE5_2文件就是C++的实现
// 两个版本的逻辑完全一样，C++版本可以通过所有测试

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.TreeMap;

public class Code04_QTREE5_1 {

	public static int MAXN = 100001;
	public static int INF = 1000000001;
	public static int n, q;

	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXN << 1];
	public static int[] to = new int[MAXN << 1];
	public static int cntg;

	public static int[] fa = new int[MAXN];
	public static int[] ls = new int[MAXN];
	public static int[] rs = new int[MAXN];

	// 节点颜色
	public static int[] color = new int[MAXN];

	// siz[x]表示以x为根的辅助splay节点数量，也就是实链长度，不包括虚子树节点
	public static int[] siz = new int[MAXN];

	// lm[x]表示以x为根的辅助splay中，从深度最小的节点出发，也就是实链顶点，到最近白色节点的距离
	public static int[] lm = new int[MAXN];

	// rm[x]表示以x为根的辅助splay中，从深度最大的节点出发，也就是实链底点，到最近白色节点的距离
	public static int[] rm = new int[MAXN];

	// 每个节点x有一张表，记录每个虚儿子，各自去下方，最近白点的距离和次数
	// 表中最小值+1，就是从x进入虚子树后，到最近白点的距离
	public static HashMap<Integer, TreeMap<Integer, Integer>> vir = new HashMap<>();

	public static void addEdge(int u, int v) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		head[u] = cntg;
	}

	public static void insert(int x, int v) {
		if (!vir.containsKey(x)) {
			vir.put(x, new TreeMap<>());
		}
		vir.get(x).put(v, vir.get(x).getOrDefault(v, 0) + 1);
	}

	public static void remove(int x, int v) {
		int cnt = vir.get(x).get(v);
		if (cnt == 1) {
			vir.get(x).remove(v);
		} else {
			vir.get(x).put(v, cnt - 1);
		}
	}

	public static int getmin(int x) {
		if (!vir.containsKey(x) || vir.get(x).isEmpty()) {
			return INF;
		}
		return vir.get(x).firstKey();
	}

	// 课上重点图解了决策
	public static void up(int x) {
		siz[x] = siz[ls[x]] + siz[rs[x]] + 1;
		int fromx = Math.min(color[x] == 1 ? 0 : INF, getmin(x) + 1);
		lm[x] = Math.min(lm[ls[x]], siz[ls[x]] + Math.min(fromx, lm[rs[x]] + 1));
		rm[x] = Math.min(rm[rs[x]], siz[rs[x]] + Math.min(fromx, rm[ls[x]] + 1));
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
		up(x);
	}

	public static void access(int x) {
		for (int y = 0; x != 0; y = x, x = fa[x]) {
			splay(x);
			if (rs[x] != 0) {
				insert(x, lm[rs[x]]);
			}
			if (y != 0) {
				remove(x, lm[y]);
			}
			rs[x] = y;
			up(x);
		}
	}

	public static void reverseColor(int x) {
		access(x);
		splay(x);
		color[x] ^= 1;
		up(x);
	}

	public static int query(int x) {
		access(x);
		splay(x);
		// access(x)之后，x是当前辅助splay中，深度最大的节点
		// 所以rm[x]就是x到最近白点的距离
		return rm[x] == INF ? -1 : rm[x];
	}

	public static void dfs(int u, int f) {
		fa[u] = f;
		siz[u] = 1;
		lm[u] = rm[u] = INF;
		for (int e = head[u]; e != 0; e = nxt[e]) {
			int v = to[e];
			if (v != f) {
				dfs(v, u);
				// 初始认为不存在实链，每个v都是u的虚儿子
				insert(u, lm[v]);
			}
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		lm[0] = rm[0] = INF;
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
				reverseColor(x);
			} else {
				out.println(query(x));
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