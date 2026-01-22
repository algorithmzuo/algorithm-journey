package class190;

// 劫掠计划，java版
// 测试链接 : https://www.luogu.com.cn/problem/P3627
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code04_RobberyPlan1 {

	public static int MAXN = 500001;
	public static int MAXM = 500001;
	public static int INF = 1000000001;
	public static int n, m, s, p;

	public static int[] money = new int[MAXN];
	public static boolean[] isBar = new boolean[MAXN];
	public static int[] a = new int[MAXM];
	public static int[] b = new int[MAXM];

	public static int[] head1 = new int[MAXN];
	public static int[] nxt1 = new int[MAXM];
	public static int[] to1 = new int[MAXM];
	public static int cnt1;

	public static int[] head2 = new int[MAXN];
	public static int[] nxt2 = new int[MAXM];
	public static int[] to2 = new int[MAXM];
	public static int cnt2;

	public static int[] dfn = new int[MAXN];
	public static int[] low = new int[MAXN];
	public static int cntd;

	public static boolean[] ins = new boolean[MAXN];
	public static int[] sta = new int[MAXN];
	public static int top;

	public static int[] belong = new int[MAXN];
	public static int[] sum = new int[MAXN];
	public static boolean[] hasBar = new boolean[MAXN];
	public static int sccCnt;

	public static int[] indegree = new int[MAXN];
	public static int[] que = new int[MAXN];
	public static int[] dp = new int[MAXN];

	// 迭代版需要的栈，讲解118讲了递归改迭代的技巧
	public static int[][] stack = new int[MAXN][3];
	public static int u, status, e;
	public static int stacksize;

	public static void push(int u, int status, int e) {
		stack[stacksize][0] = u;
		stack[stacksize][1] = status;
		stack[stacksize][2] = e;
		stacksize++;
	}

	public static void pop() {
		stacksize--;
		u = stack[stacksize][0];
		status = stack[stacksize][1];
		e = stack[stacksize][2];
	}

	public static void addEdge1(int u, int v) {
		nxt1[++cnt1] = head1[u];
		to1[cnt1] = v;
		head1[u] = cnt1;
	}

	public static void addEdge2(int u, int v) {
		nxt2[++cnt2] = head2[u];
		to2[cnt2] = v;
		head2[u] = cnt2;
	}

	// 递归版，java会爆栈，C++可以通过
	public static void tarjan1(int u) {
		dfn[u] = low[u] = ++cntd;
		sta[++top] = u;
		ins[u] = true;
		for (int e = head1[u]; e > 0; e = nxt1[e]) {
			int v = to1[e];
			if (dfn[v] == 0) {
				tarjan1(v);
				low[u] = Math.min(low[u], low[v]);
			} else {
				if (ins[v]) {
					low[u] = Math.min(low[u], dfn[v]);
				}
			}
		}
		if (dfn[u] == low[u]) {
			sccCnt++;
			sum[sccCnt] = 0;
			hasBar[sccCnt] = false;
			int pop;
			do {
				pop = sta[top--];
				belong[pop] = sccCnt;
				sum[sccCnt] += money[pop];
				hasBar[sccCnt] |= isBar[pop];
				ins[pop] = false;
			} while (pop != u);
		}
	}

	// 迭代版
	public static void tarjan2(int node) {
		stacksize = 0;
		push(node, -1, -1);
		int v;
		while (stacksize > 0) {
			pop();
			if (status == -1) {
				dfn[u] = low[u] = ++cntd;
				sta[++top] = u;
				ins[u] = true;
				e = head1[u];
			} else {
				v = to1[e];
				if (status == 0) {
					low[u] = Math.min(low[u], low[v]);
				}
				if (status == 1 && ins[v]) {
					low[u] = Math.min(low[u], dfn[v]);
				}
				e = nxt1[e];
			}
			if (e != 0) {
				v = to1[e];
				if (dfn[v] == 0) {
					push(u, 0, e);
					push(v, -1, -1);
				} else {
					push(u, 1, e);
				}
			} else {
				if (dfn[u] == low[u]) {
					sccCnt++;
					sum[sccCnt] = 0;
					hasBar[sccCnt] = false;
					int pop;
					do {
						pop = sta[top--];
						belong[pop] = sccCnt;
						sum[sccCnt] += money[pop];
						hasBar[sccCnt] |= isBar[pop];
						ins[pop] = false;
					} while (pop != u);
				}
			}
		}
	}

	public static int topo() {
		for (int i = 1; i <= sccCnt; i++) {
			dp[i] = -INF;
		}
		dp[belong[s]] = sum[belong[s]];
		int l = 1, r = 0;
		que[++r] = belong[s];
		while (l <= r) {
			int u = que[l++];
			for (int e = head2[u]; e > 0; e = nxt2[e]) {
				int v = to2[e];
				dp[v] = Math.max(dp[v], dp[u] + sum[v]);
				if (--indegree[v] == 0) {
					que[++r] = v;
				}
			}
		}
		int ans = 0;
		for (int i = 1; i <= sccCnt; i++) {
			if (dp[i] != -INF && hasBar[i]) {
				ans = Math.max(ans, dp[i]);
			}
		}
		return ans;
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		for (int i = 1; i <= m; i++) {
			a[i] = in.nextInt();
			b[i] = in.nextInt();
			addEdge1(a[i], b[i]);
		}
		for (int i = 1; i <= n; i++) {
			money[i] = in.nextInt();
		}
		s = in.nextInt();
		p = in.nextInt();
		for (int i = 1, x; i <= p; i++) {
			x = in.nextInt();
			isBar[x] = true;
		}
		// tarjan1(s);
		tarjan2(s);
		for (int i = 1; i <= m; i++) {
			int scc1 = belong[a[i]];
			int scc2 = belong[b[i]];
			if (scc1 > 0 && scc2 > 0 && scc1 != scc2) {
				indegree[scc2]++;
				addEdge2(scc1, scc2);
			}
		}
		int ans = topo();
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
