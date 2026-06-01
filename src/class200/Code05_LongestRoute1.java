package class200;

// 边不重复的最长路线，java版
// 测试链接 : https://www.luogu.com.cn/problem/P6335
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code05_LongestRoute1 {

	public static int MAXN = 10001;
	public static int MAXM = 20001;
	public static int n, m;

	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXM << 1];
	public static int[] to = new int[MAXM << 1];
	public static int cntg;

	public static int[] dfn = new int[MAXN];
	public static int[] low = new int[MAXN];
	public static int cntd;
	public static int[] sta = new int[MAXN];
	public static int top;

	public static int[] cycle = new int[MAXN];

	// f[u] : u向下的结构中，从u出发最后回到u的最大长度
	public static int[] f = new int[MAXN];

	// g[u] : u向下的结构中，从u出发最后可以不回到u的最大长度
	public static int[] g = new int[MAXN];

	public static void addEdge(int u, int v) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		head[u] = cntg;
	}

	public static int dpOnCycle(int u, int v) {
		int siz = 0;
		int pop;
		do {
			pop = sta[top--];
			cycle[++siz] = pop;
		} while (pop != v);
		int best = 0;
		int sum = 1;
		for (int i = 1; i <= siz; i++) {
			best = Math.max(best, sum + g[cycle[i]]);
			sum += f[cycle[i]] + 1;
		}
		f[u] += sum;
		int rest = sum;
		for (int i = 1; i <= siz; i++) {
			rest -= f[cycle[i]] + 1;
			best = Math.max(best, rest + g[cycle[i]]);
		}
		return best - sum;
	}

	public static void tarjan(int u, int preEdge) {
		dfn[u] = low[u] = ++cntd;
		sta[++top] = u;
		int bestOut = 0;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			if ((e ^ 1) == preEdge) {
				continue;
			}
			int v = to[e];
			if (dfn[v] == 0) {
				tarjan(v, e);
				if (low[v] < dfn[u]) {
					low[u] = Math.min(low[u], low[v]);
				} else if (low[v] > dfn[u]) {
					top--;
					bestOut = Math.max(bestOut, g[v] + 1);
				} else {
					bestOut = Math.max(bestOut, dpOnCycle(u, v));
				}
			} else if (dfn[v] < dfn[u]) {
				low[u] = Math.min(low[u], dfn[v]);
			}
		}
		g[u] = f[u] + bestOut;
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		cntg = 1;
		for (int i = 1, u, v; i <= m; i++) {
			u = in.nextInt();
			v = in.nextInt();
			addEdge(u, v);
			addEdge(v, u);
		}
		tarjan(1, 0);
		out.println(g[1]);
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