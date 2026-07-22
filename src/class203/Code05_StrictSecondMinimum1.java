package class203;

// 严格次小生成树，java版
// 给定一个无向图，图中可能存在自环
// 严格次小生成树的边权和，必须严格大于最小生成树的边权和
// 在所有满足要求的生成树中，打印边权和的最小值
// 1 <= n <= 10^5
// 1 <= m <= 3 * 10^5
// 0 <= 边权 <= 10^9
// 题目保证严格次小生成树一定存在
// 测试链接 : https://www.luogu.com.cn/problem/P4180
// 提交以下的code，提交时请把类名改成"Main"
// 本题有点卡常，java实现多提交几次可以通过，C++实现稳定通过

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code05_StrictSecondMinimum1 {

	public static int MAXN = 400001;
	public static int MAXM = 300001;
	public static int INF = 1000000001;
	public static int n, m;

	public static int[] ex = new int[MAXM];
	public static int[] ey = new int[MAXM];
	public static int[] ew = new int[MAXM];

	public static int[] father = new int[MAXN];

	public static int[] fa = new int[MAXN];
	public static int[] ls = new int[MAXN];
	public static int[] rs = new int[MAXN];
	public static boolean[] rev = new boolean[MAXN];
	public static int[] sta = new int[MAXN];

	// max1[x]表示以x为根的辅助splay中，最大边权的值
	// max2[x]表示以x为根的辅助splay中，严格次大边权
	public static int[] max1 = new int[MAXN];
	public static int[] max2 = new int[MAXN];

	public static int find(int x) {
		if (x != father[x]) {
			father[x] = find(father[x]);
		}
		return father[x];
	}

	public static void sort(int l, int r) {
		if (l >= r) return;
		int i = l, j = r, pivot = ew[(l + r) >> 1], tmp;
		while (i <= j) {
			while (ew[i] < pivot) i++;
			while (ew[j] > pivot) j--;
			if (i <= j) {
				tmp = ex[i]; ex[i] = ex[j]; ex[j] = tmp;
				tmp = ey[i]; ey[i] = ey[j]; ey[j] = tmp;
				tmp = ew[i]; ew[i] = ew[j]; ew[j] = tmp;
				i++; j--;
			}
		}
		sort(l, j);
		sort(i, r);
	}

	public static void up(int x) {
		int l = ls[x], lmax1 = max1[l], lmax2 = max2[l];
		int r = rs[x], rmax1 = max1[r], rmax2 = max2[r];
		if (lmax1 > rmax1) {
			max1[x] = lmax1;
			max2[x] = Math.max(rmax1, lmax2);
		} else if (rmax1 > lmax1) {
			max1[x] = rmax1;
			max2[x] = Math.max(lmax1, rmax2);
		} else {
			max1[x] = lmax1;
			max2[x] = Math.max(lmax2, rmax2);
		}
		int v = x <= n ? -INF : ew[x - n];
		if (v > max1[x]) {
			max2[x] = max1[x];
			max1[x] = v;
		} else if (v < max1[x] && v > max2[x]) {
			max2[x] = v;
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

	public static void split(int x, int y) {
		makeroot(x);
		access(y);
		splay(y);
	}

	// 保证了x和y不连通，所以逻辑化简了
	public static void link(int x, int y) {
		makeroot(x);
		fa[x] = y;
	}

	public static long compute() {
		sort(1, m);
		long sum = 0;
		int minDelta = INF;
		for (int i = 1; i <= m; i++) {
			int x = ex[i];
			int y = ey[i];
			int w = ew[i];
			if (x != y) {
				int fx = find(x);
				int fy = find(y);
				if (fx != fy) {
					father[fx] = fy;
					int e = n + i;
					max1[e] = w;
					max2[e] = -INF;
					link(x, e);
					link(y, e);
					sum += w;
				} else {
					split(x, y);
					int v1 = max1[y];
					int v2 = max2[y];
					if (w > v1) {
						minDelta = Math.min(minDelta, w - v1);
					} else if (w == v1 && v2 != -INF) {
						minDelta = Math.min(minDelta, w - v2);
					}
				}
			}
		}
		return sum + minDelta;
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		for (int i = 1; i <= m; i++) {
			ex[i] = in.nextInt();
			ey[i] = in.nextInt();
			ew[i] = in.nextInt();
		}
		for (int i = 1; i <= n; i++) {
			father[i] = i;
		}
		for (int i = 0; i <= n; i++) {
			max1[i] = max2[i] = -INF;
		}
		out.println(compute());
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