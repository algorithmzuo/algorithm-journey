package class174;

// 桥梁，java版
// 测试链接 : https://www.luogu.com.cn/problem/P5443
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

public class Code05_Bridge1 {

	public static int MAXN = 100001;
	public static int n, m, q;
	public static int blen, bnum;
	public static int[] u = new int[MAXN];
	public static int[] v = new int[MAXN];
	public static int[] w = new int[MAXN];

	public static int[] op = new int[MAXN];
	public static int[] a = new int[MAXN];
	public static int[] b = new int[MAXN];

	public static int[] eid = new int[MAXN];
	public static int[] change = new int[MAXN];
	public static int[] unchange = new int[MAXN];

	public static int[] qid = new int[MAXN];
	public static int[] update = new int[MAXN];
	public static int[] query = new int[MAXN];

	public static int[] fa = new int[MAXN];
	public static int[] siz = new int[MAXN];
	public static int[][] rollback = new int[MAXN][2];
	public static int opsize = 0;

	public static boolean[] vis = new boolean[MAXN];
	public static int[] curw = new int[MAXN];
	public static int[] ans = new int[MAXN];

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
			eid[++i] = w[change[l1]] >= w[unchange[l2]] ? change[l1++] : unchange[l2++];
		}
		while (l1 <= r1) {
			eid[++i] = change[l1++];
		}
		while (l2 <= r2) {
			eid[++i] = unchange[l2++];
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
			int tmp = fx;
			fx = fy;
			fy = tmp;
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
			if (op[qid[i]] == 1) {
				vis[a[qid[i]]] = true;
				update[++cntu] = qid[i];
			} else {
				query[++cntq] = qid[i];
			}
		}
		sort(query, b, 1, cntq);
		int k = 1;
		for (int i = 1; i <= cntq; i++) {
			for (; k <= m && w[eid[k]] >= b[query[i]]; k++) {
				if (!vis[eid[k]]) {
					union(u[eid[k]], v[eid[k]]);
				}
			}
			opsize = 0;
			for (int j = 1; j <= cntu; j++) {
				curw[a[update[j]]] = w[a[update[j]]];
			}
			for (int j = 1; j <= cntu; j++) {
				if (update[j] < query[i]) {
					curw[a[update[j]]] = b[update[j]];
				}
			}
			for (int j = 1; j <= cntu; j++) {
				if (curw[a[update[j]]] >= b[query[i]]) {
					union(u[a[update[j]]], v[a[update[j]]]);
				}
			}
			ans[query[i]] = siz[find(a[query[i]])];
			undo();
		}
		for (int i = 1; i <= cntu; i++) {
			w[a[update[i]]] = b[update[i]];
		}
		int siz1 = 0, siz2 = 0;
		for (int i = 1; i <= m; i++) {
			if (vis[eid[i]]) {
				change[++siz1] = eid[i];
			} else {
				unchange[++siz2] = eid[i];
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
			eid[i] = i;
		}
		for (int i = 1; i <= q; i++) {
			qid[i] = i;
		}
		sort(eid, w, 1, m);
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
			a[i] = in.nextInt();
			b[i] = in.nextInt();
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
