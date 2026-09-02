package class205;

// K-D树结合懒更新，替罪羊树的方式，java版
// 点的坐标有k维，点还有点权，k维空间中的轴对齐区域，可以用两个对角点表示
// 一共有m条操作，类型如下
// 操作 1 qx qv    : 空间里增加一个点，qx是k个值表示点的坐标，qv表示点权
// 操作 2 qx qy qv : 区域的两个对角点qx和qy，各自有k个值的坐标，该区域所有点的点权增加qv
// 操作 3 qx qy    : 区域的两个对角点qx和qy，各自有k个值的坐标，打印该区域所有点的点权和
// 本题要求强制在线，得到操作参数的规则，打开测试链接查看
// 2 <= k <= 3
// 1 <= m <= 10^5
// 坐标、点权、答案都需要long类型
// 测试链接 : https://www.luogu.com.cn/problem/P14312
// 提交以下的code，提交时请把类名改成"Main"，本题卡空间，java实现无法通过
// 想通过用C++实现，本节课Code05_KdtLazyTag2文件就是C++的实现
// 两个版本的逻辑完全一样，C++版本可以通过所有测试

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code05_KdtLazyTag1 {

	public static int MAXN = 200001;
	public static int MAXK = 3;
	public static long INF = 1L << 60;
	public static int k, m;

	public static long[][] pos = new long[MAXN][MAXK];
	public static long[] val = new long[MAXN];

	public static long[] qx = new long[MAXK];
	public static long[] qy = new long[MAXK];
	public static long qv;

	public static int cntkdt;
	public static int root;
	public static int[] ls = new int[MAXN];
	public static int[] rs = new int[MAXN];

	public static int[] siz = new int[MAXN];
	public static long[] sum = new long[MAXN];
	public static long[] tag = new long[MAXN];

	public static long[][] minv = new long[MAXN][MAXK];
	public static long[][] maxv = new long[MAXN][MAXK];

	public static double ALPHA = 0.7;
	public static int top;
	public static int topFather;
	public static int topSide;
	public static int topDimension;

	public static int[] arr = new int[MAXN];
	public static int treeSiz;

	public static int init() {
		cntkdt++;
		for (int d = 0; d < k; d++) {
			pos[cntkdt][d] = qx[d];
			minv[cntkdt][d] = maxv[cntkdt][d] = qx[d];
		}
		val[cntkdt] = qv;
		ls[cntkdt] = rs[cntkdt] = 0;
		siz[cntkdt] = 1;
		sum[cntkdt] = qv;
		tag[cntkdt] = 0;
		return cntkdt;
	}

	public static void maintain(int i) {
		siz[i] = 1 + siz[ls[i]] + siz[rs[i]];
		sum[i] = val[i] + sum[ls[i]] + sum[rs[i]];
		for (int d = 0; d < k; d++) {
			minv[i][d] = Math.min(pos[i][d], Math.min(minv[ls[i]][d], minv[rs[i]][d]));
			maxv[i][d] = Math.max(pos[i][d], Math.max(maxv[ls[i]][d], maxv[rs[i]][d]));
		}
	}

	public static void lazy(int i, long v) {
		if (i != 0) {
			val[i] += v;
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

	public static int compareNode(int i, int j, int dimension) {
		long a = pos[i][dimension];
		long b = pos[j][dimension];
		return a != b ? Long.compare(a, b) : (i - j);
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
		ls[rt] = build(l, mid - 1, (dimension + 1) % k);
		rs[rt] = build(mid + 1, r, (dimension + 1) % k);
		maintain(rt);
		return rt;
	}

	public static boolean balance(int i) {
		return ALPHA * siz[i] >= Math.max(siz[ls[i]], siz[rs[i]]);
	}

	public static void dfs(int i) {
		if (i != 0) {
			// 懒更新信息下发
			down(i);
			arr[++treeSiz] = i;
			dfs(ls[i]);
			dfs(rs[i]);
		}
	}

	public static void rebuild() {
		if (top != 0) {
			treeSiz = 0;
			dfs(top);
			int newRoot = build(1, treeSiz, topDimension);
			if (topFather == 0) {
				root = newRoot;
			} else if (topSide == 1) {
				ls[topFather] = newRoot;
			} else {
				rs[topFather] = newRoot;
			}
		}
	}

	public static int add(int insertNode, int u, int fa, int side, int dimension) {
		if (u == 0) {
			return insertNode;
		}
		// 懒更新信息下发
		down(u);
		if (compareNode(insertNode, u, dimension) < 0) {
			ls[u] = add(insertNode, ls[u], u, 1, (dimension + 1) % k);
		} else {
			rs[u] = add(insertNode, rs[u], u, 2, (dimension + 1) % k);
		}
		maintain(u);
		if (!balance(u)) {
			top = u;
			topFather = fa;
			topSide = side;
			topDimension = dimension;
		}
		return u;
	}

	public static void addNode() {
		top = topFather = topSide = topDimension = 0;
		int insertNode = init();
		root = add(insertNode, root, 0, 0, 0);
		rebuild();
	}

	public static boolean outside(int i) {
		for (int d = 0; d < k; d++) {
			if (maxv[i][d] < qx[d] || qy[d] < minv[i][d]) {
				return true;
			}
		}
		return false;
	}

	public static boolean covered(int i) {
		for (int d = 0; d < k; d++) {
			if (qx[d] > minv[i][d] || qy[d] < maxv[i][d]) {
				return false;
			}
		}
		return true;
	}

	public static boolean pointIn(int i) {
		for (int d = 0; d < k; d++) {
			if (qx[d] > pos[i][d] || qy[d] < pos[i][d]) {
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
			lazy(i, qv);
			return;
		}
		if (pointIn(i)) {
			val[i] += qv;
		}
		// 懒更新信息下发
		down(i);
		addValue(ls[i]);
		addValue(rs[i]);
		maintain(i);
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
			ans += val[i];
		}
		// 懒更新信息下发
		down(i);
		ans += querySum(ls[i]);
		ans += querySum(rs[i]);
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
					qx[d] = in.nextLong();
					qx[d] ^= lastAns;
				}
				qv = in.nextLong();
				qv ^= lastAns;
				addNode();
			} else {
				for (int d = 0; d < k; d++) {
					qx[d] = in.nextLong();
					qx[d] ^= lastAns;
				}
				for (int d = 0; d < k; d++) {
					qy[d] = in.nextLong();
					qy[d] ^= lastAns;
				}
				if (op == 2) {
					qv = in.nextLong();
					qv ^= lastAns;
					addValue(root);
				} else {
					lastAns = querySum(root);
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