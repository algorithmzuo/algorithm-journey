package class161;

// 旅游，java版
// 一共有n个城市，给定n-1条边，城市连成一棵树，每个城市给定初始的宝石价格
// 一共有m条操作，操作类型如下
// 操作 x y v : 城市x到城市y的最短路途中，你可以选择一城买入宝石
//              继续行进的过程中，再选一城卖出宝石，以此获得利润
//              打印你能获得的最大利润，如果为负数，打印0
//              当你结束旅途后，沿途所有城市的宝石价格增加v
// 1 <= n、m <= 5 * 10^4
// 0 <= 任何时候的宝石价格 <= 10^9
// 测试链接 : https://www.luogu.com.cn/problem/P3976
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code06_Tourism1 {

	public static int MAXN = 50001;

	public static int INF = 1000000001;

	public static int n, m;

	public static int[] arr = new int[MAXN];

	public static int[] head = new int[MAXN];
	public static int[] next = new int[MAXN << 1];
	public static int[] to = new int[MAXN << 1];
	public static int cntg = 0;

	public static int[] fa = new int[MAXN];
	public static int[] dep = new int[MAXN];
	public static int[] siz = new int[MAXN];
	public static int[] son = new int[MAXN];
	public static int[] top = new int[MAXN];
	public static int[] dfn = new int[MAXN];
	public static int[] seg = new int[MAXN];
	public static int cntd = 0;

	public static int[] max = new int[MAXN << 2];
	public static int[] min = new int[MAXN << 2];
	public static int[] lprofit = new int[MAXN << 2];
	public static int[] rprofit = new int[MAXN << 2];
	// 线段树范围增加的懒更新信息
	public static int[] addTag = new int[MAXN << 2];

	public static void addEdge(int u, int v) {
		next[++cntg] = head[u];
		to[cntg] = v;
		head[u] = cntg;
	}

	// 递归版，C++可以通过，java会爆栈
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

	// 递归版，C++可以通过，java会爆栈
	public static void dfs2(int u, int t) {
		top[u] = t;
		dfn[u] = ++cntd;
		seg[cntd] = u;
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

	// 不会改迭代版，去看讲解118，详解了从递归版改迭代版
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
			if (edge == -1) { // edge == -1，表示第一次来到当前节点，并且先处理重儿子
				top[first] = second;
				dfn[first] = ++cntd;
				seg[cntd] = first;
				if (son[first] == 0) {
					continue;
				}
				push(first, second, -2);
				push(son[first], second, -1);
				continue;
			} else if (edge == -2) { // edge == -2，表示处理完当前节点的重儿子，回到了当前节点
				edge = head[first];
			} else { // edge >= 0, 继续处理其他的边
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
		max[i] = Math.max(max[l], max[r]);
		min[i] = Math.min(min[l], min[r]);
		lprofit[i] = Math.max(Math.max(lprofit[l], lprofit[r]), max[r] - min[l]);
		rprofit[i] = Math.max(Math.max(rprofit[l], rprofit[r]), max[l] - min[r]);
	}

	public static void lazy(int i, int v) {
		max[i] += v;
		min[i] += v;
		addTag[i] += v;
	}

	public static void down(int i) {
		if (addTag[i] != 0) {
			lazy(i << 1, addTag[i]);
			lazy(i << 1 | 1, addTag[i]);
			addTag[i] = 0;
		}
	}

	public static void build(int l, int r, int i) {
		if (l == r) {
			max[i] = min[i] = arr[seg[l]];
		} else {
			int mid = (l + r) / 2;
			build(l, mid, i << 1);
			build(mid + 1, r, i << 1 | 1);
			up(i);
		}
	}

	public static void add(int jobl, int jobr, int jobv, int l, int r, int i) {
		if (jobl <= l && r <= jobr) {
			lazy(i, jobv);
		} else {
			down(i);
			int mid = (l + r) / 2;
			if (jobl <= mid) {
				add(jobl, jobr, jobv, l, mid, i << 1);
			}
			if (jobr > mid) {
				add(jobl, jobr, jobv, mid + 1, r, i << 1 | 1);
			}
			up(i);
		}
	}

	// ans[0] : 线段树更左侧部分的max
	// ans[1] : 线段树更左侧部分的min
	// ans[2] : 线段树更左侧部分的lprofit
	// ans[3] : 线段树更左侧部分的rprofit
	// rmax : 线段树更右侧部分的max
	// rmin : 线段树更右侧部分的min
	// rlpro : 线段树更右侧部分的lprofit
	// rrpro : 线段树更右侧部分的rprofit
	// 左侧部分和右侧部分的信息整合在一起得到整个范围的max、min、lprofit、rprofit
	public static void merge(int[] ans, int rmax, int rmin, int rlpro, int rrpro) {
		int lmax = ans[0];
		int lmin = ans[1];
		int llpro = ans[2];
		int lrpro = ans[3];
		ans[0] = Math.max(lmax, rmax);
		ans[1] = Math.min(lmin, rmin);
		ans[2] = Math.max(Math.max(llpro, rlpro), rmax - lmin);
		ans[3] = Math.max(Math.max(lrpro, rrpro), lmax - rmin);
	}

	// ans[0] : 线段树更左侧部分的max
	// ans[1] : 线段树更左侧部分的min
	// ans[2] : 线段树更左侧部分的lprofit
	// ans[3] : 线段树更左侧部分的rprofit
	// 随着线段树查询的展开，会有更右部分的信息整合进ans，最终整合出整体信息
	public static void query(int[] ans, int jobl, int jobr, int l, int r, int i) {
		if (jobl <= l && r <= jobr) {
			merge(ans, max[i], min[i], lprofit[i], rprofit[i]);
		} else {
			down(i);
			int mid = (l + r) / 2;
			if (jobl <= mid) {
				query(ans, jobl, jobr, l, mid, i << 1);
			}
			if (jobr > mid) {
				query(ans, jobl, jobr, mid + 1, r, i << 1 | 1);
			}
		}
	}

	public static void query(int[] ans, int jobl, int jobr) {
		ans[0] = -INF;
		ans[1] = INF;
		ans[2] = 0;
		ans[3] = 0;
		query(ans, jobl, jobr, 1, n, 1);
	}

	public static void clone(int[] a, int[] b) {
		a[0] = b[0];
		a[1] = b[1];
		a[2] = b[2];
		a[3] = b[3];
	}

	public static int compute(int x, int y, int v) {
		int tmpx = x, tmpy = y;
		int[] xpath = new int[] { -INF, INF, 0, 0 };
		int[] ypath = new int[] { -INF, INF, 0, 0 };
		int[] cur = new int[4];
		while (top[x] != top[y]) {
			if (dep[top[x]] <= dep[top[y]]) {
				query(cur, dfn[top[y]], dfn[y]);
				merge(cur, ypath[0], ypath[1], ypath[2], ypath[3]);
				clone(ypath, cur);
				y = fa[top[y]];
			} else {
				query(cur, dfn[top[x]], dfn[x]);
				merge(cur, xpath[0], xpath[1], xpath[2], xpath[3]);
				clone(xpath, cur);
				x = fa[top[x]];
			}
		}
		if (dep[x] <= dep[y]) {
			query(cur, dfn[x], dfn[y]);
			merge(cur, ypath[0], ypath[1], ypath[2], ypath[3]);
			clone(ypath, cur);
		} else {
			query(cur, dfn[y], dfn[x]);
			merge(cur, xpath[0], xpath[1], xpath[2], xpath[3]);
			clone(xpath, cur);
		}
		int ans = Math.max(Math.max(xpath[3], ypath[2]), ypath[0] - xpath[1]);
		x = tmpx;
		y = tmpy;
		while (top[x] != top[y]) {
			if (dep[top[x]] <= dep[top[y]]) {
				add(dfn[top[y]], dfn[y], v, 1, n, 1);
				y = fa[top[y]];
			} else {
				add(dfn[top[x]], dfn[x], v, 1, n, 1);
				x = fa[top[x]];
			}
		}
		add(Math.min(dfn[x], dfn[y]), Math.max(dfn[x], dfn[y]), v, 1, n, 1);
		return ans;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		n = (int) in.nval;
		for (int i = 1; i <= n; i++) {
			in.nextToken();
			arr[i] = (int) in.nval;
		}
		for (int i = 1, u, v; i < n; i++) {
			in.nextToken();
			u = (int) in.nval;
			in.nextToken();
			v = (int) in.nval;
			addEdge(u, v);
			addEdge(v, u);
		}
		dfs3(); // dfs3() 等同于 dfs1(1, 0)，调用迭代版防止爆栈
		dfs4(); // dfs4() 等同于 dfs2(1, 1)，调用迭代版防止爆栈
		build(1, n, 1);
		in.nextToken();
		m = (int) in.nval;
		for (int i = 1, x, y, v; i <= m; i++) {
			in.nextToken();
			x = (int) in.nval;
			in.nextToken();
			y = (int) in.nval;
			in.nextToken();
			v = (int) in.nval;
			out.println(compute(x, y, v));
		}
		out.flush();
		out.close();
		br.close();
	}

}
