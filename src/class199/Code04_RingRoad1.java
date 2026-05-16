package class199;

// 城市环路，java版
// 测试链接 : https://www.luogu.com.cn/problem/P1453
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code04_RingRoad1 {

	public static int MAXN = 100001;
	public static int n;
	public static double k;
	public static int[] arr = new int[MAXN];

	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXN << 1];
	public static int[] to = new int[MAXN << 1];
	public static int cntg;

	public static boolean[] vis = new boolean[MAXN];
	public static int start1, start2, skipEdge;

	public static int[][] dp = new int[MAXN][2];

	public static void addEdge(int u, int v) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		head[u] = cntg;
	}

	public static boolean dfs(int u, int preEdge) {
		vis[u] = true;
		boolean ans = false;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			if (e != (preEdge ^ 1)) {
				int v = to[e];
				if (vis[v] && start1 == 0) {
					start1 = v;
					start2 = u;
					skipEdge = e >> 1;
					ans = true;
				}
				if (!vis[v] && dfs(v, e) && u != start1) {
					ans = true;
				}
			}
		}
		return ans;
	}

	public static void dpOnTree(int u, int fa) {
		dp[u][0] = 0;
		dp[u][1] = arr[u];
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (v != fa && (e >> 1) != skipEdge) {
				dpOnTree(v, u);
				dp[u][0] += Math.max(dp[v][0], dp[v][1]);
				dp[u][1] += dp[v][0];
			}
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		cntg = 1;
		for (int i = 1; i <= n; i++) {
			arr[i] = in.nextInt();
		}
		for (int i = 1, u, v; i <= n; i++) {
			u = in.nextInt();
			v = in.nextInt();
			u++;
			v++;
			addEdge(u, v);
			addEdge(v, u);
		}
		k = in.nextDouble();
		start1 = start2 = 0;
		dfs(1, 0);
		dpOnTree(start1, 0);
		int ans = dp[start1][0];
		dpOnTree(start2, 0);
		ans = Math.max(ans, dp[start2][0]);
		out.printf("%.1f\n", k * ans);
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
