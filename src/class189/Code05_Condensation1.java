package class189;

// 缩点模版题，java版
// 测试链接 : https://www.luogu.com.cn/problem/P3387
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code05_Condensation1 {

	public static int MAXN = 10001;
	public static int MAXM = 100001;
	public static int n, m;

	public static int[] arr = new int[MAXN];
	public static int[] a = new int[MAXM];
	public static int[] b = new int[MAXM];

	public static int[] head1 = new int[MAXN];
	public static int[] nxt1 = new int[MAXM];
	public static int[] to1 = new int[MAXM];
	public static int cnt1;

	public static int[] dfn = new int[MAXN];
	public static int[] low = new int[MAXN];
	public static int cntd;

	public static boolean[] ins = new boolean[MAXN];
	public static int[] sta = new int[MAXN];
	public static int top;

	public static int[] belong = new int[MAXN];
	public static int[] sum = new int[MAXN];
	public static int sccCnt;

	public static int[] head2 = new int[MAXN];
	public static int[] nxt2 = new int[MAXM];
	public static int[] to2 = new int[MAXM];
	public static int cnt2;

	public static int[] indegree = new int[MAXN];
	public static int[] que = new int[MAXN];
	public static int[] dp = new int[MAXN];

	public static void addEdge1(int u, int v) {
		nxt1[++cnt1] = head1[u];
		to1[cnt1] = v;
		head1[u] = cnt1;
	}

	public static void tarjan(int u) {
		dfn[u] = low[u] = ++cntd;
		sta[++top] = u;
		ins[u] = true;
		for (int e = head1[u]; e > 0; e = nxt1[e]) {
			int v = to1[e];
			if (dfn[v] == 0) {
				tarjan(v);
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
			int pop;
			do {
				pop = sta[top--];
				belong[pop] = sccCnt;
				sum[sccCnt] += arr[pop];
				ins[pop] = false;
			} while (pop != u);
		}
	}

	public static void addEdge2(int u, int v) {
		nxt2[++cnt2] = head2[u];
		to2[cnt2] = v;
		head2[u] = cnt2;
	}

	public static int topo() {
		int l = 1, r = 0;
		for (int i = 1; i <= sccCnt; i++) {
			if (indegree[i] == 0) {
				que[++r] = i;
			}
		}
		while (l <= r) {
			int u = que[l++];
			dp[u] += sum[u];
			for (int e = head2[u]; e > 0; e = nxt2[e]) {
				int v = to2[e];
				dp[v] = Math.max(dp[v], dp[u]);
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
			addEdge1(a[i], b[i]);
		}
		for (int i = 1; i <= n; i++) {
			if (dfn[i] == 0) {
				tarjan(i);
			}
		}
		for (int i = 1; i <= m; i++) {
			int scc1 = belong[a[i]];
			int scc2 = belong[b[i]];
			if (scc1 != scc2) {
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
