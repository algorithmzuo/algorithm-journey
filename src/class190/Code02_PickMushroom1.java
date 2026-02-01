package class190;

// 采蘑菇，java版
// 给定一张n个点，m条边的有向图，每条边有初始收益、恢复系数两种边权
// 初始收益为非负整数，恢复系数范围[0, 0.8]，并且最多有一位小数
// 比如，如果重复走过一条边，该边的初始收益为10，恢复系数为0.6
// 那么依次获得的收益为，10、6、3、1、0，随后重复经过就没有收益了
// 给定起点s，必须从s出发，找到一条路径，打印收益累加和的最大值
// 1 <= n <= 8 * 10^4
// 1 <= m <= 2 * 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P2656
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code02_PickMushroom1 {

	public static int MAXN = 80001;
	public static int MAXM = 200001;
	public static int INF = 1000000001;
	public static int n, m, s;

	public static int[] a = new int[MAXM];
	public static int[] b = new int[MAXM];
	public static int[] init = new int[MAXM];
	public static int[] recover = new int[MAXM];

	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXM];
	public static int[] to = new int[MAXM];
	public static int[] weight = new int[MAXM];
	public static int cntg;

	public static int[] dfn = new int[MAXN];
	public static int[] low = new int[MAXN];
	public static int cntd;

	public static int[] sta = new int[MAXN];
	public static int top;

	public static int[] belong = new int[MAXN];
	public static int sccCnt;

	public static int[] sum = new int[MAXN];
	public static int[] dp = new int[MAXN];

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

	public static void addEdge(int u, int v, int w) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		weight[cntg] = w;
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

	public static void condense() {
		cntg = 0;
		for (int i = 1; i <= sccCnt; i++) {
			head[i] = 0;
		}
		for (int i = 1; i <= m; i++) {
			int scc1 = belong[a[i]];
			int scc2 = belong[b[i]];
			if (scc1 > 0 && scc2 > 0) {
				int val = init[i];
				int rec = recover[i];
				if (scc1 == scc2) {
					while (val > 0) {
						sum[scc1] += val;
						val = val * rec / 10;
					}
				} else {
					addEdge(scc1, scc2, val);
				}
			}
		}
	}

	public static int dpOnDAG() {
		for (int u = 1; u <= sccCnt; u++) {
			dp[u] = -INF;
		}
		dp[belong[s]] = sum[belong[s]];
		for (int u = sccCnt; u > 0; u--) {
			for (int e = head[u]; e > 0; e = nxt[e]) {
				int v = to[e];
				int w = weight[e];
				dp[v] = Math.max(dp[v], dp[u] + w + sum[v]);
			}
		}
		int ans = 0;
		for (int u = 1; u <= sccCnt; u++) {
			ans = Math.max(ans, dp[u]);
		}
		return ans;
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		for (int i = 1; i <= m; i++) {
			a[i] = in.nextInt();
			b[i] = in.nextInt();
			init[i] = in.nextInt();
			double rec = in.nextDouble();
			recover[i] = (int) (rec * 10);
			addEdge(a[i], b[i], 0);
		}
		s = in.nextInt();
		// tarjan1(s);
		tarjan2(s);
		condense();
		int ans = dpOnDAG();
		out.println(ans);
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

		double nextDouble() throws IOException {
			int c;
			do {
				c = readByte();
			} while (c <= ' ' && c != -1);
			boolean neg = false;
			if (c == '-') {
				neg = true;
				c = readByte();
			}
			long intPart = 0;
			while (c > ' ' && c != -1 && c != '.') {
				intPart = intPart * 10 + (c - '0');
				c = readByte();
			}
			double val = (double) intPart;
			if (c == '.') {
				c = readByte();
				double base = 0.1;
				while (c > ' ' && c != -1) {
					val += (c - '0') * base;
					base *= 0.1;
					c = readByte();
				}
			}
			return neg ? -val : val;
		}
	}

}
