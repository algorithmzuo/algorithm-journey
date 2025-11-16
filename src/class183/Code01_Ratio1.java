package class183;

// 聪聪可可，java版
// 一共有n个节点，给定n-1条边，每条边有边权，所有节点组成一棵树
// 如果点对(u, v)的简单路径权值和能被3整除，则称这个点对是合法的
// 认为点对(x, x)的简单路径权值和是0，并且认为(x, y)和(y, x)是不同的点对
// 打印(合法点对的数量 / 点对的总数量)的最简分数形式
// 1 <= n、边权 <= 2 * 10^4
// 测试链接 : https://www.luogu.com.cn/problem/P2634
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code01_Ratio1 {

	public static int MAXN = 20001;
	public static int n;

	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXN << 1];
	public static int[] to = new int[MAXN << 1];
	public static int[] weight = new int[MAXN << 1];
	public static int cntg;

	// vis[u] = true，表示u是之前的分治点
	public static boolean[] vis = new boolean[MAXN];
	public static int[] siz = new int[MAXN];

	// cur[v]，表示往下走的当前路径中，路径权值和 % 3 == v的路径有多少条
	// all[v]，表示往下走的所有路径中，路径权值和 % 3 == v的路径有多少条
	public static int[] cur = new int[3];
	public static int[] all = new int[3];

	public static int gcd(int a, int b) {
		return b == 0 ? a : gcd(b, a % b);
	}

	public static void addEdge(int u, int v, int w) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		weight[cntg] = w;
		head[u] = cntg;
	}

	public static void getSize(int u, int fa) {
		siz[u] = 1;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (v != fa && !vis[v]) {
				getSize(v, u);
				siz[u] += siz[v];
			}
		}
	}

	public static int getCentroid(int u, int fa) {
		getSize(u, fa);
		int half = siz[u] >> 1;
		boolean find = false;
		while (!find) {
			find = true;
			for (int e = head[u]; e > 0; e = nxt[e]) {
				int v = to[e];
				if (v != fa && !vis[v] && siz[v] > half) {
					fa = u;
					u = v;
					find = false;
					break;
				}
			}
		}
		return u;
	}

	public static void dfs(int u, int fa, int dis) {
		cur[dis % 3]++;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (v != fa && !vis[v]) {
				dfs(v, u, dis + weight[e]);
			}
		}
	}

	public static int calc(int u) {
		int ans = 1;
		all[0] = all[1] = all[2] = 0;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			int w = weight[e];
			if (!vis[v]) {
				cur[0] = cur[1] = cur[2] = 0;
				dfs(v, u, w);
				ans += all[0] * cur[0] * 2 + all[1] * cur[2] * 2 + all[2] * cur[1] * 2 + cur[0] * 2;
				all[0] += cur[0];
				all[1] += cur[1];
				all[2] += cur[2];
			}
		}
		return ans;
	}

	public static int solve(int u) {
		int ans = 0;
		vis[u] = true;
		ans += calc(u);
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (!vis[v]) {
				ans += solve(getCentroid(v, u));
			}
		}
		return ans;
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		for (int i = 1, u, v, w; i < n; i++) {
			u = in.nextInt();
			v = in.nextInt();
			w = in.nextInt();
			addEdge(u, v, w);
			addEdge(v, u, w);
		}
		int centroid = getCentroid(1, 0);
		int a = solve(centroid);
		int b = n * n;
		int c = gcd(a, b);
		a /= c;
		b /= c;
		out.println(a + "/" + b);
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
