package class205;

// 简单题，java版
// 测试链接 : https://www.luogu.com.cn/problem/P4148
// 提交以下的code，提交时请把类名改成"Main"，本题卡空间，无法通过
// 想通过用C++实现，本节课Code04_SimpleProblem2文件就是C++的实现
// 两个版本的逻辑完全一样，C++版本可以通过所有测试

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code04_SimpleProblem1 {

	public static int MAXN = 200001;
	public static int MAXP = 19;
	public static int INF = 1 << 30;
	public static int n, cntn;

	public static int[][] arr = new int[MAXN][3];

	public static int[] ls = new int[MAXN];
	public static int[] rs = new int[MAXN];
	public static int[] sum = new int[MAXN];
	public static int[] xmin = new int[MAXN];
	public static int[] xmax = new int[MAXN];
	public static int[] ymin = new int[MAXN];
	public static int[] ymax = new int[MAXN];

	// root[p]表示大小为2的p次方的KDT，根节点编号
	public static int[] root = new int[MAXP];

	public static int first, last;

	public static void swap(int i, int j) {
		int tmp = arr[i][0]; arr[i][0] = arr[j][0]; arr[j][0] = tmp;
		tmp = arr[i][1]; arr[i][1] = arr[j][1]; arr[j][1] = tmp;
		tmp = arr[i][2]; arr[i][2] = arr[j][2]; arr[j][2] = tmp;
	}

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
		sum[i] = arr[i][2] + sum[ls[i]] + sum[rs[i]];
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

	public static void add(int x, int y, int v) {
		cntn++;
		arr[cntn][0] = x;
		arr[cntn][1] = y;
		arr[cntn][2] = v;
		int p = 0;
		while (root[p] != 0) {
			root[p++] = 0;
		}
		root[p] = build(cntn - (1 << p) + 1, cntn, 0);
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
		if (x1 <= arr[i][0] && arr[i][0] <= x2 && y1 <= arr[i][1] && arr[i][1] <= y2) {
			ans += arr[i][2];
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