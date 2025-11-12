package class183;

// 距离<=k的点对数量，java版
// 测试链接 : https://www.luogu.com.cn/problem/P4178
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

public class Code03_Tree1 {

	public static int MAXN = 50001;
	public static int n, k, total;

	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXN << 1];
	public static int[] to = new int[MAXN << 1];
	public static int[] weight = new int[MAXN << 1];
	public static int cntg;

	public static boolean[] vis = new boolean[MAXN];
	public static int[] siz = new int[MAXN];
	public static int[] maxPart = new int[MAXN];
	public static int centroid;

	public static int[] dis = new int[MAXN];
	public static int[] arr = new int[MAXN];
	public static int cnta;

	public static void addEdge(int u, int v, int w) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		weight[cntg] = w;
		head[u] = cntg;
	}

	public static void getCentroid(int u, int fa) {
		siz[u] = 1;
		maxPart[u] = 0;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (v != fa && !vis[v]) {
				getCentroid(v, u);
				siz[u] += siz[v];
				maxPart[u] = Math.max(siz[v], maxPart[u]);
			}
		}
		maxPart[u] = Math.max(maxPart[u], total - siz[u]);
		if (centroid == 0 || maxPart[u] < maxPart[centroid]) {
			centroid = u;
		}
	}

	public static void dfs(int u, int fa, int w) {
		dis[u] = dis[fa] + w;
		if (dis[u] > k) {
			return;
		}
		arr[++cnta] = dis[u];
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (v != fa && !vis[v]) {
				dfs(v, u, weight[e]);
			}
		}
	}

	public static long calc(int u, int fa, int w) {
		dis[fa] = 0;
		cnta = 0;
		dfs(u, fa, w);
		long ans = 0;
		Arrays.sort(arr, 1, cnta + 1);
		for (int l = 1, r = cnta; l < r;) {
			if (arr[l] + arr[r] <= k) {
				ans += r - l;
				l++;
			} else {
				r--;
			}
		}
		return ans;
	}

	public static long solve(int u) {
		long ans = 0;
		ans += calc(u, 0, 0);
		vis[u] = true;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (!vis[v]) {
				ans -= calc(v, u, weight[e]);
				total = siz[v];
				centroid = 0;
				getCentroid(v, 0);
				ans += solve(centroid);
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
		total = n;
		centroid = 0;
		getCentroid(1, 0);
		out.println(solve(centroid));
		out.flush();
		out.close();
	}

	// 读写工具类
	static class FastReader {
		private final byte[] buffer = new byte[1 << 20];
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
