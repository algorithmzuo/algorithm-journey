package class199;

// 最小化直径，java版
// 可以删掉环上的一条边，使剩下树的直径最小
// 测试链接 : https://codeforces.com/problemset/problem/835/F
// 测试链接 : https://www.luogu.com.cn/problem/P1399
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code06_MinimizeDiameter1 {

	public static int MAXN = 200001;
	public static int n;

	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXN << 1];
	public static int[] to = new int[MAXN << 1];
	public static int[] weight = new int[MAXN << 1];
	public static int cntg;

	public static int[] dfn = new int[MAXN];
	public static int cntd;

	public static int[] fromNode = new int[MAXN];
	public static int[] fromWeight = new int[MAXN];
	public static boolean[] cycle = new boolean[MAXN];
	public static int[] arr = new int[MAXN];
	public static int[] val = new int[MAXN];
	public static int cnta;

	public static long[] dist = new long[MAXN];
	public static long diameter;

	public static long[] height = new long[MAXN];
	public static long[] preMax = new long[MAXN];
	public static long[] preDiameter = new long[MAXN];
	public static long[] sufMax = new long[MAXN + 1];
	public static long[] sufDiameter = new long[MAXN + 1];

	// 递归改迭代需要的栈
	public static int[] stau = new int[MAXN];
	public static int[] stap = new int[MAXN];
	public static int[] stas = new int[MAXN];
	public static int[] stae = new int[MAXN];
	public static int stacksize, u, preEdge, status, e;

	public static void push(int u, int preEdge, int status, int e) {
		stau[stacksize] = u;
		stap[stacksize] = preEdge;
		stas[stacksize] = status;
		stae[stacksize] = e;
		stacksize++;
	}

	public static void pop() {
		--stacksize;
		u = stau[stacksize];
		preEdge = stap[stacksize];
		status = stas[stacksize];
		e = stae[stacksize];
	}

	public static void addEdge(int u, int v, int w) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		weight[cntg] = w;
		head[u] = cntg;
	}

	// 递归版
	public static void dfs1(int u, int preEdge) {
		dfn[u] = ++cntd;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (e != (preEdge ^ 1)) {
				if (dfn[v] == 0) {
					fromNode[v] = u;
					fromWeight[v] = weight[e];
					dfs1(v, e);
				} else if (dfn[u] < dfn[v]) {
					cycle[u] = true;
					arr[++cnta] = u;
					val[cnta] = weight[e];
					for (int i = v; i != u; i = fromNode[i]) {
						cycle[i] = true;
						arr[++cnta] = i;
						val[cnta] = fromWeight[i];
					}
				}
			}
		}
	}

	// 迭代版
	public static void dfs2(int cur, int edge) {
		stacksize = 0;
		push(cur, edge, -1, -1);
		int v;
		while (stacksize > 0) {
			pop();
			if (status == -1) {
				dfn[u] = ++cntd;
				e = head[u];
			} else {
				v = to[e];
				if (status == 1 && dfn[u] < dfn[v]) {
					cycle[u] = true;
					arr[++cnta] = u;
					val[cnta] = weight[e];
					for (int i = v; i != u; i = fromNode[i]) {
						cycle[i] = true;
						arr[++cnta] = i;
						val[cnta] = fromWeight[i];
					}
				}
				e = nxt[e];
			}
			if (e == (preEdge ^ 1)) {
				e = nxt[e];
			}
			if (e != 0) {
				v = to[e];
				if (dfn[v] == 0) {
					push(u, preEdge, 0, e);
					fromNode[v] = u;
					fromWeight[v] = weight[e];
					push(v, e, -1, -1);
				} else {
					push(u, preEdge, 1, e);
				}
			}
		}
	}

	public static void dpOnTree(int u, int fa) {
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			int w = weight[e];
			if (v != fa && !cycle[v]) {
				dpOnTree(v, u);
				diameter = Math.max(diameter, dist[u] + dist[v] + w);
				dist[u] = Math.max(dist[u], dist[v] + w);
			}
		}
	}

	public static long dpOnCycle() {
		long sum = 0;
		long best = 0;
		for (int i = 1; i <= cnta; i++) {
			preMax[i] = Math.max(preMax[i - 1], height[i] + sum);
			preDiameter[i] = Math.max(preDiameter[i - 1], sum + height[i] + best);
			best = Math.max(best, height[i] - sum);
			sum += val[i];
		}
		sum = val[cnta];
		best = -val[cnta];
		for (int i = cnta; i >= 1; i--) {
			sufMax[i] = Math.max(sufMax[i + 1], height[i] + sum);
			sufDiameter[i] = Math.max(sufDiameter[i + 1], sum + height[i] + best);
			best = Math.max(best, height[i] - sum);
			sum += val[i - 1];
		}
		long ans = preDiameter[cnta];
		for (int i = 1; i < cnta; i++) {
			ans = Math.min(ans, Math.max(preMax[i] + sufMax[i + 1], Math.max(preDiameter[i], sufDiameter[i + 1])));
		}
		return ans;
	}

	public static long compute() {
		// dfs1(1, 0);
		dfs2(1, 0);
		long ans1 = 0;
		for (int i = 1; i <= cnta; i++) {
			diameter = 0;
			dpOnTree(arr[i], 0);
			ans1 = Math.max(ans1, diameter);
			height[i] = dist[arr[i]];
		}
		long ans2 = dpOnCycle();
		return Math.max(ans1, ans2);
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		cntg = 1;
		for (int i = 1, u, v, w; i <= n; i++) {
			u = in.nextInt();
			v = in.nextInt();
			w = in.nextInt();
			addEdge(u, v, w);
			addEdge(v, u, w);
		}
		long ans = compute();

		// 王国道路，CF835F，使用如下打印
		out.println(ans);

		// 快餐店，洛谷P1399，使用如下打印
		// out.printf("%.1f\n", (double) ans / 2.0);

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
