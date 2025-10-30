package class182;

// 根节点的概率，java版
// 测试链接 : https://www.luogu.com.cn/problem/P5298
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

public class Code02_Minimax1 {

	public static int MAXN = 300001;
	public static int MAXT = MAXN * 40;
	public static final int MOD = 998244353;
	public static int n;

	public static int[] fa = new int[MAXN];
	public static int[] val = new int[MAXN];
	public static int[] sorted = new int[MAXN];
	public static int cntv;

	public static int[] childCnt = new int[MAXN];
	public static int[][] child = new int[MAXN][2];

	public static int[] root = new int[MAXN];
	public static int[] ls = new int[MAXT];
	public static int[] rs = new int[MAXT];
	public static long[] sum = new long[MAXT];
	public static long[] mul = new long[MAXT];
	public static int cntt;

	public static long[] d = new long[MAXN];

	public static long power(long x, int p) {
		long ans = 1;
		while (p != 0) {
			if ((p & 1) != 0) {
				ans = ans * x % MOD;
			}
			p >>= 1;
			x = x * x % MOD;
		}
		return ans;
	}

	public static int kth(int num) {
		int left = 1, right = cntv, mid, ret = 0;
		while (left <= right) {
			mid = (left + right) >> 1;
			if (sorted[mid] <= num) {
				ret = mid;
				left = mid + 1;
			} else {
				right = mid - 1;
			}
		}
		return ret;
	}

	public static void up(int i) {
		sum[i] = (sum[ls[i]] + sum[rs[i]]) % MOD;
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

	public static int insert(int jobi, int l, int r, int i) {
		int rt = i;
		if (rt == 0) {
			rt = ++cntt;
			mul[rt] = 1;
		}
		if (l == r) {
			sum[rt] = 1;
		} else {
			down(rt);
			int mid = (l + r) >> 1;
			if (jobi <= mid) {
				ls[rt] = insert(jobi, l, mid, ls[rt]);
			} else {
				rs[rt] = insert(jobi, mid + 1, r, rs[rt]);
			}
			up(rt);
		}
		return rt;
	}

	public static long p, mul1, mul2;

	public static int merge(int l, int r, int t1, int t2) {
		if (t1 == 0 || t2 == 0) {
			if (t1 != 0) {
				lazy(t1, mul2);
			}
			if (t2 != 0) {
				lazy(t2, mul1);
			}
			return t1 + t2;
		}
		if (l == r) {
			sum[t1] = (sum[t1] * mul2 % MOD + sum[t2] * mul1 % MOD) % MOD;
		} else {
			down(t1);
			down(t2);
			int mid = (l + r) >> 1;
			long lsum1 = sum[ls[t1]];
			long rsum1 = sum[rs[t1]];
			long lsum2 = sum[ls[t2]];
			long rsum2 = sum[rs[t2]];
			long m1 = mul1;
			long m2 = mul2;
			mul1 = (m1 + rsum1 * (1 - p + MOD) % MOD) % MOD;
			mul2 = (m2 + rsum2 * (1 - p + MOD) % MOD) % MOD;
			ls[t1] = merge(l, mid, ls[t1], ls[t2]);
			mul1 = (m1 + lsum1 * p) % MOD;
			mul2 = (m2 + lsum2 * p) % MOD;
			rs[t1] = merge(mid + 1, r, rs[t1], rs[t2]);
			up(t1);
		}
		return t1;
	}

	// 迭代版，java会爆栈，C++可以通过
	public static void dfs1(int u) {
		if (childCnt[u] == 0) {
			root[u] = insert(val[u], 1, cntv, root[u]);
		} else if (childCnt[u] == 1) {
			dfs1(child[u][0]);
			root[u] = root[child[u][0]];
		} else {
			dfs1(child[u][0]);
			dfs1(child[u][1]);
			p = val[u];
			mul1 = mul2 = 0;
			root[u] = merge(1, cntv, root[child[u][0]], root[child[u][1]]);
		}
	}

	// dfs1改成迭代版
	public static void dfs2() {
		int[][] stack = new int[n + 1][2];
		int siz = 0;
		stack[++siz][0] = 1;
		stack[siz][1] = 0;
		while (siz > 0) {
			int u = stack[siz][0];
			int s = stack[siz--][1];
			if (childCnt[u] == 0) {
				root[u] = insert(val[u], 1, cntv, root[u]);
			} else if (childCnt[u] == 1) {
				if (s == 0) {
					stack[++siz][0] = u;
					stack[siz][1] = 1;
					stack[++siz][0] = child[u][0];
					stack[siz][1] = 0;
				} else {
					root[u] = root[child[u][0]];
				}
			} else {
				if (s == 0) {
					stack[++siz][0] = u;
					stack[siz][1] = 1;
					stack[++siz][0] = child[u][1];
					stack[siz][1] = 0;
					stack[++siz][0] = child[u][0];
					stack[siz][1] = 0;
				} else {
					p = val[u];
					mul1 = mul2 = 0;
					root[u] = merge(1, cntv, root[child[u][0]], root[child[u][1]]);
				}
			}
		}
	}

	public static void getd(int l, int r, int i) {
		if (i == 0) {
			return;
		}
		if (l == r) {
			d[l] = sum[i] % MOD;
		} else {
			down(i);
			int mid = (l + r) >> 1;
			getd(l, mid, ls[i]);
			getd(mid + 1, r, rs[i]);
		}
	}

	public static void prepare() {
		for (int i = 1; i <= n; i++) {
			if (fa[i] != 0) {
				child[fa[i]][childCnt[fa[i]]++] = i;
			}
		}
		long inv = power(10000, MOD - 2);
		for (int i = 1; i <= n; i++) {
			if (childCnt[i] == 0) {
				sorted[++cntv] = val[i];
			} else {
				val[i] = (int) (inv * val[i] % MOD);
			}
		}
		Arrays.sort(sorted, 1, cntv + 1);
		int len = 1;
		for (int i = 2; i <= cntv; i++) {
			if (sorted[len] != sorted[i]) {
				sorted[++len] = sorted[i];
			}
		}
		cntv = len;
		for (int i = 1; i <= n; i++) {
			if (childCnt[i] == 0) {
				val[i] = kth(val[i]);
			}
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		for (int i = 1; i <= n; i++) {
			fa[i] = in.nextInt();
		}
		for (int i = 1; i <= n; i++) {
			val[i] = in.nextInt();
		}
		prepare();
		// dfs1(1);
		dfs2();
		getd(1, cntv, root[1]);
		long ans = 0;
		for (int i = 1; i <= cntv; i++) {
			ans = (ans + 1L * i * sorted[i] % MOD * d[i] % MOD * d[i]) % MOD;
		}
		out.println(ans);
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
