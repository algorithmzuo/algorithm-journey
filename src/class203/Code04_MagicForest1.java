package class203;

// 魔法森林，java版
// 给定一个无向图，每条边有a、b两种权值，可能存在重边和自环
// 定义路径的代价为，路径上最大的a + 路径上最大的b
// 选择1号点到n号点的路径，打印路径代价最小值，无法到达打印-1
// 2 <= n <= 5 * 10^4
// 0 <= m <= 10^5
// 1 <= a、b <= 5 * 10^4
// 测试链接 : https://www.luogu.com.cn/problem/P2387
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code04_MagicForest1 {

	public static int MAXN = 200001;
	public static int INF = 1000000001;
	public static int n, m;

	public static int[] ex = new int[MAXN];
	public static int[] ey = new int[MAXN];
	public static int[] ea = new int[MAXN];
	public static int[] eb = new int[MAXN];

	public static int[] father = new int[MAXN];
	public static int[] fa = new int[MAXN];
	public static int[] ls = new int[MAXN];
	public static int[] rs = new int[MAXN];
	public static boolean[] rev = new boolean[MAXN];
	public static int[] sta = new int[MAXN];

	// maxbEdge[x]表示以x为根的辅助splay中，最大b权值的边的编号
	public static int[] maxbEdge = new int[MAXN];

	public static int find(int x) {
		if (x != father[x]) {
			father[x] = find(father[x]);
		}
		return father[x];
	}

	// 手撸双指针快排，根据a权值从小到大排序
	public static void sort(int l, int r) {
		if (l >= r) return;
		int i = l, j = r, pivot = ea[(l + r) >> 1], tmp;
		while (i <= j) {
			while (ea[i] < pivot) i++;
			while (ea[j] > pivot) j--;
			if (i <= j) {
				tmp = ex[i]; ex[i] = ex[j]; ex[j] = tmp;
				tmp = ey[i]; ey[i] = ey[j]; ey[j] = tmp;
				tmp = ea[i]; ea[i] = ea[j]; ea[j] = tmp;
				tmp = eb[i]; eb[i] = eb[j]; eb[j] = tmp;
				i++; j--;
			}
		}
		sort(l, j);
		sort(i, r);
	}

	public static void up(int x) {
		maxbEdge[x] = x <= n ? 0 : x - n;
		if (eb[maxbEdge[ls[x]]] > eb[maxbEdge[x]]) {
			maxbEdge[x] = maxbEdge[ls[x]];
		}
		if (eb[maxbEdge[rs[x]]] > eb[maxbEdge[x]]) {
			maxbEdge[x] = maxbEdge[rs[x]];
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

	public static int compute() {
		sort(1, m);
		for (int i = 1; i <= n; i++) {
			father[i] = i;
		}
		int ans = INF;
		for (int i = 1; i <= m; i++) {
			int x = ex[i];
			int y = ey[i];
			if (x != y) {
				up(n + i);
				if (find(x) != find(y)) {
					father[find(x)] = find(y);
					link(x, n + i);
					link(y, n + i);
				} else {
					split(x, y);
					int pre = maxbEdge[y];
					if (eb[i] < eb[pre]) {
						cut(ex[pre], n + pre);
						cut(ey[pre], n + pre);
						link(x, n + i);
						link(y, n + i);
					}
				}
			}
			if (find(1) == find(n)) {
				split(1, n);
				ans = Math.min(ans, ea[i] + eb[maxbEdge[n]]);
			}
		}
		return ans == INF ? -1 : ans;
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		for (int i = 1; i <= m; i++) {
			ex[i] = in.nextInt();
			ey[i] = in.nextInt();
			ea[i] = in.nextInt();
			eb[i] = in.nextInt();
		}
		int ans = compute();
		out.println(ans);
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

	}

}