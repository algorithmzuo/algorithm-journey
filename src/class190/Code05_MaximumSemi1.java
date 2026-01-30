package class190;

// 最大半连通子图，java版
// 有向图中节点u和v，只要其中一点能到达另一点，就说两点是半连通的
// 如果一个有向图，任意两点都是半连通的，这样的有向图就是半连通图
// 有向图中的子图，既是半连通图，又有节点数量最多，就是最大半连通子图
// 给定一张n个点，m条边的有向图，打印最大半连通子图的大小
// 可能存在多个最大半连通子图，打印这个数量，数量对给定的数字x取余
// 1 <= n <= 10^5
// 1 <= m <= 10^6
// 测试链接 : https://www.luogu.com.cn/problem/P2272
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

public class Code05_MaximumSemi1 {

	public static int MAXN = 100001;
	public static int MAXM = 1000001;
	public static int n, m, x;
	public static int[] a = new int[MAXM];
	public static int[] b = new int[MAXM];

	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXM];
	public static int[] to = new int[MAXM];
	public static int cntg;

	public static int[] dfn = new int[MAXN];
	public static int[] low = new int[MAXN];
	public static int cntd;

	public static int[] sta = new int[MAXN];
	public static int top;

	public static int[] belong = new int[MAXN];
	public static int[] sccSiz = new int[MAXN];
	public static int sccCnt;

	public static long[] edgeArr = new long[MAXM];
	public static int cnte;

	public static int[] indegree = new int[MAXN];
	public static int[] semiSiz = new int[MAXN];
	public static int[] semiCnt = new int[MAXN];

	public static int ans1, ans2;

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

	// 递归版，java会爆栈，C++可以通过
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
			sccSiz[sccCnt] = 0;
			int pop;
			do {
				pop = sta[top--];
				belong[pop] = sccCnt;
				sccSiz[sccCnt]++;
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
					sccSiz[sccCnt] = 0;
					int pop;
					do {
						pop = sta[top--];
						belong[pop] = sccCnt;
						sccSiz[sccCnt]++;
					} while (pop != u);
				}
			}
		}
	}

	public static void condense() {
		cntg = 0;
		for (int i = 1; i <= sccCnt; i++) {
			head[i] = 0;
		}
		for (int i = 1; i <= m; i++) {
			int scc1 = belong[a[i]];
			int scc2 = belong[b[i]];
			if (scc1 != scc2) {
				edgeArr[++cnte] = ((long) scc1 << 32) | (scc2 & 0xffffffffL);
			}
		}
		Arrays.sort(edgeArr, 1, cnte + 1);
		long pre = 0, cur;
		for (int i = 1; i <= cnte; i++) {
			cur = edgeArr[i];
			if (cur != pre) {
				int scc1 = (int) (cur >>> 32);
				int scc2 = (int) (cur & 0xffffffffL);
				indegree[scc2]++;
				addEdge(scc1, scc2);
				pre = cur;
			}
		}
	}

	public static void dpOnDAG() {
		for (int u = sccCnt; u > 0; u--) {
			if (indegree[u] == 0) {
				semiSiz[u] = sccSiz[u];
				semiCnt[u] = 1;
			}
			for (int e = head[u]; e > 0; e = nxt[e]) {
				int v = to[e];
				if (semiSiz[v] < semiSiz[u] + sccSiz[v]) {
					semiSiz[v] = semiSiz[u] + sccSiz[v];
					semiCnt[v] = semiCnt[u];
				} else if (semiSiz[v] == semiSiz[u] + sccSiz[v]) {
					semiCnt[v] = (semiCnt[v] + semiCnt[u]) % x;
				}
			}
		}
		ans1 = ans2 = 0;
		for (int i = 1; i <= sccCnt; i++) {
			if (semiSiz[i] > ans1) {
				ans1 = semiSiz[i];
				ans2 = semiCnt[i];
			} else if (semiSiz[i] == ans1) {
				ans2 = (ans2 + semiCnt[i]) % x;
			}
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		x = in.nextInt();
		for (int i = 1; i <= m; i++) {
			a[i] = in.nextInt();
			b[i] = in.nextInt();
			addEdge(a[i], b[i]);
		}
		for (int i = 1; i <= n; i++) {
			if (dfn[i] == 0) {
				// tarjan1(i);
				tarjan2(i);
			}
		}
		condense();
		dpOnDAG();
		out.println(ans1);
		out.println(ans2);
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
