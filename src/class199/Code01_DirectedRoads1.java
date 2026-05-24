package class199;

// 定向道路，java版
// 图中有n个点、n条无向边
// 图中可能有多个连通块，保证每个连通块都是一棵基环树
// 你可以给每条边分配一个方向，要求得到的有向图不能出现环
// 计算有多少种定向的方案，答案对 1000000007 取余
// 2 <= n <= 2 * 10^5
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
	public static int n, cntb;

	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXN << 1];
	public static int[] to = new int[MAXN << 1];
	public static int cntg;

	public static int[] dfn = new int[MAXN];
	public static int cntd;

	public static int[] from = new int[MAXN];
	// 基环树的节点总数，也就是边的总数
	public static int[] all = new int[MAXN];
	// 基环树的环上节点数，也就是环上边数
	public static int[] cycle = new int[MAXN];

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

	public static void dfs(int u) {
		dfn[u] = ++cntd;
		all[cntb]++;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (dfn[v] == 0) {
				from[v] = u;
				dfs(v);
			} else if (dfn[u] < dfn[v]) {
				cycle[cntb]++;
				for (int i = v; i != u; i = from[i]) {
					cycle[cntb]++;
				}
			}
		}
	}

	public static long compute() {
		long ans = 1;
		for (int i = 1; i <= n; i++) {
			if (dfn[i] == 0) {
				cntb++;
				dfs(i);
				long a = power(2, all[cntb]);
				long b = power(2, all[cntb] - cycle[cntb] + 1);
				ans = ans * ((a - b + MOD) % MOD) % MOD;
			}
		}
		return ans;
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
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
