package class162;

// 月下毛景树，java版
// 一共有n个节点，节点编号从1到n，所有节点连成一棵树
// 给定n-1条边，边的编号从1到n-1，每条边给定初始边权
// 会进行若干次操作，每条操作的类型是如下4种类型中的一种
// 操作 Change x v  : 第x条边的边权改成v
// 操作 Cover x y v : x号点到y号点的路径上，所有边权改成v
// 操作 Add x y v   : x号点到y号点的路径上，所有边权增加v
// 操作 Max x y     : x号点到y号点的路径上，打印最大的边权
// 1 <= n <= 10^5
// 任何时候的边权 <= 10^9
// 测试链接 : https://www.luogu.com.cn/problem/P4315
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Code03_UnderMoon1 {

	public static int MAXN = 100001;
	public static int n;
	public static int[][] arr = new int[MAXN][3];

	// 链式前向星
	public static int[] head = new int[MAXN];
	public static int[] next = new int[MAXN << 1];
	public static int[] to = new int[MAXN << 1];
	public static int cntg = 0;

	// 重链剖分
	public static int[] fa = new int[MAXN];
	public static int[] dep = new int[MAXN];
	public static int[] siz = new int[MAXN];
	public static int[] son = new int[MAXN];
	public static int[] top = new int[MAXN];
	public static int[] dfn = new int[MAXN];
	public static int cntd = 0;

	// 线段树查询区间最大值
	// 但是需要同时兼顾，区间增加、区间修改，这两种操作
	// 那么就牵扯到两种操作相互影响的问题
	// 因为区间修改明显会取消之前的区间增加
	// 讲解110，线段树章节，题目5、题目6，重点讲了这种线段树
	// 不会的同学可以看看，讲的非常清楚
	public static int[] max = new int[MAXN << 2];
	public static int[] addTag = new int[MAXN << 2];
	public static boolean[] updateTag = new boolean[MAXN << 2];
	public static int[] change = new int[MAXN << 2];

	public static void addEdge(int u, int v) {
		next[++cntg] = head[u];
		to[cntg] = v;
		head[u] = cntg;
	}

	public static void dfs1(int u, int f) {
		fa[u] = f;
		dep[u] = dep[f] + 1;
		siz[u] = 1;
		for (int e = head[u], v; e > 0; e = next[e]) {
			v = to[e];
			if (v != f) {
				dfs1(v, u);
			}
		}
		for (int e = head[u], v; e > 0; e = next[e]) {
			v = to[e];
			if (v != f) {
				siz[u] += siz[v];
				if (son[u] == 0 || siz[son[u]] < siz[v]) {
					son[u] = v;
				}
			}
		}
	}

	public static void dfs2(int u, int t) {
		top[u] = t;
		dfn[u] = ++cntd;
		if (son[u] == 0) {
			return;
		}
		dfs2(son[u], t);
		for (int e = head[u], v; e > 0; e = next[e]) {
			v = to[e];
			if (v != fa[u] && v != son[u]) {
				dfs2(v, v);
			}
		}
	}

	public static int[][] fse = new int[MAXN][3];

	public static int stacksize, first, second, edge;

	public static void push(int fir, int sec, int edg) {
		fse[stacksize][0] = fir;
		fse[stacksize][1] = sec;
		fse[stacksize][2] = edg;
		stacksize++;
	}

	public static void pop() {
		--stacksize;
		first = fse[stacksize][0];
		second = fse[stacksize][1];
		edge = fse[stacksize][2];
	}

	// dfs1的迭代版
	public static void dfs3() {
		stacksize = 0;
		push(1, 0, -1);
		while (stacksize > 0) {
			pop();
			if (edge == -1) {
				fa[first] = second;
				dep[first] = dep[second] + 1;
				siz[first] = 1;
				edge = head[first];
			} else {
				edge = next[edge];
			}
			if (edge != 0) {
				push(first, second, edge);
				if (to[edge] != second) {
					push(to[edge], first, -1);
				}
			} else {
				for (int e = head[first], v; e > 0; e = next[e]) {
					v = to[e];
					if (v != second) {
						siz[first] += siz[v];
						if (son[first] == 0 || siz[son[first]] < siz[v]) {
							son[first] = v;
						}
					}
				}
			}
		}
	}

	// dfs2的迭代版
	public static void dfs4() {
		stacksize = 0;
		push(1, 1, -1);
		while (stacksize > 0) {
			pop();
			if (edge == -1) {
				top[first] = second;
				dfn[first] = ++cntd;
				if (son[first] == 0) {
					continue;
				}
				push(first, second, -2);
				push(son[first], second, -1);
				continue;
			} else if (edge == -2) {
				edge = head[first];
			} else {
				edge = next[edge];
			}
			if (edge != 0) {
				push(first, second, edge);
				if (to[edge] != fa[first] && to[edge] != son[first]) {
					push(to[edge], to[edge], -1);
				}
			}
		}
	}

	public static void up(int i) {
		max[i] = Math.max(max[i << 1], max[i << 1 | 1]);
	}

	public static void addLazy(int i, int v) {
		max[i] += v;
		addTag[i] += v;
	}

	public static void updateLazy(int i, int v) {
		max[i] = v;
		addTag[i] = 0;
		updateTag[i] = true;
		change[i] = v;
	}

	public static void down(int i) {
		if (updateTag[i]) {
			updateLazy(i << 1, change[i]);
			updateLazy(i << 1 | 1, change[i]);
			updateTag[i] = false;
		}
		if (addTag[i] != 0) {
			addLazy(i << 1, addTag[i]);
			addLazy(i << 1 | 1, addTag[i]);
			addTag[i] = 0;
		}
	}

	public static void update(int jobl, int jobr, int jobv, int l, int r, int i) {
		if (jobl <= l && r <= jobr) {
			updateLazy(i, jobv);
		} else {
			int mid = (l + r) >> 1;
			down(i);
			if (jobl <= mid) {
				update(jobl, jobr, jobv, l, mid, i << 1);
			}
			if (jobr > mid) {
				update(jobl, jobr, jobv, mid + 1, r, i << 1 | 1);
			}
			up(i);
		}
	}

	public static void add(int jobl, int jobr, int jobv, int l, int r, int i) {
		if (jobl <= l && r <= jobr) {
			addLazy(i, jobv);
		} else {
			int mid = (l + r) >> 1;
			down(i);
			if (jobl <= mid) {
				add(jobl, jobr, jobv, l, mid, i << 1);
			}
			if (jobr > mid) {
				add(jobl, jobr, jobv, mid + 1, r, i << 1 | 1);
			}
			up(i);
		}
	}

	public static int query(int jobl, int jobr, int l, int r, int i) {
		if (jobl <= l && r <= jobr) {
			return max[i];
		}
		int mid = (l + r) >> 1;
		down(i);
		int ans = Integer.MIN_VALUE;
		if (jobl <= mid) {
			ans = Math.max(ans, query(jobl, jobr, l, mid, i << 1));
		}
		if (jobr > mid) {
			ans = Math.max(ans, query(jobl, jobr, mid + 1, r, i << 1 | 1));
		}
		return ans;
	}

	public static void edgeUpdate(int ei, int val) {
		int x = arr[ei][0];
		int y = arr[ei][1];
		int down = Math.max(dfn[x], dfn[y]);
		update(down, down, val, 1, n, 1);
	}

	public static void pathUpdate(int x, int y, int v) {
		while (top[x] != top[y]) {
			if (dep[top[x]] <= dep[top[y]]) {
				update(dfn[top[y]], dfn[y], v, 1, n, 1);
				y = fa[top[y]];
			} else {
				update(dfn[top[x]], dfn[x], v, 1, n, 1);
				x = fa[top[x]];
			}
		}
		update(Math.min(dfn[x], dfn[y]) + 1, Math.max(dfn[x], dfn[y]), v, 1, n, 1);
	}

	public static void pathAdd(int x, int y, int v) {
		while (top[x] != top[y]) {
			if (dep[top[x]] <= dep[top[y]]) {
				add(dfn[top[y]], dfn[y], v, 1, n, 1);
				y = fa[top[y]];
			} else {
				add(dfn[top[x]], dfn[x], v, 1, n, 1);
				x = fa[top[x]];
			}
		}
		add(Math.min(dfn[x], dfn[y]) + 1, Math.max(dfn[x], dfn[y]), v, 1, n, 1);
	}

	public static int pathMax(int x, int y) {
		int ans = Integer.MIN_VALUE;
		while (top[x] != top[y]) {
			if (dep[top[x]] <= dep[top[y]]) {
				ans = Math.max(ans, query(dfn[top[y]], dfn[y], 1, n, 1));
				y = fa[top[y]];
			} else {
				ans = Math.max(ans, query(dfn[top[x]], dfn[x], 1, n, 1));
				x = fa[top[x]];
			}
		}
		ans = Math.max(ans, query(Math.min(dfn[x], dfn[y]) + 1, Math.max(dfn[x], dfn[y]), 1, n, 1));
		return ans;
	}

	public static void prepare() {
		for (int i = 1; i < n; i++) {
			addEdge(arr[i][0], arr[i][1]);
			addEdge(arr[i][1], arr[i][0]);
		}
		dfs3();
		dfs4();
		for (int i = 1; i < n; i++) {
			edgeUpdate(i, arr[i][2]);
		}
	}

	public static void main(String[] args) {
		Kattio io = new Kattio();
		n = io.nextInt();
		for (int i = 1; i < n; i++) {
			arr[i][0] = io.nextInt();
			arr[i][1] = io.nextInt();
			arr[i][2] = io.nextInt();
		}
		prepare();
		String op = io.next();
		int x, y, v;
		while (!op.equals("Stop")) {
			if (op.equals("Change")) {
				x = io.nextInt();
				v = io.nextInt();
				edgeUpdate(x, v);
			} else if (op.equals("Cover")) {
				x = io.nextInt();
				y = io.nextInt();
				v = io.nextInt();
				pathUpdate(x, y, v);
			} else if (op.equals("Add")) {
				x = io.nextInt();
				y = io.nextInt();
				v = io.nextInt();
				pathAdd(x, y, v);
			} else {
				x = io.nextInt();
				y = io.nextInt();
				io.println(pathMax(x, y));
			}
			op = io.next();
		}
		io.flush();
		io.close();
	}

	// 读写工具类
	public static class Kattio extends PrintWriter {
		private BufferedReader r;
		private StringTokenizer st;

		public Kattio() {
			this(System.in, System.out);
		}

		public Kattio(InputStream i, OutputStream o) {
			super(o);
			r = new BufferedReader(new InputStreamReader(i));
		}

		public Kattio(String intput, String output) throws IOException {
			super(output);
			r = new BufferedReader(new FileReader(intput));
		}

		public String next() {
			try {
				while (st == null || !st.hasMoreTokens())
					st = new StringTokenizer(r.readLine());
				return st.nextToken();
			} catch (Exception e) {
			}
			return null;
		}

		public int nextInt() {
			return Integer.parseInt(next());
		}

		public double nextDouble() {
			return Double.parseDouble(next());
		}

		public long nextLong() {
			return Long.parseLong(next());
		}
	}

}
