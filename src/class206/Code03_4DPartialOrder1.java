package class206;

// 四维偏序最长链，java版
// 一共n个点，每个点有四维坐标(a, b, c, d)
// 可以任意选择点的排列顺序，每个点最多使用一次
// 点x的后面可以放置点y的条件为，y的每个坐标 >= x对应的坐标
// 希望选择的点尽量多，打印最多能选择几个点
// 1 <= n <= 5 * 10^4
// -10^9 <= 坐标值 <= +10^9
// 测试链接 : https://www.luogu.com.cn/problem/P3769
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

public class Code03_4DPartialOrder1 {

	public static int MAXN = 50001;
	public static int MAXT = 500001;
	public static int INF = 1 << 30;
	public static int n;

	public static int[][] abcd = new int[MAXN][4];

	// b值、数据下标
	public static int[][] bi = new int[MAXN][2];

	// b值排名
	public static int[] ranking = new int[MAXN];

	public static int[] c = new int[MAXT];
	public static int[] d = new int[MAXT];
	public static int cntkdt;

	// 每个树状数组的下标，维护一棵动态kdt
	public static int[] root = new int[MAXN];
	public static int[] ls = new int[MAXT];
	public static int[] rs = new int[MAXT];
	public static int[] siz = new int[MAXT];
	public static int[] cmin = new int[MAXT];
	public static int[] cmax = new int[MAXT];
	public static int[] dmin = new int[MAXT];
	public static int[] dmax = new int[MAXT];

	public static double ALPHA = 0.7;
	public static int top;
	public static int[] arr = new int[MAXN];
	public static int treeSiz;

	public static int[] dp = new int[MAXT];
	public static int[] maxdp = new int[MAXT];

	public static int init(int qc, int qd, int qv) {
		cntkdt++;
		c[cntkdt] = qc;
		d[cntkdt] = qd;
		ls[cntkdt] = rs[cntkdt] = 0;
		siz[cntkdt] = 1;
		cmin[cntkdt] = cmax[cntkdt] = qc;
		dmin[cntkdt] = dmax[cntkdt] = qd;
		dp[cntkdt] = maxdp[cntkdt] = qv;
		return cntkdt;
	}

	public static void maintain(int i) {
		siz[i] = 1 + siz[ls[i]] + siz[rs[i]];
		cmin[i] = Math.min(c[i], Math.min(cmin[ls[i]], cmin[rs[i]]));
		cmax[i] = Math.max(c[i], Math.max(cmax[ls[i]], cmax[rs[i]]));
		dmin[i] = Math.min(d[i], Math.min(dmin[ls[i]], dmin[rs[i]]));
		dmax[i] = Math.max(d[i], Math.max(dmax[ls[i]], dmax[rs[i]]));
		maxdp[i] = Math.max(dp[i], Math.max(maxdp[ls[i]], maxdp[rs[i]]));
	}

	public static int compareNode(int i, int j, int dimension) {
		int v1 = dimension == 0 ? c[i] : d[i];
		int v2 = dimension == 0 ? c[j] : d[j];
		return v1 != v2 ? (v1 - v2) : (i - j);
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
		ls[rt] = build(l, mid - 1, dimension ^ 1);
		rs[rt] = build(mid + 1, r, dimension ^ 1);
		maintain(rt);
		return rt;
	}

	public static boolean balance(int i) {
		return ALPHA * siz[i] >= Math.max(siz[ls[i]], siz[rs[i]]);
	}

	public static void dfs(int i) {
		if (i != 0) {
			arr[++treeSiz] = i;
			dfs(ls[i]);
			dfs(rs[i]);
		}
	}

	public static int rebuild(int i, int dimension) {
		if (i == top) {
			treeSiz = 0;
			dfs(i);
			return build(1, treeSiz, dimension);
		}
		if (compareNode(top, i, dimension) < 0) {
			ls[i] = rebuild(ls[i], dimension ^ 1);
		} else {
			rs[i] = rebuild(rs[i], dimension ^ 1);
		}
		maintain(i);
		return i;
	}

	public static void rebuild(int version) {
		if (top != 0) {
			root[version] = rebuild(root[version], 0);
		}
	}

	public static int insert(int insertNode, int u, int dimension) {
		if (u == 0) {
			return insertNode;
		}
		if (compareNode(insertNode, u, dimension) < 0) {
			ls[u] = insert(insertNode, ls[u], dimension ^ 1);
		} else {
			rs[u] = insert(insertNode, rs[u], dimension ^ 1);
		}
		maintain(u);
		if (!balance(u)) {
			top = u;
		}
		return u;
	}

	public static void insertKdt(int version, int qx, int qy, int qv) {
		top = 0;
		int insertNode = init(qx, qy, qv);
		root[version] = insert(insertNode, root[version], 0);
		rebuild(version);
	}

	public static int lowbit(int i) {
		return i & -i;
	}

	// 新增一个状态，b排名为rank，二维坐标为(qc, qd)，dp值为qv
	public static void add(int rank, int qc, int qd, int qv) {
		for (int i = rank; i <= n; i += lowbit(i)) {
			insertKdt(i, qc, qd, qv);
		}
	}

	public static int queryAns;

	// 一棵KDT中查询，c坐标 <= qc，d坐标 <= qd 的所有点中，最大的dp值
	public static void updateAns(int qc, int qd, int i) {
		if (i == 0) {
			return;
		}
		if (cmin[i] > qc || dmin[i] > qd) {
			return;
		}
		if (maxdp[i] <= queryAns) {
			return;
		}
		if (cmax[i] <= qc && dmax[i] <= qd) {
			queryAns = Math.max(queryAns, maxdp[i]);
			return;
		}
		if (c[i] <= qc && d[i] <= qd) {
			queryAns = Math.max(queryAns, dp[i]);
		}
		updateAns(qc, qd, ls[i]);
		updateAns(qc, qd, rs[i]);
	}

	// 查询b排名 <= rank，c坐标 <= qc，d坐标 <= qd，所有历史中的最大dp值
	public static int query(int rank, int qc, int qd) {
		queryAns = 0;
		for (int i = rank; i > 0; i -= lowbit(i)) {
			updateAns(qc, qd, root[i]);
		}
		return queryAns;
	}

	public static void prepare() {
		Arrays.sort(abcd, 1, n + 1, (o1, o2) -> {
			for (int i = 0; i < 4; i++) {
				if (o1[i] != o2[i]) {
					return o1[i] - o2[i];
				}
			}
			return 0;
		});
		for (int i = 1; i <= n; i++) {
			bi[i][0] = abcd[i][1];
			bi[i][1] = i;
		}
		Arrays.sort(bi, 1, n + 1, (o1, o2) -> o1[0] != o2[0] ? o1[0] - o2[0] : o1[1] - o2[1]);
		for (int i = 1; i <= n; i++) {
			ranking[bi[i][1]] = i;
		}
		cmin[0] = dmin[0] = INF;
		cmax[0] = dmax[0] = -INF;
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		for (int i = 1; i <= n; i++) {
			abcd[i][0] = in.nextInt();
			abcd[i][1] = in.nextInt();
			abcd[i][2] = in.nextInt();
			abcd[i][3] = in.nextInt();
		}
		prepare();
		int ans = 0;
		for (int i = 1; i <= n; i++) {
			int cur = query(ranking[i], abcd[i][2], abcd[i][3]) + 1;
			ans = Math.max(ans, cur);
			add(ranking[i], abcd[i][2], abcd[i][3], cur);
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

	}

}