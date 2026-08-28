package class205;

// 查询第k远的点，java版
// 一共n个点，编号1~n，每个点给定坐标(x, y)
// 一共m条查询，格式 qx qy qk，查询距离(qx, qy)第qk远的点，打印该点的编号
// 如果多个点到(qx, qy)的距离相同，那么编号较小的点认为距离更远
// 1 <= n <= 10^5
// 1 <= m <= 10^4
// 1 <= qk <= 20
// -10^9 <= 坐标值 <= +10^9
// 测试链接 : https://www.luogu.com.cn/problem/P2093
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.PriorityQueue;

public class Code02_QueryKthFarthest1 {

	public static int MAXN = 100001;
	public static long INF = 1L << 60;
	public static int n, m;

	public static int root;
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
		randSelect(l, r, mid, dimension);
		ls[mid] = build(l, mid - 1, dimension ^ 1);
		rs[mid] = build(mid + 1, r, dimension ^ 1);
		maintain(mid);
		return mid;
	}

	public static long dist(long x1, long y1, long x2, long y2) {
		long dx = x1 - x2;
		long dy = y1 - y2;
		return dx * dx + dy * dy;
	}

	// 估计函数，估计查询点到i子树中所有点的最大距离平方
	public static long guess(int qx, int qy, int i) {
		if (i == 0) {
			return 0;
		}
		long dx = Math.max(Math.abs(qx - xmin[i]), Math.abs(qx - xmax[i]));
		long dy = Math.max(Math.abs(qy - ymin[i]), Math.abs(qy - ymax[i]));
		return dx * dx + dy * dy;
	}

	public static void updateAns(int qx, int qy, int i) {
		if (i == 0) {
			return;
		}
		long d = dist(qx, qy, arr[i][0], arr[i][1]);
		if (d > heap.peek()[0] || (d == heap.peek()[0] && arr[i][2] < heap.peek()[1])) {
			heap.poll();
			heap.add(new long[] { d, arr[i][2] });
		}
		long gl = guess(qx, qy, ls[i]);
		long gr = guess(qx, qy, rs[i]);
		if (gl > gr) {
			// 注意判断必须是 >=
			// 因为本题距离相同时编号较小者更优
			// 所以距离相同，依然可能存在编号更小的点
			if (gl >= heap.peek()[0]) {
				updateAns(qx, qy, ls[i]);
			}
			if (gr >= heap.peek()[0]) {
				updateAns(qx, qy, rs[i]);
			}
		} else {
			if (gr >= heap.peek()[0]) {
				updateAns(qx, qy, rs[i]);
			}
			if (gl >= heap.peek()[0]) {
				updateAns(qx, qy, ls[i]);
			}
		}
	}

	public static int query(int qx, int qy, int qk) {
		heap.clear();
		for (int i = 1; i <= qk; i++) {
			heap.add(new long[] { -1, 0 });
		}
		updateAns(qx, qy, root);
		return (int) heap.peek()[1];
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		for (int i = 1; i <= n; i++) {
			arr[i][0] = in.nextInt();
			arr[i][1] = in.nextInt();
			arr[i][2] = i;
		}
		xmin[0] = ymin[0] = INF;
		xmax[0] = ymax[0] = -INF;
		root = build(1, n, 0);
		m = in.nextInt();
		for (int i = 1, qx, qy, qk; i <= m; i++) {
			qx = in.nextInt();
			qy = in.nextInt();
			qk = in.nextInt();
			out.println(query(qx, qy, qk));
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