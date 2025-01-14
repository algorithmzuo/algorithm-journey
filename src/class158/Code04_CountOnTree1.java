package class158;

// 路径上的第k小，java版
// 有n个节点，编号1~n，每个节点有权值，有n-1条边，所有节点组成一棵树
// 一共有m条查询，每条查询 u v k : 打印u号点到v号点的路径上，第k小的点权
// 题目有强制在线的要求，上一次打印的答案为lastAns，初始时lastAns = 0
// 每次给定的u、v、k，按照如下方式得到真实的u、v、k，查询完成后更新lastAns
// 真实u = 给定u ^ lastAns
// 真实v = 给定v
// 真实k = 给定k
// 1 <= n、m <= 10^5
// 1 <= arr[i] <= 2^32 - 1
// 测试链接 : https://www.luogu.com.cn/problem/P2633
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code04_CountOnTree1 {

	public static int MAXN = 100001;

	public static int MAXH = 20;

	public static int MAXT = MAXN * MAXH;

	public static int n, m, s;

	// 各个节点权值
	public static int[] arr = new int[MAXN];

	// 收集权值排序并且去重做离散化
	public static int[] sorted = new int[MAXN];

	// 链式前向星需要
	public static int[] head = new int[MAXN];

	public static int[] to = new int[MAXN << 1];

	public static int[] next = new int[MAXN << 1];

	public static int cntg = 0;

	// 可持久化线段树需要
	public static int[] root = new int[MAXN];

	public static int[] left = new int[MAXT];

	public static int[] right = new int[MAXT];

	public static int[] size = new int[MAXT];

	public static int cntt = 0;

	// 树上倍增找lca需要
	public static int[] deep = new int[MAXN];

	public static int[][] stjump = new int[MAXN][MAXH];

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

	public static int build(int l, int r) {
		int rt = ++cntt;
		size[rt] = 0;
		if (l < r) {
			int mid = (l + r) / 2;
			left[rt] = build(l, mid);
			right[rt] = build(mid + 1, r);
		}
		return rt;
	}

	public static void prepare() {
		for (int i = 1; i <= n; i++) {
			sorted[i] = arr[i];
		}
		Arrays.sort(sorted, 1, n + 1);
		s = 1;
		for (int i = 2; i <= n; i++) {
			if (sorted[s] != sorted[i]) {
				sorted[++s] = sorted[i];
			}
		}
		root[0] = build(1, s);
	}

	public static void addEdge(int u, int v) {
		next[++cntg] = head[u];
		to[cntg] = v;
		head[u] = cntg;
	}

	public static int insert(int jobi, int l, int r, int i) {
		int rt = ++cntt;
		left[rt] = left[i];
		right[rt] = right[i];
		size[rt] = size[i] + 1;
		if (l < r) {
			int mid = (l + r) / 2;
			if (jobi <= mid) {
				left[rt] = insert(jobi, l, mid, left[rt]);
			} else {
				right[rt] = insert(jobi, mid + 1, r, right[rt]);
			}
		}
		return rt;
	}

	public static int query(int jobk, int l, int r, int u, int v, int lca, int lcafa) {
		if (l == r) {
			return l;
		}
		int lsize = size[left[u]] + size[left[v]] - size[left[lca]] - size[left[lcafa]];
		int mid = (l + r) / 2;
		if (lsize >= jobk) {
			return query(jobk, l, mid, left[u], left[v], left[lca], left[lcafa]);
		} else {
			return query(jobk - lsize, mid + 1, r, right[u], right[v], right[lca], right[lcafa]);
		}
	}

	// 递归版，C++可以通过，java无法通过，递归会爆栈
	public static void dfs1(int u, int f) {
		root[u] = insert(kth(arr[u]), 1, s, root[f]);
		deep[u] = deep[f] + 1;
		stjump[u][0] = f;
		for (int p = 1; p < MAXH; p++) {
			stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
		}
		for (int ei = head[u]; ei > 0; ei = next[ei]) {
			if (to[ei] != f) {
				dfs1(to[ei], u);
			}
		}
	}

	// 迭代版，都可以通过
	// 讲解118，详解了从递归版改迭代版
	public static int[][] ufe = new int[MAXN][3];

	public static int stackSize, u, f, e;

	public static void push(int u, int f, int e) {
		ufe[stackSize][0] = u;
		ufe[stackSize][1] = f;
		ufe[stackSize][2] = e;
		stackSize++;
	}

	public static void pop() {
		--stackSize;
		u = ufe[stackSize][0];
		f = ufe[stackSize][1];
		e = ufe[stackSize][2];
	}

	// dfs1的迭代版
	public static void dfs2() {
		stackSize = 0;
		push(1, 0, -1);
		while (stackSize > 0) {
			pop();
			if (e == -1) {
				root[u] = insert(kth(arr[u]), 1, s, root[f]);
				deep[u] = deep[f] + 1;
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
			}
		}
	}

	public static int lca(int a, int b) {
		if (deep[a] < deep[b]) {
			int tmp = a;
			a = b;
			b = tmp;
		}
		for (int p = MAXH - 1; p >= 0; p--) {
			if (deep[stjump[a][p]] >= deep[b]) {
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

	public static int kth(int u, int v, int k) {
		int lca = lca(u, v);
		int i = query(k, 1, s, root[u], root[v], root[lca], root[stjump[lca][0]]);
		return sorted[i];
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		n = (int) in.nval;
		in.nextToken();
		m = (int) in.nval;
		for (int i = 1; i <= n; i++) {
			in.nextToken();
			arr[i] = (int) in.nval;
		}
		prepare();
		for (int i = 1, u, v; i < n; i++) {
			in.nextToken();
			u = (int) in.nval;
			in.nextToken();
			v = (int) in.nval;
			addEdge(u, v);
			addEdge(v, u);
		}
		dfs2(); // 使用迭代版防止爆栈
		for (int i = 1, u, v, k, lastAns = 0; i <= m; i++) {
			in.nextToken();
			u = (int) in.nval ^ lastAns;
			in.nextToken();
			v = (int) in.nval;
			in.nextToken();
			k = (int) in.nval;
			lastAns = kth(u, v, k);
			out.println(lastAns);
		}
		out.flush();
		out.close();
		br.close();
	}

}
