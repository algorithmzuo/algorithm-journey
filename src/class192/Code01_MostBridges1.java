package class192;

// 最多的桥，java版
// 测试链接 : https://www.luogu.com.cn/problem/CF1000E
// 测试链接 : https://codeforces.com/problemset/problem/1000/E
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code01_MostBridges1 {

	public static int MAXN = 300001;
	public static int MAXM = 300001;
	public static int n, m;
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

	public static int[] dist = new int[MAXN];
	public static int diameter;

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

	public static void dp(int u, int fa) {
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (v != fa) {
				dp(v, u);
				diameter = Math.max(diameter, dist[u] + dist[v] + 1);
				dist[u] = Math.max(dist[u], dist[v] + 1);
			}
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		cntg = 1;
		n = in.nextInt();
		m = in.nextInt();
		for (int i = 1; i <= m; i++) {
			a[i] = in.nextInt();
			b[i] = in.nextInt();
			addEdge(a[i], b[i]);
			addEdge(b[i], a[i]);
		}
		tarjan(1, 0);
		condense();
		dp(1, 0);
		out.println(diameter);
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
