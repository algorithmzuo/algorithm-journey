package class184;

// 支配点对距离，java版
// 一共有n个节点，给定n-1条边，每条边给定边权，所有节点组成一棵树
// 节点i到节点j的简单路径权值和，定义为dist(i, j)
// 在[x, y]范围上，选两个编号a、b，要求a < b，这样的选择可能有很多情况
// 如果dist(a, b)是所有情况中最小的，就说点对(a, b)支配了[x, y]范围
// 也可以说，[x, y]范围的支配点对距离是dist(a, b)
// 特别的，如果x == y，那么[x, y]范围的支配点对距离是-1
// 一共有m条查询，格式 x y : 输入保证x <= y，打印[x, y]范围的支配点对距离
// 1 <= n <= 2 * 10^5    1 <= m <= 10^6
// 测试链接 : https://www.luogu.com.cn/problem/P9678
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

public class Code05_TreeDistance1 {

	public static int MAXN = 200001;
	public static int MAXM = 1000001;
	public static int MAXK = 10000001;
	public static long INF = 1L << 60;
	public static int n, m;
	public static int[] queryl = new int[MAXM];
	public static int[] queryr = new int[MAXM];
	public static int[] queryId = new int[MAXM];

	public static int[] keyl = new int[MAXK];
	public static int[] keyr = new int[MAXK];
	public static long[] keyDist = new long[MAXK];
	public static int cntk;

	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXN << 1];
	public static int[] to = new int[MAXN << 1];
	public static int[] weight = new int[MAXN << 1];
	public static int cntg;

	public static boolean[] vis = new boolean[MAXN];
	public static int[] siz = new int[MAXN];

	public static long[] dist = new long[MAXN];
	public static int[] nodeArr = new int[MAXN];
	public static int cnta;
	public static int[] sta = new int[MAXN];
	public static long[] tree = new long[MAXN];

	public static long[] ans = new long[MAXM];

	// 讲解118，递归函数改成迭代所需要的栈
	public static int[][] stack = new int[MAXN][3];
	public static long[] distst = new long[MAXN];
	public static int u, f, e;
	public static long dis;
	public static int stacksize;

	public static void push(int u, int f, long dis, int e) {
		stack[stacksize][0] = u;
		stack[stacksize][1] = f;
		stack[stacksize][2] = e;
		distst[stacksize] = dis;
		stacksize++;
	}

	public static void pop() {
		--stacksize;
		u = stack[stacksize][0];
		f = stack[stacksize][1];
		e = stack[stacksize][2];
		dis = distst[stacksize];
	}

	// 所有查询，根据l的值，从大到小排序，java自带的排序慢，手撸双指针快排
	public static void sortQuery(int l, int r) {
		if (l >= r) return;
		int i = l, j = r, pivot = queryl[(l + r) >> 1], tmp;
		while (i <= j) {
			while (queryl[i] > pivot) i++;
			while (queryl[j] < pivot) j--;
			if (i <= j) {
				tmp = queryl[i]; queryl[i] = queryl[j]; queryl[j] = tmp;
				tmp = queryr[i]; queryr[i] = queryr[j]; queryr[j] = tmp;
				tmp = queryId[i]; queryId[i] = queryId[j]; queryId[j] = tmp;
				i++; j--;
			}
		}
		sortQuery(l, j);
		sortQuery(i, r);
	}

	// 所有关键点，根据l的值，从大到小排序，java自带的排序慢，手撸双指针快排
	public static void sortKey(int l, int r) {
		if (l >= r) return;
		int i = l, j = r, pivot = keyl[(l + r) >> 1], t1;
		long t2;
		while (i <= j) {
			while (keyl[i] > pivot) i++;
			while (keyl[j] < pivot) j--;
			if (i <= j) {
				t1 = keyl[i]; keyl[i] = keyl[j]; keyl[j] = t1;
				t1 = keyr[i]; keyr[i] = keyr[j]; keyr[j] = t1;
				t2 = keyDist[i]; keyDist[i] = keyDist[j]; keyDist[j] = t2;
				i++; j--;
			}
		}
		sortKey(l, j);
		sortKey(i, r);
	}

	public static void addEdge(int u, int v, int w) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		weight[cntg] = w;
		head[u] = cntg;
	}

	// 得到子树大小递归版，java会爆栈，C++可以通过
	public static void getSize1(int u, int fa) {
		siz[u] = 1;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (v != fa && !vis[v]) {
				getSize1(v, u);
				siz[u] += siz[v];
			}
		}
	}

	// 得到子树大小迭代版
	public static void getSize2(int cur, int fa) {
		stacksize = 0;
		push(cur, fa, 0, -1);
		while (stacksize > 0) {
			pop();
			if (e == -1) {
				siz[u] = 1;
				e = head[u];
			} else {
				e = nxt[e];
			}
			if (e != 0) {
				push(u, f, 0, e);
				int v = to[e];
				if (v != f && !vis[v]) {
					push(v, u, 0, -1);
				}
			} else {
				for (int ei = head[u]; ei > 0; ei = nxt[ei]) {
					int v = to[ei];
					if (v != f && !vis[v]) {
						siz[u] += siz[v];
					}
				}
			}
		}
	}

	public static int getCentroid(int u, int fa) {
		// getSize1(u, fa);
		getSize2(u, fa);
		int half = siz[u] >> 1;
		boolean find = false;
		while (!find) {
			find = true;
			for (int e = head[u]; e > 0; e = nxt[e]) {
				int v = to[e];
				if (v != fa && !vis[v] && siz[v] > half) {
					fa = u;
					u = v;
					find = false;
					break;
				}
			}
		}
		return u;
	}

	// 收集信息递归版，java会爆栈，C++可以通过
	public static void dfs1(int u, int fa, long dis) {
		dist[u] = dis;
		nodeArr[++cnta] = u;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			int w = weight[e];
			if (v != fa && !vis[v]) {
				dfs1(v, u, dis + w);
			}
		}
	}

	// 收集信息迭代版
	public static void dfs2(int cur, int fa, long d) {
		stacksize = 0;
		push(cur, fa, d, -1);
		while (stacksize > 0) {
			pop();
			if (e == -1) {
				dist[u] = dis;
				nodeArr[++cnta] = u;
				e = head[u];
			} else {
				e = nxt[e];
			}
			if (e != 0) {
				push(u, f, dis, e);
				int v = to[e];
				int w = weight[e];
				if (v != f && !vis[v]) {
					push(v, u, dis + w, -1);
				}
			}
		}
	}

	public static void addKey(int kl, int kr, long kdist) {
		keyl[++cntk] = kl;
		keyr[cntk] = kr;
		keyDist[cntk] = kdist;
	}

	public static void calc(int u) {
		cnta = 0;
		// dfs1(u, 0, 0);
		dfs2(u, 0, 0);
		Arrays.sort(nodeArr, 1, cnta + 1);
		int top = 0;
		for (int i = 1; i <= cnta; i++) {
			int cur = nodeArr[i];
			while (top > 0 && dist[sta[top]] >= dist[cur]) {
				addKey(sta[top], cur, dist[sta[top]] + dist[cur]);
				top--;
			}
			if (top > 0) {
				addKey(sta[top], cur, dist[sta[top]] + dist[cur]);
			}
			sta[++top] = cur;
		}
	}

	public static void solve(int u) {
		vis[u] = true;
		calc(u);
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (!vis[v]) {
				solve(getCentroid(v, u));
			}
		}
	}

	public static void buildTree() {
		for (int i = 1; i <= n; i++) {
			tree[i] = INF;
		}
	}

	public static int lowbit(int i) {
		return i & -i;
	}

	public static void add(int i, long v) {
		while (i <= n) {
			tree[i] = Math.min(tree[i], v);
			i += lowbit(i);
		}
	}

	public static long query(int i) {
		long ret = INF;
		while (i > 0) {
			ret = Math.min(ret, tree[i]);
			i -= lowbit(i);
		}
		return ret;
	}

	public static void compute() {
		solve(getCentroid(1, 0));
		sortQuery(1, m);
		sortKey(1, cntk);
		buildTree();
		int idx = 1;
		for (int i = 1; i <= m; i++) {
			while (idx <= cntk && keyl[idx] >= queryl[i]) {
				add(keyr[idx], keyDist[idx]);
				idx++;
			}
			if (queryl[i] == queryr[i]) {
				ans[queryId[i]] = -1;
			} else {
				ans[queryId[i]] = query(queryr[i]);
			}
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		for (int i = 1, u, v, w; i < n; i++) {
			u = in.nextInt();
			v = in.nextInt();
			w = in.nextInt();
			addEdge(u, v, w);
			addEdge(v, u, w);
		}
		m = in.nextInt();
		for (int i = 1; i <= m; i++) {
			queryl[i] = in.nextInt();
			queryr[i] = in.nextInt();
			queryId[i] = i;
		}
		compute();
		for (int i = 1; i <= m; i++) {
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
