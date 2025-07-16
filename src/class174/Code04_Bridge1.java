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

	// edge里是所有边的编号
	// change表示边的分类
	// curw表示边最新的权值
	public static int[] edge = new int[MAXM];
	public static boolean[] change = new boolean[MAXM];
	public static int[] curw = new int[MAXM];

	// edge里是所有操作的编号
	// query里是当前操作块里查询操作的编号
	// update里是当前操作块里修改操作的编号
	public static int[] operate = new int[MAXQ];
	public static int[] query = new int[MAXQ];
	public static int[] update = new int[MAXQ];

	// 可撤销并查集
	public static int[] fa = new int[MAXN];
	public static int[] siz = new int[MAXN];
	public static int[][] rollback = new int[MAXM][2];
	public static int opsize = 0;

	// 归并的辅助数组
	public static int[] arr1 = new int[MAXM];
	public static int[] arr2 = new int[MAXM];

	// 所有查询的答案
	public static int[] ans = new int[MAXQ];

	// idx[l..r]都是编号，编号根据val[编号]的值从大到小排序，手写双指针快排
	public static void sort(int[] idx, int[] val, int l, int r) {
		if (l >= r) return;
		int i = l, j = r, pivot = val[idx[(l + r) >> 1]], tmp;
		while (i <= j) {
			while (val[idx[i]] > pivot) i++;
			while (val[idx[j]] < pivot) j--;
			if (i <= j) {
				tmp = idx[i]; idx[i] = idx[j]; idx[j] = tmp;
				i++; j--;
			}
		}
		sort(idx, val, l, j);
		sort(idx, val, i, r);
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

	public static void merge() {
		int siz1 = 0, siz2 = 0;
		for (int i = 1; i <= m; i++) {
			if (change[edge[i]]) {
				arr1[++siz1] = edge[i];
			} else {
				arr2[++siz2] = edge[i];
			}
		}
		sort(arr1, w, 1, siz1);
		int i = 0, p1 = 1, p2 = 1;
		while (p1 <= siz1 && p2 <= siz2) {
			edge[++i] = w[arr1[p1]] >= w[arr2[p2]] ? arr1[p1++] : arr2[p2++];
		}
		while (p1 <= siz1) {
			edge[++i] = arr1[p1++];
		}
		while (p2 <= siz2) {
			edge[++i] = arr2[p2++];
		}
	}

	// 当前操作编号[l..r]，之前的所有修改操作都已生效
	// 所有边的编号edge[1..m]，按照边权从大到小排序
	// 处理当前操作块的所有操作
	public static void compute(int l, int r) {
		build(); // 重建并查集，目前没有任何联通性
		Arrays.fill(change, false); // 清空边的修改标记
		int cntu = 0, cntq = 0;
		for (int i = l; i <= r; i++) {
			if (op[operate[i]] == 1) { // 修改类型的操作
				change[eid[operate[i]]] = true;
				update[++cntu] = operate[i];
			} else { // 查询类型的操作
				query[++cntq] = operate[i];
			}
		}
		// 查询操作的所有编号，根据车重从大到小排序
		// 然后依次处理所有查询
		sort(query, car, 1, cntq);
		for (int i = 1, j = 1; i <= cntq; i++) {
			// 边权 >= 当前车重 的边全部连上，注意这是不回退的
			while (j <= m && w[edge[j]] >= car[query[i]]) {
				if (!change[edge[j]]) {
					union(u[edge[j]], v[edge[j]]);
				}
				j++;
			}
			// 注意需要用可撤销并查集，撤销会改值的边
			opsize = 0;
			// 会改值的边，边权先继承改之前的值
			for (int k = 1; k <= cntu; k++) {
				curw[eid[update[k]]] = w[eid[update[k]]];
			}
			// 修改操作的时序 < 当前查询操作的时序，那么相关边的边权改成最新值
			for (int k = 1; k <= cntu && update[k] < query[i]; k++) {
				curw[eid[update[k]]] = tow[update[k]];
			}
			// 会改值的边，其中 边权 >= 当前车重 的边全部连上
			for (int k = 1; k <= cntu; k++) {
				if (curw[eid[update[k]]] >= car[query[i]]) {
					union(u[eid[update[k]]], v[eid[update[k]]]);
				}
			}
			// 并查集修改完毕，查询答案
			ans[query[i]] = siz[find(nid[query[i]])];
			// 并查集的撤销
			undo();
		}
		// 所有会改值的边，边权修改，因为即将去下个操作块
		for (int i = 1; i <= cntu; i++) {
			w[eid[update[i]]] = tow[update[i]];
		}
		// 没改值的边和改了值的边，根据边权从大到小合并
		merge();
	}

	public static void prepare() {
		int log2n = 0;
		while ((1 << log2n) <= (n >> 1)) {
			log2n++;
		}
		blen = Math.max(1, (int) Math.sqrt(q * log2n));
		bnum = (q + blen - 1) / blen;
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
			edge[i] = i;
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
			operate[i] = i;
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
