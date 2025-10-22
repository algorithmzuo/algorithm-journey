package class182;

// 命运，java版
// 测试链接 : https://www.luogu.com.cn/problem/P6773
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;

public class Code03_Fate1 {

	public static int MAXN = 500001;
	public static int MAXT = MAXN * 40;
	public static int MOD = 998244353;
	public static int n, m;

	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXN << 1];
	public static int[] to = new int[MAXN << 1];
	public static int cntg;

	public static int[] root = new int[MAXN];
	public static int[] ls = new int[MAXT];
	public static int[] rs = new int[MAXT];
	public static long[] sum = new long[MAXT];
	public static long[] mul = new long[MAXT];
	public static int cntt;

	public static int[] dep = new int[MAXN];
	public static int[] maxdep = new int[MAXN];

	// 讲解118，递归改迭代需要的栈
	public static int[][] ufe = new int[MAXN][3];
	public static int stacksize, u, f, e;

	public static void push(int u, int f, int e) {
		ufe[stacksize][0] = u;
		ufe[stacksize][1] = f;
		ufe[stacksize][2] = e;
		stacksize++;
	}

	public static void pop() {
		--stacksize;
		u = ufe[stacksize][0];
		f = ufe[stacksize][1];
		e = ufe[stacksize][2];
	}

	public static void addEdge(int u, int v) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		head[u] = cntg;
	}

	public static void lazy(int i, long v) {
		if (i != 0) {
			sum[i] = sum[i] * v % MOD;
			mul[i] = mul[i] * v % MOD;
		}
	}

	public static void down(int i) {
		if (mul[i] != 1) {
			lazy(ls[i], mul[i]);
			lazy(rs[i], mul[i]);
			mul[i] = 1;
		}
	}

	public static int build(int jobi, int l, int r) {
		int rt = ++cntt;
		sum[rt] = 1;
		mul[rt] = 1;
		if (l < r) {
			int mid = (l + r) >> 1;
			if (jobi <= mid) {
				ls[rt] = build(jobi, l, mid);
			} else {
				rs[rt] = build(jobi, mid + 1, r);
			}
		}
		return rt;
	}

	public static long sum1, sum2;

	public static int merge(int l, int r, int t1, int t2) {
		if (t1 == 0 || t2 == 0) {
			if (t1 != 0) {
				sum2 = (sum2 + sum[t1]) % MOD;
				lazy(t1, sum1);
			}
			if (t2 != 0) {
				sum1 = (sum1 + sum[t2]) % MOD;
				lazy(t2, sum2);
			}
			return t1 + t2;
		}
		if (l == r) {
			sum1 = (sum1 + sum[t2]) % MOD;
			long tmp = sum[t1];
			sum[t1] = ((sum[t1] * sum1) % MOD + (sum[t2] * sum2) % MOD) % MOD;
			sum2 = (sum2 + tmp) % MOD;
		} else {
			down(t1);
			down(t2);
			int mid = (l + r) >> 1;
			ls[t1] = merge(l, mid, ls[t1], ls[t2]);
			rs[t1] = merge(mid + 1, r, rs[t1], rs[t2]);
			sum[t1] = (sum[ls[t1]] + sum[rs[t1]]) % MOD;
		}
		return t1;
	}

	public static long query(int jobl, int jobr, int l, int r, int i) {
		if (i == 0) {
			return 0;
		}
		if (jobl <= l && r <= jobr) {
			return sum[i] % MOD;
		}
		down(i);
		int mid = (l + r) >> 1;
		long ans = 0;
		if (jobl <= mid) {
			ans = query(jobl, jobr, l, mid, ls[i]);
		}
		if (jobr > mid) {
			ans = (ans + query(jobl, jobr, mid + 1, r, rs[i])) % MOD;
		}
		return ans;
	}

	// 递归版，java会爆栈，C++可以通过
	public static void dfs1(int u, int fa) {
		dep[u] = dep[fa] + 1;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (v != fa) {
				dfs1(v, u);
			}
		}
	}

	// dfs1的迭代版
	public static void dfs2() {
		stacksize = 0;
		push(1, 0, -1);
		while (stacksize > 0) {
			pop();
			if (e == -1) {
				dep[u] = dep[f] + 1;
				e = head[u];
			} else {
				e = nxt[e];
			}
			if (e != 0) {
				push(u, f, e);
				if (to[e] != f) {
					push(to[e], u, -1);
				}
			}
		}
	}

	// 递归版，java会爆栈，C++可以通过
	public static void dp1(int u, int fa) {
		root[u] = build(maxdep[u], 0, n);
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (v != fa) {
				dp1(v, u);
			}
		}
		for (int ei = head[u]; ei > 0; ei = nxt[ei]) {
			int v = to[ei];
			if (v != fa) {
				sum1 = query(0, dep[u], 0, n, root[v]);
				sum2 = 0;
				root[u] = merge(0, n, root[u], root[v]);
			}
		}
	}

	// dp1的迭代版
	public static void dp2() {
		stacksize = 0;
		push(1, 0, -1);
		while (stacksize > 0) {
			pop();
			if (e == -1) {
				root[u] = build(maxdep[u], 0, n);
				e = head[u];
			} else {
				e = nxt[e];
			}
			if (e != 0) {
				push(u, f, e);
				if (to[e] != f) {
					push(to[e], u, -1);
				}
			} else {
				for (int ei = head[u]; ei > 0; ei = nxt[ei]) {
					int v = to[ei];
					if (v != f) {
						sum1 = query(0, dep[u], 0, n, root[v]);
						sum2 = 0;
						root[u] = merge(0, n, root[u], root[v]);
					}
				}
			}
		}
	}

	public static void main(String[] args) throws IOException {
		FastReader in = new FastReader(System.in);
		n = in.nextInt();
		for (int i = 1, u, v; i < n; i++) {
			u = in.nextInt();
			v = in.nextInt();
			addEdge(u, v);
			addEdge(v, u);
		}
		// dfs1(1, 0);
		dfs2();
		m = in.nextInt();
		for (int i = 1, x, y; i <= m; i++) {
			x = in.nextInt();
			y = in.nextInt();
			maxdep[y] = Math.max(maxdep[y], dep[x]);
		}
		// dp1(1, 0);
		dp2();
		long ans = query(0, 0, 0, n, root[1]) % MOD;
		System.out.println(ans);
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