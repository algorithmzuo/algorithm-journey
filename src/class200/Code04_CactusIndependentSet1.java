package class200;

// 仙人掌最大独立集，java版
// 给定n个点、m条边的无向连通图，输入保证图是仙人掌
// 你可以选择一些点，但是图中任意相邻两点不能同时选择
// 计算能得到的最大点权累加和
// 第一个测试链接，给定每个点的点权
// 第二个测试链接，认为每个点的点权是1
// 1 <= n <= 10^5
// 1 <= m <= 2 * 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P4410
// 测试链接 : https://www.luogu.com.cn/problem/P10779
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code04_CactusIndependentSet1 {

	public static int MAXN = 100001;
	public static int MAXM = 200001;
	public static int INF = 1000000000;
	public static int n, m;
	public static int[] arr = new int[MAXN];

	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXM << 1];
	public static int[] to = new int[MAXM << 1];
	public static int cntg;

	public static int[] dfn = new int[MAXN];
	public static int[] low = new int[MAXN];
	public static int cntd;
	public static int[] sta = new int[MAXN];
	public static int top;

	// 收集环上的节点，不包含环顶
	public static int[] cycle = new int[MAXN];

	// dp[u][0] : 以u为头的子仙人掌中，不选u的情况下，获得的最大收益
	// dp[u][1] : 以u为头的子仙人掌中，选u的情况下，获得的最大收益
	public static int[][] dp = new int[MAXN][2];

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

	public static void dpOnCycle(int u, int v) {
		int siz = 0;
		int pop;
		do {
			pop = sta[top--];
			cycle[++siz] = pop;
		} while (pop != v);
		// 不选u
		int pre0 = dp[cycle[1]][0];
		int pre1 = dp[cycle[1]][1];
		int cur0, cur1;
		for (int i = 2; i <= siz; i++) {
			int x = cycle[i];
			cur0 = Math.max(pre0, pre1) + dp[x][0];
			cur1 = pre0 + dp[x][1];
			pre0 = cur0;
			pre1 = cur1;
		}
		dp[u][0] += Math.max(pre0, pre1);
		// 选u
		pre0 = dp[cycle[1]][0];
		pre1 = -INF;
		for (int i = 2; i <= siz; i++) {
			int x = cycle[i];
			cur0 = Math.max(pre0, pre1) + dp[x][0];
			cur1 = pre0 + dp[x][1];
			pre0 = cur0;
			pre1 = cur1;
		}
		dp[u][1] += pre0;
	}

	// 递归版
	public static void tarjan1(int u, int preEdge) {
		dfn[u] = low[u] = ++cntd;
		sta[++top] = u;
		dp[u][0] = 0;
		dp[u][1] = arr[u];
		for (int e = head[u]; e > 0; e = nxt[e]) {
			if ((e ^ 1) == preEdge) {
				continue;
			}
			int v = to[e];
			if (dfn[v] == 0) {
				tarjan1(v, e);
				if (low[v] < dfn[u]) {
					low[u] = Math.min(low[u], low[v]);
				} else if (low[v] > dfn[u]) {
					dp[u][0] += Math.max(dp[v][0], dp[v][1]);
					dp[u][1] += dp[v][0];
					top--;
				} else {
					dpOnCycle(u, v);
				}
			} else if (dfn[v] < dfn[u]) {
				low[u] = Math.min(low[u], dfn[v]);
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
				dp[u][0] = 0;
				dp[u][1] = arr[u];
				e = head[u];
			} else {
				v = to[e];
				if (status == 0) {
					if (low[v] < dfn[u]) {
						low[u] = Math.min(low[u], low[v]);
					} else if (low[v] > dfn[u]) {
						dp[u][0] += Math.max(dp[v][0], dp[v][1]);
						dp[u][1] += dp[v][0];
						top--;
					} else {
						dpOnCycle(u, v);
					}
				} else {
					if (dfn[v] < dfn[u]) {
						low[u] = Math.min(low[u], dfn[v]);
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
		for (int i = 1, u, v; i <= m; i++) {
			u = in.nextInt();
			v = in.nextInt();
			addEdge(u, v);
			addEdge(v, u);
		}

		// 洛谷P4410，使用如下的点权设置
		for (int i = 1; i <= n; i++) {
			arr[i] = in.nextInt();
		}

		// 洛谷P10779，使用如下的点权设置
//		for (int i = 1; i <= n; i++) {
//			arr[i] = 1;
//		}

		tarjan2(1, 0);
		out.println(Math.max(dp[1][0], dp[1][1]));
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
