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

	public static int MAXN = 200001;
	public static int n, q;

	// 原树
	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXN];
	public static int[] to = new int[MAXN];
	public static int cntg;

	// 节点x有黑白两个状态，x是黑状态，x+n是白状态
	// 两套颜色的森林共用一套LCT，彼此独立
	public static int[] fa = new int[MAXN];
	public static int[] ls = new int[MAXN];
	public static int[] rs = new int[MAXN];

	// 原树固定以1为根，每个状态节点都有同一颜色系统中的固定父亲
	// parent[x]表示黑色状态节点x的固定父亲
	// parent[x+n]表示白色状态节点x+n的固定父亲
	public static int[] parent = new int[MAXN];

	// 节点x有黑白两个状态，分别是x和x+n，有效状态只有一个
	public static int[] val = new int[MAXN];

	// vir[x]表示状态节点x的虚子树贡献，点权累加和
	public static int[] vir = new int[MAXN];

	// sum[x]表示以x为根的辅助splay中，汇总所有点权累加和
	// 包括x自己的贡献、x的所有虚子树的贡献、左右儿子的贡献
	public static int[] sum = new int[MAXN];

	public static void addEdge(int u, int v) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		head[u] = cntg;
	}

	public static void up(int x) {
		sum[x] = sum[ls[x]] + sum[rs[x]] + vir[x] + val[x];
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

	// 注意修正虚子树贡献
	public static void access(int x) {
		for (int y = 0; x != 0; y = x, x = fa[x]) {
			splay(x);
			vir[x] += sum[rs[x]];
			vir[x] -= sum[y];
			rs[x] = y;
			up(x);
		}
	}

	public static int findroot(int x) {
		access(x);
		splay(x);
		while (ls[x] != 0) {
			x = ls[x];
		}
		splay(x);
		return x;
	}

	// 原树形态固定，连接父边(x, f)，x是子，f是父
	// 连接后x作为f的虚儿子
	public static void link(int x, int f) {
		if (f == 0) {
			return;
		}
		access(f);
		splay(f);
		splay(x);
		fa[x] = f;
		vir[f] += sum[x];
		up(f);
	}

	// 原树形态固定，删除父边(x, f)，x是子，f是父
	public static void cut(int x, int f) {
		access(x);
		splay(x);
		if (f != 0) {
			int left = ls[x];
			fa[left] = 0;
			ls[x] = 0;
			up(x);
		}
	}

	public static int query(int x) {
		// 得到x的状态xs，顶部节点的状态ys
		int xs = val[x] == 1 ? x : x + n;
		int ys = findroot(xs);
		// 如果顶部节点和x同色，返回整体sum，如果不同色，返回右儿子sum
		return val[ys] == 1 ? sum[ys] : sum[rs[ys]];
	}

	public static void reverseColor(int x) {
		int pre = val[x] == 1 ? x : x + n;
		int cur = pre <= n ? pre + n : pre - n;
		// 老颜色的lct中要断边，新颜色的lct中要连边
		cut(pre, parent[pre]);
		val[pre] = 0;
		val[cur] = 1;
		link(cur, parent[cur]);
	}

	public static void dfs(int u, int f) {
		// 黑白的状态节点，各自记录父亲
		if (f != 0) {
			parent[u] = f;
			parent[u + n] = f + n;
		}
		// 节点初始都是黑色，黑色状态点才有贡献
		val[u] = sum[u] = 1;
		for (int e = head[u]; e != 0; e = nxt[e]) {
			int v = to[e];
			if (v != f) {
				dfs(v, u);
				link(v, u);
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
				reverseColor(x);
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