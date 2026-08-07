package class204;

// 须臾幻境，java版
// 无向图中有n个点、m条无向边，边的编号是1~m
// 每条询问的格式为 l r，表示保留编号[l, r]的边，打印有多少个连通块
// 本题要求强制在线，得到操作参数的规则，打开测试链接查看
// 1 <= n <= 10^5
// 1 <= m <= 2 * 10^5
// 1 <= q <= 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P5385
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code08_Fairyland1 {

	public static int MAXN = 300001;
	public static int MAXT = 10000001;
	public static int INF = 1000000001;
	public static int n, m, q, t;

	public static int[] eu = new int[MAXN];
	public static int[] ev = new int[MAXN];

	public static int[] fa = new int[MAXN];
	public static int[] ls = new int[MAXN];
	public static int[] rs = new int[MAXN];
	public static boolean[] rev = new boolean[MAXN];
	public static int[] sta = new int[MAXN];

	// 原图的点，点权为INF，原图第i号边，变成LCT的第n+i号点，点权为i
	public static int[] val = new int[MAXN];

	// minv[x]表示以x为根的辅助Splay汇总的最小点权
	public static int[] minv = new int[MAXN];

	// 可持久化线段树，记录每个生成的森林中，每条边是否存在
	public static int[] root = new int[MAXN];
	public static int[] tl = new int[MAXT];
	public static int[] tr = new int[MAXT];
	public static int[] num = new int[MAXT];
	public static int cntt;

	public static void up(int x) {
		minv[x] = Math.min(val[x], Math.min(minv[ls[x]], minv[rs[x]]));
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

	public static void cut(int x, int y) {
		makeroot(x);
		if (findroot(y) == x && fa[y] == x && ls[y] == 0 && rs[x] == y) {
			fa[y] = rs[x] = 0;
			up(x);
		}
	}

	// 查询x到y路径上编号最小的边
	public static int pathMin(int x, int y) {
		split(x, y);
		return minv[y];
	}

	public static int add(int jobi, int jobv, int l, int r, int i) {
		int rt = ++cntt;
		tl[rt] = tl[i];
		tr[rt] = tr[i];
		num[rt] = num[i];
		if (l == r) {
			num[rt] += jobv;
		} else {
			int mid = (l + r) >> 1;
			if (jobi <= mid) {
				tl[rt] = add(jobi, jobv, l, mid, tl[i]);
			} else {
				tr[rt] = add(jobi, jobv, mid + 1, r, tr[i]);
			}
			num[rt] = num[tl[rt]] + num[tr[rt]];
		}
		return rt;
	}

	public static int query(int jobl, int jobr, int l, int r, int i) {
		if (jobl <= l && r <= jobr) {
			return num[i];
		}
		int ans = 0;
		int mid = (l + r) >> 1;
		if (jobl <= mid) {
			ans += query(jobl, jobr, l, mid, tl[i]);
		}
		if (jobr > mid) {
			ans += query(jobl, jobr, mid + 1, r, tr[i]);
		}
		return ans;
	}

	public static void prepare() {
		for (int i = 0; i <= n; i++) {
			val[i] = minv[i] = INF;
		}
		for (int i = 1; i <= m; i++) {
			val[n + i] = minv[n + i] = i;
		}
		for (int i = 1; i <= m; i++) {
			int x = eu[i];
			int y = ev[i];
			if (x == y) {
				root[i] = root[i - 1];
			} else {
				root[i] = add(i, 1, 1, m, root[i - 1]);
				if (findroot(x) == findroot(y)) {
					int e = pathMin(x, y);
					cut(eu[e], n + e);
					cut(ev[e], n + e);
					root[i] = add(e, -1, 1, m, root[i]);
				}
				link(x, n + i);
				link(y, n + i);
			}
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		q = in.nextInt();
		t = in.nextInt();
		for (int i = 1; i <= m; i++) {
			eu[i] = in.nextInt();
			ev[i] = in.nextInt();
		}
		prepare();
		for (int i = 1, lastAns = 0, l, r; i <= q; i++) {
			l = in.nextInt();
			r = in.nextInt();
			if (t > 0) {
				l = (l + t * lastAns) % m + 1;
				r = (r + t * lastAns) % m + 1;
			}
			if (l > r) {
				int tmp = l;
				l = r;
				r = tmp;
			}
			lastAns = n - query(l, r, 1, m, root[r]);
			out.println(lastAns);
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