package class182;

// 根节点的取值，java版
// 一共有n个节点，1号节点是整棵树的头，所有节点组成一棵树
// 给定一个长度为n的数组arr，如果节点x是叶，那么arr[x]表示点权，所有叶节点的点权都不同
// 如果节点x不是叶，那么它最多有两个孩子，此时arr[x]代表概率，节点x按照以下规则取得点权
// 以arr[x]的概率是所有儿子的点权最大值，以1 - arr[x]的概率是所有儿子的点权最小值
// 表示概率时，arr[x]的范围是[1, 9999]，表示概率 0.0001 ~ 0.9999
// 假设1号结点的权值有m种可能性，第i小的权值是Vi，取得概率为Di
// 计算 i = 1..m 时，每一项 (i * Vi * Di * Di) 的累加和，答案对 998244353 取模
// 1 <= n <= 3 * 10^5    1 <= 叶节点权值 <= 10^9    1 <= 概率 <= 9999
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
	public static int[] arr = new int[MAXN];
	public static int[] sorted = new int[MAXN];
	public static int cntv;

	public static int[] sonCnt = new int[MAXN];
	public static int[][] son = new int[MAXN][2];

	public static int[] root = new int[MAXN];
	public static int[] ls = new int[MAXT];
	public static int[] rs = new int[MAXT];
	public static long[] sum = new long[MAXT];
	public static long[] mulLazy = new long[MAXT];
	public static int cntt;

	public static long[] D = new long[MAXN];

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
			mulLazy[i] = mulLazy[i] * v % MOD;
		}
	}

	public static void down(int i) {
		if (mulLazy[i] != 1) {
			lazy(ls[i], mulLazy[i]);
			lazy(rs[i], mulLazy[i]);
			mulLazy[i] = 1;
		}
	}

	public static int insert(int jobi, int l, int r, int i) {
		int rt = i;
		if (rt == 0) {
			rt = ++cntt;
			mulLazy[rt] = 1;
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

	public static int merge(int l, int r, int t1, int t2, int p, long sum1, long sum2) {
		if (t1 == 0 || t2 == 0) {
			if (t1 != 0) {
				lazy(t1, sum2);
			}
			if (t2 != 0) {
				lazy(t2, sum1);
			}
			return t1 + t2;
		}
		if (l == r) {
			sum[t1] = (sum[t1] * sum2 % MOD + sum[t2] * sum1 % MOD) % MOD;
		} else {
			down(t1);
			down(t2);
			int mid = (l + r) >> 1;
			long lsum1 = (sum1 + sum[rs[t1]] * (1 - p + MOD)) % MOD;
			long lsum2 = (sum2 + sum[rs[t2]] * (1 - p + MOD)) % MOD;
			long rsum1 = (sum1 + sum[ls[t1]] * p) % MOD;
			long rsum2 = (sum2 + sum[ls[t2]] * p) % MOD;
			ls[t1] = merge(l, mid, ls[t1], ls[t2], p, lsum1, lsum2);
			rs[t1] = merge(mid + 1, r, rs[t1], rs[t2], p, rsum1, rsum2);
			up(t1);
		}
		return t1;
	}

	// 迭代版，java会爆栈，C++可以通过
	public static void dp1(int u) {
		if (sonCnt[u] == 0) {
			root[u] = insert(arr[u], 1, cntv, root[u]);
		} else if (sonCnt[u] == 1) {
			dp1(son[u][0]);
			root[u] = root[son[u][0]];
		} else {
			dp1(son[u][0]);
			dp1(son[u][1]);
			root[u] = merge(1, cntv, root[son[u][0]], root[son[u][1]], arr[u], 0, 0);
		}
	}

	// dp1改成迭代版
	public static void dp2() {
		int[][] stack = new int[n + 1][2];
		int siz = 0;
		stack[++siz][0] = 1;
		stack[siz][1] = 0;
		while (siz > 0) {
			int u = stack[siz][0];
			int s = stack[siz--][1];
			if (sonCnt[u] == 0) {
				root[u] = insert(arr[u], 1, cntv, root[u]);
			} else if (sonCnt[u] == 1) {
				if (s == 0) {
					stack[++siz][0] = u;
					stack[siz][1] = 1;
					stack[++siz][0] = son[u][0];
					stack[siz][1] = 0;
				} else {
					root[u] = root[son[u][0]];
				}
			} else {
				if (s == 0) {
					stack[++siz][0] = u;
					stack[siz][1] = 1;
					stack[++siz][0] = son[u][1];
					stack[siz][1] = 0;
					stack[++siz][0] = son[u][0];
					stack[siz][1] = 0;
				} else {
					root[u] = merge(1, cntv, root[son[u][0]], root[son[u][1]], arr[u], 0, 0);
				}
			}
		}
	}

	public static void getd(int l, int r, int i) {
		if (i == 0) {
			return;
		}
		if (l == r) {
			D[l] = sum[i] % MOD;
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
				son[fa[i]][sonCnt[fa[i]]++] = i;
			}
		}
		long inv = power(10000, MOD - 2);
		for (int i = 1; i <= n; i++) {
			if (sonCnt[i] == 0) {
				sorted[++cntv] = arr[i];
			} else {
				arr[i] = (int) (inv * arr[i] % MOD);
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
			if (sonCnt[i] == 0) {
				arr[i] = kth(arr[i]);
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
			arr[i] = in.nextInt();
		}
		prepare();
		// dp1(1);
		dp2();
		getd(1, cntv, root[1]);
		long ans = 0;
		for (int i = 1; i <= cntv; i++) {
			ans = (ans + 1L * i * sorted[i] % MOD * D[i] % MOD * D[i]) % MOD;
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
