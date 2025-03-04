package class161;

// 遥远的国度，java版
// 一共有n个节点，给定n-1条边，节点连成一棵树，给定树的初始头节点，给定每个点的点权
// 一共有m条操作，每种操作是如下3种类型中的一种
// 操作 1 x     : 树的头节点变成x，整棵树需要重新组织
// 操作 2 x y v : x到y的路径上，所有节点的值改成v
// 操作 3 x     : 在当前树的状态下，打印u的子树中的最小值
// 1 <= n、m <= 10^5
// 任何时候节点值一定是正数
// 测试链接 : https://www.luogu.com.cn/problem/P3979
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code07_FarAway1 {

	public static int MAXN = 100001;
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

	public static int[] min = new int[MAXN << 2];
	// 重置操作的懒更新信息
	// 因为题目说了，任何时候节点值一定是正数
	// change[i] == 0，表示没有重置懒更新
	// change[i] != 0，表示范围内的数字修改为change[i]
	public static int[] change = new int[MAXN << 2];

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
		min[i] = Math.min(min[i << 1], min[i << 1 | 1]);
	}

	public static void lazy(int i, int v) {
		min[i] = v;
		change[i] = v;
	}

	public static void down(int i) {
		if (change[i] != 0) {
			lazy(i << 1, change[i]);
			lazy(i << 1 | 1, change[i]);
			change[i] = 0;
		}
	}

	public static void build(int l, int r, int i) {
		if (l == r) {
			min[i] = arr[seg[l]];
		} else {
			int mid = (l + r) / 2;
			build(l, mid, i << 1);
			build(mid + 1, r, i << 1 | 1);
			up(i);
		}
	}

	public static void update(int jobl, int jobr, int jobv, int l, int r, int i) {
		if (jobl <= l && r <= jobr) {
			lazy(i, jobv);
		} else {
			down(i);
			int mid = (l + r) / 2;
			if (jobl <= mid) {
				update(jobl, jobr, jobv, l, mid, i << 1);
			}
			if (jobr > mid) {
				update(jobl, jobr, jobv, mid + 1, r, i << 1 | 1);
			}
			up(i);
		}
	}

	public static int query(int jobl, int jobr, int l, int r, int i) {
		if (jobl <= l && r <= jobr) {
			return min[i];
		}
		down(i);
		int mid = (l + r) / 2;
		int ans = Integer.MAX_VALUE;
		if (jobl <= mid) {
			ans = Math.min(ans, query(jobl, jobr, l, mid, i << 1));
		}
		if (jobr > mid) {
			ans = Math.min(ans, query(jobl, jobr, mid + 1, r, i << 1 | 1));
		}
		return ans;
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
		update(Math.min(dfn[x], dfn[y]), Math.max(dfn[x], dfn[y]), v, 1, n, 1);
	}

	// 已知root一定在u的子树上
	// 找到u哪个儿子的子树里有root，返回那个儿子的编号
	public static int findSon(int root, int u) {
		while (top[root] != top[u]) {
			if (fa[top[root]] == u) {
				return top[root];
			}
			root = fa[top[root]];
		}
		return son[u];
	}

	// 假设树的头节点变成root，在当前树的状态下，查询u的子树中的最小值
	public static int treeQuery(int root, int u) {
		if (root == u) {
			return min[1];
		} else if (dfn[root] < dfn[u] || dfn[u] + siz[u] <= dfn[root]) {
			return query(dfn[u], dfn[u] + siz[u] - 1, 1, n, 1);
		} else {
			int uson = findSon(root, u);
			int ans = query(1, dfn[uson] - 1, 1, n, 1);
			if (dfn[uson] + siz[uson] <= n) {
				ans = Math.min(ans, query(dfn[uson] + siz[uson], n, 1, n, 1));
			}
			return ans;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		n = (int) in.nval;
		in.nextToken();
		m = (int) in.nval;
		for (int i = 1, u, v; i < n; i++) {
			in.nextToken();
			u = (int) in.nval;
			in.nextToken();
			v = (int) in.nval;
			addEdge(u, v);
			addEdge(v, u);
		}
		for (int i = 1; i <= n; i++) {
			in.nextToken();
			arr[i] = (int) in.nval;
		}
		dfs3(); // dfs3() 等同于 dfs1(1, 0)，调用迭代版防止爆栈
		dfs4(); // dfs4() 等同于 dfs2(1, 1)，调用迭代版防止爆栈
		build(1, n, 1);
		in.nextToken();
		int root = (int) in.nval;
		for (int i = 1, op, x, y, v; i <= m; i++) {
			in.nextToken();
			op = (int) in.nval;
			if (op == 1) {
				in.nextToken();
				root = (int) in.nval;
			} else if (op == 2) {
				in.nextToken();
				x = (int) in.nval;
				in.nextToken();
				y = (int) in.nval;
				in.nextToken();
				v = (int) in.nval;
				pathUpdate(x, y, v);
			} else {
				in.nextToken();
				x = (int) in.nval;
				out.println(treeQuery(root, x));
			}
		}
		out.flush();
		out.close();
		br.close();
	}

}
