package class180;

// 世界树，java版
// 一共有n个节点，给定n-1条无向边，所有节点组成一棵树
// 一共有q条查询，每条查询格式如下
// 查询 k a1 a2 ... ak : 给出了k个不同的关键节点，树上每个点都找最近的关键点来管理自己
//                       最近的关键点如果有多个，选择编号最小的关键点
//                       打印每个关键点的管理节点数量
// 1 <= n、q <= 3 * 10^5
// 1 <= 所有查询给出的点的总数 <= 3 * 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P3233
// 提交以下的code，提交时请把类名改成"Main"
// 本题递归函数较多，java版不改成迭代会爆栈，导致无法通过
// 但是这种改动没啥价值，因为和算法无关，纯粹语言歧视，索性不改了
// 想通过用C++实现，本节课Code04_WorldTree2文件就是C++的实现
// 两个版本的逻辑完全一样，C++版本可以通过所有测试

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code04_WorldTree1 {

	public static int MAXN = 300001;
	public static int MAXP = 20;
	public static int INF = 1000000001;
	public static int n, q, k;

	public static int[] headg = new int[MAXN];
	public static int[] nextg = new int[MAXN << 1];
	public static int[] tog = new int[MAXN << 1];
	public static int cntg;

	public static int[] headv = new int[MAXN];
	public static int[] nextv = new int[MAXN];
	public static int[] tov = new int[MAXN];
	public static int cntv;

	public static int[] dep = new int[MAXN];
	public static int[] siz = new int[MAXN];
	public static int[] dfn = new int[MAXN];
	public static int[][] stjump = new int[MAXN][MAXP];
	public static int cntd;

	public static int[] order = new int[MAXN];
	public static int[] arr = new int[MAXN];
	public static boolean[] isKey = new boolean[MAXN];
	public static int[] tmp = new int[MAXN << 1];

	public static int[] mindist = new int[MAXN];
	public static int[] pick = new int[MAXN];
	public static int[] ans = new int[MAXN];

	public static void addEdgeG(int u, int v) {
		nextg[++cntg] = headg[u];
		tog[cntg] = v;
		headg[u] = cntg;
	}

	public static void addEdgeV(int u, int v) {
		nextv[++cntv] = headv[u];
		tov[cntv] = v;
		headv[u] = cntv;
	}

	// nums中的数，根据dfn的大小排序，手撸双指针快排
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

	public static void dfs(int u, int fa) {
		dep[u] = dep[fa] + 1;
		siz[u] = 1;
		dfn[u] = ++cntd;
		stjump[u][0] = fa;
		for (int p = 1; p < MAXP; p++) {
			stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
		}
		for (int e = headg[u]; e > 0; e = nextg[e]) {
			int v = tog[e];
			if (v != fa) {
				dfs(v, u);
				siz[u] += siz[v];
			}
		}
	}

	public static int getLca(int a, int b) {
		if (dep[a] < dep[b]) {
			int tmp = a;
			a = b;
			b = tmp;
		}
		for (int p = MAXP - 1; p >= 0; p--) {
			if (dep[stjump[a][p]] >= dep[b]) {
				a = stjump[a][p];
			}
		}
		if (a == b) {
			return a;
		}
		for (int p = MAXP - 1; p >= 0; p--) {
			if (stjump[a][p] != stjump[b][p]) {
				a = stjump[a][p];
				b = stjump[b][p];
			}
		}
		return stjump[a][0];
	}

	public static int buildVirtualTree() {
		sortByDfn(arr, 1, k);
		// 一定要加入1号点
		// 因为题目问的是所有节点的归属问题
		int len = 0;
		tmp[++len] = 1;
		for (int i = 1; i < k; i++) {
			tmp[++len] = arr[i];
			tmp[++len] = getLca(arr[i], arr[i + 1]);
		}
		tmp[++len] = arr[k];
		sortByDfn(tmp, 1, len);
		int unique = 1;
		for (int i = 2; i <= len; i++) {
			if (tmp[unique] != tmp[i]) {
				tmp[++unique] = tmp[i];
			}
		}
		cntv = 0;
		for (int i = 1; i <= unique; i++) {
			headv[tmp[i]] = 0;
		}
		for (int i = 1; i < unique; i++) {
			addEdgeV(getLca(tmp[i], tmp[i + 1]), tmp[i + 1]);
		}
		return tmp[1];
	}

	public static void dp1(int u) {
		mindist[u] = INF;
		for (int e = headv[u]; e > 0; e = nextv[e]) {
			int v = tov[e];
			dp1(v);
			int dis = dep[v] - dep[u];
			if (mindist[u] > mindist[v] + dis) {
				mindist[u] = mindist[v] + dis;
				pick[u] = pick[v];
			} else if (mindist[u] == mindist[v] + dis) {
				pick[u] = Math.min(pick[u], pick[v]);
			}
		}
		if (isKey[u]) {
			mindist[u] = 0;
			pick[u] = u;
		}
	}

	public static void calc(int x, int y) {
		int b = y;
		for (int p = MAXP - 1; p >= 0; p--) {
			int l = dep[y] - dep[stjump[b][p]] + mindist[y];
			int r = dep[stjump[b][p]] - dep[x] + mindist[x];
			if (dep[stjump[b][p]] > dep[x] && (l < r || (l == r && pick[y] < pick[x]))) {
				b = stjump[b][p];
			}
		}
		ans[pick[y]] += siz[b] - siz[y];
		ans[pick[x]] -= siz[b];
	}

	public static void dp2(int u) {
		for (int e = headv[u]; e > 0; e = nextv[e]) {
			int v = tov[e];
			int dis = dep[v] - dep[u];
			if (mindist[v] > mindist[u] + dis) {
				mindist[v] = mindist[u] + dis;
				pick[v] = pick[u];
			} else if (mindist[v] == mindist[u] + dis) {
				pick[v] = Math.min(pick[v], pick[u]);
			}
			calc(u, v);
			dp2(v);
		}
		ans[pick[u]] += siz[u];
	}

	public static void compute() {
		for (int i = 1; i <= k; i++) {
			arr[i] = order[i];
		}
		for (int i = 1; i <= k; i++) {
			isKey[arr[i]] = true;
			ans[arr[i]] = 0;
		}
		int tree = buildVirtualTree();
		dp1(tree);
		dp2(tree);
		for (int i = 1; i <= k; i++) {
			isKey[arr[i]] = false;
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		for (int i = 1, u, v; i < n; i++) {
			u = in.nextInt();
			v = in.nextInt();
			addEdgeG(u, v);
			addEdgeG(v, u);
		}
		dfs(1, 0);
		q = in.nextInt();
		for (int t = 1; t <= q; t++) {
			k = in.nextInt();
			for (int i = 1; i <= k; i++) {
				order[i] = in.nextInt();
			}
			compute();
			for (int i = 1; i <= k; i++) {
				out.print(ans[order[i]] + " ");
			}
			out.println();
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
