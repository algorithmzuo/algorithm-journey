package class183;

// 所有合法路径的魔力和，java版
// 一共有n个节点，给定n-1条边，每条边有边权，所有节点组成一棵树
// 给定两个整数l、r，对于点对(x, y)，考虑两点之间的简单路径
// 如果路径的边数在[l, r]范围内，则该路径视为合法
// 一条路径的魔力值 = 该路径上所有边权的最大值
// 计算所有合法路径的魔力值之和
// 本题规定(x, x)不是点对，(x, y)和(y, x)认为是不同的点对
// 1 <= n、边权 <= 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P5351
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code06_Maschera1 {

	public static int MAXN = 100002;
	public static int n, l, r;

	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXN << 1];
	public static int[] to = new int[MAXN << 1];
	public static int[] weight = new int[MAXN << 1];
	public static int cntg;

	public static boolean[] vis = new boolean[MAXN];
	public static int[] siz = new int[MAXN];

	public static int[] maxvArr = new int[MAXN];
	public static int[] edgeArr = new int[MAXN];
	public static int cnta;

	public static int[] tree = new int[MAXN];

	// 讲解118，递归函数改成迭代所需要的栈
	public static int[][] stack = new int[MAXN][5];
	public static int u, f, maxv, edge, e;
	public static int stacksize;

	public static void push(int u, int f, int maxv, int edge, int e) {
		stack[stacksize][0] = u;
		stack[stacksize][1] = f;
		stack[stacksize][2] = maxv;
		stack[stacksize][3] = edge;
		stack[stacksize][4] = e;
		stacksize++;
	}

	public static void pop() {
		--stacksize;
		u = stack[stacksize][0];
		f = stack[stacksize][1];
		maxv = stack[stacksize][2];
		edge = stack[stacksize][3];
		e = stack[stacksize][4];
	}

	public static void sort(int l, int r) {
		if (l >= r) return;
		int i = l, j = r, pivot = maxvArr[(l + r) >> 1], tmp;
		while (i <= j) {
			while (maxvArr[i] < pivot) i++;
			while (maxvArr[j] > pivot) j--;
			if (i <= j) {
				tmp = maxvArr[i]; maxvArr[i] = maxvArr[j]; maxvArr[j] = tmp;
				tmp = edgeArr[i]; edgeArr[i] = edgeArr[j]; edgeArr[j] = tmp;
				i++; j--;
			}
		}
		sort(l, j);
		sort(i, r);
	}

	public static void addEdge(int u, int v, int w) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		weight[cntg] = w;
		head[u] = cntg;
	}

	public static int lowbit(int i) {
		return i & -i;
	}

	public static void add(int i, int v) {
		i++;
		while (i <= r + 1) {
			tree[i] += v;
			i += lowbit(i);
		}
	}

	public static int sum(int i) {
		i++;
		int ret = 0;
		while (i > 0) {
			ret += tree[i];
			i -= lowbit(i);
		}
		return ret;
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
	public static void dfs1(int u, int fa, int maxv, int edge) {
		if (edge > r) {
			return;
		}
		maxvArr[++cnta] = maxv;
		edgeArr[cnta] = edge;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (v != fa && !vis[v]) {
				dfs1(v, u, Math.max(maxv, weight[e]), edge + 1);
			}
		}
	}

	// 收集信息迭代版
	public static void dfs2(int cur, int fa, int pmaxv, int pedge) {
		stacksize = 0;
		push(cur, fa, pmaxv, pedge, -1);
		while (stacksize > 0) {
			pop();
			if (e == -1) {
				if (edge > r) {
					continue;
				}
				maxvArr[++cnta] = maxv;
				edgeArr[cnta] = edge;
				e = head[u];
			} else {
				e = nxt[e];
			}
			if (e != 0) {
				push(u, f, maxv, edge, e);
				int v = to[e];
				if (v != f && !vis[v]) {
					push(to[e], u, Math.max(maxv, weight[e]), edge + 1, -1);
				}
			}
		}
	}

	public static long calc(int u, int maxv, int edge) {
		cnta = 0;
		// dfs1(u, 0, maxv, edge);
		dfs2(u, 0, maxv, edge);
		sort(1, cnta);
		long ans = 0;
		for (int i = 1; i <= cnta; i++) {
			ans += 1L * maxvArr[i] * (sum(r - edgeArr[i]) - sum(l - edgeArr[i] - 1));
			add(edgeArr[i], 1);
		}
		for (int i = 1; i <= cnta; i++) {
			add(edgeArr[i], -1);
		}
		return ans;
	}

	public static long solve(int u) {
		vis[u] = true;
		long ans = calc(u, 0, 0);
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (!vis[v]) {
				ans -= calc(v, weight[e], 1);
				ans += solve(getCentroid(v, u));
			}
		}
		return ans;
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		l = in.nextInt();
		r = in.nextInt();
		for (int i = 1, u, v, w; i < n; i++) {
			u = in.nextInt();
			v = in.nextInt();
			w = in.nextInt();
			addEdge(u, v, w);
			addEdge(v, u, w);
		}
		long ans = solve(getCentroid(1, 0));
		out.println(ans << 1);
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
