package class184;

// 树的难题，java版
// 一共有n个节点，给定n-1条边，每条边给定颜色，所有节点组成一棵树
// 颜色一共有m种，val[i]表示第i种颜色的权值，可能为负数
// 树上的一条简单路径，依次经过的边收集其颜色，可以组成一个颜色序列
// 颜色序列划分成若干个连续同色段，比如AABAACC，有4个连续同色段
// 每个连续同色段只算一次颜色权值，颜色权值的累加和作为路径的权
// 请计算边数在[limitl, limitr]范围的所有路径中，最大的权是多少
// 1 <= n、m <= 2 * 10^5
// 题目保证一定存在边数在[limitl, limitr]的路径
// 测试链接 : https://www.luogu.com.cn/problem/P3714
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

public class Code02_Difficult1 {

	public static int MAXN = 200001;
	public static long INF = 1L << 60;
	public static int n, m, limitl, limitr;
	public static int[] val = new int[MAXN];

	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXN << 1];
	public static int[] to = new int[MAXN << 1];
	public static int[] color = new int[MAXN << 1];
	public static int cntg;

	// 之前颜色的子树形成的线段树，维护最大值信息
	public static long[] preTree = new long[MAXN << 2];
	// 当前颜色的子树形成的线段树，维护最大值信息
	public static long[] curTree = new long[MAXN << 2];

	public static boolean[] vis = new boolean[MAXN];
	public static int[] siz = new int[MAXN];

	// 每条边信息(连接的点，边的颜色)，根据边的颜色排序
	public static int[][] edgeArr = new int[MAXN][2];
	public static int cnte;

	public static int[] edgeCnt = new int[MAXN];
	public static long[] pathSum = new long[MAXN];

	public static int[] subtreeNode = new int[MAXN];
	public static int cnts;
	public static int[] colorNode = new int[MAXN];
	public static int cntc;

	// 讲解118，递归函数改成迭代所需要的栈
	public static int[][] stack = new int[MAXN][5];
	public static long[] sumst = new long[MAXN];
	public static int u, f, preColor, edge, e;
	public static long sum;
	public static int stacksize;

	public static void push(int u, int f, int preColor, int edge, long sum, int e) {
		stack[stacksize][0] = u;
		stack[stacksize][1] = f;
		stack[stacksize][2] = preColor;
		stack[stacksize][3] = edge;
		stack[stacksize][4] = e;
		sumst[stacksize] = sum;
		stacksize++;
	}

	public static void pop() {
		--stacksize;
		u = stack[stacksize][0];
		f = stack[stacksize][1];
		preColor = stack[stacksize][2];
		edge = stack[stacksize][3];
		e = stack[stacksize][4];
		sum = sumst[stacksize];
	}

	public static void addEdge(int u, int v, int c) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		color[cntg] = c;
		head[u] = cntg;
	}

	public static void build(long[] tree, int l, int r, int i) {
		tree[i] = -INF;
		if (l < r) {
			int mid = (l + r) >> 1;
			build(tree, l, mid, i << 1);
			build(tree, mid + 1, r, i << 1 | 1);
		}
	}

	public static void clear(long[] tree, int l, int r, int i) {
		if (tree[i] == -INF) {
			return;
		}
		tree[i] = -INF;
		if (l < r) {
			int mid = (l + r) >> 1;
			clear(tree, l, mid, i << 1);
			clear(tree, mid + 1, r, i << 1 | 1);
		}
	}

	public static void update(long[] tree, int jobi, long jobv, int l, int r, int i) {
		if (l == r) {
			tree[i] = Math.max(tree[i], jobv);
		} else {
			int mid = (l + r) >> 1;
			if (jobi <= mid) {
				update(tree, jobi, jobv, l, mid, i << 1);
			} else {
				update(tree, jobi, jobv, mid + 1, r, i << 1 | 1);
			}
			tree[i] = Math.max(tree[i << 1], tree[i << 1 | 1]);
		}
	}

	public static long query(long[] tree, int jobl, int jobr, int l, int r, int i) {
		if (jobl <= l && r <= jobr) {
			return tree[i];
		}
		int mid = (l + r) >> 1;
		long ans = -INF;
		if (jobl <= mid) {
			ans = Math.max(ans, query(tree, jobl, jobr, l, mid, i << 1));
		}
		if (jobr > mid) {
			ans = Math.max(ans, query(tree, jobl, jobr, mid + 1, r, i << 1 | 1));
		}
		return ans;
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
		push(cur, fa, 0, 0, 0, -1);
		while (stacksize > 0) {
			pop();
			if (e == -1) {
				siz[u] = 1;
				e = head[u];
			} else {
				e = nxt[e];
			}
			if (e != 0) {
				push(u, f, 0, 0, 0, e);
				int v = to[e];
				if (v != f && !vis[v]) {
					push(v, u, 0, 0, 0, -1);
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
	public static void dfs1(int u, int fa, int preColor, int edge, long sum) {
		if (edge > limitr) {
			return;
		}
		edgeCnt[u] = edge;
		pathSum[u] = sum;
		subtreeNode[++cnts] = u;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			int c = color[e];
			if (v != fa && !vis[v]) {
				dfs1(v, u, c, edge + 1, sum + (preColor == c ? 0 : val[c]));
			}
		}
	}

	// 收集信息迭代版
	public static void dfs2(int cur, int fa, int pcolor, int pedge, long psum) {
		stacksize = 0;
		push(cur, fa, pcolor, pedge, psum, -1);
		while (stacksize > 0) {
			pop();
			if (e == -1) {
				if (edge > limitr) {
					continue;
				}
				edgeCnt[u] = edge;
				pathSum[u] = sum;
				subtreeNode[++cnts] = u;
				e = head[u];
			} else {
				e = nxt[e];
			}
			if (e != 0) {
				push(u, f, preColor, edge, sum, e);
				int v = to[e];
				int c = color[e];
				if (v != f && !vis[v]) {
					push(v, u, c, edge + 1, sum + (preColor == c ? 0 : val[c]), -1);
				}
			}
		}
	}

	public static long calc(int u) {
		cnte = 0;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			int c = color[e];
			if (!vis[v]) {
				edgeArr[++cnte][0] = v;
				edgeArr[cnte][1] = c;
			}
		}
		Arrays.sort(edgeArr, 1, cnte + 1, (a, b) -> a[1] - b[1]);
		update(preTree, 0, 0, 0, n, 1);
		long ans = -INF;
		cntc = 0;
		for (int k = 1; k <= cnte; k++) {
			int v = edgeArr[k][0];
			int c = edgeArr[k][1];
			if (k > 1 && edgeArr[k - 1][1] != c) {
				clear(curTree, 0, n, 1);
				for (int i = 1; i <= cntc; i++) {
					int node = colorNode[i];
					update(preTree, edgeCnt[node], pathSum[node], 0, n, 1);
				}
				cntc = 0;
			}
			cnts = 0;
			// dfs1(v, u, c, 1, val[c]);
			dfs2(v, u, c, 1, val[c]);
			for (int i = 1; i <= cnts; i++) {
				int node = subtreeNode[i];
				int l = Math.max(0, limitl - edgeCnt[node]);
				int r = limitr - edgeCnt[node];
				ans = Math.max(ans, query(preTree, l, r, 0, n, 1) + pathSum[node]);
				ans = Math.max(ans, query(curTree, l, r, 0, n, 1) + pathSum[node] - val[c]);
			}
			for (int i = 1; i <= cnts; i++) {
				int node = subtreeNode[i];
				colorNode[++cntc] = node;
				update(curTree, edgeCnt[node], pathSum[node], 0, n, 1);
			}
		}
		clear(preTree, 0, n, 1);
		clear(curTree, 0, n, 1);
		return ans;
	}

	public static long solve(int u) {
		vis[u] = true;
		long ans = calc(u);
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (!vis[v]) {
				ans = Math.max(ans, solve(getCentroid(v, u)));
			}
		}
		return ans;
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		limitl = in.nextInt();
		limitr = in.nextInt();
		for (int i = 1; i <= m; i++) {
			val[i] = in.nextInt();
		}
		for (int i = 1, u, v, c; i < n; i++) {
			u = in.nextInt();
			v = in.nextInt();
			c = in.nextInt();
			addEdge(u, v, c);
			addEdge(v, u, c);
		}
		build(preTree, 0, n, 1);
		build(curTree, 0, n, 1);
		out.println(solve(getCentroid(1, 0)));
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
