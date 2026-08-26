package class205;

// 平面最近点对，java版
// 课上讲述KDT的方法，本题正解是平面最近点对的分治算法，计算几何专题会讲述正解
// 一共n个点，每个点给定坐标(x, y)，输出距离最近的两个点的距离平方
// 2 <= n <= 4 * 10^5
// -10^7 <= 坐标值 <= +10^7
// 测试链接 : https://www.luogu.com.cn/problem/P7883
// 提交以下的code，提交时请把类名改成"Main"，因为不是正解，java实现无法通过
// 想通过用C++实现，本节课Code01_ClosestPair2文件就是C++的实现
// 两个版本的逻辑完全一样，C++版本可以通过所有测试

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code01_ClosestPair1 {

	public static int MAXN = 400001;
	public static long INF = 1L << 60;
	public static int n;

	public static long[][] arr = new long[MAXN][2];

	public static int[] ls = new int[MAXN];
	public static int[] rs = new int[MAXN];

	public static long[] xmin = new long[MAXN];
	public static long[] xmax = new long[MAXN];
	public static long[] ymin = new long[MAXN];
	public static long[] ymax = new long[MAXN];

	public static long ans;

	public static void swap(int i, int j) {
		long[] tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}

	public static int first, last;

	public static void partition(int l, int r, long pivot, int dimension) {
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

	// 随机选择算法，无序数组中找到第k小的数，时间复杂度O(n)，讲解024讲述了
	public static void randSelect(int l, int r, int i, int dimension) {
		while (l <= r) {
			long pivot = arr[l + (int) (Math.random() * (r - l + 1))][dimension];
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

	// 交替选维度
	public static int build1(int l, int r, int dimension) {
		if (l > r) {
			return 0;
		}
		int mid = (l + r) >> 1;
		if (l == r) {
			ls[mid] = 0;
			rs[mid] = 0;
		} else {
			randSelect(l, r, mid, dimension);
			ls[mid] = build1(l, mid - 1, dimension ^ 1);
			rs[mid] = build1(mid + 1, r, dimension ^ 1);
		}
		maintain(mid);
		return mid;
	}

	// 返回arr[l..r][dimension]的方差
	public static double variance(int l, int r, int dimension) {
		double siz = r - l + 1, sum = 0, avg = 0, dif = 0;
		for (int i = l; i <= r; i++) {
			sum += arr[i][dimension];
		}
		avg = sum / siz;
		sum = 0;
		for (int i = l; i <= r; i++) {
			dif = arr[i][dimension] - avg;
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
		if (l == r) {
			ls[mid] = 0;
			rs[mid] = 0;
		} else {
			int dimension = variance(l, r, 0) >= variance(l, r, 1) ? 0 : 1;
			randSelect(l, r, mid, dimension);
			ls[mid] = build2(l, mid - 1);
			rs[mid] = build2(mid + 1, r);
		}
		maintain(mid);
		return mid;
	}

	public static long dist(int a, int b) {
		long dx = arr[a][0] - arr[b][0];
		long dy = arr[a][1] - arr[b][1];
		return dx * dx + dy * dy;
	}

	// 估计函数，点i到rt所有点的最小距离的平方，返回估计值
	public static long guess(int i, int rt) {
		if (rt == 0) {
			return INF;
		}
		long x = arr[i][0];
		long y = arr[i][1];
		long dx = x < xmin[rt] ? (xmin[rt] - x) : (x > xmax[rt] ? (x - xmax[rt]) : 0);
		long dy = y < ymin[rt] ? (ymin[rt] - y) : (y > ymax[rt] ? (y - ymax[rt]) : 0);
		return dx * dx + dy * dy;
	}

	public static void updateAns(int i, int l, int r) {
		if (l > r) {
			return;
		}
		int mid = (l + r) >> 1;
		// 不能算自己到自己的距离
		if (mid != i) {
			ans = Math.min(ans, dist(i, mid));
		}
		if (l < r) {
			long gl = guess(i, ls[mid]);
			long gr = guess(i, rs[mid]);
			if (gl < gr) {
				if (gl < ans) {
					updateAns(i, l, mid - 1);
				}
				if (gr < ans) {
					updateAns(i, mid + 1, r);
				}
			} else {
				if (gr < ans) {
					updateAns(i, mid + 1, r);
				}
				if (gl < ans) {
					updateAns(i, l, mid - 1);
				}
			}
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		for (int i = 1; i <= n; i++) {
			arr[i][0] = in.nextLong();
			arr[i][1] = in.nextLong();
		}
		xmin[0] = ymin[0] = INF;
		xmax[0] = ymax[0] = -INF;
		// build1(1, n, 0);
		build2(1, n);
		ans = dist(1, 2);
		for (int i = 1; i <= n; i++) {
			updateAns(i, 1, n);
			if (ans == 0) {
				break;
			}
		}
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