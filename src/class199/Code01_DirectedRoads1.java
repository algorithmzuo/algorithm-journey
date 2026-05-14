package class199;

// 定向道路，dfs找环，java版
// 测试链接 : https://www.luogu.com.cn/problem/CF711D
// 测试链接 : https://codeforces.com/problemset/problem/711/D
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code01_DirectedRoads1 {

	public static int MAXN = 200001;
	public static int MOD = 1000000007;
	public static int n;

	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXN << 1];
	public static int[] to = new int[MAXN << 1];
	public static int cntg;

	public static int[] status = new int[MAXN];
	public static int[] depth = new int[MAXN];
	public static int[] all = new int[MAXN];
	public static int[] circle = new int[MAXN];
	public static int cntb;

	public static void addEdge(int u, int v) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		head[u] = cntg;
	}

	public static long power(long x, long p) {
		long ans = 1;
		while (p > 0) {
			if ((p & 1) == 1) {
				ans = (ans * x) % MOD;
			}
			x = (x * x) % MOD;
			p >>= 1;
		}
		return ans;
	}

	public static void dfs(int u, int preEdge, int dep) {
		status[u] = 1;
		depth[u] = dep;
		all[cntb]++;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			if (e != (preEdge ^ 1)) {
				int v = to[e];
				if (status[v] == 0) {
					dfs(v, e, dep + 1);
				} else if (status[v] == 1) {
					circle[cntb] = depth[u] - depth[v] + 1;
				}
			}
		}
		status[u] = 2;
	}

	public static long compute() {
		long ans = 1;
		for (int i = 1; i <= n; i++) {
			if (status[i] == 0) {
				cntb++;
				dfs(i, 0, 1);
				long a = power(2, all[cntb]);
				long b = power(2, all[cntb] - circle[cntb] + 1);
				ans = ans * ((a - b + MOD) % MOD) % MOD;
			}
		}
		return ans;
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		cntg = 1;
		for (int i = 1, x; i <= n; i++) {
			x = in.nextInt();
			addEdge(i, x);
			addEdge(x, i);
		}
		long ans = compute();
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
