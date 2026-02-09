package class193;

// 嗅探器，java版
// 测试链接 : https://www.luogu.com.cn/problem/P5058
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code02_Sniffer1 {

	public static int MAXN = 200001;
	public static int MAXM = 500001;
	public static int n, a, b;

	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXM << 1];
	public static int[] to = new int[MAXM << 1];
	public static int cntg;

	public static int[] dfn = new int[MAXN];
	public static int[] low = new int[MAXN];
	public static int cntd;

	public static boolean[] cutVertex = new boolean[MAXN];

	// 迭代版需要的栈，讲解118讲了递归改迭代的技巧
	public static int[][] stack = new int[MAXN][4];
	public static int u, root, status, e;
	public static int stacksize;

	public static void push(int u, int root, int status, int e) {
		stack[stacksize][0] = u;
		stack[stacksize][1] = root;
		stack[stacksize][2] = status;
		stack[stacksize][3] = e;
		stacksize++;
	}

	public static void pop() {
		stacksize--;
		u = stack[stacksize][0];
		root = stack[stacksize][1];
		status = stack[stacksize][2];
		e = stack[stacksize][3];
	}

	public static void addEdge(int u, int v) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		head[u] = cntg;
	}

	// 递归版
	public static void tarjan1(int u, boolean root) {
		dfn[u] = low[u] = ++cntd;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (dfn[v] == 0) {
				tarjan1(v, false);
				low[u] = Math.min(low[u], low[v]);
				if (low[v] >= dfn[u]) {
					if (!root && dfn[b] >= dfn[v]) {
						cutVertex[u] = true;
					}
				}
			} else {
				low[u] = Math.min(low[u], dfn[v]);
			}
		}
	}

	// 迭代版
	public static void tarjan2(int node, boolean rt) {
		stacksize = 0;
		push(node, rt ? 1 : 0, -1, -1);
		int v;
		while (stacksize > 0) {
			pop();
			if (status == -1) {
				dfn[u] = low[u] = ++cntd;
				e = head[u];
			} else {
				v = to[e];
				if (status == 0) {
					low[u] = Math.min(low[u], low[v]);
					if (low[v] >= dfn[u]) {
						if (root == 0 && dfn[b] >= dfn[v]) {
							cutVertex[u] = true;
						}
					}
				} else {
					low[u] = Math.min(low[u], dfn[v]);
				}
				e = nxt[e];
			}
			if (e != 0) {
				v = to[e];
				if (dfn[v] == 0) {
					push(u, root, 0, e);
					push(v, 0, -1, -1);
				} else {
					push(u, root, 1, e);
				}
			}
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		a = in.nextInt();
		b = in.nextInt();
		while (a != 0 || b != 0) {
			addEdge(a, b);
			addEdge(b, a);
			a = in.nextInt();
			b = in.nextInt();
		}
		a = in.nextInt();
		b = in.nextInt();
		// tarjan1(a, true);
		tarjan2(a, true);
		boolean check = false;
		for (int i = 1; i <= n; i++) {
			if (cutVertex[i]) {
				out.println(i);
				check = true;
				break;
			}
		}
		if (!check) {
			out.println("No solution");
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
