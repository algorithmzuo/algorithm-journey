package class189;

// 检查站，java版
// 测试链接 : https://www.luogu.com.cn/problem/CF427C
// 测试链接 : https://codeforces.com/problemset/problem/427/C
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code10_Checkposts1 {

	public static int MAXN = 100001;
	public static int MAXM = 300001;
	public static int MOD = 1000000007;
	public static int n, p, m;
	public static int[] cost = new int[MAXN];

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

	public static int[] minVal = new int[MAXN];
	public static int[] minCnt = new int[MAXN];
	public static int sccCnt;

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
			minVal[sccCnt] = 1000000001;
			minCnt[sccCnt] = 0;
			int pop;
			do {
				pop = sta[top--];
				if (minVal[sccCnt] > cost[pop]) {
					minVal[sccCnt] = cost[pop];
					minCnt[sccCnt] = 1;
				} else if (minVal[sccCnt] == cost[pop]) {
					minCnt[sccCnt]++;
				}
				ins[pop] = false;
			} while (pop != u);
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		for (int i = 1; i <= n; i++) {
			cost[i] = in.nextInt();
		}
		m = in.nextInt();
		for (int i = 1, u, v; i <= m; i++) {
			u = in.nextInt();
			v = in.nextInt();
			addEdge(u, v);
		}
		for (int i = 1; i <= n; i++) {
			if (dfn[i] == 0) {
				tarjan(i);
			}
		}
		long ans1 = 0, ans2 = 1;
		for (int i = 1; i <= sccCnt; i++) {
			ans1 += minVal[i];
			ans2 = (ans2 * minCnt[i]) % MOD;
		}
		out.println(ans1 + " " + ans2);
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
