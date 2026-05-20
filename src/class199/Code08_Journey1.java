package class199;

// 旅行，java版
// 图中有n个点、m条无向边，节点编号1~n，图是普通树或者基环树
// 如果是普通树，遍历所有节点，可以形成dfs序列，打印字典序最小的结果
// 如果图是基环树，可以删掉环上的任何一条边，让图变成普通树
// 每棵普通树都有字典序最小的结果，在所有可能中，打印字典序最小的结果
// 1 <= n <= 5 * 10^5
// 普通版测试 : https://www.luogu.com.cn/problem/P5022
// 加强版测试 : https://www.luogu.com.cn/problem/P5049
// 提交以下的code，提交时请把类名改成"Main"，可以通过普通版测试
// 加强版测试卡常，java实现无法通过，索性递归函数也不改迭代了
// 想通过用C++实现，本节课Code08_Journey2文件就是C++的实现
// 两个版本的逻辑完全一样，C++版本可以通过所有测试

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

public class Code08_Journey1 {

	public static int MAXN = 500001;
	public static int n, m;

	public static int[][] arr = new int[MAXN << 1][3];
	public static int cnte;

	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXN << 1];
	public static int[] to = new int[MAXN << 1];
	public static int[] eid = new int[MAXN << 1];
	public static int cntg;

	public static int[] dfn = new int[MAXN];
	public static int cntd;

	public static int[] from = new int[MAXN];
	public static boolean[] cycle = new boolean[MAXN];

	public static boolean cut;
	public static boolean[] vis = new boolean[MAXN];
	public static int[] ans = new int[MAXN];
	public static int cnta;

	public static void addEdge(int u, int v, int id) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		eid[cntg] = id;
		head[u] = cntg;
	}

	public static void dfs(int u, int preEdge) {
		dfn[u] = ++cntd;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (eid[e] != eid[preEdge]) {
				if (dfn[v] == 0) {
					from[v] = u;
					dfs(v, e);
				} else if (dfn[u] < dfn[v]) {
					cycle[u] = true;
					for (int i = v; i != u; i = from[i]) {
						cycle[i] = true;
					}
				}
			}
		}
	}

	public static void path(int u, int back) {
		vis[u] = true;
		ans[++cnta] = u;
		int cutNode = 0;
		if (!cut) {
			for (int e = head[u]; e > 0; e = nxt[e]) {
				int v = to[e];
				if (!vis[v]) {
					cutNode = cycle[v] && v > back ? v : 0;
				}
			}
		}
		for (int e = head[u], ne; e > 0; e = ne) {
			ne = nxt[e];
			int v = to[e];
			if (!vis[v]) {
				if (v == cutNode) {
					cut = true;
					return;
				}
				int next = 0;
				for (; ne > 0; ne = nxt[ne]) {
					int nv = to[ne];
					if (!vis[nv]) {
						next = nv;
						break;
					}
				}
				path(v, next > 0 && cycle[u] ? next : back);
			}
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		for (int i = 1, u, v; i <= m; i++) {
			u = in.nextInt();
			v = in.nextInt();
			arr[++cnte][0] = u;
			arr[cnte][1] = v;
			arr[cnte][2] = i;
			arr[++cnte][0] = v;
			arr[cnte][1] = u;
			arr[cnte][2] = i;
		}
		Arrays.sort(arr, 1, cnte + 1, (a, b) -> a[0] != b[0] ? a[0] - b[0] : b[1] - a[1]);
		for (int i = 1; i <= cnte; i++) {
			addEdge(arr[i][0], arr[i][1], arr[i][2]);
		}
		dfs(1, 0);
		path(1, n + 1);
		for (int i = 1; i <= n; i++) {
			out.print(ans[i] + " ");
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