package class186;

// 可持久化边分树，java版
// 测试链接 : https://www.luogu.com.cn/problem/CF757G
// 测试链接 : https://codeforces.com/problemset/problem/757/G
// java实现的逻辑一定是正确的，因为递归爆栈+卡空间，导致无法通过，索性保持最易懂的写法
// 想通过用C++实现，本节课Code04_PersistentEdgeDecompositionTree2文件就是C++的实现
// 两个版本的逻辑完全一样，C++版本可以通过所有测试

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code04_PersistentEdgeDecompositionTree1 {

	public static int MAXN = 500001;
	public static int MAXM = MAXN * 30;
	public static int n, q, cntn;
	public static int[] arr = new int[MAXN];

	public static int[] headg = new int[MAXN];
	public static int[] nextg = new int[MAXN << 1];
	public static int[] tog = new int[MAXN << 1];
	public static int[] weightg = new int[MAXN << 1];
	public static int cntg;

	public static int[] sonCnt = new int[MAXN];
	public static int[] heads = new int[MAXN];
	public static int[] nexts = new int[MAXM];
	public static int[] sons = new int[MAXM];
	public static int[] weights = new int[MAXM];
	public static int cnts;

	public static boolean[] vis = new boolean[MAXN];
	public static int[] siz = new int[MAXN];

	public static int[] root = new int[MAXN];
	public static int[] ls = new int[MAXM];
	public static int[] rs = new int[MAXM];
	public static int[] lcnt = new int[MAXM];
	public static int[] rcnt = new int[MAXM];
	public static long[] lsum = new long[MAXM];
	public static long[] rsum = new long[MAXM];
	public static int cntt;

	public static int[] latest = new int[MAXN];

	public static int[] tree = new int[MAXN];

	public static void addEdge(int u, int v, int w) {
		nextg[++cntg] = headg[u];
		tog[cntg] = v;
		weightg[cntg] = w;
		headg[u] = cntg;
	}

	public static void addSon(int u, int v, int w) {
		sonCnt[u]++;
		nexts[++cnts] = heads[u];
		sons[cnts] = v;
		weights[cnts] = w;
		heads[u] = cnts;
	}

	public static void buildSon(int u, int fa) {
		for (int e = headg[u]; e > 0; e = nextg[e]) {
			int v = tog[e];
			if (v != fa) {
				addSon(u, v, weightg[e]);
				buildSon(v, u);
			}
		}
	}

	public static void rebuildTree() {
		buildSon(1, 0);
		cntn = n;
		cntg = 1;
		for (int i = 1; i <= cntn; i++) {
			headg[i] = 0;
		}
		for (int u = 1; u <= cntn; u++) {
			if (sonCnt[u] <= 2) {
				for (int e = heads[u]; e > 0; e = nexts[e]) {
					int v = sons[e];
					int w = weights[e];
					addEdge(u, v, w);
					addEdge(v, u, w);
				}
			} else {
				int node1 = ++cntn;
				int node2 = ++cntn;
				addEdge(u, node1, 0);
				addEdge(node1, u, 0);
				addEdge(u, node2, 0);
				addEdge(node2, u, 0);
				boolean add1 = true;
				for (int e = heads[u]; e > 0; e = nexts[e]) {
					int v = sons[e];
					int w = weights[e];
					if (add1) {
						addSon(node1, v, w);
					} else {
						addSon(node2, v, w);
					}
					add1 = !add1;
				}
			}
		}
	}

	public static void getSize(int u, int fa) {
		siz[u] = 1;
		for (int e = headg[u]; e > 0; e = nextg[e]) {
			int v = tog[e];
			if (v != fa && !vis[e >> 1]) {
				getSize(v, u);
				siz[u] += siz[v];
			}
		}
	}

	public static int getCentroidEdge(int u, int fa) {
		getSize(u, fa);
		int total = siz[u];
		int edge = 0;
		int best = total;
		while (u > 0) {
			int nextu = 0, nextfa = 0;
			for (int e = headg[u]; e > 0; e = nextg[e]) {
				int v = tog[e];
				if (v != fa && !vis[e >> 1]) {
					int cur = Math.max(total - siz[v], siz[v]);
					if (cur < best) {
						edge = e;
						best = cur;
						nextfa = u;
						nextu = v;
					}
				}
			}
			fa = nextfa;
			u = nextu;
		}
		return edge;
	}

	public static void dfs(int u, int fa, long dist, int op) {
		if (u <= n) {
			if (latest[u] == 0) {
				latest[u] = ++cntt;
				root[u] = cntt;
			}
			int cur = latest[u];
			int nxt = ++cntt;
			if (op == 0) {
				ls[cur] = nxt;
				lsum[cur] = dist;
				lcnt[cur] = 1;
			} else {
				rs[cur] = nxt;
				rsum[cur] = dist;
				rcnt[cur] = 1;
			}
			latest[u] = nxt;
		}
		for (int e = headg[u]; e > 0; e = nextg[e]) {
			int v = tog[e];
			if (v != fa && !vis[e >> 1]) {
				dfs(v, u, dist + weightg[e], op);
			}
		}
	}

	public static void solve(int u) {
		int edge = getCentroidEdge(u, 0);
		if (edge > 0) {
			vis[edge >> 1] = true;
			int v1 = tog[edge];
			int v2 = tog[edge ^ 1];
			dfs(v1, 0, 0, 0);
			dfs(v2, 0, weightg[edge], 1);
			solve(v1);
			solve(v2);
		}
	}

	public static int add(int pre, int addt) {
		if (pre == 0 || addt == 0) {
			return pre + addt;
		}
		int rt = ++cntt;
		ls[rt] = ls[pre];
		rs[rt] = rs[pre];
		lcnt[rt] = lcnt[pre] + lcnt[addt];
		rcnt[rt] = rcnt[pre] + rcnt[addt];
		lsum[rt] = lsum[pre] + lsum[addt];
		rsum[rt] = rsum[pre] + rsum[addt];
		if (lcnt[addt] > 0) {
			ls[rt] = add(ls[rt], ls[addt]);
		}
		if (rcnt[addt] > 0) {
			rs[rt] = add(rs[rt], rs[addt]);
		}
		return rt;
	}

	public static long query(int i, int t1, int t2) {
		if (lcnt[i] == 0 && rcnt[i] == 0) {
			return 0;
		} else if (lcnt[i] > 0) {
			return query(ls[i], ls[t1], ls[t2]) + rsum[t2] - rsum[t1] + lsum[i] * (rcnt[t2] - rcnt[t1]);
		} else {
			return query(rs[i], rs[t1], rs[t2]) + lsum[t2] - lsum[t1] + rsum[i] * (lcnt[t2] - lcnt[t1]);
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		q = in.nextInt();
		for (int i = 1; i <= n; i++) {
			arr[i] = in.nextInt();
		}
		for (int i = 1, u, v, w; i < n; i++) {
			u = in.nextInt();
			v = in.nextInt();
			w = in.nextInt();
			addEdge(u, v, w);
			addEdge(v, u, w);
		}
		rebuildTree();
		solve(1);
		for (int i = 1; i <= n; i++) {
			tree[i] = add(tree[i - 1], root[arr[i]]);
		}
		long mask = (1L << 30) - 1;
		long lastAns = 0;
		long a, b, c;
		int op, x, y, z, tmp;
		for (int i = 1; i <= q; i++) {
			op = in.nextInt();
			if (op == 1) {
				a = in.nextInt();
				b = in.nextInt();
				c = in.nextInt();
				a ^= lastAns;
				b ^= lastAns;
				c ^= lastAns;
				x = (int) a;
				y = (int) b;
				z = (int) c;
				lastAns = query(root[z], tree[x - 1], tree[y]);
				out.println(lastAns);
				lastAns &= mask;
			} else {
				a = in.nextInt();
				a ^= lastAns;
				x = (int) a;
				tmp = arr[x]; arr[x] = arr[x + 1]; arr[x + 1] = tmp;
				tree[x] = add(tree[x - 1], root[arr[x]]);
			}
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
