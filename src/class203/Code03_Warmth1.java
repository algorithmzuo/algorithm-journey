package class203;

// 温暖会指引我们前行，java版
// 测试链接 : https://uoj.ac/problem/274
// 测试链接 : https://www.luogu.com.cn/problem/P6664
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code03_Warmth1 {

	public static int MAXN = 400001;
	public static int INF = 1000000001;
	public static int n, m;

	public static int[] ex = new int[MAXN];
	public static int[] ey = new int[MAXN];
	public static int[] et = new int[MAXN];
	public static int[] el = new int[MAXN];

	// 辅助splay
	public static int[] fa = new int[MAXN];
	public static int[] ls = new int[MAXN];
	public static int[] rs = new int[MAXN];
	public static boolean[] rev = new boolean[MAXN];
	public static int[] sta = new int[MAXN];

	// sumLen[x]表示以x为根的辅助splay中，边长累加和
	public static int[] sum = new int[MAXN];

	// minEdge[x]表示以x为根的辅助splay中，温度最低的边的编号
	public static int[] minEdge = new int[MAXN];

	public static void up(int x) {
		int e = x <= n ? 0 : x - n;
		sum[x] = sum[ls[x]] + sum[rs[x]] + el[e];
		minEdge[x] = e;
		if (et[minEdge[ls[x]]] < et[minEdge[x]]) {
			minEdge[x] = minEdge[ls[x]];
		}
		if (et[minEdge[rs[x]]] < et[minEdge[x]]) {
			minEdge[x] = minEdge[rs[x]];
		}
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

	public static void addEdge(int e) {
		int x = ex[e];
		int y = ey[e];
		up(n + e);
		makeroot(x);
		if (findroot(y) != x) {
			link(x, n + e);
			link(y, n + e);
		} else {
			split(x, y);
			int p = minEdge[y];
			if (et[e] > et[p]) {
				cut(ex[p], n + p);
				cut(ey[p], n + p);
				link(x, n + e);
				link(y, n + e);
			}
		}
	}

	public static void change(int e, int l) {
		splay(n + e);
		el[e] = l;
		up(n + e);
	}

	public static int query(int x, int y) {
		makeroot(x);
		if (findroot(y) != x) {
			return -1;
		}
		split(x, y);
		return sum[y];
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		et[0] = INF;
		String op;
		int e, x, y, t, l;
		for (int i = 1; i <= m; i++) {
			op = in.nextString();
			if (op.equals("find")) {
				e = in.nextInt();
				x = in.nextInt();
				y = in.nextInt();
				t = in.nextInt();
				l = in.nextInt();
				e++;
				x++;
				y++;
				ex[e] = x;
				ey[e] = y;
				et[e] = t;
				el[e] = l;
				addEdge(e);
			} else if (op.equals("move")) {
				x = in.nextInt();
				y = in.nextInt();
				x++;
				y++;
				out.println(query(x, y));
			} else {
				e = in.nextInt();
				l = in.nextInt();
				e++;
				change(e, l);
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
