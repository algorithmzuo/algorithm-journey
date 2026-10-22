package class208;

// HLPP算法，java版
// 图中一共n个点，m条有向边，给定源点s和汇点t
// 给定每条有向边的起点、终点、容量
// 计算源点s到汇点t的网络最大流
// 1 <= n <= 1200
// 1 <= m <= 120000
// 边的容量为非负整数
// 测试链接 : https://www.luogu.com.cn/problem/P4722
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code04_HLPP1 {

	public static int MAXN = 1201;
	public static int MAXM = 120001;
	public static int n, m;

	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXM << 1];
	public static int[] to = new int[MAXM << 1];
	public static int[] limit = new int[MAXM << 1];
	public static int cntg;

	public static long[] excess = new long[MAXN];
	public static int[] dis = new int[MAXN];
	public static int[] que = new int[MAXN];
	public static int[] cur = new int[MAXN];
	public static int[] gap = new int[MAXN << 1];

	public static int[] bucket = new int[MAXN << 1];
	public static int[] nextActive = new int[MAXN];
	public static int highest;

	public static void addEdge(int u, int v, int w) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		limit[cntg] = w;
		head[u] = cntg;
	}

	public static void addActive(int u) {
		highest = Math.max(highest, dis[u]);
		nextActive[u] = bucket[dis[u]];
		bucket[dis[u]] = u;
	}

	public static void pushFlow(int u, int v, int e, long k) {
		excess[u] -= k;
		excess[v] += k;
		limit[e] -= k;
		limit[e ^ 1] += k;
	}

	public static void bfs(int s, int t) {
		for (int i = 1; i <= n; i++) {
			dis[i] = n + 1;
			cur[i] = head[i];
		}
		dis[t] = 0;
		int l = 1, r = 0;
		que[++r] = t;
		while (l <= r) {
			int u = que[l++];
			for (int e = head[u]; e > 0; e = nxt[e]) {
				int v = to[e];
				if (dis[v] == n + 1 && limit[e ^ 1] > 0) {
					dis[v] = dis[u] + 1;
					que[++r] = v;
				}
			}
		}
	}

	public static void relabel(int u, int s, int t) {
		if (dis[u] < n && gap[dis[u]] == 1) {
			int h = dis[u];
			for (int i = 1; i <= n; i++) {
				if (i != s && i != t && dis[i] >= h && dis[i] < n) {
					gap[dis[i]]--;
					dis[i] = n + 1;
					gap[dis[i]]++;
					cur[i] = head[i];
				}
			}
		} else {
			int lowest = (n << 1) - 1;
			for (int e = head[u]; e > 0; e = nxt[e]) {
				int v = to[e];
				if (u != v && limit[e] > 0) {
					lowest = Math.min(lowest, dis[v]);
				}
			}
			gap[dis[u]]--;
			dis[u] = lowest + 1;
			gap[dis[u]]++;
			cur[u] = head[u];
		}
	}

	public static void discharge(int s, int t) {
		int u = bucket[highest];
		bucket[highest] = nextActive[u];
		while (excess[u] > 0) {
			for (int e = cur[u]; e > 0; e = nxt[e]) {
				cur[u] = e;
				int v = to[e];
				int lim = limit[e];
				if (dis[u] == dis[v] + 1 && lim > 0) {
					if (v != s && v != t && excess[v] == 0) {
						addActive(v);
					}
					pushFlow(u, v, e, Math.min(excess[u], lim));
					if (excess[u] == 0) {
						return;
					}
				}
			}
			relabel(u, s, t);
		}
	}

	public static long maxflow(int s, int t) {
		bfs(s, t);
		if (dis[s] == n + 1) {
			return 0;
		}
		dis[s] = n;
		for (int i = 1; i <= n; i++) {
			gap[dis[i]]++;
		}
		for (int e = head[s]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (dis[v] < n) {
				pushFlow(s, v, e, limit[e]);
			}
		}
		for (int i = 0; i <= n << 1; i++) {
			bucket[i] = 0;
		}
		highest = -1;
		for (int i = 1; i <= n; i++) {
			if (i != s && i != t && excess[i] > 0) {
				addActive(i);
			}
		}
		while (highest >= 0) {
			if (bucket[highest] == 0) {
				highest--;
			} else {
				discharge(s, t);
			}
		}
		return excess[t];
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		int s = in.nextInt();
		int t = in.nextInt();
		cntg = 1;
		for (int i = 1, u, v, w; i <= m; i++) {
			u = in.nextInt();
			v = in.nextInt();
			w = in.nextInt();
			addEdge(u, v, w);
			addEdge(v, u, 0);
		}
		long ans = maxflow(s, t);
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