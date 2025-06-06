package class171;

// 寻找宝藏，java版
// 测试链接 : https://www.luogu.com.cn/problem/P4849
// 提交以下的code，提交时请把类名改成"Main"，多提交几次，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;

public class Code06_Treasure1 {

	public static int MAXN = 80001;
	public static long INF = (long) (1e18 + 1);
	public static int MOD = 998244353;

	public static class Node {
		int a, b, c, d;
		int i;
		long v;
		boolean left;

		public Node(int a_, int b_, int c_, int d_, long v_) {
			a = a_;
			b = b_;
			c = c_;
			d = d_;
			v = v_;
		}
	}

	public static class Cmp1 implements Comparator<Node> {
		@Override
		public int compare(Node x, Node y) {
			if (x.a != y.a) {
				return x.a - y.a;
			}
			if (x.b != y.b) {
				return x.b - y.b;
			}
			if (x.c != y.c) {
				return x.c - y.c;
			}
			if (x.d != y.d) {
				return x.d - y.d;
			}
			return Long.compare(y.v, x.v);
		}
	}

	public static class Cmp2 implements Comparator<Node> {
		@Override
		public int compare(Node x, Node y) {
			if (x.b != y.b) {
				return x.b - y.b;
			}
			if (x.c != y.c) {
				return x.c - y.c;
			}
			if (x.d != y.d) {
				return x.d - y.d;
			}
			return x.a - y.a;
		}
	}

	public static class Cmp3 implements Comparator<Node> {
		@Override
		public int compare(Node x, Node y) {
			if (x.c != y.c) {
				return x.c - y.c;
			}
			if (x.d != y.d) {
				return x.d - y.d;
			}
			if (x.a != y.a) {
				return x.a - y.a;
			}
			return x.b - y.b;
		}
	}

	public static Cmp1 cmp1 = new Cmp1();
	public static Cmp2 cmp2 = new Cmp2();
	public static Cmp3 cmp3 = new Cmp3();

	public static int n, s;
	public static Node[] arr = new Node[MAXN];
	public static Node[] tmp1 = new Node[MAXN];
	public static Node[] tmp2 = new Node[MAXN];
	public static int[] sortd = new int[MAXN];

	public static long[] treeVal = new long[MAXN];
	public static int[] treeCnt = new int[MAXN];

	public static long[] dp = new long[MAXN];
	public static int[] cnt = new int[MAXN];

	public static int lowbit(int i) {
		return i & -i;
	}

	public static void add(int i, long v, int c) {
		while (i <= s) {
			if (v > treeVal[i]) {
				treeVal[i] = v;
				treeCnt[i] = c % MOD;
			} else if (v == treeVal[i]) {
				treeCnt[i] = (treeCnt[i] + c) % MOD;
			}
			i += lowbit(i);
		}
	}

	public static long queryVal;
	public static int queryCnt;

	public static void query(int i) {
		queryVal = -INF;
		queryCnt = 0;
		while (i > 0) {
			if (treeVal[i] > queryVal) {
				queryVal = treeVal[i];
				queryCnt = treeCnt[i];
			} else if (treeVal[i] == queryVal) {
				queryCnt = (queryCnt + treeCnt[i]) % MOD;
			}
			i -= lowbit(i);
		}
	}

	public static void clear(int i) {
		while (i <= s) {
			treeVal[i] = -INF;
			treeCnt[i] = 0;
			i += lowbit(i);
		}
	}

	public static void merge(int l, int mid, int r) {
		for (int i = l; i <= r; i++) {
			tmp2[i] = tmp1[i];
		}
		Arrays.sort(tmp2, l, mid + 1, cmp3);
		Arrays.sort(tmp2, mid + 1, r + 1, cmp3);
		int p1, p2, id;
		for (p1 = l - 1, p2 = mid + 1; p2 <= r; p2++) {
			while (p1 + 1 <= mid && tmp2[p1 + 1].c <= tmp2[p2].c) {
				p1++;
				if (tmp2[p1].left) {
					add(tmp2[p1].d, dp[tmp2[p1].i], cnt[tmp2[p1].i]);
				}
			}
			if (!tmp2[p2].left) {
				query(tmp2[p2].d);
				id = tmp2[p2].i;
				if (queryVal + tmp2[p2].v > dp[id]) {
					dp[id] = queryVal + tmp2[p2].v;
					cnt[id] = queryCnt;
				} else if (queryVal + tmp2[p2].v == dp[id]) {
					cnt[id] = (cnt[id] + queryCnt) % MOD;
				}
			}
		}
		for (int i = l; i <= p1; i++) {
			if (tmp2[i].left) {
				clear(tmp2[i].d);
			}
		}
	}

	public static void cdq2(int l, int r) {
		if (l == r) {
			return;
		}
		int mid = (l + r) / 2;
		cdq2(l, mid);
		merge(l, mid, r);
		cdq2(mid + 1, r);
	}

	public static void cdq1(int l, int r) {
		if (l == r) {
			return;
		}
		int mid = (l + r) / 2;
		cdq1(l, mid);
		for (int i = l; i <= r; i++) {
			tmp1[i] = arr[i];
			tmp1[i].left = (i <= mid);
		}
		Arrays.sort(tmp1, l, r + 1, cmp2);
		cdq2(l, r);
		cdq1(mid + 1, r);
	}

	public static int lower(int x) {
		int l = 1, r = s, ans = 1;
		while (l <= r) {
			int mid = (l + r) / 2;
			if (sortd[mid] >= x) {
				ans = mid;
				r = mid - 1;
			} else {
				l = mid + 1;
			}
		}
		return ans;
	}

	public static void prepare() {
		for (int i = 1; i <= n; i++) {
			sortd[i] = arr[i].d;
		}
		Arrays.sort(sortd, 1, n + 1);
		s = 1;
		for (int i = 2; i <= n; i++) {
			if (sortd[s] != sortd[i]) {
				sortd[++s] = sortd[i];
			}
		}
		for (int i = 1; i <= n; i++) {
			arr[i].d = lower(arr[i].d);
		}
		Arrays.sort(arr, 1, n + 1, cmp1);
		int m = 1;
		for (int i = 2; i <= n; i++) {
			if (arr[m].a == arr[i].a && arr[m].b == arr[i].b && arr[m].c == arr[i].c && arr[m].d == arr[i].d) {
				arr[m].v += arr[i].v;
			} else {
				arr[++m] = arr[i];
			}
		}
		n = m;
		for (int i = 1; i <= n; i++) {
			arr[i].i = i;
			dp[i] = arr[i].v;
			cnt[i] = 1;
		}
		for (int i = 1; i <= s; i++) {
			treeVal[i] = -INF;
			treeCnt[i] = 0;
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		in.nextInt();
		for (int i = 1, a, b, c, d, v; i <= n; i++) {
			a = in.nextInt();
			b = in.nextInt();
			c = in.nextInt();
			d = in.nextInt();
			v = in.nextInt();
			arr[i] = new Node(a, b, c, d, v);
		}
		prepare();
		cdq1(1, n);
		long best = 0;
		int ways = 0;
		for (int i = 1; i <= n; i++) {
			best = Math.max(best, dp[i]);
		}
		for (int i = 1; i <= n; i++) {
			if (dp[i] == best) {
				ways = (ways + cnt[i]) % MOD;
			}
		}
		out.println(best);
		out.println(ways % MOD);
		out.flush();
		out.close();
	}

	// 读写工具类
	static class FastReader {
		private final byte[] buffer = new byte[1 << 12];
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
