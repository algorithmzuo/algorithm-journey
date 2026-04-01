package class195;

// 美丽的树，树剖优化建图，java版
// 一共n个节点，给定n-1条无向边，所有节点组成一棵树，1号节点是根
// 你需要根据一共m条关系，给每个节点赋值，关系的具体格式如下
// 关系 1 a b c : 节点a到节点b的路径上，值最小的节点必须是节点c，输入保证c一定在路径上
// 关系 2 a b c : 节点a到节点b的路径上，值最大的节点必须是节点c，输入保证c一定在路径上
// 如果存在赋值方案，并且这些值是1到n的一个排列，打印一种方案即可，否则打印-1
// 2 <= n、m <= 2 * 10^5
// 测试链接 : https://www.luogu.com.cn/problem/CF1904F
// 测试链接 : https://codeforces.com/problemset/problem/1904/F
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code06_BeautifulTreeHLD1 {

	public static int MAXN = 200001;
	public static int MAXT = MAXN * 10;
	public static int MAXE = MAXN * 50;
	public static int n, m;

	public static int[] head1 = new int[MAXN];
	public static int[] next1 = new int[MAXN << 1];
	public static int[] to1 = new int[MAXN << 1];
	public static int cnt1;

	public static int[] indegree = new int[MAXT];
	public static int[] head2 = new int[MAXT];
	public static int[] next2 = new int[MAXE];
	public static int[] to2 = new int[MAXE];
	public static int cnt2;

	public static int[] ls = new int[MAXT];
	public static int[] rs = new int[MAXT];
	public static int rootOut, rootIn;
	public static int cntt;

	public static int[] fa = new int[MAXN];
	public static int[] dep = new int[MAXN];
	public static int[] siz = new int[MAXN];
	public static int[] son = new int[MAXN];
	public static int[] top = new int[MAXN];
	public static int[] dfn = new int[MAXN];
	public static int[] seg = new int[MAXN];
	public static int cntd;

	public static int[] que = new int[MAXT];
	public static int[] ans = new int[MAXN];

	public static void addEdge1(int u, int v) {
		next1[++cnt1] = head1[u];
		to1[cnt1] = v;
		head1[u] = cnt1;
	}

	public static void addEdge2(int u, int v) {
		indegree[v]++;
		next2[++cnt2] = head2[u];
		to2[cnt2] = v;
		head2[u] = cnt2;
	}

	public static int buildOut(int l, int r) {
		int rt;
		if (l == r) {
			rt = l;
		} else {
			rt = ++cntt;
			int mid = (l + r) >> 1;
			ls[rt] = buildOut(l, mid);
			rs[rt] = buildOut(mid + 1, r);
			addEdge2(ls[rt], rt);
			addEdge2(rs[rt], rt);
		}
		return rt;
	}

	public static int buildIn(int l, int r) {
		int rt;
		if (l == r) {
			rt = l;
		} else {
			rt = ++cntt;
			int mid = (l + r) >> 1;
			ls[rt] = buildIn(l, mid);
			rs[rt] = buildIn(mid + 1, r);
			addEdge2(rt, ls[rt]);
			addEdge2(rt, rs[rt]);
		}
		return rt;
	}

	public static void xToRange(int jobx, int jobl, int jobr, int l, int r, int i) {
		if (jobl > jobr) {
			return;
		}
		if (jobl <= l && r <= jobr) {
			addEdge2(jobx, i);
		} else {
			int mid = (l + r) >> 1;
			if (jobl <= mid) {
				xToRange(jobx, jobl, jobr, l, mid, ls[i]);
			}
			if (jobr > mid) {
				xToRange(jobx, jobl, jobr, mid + 1, r, rs[i]);
			}
		}
	}

	public static void rangeToX(int jobl, int jobr, int jobx, int l, int r, int i) {
		if (jobl > jobr) {
			return;
		}
		if (jobl <= l && r <= jobr) {
			addEdge2(i, jobx);
		} else {
			int mid = (l + r) >> 1;
			if (jobl <= mid) {
				rangeToX(jobl, jobr, jobx, l, mid, ls[i]);
			}
			if (jobr > mid) {
				rangeToX(jobl, jobr, jobx, mid + 1, r, rs[i]);
			}
		}
	}

	public static void dfs1(int u, int f) {
		fa[u] = f;
		dep[u] = dep[f] + 1;
		siz[u] = 1;
		for (int e = head1[u], v; e > 0; e = next1[e]) {
			v = to1[e];
			if (v != f) {
				dfs1(v, u);
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
		seg[cntd] = u;
		if (son[u] == 0) {
			return;
		}
		dfs2(son[u], t);
		for (int e = head1[u], v; e > 0; e = next1[e]) {
			v = to1[e];
			if (v != fa[u] && v != son[u]) {
				dfs2(v, v);
			}
		}
	}

	public static void pathSet(int op, int x, int y, int z) {
		if (op == 1) {
			if (x <= z && z <= y) {
				xToRange(z, x, z - 1, 1, n, rootIn);
				xToRange(z, z + 1, y, 1, n, rootIn);
			} else {
				xToRange(z, x, y, 1, n, rootIn);
			}
		} else {
			if (x <= z && z <= y) {
				rangeToX(x, z - 1, z, 1, n, rootOut);
				rangeToX(z + 1, y, z, 1, n, rootOut);
			} else {
				rangeToX(x, y, z, 1, n, rootOut);
			}
		}
	}

	public static void link(int op, int a, int b, int c) {
		while (top[a] != top[b]) {
			if (dep[top[a]] < dep[top[b]]) {
				int tmp = a;
				a = b;
				b = tmp;
			}
			pathSet(op, dfn[top[a]], dfn[a], dfn[c]);
			a = fa[top[a]];
		}
		pathSet(op, Math.min(dfn[a], dfn[b]), Math.max(dfn[a], dfn[b]), dfn[c]);
	}

	public static boolean topo() {
		int qi = 1, qsiz = 0;
		for (int i = 1; i <= cntt; i++) {
			if (indegree[i] == 0) {
				que[++qsiz] = i;
			}
		}
		int val = 0;
		while (qi <= qsiz) {
			int u = que[qi++];
			if (u <= n) {
				ans[seg[u]] = ++val;
			}
			for (int e = head2[u]; e > 0; e = next2[e]) {
				int v = to2[e];
				if (--indegree[v] == 0) {
					que[++qsiz] = v;
				}
			}
		}
		return qsiz == cntt;
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		cntt = n;
		rootOut = buildOut(1, n);
		rootIn = buildIn(1, n);
		for (int i = 1, u, v; i < n; i++) {
			u = in.nextInt();
			v = in.nextInt();
			addEdge1(u, v);
			addEdge1(v, u);
		}
		dfs1(1, 0);
		dfs2(1, 1);
		for (int i = 1, op, a, b, c; i <= m; i++) {
			op = in.nextInt();
			a = in.nextInt();
			b = in.nextInt();
			c = in.nextInt();
			link(op, a, b, c);
		}
		boolean check = topo();
		if (check) {
			for (int i = 1; i <= n; i++) {
				out.print(ans[i]);
				out.print(" ");
			}
		} else {
			out.println(-1);
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
