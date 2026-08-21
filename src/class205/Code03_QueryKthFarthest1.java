package class205;

// 查询第k远，java版
// 测试链接 : https://www.luogu.com.cn/problem/P2093
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.PriorityQueue;

public class Code03_QueryKthFarthest1 {

	public static int MAXN = 100001;
	public static long INF = 1L << 60;
	public static int n, m;
	public static long qx, qy, qk;

	// x、y、id
	public static long[][] arr = new long[MAXN][3];

	public static int[] ls = new int[MAXN];
	public static int[] rs = new int[MAXN];

	public static long[] xmin = new long[MAXN];
	public static long[] xmax = new long[MAXN];
	public static long[] ymin = new long[MAXN];
	public static long[] ymax = new long[MAXN];

	// dist、id
	public static PriorityQueue<long[]> heap = new PriorityQueue<>(
			(a, b) -> a[0] != b[0] ? Long.compare(a[0], b[0]) : Long.compare(b[1], a[1]));

	public static int first, last;

	public static void swap(int i, int j) {
		long tmp = arr[i][0]; arr[i][0] = arr[j][0]; arr[j][0] = tmp;
		tmp = arr[i][1]; arr[i][1] = arr[j][1]; arr[j][1] = tmp;
		tmp = arr[i][2]; arr[i][2] = arr[j][2]; arr[j][2] = tmp;
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

	public static long dist(long x1, long y1, long x2, long y2) {
		long dx = x1 - x2;
		long dy = y1 - y2;
		return dx * dx + dy * dy;
	}

	public static long guess(int rt) {
		if (rt == 0) {
			return 0;
		}
		long dx = Math.max(Math.abs(qx - xmin[rt]), Math.abs(qx - xmax[rt]));
		long dy = Math.max(Math.abs(qy - ymin[rt]), Math.abs(qy - ymax[rt]));
		return dx * dx + dy * dy;
	}

	public static void updateAns(int l, int r) {
		if (l > r) {
			return;
		}
		int mid = (l + r) >> 1;
		long d = dist(qx, qy, arr[mid][0], arr[mid][1]);
		if (d > heap.peek()[0] || (d == heap.peek()[0] && arr[mid][2] < heap.peek()[1])) {
			heap.poll();
			heap.add(new long[] { d, arr[mid][2] });
		}
		if (l < r) {
			long gl = guess(ls[mid]);
			long gr = guess(rs[mid]);
			if (gl > gr) {
				// 注意判断，必须是 >=
				// 因为本题距离相同时编号较小者更优
				// 所以距离相同，依然可能存在编号更小的点
				if (gl >= heap.peek()[0]) {
					updateAns(l, mid - 1);
				}
				if (gr >= heap.peek()[0]) {
					updateAns(mid + 1, r);
				}
			} else {
				if (gr >= heap.peek()[0]) {
					updateAns(mid + 1, r);
				}
				if (gl >= heap.peek()[0]) {
					updateAns(l, mid - 1);
				}
			}
		}
	}

	public static long query() {
		heap.clear();
		for (int i = 1; i <= qk; i++) {
			heap.add(new long[] { -1, 0 });
		}
		updateAns(1, n);
		return heap.peek()[1];
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		for (int i = 1; i <= n; i++) {
			arr[i][0] = in.nextLong();
			arr[i][1] = in.nextLong();
			arr[i][2] = i;
		}
		xmin[0] = ymin[0] = INF;
		xmax[0] = ymax[0] = -INF;
		build(1, n, 0);
		m = in.nextInt();
		for (int i = 1; i <= m; i++) {
			qx = in.nextLong();
			qy = in.nextLong();
			qk = in.nextLong();
			out.println(query());
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