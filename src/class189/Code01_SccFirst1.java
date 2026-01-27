package class189;

// 强连通分量模版题1，java版
// 给定一张n个点，m条边的有向图
// 求出所有强连通分量，先打印强连通分量的数量
// 然后打印1号点所在的强连通分量，然后打印2号点所在的强连通分量，以此类推
// 如果当前节点属于之前的强连通分量，那就跳过，直到打印所有强连通分量
// 打印每个强连通分量时，按照节点编号从小到大打印
// 1 <= n <= 10^4
// 1 <= m <= 10^5
// 测试链接 : https://www.luogu.com.cn/problem/B3609
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

public class Code01_SccFirst1 {

	public static int MAXN = 10001;
	public static int MAXM = 100001;
	public static int n, m;

	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXM];
	public static int[] to = new int[MAXM];
	public static int cntg;

	public static int[] dfn = new int[MAXN];
	public static int[] low = new int[MAXN];
	public static int cntd;

	public static boolean[] ins = new boolean[MAXN];
	public static int[] sta = new int[MAXN];
	public static int top;

	public static int[] belong = new int[MAXN];
	public static int[] sccArr = new int[MAXN];
	public static int[] sccl = new int[MAXN];
	public static int[] sccr = new int[MAXN];
	public static int idx;
	public static int sccCnt;

	public static boolean[] sccPrint = new boolean[MAXN];

	public static void addEdge(int u, int v) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		head[u] = cntg;
	}

	// 递归版
	public static void tarjan1(int u) {
		dfn[u] = low[u] = ++cntd;
		sta[++top] = u;
		ins[u] = true;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (dfn[v] == 0) {
				tarjan1(v);
				low[u] = Math.min(low[u], low[v]);
			} else {
				if (ins[v]) {
					low[u] = Math.min(low[u], dfn[v]);
				}
			}
		}
		if (dfn[u] == low[u]) {
			sccCnt++;
			sccl[sccCnt] = idx + 1;
			int pop;
			do {
				pop = sta[top--];
				belong[pop] = sccCnt;
				sccArr[++idx] = pop;
				ins[pop] = false;
			} while (pop != u);
			sccr[sccCnt] = idx;
		}
	}

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

	// 迭代版
	// u表示当前节点
	// e表示u当前处理的边，如果e == 0，说明所有边都处理完了
	// status的具体说明如下
	//     如果status == -1，表示u没有遍历过任何儿子
	//     如果status == 0，表示u遍历到儿子v，然后发现dfn[v] == 0
	//         并且执行完了tarjan(v)，对应递归版for循环中的第一个分支
	//     如果status == 1，表示u遍历到儿子v，然后发现dfn[v] != 0
	//         对应递归版for循环中的第二个分支
	public static void tarjan2(int node) {
		stacksize = 0;
		push(node, -1, -1);
		int v;
		while (stacksize > 0) {
			pop();
			if (status == -1) {
				dfn[u] = low[u] = ++cntd;
				sta[++top] = u;
				ins[u] = true;
				e = head[u];
			} else {
				v = to[e];
				if (status == 0) {
					low[u] = Math.min(low[u], low[v]);
				}
				if (status == 1 && ins[v]) {
					low[u] = Math.min(low[u], dfn[v]);
				}
				e = nxt[e];
			}
			if (e != 0) {
				v = to[e];
				if (dfn[v] == 0) {
					// (当前节点, 状态, 边)先进入栈
					// (儿子节点, 状态, 边)再进入栈
					// 那么儿子节点的tarjan过程会先执行
					// 等到处理当前节点时，low[儿子节点]信息就生成好了
					push(u, 0, e);
					push(v, -1, -1);
				} else {
					push(u, 1, e);
				}
			} else {
				if (dfn[u] == low[u]) {
					sccCnt++;
					sccl[sccCnt] = idx + 1;
					int pop;
					do {
						pop = sta[top--];
						belong[pop] = sccCnt;
						sccArr[++idx] = pop;
						ins[pop] = false;
					} while (pop != u);
					sccr[sccCnt] = idx;
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
		}
		for (int i = 1; i <= n; i++) {
			if (dfn[i] == 0) {
				// tarjan1(i);
				tarjan2(i);
			}
		}
		out.println(sccCnt);
		for (int i = 1; i <= sccCnt; i++) {
			Arrays.sort(sccArr, sccl[i], sccr[i] + 1);
		}
		for (int i = 1; i <= n; i++) {
			int scc = belong[i];
			if (!sccPrint[scc]) {
				sccPrint[scc] = true;
				for (int j = sccl[scc]; j <= sccr[scc]; j++) {
					out.print(sccArr[j] + " ");
				}
				out.println();
			}
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
