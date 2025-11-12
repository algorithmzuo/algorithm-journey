package class183;

// 消息传递，java版
// 测试链接 : https://www.luogu.com.cn/problem/P6626
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code03_Message1 {

	public static int MAXN = 100001;
	public static int t, n, m, total;

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
	public static int[] maxPart = new int[MAXN];
	public static int centroid;

	public static int[] dep = new int[MAXN];
	public static int[] depCnt = new int[MAXN];
	public static int maxDeep;

	public static int[] timArr = new int[MAXN];
	public static int[] qidArr = new int[MAXN];
	public static int cnta;

	public static int[] ans = new int[MAXN];

	// 讲解118，递归函数改成迭代所需要的栈
	public static int[][] stack = new int[MAXN][3];
	public static int stacksize, u, f, e;

	public static void push(int u, int f, int e) {
		stack[stacksize][0] = u;
		stack[stacksize][1] = f;
		stack[stacksize][2] = e;
		stacksize++;
	}

	public static void pop() {
		--stacksize;
		u = stack[stacksize][0];
		f = stack[stacksize][1];
		e = stack[stacksize][2];
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

	// 找重心的递归版，java会爆栈，C++可以通过
	public static void getCentroid1(int u, int fa) {
		siz[u] = 1;
		maxPart[u] = 0;
		for (int e = headg[u]; e > 0; e = nextg[e]) {
			int v = tog[e];
			if (v != fa && !vis[v]) {
				getCentroid1(v, u);
				siz[u] += siz[v];
				maxPart[u] = Math.max(siz[v], maxPart[u]);
			}
		}
		maxPart[u] = Math.max(maxPart[u], total - siz[u]);
		if (centroid == 0 || maxPart[u] < maxPart[centroid]) {
			centroid = u;
		}
	}

	// 找重心的迭代版
	public static void getCentroid2(int cur, int fa) {
		stacksize = 0;
		push(cur, fa, -1);
		while (stacksize > 0) {
			pop();
			if (e == -1) {
				siz[u] = 1;
				maxPart[u] = 0;
				e = headg[u];
			} else {
				e = nextg[e];
			}
			if (e != 0) {
				push(u, f, e);
				int v = tog[e];
				if (v != f && !vis[v]) {
					push(tog[e], u, -1);
				}
			} else {
				for (int ei = headg[u]; ei > 0; ei = nextg[ei]) {
					int v = tog[ei];
					if (v != f && !vis[v]) {
						siz[u] += siz[v];
						maxPart[u] = Math.max(siz[v], maxPart[u]);
					}
				}
				maxPart[u] = Math.max(maxPart[u], total - siz[u]);
				if (centroid == 0 || maxPart[u] < maxPart[centroid]) {
					centroid = u;
				}
			}
		}
	}

	// 收集信息递归版，java会爆栈，C++可以通过
	public static void dfs1(int u, int fa) {
		dep[u] = dep[fa] + 1;
		depCnt[dep[u]]++;
		maxDeep = Math.max(maxDeep, dep[u]);
		for (int e = headq[u]; e > 0; e = nextq[e]) {
			if (tim[e] + 1 >= dep[u]) {
				timArr[++cnta] = tim[e] - dep[u] + 2;
				qidArr[cnta] = qid[e];
			}
		}
		for (int e = headg[u]; e > 0; e = nextg[e]) {
			int v = tog[e];
			if (v != fa && !vis[v]) {
				dfs1(v, u);
			}
		}
	}

	// 收集信息迭代版
	public static void dfs2(int cur, int fa) {
		stacksize = 0;
		push(cur, fa, -1);
		while (stacksize > 0) {
			pop();
			if (e == -1) {
				dep[u] = dep[f] + 1;
				depCnt[dep[u]]++;
				maxDeep = Math.max(maxDeep, dep[u]);
				for (int e = headq[u]; e > 0; e = nextq[e]) {
					if (tim[e] + 1 >= dep[u]) {
						timArr[++cnta] = tim[e] - dep[u] + 2;
						qidArr[cnta] = qid[e];
					}
				}
				e = headg[u];
			} else {
				e = nextg[e];
			}
			if (e != 0) {
				push(u, f, e);
				int v = tog[e];
				if (v != f && !vis[v]) {
					push(tog[e], u, -1);
				}
			}
		}
	}

	public static void calc(int u) {
		cnta = 0;
		maxDeep = 0;
		// dfs1(u, 0);
		dfs2(u, 0);
		for (int i = 1; i <= cnta; i++) {
			ans[qidArr[i]] += depCnt[timArr[i]];
		}
		for (int d = 1; d <= maxDeep; d++) {
			depCnt[d] = 0;
		}
		for (int e = headg[u]; e > 0; e = nextg[e]) {
			int v = tog[e];
			if (!vis[v]) {
				cnta = 0;
				maxDeep = 0;
				// dfs1(v, u);
				dfs2(v, u);
				for (int i = 1; i <= cnta; i++) {
					ans[qidArr[i]] -= depCnt[timArr[i]];
				}
				for (int d = 1; d <= maxDeep; d++) {
					depCnt[d] = 0;
				}
			}
		}
	}

	public static void solve(int u) {
		vis[u] = true;
		calc(u);
		for (int e = headg[u]; e > 0; e = nextg[e]) {
			int v = tog[e];
			if (!vis[v]) {
				total = siz[v];
				centroid = 0;
				// getCentroid1(v, u);
				getCentroid2(v, u);
				solve(centroid);
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
			total = n;
			centroid = 0;
			// getCentroid1(1, 0);
			getCentroid2(1, 0);
			solve(centroid);
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
