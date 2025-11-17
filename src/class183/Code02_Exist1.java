package class183;

// 距离为k的点对是否存在，java版
// 一共有n个节点，给定n-1条边，每条边有边权，所有节点组成一棵树
// 一共有m条查询，每条查询给定数字k，打印树上距离为k的点对是否存在
// 1 <= n <= 10^4    1 <= 边权 <= 10^4
// 1 <= m <= 100     1 <= k <= 10^7
// 测试链接 : https://www.luogu.com.cn/problem/P3806
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code02_Exist1 {

	public static int MAXN = 10001;
	public static int MAXM = 101;
	public static int MAXV = 10000001;
	public static int n, m;

	public static int[] query = new int[MAXM];
	public static int maxk;

	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXN << 1];
	public static int[] to = new int[MAXN << 1];
	public static int[] weight = new int[MAXN << 1];
	public static int cntg;

	public static boolean[] vis = new boolean[MAXN];
	public static int[] siz = new int[MAXN];

	// 从u出发，到当前子树的每个节点，收集路径权值和
	public static int[] cur = new int[MAXN];
	public static int cntc;

	// 从u出发，到之前所有子树的每个节点，收集路径权值和
	public static int[] all = new int[MAXN];
	public static int cnta;

	// 使用数组替代哈希表，因为哈希表常数时间大，会超时
	public static boolean[] exist = new boolean[MAXV];

	public static boolean[] ans = new boolean[MAXM];

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
		if (dis > maxk) {
			return;
		}
		cur[++cntc] = dis;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (v != fa && !vis[v]) {
				dfs(v, u, dis + weight[e]);
			}
		}
	}

	public static void calc(int u) {
		cnta = 0;
		exist[0] = true;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (!vis[v]) {
				cntc = 0;
				dfs(v, u, weight[e]);
				for (int i = 1; i <= m; i++) {
					for (int j = 1; !ans[i] && j <= cntc; j++) {
						if (query[i] - cur[j] >= 0) {
							ans[i] |= exist[query[i] - cur[j]];
						}
					}
				}
				for (int i = 1; i <= cntc; i++) {
					all[++cnta] = cur[i];
					exist[cur[i]] = true;
				}
			}
		}
		for (int i = 1; i <= cnta; i++) {
			exist[all[i]] = false;
		}
	}

	public static void solve(int u) {
		vis[u] = true;
		calc(u);
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (!vis[v]) {
				solve(getCentroid(v, u));
			}
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		for (int i = 1, u, v, w; i < n; i++) {
			u = in.nextInt();
			v = in.nextInt();
			w = in.nextInt();
			addEdge(u, v, w);
			addEdge(v, u, w);
		}
		for (int i = 1; i <= m; i++) {
			query[i] = in.nextInt();
			maxk = Math.max(maxk, query[i]);
		}
		solve(getCentroid(1, 0));
		for (int i = 1; i <= m; i++) {
			if (ans[i]) {
				out.println("AYE");
			} else {
				out.println("NAY");
			}
		}
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
