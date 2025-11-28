package class184;

// 首都，java版
// 一共有n个节点，给定n-1条边，所有节点组成一棵树
// 给定长度为n的数组color，color[i]表示i号节点的颜色，颜色有k种
// 你需要在树上找到一个连通区，连通区内出现的每种颜色，在连通区外不存在
// 这样的连通区可能有多个，希望包含的颜色数量尽量少，打印(最少颜色数 - 1)的结果
// 1 <= n、k <= 2 * 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P7215
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code01_Capital1 {

	public static int MAXN = 200001;
	public static int INF = 1000000001;
	public static int n, k;
	public static int[] color = new int[MAXN];

	// 建树的链式前向星
	public static int[] headg = new int[MAXN];
	public static int[] nextg = new int[MAXN << 1];
	public static int[] tog = new int[MAXN << 1];
	public static int cntg;

	// 颜色拥有的节点列表
	public static int[] headc = new int[MAXN];
	public static int[] nextc = new int[MAXN];
	public static int[] toc = new int[MAXN];
	public static int cntc;

	// 点分治
	public static boolean[] vis = new boolean[MAXN];
	public static int[] siz = new int[MAXN];

	// father[x] = y，表示x此时的父亲节点是y
	public static int[] father = new int[MAXN];
	// nodeRoot[x] = y，表示x此时的重心是y
	public static int[] nodeRoot = new int[MAXN];

	// 宽度优先遍历的队列
	public static int[] que = new int[MAXN];
	// 节点是否进过队列
	public static boolean[] nodeVis = new boolean[MAXN];
	// 颜色是否讨论过
	public static boolean[] colorVis = new boolean[MAXN];

	// 讲解118，递归函数改成迭代所需要的栈
	public static int[][] stack = new int[MAXN][4];
	public static int u, f, rt, e;
	public static int stacksize;

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

	// 得到子树大小递归版，java会爆栈，C++可以通过
	public static void getSize1(int u, int fa) {
		siz[u] = 1;
		for (int e = headg[u]; e > 0; e = nextg[e]) {
			int v = tog[e];
			if (v != fa && !vis[v]) {
				getSize1(v, u);
				siz[u] += siz[v];
			}
		}
	}

	// 得到子树大小迭代版
	public static void getSize2(int cur, int fa) {
		stacksize = 0;
		push(cur, fa, 0, -1);
		while (stacksize > 0) {
			pop();
			if (e == -1) {
				siz[u] = 1;
				e = headg[u];
			} else {
				e = nextg[e];
			}
			if (e != 0) {
				push(u, f, 0, e);
				int v = tog[e];
				if (v != f && !vis[v]) {
					push(v, u, 0, -1);
				}
			} else {
				for (int ei = headg[u]; ei > 0; ei = nextg[ei]) {
					int v = tog[ei];
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
			for (int e = headg[u]; e > 0; e = nextg[e]) {
				int v = tog[e];
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
	public static void dfs1(int u, int fa, int rt) {
		father[u] = fa;
		nodeRoot[u] = rt;
		nodeVis[u] = false;
		colorVis[color[u]] = false;
		for (int e = headg[u]; e > 0; e = nextg[e]) {
			int v = tog[e];
			if (v != fa && !vis[v]) {
				dfs1(v, u, rt);
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
				nodeRoot[u] = rt;
				nodeVis[u] = false;
				colorVis[color[u]] = false;
				e = headg[u];
			} else {
				e = nextg[e];
			}
			if (e != 0) {
				push(u, f, rt, e);
				int v = tog[e];
				if (v != f && !vis[v]) {
					push(v, u, rt, -1);
				}
			}
		}
	}

	public static int calc(int u) {
		// dfs1(u, 0, u);
		dfs2(u, 0, u);
		int l = 1, r = 0;
		que[++r] = u;
		nodeVis[u] = true;
		int ans = 0;
		while (l <= r) {
			int cur = que[l++];
			if (cur != u && !nodeVis[father[cur]]) {
				que[++r] = father[cur];
				nodeVis[father[cur]] = true;
			}
			if (!colorVis[color[cur]]) {
				colorVis[color[cur]] = true;
				ans++;
				for (int e = headc[color[cur]]; e > 0; e = nextc[e]) {
					int v = toc[e];
					if (nodeRoot[v] != u) {
						return INF;
					}
					if (!nodeVis[v]) {
						que[++r] = v;
						nodeVis[v] = true;
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
		int ans = solve(getCentroid(1, 0));
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
