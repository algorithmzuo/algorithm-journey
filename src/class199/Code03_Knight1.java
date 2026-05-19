package class199;

// 骑士，java版
// 图中有n个点、n条无向边，每个点有点权，可能有重边，但无自环
// 保证每个连通块恰好有一个环，图中任意相邻两点不能同时选择
// 希望选择节点的点权累加和尽量大，计算这个结果
// 1 <= n、点权 <= 10^6
// 测试链接 : https://www.luogu.com.cn/problem/P2607
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code03_Knight1 {

	public static int MAXN = 1000001;
	public static int n;
	public static int[] arr = new int[MAXN];

	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXN << 1];
	public static int[] to = new int[MAXN << 1];
	public static int cntg;

	public static int[] dfn = new int[MAXN];
	public static int cntd;

	public static int x, y, skip;

	public static long[][] dp = new long[MAXN][2];

	public static void addEdge(int u, int v) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		head[u] = cntg;
	}

	public static void dfs(int u, int preEdge) {
		dfn[u] = ++cntd;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (e != (preEdge ^ 1)) {
				if (dfn[v] == 0) {
					dfs(v, e);
				} else if (dfn[u] < dfn[v]) {
					x = u;
					y = v;
					skip = e >> 1;
				}
			}
		}
	}

	public static void dpOnTree(int u, int fa) {
		dp[u][0] = 0;
		dp[u][1] = arr[u];
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (v != fa && (e >> 1) != skip) {
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
		for (int x = 1, y; x <= n; x++) {
			arr[x] = in.nextInt();
			y = in.nextInt();
			addEdge(x, y);
			addEdge(y, x);
		}
		long ans = 0;
		for (int i = 1; i <= n; i++) {
			if (dfn[i] == 0) {
				x = y = 0;
				dfs(i, 0);
				dpOnTree(x, 0);
				long cur = dp[x][0];
				dpOnTree(y, 0);
				cur = Math.max(cur, dp[y][0]);
				ans += cur;
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
