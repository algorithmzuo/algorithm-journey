package class190;

// 最优贸易，java版
// 测试链接 : https://www.luogu.com.cn/problem/P1073
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code03_OptimalTrade1 {

	public static int MAXN = 100001;
	public static int MAXM = 500001;
	public static int INF = 1000000001;
	public static int n, m;
	public static int[] val = new int[MAXN];
	public static int[] a = new int[MAXM];
	public static int[] b = new int[MAXM];

	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXM << 1];
	public static int[] to = new int[MAXM << 1];
	public static int cntg;

	public static int[] dfn = new int[MAXN];
	public static int[] low = new int[MAXN];
	public static int cntd;

	public static boolean[] ins = new boolean[MAXN];
	public static int[] sta = new int[MAXN];
	public static int top;

	public static int[] belong = new int[MAXN];
	public static int[] minv = new int[MAXN];
	public static int[] maxv = new int[MAXN];
	public static int sccCnt;

	public static int[] premin = new int[MAXN];
	public static int[] best = new int[MAXN];

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

	public static void addEdge(int u, int v) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		head[u] = cntg;
	}

	// 递归版，java会爆栈，C++可以通过
	public static void tarjan1(int u) {
		dfn[u] = low[u] = ++cntd;
		sta[++top] = u;
		ins[u] = true;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
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
			minv[sccCnt] = INF;
			maxv[sccCnt] = -INF;
			int pop;
			do {
				pop = sta[top--];
				belong[pop] = sccCnt;
				minv[sccCnt] = Math.min(minv[sccCnt], val[pop]);
				maxv[sccCnt] = Math.max(maxv[sccCnt], val[pop]);
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
				e = head[u];
			} else {
				v = to[e];
				if (status == 0) {
					low[u] = Math.min(low[u], low[v]);
				}
				if (status == 1 && ins[v]) {
					low[u] = Math.min(low[u], dfn[v]);
				}
				e = nxt[e];
			}
			if (e != 0) {
				v = to[e];
				if (dfn[v] == 0) {
					push(u, 0, e);
					push(v, -1, -1);
				} else {
					push(u, 1, e);
				}
			} else {
				if (dfn[u] == low[u]) {
					sccCnt++;
					minv[sccCnt] = INF;
					maxv[sccCnt] = -INF;
					int pop;
					do {
						pop = sta[top--];
						belong[pop] = sccCnt;
						minv[sccCnt] = Math.min(minv[sccCnt], val[pop]);
						maxv[sccCnt] = Math.max(maxv[sccCnt], val[pop]);
						ins[pop] = false;
					} while (pop != u);
				}
			}
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
			if (scc1 > 0 && scc2 > 0 && scc1 != scc2) {
				addEdge(scc1, scc2);
			}
		}
	}

	public static int dpOnDAG() {
		for (int u = 1; u <= sccCnt; u++) {
			premin[u] = INF;
			best[u] = -INF;
		}
		int s = belong[1];
		premin[s] = minv[s];
		best[s] = maxv[s] - minv[s];
		for (int u = sccCnt; u > 0; u--) {
			if (premin[u] != INF) {
				for (int e = head[u]; e > 0; e = nxt[e]) {
					int v = to[e];
					premin[v] = Math.min(premin[v], Math.min(premin[u], minv[v]));
					best[v] = Math.max(best[v], Math.max(best[u], maxv[v] - premin[v]));
				}
			}
		}
		return best[belong[n]];
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		for (int i = 1; i <= n; i++) {
			val[i] = in.nextInt();
		}
		for (int i = 1, op; i <= m; i++) {
			a[i] = in.nextInt();
			b[i] = in.nextInt();
			op = in.nextInt();
			if (op == 1) {
				addEdge(a[i], b[i]);
			} else {
				addEdge(a[i], b[i]);
				addEdge(b[i], a[i]);
			}
		}
		// tarjan1(1);
		tarjan2(1);
		if (belong[n] == 0) {
			out.println(0);
		} else {
			condense();
			int ans = dpOnDAG();
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
