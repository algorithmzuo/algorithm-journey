package class184;

// 支配点对距离，java版
// 一共有n个节点，给定n-1条边，每条边给定边权，所有节点组成一棵树
// 节点i到节点j的简单路径，边权的累加和，定义为dist(i, j)
// 编号区间[x, y]，考虑所有点对(a, b)，要求 x <= a < b <= y
// 如果dist(a, b)是所有情况中最小的，则称(a, b)为区间[x, y]的支配点对
// 也可以说，区间[x, y]的支配点对距离为dist(a, b)
// 特别的，如果x == y，那么不存在满足要求的点对，此时支配点对距离为-1
// 一共有m次查询，格式 x y : 保证x <= y，打印[x, y]的支配点对距离
// 1 <= n <= 2 * 10^5    1 <= m <= 10^6    1 <= 边权 <= 10^9
// 测试链接 : https://www.luogu.com.cn/problem/P9678
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

public class Code06_TreeDistance1 {

	public static int MAXN = 200001;
	public static int MAXM = 1000001;
	public static int MAXP = 10000001;
	public static long INF = 1L << 60;
	public static int n, m;

	// 所有查询
	public static int[] qx = new int[MAXM];
	public static int[] qy = new int[MAXM];
	public static int[] qid = new int[MAXM];

	// 保留的点对
	public static int[] pa = new int[MAXP];
	public static int[] pb = new int[MAXP];
	public static long[] pdist = new long[MAXP];
	public static int cntp;

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

	// 单调栈
	public static int[] sta = new int[MAXN];
	public static int top;

	// 维护最小值的线段树
	public static long[] minTree = new long[MAXN << 2];

	// 查询的答案
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

	// 所有查询根据y从小到大排序，java自带的排序慢，手撸双指针快排
	public static void sortQuery(int l, int r) {
		if (l >= r) return;
		int i = l, j = r, pivot = qy[(l + r) >> 1], tmp;
		while (i <= j) {
			while (qy[i] < pivot) i++;
			while (qy[j] > pivot) j--;
			if (i <= j) {
				tmp = qx[i]; qx[i] = qx[j]; qx[j] = tmp;
				tmp = qy[i]; qy[i] = qy[j]; qy[j] = tmp;
				tmp = qid[i]; qid[i] = qid[j]; qid[j] = tmp;
				i++; j--;
			}
		}
		sortQuery(l, j);
		sortQuery(i, r);
	}

	// 所有点对根据b从小到大排序，java自带的排序慢，手撸双指针快排
	public static void sortPair(int l, int r) {
		if (l >= r) return;
		int i = l, j = r, pivot = pb[(l + r) >> 1], t1;
		long t2;
		while (i <= j) {
			while (pb[i] < pivot) i++;
			while (pb[j] > pivot) j--;
			if (i <= j) {
				t1 = pa[i]; pa[i] = pa[j]; pa[j] = t1;
				t1 = pb[i]; pb[i] = pb[j]; pb[j] = t1;
				t2 = pdist[i]; pdist[i] = pdist[j]; pdist[j] = t2;
				i++; j--;
			}
		}
		sortPair(l, j);
		sortPair(i, r);
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

	public static void stackAdd(int cur) {
		while (top > 0 && dist[sta[top]] >= dist[cur]) {
			pa[++cntp] = Math.min(sta[top], cur);
			pb[cntp] = Math.max(sta[top], cur);
			pdist[cntp] = dist[sta[top]] + dist[cur];
			top--;
		}
		sta[++top] = cur;
	}

	public static void calc(int u) {
		cnta = 0;
		// dfs1(u, 0, 0);
		dfs2(u, 0, 0);
		Arrays.sort(nodeArr, 1, cnta + 1);
		top = 0;
		// 所有点的编号，从左往右遍历
		// 找右侧最近的、距离 <= dist的点，去生成点对
		for (int i = 1; i <= cnta; i++) {
			stackAdd(nodeArr[i]);
		}
		top = 0;
		// 所有点的编号，从右往左遍历
		// 找左侧最近的、距离 <= dist的点，去生成点对
		for (int i = cnta; i >= 1; i--) {
			stackAdd(nodeArr[i]);
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

	public static void up(int i) {
		minTree[i] = Math.min(minTree[i << 1], minTree[i << 1 | 1]);
	}

	public static void build(int l, int r, int i) {
		if (l == r) {
			minTree[i] = INF;
		} else {
			int mid = (l + r) >> 1;
			build(l, mid, i << 1);
			build(mid + 1, r, i << 1 | 1);
			up(i);
		}
	}

	public static void update(int jobi, long jobv, int l, int r, int i) {
		if (l == r) {
			minTree[i] = Math.min(minTree[i], jobv);
		} else {
			int mid = (l + r) >> 1;
			if (jobi <= mid) {
				update(jobi, jobv, l, mid, i << 1);
			} else {
				update(jobi, jobv, mid + 1, r, i << 1 | 1);
			}
			up(i);
		}
	}

	public static long query(int jobl, int jobr, int l, int r, int i) {
		if (jobl <= l && r <= jobr) {
			return minTree[i];
		}
		long ans = INF;
		int mid = (l + r) >> 1;
		if (jobl <= mid) {
			ans = Math.min(ans, query(jobl, jobr, l, mid, i << 1));
		}
		if (jobr > mid) {
			ans = Math.min(ans, query(jobl, jobr, mid + 1, r, i << 1 | 1));
		}
		return ans;
	}

	public static void compute() {
		solve(getCentroid(1, 0));
		sortQuery(1, m);
		sortPair(1, cntp);
		build(1, n, 1);
		for (int i = 1, j = 1; i <= m; i++) {
			for (; j <= cntp && pb[j] <= qy[i]; j++) {
				update(pa[j], pdist[j], 1, n, 1);
			}
			if (qx[i] == qy[i]) {
				ans[qid[i]] = -1;
			} else {
				ans[qid[i]] = query(qx[i], qy[i], 1, n, 1);
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
			qx[i] = in.nextInt();
			qy[i] = in.nextInt();
			qid[i] = i;
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
