package class191;

// 边双缩点模版题，java版
// 测试链接 : https://www.luogu.com.cn/problem/P2783
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

public class Code05_EbccCondense1 {

	public static int MAXN = 10001;
	public static int MAXM = 50001;
	public static int MAXP = 15;
	public static int n, m, q;
	public static int[][] edgeArr = new int[MAXM][2];

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

	public static int[] dep = new int[MAXN];
	public static int[][] stjump = new int[MAXN][MAXP];

	public static void addEdge(int u, int v) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		head[u] = cntg;
	}

	public static void buildGraph() {
		Arrays.sort(edgeArr, 1, m + 1, (a, b) -> a[0] != b[0] ? (a[0] - b[0]) : (a[1] - b[1]));
		int k = 1;
		for (int i = 2; i <= m; i++) {
			if (edgeArr[k][0] != edgeArr[i][0] || edgeArr[k][1] != edgeArr[i][1]) {
				edgeArr[++k][0] = edgeArr[i][0];
				edgeArr[k][1] = edgeArr[i][1];
			}
		}
		for (int i = 1; i <= k; i++) {
			addEdge(edgeArr[i][0], edgeArr[i][1]);
			addEdge(edgeArr[i][1], edgeArr[i][0]);
		}
		m = k;
	}

	public static void tarjan(int u, int preEdge) {
		dfn[u] = low[u] = ++cntd;
		sta[++top] = u;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (dfn[v] == 0) {
				tarjan(v, e);
				low[u] = Math.min(low[u], low[v]);
			} else {
				if ((e ^ 1) != preEdge) {
					low[u] = Math.min(low[u], dfn[v]);
				}
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
			int ebcc1 = belong[edgeArr[i][0]];
			int ebcc2 = belong[edgeArr[i][1]];
			if (ebcc1 != ebcc2) {
				addEdge(ebcc1, ebcc2);
				addEdge(ebcc2, ebcc1);
			}
		}
	}

	public static void dfs(int u, int fa) {
		dep[u] = dep[fa] + 1;
		stjump[u][0] = fa;
		for (int p = 1; p < MAXP; p++) {
			stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
		}
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (v != fa) {
				dfs(v, u);
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

	public static int getDist(int x, int y) {
		return dep[x] + dep[y] - 2 * dep[getLca(x, y)] + 1;
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		cntg = 1;
		n = in.nextInt();
		m = in.nextInt();
		for (int i = 1, u, v; i <= m; i++) {
			u = in.nextInt();
			v = in.nextInt();
			edgeArr[i][0] = Math.min(u, v);
			edgeArr[i][1] = Math.max(u, v);
		}
		buildGraph();
		tarjan(1, 0);
		condense();
		dfs(1, 0);
		q = in.nextInt();
		for (int i = 1, x, y; i <= q; i++) {
			x = in.nextInt();
			y = in.nextInt();
			x = belong[x];
			y = belong[y];
			out.println(Integer.toBinaryString(getDist(x, y)));
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
