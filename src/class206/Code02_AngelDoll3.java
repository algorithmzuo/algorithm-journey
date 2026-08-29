package class206;

// 天使玩偶，替罪羊树的方式重构，java版
// 本题就是讲解170，题目6，讲了CDQ分治的解法，这里用kdt的解法
// 规定(x1, y1)和(x2, y2)之间的距离 = | x1 - x2 | + | y1 - y2 |
// 一开始先给定n个点的位置，接下来有m条操作，每种操作是如下两种类型中的一种
// 操作 1 x y : 在(x, y)位置添加一个点
// 操作 2 x y : 打印已经添加的所有点中，距离(x, y)最近的点有多远
// 1 <= n、m <= 3 * 10^5
// 0 <= x、y <= 10^6
// 测试链接 : https://www.luogu.com.cn/problem/P4169
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code02_AngelDoll3 {

	public static int MAXN = 1000001;
	public static int INF = 1 << 30;
	public static int n, m;

	public static int cntkdt;
	public static int root;
	public static int[] x = new int[MAXN];
	public static int[] y = new int[MAXN];
	public static int[] ls = new int[MAXN];
	public static int[] rs = new int[MAXN];
	public static int[] siz = new int[MAXN];
	public static int[] xmin = new int[MAXN];
	public static int[] xmax = new int[MAXN];
	public static int[] ymin = new int[MAXN];
	public static int[] ymax = new int[MAXN];

	public static double ALPHA = 0.7;
	public static int[] collect = new int[MAXN];
	public static int collectSiz;
	public static int top;
	public static int topFather;
	public static int topSide;
	public static int topDimension;

	public static int init(int qx, int qy) {
		cntkdt++;
		x[cntkdt] = qx;
		y[cntkdt] = qy;
		ls[cntkdt] = rs[cntkdt] = 0;
		siz[cntkdt] = 1;
		xmin[cntkdt] = xmax[cntkdt] = qx;
		ymin[cntkdt] = ymax[cntkdt] = qy;
		return cntkdt;
	}

	public static void maintain(int i) {
		siz[i] = 1 + siz[ls[i]] + siz[rs[i]];
		xmin[i] = Math.min(x[i], Math.min(xmin[ls[i]], xmin[rs[i]]));
		xmax[i] = Math.max(x[i], Math.max(xmax[ls[i]], xmax[rs[i]]));
		ymin[i] = Math.min(y[i], Math.min(ymin[ls[i]], ymin[rs[i]]));
		ymax[i] = Math.max(y[i], Math.max(ymax[ls[i]], ymax[rs[i]]));
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
			int cur = dimension == 0 ? x[idx] : y[idx];
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
			int pivot = dimension == 0 ? x[idx] : y[idx];
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
			int insertd = dimension == 0 ? x[insertNode] : y[insertNode];
			int ud = dimension == 0 ? x[u] : y[u];
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

	public static void add(int qx, int qy) {
		top = topFather = topSide = topDimension = 0;
		int insertNode = init(qx, qy);
		add(insertNode, root, 0, 0, 0);
		rebuild();
	}

	public static int guess(int qx, int qy, int i) {
		if (i == 0) {
			return INF;
		}
		int ans = 0;
		if (qx < xmin[i]) {
			ans += xmin[i] - qx;
		} else if (qx > xmax[i]) {
			ans += qx - xmax[i];
		}
		if (qy < ymin[i]) {
			ans += ymin[i] - qy;
		} else if (qy > ymax[i]) {
			ans += qy - ymax[i];
		}
		return ans;
	}

	public static int queryAns;

	public static void updateAns(int qx, int qy, int i) {
		if (i == 0) {
			return;
		}
		queryAns = Math.min(queryAns, Math.abs(qx - x[i]) + Math.abs(qy - y[i]));
		int gl = guess(qx, qy, ls[i]);
		int gr = guess(qx, qy, rs[i]);
		if (gl < gr) {
			if (gl < queryAns) {
				updateAns(qx, qy, ls[i]);
			}
			if (gr < queryAns) {
				updateAns(qx, qy, rs[i]);
			}
		} else {
			if (gr < queryAns) {
				updateAns(qx, qy, rs[i]);
			}
			if (gl < queryAns) {
				updateAns(qx, qy, ls[i]);
			}
		}
	}

	public static int query(int qx, int qy) {
		queryAns = INF;
		updateAns(qx, qy, root);
		return queryAns;
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		xmin[0] = ymin[0] = INF;
		xmax[0] = ymax[0] = -INF;
		for (int i = 1, qx, qy; i <= n; i++) {
			qx = in.nextInt();
			qy = in.nextInt();
			add(qx, qy);
		}
		for (int i = 1, op, qx, qy; i <= m; i++) {
			op = in.nextInt();
			qx = in.nextInt();
			qy = in.nextInt();
			if (op == 1) {
				add(qx, qy);
			} else {
				out.println(query(qx, qy));
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
