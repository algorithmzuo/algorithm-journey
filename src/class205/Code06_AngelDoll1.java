package class205;

// 天使玩偶，java版
// 本题就是讲解170，题目6，讲了CDQ分治的解法，这里用kdt的解法
// 规定(x1, y1)和(x2, y2)之间的距离 = | x1 - x2 | + | y1 - y2 |
// 一开始先给定n个点的位置，接下来有m条操作，每种操作是如下两种类型中的一种
// 操作 1 x y : 在(x, y)位置添加一个点
// 操作 2 x y : 打印已经添加的所有点中，到(x, y)位置最短距离的点是多远
// 1 <= n、m <= 3 * 10^5
// 0 <= x、y <= 10^6
// 测试链接 : https://www.luogu.com.cn/problem/P4169
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code06_AngelDoll1 {

	public static int MAXN = 600001;
	public static int MAXP = 20;
	public static int INF = 1 << 30;
	public static int n, m, cntn;

	public static int[][] arr = new int[MAXN][2];

	public static int[] ls = new int[MAXN];
	public static int[] rs = new int[MAXN];
	public static int[] xmin = new int[MAXN];
	public static int[] xmax = new int[MAXN];
	public static int[] ymin = new int[MAXN];
	public static int[] ymax = new int[MAXN];

	public static int[] root = new int[MAXP];

	public static void swap(int i, int j) {
		int[] tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}

	public static int first, last;

	public static void partition(int l, int r, int pivot, int dimension) {
		first = l;
		last = r;
		int i = l;
		while (i <= last) {
			if (arr[i][dimension] == pivot) {
				i++;
			} else if (arr[i][dimension] < pivot) {
				swap(first++, i++);
			} else {
				swap(i, last--);
			}
		}
	}

	public static void randSelect(int l, int r, int i, int dimension) {
		while (l <= r) {
			int pivot = arr[l + (int) (Math.random() * (r - l + 1))][dimension];
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
		xmin[i] = Math.min(arr[i][0], Math.min(xmin[ls[i]], xmin[rs[i]]));
		xmax[i] = Math.max(arr[i][0], Math.max(xmax[ls[i]], xmax[rs[i]]));
		ymin[i] = Math.min(arr[i][1], Math.min(ymin[ls[i]], ymin[rs[i]]));
		ymax[i] = Math.max(arr[i][1], Math.max(ymax[ls[i]], ymax[rs[i]]));
	}

	public static int build(int l, int r, int dimension) {
		if (l > r) {
			return 0;
		}
		int mid = (l + r) >> 1;
		if (l == r) {
			ls[mid] = 0;
			rs[mid] = 0;
		} else {
			randSelect(l, r, mid, dimension);
			ls[mid] = build(l, mid - 1, dimension ^ 1);
			rs[mid] = build(mid + 1, r, dimension ^ 1);
		}
		maintain(mid);
		return mid;
	}

	public static void add(int x, int y) {
		cntn++;
		arr[cntn][0] = x;
		arr[cntn][1] = y;
		int p = 0;
		while (root[p] != 0) {
			root[p++] = 0;
		}
		root[p] = build(cntn - (1 << p) + 1, cntn, 0);
	}

	// 估计点(x,y)到i的子树中所有点的最小曼哈顿距离
	public static int guess(int x, int y, int i) {
		if (i == 0) {
			return INF;
		}
		int ans = 0;
		if (x < xmin[i]) {
			ans += xmin[i] - x;
		} else if (x > xmax[i]) {
			ans += x - xmax[i];
		}
		if (y < ymin[i]) {
			ans += ymin[i] - y;
		} else if (y > ymax[i]) {
			ans += y - ymax[i];
		}
		return ans;
	}

	public static int queryAns;

	public static void updateAns(int x, int y, int i) {
		if (i == 0) {
			return;
		}
		// 点(x, y)到单点的曼哈顿距离
		queryAns = Math.min(queryAns, Math.abs(x - arr[i][0]) + Math.abs(y - arr[i][1]));
		int gl = guess(x, y, ls[i]);
		int gr = guess(x, y, rs[i]);
		if (gl < gr) {
			if (gl < queryAns) {
				updateAns(x, y, ls[i]);
			}
			if (gr < queryAns) {
				updateAns(x, y, rs[i]);
			}
		} else {
			if (gr < queryAns) {
				updateAns(x, y, rs[i]);
			}
			if (gl < queryAns) {
				updateAns(x, y, ls[i]);
			}
		}
	}

	public static int query(int x, int y) {
		queryAns = INF;
		for (int p = 0; p < MAXP; p++) {
			if (root[p] != 0) {
				updateAns(x, y, root[p]);
			}
		}
		return queryAns;
	}

	// 初始n个点，不再逐个调用add，直接按照二进制拆分建树
	public static void prepare() {
		for (int p = 0, siz = 1 << p, rest = n; p < MAXP; p++, siz <<= 1) {
			if ((n & (1 << p)) != 0) {
				root[p] = build(rest - siz + 1, rest, 0);
				rest -= siz;
			}
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		cntn = n;
		xmin[0] = ymin[0] = INF;
		xmax[0] = ymax[0] = -INF;
		for (int i = 1; i <= n; i++) {
			arr[i][0] = in.nextInt();
			arr[i][1] = in.nextInt();
		}
		prepare();
		for (int i = 1, op, x, y; i <= m; i++) {
			op = in.nextInt();
			x = in.nextInt();
			y = in.nextInt();
			if (op == 1) {
				add(x, y);
			} else {
				out.println(query(x, y));
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
