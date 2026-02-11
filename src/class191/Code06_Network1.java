package class191;

// 网络，java版
// 测试链接 : https://acm.hdu.edu.cn/showproblem.php?pid=2460
// 测试链接 : http://poj.org/problem?id=3694
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code06_Network1 {

	public static int MAXN = 100001;
	public static int MAXM = 200001;
	public static int t, n, m, q;
	public static int[] a = new int[MAXM];
	public static int[] b = new int[MAXM];

	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXM << 1];
	public static int[] to = new int[MAXM << 1];
	public static int cntg;

	public static int[] dfn = new int[MAXN];
	public static int[] low = new int[MAXN];
	public static int cntd;

	public static int[] sta = new int[MAXN];
	public static int top;

	public static int[] belong = new int[MAXN];
	public static int ebccCnt;

	public static int[] up = new int[MAXN];
	public static int[] dep = new int[MAXN];
	public static int[] fa = new int[MAXN];

	public static void prepare() {
		cntg = 1;
		cntd = top = ebccCnt = 0;
		for (int i = 1; i <= n; i++) {
			head[i] = dfn[i] = low[i] = 0;
		}
	}

	public static void addEdge(int u, int v) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		head[u] = cntg;
	}

	public static void tarjan(int u, int preEdge) {
		dfn[u] = low[u] = ++cntd;
		sta[++top] = u;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			if ((e ^ 1) == preEdge) {
				continue;
			}
			int v = to[e];
			if (dfn[v] == 0) {
				tarjan(v, e);
				low[u] = Math.min(low[u], low[v]);
			} else {
				low[u] = Math.min(low[u], dfn[v]);
			}
		}
		if (dfn[u] == low[u]) {
			ebccCnt++;
			int pop;
			do {
				pop = sta[top--];
				belong[pop] = ebccCnt;
			} while (pop != u);
		}
	}

	public static void condense() {
		cntg = 0;
		for (int i = 1; i <= ebccCnt; i++) {
			head[i] = 0;
		}
		for (int i = 1; i <= m; i++) {
			int ebcc1 = belong[a[i]];
			int ebcc2 = belong[b[i]];
			if (ebcc1 != ebcc2) {
				addEdge(ebcc1, ebcc2);
				addEdge(ebcc2, ebcc1);
			}
		}
	}

	public static void dfs(int u, int f) {
		dep[u] = dep[f] + 1;
		up[u] = f;
		fa[u] = u;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (v != f) {
				dfs(v, u);
			}
		}
	}

	public static int find(int i) {
		if (i != fa[i]) {
			fa[i] = find(fa[i]);
		}
		return fa[i];
	}

	public static void union(int x, int y) {
		x = find(x);
		y = find(y);
		if (x != y) {
			if (dep[x] < dep[y]) {
				fa[y] = x;
			} else {
				fa[x] = y;
			}
		}
	}

	public static void link(int x, int y) {
		x = find(belong[x]);
		y = find(belong[y]);
		while (x != y) {
			if (dep[x] >= dep[y]) {
				union(x, up[x]);
				x = find(x);
			} else {
				union(y, up[y]);
				y = find(y);
			}
			ebccCnt--;
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		t = 0;
		n = in.nextInt();
		m = in.nextInt();
		while (n != 0 || m != 0) {
			prepare();
			for (int i = 1; i <= m; i++) {
				a[i] = in.nextInt();
				b[i] = in.nextInt();
				addEdge(a[i], b[i]);
				addEdge(b[i], a[i]);
			}
			tarjan(1, 0);
			condense();
			dfs(1, 0);
			out.println("Case " + (++t) + ":");
			q = in.nextInt();
			for (int i = 1, x, y; i <= q; i++) {
				x = in.nextInt();
				y = in.nextInt();
				link(x, y);
				out.println(ebccCnt - 1);
			}
			out.println();
			n = in.nextInt();
			m = in.nextInt();
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
