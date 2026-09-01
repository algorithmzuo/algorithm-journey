package class205;

// 简单题，二进制分组的方式，java版
// 有一个n * n的平面区域，初始时没有点，有若干条操作，类型如下
// 操作 1 a b c   : 平面里增加一个点，坐标(a, b)，点权为c
// 操作 2 a b c d : 查询(a, b)为左下角、(c, d)为右上角的区域中，所有点的点权和
// 操作 3         : 终止，以后没有操作了
// 本题要求强制在线，得到操作参数的规则，打开测试链接查看
// 1 <= n <= 5 * 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P4148
// 提交以下的code，提交时请把类名改成"Main"，本题卡空间，java实现无法通过
// 想通过用C++实现，本节课Code03_SimpleProblem4文件就是C++的实现
// 两个版本的逻辑完全一样，C++版本可以通过所有测试

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code03_SimpleProblem3 {

	public static int MAXN = 200001;
	public static int MAXP = 19;
	public static int INF = 1 << 30;
	public static int n;

	public static int[] x = new int[MAXN];
	public static int[] y = new int[MAXN];
	public static int[] v = new int[MAXN];
	public static int[] arr = new int[MAXN];

	// K-D树的节点计数
	public static int cntkdt;

	// root[p]表示大小为2的p次方的K-D树，根节点编号
	public static int[] root = new int[MAXP];

	public static int[] ls = new int[MAXN];
	public static int[] rs = new int[MAXN];
	public static int[] sum = new int[MAXN];
	public static int[] xmin = new int[MAXN];
	public static int[] xmax = new int[MAXN];
	public static int[] ymin = new int[MAXN];
	public static int[] ymax = new int[MAXN];

	public static void maintain(int i) {
		sum[i] = v[i] + sum[ls[i]] + sum[rs[i]];
		xmin[i] = Math.min(x[i], Math.min(xmin[ls[i]], xmin[rs[i]]));
		xmax[i] = Math.max(x[i], Math.max(xmax[ls[i]], xmax[rs[i]]));
		ymin[i] = Math.min(y[i], Math.min(ymin[ls[i]], ymin[rs[i]]));
		ymax[i] = Math.max(y[i], Math.max(ymax[ls[i]], ymax[rs[i]]));
	}

	public static int compareNode(int i, int j, int dimension) {
		int a = dimension == 0 ? x[i] : y[i];
		int b = dimension == 0 ? x[j] : y[j];
		return a != b ? (a - b) : (i - j);
	}

	public static void swap(int i, int j) {
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}

	public static int first, last;

	public static void partition(int l, int r, int pidx, int dimension) {
		first = l;
		last = r;
		int i = l;
		while (i <= last) {
			int cmp = compareNode(arr[i], pidx, dimension);
			if (cmp == 0) {
				i++;
			} else if (cmp < 0) {
				swap(first++, i++);
			} else {
				swap(i, last--);
			}
		}
	}

	public static void randSelect(int l, int r, int i, int dimension) {
		while (l <= r) {
			int pidx = arr[l + (int) (Math.random() * (r - l + 1))];
			partition(l, r, pidx, dimension);
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
		int rt = arr[mid];
		ls[rt] = build(l, mid - 1, dimension ^ 1);
		rs[rt] = build(mid + 1, r, dimension ^ 1);
		maintain(rt);
		return rt;
	}

	public static void add(int qx, int qy, int qv) {
		cntkdt++;
		x[cntkdt] = qx;
		y[cntkdt] = qy;
		v[cntkdt] = qv;
		arr[cntkdt] = cntkdt;
		int p = 0;
		while (root[p] != 0) {
			root[p++] = 0;
		}
		root[p] = build(cntkdt - (1 << p) + 1, cntkdt, 0);
	}

	public static int query(int x1, int y1, int x2, int y2, int i) {
		if (i == 0) {
			return 0;
		}
		if (xmax[i] < x1 || x2 < xmin[i] || ymax[i] < y1 || y2 < ymin[i]) {
			return 0;
		}
		if (x1 <= xmin[i] && xmax[i] <= x2 && y1 <= ymin[i] && ymax[i] <= y2) {
			return sum[i];
		}
		int ans = 0;
		if (x1 <= x[i] && x[i] <= x2 && y1 <= y[i] && y[i] <= y2) {
			ans += v[i];
		}
		ans += query(x1, y1, x2, y2, ls[i]);
		ans += query(x1, y1, x2, y2, rs[i]);
		return ans;
	}

	public static int query(int x1, int y1, int x2, int y2) {
		int ans = 0;
		for (int p = 0; p < MAXP; p++) {
			ans += query(x1, y1, x2, y2, root[p]);
		}
		return ans;
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		// 输入的n没用
		n = in.nextInt();
		xmin[0] = ymin[0] = INF;
		xmax[0] = ymax[0] = -INF;
		int op, a, b, c, d, lastAns;
		op = in.nextInt();
		lastAns = 0;
		while (op != 3) {
			a = in.nextInt();
			b = in.nextInt();
			c = in.nextInt();
			a ^= lastAns;
			b ^= lastAns;
			c ^= lastAns;
			if (op == 1) {
				add(a, b, c);
			} else {
				d = in.nextInt();
				d ^= lastAns;
				lastAns = query(a, b, c, d);
				out.println(lastAns);
			}
			op = in.nextInt();
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