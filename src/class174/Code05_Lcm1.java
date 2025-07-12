package class174;

// 最小公倍数，java版
// 有n个点组成的无向图，依次给出m条无向边，每条边都有边权，并且边权很特殊
// u v a b : u到v的边，边权 = 2的a次方 * 3的b次方
// 接下来有q条查询，每条查询的格式如下
// u v a b : 从u出发可以随意选择边到达v，打印是否存在一条路径，满足如下条件
//           路径上所有边权的最小公倍数 = 2的a次方 * 3的b次方
// 1 <= n、q <= 5 * 10^4
// 1 <= m <= 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P3247
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code05_Lcm1 {

	public static int MAXN = 50001;
	public static int MAXM = 100001;
	public static int MAXQ = 50001;
	public static int n, m, q;
	public static int blen, bnum;

	public static int[] eu = new int[MAXM];
	public static int[] ev = new int[MAXM];
	public static int[] ea = new int[MAXM];
	public static int[] eb = new int[MAXM];

	public static int[] qu = new int[MAXQ];
	public static int[] qv = new int[MAXQ];
	public static int[] qa = new int[MAXQ];
	public static int[] qb = new int[MAXQ];
	public static int[] qid = new int[MAXQ];

	public static int[] arre = new int[MAXM];
	public static int[] arrq = new int[MAXQ];

	public static int[] cur = new int[MAXQ];
	public static int cntq = 0;

	public static int[] fa = new int[MAXN];
	public static int[] siz = new int[MAXN];
	public static int[] maxa = new int[MAXN];
	public static int[] maxb = new int[MAXN];
	public static int[][] rollback = new int[MAXN][5];
	public static int opsize = 0;

	public static boolean[] ans = new boolean[MAXQ];

	public static void sort(int[] idx, int[] v, int l, int r) {
		if (l >= r) return;
		int i = l, j = r, pivot = v[idx[(l + r) >> 1]], tmp;
		while (i <= j) {
			while (v[idx[i]] < pivot) i++;
			while (v[idx[j]] > pivot) j--;
			if (i <= j) {
				tmp = idx[i]; idx[i] = idx[j]; idx[j] = tmp;
				i++; j--;
			}
		}
		sort(idx, v, l, j);
		sort(idx, v, i, r);
	}

	public static void build() {
		for (int i = 1; i <= n; i++) {
			fa[i] = i;
			siz[i] = 1;
			maxa[i] = -1;
			maxb[i] = -1;
		}
	}

	public static int find(int x) {
		while (x != fa[x]) {
			x = fa[x];
		}
		return x;
	}

	public static void union(int x, int y, int a, int b) {
		int fx = find(x);
		int fy = find(y);
		if (siz[fx] < siz[fy]) {
			int tmp = fx; fx = fy; fy = tmp;
		}
		rollback[++opsize][0] = fx;
		rollback[opsize][1] = fy;
		rollback[opsize][2] = siz[fx];
		rollback[opsize][3] = maxa[fx];
		rollback[opsize][4] = maxb[fx];
		if (fx != fy) {
			fa[fy] = fx;
			siz[fx] += siz[fy];
		}
		maxa[fx] = Math.max(Math.max(maxa[fx], maxa[fy]), a);
		maxb[fx] = Math.max(Math.max(maxb[fx], maxb[fy]), b);
	}

	public static void undo() {
		for (int fx, fy; opsize > 0; opsize--) {
			fx = rollback[opsize][0];
			fy = rollback[opsize][1];
			fa[fy] = fy;
			siz[fx] = rollback[opsize][2];
			maxa[fx] = rollback[opsize][3];
			maxb[fx] = rollback[opsize][4];
		}
	}

	public static boolean query(int x, int y, int a, int b) {
		int fx = find(x);
		int fy = find(y);
		return fx == fy && maxa[fx] == a && maxb[fx] == b;
	}

	public static void compute(int l, int r) {
		build();
		cntq = 0;
		for (int i = 1; i <= q; i++) {
			// 保证每条询问只落入其中的一块
			if (ea[arre[l]] <= qa[arrq[i]] && (r + 1 > m || qa[arrq[i]] < ea[arre[r + 1]])) {
				cur[++cntq] = arrq[i];
			}
		}
		if (cntq > 0) {
			// 可以使用额外的数组，每块内部根据b进行排序
			// 每次把新块归并进来，保证 1..l-1 范围根据b有序
			// 可以省掉一个log的复杂度，本题不做这个优化也能通过，索性算了
			sort(arre, eb, 1, l - 1);
			int pos = 1;
			for (int i = 1; i <= cntq; i++) {
				for (int edge = arre[pos]; pos < l && eb[edge] <= qb[cur[i]]; pos++, edge = arre[pos]) {
					union(eu[edge], ev[edge], ea[edge], eb[edge]);
				}
				opsize = 0;
				for (int j = l; j <= r; j++) {
					int edge = arre[j];
					if (ea[edge] <= qa[cur[i]] && eb[edge] <= qb[cur[i]]) {
						union(eu[edge], ev[edge], ea[edge], eb[edge]);
					}
				}
				ans[qid[cur[i]]] = query(qu[cur[i]], qv[cur[i]], qa[cur[i]], qb[cur[i]]);
				undo();
			}
		}
	}

	public static int log2(int n) {
		int ans = 0;
		while ((1 << ans) <= (n >> 1)) {
			ans++;
		}
		return ans;
	}

	public static void prepare() {
		blen = Math.max(1, (int) Math.sqrt(m * log2(n)));
		bnum = (m + blen - 1) / blen;
		for (int i = 1; i <= m; i++) {
			arre[i] = i;
		}
		for (int i = 1; i <= q; i++) {
			arrq[i] = i;
		}
		sort(arre, ea, 1, m);
		sort(arrq, qb, 1, q);
	}

	public static void main(String[] args) throws IOException {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		for (int i = 1; i <= m; i++) {
			eu[i] = in.nextInt();
			ev[i] = in.nextInt();
			ea[i] = in.nextInt();
			eb[i] = in.nextInt();
		}
		q = in.nextInt();
		for (int i = 1; i <= q; i++) {
			qu[i] = in.nextInt();
			qv[i] = in.nextInt();
			qa[i] = in.nextInt();
			qb[i] = in.nextInt();
			qid[i] = i;
		}
		prepare();
		for (int i = 1, l, r; i <= bnum; i++) {
			l = (i - 1) * blen + 1;
			r = Math.min(i * blen, m);
			compute(l, r);
		}
		for (int i = 1; i <= q; i++) {
			if (ans[i]) {
				out.println("Yes");
			} else {
				out.println("No");
			}
		}
		out.flush();
		out.close();
	}

	// 读写工具类
	static class FastReader {
		private final byte[] buffer = new byte[1 << 20];
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
