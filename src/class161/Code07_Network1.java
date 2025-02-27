package class161;

// 网络，java版
// 测试链接 : https://www.luogu.com.cn/problem/P3250
// 提交以下的code，提交时请把类名改成"Main"，会有一个测试用例超时
// 因为这道题根据C++的运行时间，制定通过标准，根本没考虑java的用户
// 本节课Code07_Network2文件就是C++的实现
// 两个版本的逻辑完全一样，C++版本可以通过所有测试

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code07_Network1 {

	public static int MAXN = 100001;

	public static int MAXM = 200001;

	public static int n, m;

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

	public static int[] tree = new int[MAXN];

	public static int[][] events = new int[MAXM][4];

	public static int[][] levent = new int[MAXM][4];

	public static int[][] revent = new int[MAXM][4];

	public static int[] sorted = new int[MAXM];

	public static int s = 0;

	public static int[] ans = new int[MAXM];

	public static int cntans = 0;

	public static int kth(int num) {
		int left = 1, right = s, mid;
		while (left <= right) {
			mid = (left + right) / 2;
			if (sorted[mid] == num) {
				return mid;
			} else if (sorted[mid] < num) {
				left = mid + 1;
			} else {
				right = mid - 1;
			}
		}
		return -1;
	}

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

	public static void add(int i, int v) {
		for (; i <= n; i += i & -i) {
			tree[i] += v;
		}
	}

	public static void add(int l, int r, int v) {
		add(l, v);
		add(r + 1, -v);
	}

	public static int query(int i) {
		int sum = 0;
		for (; i > 0; i -= i & -i) {
			sum += tree[i];
		}
		return sum;
	}

	public static void pathAdd(int x, int y, int v) {
		while (top[x] != top[y]) {
			if (dep[top[x]] <= dep[top[y]]) {
				add(dfn[top[y]], dfn[y], v);
				y = fa[top[y]];
			} else {
				add(dfn[top[x]], dfn[x], v);
				x = fa[top[x]];
			}
		}
		add(Math.min(dfn[x], dfn[y]), Math.max(dfn[x], dfn[y]), v);
	}

	public static void clone(int[] event1, int[] event2) {
		event1[0] = event2[0];
		event1[1] = event2[1];
		event1[2] = event2[2];
		event1[3] = event2[3];
	}

	public static void prepare() {
		dfs3(); // dfs3() 等同于 dfs1(1, 0)，调用迭代版防止爆栈
		dfs4(); // dfs4() 等同于 dfs2(1, 1)，调用迭代版防止爆栈
		sorted[0] = -1;
		for (int i = 1; i <= m; i++) {
			if (events[i][0] == 0) {
				sorted[++s] = events[i][3];
			}
		}
		Arrays.sort(sorted, 1, s + 1);
		int len = 1;
		for (int i = 2; i <= s; i++) {
			if (sorted[len] != sorted[i]) {
				sorted[++len] = sorted[i];
			}
		}
		s = len;
		for (int i = 1; i <= m; i++) {
			if (events[i][0] == 0) {
				events[i][3] = kth(events[i][3]);
			} else if (events[i][0] == 1) {
				clone(events[i], events[events[i][1]]);
				events[i][0] = -1;
			} else {
				events[i][0] = ++cntans;
			}
		}
	}

	public static void compute(int evtl, int evtr, int impl, int impr) {
		if (impl == impr) {
			for (int i = evtl; i <= evtr; i++) {
				if (events[i][0] > 0) {
					ans[events[i][0]] = impl;
				}
			}
		} else {
			int impm = (impl + impr) / 2;
			int left = 0, right = 0, activeCnt = 0;
			for (int i = evtl; i <= evtr; i++) {
				if (events[i][0] == 0) {
					if (events[i][3] > impm) {
						pathAdd(events[i][1], events[i][2], 1);
						clone(revent[++right], events[i]);
						activeCnt++;
					} else {
						clone(levent[++left], events[i]);
					}
				} else if (events[i][0] == -1) {
					if (events[i][3] > impm) {
						pathAdd(events[i][1], events[i][2], -1);
						clone(revent[++right], events[i]);
						activeCnt--;
					} else {
						clone(levent[++left], events[i]);
					}
				} else {
					int sum = query(dfn[events[i][1]]);
					if (sum != activeCnt) {
						clone(revent[++right], events[i]);
					} else {
						clone(levent[++left], events[i]);
					}
				}
			}
			for (int i = 1; i <= right; i++) {
				if (revent[i][0] == 0) {
					if (revent[i][3] > impm) {
						pathAdd(revent[i][1], revent[i][2], -1);
					}
				} else if (revent[i][0] == -1 && revent[i][3] > impm) {
					pathAdd(revent[i][1], revent[i][2], 1);
				}
			}
			for (int i = 1; i <= left; i++) {
				clone(events[evtl + i - 1], levent[i]);
			}
			for (int i = 1; i <= right; i++) {
				clone(events[evtl + left + i - 1], revent[i]);
			}
			compute(evtl, evtl + left - 1, impl, impm);
			compute(evtl + left, evtr, impm + 1, impr);
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
		for (int i = 1; i <= m; i++) {
			in.nextToken();
			events[i][0] = (int) in.nval;
			in.nextToken();
			events[i][1] = (int) in.nval;
			if (events[i][0] == 0) {
				in.nextToken();
				events[i][2] = (int) in.nval;
				in.nextToken();
				events[i][3] = (int) in.nval;
			}
		}
		prepare();
		compute(1, m, 0, s);
		for (int i = 1; i <= cntans; i++) {
			out.println(sorted[ans[i]]);
		}
		out.flush();
		out.close();
	}

}
