package class205;

// 三维偏序，java版
// 本题就是讲解170，题目1，讲了CDQ分治的解法，这里用kdt的解法
// 一共有n个对象，属性值范围[1, k]，每个对象有a属性、b属性、c属性
// f(i)表示，aj <= ai 且 bj <= bi 且 cj <= ci 且 j != i 的j的数量
// ans(d)表示，f(i) == d 的i的数量
// 打印所有的ans[d]，d的范围[0, n)
// 1 <= n <= 10^5
// 1 <= k <= 2 * 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P3810
// 提交以下的code，提交时请把类名改成"Main"，kdt不是最优解，java实现超时
// 想通过用C++实现，本节课Code07_3DPartialOrder2文件就是C++的实现
// 两个版本的逻辑完全一样，C++版本可以通过所有测试

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

public class Code07_3DPartialOrder1 {

	public static int MAXN = 100001;
	public static int MAXP = 18;
	public static int INF = 1 << 30;
	public static int n, k, cntn;

	// a、b、c
	public static int[][] abc = new int[MAXN][3];
	// b、c
	public static int[][] bc = new int[MAXN][2];

	// siz[i]表示子树i的节点个数
	public static int[] siz = new int[MAXN];
	public static int[] ls = new int[MAXN];
	public static int[] rs = new int[MAXN];

	public static int[] bmin = new int[MAXN];
	public static int[] bmax = new int[MAXN];
	public static int[] cmin = new int[MAXN];
	public static int[] cmax = new int[MAXN];

	public static int[] root = new int[MAXP];
	public static int[] ans = new int[MAXN];

	public static void swap(int i, int j) {
		int[] tmp = bc[i];
		bc[i] = bc[j];
		bc[j] = tmp;
	}

	public static int first, last;

	public static void partition(int l, int r, int pivot, int dimension) {
		first = l;
		last = r;
		int i = l;
		while (i <= last) {
			if (bc[i][dimension] == pivot) {
				i++;
			} else if (bc[i][dimension] < pivot) {
				swap(first++, i++);
			} else {
				swap(i, last--);
			}
		}
	}

	public static void randSelect(int l, int r, int i, int dimension) {
		while (l <= r) {
			int pivot = bc[l + (int) (Math.random() * (r - l + 1))][dimension];
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
		siz[i] = siz[ls[i]] + siz[rs[i]] + 1;
		bmin[i] = Math.min(bc[i][0], Math.min(bmin[ls[i]], bmin[rs[i]]));
		bmax[i] = Math.max(bc[i][0], Math.max(bmax[ls[i]], bmax[rs[i]]));
		cmin[i] = Math.min(bc[i][1], Math.min(cmin[ls[i]], cmin[rs[i]]));
		cmax[i] = Math.max(bc[i][1], Math.max(cmax[ls[i]], cmax[rs[i]]));
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

	public static void add(int b, int c) {
		cntn++;
		bc[cntn][0] = b;
		bc[cntn][1] = c;
		int p = 0;
		while (root[p] != 0) {
			root[p++] = 0;
		}
		root[p] = build(cntn - (1 << p) + 1, cntn, 0);
	}

	// 查询i的子树中，有多少点满足 b' <= b && c' <= c
	public static int query(int b, int c, int i) {
		if (i == 0) {
			return 0;
		}
		// 整棵子树在查询矩形外
		if (bmin[i] > b || cmin[i] > c) {
			return 0;
		}
		// 整棵子树在查询矩形内
		if (bmax[i] <= b && cmax[i] <= c) {
			return siz[i];
		}
		int ans = 0;
		// 当前点满足要求
		if (bc[i][0] <= b && bc[i][1] <= c) {
			ans++;
		}
		ans += query(b, c, ls[i]);
		ans += query(b, c, rs[i]);
		return ans;
	}

	public static int query(int b, int c) {
		int ans = 0;
		for (int p = 0; p < MAXP; p++) {
			ans += query(b, c, root[p]);
		}
		return ans;
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		k = in.nextInt();
		for (int i = 1; i <= n; i++) {
			abc[i][0] = in.nextInt();
			abc[i][1] = in.nextInt();
			abc[i][2] = in.nextInt();
		}
		// 根据a排序，三维偏序变成二维偏序
		Arrays.sort(abc, 1, n + 1, (x, y) -> x[0] - y[0]);
		bmin[0] = cmin[0] = INF;
		bmax[0] = cmax[0] = -INF;
		for (int l = 1, r = 1; l <= n; l = ++r) {
			while (r + 1 <= n && abc[r + 1][0] == abc[l][0]) {
				r++;
			}
			// 同组都加入
			for (int i = l; i <= r; i++) {
				add(abc[i][1], abc[i][2]);
			}
			for (int i = l; i <= r; i++) {
				int cur = query(abc[i][1], abc[i][2]);
				// 不能把自己统计进去
				ans[cur - 1]++;
			}
		}
		for (int d = 0; d < n; d++) {
			out.println(ans[d]);
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
