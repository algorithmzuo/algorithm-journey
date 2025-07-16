package class174;

// 桥梁，java版
// 有n个点组成的无向图，依次给出m条无向边
// u v w : u到v的边，边权为w，边权同时代表限重
// 如果开车从边上经过，车的重量 <= 边的限重，车才能走过这条边
// 接下来有q条操作，每条操作的格式如下
// 操作 1 eid tow : 编号为eid的边，边权变成tow
// 操作 2 nid car : 编号为nid的点出发，车重为car，查询能到达几个不同的点
// 1 <= n <= 5 * 10^4    0 <= m <= 10^5
// 1 <= q <= 10^5        1 <= 其他数据 <= 10^9
// 测试链接 : https://www.luogu.com.cn/problem/P5443
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

public class Code04_Bridge1 {

	public static int MAXN = 50001;
	public static int MAXM = 100001;
	public static int MAXQ = 100001;
	public static int n, m, q;
	public static int blen, bnum;

	public static int[] u = new int[MAXM];
	public static int[] v = new int[MAXM];
	public static int[] w = new int[MAXM];

	public static int[] op = new int[MAXQ];
	public static int[] eid = new int[MAXQ];
	public static int[] tow = new int[MAXQ];
	public static int[] nid = new int[MAXQ];
	public static int[] car = new int[MAXQ];

	public static int[] fa = new int[MAXN];
	public static int[] siz = new int[MAXN];
	public static int[][] rollback = new int[MAXM][2];
	public static int opsize = 0;

	public static int[] edge = new int[MAXM];
	public static int[] change = new int[MAXM];
	public static int[] unchange = new int[MAXM];

	public static int[] operate = new int[MAXQ];
	public static int[] update = new int[MAXQ];
	public static int[] query = new int[MAXQ];

	public static boolean[] vis = new boolean[MAXM];
	public static int[] curw = new int[MAXM];

	public static int[] ans = new int[MAXQ];

	// idx[l..r]由序号组成，v[序号]的值越大，序号越靠前，手写双指针快排
	public static void sort(int[] idx, int[] v, int l, int r) {
		if (l >= r) return;
		int i = l, j = r, pivot = v[idx[(l + r) >> 1]], tmp;
		while (i <= j) {
			while (v[idx[i]] > pivot) i++;
			while (v[idx[j]] < pivot) j--;
			if (i <= j) {
				tmp = idx[i]; idx[i] = idx[j]; idx[j] = tmp;
				i++; j--;
			}
		}
		sort(idx, v, l, j);
		sort(idx, v, i, r);
	}

	// change[l1..r1]都是边的序号，已经有序了，w[序号]的值越大，序号越靠前
	// unchange[l2..r2]都是边的序号，已经有序了，w[序号]的值越大，序号越靠前
	// 把两个数组中的序号，根据w[序号]的值，归并到eid中
	public static void merge(int l1, int r1, int l2, int r2) {
		int i = 0;
		while (l1 <= r1 && l2 <= r2) {
			edge[++i] = w[change[l1]] >= w[unchange[l2]] ? change[l1++] : unchange[l2++];
		}
		while (l1 <= r1) {
			edge[++i] = change[l1++];
		}
		while (l2 <= r2) {
			edge[++i] = unchange[l2++];
		}
	}

	public static void build() {
		for (int i = 1; i <= n; i++) {
			fa[i] = i;
			siz[i] = 1;
		}
	}

	public static int find(int x) {
		while (x != fa[x]) {
			x = fa[x];
		}
		return x;
	}

	public static void union(int x, int y) {
		int fx = find(x);
		int fy = find(y);
		if (fx == fy) {
			return;
		}
		if (siz[fx] < siz[fy]) {
			int tmp = fx; fx = fy; fy = tmp;
		}
		fa[fy] = fx;
		siz[fx] += siz[fy];
		rollback[++opsize][0] = fx;
		rollback[opsize][1] = fy;
	}

	public static void undo() {
		for (int fx, fy; opsize > 0; opsize--) {
			fx = rollback[opsize][0];
			fy = rollback[opsize][1];
			fa[fy] = fy;
			siz[fx] -= siz[fy];
		}
	}

	public static void compute(int l, int r) {
		build();
		Arrays.fill(vis, false);
		int cntu = 0, cntq = 0;
		for (int i = l; i <= r; i++) {
			if (op[operate[i]] == 1) {
				vis[eid[operate[i]]] = true;
				update[++cntu] = operate[i];
			} else {
				query[++cntq] = operate[i];
			}
		}
		sort(query, car, 1, cntq);
		for (int i = 1, j = 1; i <= cntq; i++) {
			while (j <= m && w[edge[j]] >= car[query[i]]) {
				if (!vis[edge[j]]) {
					union(u[edge[j]], v[edge[j]]);
				}
				j++;
			}
			opsize = 0;
			for (int k = 1; k <= cntu; k++) {
				curw[eid[update[k]]] = w[eid[update[k]]];
			}
			for (int k = 1; k <= cntu; k++) {
				if (update[k] < query[i]) {
					curw[eid[update[k]]] = tow[update[k]];
				}
			}
			for (int k = 1; k <= cntu; k++) {
				if (curw[eid[update[k]]] >= car[query[i]]) {
					union(u[eid[update[k]]], v[eid[update[k]]]);
				}
			}
			ans[query[i]] = siz[find(nid[query[i]])];
			undo();
		}
		for (int i = 1; i <= cntu; i++) {
			w[eid[update[i]]] = tow[update[i]];
		}
		int siz1 = 0, siz2 = 0;
		for (int i = 1; i <= m; i++) {
			if (vis[edge[i]]) {
				change[++siz1] = edge[i];
			} else {
				unchange[++siz2] = edge[i];
			}
		}
		sort(change, w, 1, siz1);
		merge(1, siz1, 1, siz2);
	}

	public static int log2(int n) {
		int ans = 0;
		while ((1 << ans) <= (n >> 1)) {
			ans++;
		}
		return ans;
	}

	public static void prepare() {
		blen = Math.max(1, (int) Math.sqrt(q * log2(n)));
		bnum = (q + blen - 1) / blen;
		for (int i = 1; i <= m; i++) {
			edge[i] = i;
		}
		for (int i = 1; i <= q; i++) {
			operate[i] = i;
		}
		sort(edge, w, 1, m);
	}

	public static void main(String[] args) throws IOException {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		for (int i = 1; i <= m; i++) {
			u[i] = in.nextInt();
			v[i] = in.nextInt();
			w[i] = in.nextInt();
		}
		q = in.nextInt();
		for (int i = 1; i <= q; i++) {
			op[i] = in.nextInt();
			if (op[i] == 1) {
				eid[i] = in.nextInt();
				tow[i] = in.nextInt();
			} else {
				nid[i] = in.nextInt();
				car[i] = in.nextInt();
			}
		}
		prepare();
		for (int i = 1, l, r; i <= bnum; i++) {
			l = (i - 1) * blen + 1;
			r = Math.min(i * blen, q);
			compute(l, r);
		}
		for (int i = 1; i <= q; i++) {
			if (ans[i] > 0) {
				out.println(ans[i]);
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
