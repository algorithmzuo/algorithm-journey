package class189;

// 间谍网络，java版
// 测试链接 : https://www.luogu.com.cn/problem/P1262
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code07_Spy1 {

	public static int MAXN = 3001;
	public static int MAXM = 8001;
	public static int INF = 1000000001;
	public static int n, p, m;

	public static int[] cost = new int[MAXN];
	public static int[] a = new int[MAXM];
	public static int[] b = new int[MAXM];

	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXM];
	public static int[] to = new int[MAXM];
	public static int cntg;

	public static int[] dfn = new int[MAXN];
	public static int[] low = new int[MAXN];
	public static int cntd;

	public static boolean[] ins = new boolean[MAXN];
	public static int[] sta = new int[MAXN];
	public static int top;

	public static int[] belong = new int[MAXN];
	public static int[] minVal = new int[MAXN];
	public static int sccCnt;

	public static int[] indegree = new int[MAXN];

	public static void addEdge(int u, int v) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		head[u] = cntg;
	}

	public static void tarjan(int u) {
		dfn[u] = low[u] = ++cntd;
		sta[++top] = u;
		ins[u] = true;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
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
			minVal[sccCnt] = INF;
			int pop;
			do {
				pop = sta[top--];
				belong[pop] = sccCnt;
				minVal[sccCnt] = Math.min(minVal[sccCnt], cost[pop]);
				ins[pop] = false;
			} while (pop != u);
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		p = in.nextInt();
		for (int i = 1; i <= n; i++) {
			cost[i] = INF;
		}
		for (int i = 1, u, c; i <= p; i++) {
			u = in.nextInt();
			c = in.nextInt();
			cost[u] = c;
		}
		m = in.nextInt();
		for (int i = 1; i <= m; i++) {
			a[i] = in.nextInt();
			b[i] = in.nextInt();
			addEdge(a[i], b[i]);
		}
		for (int i = 1; i <= n; i++) {
			if (cost[i] != INF && dfn[i] == 0) {
				tarjan(i);
			}
		}
		boolean check = true;
		int ans = 0;
		for (int i = 1; i <= n; i++) {
			if (belong[i] == 0) {
				check = false;
				ans = i;
				break;
			}
		}
		if (!check) {
			out.println("NO");
			out.println(ans);
		} else {
			for (int i = 1; i <= m; i++) {
				int scc1 = belong[a[i]];
				int scc2 = belong[b[i]];
				if (scc1 != scc2) {
					indegree[scc2]++;
				}
			}
			for (int i = 1; i <= sccCnt; i++) {
				if (indegree[i] == 0) {
					ans += minVal[i];
				}
			}
			out.println("YES");
			out.println(ans);
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
