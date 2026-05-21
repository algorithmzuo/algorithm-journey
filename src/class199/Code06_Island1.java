package class199;

// 岛屿，java版
// 图中有n个点、n条无向边，每条边有边权
// 图中可能有多个连通块，保证每个连通块都是一棵基环树
// 对每个连通块，计算点不重复的路径能得到的最大边权和，即基环树直径
// 图中每棵基环树的直径累加起来，打印这个结果
// 2 <= n <= 10^6
// 1 <= 边权 <= 10^8
// 测试链接 : https://www.luogu.com.cn/problem/P4381
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code06_Island1 {

	public static int MAXN = 1000001;
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

	public static long[] sum = new long[MAXN << 1];
	public static long[] height = new long[MAXN << 1];
	public static int[] que = new int[MAXN << 1];

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

	public static long compute(int root) {
		cnta = 0;
		// dfs1(root, 0);
		dfs2(root, 0);
		sum[1] = 0;
		for (int i = 2, j = 1; j <= cnta; i++, j++) {
			sum[i] = val[j];
		}
		for (int i = cnta + 2; i <= cnta * 2; i++) {
			sum[i] = sum[i - cnta];
		}
		for (int i = 1; i <= cnta * 2; i++) {
			sum[i] += sum[i - 1];
		}
		long ans1 = 0;
		for (int i = 1; i <= cnta; i++) {
			diameter = 0;
			dpOnTree(arr[i], 0);
			ans1 = Math.max(ans1, diameter);
			height[i] = dist[arr[i]];
			height[i + cnta] = height[i];
		}
		long ans2 = 0;
		int ql = 1, qr = 0;
		for (int i = 1; i <= cnta * 2; i++) {
			while (ql <= qr && que[ql] <= i - cnta) {
				ql++;
			}
			if (ql <= qr) {
				ans2 = Math.max(ans2, height[i] + height[que[ql]] + sum[i] - sum[que[ql]]);
			}
			while (ql <= qr && height[que[qr]] - sum[que[qr]] <= height[i] - sum[i]) {
				qr--;
			}
			que[++qr] = i;
		}
		return Math.max(ans1, ans2);
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		cntg = 1;
		for (int u = 1, v, w; u <= n; u++) {
			v = in.nextInt();
			w = in.nextInt();
			addEdge(u, v, w);
			addEdge(v, u, w);
		}
		long ans = 0;
		for (int i = 1; i <= n; i++) {
			if (dfn[i] == 0) {
				ans += compute(i);
			}
		}
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
	}

}
