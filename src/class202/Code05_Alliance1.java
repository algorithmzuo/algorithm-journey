package class202;

// 星球联盟，java版
// 一共有n个点、m条无向边，接下来依次加入p条无向边
// 如果两个点属于同一个联盟，必须存在一条环形线路经过这两个点
// 等价地说，两个点之间存在两条没有公共边的路径，也就是在同一个边双连通分量中
// 每次加入一条新边(x,y)，加入之后判断点x和点y是否属于同一个联盟
// 如果不属于同一个联盟，打印"No"
// 如果属于同一个联盟，打印这个联盟里的节点数
// 1 <= n、m、p <= 2 * 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P10657
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code05_Alliance1 {

	public static int MAXN = 200001;
	public static int n, m, p;

	public static int[] father = new int[MAXN];

	public static int[] fa = new int[MAXN];
	public static int[] ls = new int[MAXN];
	public static int[] rs = new int[MAXN];
	public static boolean[] rev = new boolean[MAXN];
	public static int[] sta = new int[MAXN];

	// nodeCnt[x]表示x代表的边双连通分量中，有多少个原图节点
	public static int[] nodeCnt = new int[MAXN];

	public static int find(int x) {
		if (x != father[x]) {
			father[x] = find(father[x]);
		}
		return father[x];
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
		x = find(x);
		for (int y = 0; x != 0; y = x, x = fa[x]) {
			splay(x);
			rs[x] = y;
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

	public static void condense(int x, int root) {
		if (x != 0) {
			father[x] = root;
			nodeCnt[root] += nodeCnt[x];
			condense(ls[x], root);
			condense(rs[x], root);
		}
	}

	// 如果两点不连通就连接，如果连通，说明形成环，缩成一个边双
	// 如果不连通就返回-1，否则返回边双的节点数量
	public static int link(int x, int y) {
		x = find(x);
		y = find(y);
		if (x == y) {
			return nodeCnt[x];
		}
		makeroot(x);
		if (findroot(y) != x) {
			fa[x] = y;
			return -1;
		} else {
			condense(rs[x], x);
			rs[x] = 0;
			return nodeCnt[x];
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		p = in.nextInt();
		for (int i = 1; i <= n; i++) {
			father[i] = i;
			nodeCnt[i] = 1;
		}
		for (int i = 1, x, y; i <= m; i++) {
			x = in.nextInt();
			y = in.nextInt();
			link(x, y);
		}
		for (int i = 1, x, y; i <= p; i++) {
			x = in.nextInt();
			y = in.nextInt();
			int ans = link(x, y);
			if (ans == -1) {
				out.println("No");
			} else {
				out.println(ans);
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