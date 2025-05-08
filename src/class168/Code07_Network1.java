package class168;

// 网络，java版
// 一共有n个服务器，给定n-1条边，所有服务器连成一棵树
// 某两个服务器之间的路径上，可能接受一条请求，路径上的所有服务器都需要保存该请求的重要度
// 一共有m条操作，每条操作是如下3种类型中的一种，操作依次发生，第i条操作发生的时间为i
// 操作 0 a b v : a号服务器到b号服务器的路径上，增加了一个重要度为v的请求
// 操作 1 t     : 当初时间为t的操作，一定是增加请求的操作，现在这个请求结束了
// 操作 2 x     : 当前时间下，和x号服务器无关的所有请求中，打印最大的重要度
// 关于操作2，如果当前时间下，没有任何请求、或者所有请求都和x号服务器有关，打印-1
// 2 <= n <= 10^5    1 <= m <= 2 * 10^5    1 <= 重要度 <= 10^9
// 测试链接 : https://www.luogu.com.cn/problem/P3250
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

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
	public static int MAXH = 20;
	public static int n, m;

	// 链式前向星
	public static int[] head = new int[MAXN];
	public static int[] next = new int[MAXN << 1];
	public static int[] to = new int[MAXN << 1];
	public static int cntg = 0;

	// 树上点差分 + 树上倍增
	public static int[] fa = new int[MAXN];
	public static int[] dep = new int[MAXN];
	public static int[] siz = new int[MAXN];
	public static int[] dfn = new int[MAXN];
	public static int[][] stjump = new int[MAXN][MAXH];
	public static int cntd;

	// 树状数组
	public static int[] tree = new int[MAXN];

	// 从早到晚发生的事件
	public static int[][] events = new int[MAXM][4];
	public static int[] sorted = new int[MAXM];
	public static int s = 0;

	// 整体二分
	public static int[][] lset = new int[MAXM][4];
	public static int[][] rset = new int[MAXM][4];
	public static int[] ans = new int[MAXM];
	public static int cntans = 0;

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
		dfn[u] = ++cntd;
		stjump[u][0] = f;
		for (int p = 1; p < MAXH; p++) {
			stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
		}
		for (int e = head[u]; e != 0; e = next[e]) {
			if (to[e] != f) {
				dfs1(to[e], u);
			}
		}
		for (int e = head[u]; e != 0; e = next[e]) {
			if (to[e] != f) {
				siz[u] += siz[to[e]];
			}
		}
	}

	// 不会改迭代版，去看讲解118，详解了从递归版改迭代版
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

	// dfs1的迭代版
	public static void dfs2() {
		stacksize = 0;
		push(1, 0, -1);
		while (stacksize > 0) {
			pop();
			if (e == -1) {
				fa[u] = f;
				dep[u] = dep[f] + 1;
				siz[u] = 1;
				dfn[u] = ++cntd;
				stjump[u][0] = f;
				for (int p = 1; p < MAXH; p++) {
					stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
				}
				e = head[u];
			} else {
				e = next[e];
			}
			if (e != 0) {
				push(u, f, e);
				if (to[e] != f) {
					push(to[e], u, -1);
				}
			} else {
				for (int e = head[u]; e != 0; e = next[e]) {
					if (to[e] != f) {
						siz[u] += siz[to[e]];
					}
				}
			}
		}
	}

	public static int lca(int a, int b) {
		if (dep[a] < dep[b]) {
			int tmp = a;
			a = b;
			b = tmp;
		}
		for (int p = MAXH - 1; p >= 0; p--) {
			if (dep[stjump[a][p]] >= dep[b]) {
				a = stjump[a][p];
			}
		}
		if (a == b) {
			return a;
		}
		for (int p = MAXH - 1; p >= 0; p--) {
			if (stjump[a][p] != stjump[b][p]) {
				a = stjump[a][p];
				b = stjump[b][p];
			}
		}
		return stjump[a][0];
	}

	public static void add(int i, int v) {
		for (; i <= n; i += i & -i) {
			tree[i] += v;
		}
	}

	public static int query(int i) {
		int sum = 0;
		for (; i > 0; i -= i & -i) {
			sum += tree[i];
		}
		return sum;
	}

	public static void pathAdd(int x, int y, int v) {
		int xylca = lca(x, y);
		int lcafa = fa[xylca];
		add(dfn[x], v);
		add(dfn[y], v);
		add(dfn[xylca], -v);
		if (lcafa != 0) {
			add(dfn[lcafa], -v);
		}
	}

	public static int pointQuery(int x) {
		return query(dfn[x] + siz[x] - 1) - query(dfn[x] - 1);
	}

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

	public static void clone(int[] event1, int[] event2) {
		event1[0] = event2[0];
		event1[1] = event2[1];
		event1[2] = event2[2];
		event1[3] = event2[3];
	}

	public static void prepare() {
		dfs2(); // 为了防止爆栈调用迭代版
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
		if (evtl > evtr) {
			return;
		}
		if (impl == impr) {
			for (int i = evtl; i <= evtr; i++) {
				if (events[i][0] > 0) {
					ans[events[i][0]] = impl;
				}
			}
		} else {
			int impm = (impl + impr) / 2;
			int lsize = 0, rsize = 0, request = 0;
			for (int i = evtl; i <= evtr; i++) {
				if (events[i][0] == 0) {
					if (events[i][3] > impm) {
						pathAdd(events[i][1], events[i][2], 1);
						clone(rset[++rsize], events[i]);
						request++;
					} else {
						clone(lset[++lsize], events[i]);
					}
				} else if (events[i][0] == -1) {
					if (events[i][3] > impm) {
						pathAdd(events[i][1], events[i][2], -1);
						clone(rset[++rsize], events[i]);
						request--;
					} else {
						clone(lset[++lsize], events[i]);
					}
				} else {
					if (pointQuery(events[i][1]) != request) {
						clone(rset[++rsize], events[i]);
					} else {
						clone(lset[++lsize], events[i]);
					}
				}
			}
			for (int i = 1; i <= rsize; i++) {
				if (rset[i][0] == 0 && rset[i][3] > impm) {
					pathAdd(rset[i][1], rset[i][2], -1);
				}
				if (rset[i][0] == -1 && rset[i][3] > impm) {
					pathAdd(rset[i][1], rset[i][2], 1);
				}
			}
			for (int i = evtl, j = 1; j <= lsize; i++, j++) {
				clone(events[i], lset[j]);
			}
			for (int i = evtl + lsize, j = 1; j <= rsize; i++, j++) {
				clone(events[i], rset[j]);
			}
			compute(evtl, evtl + lsize - 1, impl, impm);
			compute(evtl + lsize, evtr, impm + 1, impr);
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
