package class206;

// 三维偏序，替罪羊树的方式重构，java版
// 本题就是讲解170，题目1，讲了CDQ分治的解法，这里用kdt的解法
// 一共有n个对象，每个对象有a、b、c三个属性，每个属性值的范围都是[1, k]
// f(i)表示，aj <= ai 且 bj <= bi 且 cj <= ci 且 j != i 的j的数量
// ans(d)表示，f(i) == d 的i的数量
// 打印所有的ans[d]，d的范围[0, n)
// 1 <= n <= 10^5
// 1 <= k <= 2 * 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P3810
// 提交以下的code，提交时请把类名改成"Main"，因为不是正解，java实现无法通过
// 想通过用C++实现，本节课Code03_3DPartialOrder4文件就是C++的实现
// 两个版本的逻辑完全一样，C++版本可以通过所有测试

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

public class Code03_3DPartialOrder3 {

	public static int MAXN = 100001;
	public static int INF = 1 << 30;
	public static int n, k;
	public static int[][] abc = new int[MAXN][3];

	public static int cntkdt;
	public static int root;
	public static int[] b = new int[MAXN];
	public static int[] c = new int[MAXN];
	public static int[] siz = new int[MAXN];
	public static int[] ls = new int[MAXN];
	public static int[] rs = new int[MAXN];
	public static int[] bmin = new int[MAXN];
	public static int[] bmax = new int[MAXN];
	public static int[] cmin = new int[MAXN];
	public static int[] cmax = new int[MAXN];

	public static double ALPHA = 0.7;
	public static int[] collect = new int[MAXN];
	public static int collectSiz;
	public static int top;
	public static int topFather;
	public static int topSide;
	public static int topDimension;

	public static int[] ans = new int[MAXN];

	public static int init(int qb, int qc) {
		cntkdt++;
		b[cntkdt] = qb;
		c[cntkdt] = qc;
		ls[cntkdt] = rs[cntkdt] = 0;
		siz[cntkdt] = 1;
		bmin[cntkdt] = bmax[cntkdt] = qb;
		cmin[cntkdt] = cmax[cntkdt] = qc;
		return cntkdt;
	}

	public static void maintain(int i) {
		siz[i] = siz[ls[i]] + siz[rs[i]] + 1;
		bmin[i] = Math.min(b[i], Math.min(bmin[ls[i]], bmin[rs[i]]));
		bmax[i] = Math.max(b[i], Math.max(bmax[ls[i]], bmax[rs[i]]));
		cmin[i] = Math.min(c[i], Math.min(cmin[ls[i]], cmin[rs[i]]));
		cmax[i] = Math.max(c[i], Math.max(cmax[ls[i]], cmax[rs[i]]));
	}

	public static void swap(int i, int j) {
		int tmp = collect[i];
		collect[i] = collect[j];
		collect[j] = tmp;
	}

	public static int first, last;

	public static void partition(int l, int r, int pivot, int dimension) {
		first = l;
		last = r;
		int i = l;
		while (i <= last) {
			int idx = collect[i];
			int cur = dimension == 0 ? b[idx] : c[idx];
			if (cur == pivot) {
				i++;
			} else if (cur < pivot) {
				swap(first++, i++);
			} else {
				swap(i, last--);
			}
		}
	}

	public static void randSelect(int l, int r, int i, int dimension) {
		while (l <= r) {
			int idx = collect[l + (int) (Math.random() * (r - l + 1))];
			int pivot = dimension == 0 ? b[idx] : c[idx];
			partition(l, r, pivot, dimension);
			if (i < first) {
				r = first - 1;
			} else if (i > last) {
				l = last + 1;
			} else {
				break;
			}
		}
	}

	public static int build(int l, int r, int dimension) {
		if (l > r) {
			return 0;
		}
		int mid = (l + r) >> 1;
		randSelect(l, r, mid, dimension);
		int rt = collect[mid];
		ls[rt] = build(l, mid - 1, dimension ^ 1);
		rs[rt] = build(mid + 1, r, dimension ^ 1);
		maintain(rt);
		return rt;
	}

	public static boolean balance(int i) {
		return ALPHA * siz[i] >= Math.max(siz[ls[i]], siz[rs[i]]);
	}

	public static void dfs(int i) {
		if (i != 0) {
			collect[++collectSiz] = i;
			dfs(ls[i]);
			dfs(rs[i]);
		}
	}

	public static void rebuild() {
		if (top != 0) {
			collectSiz = 0;
			dfs(top);
			int rt = build(1, collectSiz, topDimension);
			if (topFather == 0) {
				root = rt;
			} else if (topSide == 1) {
				ls[topFather] = rt;
			} else {
				rs[topFather] = rt;
			}
		}
	}

	public static void add(int insertNode, int u, int fa, int side, int dimension) {
		if (u == 0) {
			if (fa == 0) {
				root = insertNode;
			} else if (side == 1) {
				ls[fa] = insertNode;
			} else {
				rs[fa] = insertNode;
			}
		} else {
			int insertd = dimension == 0 ? b[insertNode] : c[insertNode];
			int ud = dimension == 0 ? b[u] : c[u];
			if (insertd <= ud) {
				add(insertNode, ls[u], u, 1, dimension ^ 1);
			} else {
				add(insertNode, rs[u], u, 2, dimension ^ 1);
			}
			maintain(u);
			if (!balance(u)) {
				top = u;
				topFather = fa;
				topSide = side;
				topDimension = dimension;
			}
		}
	}

	public static void add(int qb, int qc) {
		top = topFather = topSide = topDimension = 0;
		int insertNode = init(qb, qc);
		add(insertNode, root, 0, 0, 0);
		rebuild();
	}

	public static int query(int qb, int qc, int i) {
		if (i == 0) {
			return 0;
		}
		if (bmin[i] > qb || cmin[i] > qc) {
			return 0;
		}
		if (bmax[i] <= qb && cmax[i] <= qc) {
			return siz[i];
		}
		int ans = 0;
		if (b[i] <= qb && c[i] <= qc) {
			ans++;
		}
		ans += query(qb, qc, ls[i]);
		ans += query(qb, qc, rs[i]);
		return ans;
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		k = in.nextInt();
		for (int i = 1; i <= n; i++) {
			abc[i][0] = in.nextInt();
			abc[i][1] = in.nextInt();
			abc[i][2] = in.nextInt();
		}
		Arrays.sort(abc, 1, n + 1, (x, y) -> x[0] - y[0]);
		bmin[0] = cmin[0] = INF;
		bmax[0] = cmax[0] = -INF;
		for (int l = 1, r = 1; l <= n; l = ++r) {
			while (r + 1 <= n && abc[r + 1][0] == abc[l][0]) {
				r++;
			}
			for (int i = l; i <= r; i++) {
				add(abc[i][1], abc[i][2]);
			}
			for (int i = l; i <= r; i++) {
				int cur = query(abc[i][1], abc[i][2], root);
				ans[cur - 1]++;
			}
		}
		for (int d = 0; d < n; d++) {
			out.println(ans[d]);
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