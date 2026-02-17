package class192;

// 追寻文物，C++版
// 给定一张无向图，一共n个点、m条边，保证所有点连通
// 每条边除了端点之外，还有属性c表示该边上是否有商店
// 给定起点s和终点t，路途怎么走随意，但是沿途每条边只能经过一次
// 从s到t的路途中能遇到商店打印"YES"，否则打印"NO"
// 1 <= n、m <= 3 * 10^5
// 测试链接 : https://www.luogu.com.cn/problem/CF652E
// 测试链接 : https://codeforces.com/problemset/problem/652/E
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code01_PursuitArtifacts1 {

	public static int MAXN = 300001;
	public static int MAXM = 300001;
	public static int n, m, s, t;
	public static int[] a = new int[MAXM];
	public static int[] b = new int[MAXM];
	public static int[] c = new int[MAXM];

	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXM << 1];
	public static int[] to = new int[MAXM << 1];
	public static int[] weight = new int[MAXM << 1];
	public static int cntg;

	public static int[] dfn = new int[MAXN];
	public static int[] low = new int[MAXN];
	public static int cntd;

	public static int[] sta = new int[MAXN];
	public static int top;

	public static int[] belong = new int[MAXN];
	public static int[] val = new int[MAXN];
	public static int ebccCnt;

	public static void addEdge(int u, int v, int w) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		weight[cntg] = w;
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
			int w = c[i];
			if (ebcc1 == ebcc2) {
				if (w == 1) {
					val[ebcc1] = 1;
				}
			} else {
				addEdge(ebcc1, ebcc2, w);
				addEdge(ebcc2, ebcc1, w);
			}
		}
	}

	public static boolean check(int u, int fa, boolean ok) {
		ok |= val[u] > 0;
		if (u == t) {
			return ok;
		}
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			int w = weight[e];
			if (v != fa) {
				if (check(v, u, ok || w > 0)) {
					return true;
				}
			}
		}
		return false;
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
			c[i] = in.nextInt();
			addEdge(a[i], b[i], 0);
			addEdge(b[i], a[i], 0);
		}
		tarjan(1, 0);
		condense();
		s = in.nextInt();
		t = in.nextInt();
		s = belong[s];
		t = belong[t];
		if (check(s, 0, false)) {
			out.println("YES");
		} else {
			out.println("NO");
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
