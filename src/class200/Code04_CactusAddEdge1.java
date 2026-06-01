package class200;

// 加边的方法数，java版
// 测试链接 : https://www.luogu.com.cn/problem/P3687
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code04_CactusAddEdge1 {

	public static int MAXN = 500001;
	public static int MAXM = 1000001;
	public static int MOD = 998244353;
	public static int t, n, m;

	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXM << 1];
	public static int[] to = new int[MAXM << 1];
	public static boolean[] ban = new boolean[MAXM << 1];
	public static int cntg;

	public static int[] dfn = new int[MAXN];
	public static int[] low = new int[MAXN];
	public static int cntd;
	public static int[] sta = new int[MAXN];
	public static int top;

	public static int[] fromEdge = new int[MAXN];
	public static int[] cycleCnt = new int[MAXN];

	public static boolean check;
	public static long[] f = new long[MAXN];

	public static boolean[] vis = new boolean[MAXN];
	public static long[] dp = new long[MAXN];

	// 递归改迭代需要的栈
	public static int[] stau = new int[MAXN];
	public static int[] stap = new int[MAXN];
	public static int[] stas = new int[MAXN];
	public static int[] staf = new int[MAXN];
	public static int[] stae = new int[MAXN];
	public static int u, preEdge, status, fa, e;
	public static int stacksiz;

	public static void push(int u, int preEdge, int status, int fa, int e) {
		stau[stacksiz] = u;
		stap[stacksiz] = preEdge;
		stas[stacksiz] = status;
		staf[stacksiz] = fa;
		stae[stacksiz] = e;
		stacksiz++;
	}

	public static void pop() {
		stacksiz--;
		u = stau[stacksiz];
		preEdge = stap[stacksiz];
		status = stas[stacksiz];
		fa = staf[stacksiz];
		e = stae[stacksiz];
	}

	public static void addEdge(int u, int v) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		ban[cntg] = false;
		head[u] = cntg;
	}

	public static void banEdge(int e) {
		if (ban[e] || ban[e ^ 1]) {
			check = false;
		} else {
			ban[e] = ban[e ^ 1] = true;
		}
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
				fromEdge[v] = e;
				if (low[v] < dfn[u]) {
					low[u] = Math.min(low[u], low[v]);
					cycleCnt[u]++;
				} else if (low[v] > dfn[u]) {
					top--;
				} else {
					banEdge(fromEdge[u]);
					int pop;
					do {
						pop = sta[top--];
						banEdge(fromEdge[pop]);
					} while (pop != v);
				}
			} else if (dfn[v] < dfn[u]) {
				fromEdge[v] = e;
				low[u] = Math.min(low[u], dfn[v]);
				cycleCnt[u]++;
			}
		}
	}

	// 迭代版
	public static void tarjan2(int node, int pree) {
		stacksiz = 0;
		push(node, pree, -1, 0, -1);
		int v;
		while (stacksiz > 0) {
			pop();
			if (status == -1) {
				dfn[u] = low[u] = ++cntd;
				sta[++top] = u;
				e = head[u];
			} else {
				v = to[e];
				if (status == 0) {
					fromEdge[v] = e;
					if (low[v] < dfn[u]) {
						low[u] = Math.min(low[u], low[v]);
						cycleCnt[u]++;
					} else if (low[v] > dfn[u]) {
						top--;
					} else {
						banEdge(fromEdge[u]);
						int pop;
						do {
							pop = sta[top--];
							banEdge(fromEdge[pop]);
						} while (pop != v);
					}
				} else {
					if (dfn[v] < dfn[u]) {
						fromEdge[v] = e;
						low[u] = Math.min(low[u], dfn[v]);
						cycleCnt[u]++;
					}
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
			}
		}
	}

	// 递归版
	public static void dpOnTree1(int u, int fa) {
		vis[u] = true;
		long ans = 1;
		int son = fa == 0 ? 0 : 1;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (v != fa && !ban[e]) {
				dpOnTree1(v, u);
				ans = ans * dp[v] % MOD;
				son++;
			}
		}
		dp[u] = ans * f[son] % MOD;
	}

	// 迭代版
	public static void dpOnTree2(int cur, int father) {
		stacksiz = 0;
		push(cur, 0, 0, father, -1);
		while (stacksiz > 0) {
			pop();
			if (e == -1) {
				vis[u] = true;
				e = head[u];
			} else {
				e = nxt[e];
			}
			if (e != 0) {
				push(u, 0, 0, fa, e);
				int v = to[e];
				if (v != fa && !ban[e]) {
					push(v, 0, 0, u, -1);
				}
			} else {
				long ans = 1;
				int son = fa == 0 ? 0 : 1;
				for (int e = head[u]; e > 0; e = nxt[e]) {
					int v = to[e];
					if (v != fa && !ban[e]) {
						ans = ans * dp[v] % MOD;
						son++;
					}
				}
				dp[u] = ans * f[son] % MOD;
			}
		}
	}

	public static void prepare() {
		f[0] = f[1] = 1;
		for (int i = 2; i <= n; i++) {
			f[i] = (f[i - 1] + f[i - 2] * (i - 1)) % MOD;
		}
		for (int i = 1; i <= n; i++) {
			head[i] = dfn[i] = low[i] = fromEdge[i] = cycleCnt[i] = 0;
			vis[i] = false;
			dp[i] = 0;
		}
		cntg = 1;
		cntd = top = 0;
		check = true;
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		t = in.nextInt();
		for (int c = 1; c <= t; c++) {
			n = in.nextInt();
			m = in.nextInt();
			prepare();
			for (int i = 1, u, v; i <= m; i++) {
				u = in.nextInt();
				v = in.nextInt();
				addEdge(u, v);
				addEdge(v, u);
			}
			// tarjan1(1, 0);
			tarjan2(1, 0);
			if (check) {
				for (int i = 1; i <= n; i++) {
					if (dfn[i] == 0 || cycleCnt[i] >= 2) {
						check = false;
						break;
					}
				}
			}
			if (check) {
				long ans = 1;
				for (int i = 1; i <= n; i++) {
					if (!vis[i]) {
						// dpOnTree1(i, 0);
						dpOnTree2(i, 0);
						ans = ans * dp[i] % MOD;
					}
				}
				out.println(ans);
			} else {
				out.println(0);
			}
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