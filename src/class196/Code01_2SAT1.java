package class196;

// 2-SAT模版题，java版
// 一共n个变量，变量的编号1~n，每个变量的取值不是0就是1
// 给定m条限制，每条限制的格式 x1 v1 x2 v2，含义如下
// 编号为x1的变量取值为v1 或者 编号为x2的变量取值为v2
// 决定每个变量的值，来满足所有限制，如果无法做到，打印"IMPOSSIBLE"
// 如果可以做到，先打印"POSSIBLE"，然后打印每个变量的值，任何一组解都可以
// 1 <= n、m <= 10^6
// 测试链接 : https://www.luogu.com.cn/problem/P4782
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code01_2SAT1 {

	public static int MAXN = 2000001;
	public static int n, m;

	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXN];
	public static int[] to = new int[MAXN];
	public static int cntg;

	public static int[] dfn = new int[MAXN];
	public static int[] low = new int[MAXN];
	public static int cntd;

	public static int[] sta = new int[MAXN];
	public static int top;

	public static int[] belong = new int[MAXN];
	public static int sccCnt;

	// 迭代版需要的栈，讲解118讲了递归改迭代的技巧
	public static int[][] stack = new int[MAXN][3];
	public static int u, status, e;
	public static int stacksize;

	public static void push(int u, int status, int e) {
		stack[stacksize][0] = u;
		stack[stacksize][1] = status;
		stack[stacksize][2] = e;
		stacksize++;
	}

	public static void pop() {
		stacksize--;
		u = stack[stacksize][0];
		status = stack[stacksize][1];
		e = stack[stacksize][2];
	}

	public static void addEdge(int u, int v) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		head[u] = cntg;
	}

	// 递归版
	public static void tarjan1(int u) {
		dfn[u] = low[u] = ++cntd;
		sta[++top] = u;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (dfn[v] == 0) {
				tarjan1(v);
				low[u] = Math.min(low[u], low[v]);
			} else {
				if (belong[v] == 0) {
					low[u] = Math.min(low[u], dfn[v]);
				}
			}
		}
		if (dfn[u] == low[u]) {
			sccCnt++;
			int pop;
			do {
				pop = sta[top--];
				belong[pop] = sccCnt;
			} while (pop != u);
		}
	}

	// 迭代版
	public static void tarjan2(int node) {
		stacksize = 0;
		push(node, -1, -1);
		int v;
		while (stacksize > 0) {
			pop();
			if (status == -1) {
				dfn[u] = low[u] = ++cntd;
				sta[++top] = u;
				e = head[u];
			} else {
				v = to[e];
				if (status == 0) {
					low[u] = Math.min(low[u], low[v]);
				}
				if (status == 1 && belong[v] == 0) {
					low[u] = Math.min(low[u], dfn[v]);
				}
				e = nxt[e];
			}
			if (e != 0) {
				v = to[e];
				if (dfn[v] == 0) {
					push(u, 0, e);
					push(v, -1, -1);
				} else {
					push(u, 1, e);
				}
			} else {
				if (dfn[u] == low[u]) {
					sccCnt++;
					int pop;
					do {
						pop = sta[top--];
						belong[pop] = sccCnt;
					} while (pop != u);
				}
			}
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		for (int i = 1, x1, v1, x2, v2; i <= m; i++) {
			x1 = in.nextInt();
			v1 = in.nextInt();
			x2 = in.nextInt();
			v2 = in.nextInt();
			if (v1 == 1 && v2 == 1) {
				addEdge(x1 + n, x2);
				addEdge(x2 + n, x1);
			} else if (v1 == 0 && v2 == 1) {
				addEdge(x1, x2);
				addEdge(x2 + n, x1 + n);
			} else if (v1 == 1 && v2 == 0) {
				addEdge(x2, x1);
				addEdge(x1 + n, x2 + n);
			} else {
				addEdge(x1, x2 + n);
				addEdge(x2, x1 + n);
			}
		}
		for (int i = 1; i <= n << 1; i++) {
			if (dfn[i] == 0) {
				// tarjan1(i);
				tarjan2(i);
			}
		}
		boolean check = true;
		for (int i = 1; i <= n; i++) {
			if (belong[i] == belong[i + n]) {
				check = false;
				break;
			}
		}
		if (check) {
			out.println("POSSIBLE");
			for (int i = 1; i <= n; i++) {
				if (belong[i] < belong[i + n]) {
					out.print("1 ");
				} else {
					out.print("0 ");
				}
			}
		} else {
			out.println("IMPOSSIBLE");
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
