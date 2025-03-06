package class162;

// 国家集训队旅游，java版
// 一共有n个节点，节点编号从0到n-1，所有节点连成一棵树
// 给定n-1条边，边的编号从1到n-1，每条边给定初始边权
// 一共有m条操作，每条操作的类型是如下5种类型中的一种
// 操作 C x y   : 第x条边的边权改成y
// 操作 N x y   : x号点到y号点的路径上，所有边权变成相反数
// 操作 SUM x y : x号点到y号点的路径上，查询所有边权的累加和
// 操作 MAX x y : x号点到y号点的路径上，查询所有边权的最大值
// 操作 MIN x y : x号点到y号点的路径上，查询所有边权的最小值
// 1 <= n、m <= 2 * 10^5
// -1000 <= 任何时候的边权 <= +1000
// 测试链接 : https://www.luogu.com.cn/problem/P1505
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Code03_NationalTour1 {

	public static int MAXN = 200001;
	public static int n, m;
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

	// 线段树
	public static int[] sum = new int[MAXN << 2];
	public static int[] max = new int[MAXN << 2];
	public static int[] min = new int[MAXN << 2];
	public static boolean[] negativeTag = new boolean[MAXN << 2];

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
		int l = i << 1, r = i << 1 | 1;
		sum[i] = sum[l] + sum[r];
		max[i] = Math.max(max[l], max[r]);
		min[i] = Math.min(min[l], min[r]);
	}

	public static void lazy(int i) {
		sum[i] = -sum[i];
		int tmp = max[i];
		max[i] = -min[i];
		min[i] = -tmp;
		negativeTag[i] = !negativeTag[i];
	}

	public static void down(int i) {
		if (negativeTag[i]) {
			lazy(i << 1);
			lazy(i << 1 | 1);
			negativeTag[i] = false;
		}
	}

	public static void update(int jobi, int jobv, int l, int r, int i) {
		if (l == r) {
			sum[i] = max[i] = min[i] = jobv;
		} else {
			down(i);
			int mid = (l + r) / 2;
			if (jobi <= mid) {
				update(jobi, jobv, l, mid, i << 1);
			} else {
				update(jobi, jobv, mid + 1, r, i << 1 | 1);
			}
			up(i);
		}
	}

	public static void negative(int jobl, int jobr, int l, int r, int i) {
		if (jobl <= l && r <= jobr) {
			lazy(i);
		} else {
			down(i);
			int mid = (l + r) / 2;
			if (jobl <= mid) {
				negative(jobl, jobr, l, mid, i << 1);
			}
			if (jobr > mid) {
				negative(jobl, jobr, mid + 1, r, i << 1 | 1);
			}
			up(i);
		}
	}

	public static int querySum(int jobl, int jobr, int l, int r, int i) {
		if (jobl <= l && r <= jobr) {
			return sum[i];
		}
		down(i);
		int mid = (l + r) / 2;
		int ans = 0;
		if (jobl <= mid) {
			ans += querySum(jobl, jobr, l, mid, i << 1);
		}
		if (jobr > mid) {
			ans += querySum(jobl, jobr, mid + 1, r, i << 1 | 1);
		}
		return ans;
	}

	public static int queryMax(int jobl, int jobr, int l, int r, int i) {
		if (jobl <= l && r <= jobr) {
			return max[i];
		}
		down(i);
		int mid = (l + r) / 2;
		int ans = Integer.MIN_VALUE;
		if (jobl <= mid) {
			ans = Math.max(ans, queryMax(jobl, jobr, l, mid, i << 1));
		}
		if (jobr > mid) {
			ans = Math.max(ans, queryMax(jobl, jobr, mid + 1, r, i << 1 | 1));
		}
		return ans;
	}

	public static int queryMin(int jobl, int jobr, int l, int r, int i) {
		if (jobl <= l && r <= jobr) {
			return min[i];
		}
		down(i);
		int mid = (l + r) / 2;
		int ans = Integer.MAX_VALUE;
		if (jobl <= mid) {
			ans = Math.min(ans, queryMin(jobl, jobr, l, mid, i << 1));
		}
		if (jobr > mid) {
			ans = Math.min(ans, queryMin(jobl, jobr, mid + 1, r, i << 1 | 1));
		}
		return ans;
	}

	public static void edgeUpdate(int ei, int val) {
		int x = arr[ei][0];
		int y = arr[ei][1];
		int down = Math.max(dfn[x], dfn[y]);
		update(down, val, 1, n, 1);
	}

	public static void pathNegative(int x, int y) {
		while (top[x] != top[y]) {
			if (dep[top[x]] <= dep[top[y]]) {
				negative(dfn[top[y]], dfn[y], 1, n, 1);
				y = fa[top[y]];
			} else {
				negative(dfn[top[x]], dfn[x], 1, n, 1);
				x = fa[top[x]];
			}
		}
		negative(Math.min(dfn[x], dfn[y]) + 1, Math.max(dfn[x], dfn[y]), 1, n, 1);
	}

	public static int pathSum(int x, int y) {
		int ans = 0;
		while (top[x] != top[y]) {
			if (dep[top[x]] <= dep[top[y]]) {
				ans += querySum(dfn[top[y]], dfn[y], 1, n, 1);
				y = fa[top[y]];
			} else {
				ans += querySum(dfn[top[x]], dfn[x], 1, n, 1);
				x = fa[top[x]];
			}
		}
		ans += querySum(Math.min(dfn[x], dfn[y]) + 1, Math.max(dfn[x], dfn[y]), 1, n, 1);
		return ans;
	}

	public static int pathMax(int x, int y) {
		int ans = Integer.MIN_VALUE;
		while (top[x] != top[y]) {
			if (dep[top[x]] <= dep[top[y]]) {
				ans = Math.max(ans, queryMax(dfn[top[y]], dfn[y], 1, n, 1));
				y = fa[top[y]];
			} else {
				ans = Math.max(ans, queryMax(dfn[top[x]], dfn[x], 1, n, 1));
				x = fa[top[x]];
			}
		}
		ans = Math.max(ans, queryMax(Math.min(dfn[x], dfn[y]) + 1, Math.max(dfn[x], dfn[y]), 1, n, 1));
		return ans;
	}

	public static int pathMin(int x, int y) {
		int ans = Integer.MAX_VALUE;
		while (top[x] != top[y]) {
			if (dep[top[x]] <= dep[top[y]]) {
				ans = Math.min(ans, queryMin(dfn[top[y]], dfn[y], 1, n, 1));
				y = fa[top[y]];
			} else {
				ans = Math.min(ans, queryMin(dfn[top[x]], dfn[x], 1, n, 1));
				x = fa[top[x]];
			}
		}
		ans = Math.min(ans, queryMin(Math.min(dfn[x], dfn[y]) + 1, Math.max(dfn[x], dfn[y]), 1, n, 1));
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
			arr[i][0] = io.nextInt() + 1;
			arr[i][1] = io.nextInt() + 1;
			arr[i][2] = io.nextInt();
		}
		prepare();
		m = io.nextInt();
		String op;
		for (int i = 1, x, y; i <= m; i++) {
			op = io.next();
			if (op.equals("C")) {
				x = io.nextInt();
				y = io.nextInt();
				edgeUpdate(x, y);
			} else {
				x = io.nextInt() + 1;
				y = io.nextInt() + 1;
				if (op.equals("N")) {
					pathNegative(x, y);
				} else if (op.equals("SUM")) {
					io.println(pathSum(x, y));
				} else if (op.equals("MAX")) {
					io.println(pathMax(x, y));
				} else {
					io.println(pathMin(x, y));
				}
			}
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
