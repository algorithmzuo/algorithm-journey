package class205;

// 四维偏序最长链，java版
// 测试链接 : https://www.luogu.com.cn/problem/P3769
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

public class Code08_4DPartialOrder1 {

	public static int MAXN = 50001;
	public static int MAXT = 500001;
	public static int INF = 1 << 30;
	public static int n, cntn;

	public static int[][] abcd = new int[MAXN][4];

	public static int[][] bi = new int[MAXN][2];
	public static int[] ranking = new int[MAXN];

	public static int[][] cd = new int[MAXN][2];

	public static int[][] kdtcd = new int[MAXT][2];
	public static int[] ls = new int[MAXT];
	public static int[] rs = new int[MAXT];

	public static int[] cmin = new int[MAXT];
	public static int[] cmax = new int[MAXT];
	public static int[] dmin = new int[MAXT];
	public static int[] dmax = new int[MAXT];

	public static int[] dp = new int[MAXT];
	public static int[] maxdp = new int[MAXT];
	public static int[] tag = new int[MAXT];

	public static int[] root = new int[MAXN];

	public static int first, last;

	public static void swap(int i, int j) {
		int tmp = cd[i][0]; cd[i][0] = cd[j][0]; cd[j][0] = tmp;
		tmp = cd[i][1]; cd[i][1] = cd[j][1]; cd[j][1] = tmp;
	}

	public static void partition(int l, int r, int pivot, int dimension) {
		first = l;
		last = r;
		int i = l;
		while (i <= last) {
			if (cd[i][dimension] == pivot) {
				i++;
			} else if (cd[i][dimension] < pivot) {
				swap(first++, i++);
			} else {
				swap(i, last--);
			}
		}
	}

	public static void randSelect(int l, int r, int i, int dimension) {
		while (l <= r) {
			int pivot = cd[l + (int) (Math.random() * (r - l + 1))][dimension];
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
		maxdp[i] = Math.max(dp[i], Math.max(maxdp[ls[i]], maxdp[rs[i]]));
		cmin[i] = Math.min(kdtcd[i][0], Math.min(cmin[ls[i]], cmin[rs[i]]));
		cmax[i] = Math.max(kdtcd[i][0], Math.max(cmax[ls[i]], cmax[rs[i]]));
		dmin[i] = Math.min(kdtcd[i][1], Math.min(dmin[ls[i]], dmin[rs[i]]));
		dmax[i] = Math.max(kdtcd[i][1], Math.max(dmax[ls[i]], dmax[rs[i]]));
	}

	public static int build(int l, int r, int dimension) {
		if (l > r) {
			return 0;
		}
		int mid = (l + r) >> 1;
		int rt = ++cntn;
		randSelect(l, r, mid, dimension);
		kdtcd[rt][0] = cd[mid][0];
		kdtcd[rt][1] = cd[mid][1];
		ls[rt] = build(l, mid - 1, dimension ^ 1);
		rs[rt] = build(mid + 1, r, dimension ^ 1);
		maintain(rt);
		return rt;
	}

	public static void lazy(int i, int v) {
		if (i != 0) {
			dp[i] = Math.max(dp[i], v);
			maxdp[i] = Math.max(maxdp[i], v);
			tag[i] = Math.max(tag[i], v);
		}
	}

	public static void down(int i) {
		if (tag[i] != 0) {
			lazy(ls[i], tag[i]);
			lazy(rs[i], tag[i]);
			tag[i] = 0;
		}
	}

	public static int lowbit(int i) {
		return i & -i;
	}

	public static int queryAns;

	public static void updateAns(int c, int d, int i) {
		if (i == 0) {
			return;
		}
		if (cmin[i] > c || dmin[i] > d) {
			return;
		}
		if (maxdp[i] <= queryAns) {
			return;
		}
		if (cmax[i] <= c && dmax[i] <= d) {
			queryAns = Math.max(queryAns, maxdp[i]);
			return;
		}
		down(i);
		if (kdtcd[i][0] <= c && kdtcd[i][1] <= d) {
			queryAns = Math.max(queryAns, dp[i]);
		}
		updateAns(c, d, ls[i]);
		updateAns(c, d, rs[i]);
	}

	public static int query(int rank, int c, int d) {
		queryAns = 0;
		for (int i = rank; i > 0; i -= lowbit(i)) {
			updateAns(c, d, root[i]);
		}
		return queryAns;
	}

	public static void update(int c, int d, int v, int i) {
		if (i == 0) {
			return;
		}
		if (c < cmin[i] || c > cmax[i] || d < dmin[i] || d > dmax[i]) {
			return;
		}
		if (cmin[i] == c && cmax[i] == c && dmin[i] == d && dmax[i] == d) {
			lazy(i, v);
			return;
		}
		down(i);
		if (kdtcd[i][0] == c && kdtcd[i][1] == d) {
			dp[i] = Math.max(dp[i], v);
		}
		update(c, d, v, ls[i]);
		update(c, d, v, rs[i]);
		maxdp[i] = Math.max(dp[i], Math.max(maxdp[ls[i]], maxdp[rs[i]]));
	}

	public static void add(int rank, int c, int d, int v) {
		for (int i = rank; i <= n; i += lowbit(i)) {
			update(c, d, v, root[i]);
		}
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
		for (int i = 1; i <= n; i++) {
			int siz = lowbit(i);
			int l = i - siz + 1;
			for (int j = 1; j <= siz; j++) {
				int idx = bi[l + j - 1][1];
				cd[j][0] = abcd[idx][2];
				cd[j][1] = abcd[idx][3];
			}
			root[i] = build(1, siz, 0);
		}
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