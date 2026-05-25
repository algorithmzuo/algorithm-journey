package class199;

// 树直径最小化，java版
// 图中有n个点、n条无向边，每条边有边权，图是一棵基环树
// 可以任意删掉环上的一条边，让图变成树，希望让树的直径最小
// 第一个测试链接，计算树的直径最小值
// 第二个测试链接，等价于计算树的直径最小值 / 2，结果保留一位小数
// 1 <= n <= 2 * 10^5
// 1 <= 边权 <= 10^9
// 测试链接 : https://codeforces.com/problemset/problem/835/F
// 测试链接 : https://www.luogu.com.cn/problem/P1399
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code05_DiameterMinimize1 {

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

	public static long[] sufMax = new long[MAXN];
	public static long[] sufDiameter = new long[MAXN];

	// 递归改迭代需要的栈
	public static int[] stau = new int[MAXN];
	public static int[] stas = new int[MAXN];
	public static int[] stae = new int[MAXN];
	public static int stacksize, u, status, e;

	public static void push(int u, int status, int e) {
		stau[stacksize] = u;
		stas[stacksize] = status;
		stae[stacksize] = e;
		stacksize++;
	}

	public static void pop() {
		--stacksize;
		u = stau[stacksize];
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
	public static void dfs1(int u) {
		dfn[u] = ++cntd;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (dfn[v] == 0) {
				fromNode[v] = u;
				fromWeight[v] = weight[e];
				dfs1(v);
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

	// 迭代版
	public static void dfs2(int cur) {
		stacksize = 0;
		push(cur, -1, -1);
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
			if (e != 0) {
				v = to[e];
				if (dfn[v] == 0) {
					push(u, 0, e);
					fromNode[v] = u;
					fromWeight[v] = weight[e];
					push(v, -1, -1);
				} else {
					push(u, 1, e);
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
		long best = 0;
		long sum = 0;
		for (int i = 1; i <= cnta; i++) {
			preMax[i] = Math.max(preMax[i - 1], height[i] + sum);
			preDiameter[i] = Math.max(preDiameter[i - 1], sum + height[i] + best);
			best = Math.max(best, height[i] - sum);
			sum += val[i];
		}
		sufMax[cnta] = height[cnta] + val[cnta];
		sufDiameter[cnta] = height[cnta];
		best = height[cnta] - val[cnta];
		sum = val[cnta] + val[cnta - 1];
		for (int i = cnta - 1; i >= 1; i--) {
			sufMax[i] = Math.max(sufMax[i + 1], height[i] + sum);
			sufDiameter[i] = Math.max(sufDiameter[i + 1], sum + height[i] + best);
			best = Math.max(best, height[i] - sum);
			sum += val[i - 1];
		}
		long ans = Long.MAX_VALUE;
		for (int i = 1; i < cnta; i++) {
			ans = Math.min(ans, Math.max(preMax[i] + sufMax[i + 1], Math.max(preDiameter[i], sufDiameter[i + 1])));
		}
		ans = Math.min(ans, preDiameter[cnta]);
		return ans;
	}

	public static long compute() {
		// dfs1(1);
		dfs2(1);
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
