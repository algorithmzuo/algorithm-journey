package class208;

// Edmonds-Karp算法，java版
// 测试链接 : https://www.luogu.com.cn/problem/P3376
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

public class Code01_EdmondsKarp1 {

	public static int MAXN = 201;
	public static int MAXM = 5001;
	public static int INF = 1000000001;
	public static int n, m;

	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXM << 1];
	public static int[] to = new int[MAXM << 1];
	public static int[] limit = new int[MAXM << 1];
	public static int cntg;

	public static long[] flow = new long[MAXN];
	public static int[] pre = new int[MAXN];
	public static int[] que = new int[MAXN];

	public static void addEdge(int u, int v, int w) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		limit[cntg] = w;
		head[u] = cntg;
	}

	public static void bfs(int s, int t) {
		Arrays.fill(flow, 1, n + 1, -1);
		flow[s] = INF;
		int l = 1, r = 0;
		que[++r] = s;
		while (l <= r) {
			int u = que[l++];
			if (u == t) {
				break;
			}
			for (int e = head[u]; e > 0; e = nxt[e]) {
				int v = to[e];
				int lim = limit[e];
				if (lim > 0 && flow[v] == -1) {
					flow[v] = Math.min(flow[u], lim);
					pre[v] = e;
					que[++r] = v;
				}
			}
		}
	}

	public static long maxflow(int s, int t) {
		long ans = 0;
		bfs(s, t);
		while (flow[t] != -1) {
			ans += flow[t];
			for (int u = t; u != s; u = to[pre[u] ^ 1]) {
				limit[pre[u]] -= flow[t];
				limit[pre[u] ^ 1] += flow[t];
			}
			bfs(s, t);
		}
		return ans;
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
