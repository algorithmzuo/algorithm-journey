package class181;

// 雨天的尾巴，java版
// 测试链接 : https://www.luogu.com.cn/problem/P4556
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code02_RainDayTail1 {

	public static int MAXN = 100001;
	public static int MAXV = 100000;
	public static int MAXT = MAXN * 50;
	public static int MAXP = 20;
	public static int n, m;
	public static int[][] query = new int[MAXN][3];

	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXN << 1];
	public static int[] to = new int[MAXN << 1];
	public static int cntg;

	public static int[] dep = new int[MAXN];
	public static int[][] stjump = new int[MAXN][MAXP];

	public static int[] root = new int[MAXN];
	public static int[] ls = new int[MAXT];
	public static int[] rs = new int[MAXT];
	public static int[] maxCnt = new int[MAXT];
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
		maxCnt[i] = Math.max(maxCnt[ls[i]], maxCnt[rs[i]]);
	}

	public static int add(int jobi, int jobv, int l, int r, int i) {
		int rt = i;
		if (rt == 0) {
			rt = ++cntt;
		}
		if (l == r) {
			maxCnt[rt] += jobv;
		} else {
			int mid = (l + r) / 2;
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
			maxCnt[t1] += maxCnt[t2];
		} else {
			int mid = (l + r) / 2;
			ls[t1] = merge(l, mid, ls[t1], ls[t2]);
			rs[t1] = merge(mid + 1, r, rs[t1], rs[t2]);
			up(t1);
		}
		return t1;
	}

	public static int query(int l, int r, int i) {
		if (l == r) {
			return l;
		}
		int mid = (l + r) / 2;
		if (maxCnt[i] == maxCnt[ls[i]]) {
			return query(l, mid, ls[i]);
		} else {
			return query(mid + 1, r, rs[i]);
		}
	}

	public static void calc1(int u, int fa) {
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (v != fa) {
				calc1(v, u);
			}
		}
		for (int ei = head[u]; ei > 0; ei = nxt[ei]) {
			int v = to[ei];
			if (v != fa) {
				root[u] = merge(1, MAXV, root[u], root[v]);
			}
		}
		if (maxCnt[root[u]] > 0) {
			ans[u] = query(1, MAXV, root[u]);
		}
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
						root[u] = merge(1, MAXV, root[u], root[v]);
					}
				}
				if (maxCnt[root[u]] > 0) {
					ans[u] = query(1, MAXV, root[u]);
				}
			}
		}
	}

	public static void compute() {
		// dfs1(1, 0);
		dfs2();
		for (int i = 1; i <= m; i++) {
			int x = query[i][0];
			int y = query[i][1];
			int food = query[i][2];
			int lca = getLca(x, y);
			int lcafa = stjump[lca][0];
			root[x] = add(food, 1, 1, MAXV, root[x]);
			root[y] = add(food, 1, 1, MAXV, root[y]);
			root[lca] = add(food, -1, 1, MAXV, root[lca]);
			root[lcafa] = add(food, -1, 1, MAXV, root[lcafa]);
		}
		// calc1(1, 0);
		calc2();
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
		for (int i = 1; i <= m; i++) {
			query[i][0] = in.nextInt();
			query[i][1] = in.nextInt();
			query[i][2] = in.nextInt();
		}
		compute();
		for (int i = 1; i <= n; i++) {
			out.println(ans[i]);
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
