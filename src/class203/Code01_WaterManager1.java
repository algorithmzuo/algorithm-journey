package class203;

// 水管局长，java版
// 任何时刻图都连通，并且无重边无自环
// 测试链接 : https://www.luogu.com.cn/problem/P4172
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;

public class Code01_WaterManager1 {

	public static int MAXN = 200001;
	public static int n, m, q;

	public static int[] ex = new int[MAXN];
	public static int[] ey = new int[MAXN];
	public static int[] ew = new int[MAXN];

	public static int[] qop = new int[MAXN];
	public static int[] qx = new int[MAXN];
	public static int[] qy = new int[MAXN];

	// 并查集
	public static int[] father = new int[MAXN];

	// 时光倒流加边
	public static boolean[] deleted = new boolean[MAXN];
	public static HashMap<Long, Integer> edgeMap = new HashMap<>();

	// 辅助splay
	public static int[] fa = new int[MAXN];
	public static int[] ls = new int[MAXN];
	public static int[] rs = new int[MAXN];
	public static boolean[] rev = new boolean[MAXN];
	public static int[] sta = new int[MAXN];

	// maxEdge[x]表示以x为根的辅助splay中，权值最大的边的编号
	public static int[] maxEdge = new int[MAXN];

	// 查询的答案
	public static int[] queryAns = new int[MAXN];

	public static int find(int x) {
		if (x != father[x]) {
			father[x] = find(father[x]);
		}
		return father[x];
	}

	public static boolean union(int x, int y) {
		x = find(x);
		y = find(y);
		if (x == y) {
			return false;
		}
		father[x] = y;
		return true;
	}

	// 手撸双指针快排，根据边权从小到大排序
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
		maxEdge[x] = x <= n ? 0 : x - n;
		if (ew[maxEdge[ls[x]]] > ew[maxEdge[x]]) {
			maxEdge[x] = maxEdge[ls[x]];
		}
		if (ew[maxEdge[rs[x]]] > ew[maxEdge[x]]) {
			maxEdge[x] = maxEdge[rs[x]];
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

	public static long key(int x, int y) {
		int a = Math.min(x, y);
		int b = Math.max(x, y);
		return ((long) a << 32) | b;
	}

	public static void prepare() {
		for (int i = 1; i <= n; i++) {
			father[i] = i;
		}
		sort(1, m);
		for (int i = 1; i <= m; i++) {
			edgeMap.put(key(ex[i], ey[i]), i);
		}
		for (int i = 1; i <= q; i++) {
			if (qop[i] == 2) {
				deleted[edgeMap.get(key(qx[i], qy[i]))] = true;
			}
		}
		int edgeCnt = 0;
		for (int i = 1; i <= m && edgeCnt != n - 1; i++) {
			if (!deleted[i] && union(ex[i], ey[i])) {
				link(ex[i], n + i);
				link(ey[i], n + i);
				edgeCnt++;
			}
		}
	}

	// 恢复cur这条边
	public static void restore(int cur) {
		int curx = ex[cur], cury = ey[cur];
		split(curx, cury);
		int pre = maxEdge[cury], prex = ex[pre], prey = ey[pre];
		if (ew[cur] < ew[pre]) {
			cut(prex, n + pre);
			cut(prey, n + pre);
			link(curx, n + cur);
			link(cury, n + cur);
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		q = in.nextInt();
		for (int i = 1; i <= m; i++) {
			ex[i] = in.nextInt();
			ey[i] = in.nextInt();
			ew[i] = in.nextInt();
		}
		for (int i = 1; i <= q; i++) {
			qop[i] = in.nextInt();
			qx[i] = in.nextInt();
			qy[i] = in.nextInt();
		}
		prepare();
		for (int i = q; i >= 1; i--) {
			if (qop[i] == 1) {
				split(qx[i], qy[i]);
				queryAns[i] = ew[maxEdge[qy[i]]];
			} else {
				restore(edgeMap.get(key(qx[i], qy[i])));
			}
		}
		for (int i = 1; i <= q; i++) {
			if (qop[i] == 1) {
				out.println(queryAns[i]);
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

	}

}