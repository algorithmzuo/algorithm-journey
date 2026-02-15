package class192;

// 只能一个方向，java版
// 测试链接 : https://www.luogu.com.cn/problem/CF555E
// 测试链接 : https://codeforces.com/problemset/problem/555/E
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code06_OneDirection1 {

	public static int MAXN = 200001;
	public static int MAXM = 200001;
	public static int MAXP = 20;
	public static int n, m, q;
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

	public static int[] block = new int[MAXN];
	public static int[] dep = new int[MAXN];
	public static int[][] stjump = new int[MAXN][MAXP];

	public static int[] upCnt = new int[MAXN];
	public static int[] downCnt = new int[MAXN];

	public static boolean ans;
	public static boolean[] vis = new boolean[MAXN];

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

	public static void dfs(int u, int fa, int bid) {
		block[u] = bid;
		dep[u] = dep[fa] + 1;
		stjump[u][0] = fa;
		for (int p = 1; p < MAXP; p++) {
			stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
		}
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (v != fa) {
				dfs(v, u, bid);
			}
		}
	}

	public static int getLca(int a, int b) {
		if (dep[a] < dep[b]) {
			int tmp = a;
			a = b;
			b = tmp;
		}
		for (int p = MAXP - 1; p >= 0; p--) {
			if (dep[stjump[a][p]] >= dep[b]) {
				a = stjump[a][p];
			}
		}
		if (a == b) {
			return a;
		}
		for (int p = MAXP - 1; p >= 0; p--) {
			if (stjump[a][p] != stjump[b][p]) {
				a = stjump[a][p];
				b = stjump[b][p];
			}
		}
		return stjump[a][0];
	}

	public static void check(int u, int fa) {
		vis[u] = true;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (v != fa) {
				check(v, u);
				upCnt[u] += upCnt[v];
				downCnt[u] += downCnt[v];
			}
		}
		if (upCnt[u] > 0 && downCnt[u] > 0) {
			ans = false;
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		cntg = 1;
		n = in.nextInt();
		m = in.nextInt();
		q = in.nextInt();
		for (int i = 1; i <= m; i++) {
			a[i] = in.nextInt();
			b[i] = in.nextInt();
			addEdge(a[i], b[i]);
			addEdge(b[i], a[i]);
		}
		for (int i = 1; i <= n; i++) {
			if (dfn[i] == 0) {
				tarjan(i, 0);
			}
		}
		condense();
		for (int i = 1, b = 0; i <= ebccCnt; i++) {
			if (block[i] == 0) {
				dfs(i, 0, ++b);
			}
		}
		ans = true;
		for (int i = 1, x, y, xylca; i <= q; i++) {
			x = in.nextInt();
			y = in.nextInt();
			x = belong[x];
			y = belong[y];
			if (block[x] != block[y]) {
				ans = false;
				break;
			}
			xylca = getLca(x, y);
			upCnt[x]++;
			upCnt[xylca]--;
			downCnt[y]++;
			downCnt[xylca]--;
		}
		if (ans) {
			for (int i = 1; i <= ebccCnt; i++) {
				if (!vis[i]) {
					check(i, 0);
					if (!ans) {
						break;
					}
				}
			}
		}
		out.println(ans ? "Yes" : "No");
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
