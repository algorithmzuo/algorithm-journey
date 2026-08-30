package class206;

// 崂山白花蛇草水，java版
// 网格空间n * n，一共有q条操作，类型如下
// 操作 1 a b v     : 坐标(a, b)，增加一个点，点权为v
// 操作 2 a b c d k : 查询(a, b)为左下角，(c, d)为右上角的矩形中，第k大的点权
// 如果点不够k个，打印 NAIVE!ORZzyz.
// 本题要求强制在线，得到操作参数的规则，打开测试链接查看
// 1 <= n <= 5 * 10^5
// 1 <= q <= 10^5
// 1 <= v <= 10^9
// 本题推荐loj的测试，洛谷本题的新增用例让该题变成了卡常竞赛，实在没必要
// 测试链接 : https://loj.ac/p/6016
// 测试链接 : https://www.luogu.com.cn/problem/P4848
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code05_LaoshanBaihua1 {

	public static int MAXN = 100001;
	public static int MAXT = 3000001;
	public static int MAXV = 1000000000;
	public static int INF = 1 << 30;
	public static int n, q;
	public static int a, b, c, d, v, k;

	// 权值线段树节点计数
	public static int cntseg;
	// K-D树的节点计数
	public static int cntkdt;

	// 权值线段树
	public static int rootseg;
	public static int[] lseg = new int[MAXT];
	public static int[] rseg = new int[MAXT];
	public static int[] rootkdt = new int[MAXT];

	// K-D树
	public static int[] x = new int[MAXT];
	public static int[] y = new int[MAXT];
	public static int[] ls = new int[MAXT];
	public static int[] rs = new int[MAXT];
	public static int[] siz = new int[MAXT];
	public static int[] xmin = new int[MAXT];
	public static int[] xmax = new int[MAXT];
	public static int[] ymin = new int[MAXT];
	public static int[] ymax = new int[MAXT];

	public static double ALPHA = 0.7;
	public static int top;
	public static int topFather;
	public static int topSide;
	public static int topDimension;

	public static int[] arr = new int[MAXN];
	public static int treeSiz;

	public static int init() {
		cntkdt++;
		x[cntkdt] = a;
		y[cntkdt] = b;
		ls[cntkdt] = rs[cntkdt] = 0;
		siz[cntkdt] = 1;
		xmin[cntkdt] = xmax[cntkdt] = a;
		ymin[cntkdt] = ymax[cntkdt] = b;
		return cntkdt;
	}

	public static void maintain(int i) {
		siz[i] = 1 + siz[ls[i]] + siz[rs[i]];
		xmin[i] = Math.min(x[i], Math.min(xmin[ls[i]], xmin[rs[i]]));
		xmax[i] = Math.max(x[i], Math.max(xmax[ls[i]], xmax[rs[i]]));
		ymin[i] = Math.min(y[i], Math.min(ymin[ls[i]], ymin[rs[i]]));
		ymax[i] = Math.max(y[i], Math.max(ymax[ls[i]], ymax[rs[i]]));
	}

	public static void swap(int i, int j) {
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}

	public static int first, last;

	public static void partition(int l, int r, int pivot, int dimension) {
		first = l;
		last = r;
		int i = l;
		while (i <= last) {
			int idx = arr[i];
			int cur = dimension == 0 ? x[idx] : y[idx];
			if (cur == pivot) {
				i++;
			} else if (cur < pivot) {
				swap(first++, i++);
			} else {
				swap(i, last--);
			}
		}
	}

	public static void randSelect(int l, int r, int i, int dimension) {
		while (l <= r) {
			int idx = arr[l + (int) (Math.random() * (r - l + 1))];
			int pivot = dimension == 0 ? x[idx] : y[idx];
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

	public static int build(int l, int r, int dimension) {
		if (l > r) {
			return 0;
		}
		int mid = (l + r) >> 1;
		randSelect(l, r, mid, dimension);
		int rt = arr[mid];
		ls[rt] = build(l, mid - 1, dimension ^ 1);
		rs[rt] = build(mid + 1, r, dimension ^ 1);
		maintain(rt);
		return rt;
	}

	public static boolean balance(int i) {
		return ALPHA * siz[i] >= Math.max(siz[ls[i]], siz[rs[i]]);
	}

	// 因为有重构的可能，所以牵扯到换头，换头改rootkdt[version]即可
	public static void add(int insertNode, int version, int u, int fa, int side, int dimension) {
		if (u == 0) {
			if (fa == 0) {
				rootkdt[version] = insertNode;
			} else if (side == 1) {
				ls[fa] = insertNode;
			} else {
				rs[fa] = insertNode;
			}
		} else {
			int insertd = dimension == 0 ? x[insertNode] : y[insertNode];
			int ud = dimension == 0 ? x[u] : y[u];
			if (insertd <= ud) {
				add(insertNode, version, ls[u], u, 1, dimension ^ 1);
			} else {
				add(insertNode, version, rs[u], u, 2, dimension ^ 1);
			}
			maintain(u);
			if (!balance(u)) {
				top = u;
				topFather = fa;
				topSide = side;
				topDimension = dimension;
			}
		}
	}

	public static void dfs(int i) {
		if (i != 0) {
			arr[++treeSiz] = i;
			dfs(ls[i]);
			dfs(rs[i]);
		}
	}

	public static void rebuild(int version) {
		if (top != 0) {
			treeSiz = 0;
			dfs(top);
			int rt = build(1, treeSiz, topDimension);
			if (topFather == 0) {
				rootkdt[version] = rt;
			} else if (topSide == 1) {
				ls[topFather] = rt;
			} else {
				rs[topFather] = rt;
			}
		}
	}

	// 当前点属于以rootkdt[version]为头的kdt，插入并调整
	public static void insertKdt(int version) {
		top = topFather = topSide = topDimension = 0;
		int insertNode = init();
		add(insertNode, version, rootkdt[version], 0, 0, 0);
		rebuild(version);
	}

	// 权值线段树上增加点
	public static int add(int l, int r, int i) {
		if (i == 0) {
			i = ++cntseg;
		}
		insertKdt(i);
		if (l < r) {
			int mid = (l + r) >> 1;
			if (v <= mid) {
				lseg[i] = add(l, mid, lseg[i]);
			} else {
				rseg[i] = add(mid + 1, r, rseg[i]);
			}
		}
		return i;
	}

	public static boolean outside(int i) {
		return xmax[i] < a || c < xmin[i] || ymax[i] < b || d < ymin[i];
	}

	public static boolean covered(int i) {
		return a <= xmin[i] && xmax[i] <= c && b <= ymin[i] && ymax[i] <= d;
	}

	public static boolean pointIn(int i) {
		return a <= x[i] && x[i] <= c && b <= y[i] && y[i] <= d;
	}

	// 查询一棵KDT中，矩形内有多少个点
	public static int queryCount(int i) {
		if (i == 0) {
			return 0;
		}
		if (outside(i)) {
			return 0;
		}
		if (covered(i)) {
			return siz[i];
		}
		int ans = pointIn(i) ? 1 : 0;
		ans += queryCount(ls[i]);
		ans += queryCount(rs[i]);
		return ans;
	}

	// 查询权值线段树中，矩形内第jobk大的点权，确定存在
	public static int query(int jobk, int l, int r, int i) {
		if (l == r) {
			return l;
		}
		int mid = (l + r) >> 1;
		int cnt = queryCount(rootkdt[rseg[i]]);
		if (cnt >= jobk) {
			return query(jobk, mid + 1, r, rseg[i]);
		} else {
			return query(jobk - cnt, l, mid, lseg[i]);
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		q = in.nextInt();
		xmin[0] = ymin[0] = INF;
		xmax[0] = ymax[0] = -INF;
		for (int i = 1, op, lastAns = 0; i <= q; i++) {
			op = in.nextInt();
			if (op == 1) {
				a = in.nextInt();
				b = in.nextInt();
				v = in.nextInt();
				a ^= lastAns;
				b ^= lastAns;
				v ^= lastAns;
				rootseg = add(1, MAXV, rootseg);
			} else {
				a = in.nextInt();
				b = in.nextInt();
				c = in.nextInt();
				d = in.nextInt();
				k = in.nextInt();
				a ^= lastAns;
				b ^= lastAns;
				c ^= lastAns;
				d ^= lastAns;
				k ^= lastAns;
				if (queryCount(rootkdt[rootseg]) >= k) {
					lastAns = query(k, 1, MAXV, rootseg);
				} else {
					lastAns = 0;
				}
				if (lastAns == 0) {
					out.println("NAIVE!ORZzyz.");
				} else {
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

	}

}