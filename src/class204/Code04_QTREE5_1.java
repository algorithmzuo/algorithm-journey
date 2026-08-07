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

	public static int[] color = new int[MAXN];

	// siz[x]表示以x为根的辅助Splay节点总量，不包括虚子树中的节点
	public static int[] siz = new int[MAXN];

	// lm[x]表示以x为根的辅助Splay中，从深度最浅的节点出发，到最近白色节点的距离
	public static int[] lm = new int[MAXN];

	// rm[x]表示以x为根的辅助Splay中，从深度最深的节点出发，到最近白色节点的距离
	public static int[] rm = new int[MAXN];

	// vir.get(x)表示节点x维护的multiset
	// 保存从x经过每个直接虚儿子，到达最近白色节点的距离，以及距离的出现次数
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

	public static void up(int x) {
		siz[x] = siz[ls[x]] + siz[rs[x]] + 1;
		int cur = color[x] == 1 ? 0 : INF;
		// 从辅助Splay中深度最浅的节点出发，最近白点可能在左子树中，也可能经过x到达其他方向
		lm[x] = Math.min(lm[ls[x]], siz[ls[x]] + Math.min(cur, Math.min(getmin(x), lm[rs[x]] + 1)));
		// 从辅助Splay中深度最深的节点出发，最近白点可能在右子树中，也可能经过x到达其他方向
		rm[x] = Math.min(rm[rs[x]], siz[rs[x]] + Math.min(cur, Math.min(getmin(x), rm[ls[x]] + 1)));
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
			// 原右儿子由实儿子变成虚儿子
			// 从x到其中最近白点的距离为lm[rs[x]] + 1
			// y由虚儿子变成实儿子
			// 删除它原来在x的multiset中的贡献
			if (rs[x] != 0) {
				insert(x, lm[rs[x]] + 1);
			}
			if (y != 0) {
				remove(x, lm[y] + 1);
			}
			rs[x] = y;
			up(x);
		}
	}

	public static void changeColor(int x) {
		access(x);
		splay(x);
		color[x] ^= 1;
		up(x);
	}

	public static int query(int x) {
		access(x);
		splay(x);
		// access(x)之后，x是当前辅助Splay中深度最深的节点
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
				// 初始不存在实链，每个孩子都是u的直接虚儿子
				// 从u经过v到最近白点的距离为lm[v] + 1
				insert(u, lm[v] + 1);
			}
		}
		up(u);
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		lm[0] = rm[0] = INF;
		for (int i = 1; i < n; i++) {
			int u = in.nextInt();
			int v = in.nextInt();
			addEdge(u, v);
			addEdge(v, u);
		}
		dfs(1, 0);
		q = in.nextInt();
		for (int i = 1, op, x; i <= q; i++) {
			op = in.nextInt();
			x = in.nextInt();
			if (op == 0) {
				changeColor(x);
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