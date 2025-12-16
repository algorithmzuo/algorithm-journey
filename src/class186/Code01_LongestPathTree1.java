package class186;

// 最长道路tree，java版
// 测试链接 : https://hydro.ac/p/bzoj-P2870
// 测试链接 : https://darkbzoj.cc/problem/2870
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code01_LongestPathTree1 {

	public static int MAXN = 200001;
	public static int MAXS = 1000001;
	public static int n;
	public static int[] arr = new int[MAXN];

	public static int[] headg = new int[MAXN];
	public static int[] nextg = new int[MAXN << 1];
	public static int[] tog = new int[MAXN << 1];
	public static int[] weightg = new int[MAXN << 1];
	public static int cntg;

	public static int[] sonCnt = new int[MAXN];
	public static int[] heads = new int[MAXN];
	public static int[] nexts = new int[MAXS];
	public static int[] sons = new int[MAXS];
	public static int cnts;

	public static boolean[] vis = new boolean[MAXN];
	public static int[] siz = new int[MAXN];

	public static int[] preEdge = new int[MAXN];
	public static int[] preMinv = new int[MAXN];
	public static int cntp;

	public static int[] curEdge = new int[MAXN];
	public static int[] curMinv = new int[MAXN];
	public static int cntc;

	// 讲解118，递归函数改成迭代所需要的栈
	public static int[][] stack = new int[MAXN][6];
	public static int u, f, edge, minv, op, e;
	public static int stacksize;

	public static void push(int u, int f, int edge, int minv, int op, int e) {
		stack[stacksize][0] = u;
		stack[stacksize][1] = f;
		stack[stacksize][2] = edge;
		stack[stacksize][3] = minv;
		stack[stacksize][4] = op;
		stack[stacksize][5] = e;
		stacksize++;
	}

	public static void pop() {
		--stacksize;
		u = stack[stacksize][0];
		f = stack[stacksize][1];
		edge = stack[stacksize][2];
		minv = stack[stacksize][3];
		op = stack[stacksize][4];
		e = stack[stacksize][5];
	}

	public static void addEdge(int u, int v, int w) {
		nextg[++cntg] = headg[u];
		tog[cntg] = v;
		weightg[cntg] = w;
		headg[u] = cntg;
	}

	public static void addSon(int u, int v) {
		sonCnt[u]++;
		nexts[++cnts] = heads[u];
		sons[cnts] = v;
		heads[u] = cnts;
	}

	// 生成孩子列表递归版，java会爆栈，C++可以通过
	public static void buildSon1(int u, int fa) {
		for (int e = headg[u]; e > 0; e = nextg[e]) {
			int v = tog[e];
			if (v != fa) {
				addSon(u, v);
				buildSon1(v, u);
			}
		}
	}

	// buildSon1的迭代版
	public static void buildSon2(int cur, int father) {
		stacksize = 0;
		push(cur, father, 0, 0, 0, -1);
		while (stacksize > 0) {
			pop();
			if (e == -1) {
				for (int ei = headg[u]; ei > 0; ei = nextg[ei]) {
					int v = tog[ei];
					if (v != f) {
						addSon(u, v);
					}
				}
				e = headg[u];
			} else {
				e = nextg[e];
			}
			if (e != 0) {
				push(u, f, 0, 0, 0, e);
				int v = tog[e];
				if (v != f) {
					push(v, u, 0, 0, 0, -1);
				}
			}
		}
	}

	public static void rebuildTree() {
		// buildSon1(1, 0);
		buildSon2(1, 0);
		cntg = 1;
		for (int u = 1; u <= n; u++) {
			headg[u] = 0;
		}
		for (int u = 1; u <= n; u++) {
			if (sonCnt[u] <= 2) {
				for (int e = heads[u]; e > 0; e = nexts[e]) {
					int son = sons[e];
					addEdge(u, son, 1);
					addEdge(son, u, 1);
				}
			} else {
				int addNode1 = ++n;
				int addNode2 = ++n;
				arr[addNode1] = arr[u];
				arr[addNode2] = arr[u];
				addEdge(u, addNode1, 0);
				addEdge(addNode1, u, 0);
				addEdge(u, addNode2, 0);
				addEdge(addNode2, u, 0);
				boolean add1 = true;
				for (int e = heads[u]; e > 0; e = nexts[e]) {
					int son = sons[e];
					if (add1) {
						addSon(addNode1, son);
					} else {
						addSon(addNode2, son);
					}
					add1 = !add1;
				}
			}
		}
	}

	// 得到子树大小递归版，java会爆栈，C++可以通过
	public static void getSize1(int u, int fa) {
		siz[u] = 1;
		for (int e = headg[u]; e > 0; e = nextg[e]) {
			int v = tog[e];
			if (v != fa && !vis[e >> 1]) {
				getSize1(v, u);
				siz[u] += siz[v];
			}
		}
	}

	// getSize1的迭代版
	public static void getSize2(int cur, int fa) {
		stacksize = 0;
		push(cur, fa, 0, 0, 0, -1);
		while (stacksize > 0) {
			pop();
			if (e == -1) {
				siz[u] = 1;
				e = headg[u];
			} else {
				e = nextg[e];
			}
			if (e != 0) {
				push(u, f, 0, 0, 0, e);
				int v = tog[e];
				if (v != f && !vis[e >> 1]) {
					push(v, u, 0, 0, 0, -1);
				}
			} else {
				for (int ei = headg[u]; ei > 0; ei = nextg[ei]) {
					int v = tog[ei];
					if (v != f && !vis[ei >> 1]) {
						siz[u] += siz[v];
					}
				}
			}
		}
	}

	public static int getCentroidEdge(int u, int fa) {
		// getSize1(u, fa);
		getSize2(u, fa);
		int total = siz[u];
		int edge = 0;
		int best = total;
		while (u > 0) {
			int nextu = 0, nextfa = 0;
			for (int e = headg[u]; e > 0; e = nextg[e]) {
				int v = tog[e];
				if (v != fa && !vis[e >> 1]) {
					int cur = Math.max(total - siz[v], siz[v]);
					if (cur < best) {
						edge = e;
						best = cur;
						nextfa = u;
						nextu = v;
					}
				}
			}
			fa = nextfa;
			u = nextu;
		}
		return edge;
	}

	// 收集信息递归版，java会爆栈，C++可以通过
	public static void dfs1(int u, int fa, int edge, int minv, int op) {
		if (op == 0) {
			preEdge[++cntp] = edge;
			preMinv[cntp] = minv;
		} else {
			curEdge[++cntc] = edge;
			curMinv[cntc] = minv;
		}
		for (int e = headg[u]; e > 0; e = nextg[e]) {
			int v = tog[e];
			if (v != fa && !vis[e >> 1]) {
				dfs1(v, u, edge + weightg[e], Math.min(minv, arr[v]), op);
			}
		}
	}

	// dfs1的迭代版
	public static void dfs2(int cur, int fa, int pedge, int pminv, int opt) {
		stacksize = 0;
		push(cur, fa, pedge, pminv, opt, -1);
		while (stacksize > 0) {
			pop();
			if (e == -1) {
				if (op == 0) {
					preEdge[++cntp] = edge;
					preMinv[cntp] = minv;
				} else {
					curEdge[++cntc] = edge;
					curMinv[cntc] = minv;
				}
				e = headg[u];
			} else {
				e = nextg[e];
			}
			if (e != 0) {
				push(u, f, edge, minv, op, e);
				int v = tog[e];
				if (v != f && !vis[e >> 1]) {
					push(v, u, edge + weightg[e], Math.min(minv, arr[v]), op, -1);
				}
			}
		}
	}

	// 根据minv的值从小到大排序
	// java自带的排序慢，手撸双指针快排，C++实现时可以用自带的排序
	public static void sort(int[] edge, int[] minv, int l, int r) {
		if (l >= r) return;
		int i = l, j = r, pivot = minv[(l + r) >> 1], tmp;
		while (i <= j) {
			while (minv[i] < pivot) i++;
			while (minv[j] > pivot) j--;
			if (i <= j) {
				tmp = edge[i]; edge[i] = edge[j]; edge[j] = tmp;
				tmp = minv[i]; minv[i] = minv[j]; minv[j] = tmp;
				i++; j--;
			}
		}
		sort(edge, minv, l, j);
		sort(edge, minv, i, r);
	}

	public static long calc(int edge) {
		cntp = cntc = 0;
		int v = tog[edge];
		// dfs1(v, 0, 0, arr[v], 0);
		dfs2(v, 0, 0, arr[v], 0);
		v = tog[edge ^ 1];
		// dfs1(v, 0, 0, arr[v], 1);
		dfs2(v, 0, 0, arr[v], 1);
		sort(preEdge, preMinv, 1, cntp);
		sort(curEdge, curMinv, 1, cntc);
		long ans = 0;
		long maxEdge = 0;
		for (int i = cntc, j = cntp; i >= 1; i--) {
			while (j >= 1 && preMinv[j] >= curMinv[i]) {
				maxEdge = Math.max(maxEdge, preEdge[j]);
				j--;
			}
			if (j < cntp) {
				ans = Math.max(ans, 1L * curMinv[i] * (maxEdge + curEdge[i] + weightg[edge] + 1));
			}
		}
		maxEdge = 0;
		for (int i = cntp, j = cntc; i >= 1; i--) {
			while (j >= 1 && curMinv[j] >= preMinv[i]) {
				maxEdge = Math.max(maxEdge, curEdge[j]);
				j--;
			}
			if (j < cntc) {
				ans = Math.max(ans, 1L * preMinv[i] * (maxEdge + preEdge[i] + weightg[edge] + 1));
			}
		}
		return ans;
	}

	public static long solve(int u) {
		long ans = 0;
		int edge = getCentroidEdge(u, 0);
		if (edge > 0) {
			vis[edge >> 1] = true;
			ans = calc(edge);
			ans = Math.max(ans, solve(tog[edge]));
			ans = Math.max(ans, solve(tog[edge ^ 1]));
		}
		return ans;
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		for (int i = 1; i <= n; i++) {
			arr[i] = in.nextInt();
		}
		for (int i = 1, u, v; i < n; i++) {
			u = in.nextInt();
			v = in.nextInt();
			addEdge(u, v, 1);
			addEdge(v, u, 1);
		}
		rebuildTree();
		long ans = solve(1);
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
