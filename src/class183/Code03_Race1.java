package class183;

// 路径权值和为k的最少边数，java版
// 一共有n个节点，给定n-1条边，每条边有边权，所有节点组成一棵树
// 给定数字k，要求找到路径权值和等于k的简单路径，并且边的数量最小
// 打印这个最小的边数，如果不存在路径权值和等于k的简单路径，那么打印-1
// 注意，本题给定的点的编号从0开始
// 1 <= n <= 2 * 10^5
// 0 <= 边权、k <= 10^6
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
	public static int n, k;

	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXN << 1];
	public static int[] to = new int[MAXN << 1];
	public static int[] weight = new int[MAXN << 1];
	public static int cntg;

	public static boolean[] vis = new boolean[MAXN];
	public static int[] siz = new int[MAXN];

	// 从u出发到当前子树的节点，收集(路径权值和, 路径边数)
	public static int[] curDis = new int[MAXN];
	public static int[] curEdge = new int[MAXN];
	public static int cntc;

	// 从u出发到之前子树的节点，收集路径权值和
	public static int[] allDis = new int[MAXN];
	public static int cnta;

	// dp[s]表示，从u出发到之前子树的节点，路径权值和为s的路径，最少边数是多少
	public static int[] dp = new int[MAXK];

	// 讲解118，递归函数改成迭代所需要的栈
	public static int[][] stack = new int[MAXN][5];
	public static int u, f, dis, edge, e;
	public static int stacksize;

	public static void push(int u, int f, int dis, int edge, int e) {
		stack[stacksize][0] = u;
		stack[stacksize][1] = f;
		stack[stacksize][2] = dis;
		stack[stacksize][3] = edge;
		stack[stacksize][4] = e;
		stacksize++;
	}

	public static void pop() {
		--stacksize;
		u = stack[stacksize][0];
		f = stack[stacksize][1];
		dis = stack[stacksize][2];
		edge = stack[stacksize][3];
		e = stack[stacksize][4];
	}

	public static void addEdge(int u, int v, int w) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		weight[cntg] = w;
		head[u] = cntg;
	}

	// 得到子树大小递归版，java会爆栈，C++可以通过
	public static void getSize1(int u, int fa) {
		siz[u] = 1;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (v != fa && !vis[v]) {
				getSize1(v, u);
				siz[u] += siz[v];
			}
		}
	}

	// 得到子树大小迭代版
	public static void getSize2(int cur, int fa) {
		stacksize = 0;
		push(cur, fa, 0, 0, -1);
		while (stacksize > 0) {
			pop();
			if (e == -1) {
				siz[u] = 1;
				e = head[u];
			} else {
				e = nxt[e];
			}
			if (e != 0) {
				push(u, f, 0, 0, e);
				int v = to[e];
				if (v != f && !vis[v]) {
					push(v, u, 0, 0, -1);
				}
			} else {
				for (int ei = head[u]; ei > 0; ei = nxt[ei]) {
					int v = to[ei];
					if (v != f && !vis[v]) {
						siz[u] += siz[v];
					}
				}
			}
		}
	}

	public static int getCentroid(int u, int fa) {
		// getSize1(u, fa);
		getSize2(u, fa);
		int half = siz[u] >> 1;
		boolean find = false;
		while (!find) {
			find = true;
			for (int e = head[u]; e > 0; e = nxt[e]) {
				int v = to[e];
				if (v != fa && !vis[v] && siz[v] > half) {
					fa = u;
					u = v;
					find = false;
					break;
				}
			}
		}
		return u;
	}

	// 收集信息递归版，java会爆栈，C++可以通过
	public static void dfs1(int u, int fa, int dis, int edge) {
		if (dis > k) {
			return;
		}
		curDis[++cntc] = dis;
		curEdge[cntc] = edge;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (v != fa && !vis[v]) {
				dfs1(v, u, dis + weight[e], edge + 1);
			}
		}
	}

	// 收集信息迭代版
	public static void dfs2(int cur, int fa, int pathDis, int pathEdge) {
		stacksize = 0;
		push(cur, fa, pathDis, pathEdge, -1);
		while (stacksize > 0) {
			pop();
			if (e == -1) {
				if (dis > k) {
					continue;
				}
				curDis[++cntc] = dis;
				curEdge[cntc] = edge;
				e = head[u];
			} else {
				e = nxt[e];
			}
			if (e != 0) {
				push(u, f, dis, edge, e);
				int v = to[e];
				if (v != f && !vis[v]) {
					push(to[e], u, dis + weight[e], edge + 1, -1);
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
				cntc = 0;
				// dfs1(v, u, weight[e], 1);
				dfs2(v, u, weight[e], 1);
				for (int i = 1; i <= cntc; i++) {
					ans = Math.min(ans, dp[k - curDis[i]] + curEdge[i]);
				}
				for (int i = 1; i <= cntc; i++) {
					allDis[++cnta] = curDis[i];
					dp[curDis[i]] = Math.min(dp[curDis[i]], curEdge[i]);
				}
			}
		}
		for (int i = 1; i <= cnta; i++) {
			dp[allDis[i]] = INF;
		}
		return ans;
	}

	public static int solve(int u) {
		vis[u] = true;
		int ans = calc(u);
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (!vis[v]) {
				ans = Math.min(ans, solve(getCentroid(v, u)));
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
		Arrays.fill(dp, INF);
		int ans = solve(getCentroid(1, 0));
		if (ans == INF) {
			ans = -1;
		}
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
