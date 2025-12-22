package class186;

// 可持久化边分树，java版
// 树上有n个节点，给定n-1条边，每条边有边权
// 给定长度为n的数组arr，代表点编号组成的一个排列
// 接下来有q条操作，每条操作是如下两种类型中的一种
// 操作 1 x y z : 打印arr[x..y]中每个节点到节点z的简单路径距离之和
// 操作 2 x     : 交换arr[x]和arr[x+1]的值，输入保证 1 <= x < n
// 1 <= n、q <= 2 * 10^5
// 本题要求强制在线，得到操作参数的规则，打开测试链接查看
// 测试链接 : https://www.luogu.com.cn/problem/CF757G
// 测试链接 : https://codeforces.com/problemset/problem/757/G
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code04_PersistentEdgeDecompositionTree1 {

	public static int MAXN = 400001;
	public static int MAXT = MAXN * 30;
	public static int n, q, cntn;

	public static int[] head1 = new int[MAXN];
	public static int[] next1 = new int[MAXN << 1];
	public static int[] to1 = new int[MAXN << 1];
	public static int[] weight1 = new int[MAXN << 1];
	public static int cnt1;

	public static int[] head2 = new int[MAXN];
	public static int[] next2 = new int[MAXN << 1];
	public static int[] to2 = new int[MAXN << 1];
	public static int[] weight2 = new int[MAXN << 1];
	public static int cnt2;

	public static boolean[] vis = new boolean[MAXN];
	public static int[] siz = new int[MAXN];

	public static int[] up = new int[MAXN];
	public static int[] root = new int[MAXN];
	public static int[] ls = new int[MAXT];
	public static int[] rs = new int[MAXT];
	public static int[] lcnt = new int[MAXT];
	public static int[] rcnt = new int[MAXT];
	public static long[] lsum = new long[MAXT];
	public static long[] rsum = new long[MAXT];
	public static int cntt;

	public static int[] arr = new int[MAXN];
	public static int[] tree = new int[MAXN];

	public static void addEdge1(int u, int v, int w) {
		next1[++cnt1] = head1[u];
		to1[cnt1] = v;
		weight1[cnt1] = w;
		head1[u] = cnt1;
	}

	public static void addEdge2(int u, int v, int w) {
		next2[++cnt2] = head2[u];
		to2[cnt2] = v;
		weight2[cnt2] = w;
		head2[u] = cnt2;
	}

	public static void rebuild(int u, int fa) {
		int last = 0;
		for (int e = head1[u]; e > 0; e = next1[e]) {
			int v = to1[e];
			int w = weight1[e];
			if (v != fa) {
				if (last == 0) {
					last = u;
					addEdge2(u, v, w);
					addEdge2(v, u, w);
				} else {
					int add = ++cntn;
					addEdge2(last, add, 0);
					addEdge2(add, last, 0);
					addEdge2(add, v, w);
					addEdge2(v, add, w);
					last = add;
				}
				rebuild(v, u);
			}
		}
	}

	public static void getSize(int u, int fa) {
		siz[u] = 1;
		for (int e = head2[u]; e > 0; e = next2[e]) {
			int v = to2[e];
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
			for (int e = head2[u]; e > 0; e = next2[e]) {
				int v = to2[e];
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
			if (up[u] == 0) {
				up[u] = ++cntt;
				root[u] = cntt;
			}
			int cur = up[u];
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
			up[u] = nxt;
		}
		for (int e = head2[u]; e > 0; e = next2[e]) {
			int v = to2[e];
			if (v != fa && !vis[e >> 1]) {
				dfs(v, u, dist + weight2[e], op);
			}
		}
	}

	public static void solve(int u) {
		int edge = getCentroidEdge(u, 0);
		if (edge > 0) {
			vis[edge >> 1] = true;
			int v1 = to2[edge];
			int v2 = to2[edge ^ 1];
			dfs(v1, 0, 0, 0);
			dfs(v2, 0, weight2[edge], 1);
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
			addEdge1(u, v, w);
			addEdge1(v, u, w);
		}
		cntn = n;
		cnt2 = 1;
		rebuild(1, 0);
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
				tmp = arr[x];
				arr[x] = arr[x + 1];
				arr[x + 1] = tmp;
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
