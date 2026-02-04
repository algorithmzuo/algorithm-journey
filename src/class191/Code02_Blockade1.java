package class191;

// 城市阻断，java版
// 测试链接 : https://www.luogu.com.cn/problem/P3469
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code02_Blockade1 {

	public static int MAXN = 100001;
	public static int MAXM = 500001;
	public static int n, m;

	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXM << 1];
	public static int[] to = new int[MAXM << 1];
	public static int cntg;

	public static int[] dfn = new int[MAXN];
	public static int[] low = new int[MAXN];
	public static int cntd;

	public static boolean[] cutVertex = new boolean[MAXN];

	public static int[] siz = new int[MAXN];
	public static long[] ans = new long[MAXN];

	// 迭代版需要的栈，讲解118讲了递归改迭代的技巧
	public static int[][] stack = new int[MAXN][6];
	public static int u, root, son, sum, status, e;
	public static int stacksize;

	public static void push(int u, int root, int son, int sum, int status, int e) {
		stack[stacksize][0] = u;
		stack[stacksize][1] = root;
		stack[stacksize][2] = son;
		stack[stacksize][3] = sum;
		stack[stacksize][4] = status;
		stack[stacksize][5] = e;
		stacksize++;
	}

	public static void pop() {
		stacksize--;
		u = stack[stacksize][0];
		root = stack[stacksize][1];
		son = stack[stacksize][2];
		sum = stack[stacksize][3];
		status = stack[stacksize][4];
		e = stack[stacksize][5];
	}

	public static void addEdge(int u, int v) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		head[u] = cntg;
	}

	// 递归版
	public static void tarjan1(int u, boolean root) {
		dfn[u] = low[u] = ++cntd;
		int son = 0;
		siz[u] = 1;
		int sum = 0;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (dfn[v] == 0) {
				son++;
				tarjan1(v, false);
				low[u] = Math.min(low[u], low[v]);
				if (!root && low[v] >= dfn[u]) {
					cutVertex[u] = true;
				}
				siz[u] += siz[v];
				if (low[v] >= dfn[u]) {
					sum += siz[v];
					ans[u] += (long) siz[v] * (n - siz[v]);
				}
			} else {
				low[u] = Math.min(low[u], dfn[v]);
			}
		}
		if (root && son >= 2) {
			cutVertex[u] = true;
		}
		if (cutVertex[u]) {
			ans[u] += (long) (n - sum - 1) * (sum + 1) + (n - 1);
		} else {
			ans[u] = 2 * (n - 1);
		}
	}

	// 迭代版
	public static void tarjan2(int node, boolean rt) {
		stacksize = 0;
		push(node, rt ? 1 : 0, 0, 0, -1, -1);
		int v;
		while (stacksize > 0) {
			pop();
			if (status == -1) {
				dfn[u] = low[u] = ++cntd;
				siz[u] = 1;
				e = head[u];
			} else {
				v = to[e];
				if (status == 0) {
					low[u] = Math.min(low[u], low[v]);
					if (root == 0 && low[v] >= dfn[u]) {
						cutVertex[u] = true;
					}
					siz[u] += siz[v];
					if (low[v] >= dfn[u]) {
						sum += siz[v];
						ans[u] += (long) siz[v] * (n - siz[v]);
					}
				} else {
					low[u] = Math.min(low[u], dfn[v]);
				}
				e = nxt[e];
			}
			if (e != 0) {
				v = to[e];
				if (dfn[v] == 0) {
					son++;
					push(u, root, son, sum, 0, e);
					push(v, 0, 0, 0, -1, -1);
				} else {
					push(u, root, son, sum, 1, e);
				}
			} else {
				if (root == 1 && son >= 2) {
					cutVertex[u] = true;
				}
				if (cutVertex[u]) {
					ans[u] += (long) (n - sum - 1) * (sum + 1) + (n - 1);
				} else {
					ans[u] = 2 * (n - 1);
				}
			}
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		for (int i = 1, u, v; i <= m; i++) {
			u = in.nextInt();
			v = in.nextInt();
			addEdge(u, v);
			addEdge(v, u);
		}
		// tarjan1(1, true);
		tarjan2(1, true);
		for (int i = 1; i <= n; i++) {
			out.println(ans[i]);
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
