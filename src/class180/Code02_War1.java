package class180;

// 消耗战，java版
// 测试链接 : https://www.luogu.com.cn/problem/P2495
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code02_War1 {

	public static int MAXN = 300001;
	public static int MAXP = 20;
	public static int n, m, k;

	public static int[] headg = new int[MAXN];
	public static int[] nextg = new int[MAXN << 1];
	public static int[] tog = new int[MAXN << 1];
	public static int[] weightg = new int[MAXN << 1];
	public static int cntg;

	public static int[] headv = new int[MAXN];
	public static int[] nextv = new int[MAXN];
	public static int[] tov = new int[MAXN];
	public static int[] weightv = new int[MAXN];
	public static int cntv;

	public static int[] dep = new int[MAXN];
	public static int[] dfn = new int[MAXN];
	public static int[][] stjump = new int[MAXN][MAXP];
	public static int[][] mindist = new int[MAXN][MAXP];
	public static int cntd;

	public static int[] arr = new int[MAXN];
	public static boolean[] isKey = new boolean[MAXN];
	public static int[] stack = new int[MAXN];
	public static int top;

	public static long[] dp = new long[MAXN];

	public static void addEdgeG(int u, int v, int w) {
		nextg[++cntg] = headg[u];
		tog[cntg] = v;
		weightg[cntg] = w;
		headg[u] = cntg;
	}

	public static void addEdgeV(int u, int v, int w) {
		nextv[++cntv] = headv[u];
		tov[cntv] = v;
		weightv[cntv] = w;
		headv[u] = cntv;
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

	public static void dfs(int u, int fa, int w) {
		dep[u] = dep[fa] + 1;
		dfn[u] = ++cntd;
		stjump[u][0] = fa;
		mindist[u][0] = w;
		for (int p = 1; p < MAXP; p++) {
			stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
			mindist[u][p] = Math.min(mindist[u][p - 1], mindist[stjump[u][p - 1]][p - 1]);
		}
		for (int e = headg[u]; e > 0; e = nextg[e]) {
			if (tog[e] != fa) {
				dfs(tog[e], u, weightg[e]);
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

	public static int getDist(int a, int b) {
		int dist = 100000001;
		if (dep[a] < dep[b]) {
			int tmp = a;
			a = b;
			b = tmp;
		}
		for (int p = MAXP - 1; p >= 0; p--) {
			if (dep[stjump[a][p]] >= dep[b]) {
				dist = Math.min(dist, mindist[a][p]);
				a = stjump[a][p];
			}
		}
		if (a == b) {
			return dist;
		}
		for (int p = MAXP - 1; p >= 0; p--) {
			if (stjump[a][p] != stjump[b][p]) {
				dist = Math.min(dist, Math.min(mindist[a][p], mindist[b][p]));
				a = stjump[a][p];
				b = stjump[b][p];
			}
		}
		return dist;
	}

	// 单调栈的方式建立虚树
	public static void buildVirtualTree() {
		sortByDfn(arr, 1, k);
		cntv = 0;
		top = 0;
		headv[1] = 0;
		stack[++top] = 1;
		for (int i = 1; i <= k; i++) {
			int x = arr[i];
			int y = stack[top];
			int lca = getLca(x, y);
			while (top > 1 && dfn[stack[top - 1]] >= dfn[lca]) {
				addEdgeV(stack[top - 1], stack[top], getDist(stack[top - 1], stack[top]));
				top--;
			}
			if (lca != stack[top]) {
				headv[lca] = 0;
				addEdgeV(lca, stack[top], getDist(lca, stack[top]));
				top--;
				stack[++top] = lca;
			}
			headv[x] = 0;
			stack[++top] = x;
		}
		while (top > 1) {
			addEdgeV(stack[top - 1], stack[top], getDist(stack[top - 1], stack[top]));
			top--;
		}
	}

	public static void dpOnTree(int u) {
		for (int e = headv[u]; e > 0; e = nextv[e]) {
			dpOnTree(tov[e]);
		}
		dp[u] = 0;
		for (int e = headv[u], v, w; e > 0; e = nextv[e]) {
			v = tov[e];
			w = weightv[e];
			if (isKey[v]) {
				dp[u] += w;
			} else {
				dp[u] += Math.min(dp[v], w);
			}
		}
	}

	public static long compute() {
		buildVirtualTree();
		for (int i = 1; i <= k; i++) {
			isKey[arr[i]] = true;
		}
		dpOnTree(1);
		for (int i = 1; i <= k; i++) {
			isKey[arr[i]] = false;
		}
		return dp[1];
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		for (int i = 1, u, v, w; i < n; i++) {
			u = in.nextInt();
			v = in.nextInt();
			w = in.nextInt();
			addEdgeG(u, v, w);
			addEdgeG(v, u, w);
		}
		dfs(1, 0, 0);
		m = in.nextInt();
		for (int t = 1; t <= m; t++) {
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
