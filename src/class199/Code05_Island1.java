package class199;

// 岛屿，java版
// 测试链接 : https://www.luogu.com.cn/problem/P4381
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code05_Island1 {

	public static int MAXN = 1000001;
	public static int n;

	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXN << 1];
	public static int[] to = new int[MAXN << 1];
	public static int[] weight = new int[MAXN << 1];
	public static int cntg;

	public static boolean[] vis = new boolean[MAXN];
	public static boolean[] cycle = new boolean[MAXN];
	public static int start;

	public static int[] arr = new int[MAXN];
	public static int cnta;

	public static long[] dist = new long[MAXN];
	public static long diameter;

	public static long[] sum = new long[MAXN << 1];
	public static long[] height = new long[MAXN << 1];
	public static int[] que = new int[MAXN << 1];

	public static void addEdge(int u, int v, int w) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		weight[cntg] = w;
		head[u] = cntg;
	}

	// 递归版
	public static boolean dfs1(int u, int preEdge) {
		vis[u] = true;
		boolean ans = false;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			if (e != (preEdge ^ 1)) {
				int v = to[e];
				int w = weight[e];
				if (vis[v] && start == 0) {
					start = v;
					cycle[v] = true;
					arr[++cnta] = v;
					sum[cnta] = 0;
					cycle[u] = true;
					arr[++cnta] = u;
					sum[cnta] = w;
					ans = true;
				}
				if (!vis[v] && dfs1(v, e) && u != start) {
					cycle[u] = true;
					arr[++cnta] = u;
					sum[cnta] = w;
					ans = true;
				}
			}
		}
		return ans;
	}

	public static int[] sta = new int[MAXN];
	public static int[] pree = new int[MAXN];
	public static int[] iter = new int[MAXN];
	public static boolean[] staAns = new boolean[MAXN];

	// dfs1的迭代版
	public static boolean dfs2(int root, int preEdge) {
		int top = 0;
		sta[++top] = root;
		pree[root] = preEdge;
		iter[root] = head[root];
		staAns[top] = false;
		vis[root] = true;
		boolean ans = false;
		while (top > 0) {
			int u = sta[top];
			int e = iter[u];
			if (e == 0) {
				ans = staAns[top];
				top--;
				if (top > 0) {
					int father = sta[top];
					int w = weight[pree[u]];
					if (ans && father != start) {
						cycle[father] = true;
						arr[++cnta] = father;
						sum[cnta] = w;
						staAns[top] = true;
					}
				}
			} else {
				iter[u] = nxt[e];
				if (e != (pree[u] ^ 1)) {
					int v = to[e];
					int w = weight[e];
					if (vis[v] && start == 0) {
						start = v;
						cycle[v] = true;
						arr[++cnta] = v;
						sum[cnta] = 0;
						cycle[u] = true;
						arr[++cnta] = u;
						sum[cnta] = w;
						staAns[top] = true;
					}
					if (!vis[v]) {
						vis[v] = true;
						sta[++top] = v;
						pree[v] = e;
						iter[v] = head[v];
						staAns[top] = false;
					}
				}
			}
		}
		return ans;
	}

	public static void dp(int u, int fa) {
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			int w = weight[e];
			if (v != fa && !cycle[v]) {
				dp(v, u);
				diameter = Math.max(diameter, dist[u] + dist[v] + w);
				dist[u] = Math.max(dist[u], dist[v] + w);
			}
		}
	}

	public static long compute(int root) {
		start = 0;
		cnta = 0;
		// dfs1(root, 0);
		dfs2(root, 0);
		if (start == 0) {
			diameter = 0;
			dp(root, 0);
			return diameter;
		}
		for (int e = head[arr[1]]; e > 0; e = nxt[e]) {
			int v = to[e];
			int w = weight[e];
			if (v == arr[cnta]) {
				sum[cnta + 1] = w;
				break;
			}
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
			dp(arr[i], 0);
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
			if (!vis[i]) {
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
