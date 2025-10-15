package class180;

// 寻宝游戏，java版
// 一共有n个节点，给定n-1条无向边，每条边有边权，所有节点组成一棵树
// 开始时所有节点都是无宝点，接下来有m条操作，每条操作格式如下
// 操作 x : 如果x号点是无宝点，那么变成有宝点，如果x号点是有宝点，那么变成无宝点
// 每次操作后，每个有宝点都会刷新宝物，你可以瞬移到任何地点作为出发点，瞬移无代价
// 然后你需要走路去拿所有的宝物，最后回到出发点，打印最小的行走总路程，一共m条打印
// 1 <= n、m <= 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P3320
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.TreeSet;

public class Code06_TreasureHunt1 {

	public static int MAXN = 100001;
	public static int MAXP = 20;
	public static int n, m;

	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXN << 1];
	public static int[] to = new int[MAXN << 1];
	public static int[] weight = new int[MAXN << 1];
	public static int cntg;

	public static int[] dep = new int[MAXN];
	public static int[] dfn = new int[MAXN];
	public static int[] seg = new int[MAXN];
	public static long[] dist = new long[MAXN];
	public static int[][] stjump = new int[MAXN][MAXP];
	public static int cntd;

	public static int[] arr = new int[MAXN];
	public static boolean[] treasure = new boolean[MAXN];
	// 这里为了方便，使用语言自带的有序表
	public static TreeSet<Integer> set = new TreeSet<>();

	public static long[] ans = new long[MAXN];

	public static void addEdge(int u, int v, int w) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		weight[cntg] = w;
		head[u] = cntg;
	}

	// dfs递归版，java会爆栈，C++可以通过
	public static void dfs1(int u, int fa, int w) {
		dep[u] = dep[fa] + 1;
		dfn[u] = ++cntd;
		seg[cntd] = u;
		dist[u] = dist[fa] + w;
		stjump[u][0] = fa;
		for (int p = 1; p < MAXP; p++) {
			stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
		}
		for (int e = head[u]; e > 0; e = nxt[e]) {
			if (to[e] != fa) {
				dfs1(to[e], u, weight[e]);
			}
		}
	}

	// 不会改迭代版，去看讲解118，详解了从递归版改迭代版
	public static int[][] ufwe = new int[MAXN][4];

	public static int stacksize, u, f, w, e;

	public static void push(int u, int f, int w, int e) {
		ufwe[stacksize][0] = u;
		ufwe[stacksize][1] = f;
		ufwe[stacksize][2] = w;
		ufwe[stacksize][3] = e;
		stacksize++;
	}

	public static void pop() {
		--stacksize;
		u = ufwe[stacksize][0];
		f = ufwe[stacksize][1];
		w = ufwe[stacksize][2];
		e = ufwe[stacksize][3];
	}

	// dfs1的迭代版
	public static void dfs2() {
		stacksize = 0;
		push(1, 0, 0, -1);
		while (stacksize > 0) {
			pop();
			if (e == -1) {
				dep[u] = dep[f] + 1;
				dfn[u] = ++cntd;
				seg[cntd] = u;
				dist[u] = dist[f] + w;
				stjump[u][0] = f;
				for (int p = 1; p < MAXP; p++) {
					stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
				}
				e = head[u];
			} else {
				e = nxt[e];
			}
			if (e != 0) {
				push(u, f, w, e);
				if (to[e] != f) {
					push(to[e], u, weight[e], -1);
				}
			}
		}
	}

	public static int getLca(int a, int b) {
		if (dep[a] < dep[b]) {
			int tmp = a; a = b; b = tmp;
		}
		for (int p = MAXP - 1; p >= 0; p--) {
			if (dep[stjump[a][p]] >= dep[b]) {
				a = stjump[a][p];
			}
		}
		if (a == b) {
			return a;
		}
		for (int p = MAXP - 1; p >= 0; p--) {
			if (stjump[a][p] != stjump[b][p]) {
				a = stjump[a][p];
				b = stjump[b][p];
			}
		}
		return stjump[a][0];
	}

	public static long getDist(int x, int y) {
		return dist[x] + dist[y] - 2 * dist[getLca(x, y)];
	}

	public static void compute() {
		long curAns = 0;
		for (int i = 1; i <= m; i++) {
			int nodeId = arr[i];
			int dfnId = dfn[nodeId];
			if (!treasure[nodeId]) {
				treasure[nodeId] = true;
				set.add(dfnId);
			} else {
				treasure[nodeId] = false;
				set.remove(dfnId);
			}
			if (set.size() <= 1) {
				curAns = 0;
			} else {
				int low = seg[set.lower(dfnId) != null ? set.lower(dfnId) : set.last()];
				int high = seg[set.higher(dfnId) != null ? set.higher(dfnId) : set.first()];
				long delta = getDist(nodeId, low) + getDist(nodeId, high) - getDist(low, high);
				if (treasure[nodeId]) {
					curAns += delta;
				} else {
					curAns -= delta;
				}
			}
			ans[i] = curAns;
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
		// dfs1(1, 0, 0);
		dfs2();
		for (int i = 1; i <= m; i++) {
			arr[i] = in.nextInt();
		}
		compute();
		for (int i = 1; i <= m; i++) {
			out.println(ans[i]);
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
