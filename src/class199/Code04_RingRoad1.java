package class199;

// 城市环路，java版
// 图中有n个点、n条无向边，每个点有点权，图连通并且有一个环
// 图中任意相邻两点不能同时选择，先得到最大的点权累加和
// 然后乘以题目给定的系数k，打印结果
// 1 <= n <= 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P1453
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code04_RingRoad1 {

	public static int MAXN = 100001;
	public static int n;
	public static double k;
	public static int[] arr = new int[MAXN];

	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXN << 1];
	public static int[] to = new int[MAXN << 1];
	public static int cntg;

	public static int[] dfn = new int[MAXN];
	public static int cntd;

	public static int x, y, skip;

	public static int[][] dp = new int[MAXN][2];

	// 递归改迭代需要的栈
	public static int[] stau = new int[MAXN];
	public static int[] stap = new int[MAXN];
	public static int[] stas = new int[MAXN];
	public static int[] staf = new int[MAXN];
	public static int[] stae = new int[MAXN];
	public static int stacksize, u, preEdge, status, fa, e;

	public static void push(int u, int preEdge, int status, int fa, int e) {
		stau[stacksize] = u;
		stap[stacksize] = preEdge;
		stas[stacksize] = status;
		staf[stacksize] = fa;
		stae[stacksize] = e;
		stacksize++;
	}

	public static void pop() {
		--stacksize;
		u = stau[stacksize];
		preEdge = stap[stacksize];
		status = stas[stacksize];
		fa = staf[stacksize];
		e = stae[stacksize];
	}

	public static void addEdge(int u, int v) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		head[u] = cntg;
	}

	// 递归版
	public static void dfs1(int u, int preEdge) {
		dfn[u] = ++cntd;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (e != (preEdge ^ 1)) {
				if (dfn[v] == 0) {
					dfs1(v, e);
				} else if (dfn[u] < dfn[v]) {
					x = u;
					y = v;
					skip = e >> 1;
				}
			}
		}
	}

	// 迭代版
	public static void dfs2(int cur, int edge) {
		stacksize = 0;
		push(cur, edge, -1, 0, -1);
		int v;
		while (stacksize > 0) {
			pop();
			if (status == -1) {
				dfn[u] = ++cntd;
				e = head[u];
			} else {
				v = to[e];
				if (status == 1 && dfn[u] < dfn[v]) {
					x = u;
					y = v;
					skip = e >> 1;
				}
				e = nxt[e];
			}
			if (e == (preEdge ^ 1)) {
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
		dp[u][0] = 0;
		dp[u][1] = arr[u];
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (v != fa && (e >> 1) != skip) {
				dpOnTree1(v, u);
				dp[u][0] += Math.max(dp[v][0], dp[v][1]);
				dp[u][1] += dp[v][0];
			}
		}
	}

	// 迭代版
	public static void dpOnTree2(int cur, int father) {
		stacksize = 0;
		push(cur, 0, 0, father, -1);
		while (stacksize > 0) {
			pop();
			if (e == -1) {
				dp[u][0] = 0;
				dp[u][1] = arr[u];
				e = head[u];
			} else {
				e = nxt[e];
			}
			if (e != 0) {
				push(u, 0, 0, fa, e);
				if (to[e] != fa && (e >> 1) != skip) {
					push(to[e], 0, 0, u, -1);
				}
			} else {
				for (int ei = head[u]; ei > 0; ei = nxt[ei]) {
					int v = to[ei];
					if (v != fa && (ei >> 1) != skip) {
						dp[u][0] += Math.max(dp[v][0], dp[v][1]);
						dp[u][1] += dp[v][0];
					}
				}
			}
		}
	}

	public static long compute() {
		x = y = 0;
		dfs2(1, 0);
		dpOnTree2(x, 0);
		long ans = dp[x][0];
		dpOnTree2(y, 0);
		ans = Math.max(ans, dp[y][0]);
		return ans;
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		cntg = 1;
		for (int i = 1; i <= n; i++) {
			arr[i] = in.nextInt();
		}
		for (int i = 1, u, v; i <= n; i++) {
			u = in.nextInt();
			v = in.nextInt();
			u++;
			v++;
			addEdge(u, v);
			addEdge(v, u);
		}
		k = in.nextDouble();
		long ans = compute();
		out.printf("%.1f\n", k * ans);
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

		double nextDouble() throws IOException {
			int c;
			do {
				c = readByte();
			} while (c <= ' ' && c != -1);
			boolean neg = false;
			if (c == '-') {
				neg = true;
				c = readByte();
			}
			long intPart = 0;
			while (c > ' ' && c != -1 && c != '.') {
				intPart = intPart * 10 + (c - '0');
				c = readByte();
			}
			double val = (double) intPart;
			if (c == '.') {
				c = readByte();
				double base = 0.1;
				while (c > ' ' && c != -1) {
					val += (c - '0') * base;
					base *= 0.1;
					c = readByte();
				}
			}
			return neg ? -val : val;
		}

	}

}
