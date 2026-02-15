package class192;

// 建造军营，java版
// 测试链接 : https://www.luogu.com.cn/problem/P8867
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code07_MilitaryCamp1 {

	public static int MAXN = 500001;
	public static int MAXM = 1000001;
	public static int MOD = 1000000007;
	public static int n, m;
	public static int[] a = new int[MAXM];
	public static int[] b = new int[MAXM];

	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXM << 1];
	public static int[] to = new int[MAXM << 1];
	public static int cntg;

	public static int[] dfn = new int[MAXN];
	public static int[] low = new int[MAXN];
	public static int cntd;

	public static int[] sta = new int[MAXN];
	public static int top;

	public static int[] belong = new int[MAXN];
	public static int[] ebccSiz = new int[MAXN];
	public static int ebccCnt;

	public static long[] power2 = new long[MAXM];
	public static int[] tsiz = new int[MAXN];
	public static long[][] dp = new long[MAXN][2];
	public static long ans;

	// 迭代版需要的栈，讲解118讲了递归改迭代的技巧
	public static int[][] stack = new int[MAXN][5];
	public static int u, preEdge, status, fa, e;
	public static int stacksize;

	public static void push(int u, int preEdge, int status, int fa, int e) {
		stack[stacksize][0] = u;
		stack[stacksize][1] = preEdge;
		stack[stacksize][2] = status;
		stack[stacksize][3] = fa;
		stack[stacksize][4] = e;
		stacksize++;
	}

	public static void pop() {
		stacksize--;
		u = stack[stacksize][0];
		preEdge = stack[stacksize][1];
		status = stack[stacksize][2];
		fa = stack[stacksize][3];
		e = stack[stacksize][4];
	}

	public static void addEdge(int u, int v) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		head[u] = cntg;
	}

	// 递归版
	public static void tarjan1(int u, int preEdge) {
		dfn[u] = low[u] = ++cntd;
		sta[++top] = u;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			if ((e ^ 1) == preEdge) {
				continue;
			}
			int v = to[e];
			if (dfn[v] == 0) {
				tarjan1(v, e);
				low[u] = Math.min(low[u], low[v]);
			} else {
				low[u] = Math.min(low[u], dfn[v]);
			}
		}
		if (dfn[u] == low[u]) {
			ebccCnt++;
			ebccSiz[ebccCnt] = 0;
			int pop;
			do {
				pop = sta[top--];
				belong[pop] = ebccCnt;
				ebccSiz[ebccCnt]++;
			} while (pop != u);
		}
	}

	// 迭代版
	public static void tarjan2(int node, int pree) {
		stacksize = 0;
		push(node, pree, -1, 0, -1);
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
				} else {
					low[u] = Math.min(low[u], dfn[v]);
				}
				e = nxt[e];
			}
			if ((e ^ 1) == preEdge) {
				e = nxt[e];
			}
			if (e != 0) {
				v = to[e];
				if (dfn[v] == 0) {
					push(u, preEdge, 0, 0, e);
					push(v, e, -1, 0, -1);
				} else {
					push(u, preEdge, 1, 0, e);
				}
			} else {
				if (dfn[u] == low[u]) {
					ebccCnt++;
					ebccSiz[ebccCnt] = 0;
					int pop;
					do {
						pop = sta[top--];
						belong[pop] = ebccCnt;
						ebccSiz[ebccCnt]++;
					} while (pop != u);
				}
			}
		}
	}

	public static void condense() {
		cntg = 0;
		for (int i = 1; i <= ebccCnt; i++) {
			head[i] = 0;
		}
		for (int i = 1; i <= m; i++) {
			int ebcc1 = belong[a[i]];
			int ebcc2 = belong[b[i]];
			if (ebcc1 != ebcc2) {
				addEdge(ebcc1, ebcc2);
				addEdge(ebcc2, ebcc1);
			}
		}
	}

	// 递归版
	public static void dpOnTree1(int u, int fa) {
		tsiz[u] = 1;
		dp[u][0] = 1;
		dp[u][1] = power2[ebccSiz[u]] - 1;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (v != fa) {
				dpOnTree1(v, u);
				tsiz[u] += tsiz[v];
				long dp0 = dp[u][0];
				long dp1 = dp[u][1];
				dp[u][0] = dp0 * 2 % MOD * dp[v][0] % MOD;
				dp[u][1] = dp1 * 2 % MOD * dp[v][0] % MOD;
				dp[u][1] = (dp[u][1] + dp0 * dp[v][1] % MOD) % MOD;
				dp[u][1] = (dp[u][1] + dp1 * dp[v][1] % MOD) % MOD;
			}
		}
		int p = tsiz[u];
		if (u == 1) {
			p--;
		}
		ans = (ans + dp[u][1] * power2[m - p] % MOD) % MOD;
	}

	// 迭代版
	public static void dpOnTree2(int cur, int father) {
		stacksize = 0;
		push(cur, 0, 0, father, -1);
		while (stacksize > 0) {
			pop();
			if (e == -1) {
				tsiz[u] = 1;
				dp[u][0] = 1;
				dp[u][1] = power2[ebccSiz[u]] - 1;
				e = head[u];
			} else {
				e = nxt[e];
			}
			if (e != 0) {
				push(u, 0, 0, fa, e);
				int v = to[e];
				if (v != fa) {
					push(v, 0, 0, u, -1);
				}
			} else {
				for (int ei = head[u]; ei > 0; ei = nxt[ei]) {
					int v = to[ei];
					if (v != fa) {
						tsiz[u] += tsiz[v];
						long dp0 = dp[u][0];
						long dp1 = dp[u][1];
						dp[u][0] = dp0 * 2 % MOD * dp[v][0] % MOD;
						dp[u][1] = dp1 * 2 % MOD * dp[v][0] % MOD;
						dp[u][1] = (dp[u][1] + dp0 * dp[v][1] % MOD) % MOD;
						dp[u][1] = (dp[u][1] + dp1 * dp[v][1] % MOD) % MOD;
					}
				}
				int p = tsiz[u];
				if (u == 1) {
					p--;
				}
				ans = (ans + dp[u][1] * power2[m - p] % MOD) % MOD;
			}
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		cntg = 1;
		n = in.nextInt();
		m = in.nextInt();
		for (int i = 1; i <= m; i++) {
			a[i] = in.nextInt();
			b[i] = in.nextInt();
			addEdge(a[i], b[i]);
			addEdge(b[i], a[i]);
		}
		// tarjan1(1, 0);
		tarjan2(1, 0);
		condense();
		power2[0] = 1;
		for (int i = 1; i <= m; i++) {
			power2[i] = power2[i - 1] * 2 % MOD;
		}
		// dpOnTree1(1, 0);
		dpOnTree2(1, 0);
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
