package class180;

// 虚树模版题，java版
// 测试链接 : https://www.luogu.com.cn/problem/CF613D
// 测试链接 : https://codeforces.com/problemset/problem/613/D
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

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

	// 节点编号、dfn序
	public static int[][] arr = new int[MAXN][2];
	public static boolean[] isKey = new boolean[MAXN];

	// 第一种建树方式
	public static int[][] tmp = new int[MAXN << 1][2];

	// 第二种建树方式
	public static int[] stack = new int[MAXN];
	public static int top;

	public static int[] siz = new int[MAXN];
	public static int[] dp = new int[MAXN];

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
	public static void buildVirtualTree1() {
		Arrays.sort(arr, 1, k + 1, (a, b) -> a[1] - b[1]);
		int len = 0;
		for (int i = 1; i < k; i++) {
			tmp[++len][0] = arr[i][0];
			tmp[len][1] = arr[i][1];
			int lca = getLca(arr[i][0], arr[i + 1][0]);
			tmp[++len][0] = lca;
			tmp[len][1] = dfn[lca];
		}
		tmp[++len][0] = arr[k][0];
		tmp[len][1] = arr[k][1];
		tmp[++len][0] = 1;
		tmp[len][1] = dfn[1];
		Arrays.sort(tmp, 1, len + 1, (a, b) -> a[1] - b[1]);
		int unique = 1;
		for (int i = 2; i <= len; i++) {
			if (tmp[unique][0] != tmp[i][0]) {
				tmp[++unique][0] = tmp[i][0];
				tmp[unique][1] = tmp[i][1];
			}
		}
		cntv = 0;
		for (int i = 1; i <= unique; i++) {
			headv[tmp[i][0]] = 0;
		}
		for (int i = 1; i < unique; i++) {
			addEdgeV(getLca(tmp[i][0], tmp[i + 1][0]), tmp[i + 1][0]);
		}
	}

	// 单调栈的方式建立虚树
	public static void buildVirtualTree2() {
		Arrays.sort(arr, 1, k + 1, (a, b) -> a[1] - b[1]);
		cntv = 0;
		top = 0;
		headv[1] = 0;
		stack[++top] = 1;
		for (int i = 1; i <= k; i++) {
			int x = arr[i][0];
			if (x == 1) {
				continue;
			}
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
	}

	public static void dpOnTree(int u, int fa) {
		dp[u] = siz[u] = 0;
		for (int e = headv[u], v; e > 0; e = nextv[e]) {
			v = tov[e];
			if (v != fa) {
				dpOnTree(v, u);
				dp[u] += dp[v];
				siz[u] += siz[v];
			}
		}
		if (isKey[u]) {
			dp[u] += siz[u];
			siz[u] = 1;
		} else if (siz[u] > 1) {
			dp[u]++;
			siz[u] = 0;
		}
	}

	public static int compute() {
		for (int i = 1; i <= k; i++) {
			isKey[arr[i][0]] = true;
		}
		boolean check = true;
		for (int i = 1; i <= k; i++) {
			if (isKey[stjump[arr[i][0]][0]]) {
				check = false;
				break;
			}
		}
		int ans = -1;
		if (check) {
			// buildVirtualTree1();
			buildVirtualTree2();
			dpOnTree(1, 0);
			ans = dp[1];
		}
		for (int i = 1; i <= k; i++) {
			isKey[arr[i][0]] = false;
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
			for (int i = 1, node; i <= k; i++) {
				node = in.nextInt();
				arr[i][0] = node;
				arr[i][1] = dfn[node];
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
