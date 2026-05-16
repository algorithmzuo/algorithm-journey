package class199;

// 城市环路，java版
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

	public static boolean[] vis = new boolean[MAXN];
	public static int start1, start2, skipEdge;

	public static int[][] dp = new int[MAXN][2];

	public static void addEdge(int u, int v) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		head[u] = cntg;
	}

	// 递归版
	public static boolean dfs1(int u, int preEdge) {
		vis[u] = true;
		boolean ans = false;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			if (e != (preEdge ^ 1)) {
				int v = to[e];
				if (vis[v] && start1 == 0) {
					start1 = v;
					start2 = u;
					skipEdge = e >> 1;
					ans = true;
				}
				if (!vis[v] && dfs1(v, e) && u != start1) {
					ans = true;
				}
			}
		}
		return ans;
	}

	// 递归版
	public static void dpOnTree1(int u, int fa) {
		dp[u][0] = 0;
		dp[u][1] = arr[u];
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (v != fa && (e >> 1) != skipEdge) {
				dpOnTree1(v, u);
				dp[u][0] += Math.max(dp[v][0], dp[v][1]);
				dp[u][1] += dp[v][0];
			}
		}
	}

	public static int[] sta = new int[MAXN];
	public static int[] pree = new int[MAXN];
	public static int[] iter = new int[MAXN];
	public static boolean[] staAns = new boolean[MAXN];

	// dfs1的迭代版
	public static boolean dfs2(int root, int preEdge) {
		int top = 0;
		sta[++top] = root;
		pree[root] = preEdge;
		iter[root] = head[root];
		staAns[top] = false;
		vis[root] = true;
		boolean ans = false;
		while (top > 0) {
			int u = sta[top];
			int e = iter[u];
			if (e == 0) {
				ans = staAns[top];
				top--;
				if (top > 0) {
					int father = sta[top];
					if (ans && father != start1) {
						staAns[top] = true;
					}
				}
			} else {
				iter[u] = nxt[e];
				if (e != (pree[u] ^ 1)) {
					int v = to[e];
					if (vis[v] && start1 == 0) {
						start1 = v;
						start2 = u;
						skipEdge = e >> 1;
						staAns[top] = true;
					}
					if (!vis[v]) {
						vis[v] = true;
						pree[v] = e;
						iter[v] = head[v];
						sta[++top] = v;
						staAns[top] = false;
					}
				}
			}
		}
		return ans;
	}

	public static int[][] ufe = new int[MAXN][3];
	public static int stacksize, u, f, e;

	public static void push(int u, int f, int e) {
		ufe[stacksize][0] = u;
		ufe[stacksize][1] = f;
		ufe[stacksize][2] = e;
		stacksize++;
	}

	public static void pop() {
		--stacksize;
		u = ufe[stacksize][0];
		f = ufe[stacksize][1];
		e = ufe[stacksize][2];
	}

	// dpOnTree1的迭代版
	public static void dpOnTree2(int cur, int father) {
		stacksize = 0;
		push(cur, father, -1);
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
				push(u, f, e);
				if (to[e] != f && (e >> 1) != skipEdge) {
					push(to[e], u, -1);
				}
			} else {
				for (int ei = head[u]; ei > 0; ei = nxt[ei]) {
					int v = to[ei];
					if (v != f && (ei >> 1) != skipEdge) {
						dp[u][0] += Math.max(dp[v][0], dp[v][1]);
						dp[u][1] += dp[v][0];
					}
				}
			}
		}
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
		start1 = start2 = 0;
		// dfs1(1, 0);
		dfs2(1, 0);
		// dpOnTree1(start1, 0);
		dpOnTree2(start1, 0);
		int ans = dp[start1][0];
		// dpOnTree1(start2, 0);
		dpOnTree2(start2, 0);
		ans = Math.max(ans, dp[start2][0]);
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
