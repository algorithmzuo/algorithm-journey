package class190;

// 糖果，java版
// 一共n个人，每人至少得到一个糖果，一共k条要求，要求有5种类型
// 1 u v : u的糖果 == v的糖果    2 u v : u的糖果 < v的糖果
// 3 u v : u的糖果 >= v的糖果    4 u v : u的糖果 > v的糖果
// 5 u v : u的糖果 <= v的糖果
// 如果无法满足所有要求打印-1，否则打印需要的最少糖果数
// 1 <= n、k <= 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P3275
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code08_Candy1 {

	public static int MAXN = 100001;
	public static int MAXM = 200001;
	public static int n, k, m;
	public static int[] a = new int[MAXM];
	public static int[] b = new int[MAXM];
	public static int[] c = new int[MAXM];

	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXM];
	public static int[] to = new int[MAXM];
	public static int[] weight = new int[MAXM];
	public static int cntg;

	public static int[] dfn = new int[MAXN];
	public static int[] low = new int[MAXN];
	public static int cntd;

	public static int[] sta = new int[MAXN];
	public static int top;

	public static int[] belong = new int[MAXN];
	public static int[] sccSiz = new int[MAXN];
	public static int sccCnt;

	public static int[] indegree = new int[MAXN];
	public static long[] dp = new long[MAXN];

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

	public static void addEdge(int u, int v, int w) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		weight[cntg] = w;
		head[u] = cntg;
	}

	// 递归版，java会爆栈，C++可以通过
	public static void tarjan1(int u) {
		dfn[u] = low[u] = ++cntd;
		sta[++top] = u;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (dfn[v] == 0) {
				tarjan1(v);
				low[u] = Math.min(low[u], low[v]);
			} else {
				if (belong[v] == 0) {
					low[u] = Math.min(low[u], dfn[v]);
				}
			}
		}
		if (dfn[u] == low[u]) {
			sccCnt++;
			sccSiz[sccCnt] = 0;
			int pop;
			do {
				pop = sta[top--];
				belong[pop] = sccCnt;
				sccSiz[sccCnt]++;
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
				e = head[u];
			} else {
				v = to[e];
				if (status == 0) {
					low[u] = Math.min(low[u], low[v]);
				}
				if (status == 1 && belong[v] == 0) {
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
					sccSiz[sccCnt] = 0;
					int pop;
					do {
						pop = sta[top--];
						belong[pop] = sccCnt;
						sccSiz[sccCnt]++;
					} while (pop != u);
				}
			}
		}
	}

	public static boolean condense() {
		cntg = 0;
		for (int i = 1; i <= sccCnt; i++) {
			head[i] = 0;
		}
		for (int i = 1; i <= m; i++) {
			int scc1 = belong[a[i]];
			int scc2 = belong[b[i]];
			int w = c[i];
			if (scc1 == scc2 && w > 0) {
				return false;
			}
			if (scc1 != scc2) {
				indegree[scc2]++;
				addEdge(scc1, scc2, w);
			}
		}
		return true;
	}

	public static long dpOnDAG() {
		for (int u = sccCnt; u > 0; u--) {
			if (indegree[u] == 0) {
				dp[u] = 1;
			}
			for (int e = head[u]; e > 0; e = nxt[e]) {
				int v = to[e];
				int w = weight[e];
				dp[v] = Math.max(dp[v], dp[u] + w);
			}
		}
		long ans = 0;
		for (int i = 1; i <= sccCnt; i++) {
			ans += dp[i] * sccSiz[i];
		}
		return ans;
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		k = in.nextInt();
		m = 0;
		for (int i = 1, op, u, v; i <= k; i++) {
			op = in.nextInt();
			u = in.nextInt();
			v = in.nextInt();
			if (op == 1) {
				a[++m] = u; b[m] = v; c[m] = 0;
				a[++m] = v; b[m] = u; c[m] = 0;
			} else if (op == 2) {
				a[++m] = u; b[m] = v; c[m] = 1;
			} else if (op == 3) {
				a[++m] = v; b[m] = u; c[m] = 0;
			} else if (op == 4) {
				a[++m] = v; b[m] = u; c[m] = 1;
			} else {
				a[++m] = u; b[m] = v; c[m] = 0;
			}
		}
		for (int i = 1; i <= m; i++) {
			addEdge(a[i], b[i], c[i]);
		}
		for (int i = 1; i <= n; i++) {
			if (dfn[i] == 0) {
				// tarjan1(i);
				tarjan2(i);
			}
		}
		boolean check = condense();
		if (!check) {
			out.println(-1);
		} else {
			long ans = dpOnDAG();
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
