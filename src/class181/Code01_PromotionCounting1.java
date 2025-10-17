package class181;

// 晋升者计数，java版
// 测试链接 : https://www.luogu.com.cn/problem/P3605
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

public class Code01_PromotionCounting1 {

	public static int MAXN = 100001;
	public static int MAXT = MAXN * 40;
	public static int n;

	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXN << 1];
	public static int[] to = new int[MAXN << 1];
	public static int cntg;

	public static int[] arr = new int[MAXN];
	public static int[] sorted = new int[MAXN];
	public static int cntv;

	public static int[] root = new int[MAXN];
	public static int[] ls = new int[MAXT];
	public static int[] rs = new int[MAXT];
	public static int[] siz = new int[MAXT];
	public static int cntt;

	public static int[] ans = new int[MAXN];

	public static void addEdge(int u, int v) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		head[u] = cntg;
	}

	public static int kth(int num) {
		int left = 1, right = cntv, mid, ret = 0;
		while (left <= right) {
			mid = (left + right) / 2;
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
		siz[i] = siz[ls[i]] + siz[rs[i]];
	}

	public static int insert(int jobi, int l, int r, int i) {
		int rt = i;
		if (rt == 0) {
			rt = ++cntt;
		}
		if (l == r) {
			siz[rt]++;
		} else {
			int mid = (l + r) / 2;
			if (jobi <= mid) {
				ls[rt] = insert(jobi, l, mid, ls[rt]);
			} else {
				rs[rt] = insert(jobi, mid + 1, r, rs[rt]);
			}
			up(rt);
		}
		return rt;
	}

	public static int merge(int l, int r, int t1, int t2) {
		if (t1 == 0 || t2 == 0) {
			return t1 + t2;
		}
		if (l == r) {
			siz[t1] += siz[t2];
		} else {
			int mid = (l + r) / 2;
			ls[t1] = merge(l, mid, ls[t1], ls[t2]);
			rs[t1] = merge(mid + 1, r, rs[t1], rs[t2]);
			up(t1);
		}
		return t1;
	}

	public static int query(int jobl, int jobr, int l, int r, int i) {
		if (jobl > jobr || i == 0) {
			return 0;
		}
		if (jobl <= l && r <= jobr) {
			return siz[i];
		}
		int mid = (l + r) / 2;
		int ret = 0;
		if (jobl <= mid) {
			ret += query(jobl, jobr, l, mid, ls[i]);
		}
		if (jobr > mid) {
			ret += query(jobl, jobr, mid + 1, r, rs[i]);
		}
		return ret;
	}

	// 递归版，java会爆栈，C++可以通过
	public static void dfs1(int u, int fa) {
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (v != fa) {
				dfs1(v, u);
			}
		}
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (v != fa) {
				root[u] = merge(1, cntv, root[u], root[v]);
			}
		}
		ans[u] = query(arr[u] + 1, cntv, 1, cntv, root[u]);
	}

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

	// dfs1改的迭代版
	public static void dfs2() {
		stacksize = 0;
		push(1, 0, -1);
		while (stacksize > 0) {
			pop();
			if (e == -1) {
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
						root[u] = merge(1, cntv, root[u], root[v]);
					}
				}
				ans[u] = query(arr[u] + 1, cntv, 1, cntv, root[u]);
			}
		}
	}

	public static void compute() {
		for (int i = 1; i <= n; i++) {
			sorted[i] = arr[i];
		}
		Arrays.sort(sorted, 1, n + 1);
		cntv = 1;
		for (int i = 2; i <= n; i++) {
			if (sorted[cntv] != sorted[i]) {
				sorted[++cntv] = sorted[i];
			}
		}
		for (int i = 1; i <= n; i++) {
			arr[i] = kth(arr[i]);
		}
		for (int i = 1; i <= n; i++) {
			root[i] = insert(arr[i], 1, cntv, root[i]);
		}
		// dfs1(1, 0);
		dfs2();
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		for (int i = 1; i <= n; i++) {
			arr[i] = in.nextInt();
		}
		for (int i = 2, fa; i <= n; i++) {
			fa = in.nextInt();
			addEdge(fa, i);
			addEdge(i, fa);
		}
		compute();
		for (int i = 1; i <= n; i++) {
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
