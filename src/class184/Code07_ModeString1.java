package class184;

// 模式字符串，java版
// 一共有n个点，给定n-1条边，所有节点组成一棵树
// 每个点有点权，是一个大写字母，只考虑大写字母A到Z
// 给定一个长度为m的字符串s，也只由大写字母A到Z组成
// 考虑点对(u, v)的简单路径，把沿途节点的字母拼接起来
// 如果拼接字符串恰好是s重复正数次，那么该点对合法
// 打印合法点对的数量，注意(u, v)和(v, u)是不同的点对
// 1 <= m <= n <= 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P4075
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code07_ModeString1 {

	public static int MAXN = 100001;
	public static final int BASE = 499;
	public static int t, n, m;

	public static char[] val = new char[MAXN];
	public static char[] str = new char[MAXN];
	public static int[] a = new int[MAXN];
	public static int[] b = new int[MAXN];

	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXN << 1];
	public static int[] to = new int[MAXN << 1];
	public static int cntg;

	public static long[] pre = new long[MAXN];
	public static long[] suf = new long[MAXN];

	public static boolean[] vis = new boolean[MAXN];
	public static int[] siz = new int[MAXN];

	public static int[] deep = new int[MAXN];
	public static long[] curp = new long[MAXN];
	public static long[] curs = new long[MAXN];
	public static long[] allp = new long[MAXN];
	public static long[] alls = new long[MAXN];

	public static long ans;

	// 讲解118，递归函数改成迭代所需要的栈
	public static int[][] stack = new int[MAXN][4];
	public static long[] hashst = new long[MAXN];
	public static int u, f, dep, e;
	public static long hash;
	public static int stacksize;

	public static void push(int u, int f, int dep, long hash, int e) {
		stack[stacksize][0] = u;
		stack[stacksize][1] = f;
		stack[stacksize][2] = dep;
		stack[stacksize][3] = e;
		hashst[stacksize] = hash;
		stacksize++;
	}

	public static void pop() {
		--stacksize;
		u = stack[stacksize][0];
		f = stack[stacksize][1];
		dep = stack[stacksize][2];
		e = stack[stacksize][3];
		hash = hashst[stacksize];
	}

	public static void addEdge(int u, int v) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		head[u] = cntg;
	}

	// 得到子树大小递归版，java会爆栈，C++可以通过
	public static void getSize1(int u, int fa) {
		siz[u] = 1;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (v != fa && !vis[v]) {
				getSize1(v, u);
				siz[u] += siz[v];
			}
		}
	}

	// 得到子树大小迭代版
	public static void getSize2(int cur, int fa) {
		stacksize = 0;
		push(cur, fa, 0, 0, -1);
		while (stacksize > 0) {
			pop();
			if (e == -1) {
				siz[u] = 1;
				e = head[u];
			} else {
				e = nxt[e];
			}
			if (e != 0) {
				push(u, f, 0, 0, e);
				int v = to[e];
				if (v != f && !vis[v]) {
					push(v, u, 0, 0, -1);
				}
			} else {
				for (int ei = head[u]; ei > 0; ei = nxt[ei]) {
					int v = to[ei];
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

	// 收集信息 + 结算答案递归版，java会爆栈，C++可以通过
	public static void dfs1(int u, int fa, int dep, long hash) {
		deep[u] = dep;
		hash = hash * BASE + val[u] - 'A' + 1;
		if (hash == pre[dep]) {
			curp[(dep - 1) % m + 1]++;
			ans += alls[m - (dep - 1) % m];
		}
		if (hash == suf[dep]) {
			curs[(dep - 1) % m + 1]++;
			ans += allp[m - (dep - 1) % m];
		}
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (v != fa && !vis[v]) {
				dfs1(v, u, dep + 1, hash);
				deep[u] = Math.max(deep[u], deep[v]);
			}
		}
	}

	// 收集信息 + 结算答案迭代版
	public static void dfs2(int cur, int fa, int pdep, long phash) {
		stacksize = 0;
		push(cur, fa, pdep, phash, -1);
		while (stacksize > 0) {
			pop();
			if (e == -1) {
				deep[u] = dep;
				hash = hash * BASE + val[u] - 'A' + 1;
				if (hash == pre[dep]) {
					curp[(dep - 1) % m + 1]++;
					ans += alls[m - (dep - 1) % m];
				}
				if (hash == suf[dep]) {
					curs[(dep - 1) % m + 1]++;
					ans += allp[m - (dep - 1) % m];
				}
				e = head[u];
			} else {
				e = nxt[e];
			}
			if (e != 0) {
				push(u, f, dep, hash, e);
				int v = to[e];
				if (v != f && !vis[v]) {
					push(v, u, dep + 1, hash, -1);
				}
			} else {
				for (int ei = head[u]; ei > 0; ei = nxt[ei]) {
					int v = to[ei];
					if (v != f && !vis[v]) {
						deep[u] = Math.max(deep[u], deep[v]);
					}
				}
			}
		}
	}

	public static void calc(int u) {
		int maxDep = 0;
		allp[1] = alls[1] = 1;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (!vis[v]) {
				// dfs1(v, u, 2, arr[u] - 'A' + 1);
				dfs2(v, u, 2, val[u] - 'A' + 1);
				int curDep = Math.min(deep[v], m);
				for (int i = 1; i <= curDep; i++) {
					allp[i] += curp[i];
					alls[i] += curs[i];
					curp[i] = curs[i] = 0;
				}
				maxDep = Math.max(maxDep, curDep);
			}
		}
		for (int i = 1; i <= maxDep; i++) {
			allp[i] = alls[i] = 0;
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

	public static void prepare() {
		cntg = 0;
		ans = 0;
		for (int i = 1; i <= n; i++) {
			head[i] = 0;
			vis[i] = false;
		}
		for (int i = 1; i < n; i++) {
			addEdge(a[i], b[i]);
			addEdge(b[i], a[i]);
		}
		long tmp = 1;
		for (int i = 1; i <= n; i++) {
			pre[i] = pre[i - 1] + tmp * (str[(i - 1) % m + 1] - 'A' + 1);
			suf[i] = suf[i - 1] + tmp * (str[m - (i - 1) % m] - 'A' + 1);
			tmp = tmp * BASE;
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		t = in.nextInt();
		for (int k = 1; k <= t; k++) {
			n = in.nextInt();
			m = in.nextInt();
			for (int i = 1; i <= n; i++) {
				val[i] = in.nextUpperCase();
			}
			for (int i = 1; i < n; i++) {
				a[i] = in.nextInt();
				b[i] = in.nextInt();
			}
			for (int i = 1; i <= m; i++) {
				str[i] = in.nextUpperCase();
			}
			prepare();
			solve(getCentroid(1, 0));
			out.println(ans);
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

		private boolean hasNextByte() throws IOException {
			if (ptr < len)
				return true;
			ptr = 0;
			len = in.read(buffer);
			return len > 0;
		}

		private byte readByte() throws IOException {
			if (!hasNextByte())
				return -1;
			return buffer[ptr++];
		}

		public char nextUpperCase() throws IOException {
			int c;
			while (true) {
				c = readByte();
				if (c >= 'A' && c <= 'Z')
					return (char) c;
			}
		}

		public int nextInt() throws IOException {
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
