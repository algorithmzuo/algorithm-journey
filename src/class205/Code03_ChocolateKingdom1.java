package class205;

// 巧克力王国，java版
// 一共n个点，每个点给定坐标(x, y)，还有点权v
// 一共m条查询，格式 a b c : 所有a*x+b*y<c的点，打印点权累加和
// 测试链接 : https://www.luogu.com.cn/problem/P4475
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code03_ChocolateKingdom1 {

	public static int MAXN = 50001;
	public static long INF = 1L << 60;
	public static int n, m;

	// x y v
	public static long[][] arr = new long[MAXN][3];

	public static int[] ls = new int[MAXN];
	public static int[] rs = new int[MAXN];

	// sum[i]表示以i为根的K-D Tree子树，所有点的权值和
	public static long[] sum = new long[MAXN];

	public static long[] xmin = new long[MAXN];
	public static long[] xmax = new long[MAXN];
	public static long[] ymin = new long[MAXN];
	public static long[] ymax = new long[MAXN];

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
		sum[i] = sum[ls[i]] + sum[rs[i]] + arr[i][2];
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

	public static long query(int a, int b, int c, int l, int r) {
		if (l > r) {
			return 0;
		}
		int mid = (l + r) >> 1;
		long axmin = a * xmin[mid];
		long axmax = a * xmax[mid];
		long bymin = b * ymin[mid];
		long bymax = b * ymax[mid];
		long minv = Math.min(axmin, axmax) + Math.min(bymin, bymax);
		long maxv = Math.max(axmin, axmax) + Math.max(bymin, bymax);
		if (minv >= c) {
			return 0;
		} else if (maxv < c) {
			return sum[mid];
		} else {
			long ans = 0;
			if (a * arr[mid][0] + b * arr[mid][1] < c) {
				ans += arr[mid][2];
			}
			ans += query(a, b, c, l, mid - 1);
			ans += query(a, b, c, mid + 1, r);
			return ans;
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		for (int i = 1; i <= n; i++) {
			arr[i][0] = in.nextInt();
			arr[i][1] = in.nextInt();
			arr[i][2] = in.nextInt();
		}
		xmin[0] = ymin[0] = INF;
		xmax[0] = ymax[0] = -INF;
		build(1, n, 0);
		for (int i = 1, a, b, c; i <= m; i++) {
			a = in.nextInt();
			b = in.nextInt();
			c = in.nextInt();
			out.println(query(a, b, c, 1, n));
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
