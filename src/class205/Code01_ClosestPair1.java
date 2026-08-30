package class205;

// 平面最近点对，java版
// 课上讲述K-D树的方法，但这不是正解，刻意设计测试是可以卡住的
// 本题复杂度正确的正解，是平面最近点对的分治算法，计算几何专题会讲述
// 一共n个点，每个点给定坐标(x, y)，输出最近两个点的距离，保留四位小数
// 2 <= n <= 2 * 10^5
// 0 <= x、y <= 10^9
// 测试链接 : https://www.luogu.com.cn/problem/P1429
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code01_ClosestPair1 {

	public static int MAXN = 200001;
	public static long INF = 1L << 60;
	public static int n;

	public static long[] x = new long[MAXN];
	public static long[] y = new long[MAXN];
	public static int[] arr = new int[MAXN];

	public static int root;
	public static int[] ls = new int[MAXN];
	public static int[] rs = new int[MAXN];
	public static long[] xmin = new long[MAXN];
	public static long[] xmax = new long[MAXN];
	public static long[] ymin = new long[MAXN];
	public static long[] ymax = new long[MAXN];

	public static long ans;

	public static void swap(int i, int j) {
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}

	public static int first, last;

	public static void partition(int l, int r, long pivot, int dimension) {
		first = l;
		last = r;
		int i = l;
		while (i <= last) {
			int idx = arr[i];
			long cur = dimension == 0 ? x[idx] : y[idx];
			if (cur == pivot) {
				i++;
			} else if (cur < pivot) {
				swap(first++, i++);
			} else {
				swap(i, last--);
			}
		}
	}

	// 讲解024，随机选择算法，无序数组中找到第k小的数，时间复杂度O(n)
	public static void randSelect(int l, int r, int i, int dimension) {
		while (l <= r) {
			int idx = arr[l + (int) (Math.random() * (r - l + 1))];
			long pivot = dimension == 0 ? x[idx] : y[idx];
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

	// 交替选维度
	public static int build1(int l, int r, int dimension) {
		if (l > r) {
			return 0;
		}
		int mid = (l + r) >> 1;
		randSelect(l, r, mid, dimension);
		int rt = arr[mid];
		ls[rt] = build1(l, mid - 1, dimension ^ 1);
		rs[rt] = build1(mid + 1, r, dimension ^ 1);
		maintain(rt);
		return rt;
	}

	// 返回dimension维度数据的方差
	public static double variance(int l, int r, int dimension) {
		double siz = r - l + 1, sum = 0, avg = 0, dif = 0;
		for (int i = l; i <= r; i++) {
			sum += dimension == 0 ? x[arr[i]] : y[arr[i]];
		}
		avg = sum / siz;
		sum = 0;
		for (int i = l; i <= r; i++) {
			dif = (dimension == 0 ? x[arr[i]] : y[arr[i]]) - avg;
			sum += dif * dif;
		}
		return sum / siz;
	}

	// 方差选维度
	public static int build2(int l, int r) {
		if (l > r) {
			return 0;
		}
		int mid = (l + r) >> 1;
		int dimension = variance(l, r, 0) >= variance(l, r, 1) ? 0 : 1;
		randSelect(l, r, mid, dimension);
		int rt = arr[mid];
		ls[rt] = build2(l, mid - 1);
		rs[rt] = build2(mid + 1, r);
		maintain(rt);
		return rt;
	}

	public static long dist(int qi, int i) {
		long dx = x[qi] - x[i];
		long dy = y[qi] - y[i];
		return dx * dx + dy * dy;
	}

	// 估计函数，估计查询点qi到i子树中所有点的最小距离平方
	public static long guess(int qi, int i) {
		if (i == 0) {
			return INF;
		}
		long qx = x[qi];
		long qy = y[qi];
		long dx = qx < xmin[i] ? (xmin[i] - qx) : (qx > xmax[i] ? (qx - xmax[i]) : 0);
		long dy = qy < ymin[i] ? (ymin[i] - qy) : (qy > ymax[i] ? (qy - ymax[i]) : 0);
		return dx * dx + dy * dy;
	}

	public static void updateAns(int qi, int i) {
		if (i == 0) {
			return;
		}
		// 不能算自己到自己的距离
		if (qi != i) {
			ans = Math.min(ans, dist(qi, i));
		}
		long gl = guess(qi, ls[i]);
		long gr = guess(qi, rs[i]);
		if (gl < gr) {
			if (gl < ans) {
				updateAns(qi, ls[i]);
			}
			if (gr < ans) {
				updateAns(qi, rs[i]);
			}
		} else {
			if (gr < ans) {
				updateAns(qi, rs[i]);
			}
			if (gl < ans) {
				updateAns(qi, ls[i]);
			}
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		for (int i = 1; i <= n; i++) {
			x[i] = in.nextLong();
			y[i] = in.nextLong();
			arr[i] = i;
		}
		xmin[0] = ymin[0] = INF;
		xmax[0] = ymax[0] = -INF;
		root = build1(1, n, 0);
		// root = build2(1, n);
		ans = dist(1, 2);
		for (int i = 1; i <= n; i++) {
			updateAns(i, root);
			if (ans == 0) {
				break;
			}
		}
		out.printf("%.4f\n", Math.sqrt(ans));
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

		long nextLong() throws IOException {
			int c;
			do {
				c = readByte();
			} while (c <= ' ' && c != -1);
			boolean neg = false;
			if (c == '-') {
				neg = true;
				c = readByte();
			}
			long val = 0;
			while (c > ' ' && c != -1) {
				val = val * 10 + (c - '0');
				c = readByte();
			}
			return neg ? -val : val;
		}

	}

}