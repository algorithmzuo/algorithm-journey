package class191;

// 关键网络线路，java版
// 原图即使有重边，答案依然正确
// 测试链接 : https://www.luogu.com.cn/problem/P7687
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code03_CriticalNetworkLines1 {

	public static int MAXN = 100001;
	public static int MAXM = 1000001;
	public static int n, m, l, k;
	public static int[] acnt = new int[MAXN];
	public static int[] bcnt = new int[MAXN];

	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXM << 1];
	public static int[] to = new int[MAXM << 1];
	public static int cntg;

	public static int[] dfn = new int[MAXN];
	public static int[] low = new int[MAXN];
	public static int cntd;

	public static int[] a = new int[MAXN];
	public static int[] b = new int[MAXN];
	public static int cntAns;

	// 迭代版需要的栈，讲解118讲了递归改迭代的技巧
	public static int[] stau = new int[MAXN];
	public static int[] stap = new int[MAXN];
	public static int[] stas = new int[MAXN];
	public static int[] stae = new int[MAXN];
	public static int u, preEdge, status, e;
	public static int stasiz;

	public static void push(int u, int preEdge, int status, int e) {
		stau[stasiz] = u;
		stap[stasiz] = preEdge;
		stas[stasiz] = status;
		stae[stasiz] = e;
		stasiz++;
	}

	public static void pop() {
		stasiz--;
		u = stau[stasiz];
		preEdge = stap[stasiz];
		status = stas[stasiz];
		e = stae[stasiz];
	}

	public static void addEdge(int u, int v) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		head[u] = cntg;
	}

	// 递归版
	public static void tarjan1(int u, int preEdge) {
		low[u] = dfn[u] = ++cntd;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (dfn[v] == 0) {
				tarjan1(v, e);
				low[u] = Math.min(low[u], low[v]);
				if (low[v] > dfn[u]) {
					if (acnt[v] == 0 || bcnt[v] == 0 || acnt[v] == l || bcnt[v] == k) {
						cntAns++;
						a[cntAns] = v;
						b[cntAns] = u;
					}
				}
				acnt[u] += acnt[v];
				bcnt[u] += bcnt[v];
			} else {
				if ((e ^ 1) != preEdge) {
					low[u] = Math.min(low[u], dfn[v]);
				}
			}
		}
	}

	// 迭代版
	public static void tarjan2(int node, int pree) {
		stasiz = 0;
		push(node, pree, -1, -1);
		int v;
		while (stasiz > 0) {
			pop();
			if (status == -1) {
				dfn[u] = low[u] = ++cntd;
				e = head[u];
			} else {
				v = to[e];
				if (status == 0) {
					low[u] = Math.min(low[u], low[v]);
					if (low[v] > dfn[u]) {
						if (acnt[v] == 0 || bcnt[v] == 0 || acnt[v] == l || bcnt[v] == k) {
							cntAns++;
							a[cntAns] = v;
							b[cntAns] = u;
						}
					}
					acnt[u] += acnt[v];
					bcnt[u] += bcnt[v];
				} else {
					if ((e ^ 1) != preEdge) {
						low[u] = Math.min(low[u], dfn[v]);
					}
				}
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
		cntg = 1;
		n = in.nextInt();
		m = in.nextInt();
		l = in.nextInt();
		k = in.nextInt();
		for (int i = 1, x; i <= l; i++) {
			x = in.nextInt();
			acnt[x] = 1;
		}
		for (int i = 1, x; i <= k; i++) {
			x = in.nextInt();
			bcnt[x] = 1;
		}
		for (int i = 1, u, v; i <= m; i++) {
			u = in.nextInt();
			v = in.nextInt();
			addEdge(u, v);
			addEdge(v, u);
		}
		// tarjan1(1, 0);
		tarjan2(1, 0);
		out.println(cntAns);
		for (int i = 1; i <= cntAns; i++) {
			out.println(a[i] + " " + b[i]);
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
