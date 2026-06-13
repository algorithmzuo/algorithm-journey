package class200;

// 最短路最大值，java版
// 给定n个点、m条边的仙人掌图，每条边有边权，没有自环，可能有重边
// 一共q条查询，格式 k a1 a2 .. ak 含义如下
// 给定的k个点中，任意选两个点，可以选择相同点
// 打印它们之间最短路的最大值是多少
// 1 <= n、查询中k的总和 <= 3 * 10^5
// 测试链接 : https://uoj.ac/problem/87
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code07_MaximumOfShortestPath1 {

	public static int MAXN = 600001;
	public static int n, m, q, k, cntn;

	// 原图
	public static int[] head1 = new int[MAXN];
	public static int[] next1 = new int[MAXN << 1];
	public static int[] to1 = new int[MAXN << 1];
	public static int[] weight1 = new int[MAXN << 1];
	public static int cnt1;

	// 圆方树
	public static int[] head2 = new int[MAXN];
	public static int[] next2 = new int[MAXN];
	public static int[] to2 = new int[MAXN];
	public static long[] weight2 = new long[MAXN];
	public static int cnt2;

	// 虚树
	public static int[] head3 = new int[MAXN];
	public static int[] next3 = new int[MAXN];
	public static int[] to3 = new int[MAXN];
	public static int cnt3;

	// dfn数组，tarjan和树剖都要使用
	public static int[] dfn = new int[MAXN];
	public static int[] low = new int[MAXN];
	public static int cntd;
	public static int[] sta = new int[MAXN];
	public static int stasiz;

	// 环的信息
	public static long[] fromWeight = new long[MAXN];
	public static long[] cycleLen = new long[MAXN];
	public static long[] cycleSum = new long[MAXN];

	// 树链剖分
	public static int[] fa = new int[MAXN];
	public static int[] dep = new int[MAXN];
	public static int[] siz = new int[MAXN];
	public static long[] len = new long[MAXN];
	public static int[] son = new int[MAXN];
	public static int[] top = new int[MAXN];

	// 批量节点
	public static int[] arr = new int[MAXN];
	public static int[] tmp = new int[MAXN];

	// dist[u] : 以u为头的树，u到任意点的最短路距离的最大值
	public static long[] dist = new long[MAXN];
	public static long diameter;

	// 单调队列
	public static int[] idx = new int[MAXN];
	public static long[] pre = new long[MAXN];
	public static long[] val = new long[MAXN];
	public static int[] que = new int[MAXN];

	public static void addEdge1(int u, int v, int w) {
		next1[++cnt1] = head1[u];
		to1[cnt1] = v;
		weight1[cnt1] = w;
		head1[u] = cnt1;
	}

	public static void addEdge2(int u, int v, long w) {
		next2[++cnt2] = head2[u];
		to2[cnt2] = v;
		weight2[cnt2] = w;
		head2[u] = cnt2;
	}

	public static void addEdge3(int u, int v) {
		next3[++cnt3] = head3[u];
		to3[cnt3] = v;
		head3[u] = cnt3;
	}

	public static void sortByDfn(int[] nums, int l, int r) {
		if (l >= r) return;
		int i = l, j = r;
		int pivot = nums[(l + r) >> 1];
		while (i <= j) {
			while (dfn[nums[i]] < dfn[pivot]) i++;
			while (dfn[nums[j]] > dfn[pivot]) j--;
			if (i <= j) {
				int tmp = nums[i]; nums[i] = nums[j]; nums[j] = tmp;
				i++; j--;
			}
		}
		sortByDfn(nums, l, j);
		sortByDfn(nums, i, r);
	}

	public static void sortByLen(int[] nums, int l, int r) {
		if (l >= r) return;
		int i = l, j = r;
		int pivot = nums[(l + r) >> 1];
		while (i <= j) {
			while (cycleLen[nums[i]] < cycleLen[pivot]) i++;
			while (cycleLen[nums[j]] > cycleLen[pivot]) j--;
			if (i <= j) {
				int tmp = nums[i]; nums[i] = nums[j]; nums[j] = tmp;
				i++; j--;
			}
		}
		sortByLen(nums, l, j);
		sortByLen(nums, i, r);
	}

	public static void cycleLink(int u, int v) {
		cntn++;
		cycleSum[cntn] = fromWeight[u];
		addEdge2(u, cntn, 0);
		int tmp = stasiz;
		int pop;
		do {
			pop = sta[tmp--];
			cycleLen[pop] = cycleSum[cntn];
			cycleSum[cntn] += fromWeight[pop];
		} while (pop != v);
		do {
			pop = sta[stasiz--];
			addEdge2(cntn, pop, Math.min(cycleLen[pop], cycleSum[cntn] - cycleLen[pop]));
		} while (pop != v);
	}

	public static void tarjan(int u, int preEdge) {
		dfn[u] = low[u] = ++cntd;
		sta[++stasiz] = u;
		for (int e = head1[u]; e > 0; e = next1[e]) {
			if ((e ^ 1) == preEdge) {
				continue;
			}
			int v = to1[e];
			long w = weight1[e];
			if (dfn[v] == 0) {
				tarjan(v, e);
				fromWeight[v] = w;
				if (low[v] < dfn[u]) {
					low[u] = Math.min(low[u], low[v]);
				} else if (low[v] > dfn[u]) {
					stasiz--;
					addEdge2(u, v, w);
				} else {
					cycleLink(u, v);
				}
			} else if (dfn[v] < dfn[u]) {
				fromWeight[v] = w;
				low[u] = Math.min(low[u], dfn[v]);
			}
		}
	}

	public static void dfs1(int u, int f, long l) {
		fa[u] = f;
		dep[u] = dep[f] + 1;
		siz[u] = 1;
		len[u] = l;
		for (int e = head2[u], v; e > 0; e = next2[e]) {
			v = to2[e];
			if (v != f) {
				dfs1(v, u, len[u] + weight2[e]);
				siz[u] += siz[v];
				if (son[u] == 0 || siz[son[u]] < siz[v]) {
					son[u] = v;
				}
			}
		}
	}

	public static void dfs2(int u, int t) {
		top[u] = t;
		dfn[u] = ++cntd;
		if (son[u] != 0) {
			dfs2(son[u], t);
		}
		for (int e = head2[u]; e > 0; e = next2[e]) {
			int v = to2[e];
			if (v != fa[u] && v != son[u]) {
				dfs2(v, v);
			}
		}
	}

	public static int lca(int a, int b) {
		while (top[a] != top[b]) {
			if (dep[top[a]] <= dep[top[b]]) {
				b = fa[top[b]];
			} else {
				a = fa[top[a]];
			}
		}
		return dep[a] <= dep[b] ? a : b;
	}

	public static int find(int x, int square) {
		int pre = 0;
		while (top[x] != top[square]) {
			pre = top[x];
			x = fa[pre];
		}
		return x == square ? pre : son[square];
	}

	// 虚树的建立方式，二次排序 + 相邻LCA连边
	public static int buildVirtualTree() {
		sortByDfn(arr, 1, k);
		int len = 0;
		for (int i = 1; i < k; i++) {
			tmp[++len] = arr[i];
			tmp[++len] = lca(arr[i], arr[i + 1]);
		}
		tmp[++len] = arr[k];
		sortByDfn(tmp, 1, len);
		int unique = 1;
		for (int i = 2; i <= len; i++) {
			if (tmp[unique] != tmp[i]) {
				tmp[++unique] = tmp[i];
			}
		}
		cnt3 = 0;
		for (int i = 1; i <= unique; i++) {
			head3[tmp[i]] = 0;
		}
		for (int i = 1; i < unique; i++) {
			addEdge3(lca(tmp[i], tmp[i + 1]), tmp[i + 1]);
		}
		return tmp[1];
	}

	public static void computeOnCycle(int u, int siz) {
		sortByLen(idx, 1, siz);
		for (int i = 1; i <= siz; i++) {
			pre[i] = cycleLen[idx[i]];
			pre[i + siz] = pre[i] + cycleSum[u];
			val[i] = dist[idx[i]];
			val[i + siz] = val[i];
		}
		int l = 1;
		int r = 0;
		for (int i = 1; i <= siz * 2; i++) {
			while (l <= r && (pre[i] - pre[que[l]]) * 2 > cycleSum[u]) {
				l++;
			}
			if (l <= r) {
				diameter = Math.max(diameter, val[que[l]] - pre[que[l]] + val[i] + pre[i]);
			}
			while (l <= r && val[que[r]] - pre[que[r]] <= val[i] - pre[i]) {
				r--;
			}
			que[++r] = i;
		}
	}

	public static void dpOnTree(int u) {
		dist[u] = 0;
		if (u <= n) {
			for (int e = head3[u]; e > 0; e = next3[e]) {
				int v = to3[e];
				dpOnTree(v);
				diameter = Math.max(diameter, dist[u] + dist[v] + len[v] - len[u]);
				dist[u] = Math.max(dist[u], dist[v] + len[v] - len[u]);
			}
		} else {
			for (int e = head3[u]; e > 0; e = next3[e]) {
				int v = to3[e];
				dpOnTree(v);
			}
			// 注意，下面的for循环，不能和上面的for循环合并处理
			// 因为idx是全局数组，此时u是方点，会收集环上的相关节点
			// 如果边递归边收集idx，那么当前已经写入的idx[1..siz]
			// 可能会被孩子的递归过程覆盖掉，导致idx收集脏数据
			// 所以先执行所有孩子的递归，再收集环上节点
			int siz = 0;
			for (int e = head3[u]; e > 0; e = next3[e]) {
				int v = to3[e];
				int f = find(v, u);
				dist[f] = dist[v] + len[v] - len[f];
				idx[++siz] = f;
				dist[u] = Math.max(dist[u], dist[v] + len[v] - len[u]);
			}
			computeOnCycle(u, siz);
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		cntn = n;
		cnt1 = 1;
		for (int i = 1, u, v, w; i <= m; i++) {
			u = in.nextInt();
			v = in.nextInt();
			w = in.nextInt();
			addEdge1(u, v, w);
			addEdge1(v, u, w);
		}
		tarjan(1, 0);
		cntd = 0;
		dfs1(1, 0, 0);
		dfs2(1, 1);
		q = in.nextInt();
		for (int i = 1; i <= q; i++) {
			k = in.nextInt();
			for (int j = 1; j <= k; j++) {
				arr[j] = in.nextInt();
			}
			diameter = 0;
			int tree = buildVirtualTree();
			dpOnTree(tree);
			out.println(diameter);
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