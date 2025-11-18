package class183;

// 消息传递，java版
// 一共有n个节点，给定n-1条边，所有节点组成一棵树
// 如果x号节点收到一个消息，那么消息会从x开始扩散，速度为每天越过一条边
// 接下来有m条查询，每条查询都是相互独立的，格式如下
// 查询 x k : 第0天的时候，x号节点得到一条信息，打印第k天时，新收到该消息的人数
// 1 <= n、m <= 10^5
// 0 <= k < n
// 测试链接 : https://www.luogu.com.cn/problem/P6626
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code07_Message1 {

	public static int MAXN = 100001;
	public static int t, n, m;

	public static int[] headg = new int[MAXN];
	public static int[] nextg = new int[MAXN << 1];
	public static int[] tog = new int[MAXN << 1];
	public static int cntg;

	public static int[] headq = new int[MAXN];
	public static int[] nextq = new int[MAXN];
	public static int[] tim = new int[MAXN];
	public static int[] qid = new int[MAXN];
	public static int cntq;

	public static boolean[] vis = new boolean[MAXN];
	public static int[] siz = new int[MAXN];

	// nodeCnt[i] = j，表示距离头有i条边的点有j个
	public static int[] nodeCnt = new int[MAXN];
	public static int maxEdge;

	public static int[] timArr = new int[MAXN];
	public static int[] qidArr = new int[MAXN];
	public static int cnta;

	public static int[] ans = new int[MAXN];

	// 讲解118，递归函数改成迭代所需要的栈
	public static int[][] stack = new int[MAXN][4];
	public static int u, f, edge, e;
	public static int stacksize;

	public static void push(int u, int f, int edge, int e) {
		stack[stacksize][0] = u;
		stack[stacksize][1] = f;
		stack[stacksize][2] = edge;
		stack[stacksize][3] = e;
		stacksize++;
	}

	public static void pop() {
		--stacksize;
		u = stack[stacksize][0];
		f = stack[stacksize][1];
		edge = stack[stacksize][2];
		e = stack[stacksize][3];
	}

	public static void addEdge(int u, int v) {
		nextg[++cntg] = headg[u];
		tog[cntg] = v;
		headg[u] = cntg;
	}

	public static void addQuery(int u, int t, int id) {
		nextq[++cntq] = headq[u];
		tim[cntq] = t;
		qid[cntq] = id;
		headq[u] = cntq;
	}

	// 得到子树大小递归版，java会爆栈，C++可以通过
	public static void getSize1(int u, int fa) {
		siz[u] = 1;
		for (int e = headg[u]; e > 0; e = nextg[e]) {
			int v = tog[e];
			if (v != fa && !vis[v]) {
				getSize1(v, u);
				siz[u] += siz[v];
			}
		}
	}

	// 得到子树大小迭代版
	public static void getSize2(int cur, int fa) {
		stacksize = 0;
		push(cur, fa, 0, -1);
		while (stacksize > 0) {
			pop();
			if (e == -1) {
				siz[u] = 1;
				e = headg[u];
			} else {
				e = nextg[e];
			}
			if (e != 0) {
				push(u, f, 0, e);
				int v = tog[e];
				if (v != f && !vis[v]) {
					push(v, u, 0, -1);
				}
			} else {
				for (int ei = headg[u]; ei > 0; ei = nextg[ei]) {
					int v = tog[ei];
					if (v != f && !vis[v]) {
						siz[u] += siz[v];
					}
				}
			}
		}
	}

	public static int getCentroid(int u, int fa) {
		// getSize1(u, fa);
		getSize2(u, fa);
		int half = siz[u] >> 1;
		boolean find = false;
		while (!find) {
			find = true;
			for (int e = headg[u]; e > 0; e = nextg[e]) {
				int v = tog[e];
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

	// 收集信息递归版，java会爆栈，C++可以通过
	public static void dfs1(int u, int fa, int edge) {
		nodeCnt[edge]++;
		maxEdge = Math.max(maxEdge, edge);
		for (int e = headq[u]; e > 0; e = nextq[e]) {
			if (tim[e] >= edge) {
				timArr[++cnta] = tim[e] - edge;
				qidArr[cnta] = qid[e];
			}
		}
		for (int e = headg[u]; e > 0; e = nextg[e]) {
			int v = tog[e];
			if (v != fa && !vis[v]) {
				dfs1(v, u, edge + 1);
			}
		}
	}

	// 收集信息迭代版
	public static void dfs2(int cur, int fa, int edg) {
		stacksize = 0;
		push(cur, fa, edg, -1);
		while (stacksize > 0) {
			pop();
			if (e == -1) {
				nodeCnt[edge]++;
				maxEdge = Math.max(maxEdge, edge);
				for (int e = headq[u]; e > 0; e = nextq[e]) {
					if (tim[e] >= edge) {
						timArr[++cnta] = tim[e] - edge;
						qidArr[cnta] = qid[e];
					}
				}
				e = headg[u];
			} else {
				e = nextg[e];
			}
			if (e != 0) {
				push(u, f, edge, e);
				int v = tog[e];
				if (v != f && !vis[v]) {
					push(tog[e], u, edge + 1, -1);
				}
			}
		}
	}

	public static void calc(int u, int edge, int effect) {
		cnta = 0;
		maxEdge = 0;
		// dfs1(u, 0, edge);
		dfs2(u, 0, edge);
		for (int i = 1; i <= cnta; i++) {
			ans[qidArr[i]] += nodeCnt[timArr[i]] * effect;
		}
		for (int v = 0; v <= maxEdge; v++) {
			nodeCnt[v] = 0;
		}
	}

	public static void solve(int u) {
		vis[u] = true;
		calc(u, 0, 1);
		for (int e = headg[u]; e > 0; e = nextg[e]) {
			int v = tog[e];
			if (!vis[v]) {
				calc(v, 1, -1);
				solve(getCentroid(v, u));
			}
		}
	}

	public static void prepare() {
		cntg = 0;
		cntq = 0;
		for (int i = 1; i <= n; i++) {
			headg[i] = 0;
			headq[i] = 0;
			vis[i] = false;
		}
		for (int i = 1; i <= m; i++) {
			ans[i] = 0;
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		t = in.nextInt();
		for (int c = 1; c <= t; c++) {
			n = in.nextInt();
			m = in.nextInt();
			prepare();
			for (int i = 1, u, v; i < n; i++) {
				u = in.nextInt();
				v = in.nextInt();
				addEdge(u, v);
				addEdge(v, u);
			}
			for (int i = 1, x, k; i <= m; i++) {
				x = in.nextInt();
				k = in.nextInt();
				addQuery(x, k, i);
			}
			solve(getCentroid(1, 0));
			for (int i = 1; i <= m; i++) {
				out.println(ans[i]);
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
