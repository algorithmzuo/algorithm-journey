package class202;

// 航线规划，java版
// 测试链接 : https://www.luogu.com.cn/problem/P2542
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;

public class Code05_Flight1 {

	public static int MAXN = 30001;
	public static int MAXM = 100001;
	public static int MAXQ = 40001;
	public static int n, m, q;

	public static int[] ex = new int[MAXM];
	public static int[] ey = new int[MAXM];

	public static int[] qop = new int[MAXQ];
	public static int[] qx = new int[MAXQ];
	public static int[] qy = new int[MAXQ];

	public static int[] queryAns = new int[MAXQ];
	public static int queryCnt;

	public static boolean[] deleted = new boolean[MAXM];
	public static HashMap<Long, Integer> edgeMap = new HashMap<>();

	// 并查集，维护每个点归属的边双连通分量
	public static int[] father = new int[MAXN];

	// 辅助splay
	public static int[] fa = new int[MAXN];
	public static int[] ls = new int[MAXN];
	public static int[] rs = new int[MAXN];
	public static boolean[] rev = new boolean[MAXN];
	public static int[] sta = new int[MAXN];

	// ebccSiz[x]表示以x为根的辅助splay，有多少边双连通分量
	public static int[] ebccSiz = new int[MAXN];

	public static int find(int x) {
		if (x != father[x]) {
			father[x] = find(father[x]);
		}
		return father[x];
	}

	public static void up(int x) {
		ebccSiz[x] = ebccSiz[ls[x]] + ebccSiz[rs[x]] + 1;
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

	// access方法，每次向上跳，要把fa[x]更新为当前边双连通分量的代表节点
	// 一旦缩点，被缩掉节点的fa可能还残留旧的LCT关系，但这些节点已经不是缩点树上的有效代表元
	// 因此沿fa往上跳时，用并查集把fa[x]修正到当前边双连通分量代表元
	// 也就是 fa[x] = find(fa[x]) 这一句要注意
	public static void access(int x) {
		for (int y = 0; x != 0; y = x, x = fa[x]) {
			splay(x);
			rs[x] = y;
			up(x);
			fa[x] = find(fa[x]);
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

	// 以x为根的辅助splay子树，合并成一个边双，迭代的实现方式
	public static void condense(int x, int root) {
		if (x != 0) {
			int size = 0;
			sta[++size] = x;
			while (size != 0) {
				x = sta[size--];
				father[x] = root;
				if (ls[x] != 0) {
					sta[++size] = ls[x];
				}
				if (rs[x] != 0) {
					sta[++size] = rs[x];
				}
			}
		}
	}

	// 倒序加边，如果两点不连通就连接，如果连通，说明形成环，缩成一个边双
	public static void merge(int x, int y) {
		x = find(x);
		y = find(y);
		if (x == y) {
			return;
		}
		makeroot(x);
		if (findroot(y) != x) {
			fa[x] = y;
		} else {
			condense(rs[x], x);
			rs[x] = 0;
			up(x);
		}
	}

	public static long key(int x, int y) {
		int a = Math.min(x, y);
		int b = Math.max(x, y);
		return ((long) a << 32) | b;
	}

	public static void prepare() {
		for (int i = 1; i <= n; i++) {
			father[i] = i;
			ebccSiz[i] = 1;
		}
		for (int i = 1; i <= m; i++) {
			edgeMap.put(key(ex[i], ey[i]), i);
		}
		for (int i = 1; i <= q; i++) {
			if (qop[i] == 0) {
				deleted[edgeMap.get(key(qx[i], qy[i]))] = true;
			} else {
				queryCnt++;
			}
		}
		for (int i = 1; i <= m; i++) {
			if (!deleted[i]) {
				merge(ex[i], ey[i]);
			}
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		for (int i = 1; i <= m; i++) {
			ex[i] = in.nextInt();
			ey[i] = in.nextInt();
		}
		int op = in.nextInt();
		while (op != -1) {
			qop[++q] = op;
			qx[q] = in.nextInt();
			qy[q] = in.nextInt();
			op = in.nextInt();
		}
		prepare();
		for (int i = q, j = queryCnt; i >= 1; i--) {
			int x = find(qx[i]);
			int y = find(qy[i]);
			if (qop[i] == 0) {
				merge(x, y);
			} else {
				if (x == y) {
					queryAns[j--] = 0;
				} else {
					split(x, y);
					queryAns[j--] = ebccSiz[y] - 1;
				}
			}
		}
		for (int i = 1; i <= queryCnt; i++) {
			out.println(queryAns[i]);
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