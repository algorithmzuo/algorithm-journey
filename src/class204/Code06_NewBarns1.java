package class204;

// 查询最远距离，java版
// 初始没有节点，接下来有q条操作，操作类型如下
// 操作 B p : 新建一个节点，并与节点p连接，如果p为-1则新建一个独立节点
// 操作 Q x : 打印节点x到所在连通块中最远节点的距离
// 1 <= q <= 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P4271
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code06_NewBarns1 {

	public static int MAXN = 100001;
	public static int q, cntn;

	public static int[] fa = new int[MAXN];
	public static int[] ls = new int[MAXN];
	public static int[] rs = new int[MAXN];
	public static boolean[] rev = new boolean[MAXN];
	public static int[] sta = new int[MAXN];

	// siz[x]表示以x为根的辅助Splay节点总量
	public static int[] siz = new int[MAXN];

	// 并查集维护每个连通块
	public static int[] father = new int[MAXN];

	// diameter[root]表示连通块的直径长度
	public static int[] diameter = new int[MAXN];

	// lnode[root]和rnode[root]表示连通块直径的两个端点
	public static int[] lnode = new int[MAXN];
	public static int[] rnode = new int[MAXN];

	public static int find(int x) {
		if (father[x] != x) {
			father[x] = find(father[x]);
		}
		return father[x];
	}

	public static void up(int x) {
		siz[x] = siz[ls[x]] + siz[rs[x]] + 1;
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
		int size = 0;
		sta[++size] = x;
		for (int y = x; !isroot(y); y = fa[y]) {
			sta[++size] = fa[y];
		}
		while (size != 0) {
			down(sta[size--]);
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
		up(x);
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

	// 节点数量-1就是边的数量
	public static int getDist(int x, int y) {
		split(x, y);
		return siz[y] - 1;
	}

	public static void build(int p) {
		// 初始时，新节点是独立的连通块
		int x = ++cntn;
		siz[x] = 1;
		father[x] = x;
		lnode[x] = x;
		rnode[x] = x;
		diameter[x] = 0;
		if (p != -1) {
			// 得到原连通块的直径端点和直径长度
			int root = find(p);
			int a = lnode[root];
			int b = rnode[root];
			int best = diameter[root];
			int bestl = a;
			int bestr = b;
			link(x, p);
			// 新的直径可能就是老的直径
			// 或者新节点x与旧直径的某个端点组成的路径
			int dista = getDist(x, a);
			if (dista > best) {
				best = dista;
				bestl = x;
				bestr = a;
			}
			int distb = getDist(x, b);
			if (distb > best) {
				best = distb;
				bestl = x;
				bestr = b;
			}
			father[x] = root;
			lnode[root] = bestl;
			rnode[root] = bestr;
			diameter[root] = best;
		}
	}

	// 某点在树中的最远点，一定是直径的两个端点的其中之一
	public static int query(int x) {
		int fx = find(x);
		return Math.max(getDist(x, lnode[fx]), getDist(x, rnode[fx]));
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		q = in.nextInt();
		String op;
		int x;
		for (int i = 1; i <= q; i++) {
			op = in.nextString();
			x = in.nextInt();
			if (op.equals("B")) {
				build(x);
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
				val = val * 10 + (c - '0');
				c = readByte();
			}
			return neg ? -val : val;
		}

		String nextString() throws IOException {
			int c;
			do {
				c = readByte();
			} while (c <= ' ' && c != -1);
			if (c == -1) {
				return null;
			}
			StringBuilder sb = new StringBuilder();
			while (c > ' ' && c != -1) {
				sb.append((char) c);
				c = readByte();
			}
			return sb.toString();
		}

	}

}