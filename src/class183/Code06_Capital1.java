package class183;

// 首都，java版
// 测试链接 : https://www.luogu.com.cn/problem/P7215
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code06_Capital1 {

	public static int MAXN = 200001;
	public static int INF = 1000000001;
	public static int n, k;
	public static int[] color = new int[MAXN];

	public static int[] headg = new int[MAXN];
	public static int[] nextg = new int[MAXN << 1];
	public static int[] tog = new int[MAXN << 1];
	public static int cntg;

	public static int[] headc = new int[MAXN];
	public static int[] nextc = new int[MAXN];
	public static int[] toc = new int[MAXN];
	public static int cntc;

	public static boolean[] vis = new boolean[MAXN];
	public static int[] siz = new int[MAXN];
	public static int[] maxPart = new int[MAXN];
	public static int total;
	public static int centroid;

	public static int[] father = new int[MAXN];
	public static int[] nodeStamp = new int[MAXN];
	public static int[] colorStamp = new int[MAXN];
	public static int[] que = new int[MAXN];
	public static boolean[] enter = new boolean[MAXN];

	// 讲解118，递归函数改成迭代所需要的栈
	public static int[][] stack = new int[MAXN][4];
	public static int stacksize, u, f, rt, e;

	public static void push(int u, int f, int rt, int e) {
		stack[stacksize][0] = u;
		stack[stacksize][1] = f;
		stack[stacksize][2] = rt;
		stack[stacksize][3] = e;
		stacksize++;
	}

	public static void pop() {
		--stacksize;
		u = stack[stacksize][0];
		f = stack[stacksize][1];
		rt = stack[stacksize][2];
		e = stack[stacksize][3];
	}

	public static void addEdge(int u, int v) {
		nextg[++cntg] = headg[u];
		tog[cntg] = v;
		headg[u] = cntg;
	}

	public static void addNode(int color, int node) {
		nextc[++cntc] = headc[color];
		toc[cntc] = node;
		headc[color] = cntc;
	}

	// 找重心的递归版，java会爆栈，C++可以通过
	public static void getCentroid1(int u, int fa) {
		siz[u] = 1;
		maxPart[u] = 0;
		for (int e = headg[u]; e > 0; e = nextg[e]) {
			int v = tog[e];
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
		push(cur, fa, 0, -1);
		while (stacksize > 0) {
			pop();
			if (e == -1) {
				siz[u] = 1;
				maxPart[u] = 0;
				e = headg[u];
			} else {
				e = nextg[e];
			}
			if (e != 0) {
				push(u, f, 0, e);
				int v = tog[e];
				if (v != f && !vis[v]) {
					push(tog[e], u, 0, -1);
				}
			} else {
				for (int ei = headg[u]; ei > 0; ei = nextg[ei]) {
					int v = tog[ei];
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

	// 收集信息递归版，java会爆栈，C++可以通过
	public static void dfs1(int u, int fa, int root) {
		father[u] = fa;
		nodeStamp[u] = root;
		enter[u] = false;
		for (int e = headg[u]; e > 0; e = nextg[e]) {
			int v = tog[e];
			if (v != fa && !vis[v]) {
				dfs1(v, u, root);
			}
		}
	}

	// 收集信息迭代版
	public static void dfs2(int cur, int fa, int root) {
		stacksize = 0;
		push(cur, fa, root, -1);
		while (stacksize > 0) {
			pop();
			if (e == -1) {
				father[u] = f;
				nodeStamp[u] = rt;
				enter[u] = false;
				e = headg[u];
			} else {
				e = nextg[e];
			}
			if (e != 0) {
				push(u, f, rt, e);
				int v = tog[e];
				if (v != f && !vis[v]) {
					push(tog[e], u, rt, -1);
				}
			}
		}
	}

	public static int calc(int u) {
		// dfs1(u, 0, u);
		dfs2(u, 0, u);
		int l = 1, r = 0;
		que[++r] = u;
		enter[u] = true;
		int ans = 0;
		while (l <= r) {
			int cur = que[l++];
			if (cur != u && !enter[father[cur]]) {
				que[++r] = father[cur];
				enter[father[cur]] = true;
			}
			if (colorStamp[color[cur]] != u) {
				colorStamp[color[cur]] = u;
				ans++;
				for (int e = headc[color[cur]]; e > 0; e = nextc[e]) {
					int v = toc[e];
					if (nodeStamp[v] != u) {
						return INF;
					}
					if (!enter[v]) {
						que[++r] = v;
						enter[v] = true;
					}
				}
			}
		}
		return ans;
	}

	public static int solve(int u) {
		vis[u] = true;
		int ans = calc(u);
		for (int e = headg[u]; e > 0; e = nextg[e]) {
			int v = tog[e];
			if (!vis[v]) {
				total = siz[v];
				centroid = 0;
				// getCentroid1(v, u);
				getCentroid2(v, u);
				ans = Math.min(ans, solve(centroid));
			}
		}
		return ans;
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		k = in.nextInt();
		for (int i = 1, u, v; i < n; i++) {
			u = in.nextInt();
			v = in.nextInt();
			addEdge(u, v);
			addEdge(v, u);
		}
		for (int i = 1; i <= n; i++) {
			color[i] = in.nextInt();
			addNode(color[i], i);
		}
		total = n;
		centroid = 0;
		// getCentroid1(1, 0);
		getCentroid2(1, 0);
		int ans = solve(centroid);
		out.println(ans - 1);
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
