package class205;

// kd树结合懒更新，java版
// 测试链接 : https://www.luogu.com.cn/problem/P14312
// 提交以下的code，提交时请把类名改成"Main"，本题卡空间，无法通过
// 想通过用C++实现，本节课Code06_KdtLazyTag2文件就是C++的实现
// 两个版本的逻辑完全一样，C++版本可以通过所有测试

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code06_KdtLazyTag1 {

	public static int MAXN = 200001;
	public static int MAXP = 19;
	public static int MAXK = 3;
	public static long INF = 1L << 60;
	public static int k, m, cntn;

	public static long[] coordinate = new long[MAXK];
	public static long[] low = new long[MAXK];
	public static long[] high = new long[MAXK];
	public static long val;

	public static long[][] arr = new long[MAXN][MAXK + 1];
	public static int[] ls = new int[MAXN];
	public static int[] rs = new int[MAXN];

	// siz[i]表示i子树的节点个数
	// sum[i]表示i子树的点权累加和
	// tag[i]表示i子树的点权增加幅度，懒更新信息
	public static int[] siz = new int[MAXN];
	public static long[] sum = new long[MAXN];
	public static long[] tag = new long[MAXN];

	// 每个维度的最小值、最大值
	public static long[][] minv = new long[MAXN][MAXK];
	public static long[][] maxv = new long[MAXN][MAXK];

	public static int[] root = new int[MAXP];

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
		siz[i] = 1 + siz[ls[i]] + siz[rs[i]];
		sum[i] = arr[i][k] + sum[ls[i]] + sum[rs[i]];
		for (int d = 0; d < k; d++) {
			minv[i][d] = Math.min(arr[i][d], Math.min(minv[ls[i]][d], minv[rs[i]][d]));
			maxv[i][d] = Math.max(arr[i][d], Math.max(maxv[ls[i]][d], maxv[rs[i]][d]));
		}
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
			ls[mid] = build(l, mid - 1, (dimension + 1) % k);
			rs[mid] = build(mid + 1, r, (dimension + 1) % k);
		}
		maintain(mid);
		return mid;
	}

	public static void lazy(int i, long v) {
		if (i != 0) {
			arr[i][k] += v;
			sum[i] += v * siz[i];
			tag[i] += v;
		}
	}

	public static void down(int i) {
		if (tag[i] != 0) {
			lazy(ls[i], tag[i]);
			lazy(rs[i], tag[i]);
			tag[i] = 0;
		}
	}

	public static void dfs(int i) {
		if (i != 0) {
			down(i);
			dfs(ls[i]);
			dfs(rs[i]);
		}
	}

	public static void insert() {
		cntn++;
		for (int d = 0; d < k; d++) {
			arr[cntn][d] = coordinate[d];
		}
		arr[cntn][k] = val;
		int p = 0;
		while (root[p] != 0) {
			dfs(root[p]);
			root[p++] = 0;
		}
		root[p] = build(cntn - (1 << p) + 1, cntn, 0);
	}

	public static boolean outside(int i) {
		for (int d = 0; d < k; d++) {
			if (maxv[i][d] < low[d] || high[d] < minv[i][d]) {
				return true;
			}
		}
		return false;
	}

	public static boolean covered(int i) {
		for (int d = 0; d < k; d++) {
			if (low[d] > minv[i][d] || high[d] < maxv[i][d]) {
				return false;
			}
		}
		return true;
	}

	public static boolean pointIn(int i) {
		for (int d = 0; d < k; d++) {
			if (low[d] > arr[i][d] || high[d] < arr[i][d]) {
				return false;
			}
		}
		return true;
	}

	public static void addValue(int i) {
		if (i == 0) {
			return;
		}
		if (outside(i)) {
			return;
		}
		if (covered(i)) {
			lazy(i, val);
			return;
		}
		if (pointIn(i)) {
			arr[i][k] += val;
		}
		down(i);
		addValue(ls[i]);
		addValue(rs[i]);
		maintain(i);
	}

	public static void addValue() {
		for (int p = 0; p < MAXP; p++) {
			addValue(root[p]);
		}
	}

	public static long querySum(int i) {
		if (i == 0) {
			return 0;
		}
		if (outside(i)) {
			return 0;
		}
		if (covered(i)) {
			return sum[i];
		}
		long ans = 0;
		if (pointIn(i)) {
			ans += arr[i][k];
		}
		down(i);
		ans += querySum(ls[i]);
		ans += querySum(rs[i]);
		return ans;
	}

	public static long querySum() {
		long ans = 0;
		for (int p = 0; p < MAXP; p++) {
			ans += querySum(root[p]);
		}
		return ans;
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		k = in.nextInt();
		m = in.nextInt();
		for (int d = 0; d < k; d++) {
			minv[0][d] = INF;
			maxv[0][d] = -INF;
		}
		long lastAns = 0;
		for (int i = 1, op; i <= m; i++) {
			op = in.nextInt();
			if (op == 1) {
				for (int d = 0; d < k; d++) {
					coordinate[d] = in.nextLong();
					coordinate[d] ^= lastAns;
				}
				val = in.nextLong();
				val ^= lastAns;
				insert();
			} else {
				for (int d = 0; d < k; d++) {
					low[d] = in.nextLong();
					low[d] ^= lastAns;
				}
				for (int d = 0; d < k; d++) {
					high[d] = in.nextLong();
					high[d] ^= lastAns;
				}
				if (op == 2) {
					val = in.nextLong();
					val ^= lastAns;
					addValue();
				} else {
					lastAns = querySum();
					out.println(lastAns);
				}
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