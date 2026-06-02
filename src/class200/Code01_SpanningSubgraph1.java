package class200;

// 支撑子图数量，java版
// 测试链接 : https://www.luogu.com.cn/problem/P4129
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.BigInteger;

public class Code01_SpanningSubgraph1 {

	public static int MAXN = 20001;
	public static int MAXM = 1000001;
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

	public static int[] edgeCnt = new int[MAXN];
	public static int cntc;

	public static int[] cycleCnt = new int[MAXN];

	// 本题需要高精度乘法
	public static BigInteger ans;

	// 递归改迭代需要的栈
	public static int[] stau = new int[MAXN];
	public static int[] stap = new int[MAXN];
	public static int[] stas = new int[MAXN];
	public static int[] stae = new int[MAXN];
	public static int u, preEdge, status, e;
	public static int stacksiz;

	public static void push(int u, int preEdge, int status, int e) {
		stau[stacksiz] = u;
		stap[stacksiz] = preEdge;
		stas[stacksiz] = status;
		stae[stacksiz] = e;
		stacksiz++;
	}

	public static void pop() {
		stacksiz--;
		u = stau[stacksiz];
		preEdge = stap[stacksiz];
		status = stas[stacksiz];
		e = stae[stacksiz];
	}

	public static void addEdge(int u, int v) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		head[u] = cntg;
	}

	// 递归版
	public static void tarjan1(int u, int preEdge) {
		dfn[u] = low[u] = ++cntd;
		sta[++top] = u;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			if ((e ^ 1) == preEdge) {
				continue;
			}
			int v = to[e];
			if (dfn[v] == 0) {
				tarjan1(v, e);
				if (low[v] < dfn[u]) {
					low[u] = Math.min(low[u], low[v]);
					cycleCnt[u]++;
				} else if (low[v] > dfn[u]) {
					top--;
				} else {
					cntc++;
					edgeCnt[cntc] = 1;
					int pop;
					do {
						pop = sta[top--];
						edgeCnt[cntc]++;
					} while (pop != v);
				}
			} else if (dfn[v] < dfn[u]) {
				low[u] = Math.min(low[u], dfn[v]);
				cycleCnt[u]++;
			}
		}
	}

	// 迭代版
	public static void tarjan2(int node, int pree) {
		stacksiz = 0;
		push(node, pree, -1, -1);
		int v;
		while (stacksiz > 0) {
			pop();
			if (status == -1) {
				dfn[u] = low[u] = ++cntd;
				sta[++top] = u;
				e = head[u];
			} else {
				v = to[e];
				if (status == 0) {
					if (low[v] < dfn[u]) {
						low[u] = Math.min(low[u], low[v]);
						cycleCnt[u]++;
					} else if (low[v] > dfn[u]) {
						top--;
					} else {
						cntc++;
						edgeCnt[cntc] = 1;
						int pop;
						do {
							pop = sta[top--];
							edgeCnt[cntc]++;
						} while (pop != v);
					}
				} else {
					if (dfn[v] < dfn[u]) {
						low[u] = Math.min(low[u], dfn[v]);
						cycleCnt[u]++;
					}
				}
				e = nxt[e];
			}
			if ((e ^ 1) == preEdge) {
				e = nxt[e];
			}
			if (e != 0) {
				v = to[e];
				if (dfn[v] == 0) {
					push(u, preEdge, 0, e);
					push(v, e, -1, -1);
				} else {
					push(u, preEdge, 1, e);
				}
			}
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		cntg = 1;
		for (int i = 1, k, x, y; i <= m; i++) {
			k = in.nextInt();
			x = in.nextInt();
			for (int j = 2; j <= k; j++) {
				y = in.nextInt();
				addEdge(x, y);
				addEdge(y, x);
				x = y;
			}
		}
		// tarjan1(1, 0);
		tarjan2(1, 0);
		boolean check = true;
		for (int i = 1; i <= n; i++) {
			if (dfn[i] == 0 || cycleCnt[i] >= 2) {
				check = false;
				break;
			}
		}
		if (check) {
			ans = BigInteger.ONE;
			for (int i = 1; i <= cntc; i++) {
				ans = ans.multiply(BigInteger.valueOf(edgeCnt[i] + 1));
			}
			out.println(ans);
		} else {
			out.println(0);
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
