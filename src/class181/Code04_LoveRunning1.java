package class181;

// 天天爱跑步，java版
// 一共有n个点，给定n-1条边，所有节点组成一棵树
// 每个点上都有一个观察员，给出每个观察员的观测时刻w[i]
// 给出m个跑步者的路线，格式 x y : 该跑步者出发时刻为0，从x跑到y
// 任何跑步者通过任何一条边，耗时都是1秒
// 某个跑步者到达i号点的时刻 == w[i]，那么该跑步者才会被i号点的观察员观测到
// 打印i号点的观察员，能观测到多少人，一共n条打印
// 1 <= n、m、w[i] <= 3 * 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P1600
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code04_LoveRunning1 {

	public static int MAXN = 300001;
	public static int MAXT = MAXN * 50;
	public static int MAXP = 20;
	public static int n, m;
	public static int[] w = new int[MAXN];

	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXN << 1];
	public static int[] to = new int[MAXN << 1];
	public static int cntg;

	public static int[] dep = new int[MAXN];
	public static int[][] stjump = new int[MAXN][MAXP];

	public static int[] rtUp = new int[MAXN];
	public static int[] rtDown = new int[MAXN];
	public static int[] ls = new int[MAXT];
	public static int[] rs = new int[MAXT];
	public static int[] sum = new int[MAXT];
	public static int cntt;

	public static int[] ans = new int[MAXN];

	// 递归改迭代需要
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

	public static void addEdge(int u, int v) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		head[u] = cntg;
	}

	// 递归版，java会爆栈，C++可以通过
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

	// dfs1改迭代
	public static void dfs2() {
		stacksize = 0;
		push(1, 0, -1);
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
				push(u, f, e);
				if (to[e] != f) {
					push(to[e], u, -1);
				}
			}
		}
	}

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

	public static void up(int i) {
		sum[i] = sum[ls[i]] + sum[rs[i]];
	}

	public static int add(int jobi, int jobv, int l, int r, int i) {
		int rt = i;
		if (rt == 0) {
			rt = ++cntt;
		}
		if (l == r) {
			sum[rt] += jobv;
		} else {
			int mid = (l + r) >> 1;
			if (jobi <= mid) {
				ls[rt] = add(jobi, jobv, l, mid, ls[rt]);
			} else {
				rs[rt] = add(jobi, jobv, mid + 1, r, rs[rt]);
			}
			up(rt);
		}
		return rt;
	}

	public static int merge(int l, int r, int t1, int t2) {
		if (t1 == 0 || t2 == 0) {
			return t1 + t2;
		}
		if (l == r) {
			sum[t1] += sum[t2];
		} else {
			int mid = (l + r) >> 1;
			ls[t1] = merge(l, mid, ls[t1], ls[t2]);
			rs[t1] = merge(mid + 1, r, rs[t1], rs[t2]);
			up(t1);
		}
		return t1;
	}

	public static int query(int jobi, int l, int r, int i) {
		if (jobi < l || jobi > r || i == 0) {
			return 0;
		}
		if (l == r) {
			return sum[i];
		}
		int mid = (l + r) >> 1;
		if (jobi <= mid) {
			return query(jobi, l, mid, ls[i]);
		} else {
			return query(jobi, mid + 1, r, rs[i]);
		}
	}

	// 递归版，java会爆栈，C++可以通过
	public static void calc1(int u, int fa) {
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (v != fa) {
				calc1(v, u);
				rtUp[u] = merge(1, n, rtUp[u], rtUp[v]);
				rtDown[u] = merge(-n, n, rtDown[u], rtDown[v]);
			}
		}
		ans[u] = query(dep[u] + w[u], 1, n, rtUp[u]) + query(dep[u] - w[u], -n, n, rtDown[u]);
	}

	// calc1改迭代
	public static void calc2() {
		stacksize = 0;
		push(1, 0, -1);
		while (stacksize > 0) {
			pop();
			if (e == -1) {
				e = head[u];
			} else {
				e = nxt[e];
			}
			if (e != 0) {
				push(u, f, e);
				if (to[e] != f) {
					push(to[e], u, -1);
				}
			} else {
				for (int ei = head[u]; ei > 0; ei = nxt[ei]) {
					int v = to[ei];
					if (v != f) {
						rtUp[u] = merge(1, n, rtUp[u], rtUp[v]);
						rtDown[u] = merge(-n, n, rtDown[u], rtDown[v]);
					}
				}
				ans[u] = query(dep[u] + w[u], 1, n, rtUp[u]) + query(dep[u] - w[u], -n, n, rtDown[u]);
			}
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		for (int i = 1, u, v; i < n; i++) {
			u = in.nextInt();
			v = in.nextInt();
			addEdge(u, v);
			addEdge(v, u);
		}
		for (int i = 1; i <= n; i++) {
			w[i] = in.nextInt();
		}
		// dfs1(1, 0);
		dfs2();
		for (int i = 1; i <= m; i++) {
			int x = in.nextInt();
			int y = in.nextInt();
			int lca = getLca(x, y);
			int lcafa = stjump[lca][0];
			rtUp[x] = add(dep[x], 1, 1, n, rtUp[x]);
			rtUp[lca] = add(dep[x], -1, 1, n, rtUp[lca]);
			rtDown[y] = add(2 * dep[lca] - dep[x], 1, -n, n, rtDown[y]);
			rtDown[lcafa] = add(2 * dep[lca] - dep[x], -1, -n, n, rtDown[lcafa]);
		}
		// calc1(1, 0);
		calc2();
		for (int i = 1; i <= n; i++) {
			out.print(ans[i] + " ");
		}
		out.println();
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
