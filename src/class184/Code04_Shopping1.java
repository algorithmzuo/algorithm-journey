package class184;

// 树上购物，java版
// 一共有n个商店，有n-1条路构成一棵树，第i个商店只卖第i种物品
// 给定每种物品三个属性，价值v[i]、单价c[i]、数量d[i]
// 你逛商店可能会购买物品，要求所有买过东西的商店，在树上必须连通
// 你有m元，打印能获得的最大价值总和
// 1 <= n <= 500    1 <= m <= 4000    1 <= d[i] <= 2000
// 测试链接 : https://www.luogu.com.cn/problem/P6326
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code04_Shopping1 {

	public static int MAXN = 501;
	public static int MAXM = 4001;
	public static int t, n, m;

	public static int[] v = new int[MAXN];
	public static int[] c = new int[MAXN];
	public static int[] d = new int[MAXN];

	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXN << 1];
	public static int[] to = new int[MAXN << 1];
	public static int cntg;

	public static boolean[] vis = new boolean[MAXN];
	public static int[] siz = new int[MAXN];

	public static int[] nodeArr = new int[MAXN];
	public static int[] endDfn = new int[MAXN];
	public static int cntd;

	public static int[] val = new int[MAXN];
	public static int[] cost = new int[MAXN];
	public static int[][] dp = new int[MAXN + 1][MAXM];

	public static void prepare() {
		cntg = 0;
		for (int i = 1; i <= n; i++) {
			head[i] = 0;
			vis[i] = false;
		}
	}

	public static void addEdge(int u, int v) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		head[u] = cntg;
	}

	public static void getSize(int u, int fa) {
		siz[u] = 1;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (v != fa && !vis[v]) {
				getSize(v, u);
				siz[u] += siz[v];
			}
		}
	}

	public static int getCentroid(int u, int fa) {
		getSize(u, fa);
		int half = siz[u] >> 1;
		boolean find = false;
		while (!find) {
			find = true;
			for (int e = head[u]; e > 0; e = nxt[e]) {
				int v = to[e];
				if (v != fa && !vis[v] && siz[v] > half) {
					fa = u;
					u = v;
					find = false;
					break;
				}
			}
		}
		return u;
	}

	public static void dfs(int u, int fa) {
		nodeArr[++cntd] = u;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (v != fa && !vis[v]) {
				dfs(v, u);
			}
		}
		endDfn[u] = cntd;
	}

	public static int calc(int u) {
		cntd = 0;
		dfs(u, 0);
		for (int i = cntd; i > 0; i--) {
			int cur = nodeArr[i];
			// 二进制分组优化，注意物品量-1，然后二进制分组
			// 先必选一件，然后考虑更多件，课上解释了为什么
			int cnt = d[cur] - 1;
			int num = 0;
			for (int k = 1; k <= cnt; k <<= 1) {
				val[++num] = v[cur] * k;
				cost[num] = c[cur] * k;
				cnt -= k;
			}
			if (cnt > 0) {
				val[++num] = v[cur] * cnt;
				cost[num] = c[cur] * cnt;
			}
			// 必选cur的情况，先必选一件
			for (int j = m; j >= c[cur]; j--) {
				dp[i][j] = dp[i + 1][j - c[cur]] + v[cur];
			}
			// 然后用二进制分组，拼出可能的更多件
			for (int k = 1; k <= num; k++) {
				for (int j = m; j >= cost[k]; j--) {
					dp[i][j] = Math.max(dp[i][j], dp[i][j - cost[k]] + val[k]);
				}
			}
			// 不选cur的情况，那么cur的子树都跳过，直接跳到endDfn[cur] + 1
			for (int j = 0; j <= m; j++) {
				dp[i][j] = Math.max(dp[i][j], dp[endDfn[cur] + 1][j]);
			}
		}
		int ans = dp[1][m];
		// 返回之前清空
		for (int i = 1; i <= cntd; i++) {
			for (int j = 0; j <= m; j++) {
				dp[i][j] = 0;
			}
		}
		return ans;
	}

	public static int solve(int u) {
		vis[u] = true;
		int ans = calc(u);
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (!vis[v]) {
				ans = Math.max(ans, solve(getCentroid(v, u)));
			}
		}
		return ans;
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		t = in.nextInt();
		for (int k = 1; k <= t; k++) {
			n = in.nextInt();
			m = in.nextInt();
			for (int i = 1; i <= n; i++) {
				v[i] = in.nextInt();
			}
			for (int i = 1; i <= n; i++) {
				c[i] = in.nextInt();
			}
			for (int i = 1; i <= n; i++) {
				d[i] = in.nextInt();
			}
			prepare();
			for (int i = 1, u, v; i < n; i++) {
				u = in.nextInt();
				v = in.nextInt();
				addEdge(u, v);
				addEdge(v, u);
			}
			out.println(solve(getCentroid(1, 0)));
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
