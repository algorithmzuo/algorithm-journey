package class180;

// 虚树模版题，java版
// 一共有n个节点，给定n-1条边，所有节点组成一棵树
// 一共有q条查询，每条查询格式如下
// 查询 k a1 a2 ... ak : 给出了k个不同的重要点，其他点是非重要点
//                       你可以攻占非重要点，被攻占的点无法通行
//                       要让重要点两两之间不再连通，打印至少需要攻占几个非重要点
//                       如果攻占非重要点无法达成目标，打印-1
// 1 <= n、q <= 10^5
// 1 <= 所有查询给出的点的总数 <= 10^5
// 测试链接 : https://www.luogu.com.cn/problem/CF613D
// 测试链接 : https://codeforces.com/problemset/problem/613/D
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code01_VirtualTree1 {

	public static int MAXN = 100001;
	public static int MAXP = 20;
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
	public static int[] dfn = new int[MAXN];
	public static int[][] stjump = new int[MAXN][MAXP];
	public static int cntd;

	public static int[] arr = new int[MAXN];
	public static boolean[] isKey = new boolean[MAXN];

	// 第一种建树方式
	public static int[] tmp = new int[MAXN << 1];
	// 第二种建树方式
	public static int[] stk = new int[MAXN];

	public static int[] siz = new int[MAXN];
	public static int[] cost = new int[MAXN];

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
		dfn[u] = ++cntd;
		stjump[u][0] = fa;
		for (int p = 1; p < MAXP; p++) {
			stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
		}
		for (int e = headg[u]; e > 0; e = nextg[e]) {
			if (tog[e] != fa) {
				dfs(tog[e], u);
			}
		}
	}

	public static int getLca(int a, int b) {
		if (dep[a] < dep[b]) {
			int tmp = a; a = b; b = tmp;
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

	// 二次排序 + LCA连边的方式建立虚树
	public static int buildVirtualTree1() {
		sortByDfn(arr, 1, k);
		int len = 0;
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

	// 单调栈的方式建立虚树
	public static int buildVirtualTree2() {
		sortByDfn(arr, 1, k);
		cntv = 0;
		headv[arr[1]] = 0;
		int top = 0;
		stk[++top] = arr[1];
		for (int i = 2; i <= k; i++) {
			int x = arr[i];
			int y = stk[top];
			int lca = getLca(x, y);
			while (top > 1 && dfn[stk[top - 1]] >= dfn[lca]) {
				addEdgeV(stk[top - 1], stk[top]);
				top--;
			}
			if (lca != stk[top]) {
				headv[lca] = 0;
				addEdgeV(lca, stk[top]);
				top--;
				stk[++top] = lca;
			}
			headv[x] = 0;
			stk[++top] = x;
		}
		while (top > 1) {
			addEdgeV(stk[top - 1], stk[top]);
			top--;
		}
		return stk[1];
	}

	public static void dp(int u) {
		cost[u] = siz[u] = 0;
		for (int e = headv[u], v; e > 0; e = nextv[e]) {
			v = tov[e];
			dp(v);
			cost[u] += cost[v];
			siz[u] += siz[v];
		}
		if (isKey[u]) {
			cost[u] += siz[u];
			siz[u] = 1;
		} else if (siz[u] > 1) {
			cost[u]++;
			siz[u] = 0;
		}
	}

	public static int compute() {
		for (int i = 1; i <= k; i++) {
			isKey[arr[i]] = true;
		}
		boolean check = true;
		for (int i = 1; i <= k; i++) {
			if (isKey[stjump[arr[i]][0]]) {
				check = false;
				break;
			}
		}
		int ans = -1;
		if (check) {
			int tree = buildVirtualTree1();
			// int tree = buildVirtualTree2();
			dp(tree);
			ans = cost[tree];
		}
		for (int i = 1; i <= k; i++) {
			isKey[arr[i]] = false;
		}
		return ans;
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
				arr[i] = in.nextInt();
			}
			out.println(compute());
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
