package class205;

// K远点对，java版
// 课上讲述K-D Tree的方法，java实现和C++实现，都有一个测试点超时
// 本题正解是旋转卡壳，计算几何专题时，会讲述正解，这个题会重新讲述
// 测试链接 : https://www.luogu.com.cn/problem/P4357
// 提交以下的code，提交时请把类名改成"Main"，不是正解，无法通过全部测试

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code02_KthFarthestPair1 {

	public static int MAXN = 100001;
	public static int MAXK = 201;
	public static long INF = 1L << 60;
	public static int n, k;

	public static long[][] arr = new long[MAXN][2];

	public static int[] ls = new int[MAXN];
	public static int[] rs = new int[MAXN];

	public static long[] xmin = new long[MAXN];
	public static long[] xmax = new long[MAXN];
	public static long[] ymin = new long[MAXN];
	public static long[] ymax = new long[MAXN];

	public static long[] heap = new long[MAXK];

	public static int first, last;

	public static void swap(int i, int j) {
		long tmp = arr[i][0]; arr[i][0] = arr[j][0]; arr[j][0] = tmp;
		tmp = arr[i][1]; arr[i][1] = arr[j][1]; arr[j][1] = tmp;
	}

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

	// 堆顶在1位置，考察新来的v值，判断能否更新堆
	public static void updateHeap(long v) {
		if (v <= heap[1]) {
			return;
		}
		heap[1] = v;
		int i = 1;
		int l = i * 2;
		while (l <= k) {
			int best = l + 1 <= k && heap[l + 1] < heap[l] ? l + 1 : l;
			best = heap[best] < heap[i] ? best : i;
			if (best == i) {
				break;
			}
			long tmp = heap[i]; heap[i] = heap[best]; heap[best] = tmp;
			i = best;
			l = i * 2;
		}
	}

	// 估计函数
	// 估计点i到rt子树中所有点的最大距离平方
	// 返回的是一个上界，只要这个上界都不超过当前第k远，就可以剪枝
	public static long guess(int i, int rt) {
		if (rt == 0) {
			return 0;
		}
		long x = arr[i][0];
		long y = arr[i][1];
		// x方向更远的一端
		long dx = Math.max(Math.abs(x - xmin[rt]), Math.abs(x - xmax[rt]));
		// y方向更远的一端
		long dy = Math.max(Math.abs(y - ymin[rt]), Math.abs(y - ymax[rt]));
		return dx * dx + dy * dy;
	}

	public static void updateAns(int i, int l, int r) {
		if (l > r) {
			return;
		}
		int mid = (l + r) >> 1;
		if (mid != i) {
			updateHeap(dist(i, mid));
		}
		if (l < r) {
			long gl = guess(i, ls[mid]);
			long gr = guess(i, rs[mid]);
			if (gl > gr) {
				if (gl > heap[1]) {
					updateAns(i, l, mid - 1);
				}
				if (gr > heap[1]) {
					updateAns(i, mid + 1, r);
				}
			} else {
				if (gr > heap[1]) {
					updateAns(i, mid + 1, r);
				}
				if (gl > heap[1]) {
					updateAns(i, l, mid - 1);
				}
			}
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		k = in.nextInt();
		// 无序点被计算两次，(a, b)和(b, a)，所以让k翻倍
		k <<= 1;
		for (int i = 1; i <= n; i++) {
			arr[i][0] = in.nextLong();
			arr[i][1] = in.nextLong();
		}
		xmin[0] = ymin[0] = INF;
		xmax[0] = ymax[0] = -INF;
		// build1(1, n, 0);
		build2(1, n);
		for (int i = 1; i <= n; i++) {
			updateAns(i, 1, n);
		}
		out.println(heap[1]);
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