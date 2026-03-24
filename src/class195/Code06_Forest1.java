package class195;

// 逛森林，java版
// 测试链接 : https://www.luogu.com.cn/problem/P5344
// 提交以下的code，提交时请把类名改成"Main"
// 本题卡常java的实现无法通过
// 想通过用C++实现，本节课Code06_Forest2文件就是C++的实现
// 两个版本的逻辑完全一样，C++版本可以通过所有测试

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.PriorityQueue;

public class Code06_Forest1 {

	public static int MAXN = 50001;
	public static int MAXM = 1000001;
	public static int MAXT = 5000001;
	public static int MAXE = 30000001;
	public static int MAXP = 17;
	public static int INF = 1 << 30;
	public static int n, m, s;

	public static int[] u1 = new int[MAXM];
	public static int[] v1 = new int[MAXM];
	public static int[] u2 = new int[MAXM];
	public static int[] v2 = new int[MAXM];
	public static int[] weight = new int[MAXM];
	public static int cntq;

	public static int[] head1 = new int[MAXN];
	public static int[] next1 = new int[MAXN << 1];
	public static int[] to1 = new int[MAXN << 1];
	public static int cnt1;

	public static int[] head2 = new int[MAXT];
	public static int[] next2 = new int[MAXE];
	public static int[] to2 = new int[MAXE];
	public static int[] weight2 = new int[MAXE];
	public static int cnt2;

	public static int[] father = new int[MAXN];

	public static int[] dep = new int[MAXN];
	public static int[][] stfa = new int[MAXN][MAXP];
	public static int[][] stin = new int[MAXN][MAXP];
	public static int[][] stout = new int[MAXN][MAXP];
	public static int cntt;

	public static int[] dist = new int[MAXT];
	public static boolean[] vis = new boolean[MAXT];
	public static PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> a[1] - b[1]);

	public static void addEdge1(int u, int v) {
		next1[++cnt1] = head1[u];
		to1[cnt1] = v;
		head1[u] = cnt1;
	}

	public static void addEdge2(int u, int v, int w) {
		next2[++cnt2] = head2[u];
		to2[cnt2] = v;
		weight2[cnt2] = w;
		head2[u] = cnt2;
	}

	public static int find(int i) {
		if (i != father[i]) {
			father[i] = find(father[i]);
		}
		return father[i];
	}

	public static void dfs(int u, int fa) {
		dep[u] = dep[fa] + 1;
		stfa[u][0] = fa;
		stin[u][0] = ++cntt;
		addEdge2(u, cntt, 0);
		addEdge2(fa, cntt, 0);
		stout[u][0] = ++cntt;
		addEdge2(cntt, u, 0);
		addEdge2(cntt, fa, 0);
		for (int p = 1; p < MAXP; p++) {
			stfa[u][p] = stfa[stfa[u][p - 1]][p - 1];
			stin[u][p] = ++cntt;
			addEdge2(stin[u][p - 1], cntt, 0);
			addEdge2(stin[stfa[u][p - 1]][p - 1], cntt, 0);
			stout[u][p] = ++cntt;
			addEdge2(cntt, stout[u][p - 1], 0);
			addEdge2(cntt, stout[stfa[u][p - 1]][p - 1], 0);
		}
		for (int e = head1[u]; e > 0; e = next1[e]) {
			int v = to1[e];
			if (v != fa) {
				dfs(v, u);
			}
		}
	}

	public static void lcaIn(int x, int y, int vnode) {
		if (dep[x] < dep[y]) {
			int tmp = x;
			x = y;
			y = tmp;
		}
		addEdge2(y, vnode, 0);
		for (int p = MAXP - 1; p >= 0; p--) {
			if (dep[stfa[x][p]] >= dep[y]) {
				addEdge2(stin[x][p], vnode, 0);
				x = stfa[x][p];
			}
		}
		if (x == y) {
			return;
		}
		for (int p = MAXP - 1; p >= 0; p--) {
			if (stfa[x][p] != stfa[y][p]) {
				addEdge2(stin[x][p], vnode, 0);
				addEdge2(stin[y][p], vnode, 0);
				x = stfa[x][p];
				y = stfa[y][p];
			}
		}
		addEdge2(stin[x][0], vnode, 0);
	}

	public static void lcaOut(int x, int y, int vnode) {
		if (dep[x] < dep[y]) {
			int tmp = x;
			x = y;
			y = tmp;
		}
		addEdge2(vnode, y, 0);
		for (int p = MAXP - 1; p >= 0; p--) {
			if (dep[stfa[x][p]] >= dep[y]) {
				addEdge2(vnode, stout[x][p], 0);
				x = stfa[x][p];
			}
		}
		if (x == y) {
			return;
		}
		for (int p = MAXP - 1; p >= 0; p--) {
			if (stfa[x][p] != stfa[y][p]) {
				addEdge2(vnode, stout[x][p], 0);
				addEdge2(vnode, stout[y][p], 0);
				x = stfa[x][p];
				y = stfa[y][p];
			}
		}
		addEdge2(vnode, stout[x][0], 0);
	}

	public static void dijkstra() {
		heap.clear();
		for (int i = 0; i <= cntt; i++) {
			dist[i] = INF;
			vis[i] = false;
		}
		dist[s] = 0;
		heap.add(new int[] { s, 0 });
		while (!heap.isEmpty()) {
			int[] cur = heap.poll();
			int u = cur[0];
			int d = cur[1];
			if (!vis[u]) {
				vis[u] = true;
				for (int e = head2[u]; e > 0; e = next2[e]) {
					int v = to2[e];
					int w = weight2[e];
					if (!vis[v] && dist[v] > d + w) {
						dist[v] = d + w;
						heap.add(new int[] { v, dist[v] });
					}
				}
			}
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		s = in.nextInt();
		cntt = n;
		for (int i = 1; i <= n; i++) {
			father[i] = i;
		}
		for (int i = 1, op, a, b, c, d, w, u, v; i <= m; i++) {
			op = in.nextInt();
			if (op == 1) {
				a = in.nextInt();
				b = in.nextInt();
				c = in.nextInt();
				d = in.nextInt();
				w = in.nextInt();
				if (find(a) == find(b) && find(c) == find(d)) {
					u1[++cntq] = a;
					v1[cntq] = b;
					u2[cntq] = c;
					v2[cntq] = d;
					weight[cntq] = w;
				}
			} else {
				u = in.nextInt();
				v = in.nextInt();
				w = in.nextInt();
				int ufa = find(u);
				int vfa = find(v);
				if (ufa != vfa) {
					addEdge1(u, v);
					addEdge1(v, u);
					addEdge2(u, v, w);
					addEdge2(v, u, w);
					father[ufa] = vfa;
				}
			}
		}
		for (int i = 1; i <= n; i++) {
			if (dep[i] == 0) {
				dfs(i, 0);
			}
		}
		for (int i = 1; i <= cntq; i++) {
			int vin = ++cntt;
			int vout = ++cntt;
			lcaIn(u1[i], v1[i], vin);
			lcaOut(u2[i], v2[i], vout);
			addEdge2(vin, vout, weight[i]);
		}
		dijkstra();
		for (int i = 1; i <= n; i++) {
			out.print(dist[i] == INF ? -1 : dist[i]);
			out.print(" ");
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
