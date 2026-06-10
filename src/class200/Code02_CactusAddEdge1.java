package class200;

// 仙人掌加边方案数，java版
// 给定n个点、m条边的无向连通图，输入保证没有自环、没有重边
// 可以在图中加入一些新的边，如果加入后得到的图是仙人掌，就叫有效的加边方案
// 允许一条边也不加，但不允许加边后，形成自环或二元环，计算有效的加边方案数
// 答案对 998244353 取余，每次输入可能有多组测试数据
// 1 <= n <= 5 * 10^5
// 1 <= m <= 10^6
// 测试链接 : https://www.luogu.com.cn/problem/P3687
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code02_CactusAddEdge1 {

	public static int MAXN = 500001;
	public static int MAXM = 1000001;
	public static int MOD = 998244353;
	public static int t, n, m;

	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXM << 1];
	public static int[] to = new int[MAXM << 1];
	public static boolean[] cycleEdge = new boolean[MAXM << 1];
	public static int cntg;

	public static int[] dfn = new int[MAXN];
	public static int[] low = new int[MAXN];
	public static int cntd;
	public static int[] sta = new int[MAXN];
	public static int top;

	public static int[] fromEdge = new int[MAXN];
	public static int[] cycleCnt = new int[MAXN];

	public static boolean[] vis = new boolean[MAXN];

	// f[i] : 一个点周围有i条可用方向时，这些方向两两配对或不配对的方案数
	// 每条方向最多参与一次配对
	public static long[] f = new long[MAXN];

	// dp[u] : u为头的子树内每个节点
	// 对自己周围的可用方向配对后，形成的总方案数
	// 如果u不是连通块的根，u到父亲也算一个方向，该方向上的节点数是1
	// 如果u是连通块的根，dp[u]表示这个连通块完整的合法加边方案数
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
		cycleEdge[cntg] = false;
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
				fromEdge[v] = e;
				if (low[v] < dfn[u]) {
					low[u] = Math.min(low[u], low[v]);
					cycleCnt[u]++;
				} else if (low[v] > dfn[u]) {
					top--;
				} else {
					int pop;
					int edge = fromEdge[u];
					cycleEdge[edge] = cycleEdge[edge ^ 1] = true;
					do {
						pop = sta[top--];
						edge = fromEdge[pop];
						cycleEdge[edge] = cycleEdge[edge ^ 1] = true;
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
						int pop;
						int edge = fromEdge[u];
						cycleEdge[edge] = cycleEdge[edge ^ 1] = true;
						do {
							pop = sta[top--];
							edge = fromEdge[pop];
							cycleEdge[edge] = cycleEdge[edge ^ 1] = true;
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
		int direction = fa == 0 ? 0 : 1;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (v != fa && !cycleEdge[e]) {
				dpOnTree1(v, u);
				ans = ans * dp[v] % MOD;
				direction++;
			}
		}
		dp[u] = ans * f[direction] % MOD;
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
				if (v != fa && !cycleEdge[e]) {
					push(v, 0, 0, u, -1);
				}
			} else {
				long ans = 1;
				int direction = fa == 0 ? 0 : 1;
				for (int e = head[u]; e > 0; e = nxt[e]) {
					int v = to[e];
					if (v != fa && !cycleEdge[e]) {
						ans = ans * dp[v] % MOD;
						direction++;
					}
				}
				dp[u] = ans * f[direction] % MOD;
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
			boolean check = true;
			for (int i = 1; i <= n; i++) {
				if (cycleCnt[i] >= 2) {
					check = false;
					break;
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