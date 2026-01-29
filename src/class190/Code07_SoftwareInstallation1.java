package class190;

// 软件安装，java版
// 测试链接 : https://www.luogu.com.cn/problem/P2515
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code07_SoftwareInstallation1 {

	public static int MAXN = 301;
	public static int MAXW = 601;
	public static int n, knapsack;
	public static int[] w = new int[MAXN];
	public static int[] v = new int[MAXN];
	public static int[] depend = new int[MAXN];

	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXN];
	public static int[] to = new int[MAXN];
	public static int cntg;

	public static int[] dfn = new int[MAXN];
	public static int[] low = new int[MAXN];
	public static int cntd;

	public static int[] sta = new int[MAXN];
	public static int top;

	public static int[] belong = new int[MAXN];
	public static int[] wsum = new int[MAXN];
	public static int[] vsum = new int[MAXN];
	public static int sccCnt;

	public static int[] indegree = new int[MAXN];

	// 树上背包最优解，来自讲解079，题目5
	public static int[] siz = new int[MAXN];
	public static int[] weight = new int[MAXN];
	public static int[] value = new int[MAXN];
	public static int dfnCnt;
	public static int[][] dp = new int[MAXN][MAXW];

	public static void addEdge(int a, int b) {
		nxt[++cntg] = head[a];
		to[cntg] = b;
		head[a] = cntg;
	}

	public static void tarjan(int u) {
		dfn[u] = low[u] = ++cntd;
		sta[++top] = u;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (dfn[v] == 0) {
				tarjan(v);
				low[u] = Math.min(low[u], low[v]);
			} else {
				if (belong[v] == 0) {
					low[u] = Math.min(low[u], dfn[v]);
				}
			}
		}
		if (dfn[u] == low[u]) {
			sccCnt++;
			int pop;
			do {
				pop = sta[top--];
				belong[pop] = sccCnt;
				wsum[sccCnt] += w[pop];
				vsum[sccCnt] += v[pop];
			} while (pop != u);
		}
	}

	public static void condense() {
		cntg = 0;
		for (int i = 1; i <= sccCnt; i++) {
			head[i] = 0;
		}
		for (int i = 1; i <= n; i++) {
			if (depend[i] > 0) {
				int scc1 = belong[depend[i]];
				int scc2 = belong[i];
				if (scc1 != scc2) {
					addEdge(scc1, scc2);
					indegree[scc2]++;
				}
			}
		}
		for (int i = 1; i <= sccCnt; i++) {
			if (indegree[i] == 0) {
				addEdge(0, i);
			}
		}
	}

	// 缩点后的树，每个节点重新分配dfn序号
	public static int dfs(int u) {
		int i = ++dfnCnt;
		siz[i] = 1;
		weight[i] = wsum[u];
		value[i] = vsum[u];
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			siz[i] += dfs(v);
		}
		return siz[i];
	}

	// 树上01背包最优解
	// 讲解079，题目5，树上背包最优解，讲的非常清楚
	public static int knapsackOnTree() {
		dfs(0);
		for (int i = dfnCnt; i >= 2; i--) {
			for (int j = 1; j <= knapsack; j++) {
				dp[i][j] = dp[i + siz[i]][j];
				if (j - weight[i] >= 0) {
					dp[i][j] = Math.max(dp[i][j], value[i] + dp[i + 1][j - weight[i]]);
				}
			}
		}
		return dp[2][knapsack];
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		knapsack = in.nextInt();
		for (int i = 1; i <= n; i++) {
			w[i] = in.nextInt();
		}
		for (int i = 1; i <= n; i++) {
			v[i] = in.nextInt();
		}
		for (int i = 1; i <= n; i++) {
			depend[i] = in.nextInt();
			if (depend[i] > 0) {
				addEdge(depend[i], i);
			}
		}
		for (int i = 1; i <= n; i++) {
			if (dfn[i] == 0) {
				tarjan(i);
			}
		}
		condense();
		int ans = knapsackOnTree();
		out.println(ans);
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
