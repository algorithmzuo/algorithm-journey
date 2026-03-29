package class195;

// 沙漠，java版
// 一共n个数字，所有数字都在 1 ~ 10^9 的范围，这是范围说明
// 接下来给定s条设置说明，格式 x v ，表示第x个数的值确定是v
// 接下来给定m条关系说明，格式 l r k x1 x2 ... xk 含义如下
// 第l到第r个数字，其中有k个数字，分别是第x1、第x2 .. 第xk个数字
// 这k个数字中的每一个，都比剩下的(r - l + 1 - k)个数字要大，严格大于
// 根据上面的说明，找到没有矛盾的，给每个数字赋值的方案，任何一个方案即可
// 如果存在方案打印"TAK"，然后打印每个数字，不存在方案打印"NIE"
// 1 <= n、s <= 10^5
// 1 <= m <= 2 * 10^5
// 所有k的累加和 <= 3 * 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P3588
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code04_Desert1 {

	public static int MAXN = 100001;
	public static int MAXT = MAXN * 5;
	public static int MAXE = MAXN * 20;
	public static int LIMIT = 1000000000;
	public static int n, s, m;

	public static int[] head = new int[MAXT];
	public static int[] nxt = new int[MAXE];
	public static int[] to = new int[MAXE];
	public static int[] weight = new int[MAXE];
	public static int cntg;

	public static int[] ls = new int[MAXT];
	public static int[] rs = new int[MAXT];
	public static int root;
	public static int cntt;

	public static int[] val = new int[MAXT];
	public static int[] indegree = new int[MAXT];
	public static int[] dist = new int[MAXT];
	public static int[] que = new int[MAXT];

	public static void addEdge(int u, int v, int w) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		weight[cntg] = w;
		head[u] = cntg;
		indegree[v]++;
	}

	public static int build(int l, int r) {
		int rt;
		if (l == r) {
			rt = l;
		} else {
			rt = ++cntt;
			int mid = (l + r) >> 1;
			ls[rt] = build(l, mid);
			rs[rt] = build(mid + 1, r);
			addEdge(rt, ls[rt], 0);
			addEdge(rt, rs[rt], 0);
		}
		return rt;
	}

	public static void xToRange(int jobx, int jobl, int jobr, int jobw, int l, int r, int i) {
		if (jobl > jobr) {
			return;
		}
		if (jobl <= l && r <= jobr) {
			addEdge(jobx, i, jobw);
		} else {
			int mid = (l + r) >> 1;
			if (jobl <= mid) {
				xToRange(jobx, jobl, jobr, jobw, l, mid, ls[i]);
			}
			if (jobr > mid) {
				xToRange(jobx, jobl, jobr, jobw, mid + 1, r, rs[i]);
			}
		}
	}

	public static boolean topo() {
		int qi = 1, qsiz = 0;
		for (int i = 1; i <= cntt; i++) {
			if (indegree[i] == 0) {
				que[++qsiz] = i;
			}
			dist[i] = val[i] > 0 ? val[i] : LIMIT;
		}
		while (qi <= qsiz) {
			int u = que[qi++];
			if (dist[u] < 1) {
				return false;
			}
			for (int e = head[u]; e > 0; e = nxt[e]) {
				int v = to[e];
				int w = weight[e];
				if (dist[v] > dist[u] + w) {
					dist[v] = dist[u] + w;
					if (val[v] != 0 && dist[v] < val[v]) {
						return false;
					}
				}
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
		s = in.nextInt();
		m = in.nextInt();
		cntt = n;
		root = build(1, n);
		for (int i = 1; i <= s; i++) {
			int x = in.nextInt();
			int v = in.nextInt();
			val[x] = v;
		}
		for (int i = 1; i <= m; i++) {
			int l = in.nextInt();
			int r = in.nextInt();
			int k = in.nextInt();
			int vnode = ++cntt;
			for (int j = 1; j <= k; j++) {
				int x = in.nextInt();
				addEdge(x, vnode, 0);
				xToRange(vnode, l, x - 1, -1, 1, n, root);
				l = x + 1;
			}
			xToRange(vnode, l, r, -1, 1, n, root);
		}
		boolean check = topo();
		if (check) {
			out.println("TAK");
			for (int i = 1; i <= n; i++) {
				out.print(dist[i] + " ");
			}
			out.println();
		} else {
			out.println("NIE");
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
