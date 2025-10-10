package class180;

// 大工程，java版
// 测试链接 : https://www.luogu.com.cn/problem/P4103
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code02_BigProject1 {

	public static int MAXN = 1000001;
	public static int MAXP = 21;
	public static long INF = 1L << 60;
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
	public static int[] tmp = new int[MAXN << 1];
	public static int[] stack = new int[MAXN];

	public static int[] siz = new int[MAXN];
	public static long[] dp = new long[MAXN];
	public static long[] minv = new long[MAXN];
	public static long[] maxv = new long[MAXN];
	public static long costSum, costMin, costMax;

	// dfs过程和dp过程，C++同学可以使用递归版
	// 但是java同学必须改迭代版否则会爆栈
	// 不会改迭代版，去看讲解118，详解了从递归版改迭代版
	// ufe不仅用于dfs改迭代，也用于dp改迭代
	public static int[][] ufe = new int[MAXN][3];

	public static int stacksize, u, f, e;

	public static void push(int u, int f, int e) {
		ufe[stacksize][0] = u;
		ufe[stacksize][1] = f;
		ufe[stacksize][2] = e;
		stacksize++;
	}

	public static void pop() {
		--stacksize;
		u = ufe[stacksize][0];
		f = ufe[stacksize][1];
		e = ufe[stacksize][2];
	}

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

	// dfs递归版，java会爆栈，C++可以通过
	public static void dfs1(int u, int fa) {
		dep[u] = dep[fa] + 1;
		dfn[u] = ++cntd;
		stjump[u][0] = fa;
		for (int p = 1; p < MAXP; p++) {
			stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
		}
		for (int e = headg[u]; e > 0; e = nextg[e]) {
			if (tog[e] != fa) {
				dfs1(tog[e], u);
			}
		}
	}

	// dfs1的迭代版
	public static void dfs2() {
		stacksize = 0;
		push(1, 0, -1);
		while (stacksize > 0) {
			pop();
			if (e == -1) {
				dep[u] = dep[f] + 1;
				dfn[u] = ++cntd;
				stjump[u][0] = f;
				for (int p = 1; p < MAXP; p++) {
					stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
				}
				e = headg[u];
			} else {
				e = nextg[e];
			}
			if (e != 0) {
				push(u, f, e);
				if (tog[e] != f) {
					push(tog[e], u, -1);
				}
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
		stack[++top] = arr[1];
		for (int i = 2; i <= k; i++) {
			int x = arr[i];
			int y = stack[top];
			int lca = getLca(x, y);
			while (top > 1 && dfn[stack[top - 1]] >= dfn[lca]) {
				addEdgeV(stack[top - 1], stack[top]);
				top--;
			}
			if (lca != stack[top]) {
				headv[lca] = 0;
				addEdgeV(lca, stack[top]);
				top--;
				stack[++top] = lca;
			}
			headv[x] = 0;
			stack[++top] = x;
		}
		while (top > 1) {
			addEdgeV(stack[top - 1], stack[top]);
			top--;
		}
		return stack[1];
	}

	// dp递归版，java会爆栈，C++可以通过
	public static void dpOnTree1(int u) {
		siz[u] = isKey[u] ? 1 : 0;
		dp[u] = 0;
		if (isKey[u]) {
			maxv[u] = minv[u] = 0;
		} else {
			minv[u] = INF;
			maxv[u] = -INF;
		}
		for (int e = headv[u]; e > 0; e = nextv[e]) {
			dpOnTree1(tov[e]);
		}
		for (int ei = headv[u]; ei > 0; ei = nextv[ei]) {
			int v = tov[ei];
			long len = dep[v] - dep[u];
			costSum += (dp[u] + 1L * siz[u] * len) * siz[v] + dp[v] * siz[u];
			siz[u] += siz[v];
			dp[u] += dp[v] + len * siz[v];
			costMin = Math.min(costMin, minv[u] + minv[v] + len);
			costMax = Math.max(costMax, maxv[u] + maxv[v] + len);
			minv[u] = Math.min(minv[u], minv[v] + len);
			maxv[u] = Math.max(maxv[u], maxv[v] + len);
		}
	}

	// dpOnTree1的迭代版
	public static void dpOnTree2(int tree) {
		stacksize = 0;
		push(tree, 0, -1);
		while (stacksize > 0) {
			pop();
			if (e == -1) {
				siz[u] = isKey[u] ? 1 : 0;
				dp[u] = 0;
				if (isKey[u]) {
					maxv[u] = minv[u] = 0;
				} else {
					minv[u] = INF;
					maxv[u] = -INF;
				}
				e = headv[u];
			} else {
				e = nextv[e];
			}
			if (e != 0) {
				push(u, 0, e);
				push(tov[e], 0, -1);
			} else {
				for (int ei = headv[u]; ei > 0; ei = nextv[ei]) {
					int v = tov[ei];
					long len = dep[v] - dep[u];
					costSum += (dp[u] + 1L * siz[u] * len) * siz[v] + dp[v] * siz[u];
					siz[u] += siz[v];
					dp[u] += dp[v] + len * siz[v];
					costMin = Math.min(costMin, minv[u] + minv[v] + len);
					costMax = Math.max(costMax, maxv[u] + maxv[v] + len);
					minv[u] = Math.min(minv[u], minv[v] + len);
					maxv[u] = Math.max(maxv[u], maxv[v] + len);
				}
			}
		}
	}

	public static void compute() {
		for (int i = 1; i <= k; i++) {
			isKey[arr[i]] = true;
		}
		int tree = buildVirtualTree1();
		// int tree = buildVirtualTree2();
		costSum = 0;
		costMin = INF;
		costMax = -INF;
		// dpOnTree1(tree);
		dpOnTree2(tree);
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
		// dfs1(1, 0);
		dfs2();
		q = in.nextInt();
		for (int t = 1; t <= q; t++) {
			k = in.nextInt();
			for (int i = 1; i <= k; i++) {
				arr[i] = in.nextInt();
			}
			compute();
			out.println(costSum + " " + costMin + " " + costMax);
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
