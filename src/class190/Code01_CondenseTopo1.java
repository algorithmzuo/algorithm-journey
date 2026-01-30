package class190;

// 缩点结合拓扑排序模版题，java版
// 给定一张n个点，m条边的有向图，每个点给定非负点权
// 如果重复经过一个点，点权只获得一次
// 找到一条路径，使得点权累加和最大，打印这个值
// 1 <= n <= 10^4
// 1 <= m <= 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P3387
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code01_CondenseTopo1 {

	public static int MAXN = 10001;
	public static int MAXM = 100001;
	public static int n, m;

	public static int[] arr = new int[MAXN];
	public static int[] a = new int[MAXM];
	public static int[] b = new int[MAXM];

	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXM];
	public static int[] to = new int[MAXM];
	public static int cntg;

	public static int[] dfn = new int[MAXN];
	public static int[] low = new int[MAXN];
	public static int cntd;

	public static int[] sta = new int[MAXN];
	public static int top;

	public static int[] belong = new int[MAXN];
	public static int[] sum = new int[MAXN];
	public static int sccCnt;

	public static int[] indegree = new int[MAXN];
	public static int[] que = new int[MAXN];
	public static int[] dp = new int[MAXN];

	public static void addEdge(int u, int v) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		head[u] = cntg;
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
				sum[sccCnt] += arr[pop];
			} while (pop != u);
		}
	}

	public static void condense() {
		cntg = 0;
		for (int i = 1; i <= sccCnt; i++) {
			head[i] = 0;
		}
		for (int i = 1; i <= m; i++) {
			int scc1 = belong[a[i]];
			int scc2 = belong[b[i]];
			if (scc1 != scc2) {
				indegree[scc2]++;
				addEdge(scc1, scc2);
			}
		}
	}

	// 经典拓扑排序的写法
	public static int topo() {
		int l = 1, r = 0;
		for (int i = 1; i <= sccCnt; i++) {
			if (indegree[i] == 0) {
				dp[i] = sum[i];
				que[++r] = i;
			}
		}
		while (l <= r) {
			int u = que[l++];
			for (int e = head[u]; e > 0; e = nxt[e]) {
				int v = to[e];
				dp[v] = Math.max(dp[v], dp[u] + sum[v]);
				if (--indegree[v] == 0) {
					que[++r] = v;
				}
			}
		}
		int ans = 0;
		for (int i = 1; i <= sccCnt; i++) {
			ans = Math.max(ans, dp[i]);
		}
		return ans;
	}

	// 直接转移的写法
	public static int dpOnDAG() {
		for (int u = sccCnt; u > 0; u--) {
			if (indegree[u] == 0) {
				dp[u] = sum[u];
			}
			for (int e = head[u]; e > 0; e = nxt[e]) {
				int v = to[e];
				dp[v] = Math.max(dp[v], dp[u] + sum[v]);
			}
		}
		int ans = 0;
		for (int u = 1; u <= sccCnt; u++) {
			ans = Math.max(ans, dp[u]);
		}
		return ans;
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		for (int i = 1; i <= n; i++) {
			arr[i] = in.nextInt();
		}
		for (int i = 1; i <= m; i++) {
			a[i] = in.nextInt();
			b[i] = in.nextInt();
			addEdge(a[i], b[i]);
		}
		for (int i = 1; i <= n; i++) {
			if (dfn[i] == 0) {
				tarjan(i);
			}
		}
		condense();
		// int ans = topo();
		int ans = dpOnDAG();
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
