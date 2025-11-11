package class183;

// 权值和为k的路径的最少边数，java版
// 测试链接 : https://www.luogu.com.cn/problem/P4149
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

public class Code03_Race1 {

	public static int MAXN = 200001;
	public static int MAXK = 1000001;
	public static int INF = 1000000001;
	public static int n, k, total;

	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXN << 1];
	public static int[] to = new int[MAXN << 1];
	public static int[] weight = new int[MAXN << 1];
	public static int cntg;

	public static boolean[] vis = new boolean[MAXN];
	public static int[] siz = new int[MAXN];
	public static int[] maxPart = new int[MAXN];
	public static int centroid;

	public static int[] sumArr = new int[MAXN];
	public static int[] edgeArr = new int[MAXN];
	public static int cnta;

	public static int[] dp = new int[MAXK];

	// java的实现，getCentroid方法、getPath方法改成迭代才能通过，C++的实现不需要改动
	// 讲解118，讲了如何把递归函数改成迭代
	public static int[][] stack = new int[MAXN][5];
	public static int stacksize, u, f, sum, edge, e;

	public static void push(int u, int f, int sum, int edge, int e) {
		stack[stacksize][0] = u;
		stack[stacksize][1] = f;
		stack[stacksize][2] = sum;
		stack[stacksize][3] = edge;
		stack[stacksize][4] = e;
		stacksize++;
	}

	public static void pop() {
		--stacksize;
		u = stack[stacksize][0];
		f = stack[stacksize][1];
		sum = stack[stacksize][2];
		edge = stack[stacksize][3];
		e = stack[stacksize][4];
	}

	public static void addEdge(int u, int v, int w) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		weight[cntg] = w;
		head[u] = cntg;
	}

	// 找重心的递归版，java会爆栈，C++可以通过
	public static void getCentroid1(int u, int fa) {
		siz[u] = 1;
		maxPart[u] = 0;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (v != fa && !vis[v]) {
				getCentroid1(v, u);
				siz[u] += siz[v];
				maxPart[u] = Math.max(siz[v], maxPart[u]);
			}
		}
		maxPart[u] = Math.max(maxPart[u], total - siz[u]);
		if (centroid == 0 || maxPart[u] < maxPart[centroid]) {
			centroid = u;
		}
	}

	// 找重心的迭代版
	public static void getCentroid2(int cur, int fa) {
		stacksize = 0;
		push(cur, fa, 0, 0, -1);
		while (stacksize > 0) {
			pop();
			if (e == -1) {
				siz[u] = 1;
				maxPart[u] = 0;
				e = head[u];
			} else {
				e = nxt[e];
			}
			if (e != 0) {
				push(u, f, 0, 0, e);
				int v = to[e];
				if (v != f && !vis[v]) {
					push(to[e], u, 0, 0, -1);
				}
			} else {
				for (int ei = head[u]; ei > 0; ei = nxt[ei]) {
					int v = to[ei];
					if (v != f && !vis[v]) {
						siz[u] += siz[v];
						maxPart[u] = Math.max(siz[v], maxPart[u]);
					}
				}
				maxPart[u] = Math.max(maxPart[u], total - siz[u]);
				if (centroid == 0 || maxPart[u] < maxPart[centroid]) {
					centroid = u;
				}
			}
		}
	}

	// 收集路径的递归版，java会爆栈，C++可以通过
	public static void getPath1(int u, int fa, int sum, int edge) {
		if (sum > k) {
			return;
		}
		sumArr[++cnta] = sum;
		edgeArr[cnta] = edge;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (v != fa && !vis[v]) {
				getPath1(v, u, sum + weight[e], edge + 1);
			}
		}
	}

	// 收集路径的迭代版
	public static void getPath2(int cur, int fa, int psum, int pedge) {
		stacksize = 0;
		push(cur, fa, psum, pedge, -1);
		while (stacksize > 0) {
			pop();
			if (e == -1) {
				if (sum > k) {
					continue;
				}
				sumArr[++cnta] = sum;
				edgeArr[cnta] = edge;
				e = head[u];
			} else {
				e = nxt[e];
			}
			if (e != 0) {
				push(u, f, sum, edge, e);
				int v = to[e];
				if (v != f && !vis[v]) {
					push(to[e], u, sum + weight[e], edge + 1, -1);
				}
			}
		}
	}

	public static int calc(int u) {
		int ans = INF;
		cnta = 0;
		dp[0] = 0;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (!vis[v]) {
				int tmp = cnta;
				// getPath1(v, u, weight[e], 1);
				getPath2(v, u, weight[e], 1);
				for (int i = tmp + 1; i <= cnta; i++) {
					ans = Math.min(ans, dp[k - sumArr[i]] + edgeArr[i]);
				}
				for (int i = tmp + 1; i <= cnta; i++) {
					dp[sumArr[i]] = Math.min(dp[sumArr[i]], edgeArr[i]);
				}
			}
		}
		for (int i = 1; i <= cnta; i++) {
			dp[sumArr[i]] = INF;
		}
		return ans;
	}

	public static int compute(int u) {
		int ans = INF;
		vis[u] = true;
		ans = Math.min(ans, calc(u));
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (!vis[v]) {
				total = siz[v];
				centroid = 0;
				// getCentroid1(v, 0);
				getCentroid2(v, 0);
				ans = Math.min(ans, compute(centroid));
			}
		}
		return ans;
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		k = in.nextInt();
		for (int i = 1, u, v, w; i < n; i++) {
			u = in.nextInt() + 1;
			v = in.nextInt() + 1;
			w = in.nextInt();
			addEdge(u, v, w);
			addEdge(v, u, w);
		}
		total = n;
		centroid = 0;
		// getCentroid1(1, 0);
		getCentroid2(1, 0);
		Arrays.fill(dp, INF);
		int ans = compute(centroid);
		if (ans == INF) {
			ans = -1;
		}
		out.println(ans);
		out.flush();
		out.close();
	}

	// 读写工具类
	static class FastReader {
		private final byte[] buffer = new byte[1 << 20];
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
