package class194;

// 道路相遇，java版
// 测试链接 : https://www.luogu.com.cn/problem/P4320
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code01_RoadsMeet1 {

	public static int MAXN = 500001;
	public static int MAXM = 1000001;
	public static int MAXP = 20;
	public static int n, m, q;

	public static int[] head = new int[MAXN << 1];
	public static int[] nxt = new int[MAXM << 2];
	public static int[] to = new int[MAXM << 2];
	public static int cntg;

	public static int[] dfn = new int[MAXN];
	public static int[] low = new int[MAXN];
	public static int cntd;

	public static int[] sta = new int[MAXN];
	public static int top;

	public static int[] vbccArr = new int[MAXN << 1];
	public static int[] vbccl = new int[MAXN];
	public static int[] vbccr = new int[MAXN];
	public static int idx;
	public static int vbccCnt;

	public static int[] dep = new int[MAXN << 1];
	public static int[][] stjump = new int[MAXN << 1][MAXP];

	// 迭代版需要的栈，讲解118讲了递归改迭代的技巧
	public static int[][] stack = new int[MAXN][5];
	public static int u, root, status, f, e;
	public static int stacksize;

	public static void push(int u, int root, int status, int f, int e) {
		stack[stacksize][0] = u;
		stack[stacksize][1] = root;
		stack[stacksize][2] = status;
		stack[stacksize][3] = f;
		stack[stacksize][4] = e;
		stacksize++;
	}

	public static void pop() {
		stacksize--;
		u = stack[stacksize][0];
		root = stack[stacksize][1];
		status = stack[stacksize][2];
		f = stack[stacksize][3];
		e = stack[stacksize][4];
	}

	public static void addEdge(int u, int v) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		head[u] = cntg;
	}

	// 递归版，每个点双连通分量收集拥有的节点
	public static void tarjan1(int u, boolean root) {
		dfn[u] = low[u] = ++cntd;
		sta[++top] = u;
		if (root && head[u] == 0) {
			vbccCnt++;
			vbccArr[++idx] = u;
			vbccl[vbccCnt] = vbccr[vbccCnt] = idx;
			return;
		}
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (dfn[v] == 0) {
				tarjan1(v, false);
				low[u] = Math.min(low[u], low[v]);
				if (low[v] == dfn[u]) {
					vbccCnt++;
					vbccArr[++idx] = u;
					vbccl[vbccCnt] = idx;
					int pop;
					do {
						pop = sta[top--];
						vbccArr[++idx] = pop;
					} while (pop != v);
					vbccr[vbccCnt] = idx;
				}
			} else {
				low[u] = Math.min(low[u], dfn[v]);
			}
		}
	}

	// 迭代版，每个点双连通分量收集拥有的节点
	public static void tarjan2(int node, boolean rt) {
		stacksize = 0;
		push(node, rt ? 1 : 0, -1, 0, -1);
		int v;
		while (stacksize > 0) {
			pop();
			if (status == -1) {
				dfn[u] = low[u] = ++cntd;
				sta[++top] = u;
				if (root == 1 && head[u] == 0) {
					vbccCnt++;
					vbccArr[++idx] = u;
					vbccl[vbccCnt] = vbccr[vbccCnt] = idx;
					continue;
				} else {
					e = head[u];
				}
			} else {
				v = to[e];
				if (status == 0) {
					low[u] = Math.min(low[u], low[v]);
					if (low[v] == dfn[u]) {
						vbccCnt++;
						vbccArr[++idx] = u;
						vbccl[vbccCnt] = idx;
						int pop;
						do {
							pop = sta[top--];
							vbccArr[++idx] = pop;
						} while (pop != v);
						vbccr[vbccCnt] = idx;
					}
				} else {
					low[u] = Math.min(low[u], dfn[v]);
				}
				e = nxt[e];
			}
			if (e != 0) {
				v = to[e];
				if (dfn[v] == 0) {
					push(u, root, 0, 0, e);
					push(v, 0, -1, 0, -1);
				} else {
					push(u, root, 1, 0, e);
				}
			}
		}
	}

	// 根据点双连通分量拥有的节点建立圆方树
	public static void condense() {
		cntg = 0;
		for (int i = 1; i <= n; i++) {
			head[i] = 0;
		}
		for (int i = 1, vbcc = 1 + n; i <= vbccCnt; i++, vbcc++) {
			for (int j = vbccl[i]; j <= vbccr[i]; j++) {
				addEdge(vbcc, vbccArr[j]);
				addEdge(vbccArr[j], vbcc);
			}
		}
	}

	// 递归版，圆方树上建立深度和倍增表
	public static void dfs1(int u, int fa) {
		dep[u] = dep[fa] + 1;
		stjump[u][0] = fa;
		for (int p = 1; p < MAXP; p++) {
			stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
		}
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (v != fa) {
				dfs1(v, u);
			}
		}
	}

	// 迭代版，圆方树上建立深度和倍增表
	public static void dfs2(int cur, int fa) {
		stacksize = 0;
		push(cur, 0, 0, fa, -1);
		while (stacksize > 0) {
			pop();
			if (e == -1) {
				dep[u] = dep[f] + 1;
				stjump[u][0] = f;
				for (int p = 1; p < MAXP; p++) {
					stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
				}
				e = head[u];
			} else {
				e = nxt[e];
			}
			if (e != 0) {
				push(u, 0, 0, f, e);
				if (to[e] != f) {
					push(to[e], 0, 0, u, -1);
				}
			}
		}
	}

	// 圆方树上任意两点的最低公共祖先
	public static int getLca(int a, int b) {
		if (dep[a] < dep[b]) {
			int tmp = a;
			a = b;
			b = tmp;
		}
		for (int p = MAXP - 1; p >= 0; p--) {
			if (dep[stjump[a][p]] >= dep[b]) {
				a = stjump[a][p];
			}
		}
		if (a == b) {
			return a;
		}
		for (int p = MAXP - 1; p >= 0; p--) {
			if (stjump[a][p] != stjump[b][p]) {
				a = stjump[a][p];
				b = stjump[b][p];
			}
		}
		return stjump[a][0];
	}

	// 圆方树上任意两点间的距离
	public static int getDist(int x, int y) {
		return dep[x] + dep[y] - 2 * dep[getLca(x, y)];
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		for (int i = 1, u, v; i <= m; i++) {
			u = in.nextInt();
			v = in.nextInt();
			addEdge(u, v);
			addEdge(v, u);
		}
		// tarjan1(1, true);
		tarjan2(1, true);
		condense();
		// dfs1(1, 0);
		dfs2(1, 0);
		q = in.nextInt();
		for (int i = 1, x, y; i <= q; i++) {
			x = in.nextInt();
			y = in.nextInt();
			out.println(getDist(x, y) / 2 + 1);
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
