package class193;

// 点双连通分量模版题1，java版
// 测试链接 : https://www.luogu.com.cn/problem/P8435
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code05_VBCC {

	public static int MAXN = 500001;
	public static int MAXM = 2000001;
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

	public static int[] vbccSiz = new int[MAXN];
	public static int[] vbccArr = new int[MAXN << 1];
	public static int[] vbccl = new int[MAXN];
	public static int[] vbccr = new int[MAXN];
	public static int idx;
	public static int vbccCnt;

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
				if (low[v] == dfn[u]) {
					vbccCnt++;
					vbccSiz[vbccCnt] = 1;
					vbccArr[++idx] = u;
					vbccl[vbccCnt] = idx;
					int pop;
					do {
						pop = sta[top--];
						vbccSiz[vbccCnt]++;
						vbccArr[++idx] = pop;
					} while (pop != v);
					vbccr[vbccCnt] = idx;
				}
			} else {
				low[u] = Math.min(low[u], dfn[v]);
			}
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
					if (low[v] == dfn[u]) {
						vbccCnt++;
						vbccSiz[vbccCnt] = 1;
						vbccArr[++idx] = u;
						vbccl[vbccCnt] = idx;
						int pop;
						do {
							pop = sta[top--];
							vbccSiz[vbccCnt]++;
							vbccArr[++idx] = pop;
						} while (pop != v);
						vbccr[vbccCnt] = idx;
					}
				} else {
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
			if (u != v) {
				addEdge(u, v);
				addEdge(v, u);
			}
		}
		for (int i = 1; i <= n; i++) {
			if (dfn[i] == 0) {
				if (head[i] == 0) {
					vbccCnt++;
					vbccSiz[vbccCnt] = 1;
					vbccArr[++idx] = i;
					vbccl[vbccCnt] = vbccr[vbccCnt] = idx;
				} else {
					// tarjan1(i);
					tarjan2(i);
				}
			}
		}
		out.println(vbccCnt);
		for (int i = 1; i <= vbccCnt; i++) {
			out.println(vbccSiz[i]);
			for (int j = vbccl[i]; j <= vbccr[i]; j++) {
				out.print(vbccArr[j] + " ");
			}
			out.println();
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
