package class158;

// 更为厉害，java版
// 为了方便理解，我改写了题意，但是改写的题意和原始题意等效
// 有n个节点，编号1~n，给定n-1条边，连成一棵树，1号点是树头
// 如果x是y的祖先节点，认为"x比y更厉害"
// 如果x到y的路径上，边的数量 <= 某个常数，认为"x和y是邻居"
// 一共有m条查询，每条查询 a k : 打印有多少三元组(a, b, c)满足如下规定
// a、b、c为三个不同的点；a和b都比c厉害；a和b是邻居，路径边的数量 <= 给定的k
// 1 <= n、m、k <= 3 * 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P3899
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code05_MoreImpressive1 {

	public static int MAXN = 300001;

	public static int MAXT = MAXN * 22;

	public static int n, m, depth;

	// 链式前向星需要
	public static int[] head = new int[MAXN];

	public static int[] to = new int[MAXN << 1];

	public static int[] next = new int[MAXN << 1];

	public static int cntg = 0;

	// 可持久化线段树需要
	public static int[] root = new int[MAXN];

	public static int[] left = new int[MAXT];

	public static int[] right = new int[MAXT];

	public static long[] sum = new long[MAXT];

	public static int cntt = 0;

	// dfs需要
	// deep[i] : i号节点的深度
	public static int[] deep = new int[MAXN];

	// size[i] : 以i号节点为头的树，有多少个节点
	public static int[] size = new int[MAXN];

	// dfn[i] : i号节点的dfn序号
	public static int[] dfn = new int[MAXN];

	public static int cntd = 0;

	public static void addEdge(int u, int v) {
		next[++cntg] = head[u];
		to[cntg] = v;
		head[u] = cntg;
	}

	public static int build(int l, int r) {
		int rt = ++cntt;
		sum[rt] = 0;
		if (l < r) {
			int mid = (l + r) / 2;
			left[rt] = build(l, mid);
			right[rt] = build(mid + 1, r);
		}
		return rt;
	}

	public static int add(int jobi, long jobv, int l, int r, int i) {
		int rt = ++cntt;
		left[rt] = left[i];
		right[rt] = right[i];
		sum[rt] = sum[i] + jobv;
		if (l < r) {
			int mid = (l + r) / 2;
			if (jobi <= mid) {
				left[rt] = add(jobi, jobv, l, mid, left[rt]);
			} else {
				right[rt] = add(jobi, jobv, mid + 1, r, right[rt]);
			}
		}
		return rt;
	}

	public static long query(int jobl, int jobr, int l, int r, int u, int v) {
		if (jobl <= l && r <= jobr) {
			return sum[v] - sum[u];
		}
		long ans = 0;
		int mid = (l + r) / 2;
		if (jobl <= mid) {
			ans += query(jobl, jobr, l, mid, left[u], left[v]);
		}
		if (jobr > mid) {
			ans += query(jobl, jobr, mid + 1, r, right[u], right[v]);
		}
		return ans;
	}

	// 递归版，C++可以通过，java无法通过，递归会爆栈
	public static void dfs1(int u, int f) {
		deep[u] = deep[f] + 1;
		depth = Math.max(depth, deep[u]);
		size[u] = 1;
		dfn[u] = ++cntd;
		for (int ei = head[u]; ei > 0; ei = next[ei]) {
			if (to[ei] != f) {
				dfs1(to[ei], u);
			}
		}
		for (int ei = head[u]; ei > 0; ei = next[ei]) {
			if (to[ei] != f) {
				size[u] += size[to[ei]];
			}
		}
	}

	// 递归版，C++可以通过，java无法通过，递归会爆栈
	public static void dfs2(int u, int f) {
		root[dfn[u]] = add(deep[u], size[u] - 1, 1, depth, root[dfn[u] - 1]);
		for (int ei = head[u]; ei > 0; ei = next[ei]) {
			if (to[ei] != f) {
				dfs2(to[ei], u);
			}
		}
	}

	// dfs1、dfs2，分别改成迭代版，dfs3、dfs4
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
	public static void dfs3() {
		stackSize = 0;
		push(1, 0, -1);
		while (stackSize > 0) {
			pop();
			if (e == -1) {
				deep[u] = deep[f] + 1;
				depth = Math.max(depth, deep[u]);
				size[u] = 1;
				dfn[u] = ++cntd;
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
				for (int ei = head[u]; ei > 0; ei = next[ei]) {
					if (to[ei] != f) {
						size[u] += size[to[ei]];
					}
				}
			}
		}
	}

	// dfs2的迭代版
	public static void dfs4() {
		stackSize = 0;
		push(1, 0, -1);
		while (stackSize > 0) {
			pop();
			if (e == -1) {
				root[dfn[u]] = add(deep[u], size[u] - 1, 1, depth, root[dfn[u] - 1]);
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

	public static void prepare() {
		depth = 0;
		dfs3(); // 使用迭代版防止爆栈
		root[0] = build(1, depth);
		dfs4(); // 使用迭代版防止爆栈
	}

	public static long compute(int a, int k) {
		long ans = (long) (size[a] - 1) * Math.min(k, deep[a] - 1);
		ans += query(deep[a] + 1, deep[a] + k, 1, depth, root[dfn[a] - 1], root[dfn[a] + size[a] - 1]);
		return ans;
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
		prepare();
		for (int i = 1, a, k; i <= m; i++) {
			in.nextToken();
			a = (int) in.nval;
			in.nextToken();
			k = (int) in.nval;
			out.println(compute(a, k));
		}
		out.flush();
		out.close();
		br.close();
	}

}
