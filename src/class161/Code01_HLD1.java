package class161;

// 树链剖分模版题1，java版
// 一共有n个节点，给定n-1条边，节点连成一棵树，给定树的头节点编号root
// 一共有m条操作，每种操作是如下4种类型中的一种
// 操作 1 x y v : x到y的路径上，每个节点值增加v
// 操作 2 x y   : x到y的路径上，打印所有节点值的累加和
// 操作 3 x v   : x为头的子树上，每个节点值增加v
// 操作 4 x     : x为头的子树上，打印所有节点值的累加和
// 1 <= n、m <= 10^5
// 1 <= MOD <= 2^30
// 输入的值都为int类型
// 查询操作时，打印(查询结果 % MOD)，题目会给定MOD值
// 测试链接 : https://www.luogu.com.cn/problem/P3384
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code01_HLD1 {

	public static int MAXN = 100001;

	public static int n, m, root, MOD;

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

	public static long[] sum = new long[MAXN << 2];

	public static long[] addTag = new long[MAXN << 2];

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
		push(root, 0, -1);
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
		push(root, root, -1);
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
		sum[i] = (sum[i << 1] + sum[i << 1 | 1]) % MOD;
	}

	public static void lazy(int i, long v, int n) {
		sum[i] = (sum[i] + v * n) % MOD;
		addTag[i] = (addTag[i] + v) % MOD;
	}

	public static void down(int i, int ln, int rn) {
		if (addTag[i] != 0) {
			lazy(i << 1, addTag[i], ln);
			lazy(i << 1 | 1, addTag[i], rn);
			addTag[i] = 0;
		}
	}

	public static void build(int l, int r, int i) {
		if (l == r) {
			sum[i] = arr[seg[l]] % MOD;
		} else {
			int mid = (l + r) / 2;
			build(l, mid, i << 1);
			build(mid + 1, r, i << 1 | 1);
			up(i);
		}
	}

	public static void add(int jobl, int jobr, int jobv, int l, int r, int i) {
		if (jobl <= l && r <= jobr) {
			lazy(i, jobv, r - l + 1);
		} else {
			int mid = (l + r) / 2;
			down(i, mid - l + 1, r - mid);
			if (jobl <= mid) {
				add(jobl, jobr, jobv, l, mid, i << 1);
			}
			if (jobr > mid) {
				add(jobl, jobr, jobv, mid + 1, r, i << 1 | 1);
			}
			up(i);
		}
	}

	public static long query(int jobl, int jobr, int l, int r, int i) {
		if (jobl <= l && r <= jobr) {
			return sum[i];
		}
		int mid = (l + r) / 2;
		down(i, mid - l + 1, r - mid);
		long ans = 0;
		if (jobl <= mid) {
			ans = (ans + query(jobl, jobr, l, mid, i << 1)) % MOD;
		}
		if (jobr > mid) {
			ans = (ans + query(jobl, jobr, mid + 1, r, i << 1 | 1)) % MOD;
		}
		return ans;
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
		add(Math.min(dfn[x], dfn[y]), Math.max(dfn[x], dfn[y]), v, 1, n, 1);
	}

	public static void subtreeAdd(int x, int v) {
		add(dfn[x], dfn[x] + siz[x] - 1, v, 1, n, 1);
	}

	public static long pathSum(int x, int y) {
		long ans = 0;
		while (top[x] != top[y]) {
			if (dep[top[x]] <= dep[top[y]]) {
				ans = (ans + query(dfn[top[y]], dfn[y], 1, n, 1)) % MOD;
				y = fa[top[y]];
			} else {
				ans = (ans + query(dfn[top[x]], dfn[x], 1, n, 1)) % MOD;
				x = fa[top[x]];
			}
		}
		ans = (ans + query(Math.min(dfn[x], dfn[y]), Math.max(dfn[x], dfn[y]), 1, n, 1)) % MOD;
		return ans;
	}

	public static long subtreeSum(int x) {
		return query(dfn[x], dfn[x] + siz[x] - 1, 1, n, 1);
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		n = (int) in.nval;
		in.nextToken();
		m = (int) in.nval;
		in.nextToken();
		root = (int) in.nval;
		in.nextToken();
		MOD = (int) in.nval;
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
		dfs3(); // dfs3() 等同于 dfs1(root, 0)，调用迭代版防止爆栈
		dfs4(); // dfs4() 等同于 dfs2(root, root)，调用迭代版防止爆栈
		build(1, n, 1);
		for (int i = 1, op, x, y, v; i <= m; i++) {
			in.nextToken();
			op = (int) in.nval;
			if (op == 1) {
				in.nextToken();
				x = (int) in.nval;
				in.nextToken();
				y = (int) in.nval;
				in.nextToken();
				v = (int) in.nval;
				pathAdd(x, y, v);
			} else if (op == 2) {
				in.nextToken();
				x = (int) in.nval;
				in.nextToken();
				y = (int) in.nval;
				out.println(pathSum(x, y));
			} else if (op == 3) {
				in.nextToken();
				x = (int) in.nval;
				in.nextToken();
				v = (int) in.nval;
				subtreeAdd(x, v);
			} else {
				in.nextToken();
				x = (int) in.nval;
				out.println(subtreeSum(x));
			}
		}
		out.flush();
		out.close();
		br.close();
	}
}