package class183;

// 距离<=k的点对数量，java版
// 一共有n个节点，给定n-1条边，每条边有边权，所有节点组成一棵树
// 给定数字k，求出树上两点距离<=k的点对数量
// 本题规定(x, x)不是点对，(x, y)和(y, x)认为是同一个点对
// 1 <= n <= 4 * 10^4
// 0 <= 边权 <= 10^3
// 0 <= k <= 2 * 10^4
// 测试链接 : https://www.luogu.com.cn/problem/P4178
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

public class Code04_Tree1 {

	public static int MAXN = 50001;
	public static int n, k;

	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXN << 1];
	public static int[] to = new int[MAXN << 1];
	public static int[] weight = new int[MAXN << 1];
	public static int cntg;

	public static boolean[] vis = new boolean[MAXN];
	public static int[] siz = new int[MAXN];

	public static int[] disArr = new int[MAXN];
	public static int cnta;

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
		if (dis > k) {
			return;
		}
		disArr[++cnta] = dis;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (v != fa && !vis[v]) {
				dfs(v, u, dis + weight[e]);
			}
		}
	}

	// 从真正的头来到u时，路径权值和为dis
	// 子树u上的点对(x, y)，需要走过，x -> 真正的头 -> y
	// 返回距离<=k的点对数量
	public static long calc(int u, int dis) {
		cnta = 0;
		dfs(u, 0, dis);
		long ans = 0;
		Arrays.sort(disArr, 1, cnta + 1);
		for (int l = 1, r = cnta; l <= r;) {
			if (disArr[l] + disArr[r] <= k) {
				ans += r - l;
				l++;
			} else {
				r--;
			}
		}
		return ans;
	}

	public static long solve(int u) {
		vis[u] = true;
		long ans = calc(u, 0);
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (!vis[v]) {
				ans -= calc(v, weight[e]);
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
		k = in.nextInt();
		out.println(solve(getCentroid(1, 0)));
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
