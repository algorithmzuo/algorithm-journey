package class206;

// 天使玩偶，二进制分组的方式重构，java版
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

public class Code02_AngelDoll1 {

	public static int MAXN = 500001;
	public static int MAXP = 20;
	public static int INF = 1 << 30;
	public static int n, m;

	public static int cntkdt;
	public static int[] root = new int[MAXP];
	public static int[] x = new int[MAXN];
	public static int[] y = new int[MAXN];
	public static int[] ls = new int[MAXN];
	public static int[] rs = new int[MAXN];

	public static int[] xmin = new int[MAXN];
	public static int[] xmax = new int[MAXN];
	public static int[] ymin = new int[MAXN];
	public static int[] ymax = new int[MAXN];

	public static void swap(int i, int j) {
		int tmp = x[i]; x[i] = x[j]; x[j] = tmp;
		tmp = y[i]; y[i] = y[j]; y[j] = tmp;
	}

	public static int first, last;

	public static void partition(int l, int r, int pivot, int dimension) {
		first = l;
		last = r;
		int i = l;
		while (i <= last) {
			int cur = dimension == 0 ? x[i] : y[i];
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
			int idx = l + (int) (Math.random() * (r - l + 1));
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

	public static void maintain(int i) {
		xmin[i] = Math.min(x[i], Math.min(xmin[ls[i]], xmin[rs[i]]));
		xmax[i] = Math.max(x[i], Math.max(xmax[ls[i]], xmax[rs[i]]));
		ymin[i] = Math.min(y[i], Math.min(ymin[ls[i]], ymin[rs[i]]));
		ymax[i] = Math.max(y[i], Math.max(ymax[ls[i]], ymax[rs[i]]));
	}

	public static int build(int l, int r, int dimension) {
		if (l > r) {
			return 0;
		}
		int mid = (l + r) >> 1;
		randSelect(l, r, mid, dimension);
		ls[mid] = build(l, mid - 1, dimension ^ 1);
		rs[mid] = build(mid + 1, r, dimension ^ 1);
		maintain(mid);
		return mid;
	}

	public static void add(int qx, int qy) {
		cntkdt++;
		x[cntkdt] = qx;
		y[cntkdt] = qy;
		int p = 0;
		while (root[p] != 0) {
			root[p++] = 0;
		}
		root[p] = build(cntkdt - (1 << p) + 1, cntkdt, 0);
	}

	// 估计查询点到i子树中的所有点，最小曼哈顿距离
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
		// 查询点(qx, qy)到单点的曼哈顿距离
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
		for (int p = 0; p < MAXP; p++) {
			if (root[p] != 0) {
				updateAns(qx, qy, root[p]);
			}
		}
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
