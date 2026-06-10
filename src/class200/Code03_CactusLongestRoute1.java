package class200;

// 仙人掌最长路线，java版
// 给定n个点、m条边的无向连通图，输入保证图是仙人掌
// 路线可以从任何城市开始，但必须在1号点结束
// 路线可以重复经过同一个点，但不能重复经过同一条边
// 计算最长路线的边数
// 1 <= n <= 10^4
// 1 <= m <= 2 * 10^4
// 测试链接 : https://www.luogu.com.cn/problem/P6335
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code03_CactusLongestRoute1 {

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

	// f[u] : 在u向下的结构中，从u出发并最终回到u，能走过的最大边数
	public static int[] f = new int[MAXN];

	// g[u] : 在u向下的结构中，从u出发，终点任意，能走过的最大边数
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
		// best表示，这个环上任选一个点作为最后出去的位置
		// 从u沿两侧之一走到它，能够得到的不回到u的最大边数
		int best = 0;
		int sum = 1;
		for (int i = 1; i <= siz; i++) {
			best = Math.max(best, sum + g[cycle[i]]);
			sum += f[cycle[i]] + 1;
		}
		// u可以下方有多个环，所以新增此时的sum
		f[u] += sum;
		int otherSide = sum;
		for (int i = 1; i <= siz; i++) {
			otherSide -= f[cycle[i]] + 1;
			best = Math.max(best, otherSide + g[cycle[i]]);
		}
		return best - sum;
	}

	public static void tarjan(int u, int preEdge) {
		dfn[u] = low[u] = ++cntd;
		sta[++top] = u;
		// bestOut用来计算 g[u] = f[u] + bestOut
		// f[u]表示从u出发并最终回到u的最大边数
		// bestOut表示额外选择一条最后不回到u的出口路线，能带来的最大增量
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