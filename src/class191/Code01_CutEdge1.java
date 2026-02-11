package class191;

// 割边模版题1，java版
// 原图即使有重边，答案依然正确
// 测试链接 : https://www.luogu.com.cn/problem/U582665
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code01_CutEdge1 {

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

	public static boolean[] cutEdge = new boolean[MAXM];

	// 迭代版需要的栈，讲解118讲了递归改迭代的技巧
	public static int[][] stack = new int[MAXN][4];
	public static int u, preEdge, status, e;
	public static int stacksize;

	public static void push(int u, int preEdge, int status, int e) {
		stack[stacksize][0] = u;
		stack[stacksize][1] = preEdge;
		stack[stacksize][2] = status;
		stack[stacksize][3] = e;
		stacksize++;
	}

	public static void pop() {
		stacksize--;
		u = stack[stacksize][0];
		preEdge = stack[stacksize][1];
		status = stack[stacksize][2];
		e = stack[stacksize][3];
	}

	public static void addEdge(int u, int v) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		head[u] = cntg;
	}

	// 递归版
	public static void tarjan1(int u, int preEdge) {
		dfn[u] = low[u] = ++cntd;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			if ((e ^ 1) == preEdge) { // 来边
				continue;
			}
			int v = to[e];
			if (dfn[v] == 0) { // 树边
				tarjan1(v, e);
				low[u] = Math.min(low[u], low[v]);
				if (low[v] > dfn[u]) {
					cutEdge[e >> 1] = true;
				}
			} else { // 回边或弃边
				low[u] = Math.min(low[u], dfn[v]);
			}
		}
	}

	// 迭代版
	// u表示当前节点，preEdge表示来边
	// e表示u当前处理的边
	//     如果(e ^ 1) == preEdge，跳过当前边，对应递归版中的第一个if
	//     如果e == 0，说明所有边都处理完了
	// status的具体说明如下
	//     如果status == -1，表示u没有遍历过任何儿子
	//     如果status == 0，表示u遍历到儿子v，然后发现dfn[v] == 0
	//         并且执行完了tarjan(v, e)，对应递归版for循环的第二个if
	//     如果status == 1，表示u遍历到儿子v，然后发现dfn[v] != 0
	//         对应递归版for循环中的else分支
	public static void tarjan2(int node, int pree) {
		stacksize = 0;
		push(node, pree, -1, -1);
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
					if (low[v] > dfn[u]) {
						cutEdge[e >> 1] = true;
					}
				} else {
					low[u] = Math.min(low[u], dfn[v]);
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
		cntg = 1;
		n = in.nextInt();
		m = in.nextInt();
		for (int i = 1, u, v; i <= m; i++) {
			u = in.nextInt();
			v = in.nextInt();
			addEdge(u, v);
			addEdge(v, u);
		}
		for (int i = 1; i <= n; i++) {
			if (dfn[i] == 0) {
				// tarjan1(i, 0);
				tarjan2(i, 0);
			}
		}
		int ansCnt = 0;
		for (int i = 1; i <= m; i++) {
			if (cutEdge[i]) {
				ansCnt++;
			}
		}
		out.println(ansCnt);
		for (int i = 1; i <= m; i++) {
			if (cutEdge[i]) {
				out.print(i + " ");
			}
		}
		out.println();
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
