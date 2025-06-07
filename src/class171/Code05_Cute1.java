package class171;

// 德丽莎世界第一可爱，java版
// 一共有n个怪兽，每个怪兽有a、b、c、d四个能力值，以及打败之后的收益v
// 可以选择任意顺序打怪兽，每次打的怪兽的四种能力值都不能小于上次打的怪兽
// 打印能获得的最大收益，可能所有怪兽收益都是负数，那也需要至少打一只怪兽
// 1 <= n <= 5 * 10^4
// -10^5 <= a、b、c、d <= +10^5
// -10^9 <= v <= +10^9
// 测试链接 : https://www.luogu.com.cn/problem/P5621
// 提交以下的code，提交时请把类名改成"Main"
// java实现的逻辑一定是正确的，但是有两个测试用例超时
// 因为这道题只考虑C++能通过的时间标准，根本没考虑java的用户
// 想通过用C++实现，本节课Code05_Cute2文件就是C++的实现
// 两个版本的逻辑完全一样，C++版本可以通过所有测试

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;

public class Code05_Cute1 {

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

	// 需要排序的稳定性
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

	// 需要排序的稳定性
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

	public static int MAXN = 50001;
	public static long INF = (long) (1e18 + 1);
	public static int n, s;

	public static Node[] arr = new Node[MAXN];
	public static int[] sortd = new int[MAXN];

	public static Node[] tmp1 = new Node[MAXN];
	public static Node[] tmp2 = new Node[MAXN];

	public static long[] tree = new long[MAXN];
	public static long[] dp = new long[MAXN];

	public static int lowbit(int i) {
		return i & -i;
	}

	public static void more(int i, long num) {
		while (i <= s) {
			tree[i] = Math.max(tree[i], num);
			i += lowbit(i);
		}
	}

	public static long query(int i) {
		long ret = -INF;
		while (i > 0) {
			ret = Math.max(ret, tree[i]);
			i -= lowbit(i);
		}
		return ret;
	}

	public static void clear(int i) {
		while (i <= s) {
			tree[i] = -INF;
			i += lowbit(i);
		}
	}

	public static void merge(int l, int m, int r) {
		for (int i = l; i <= r; i++) {
			tmp2[i] = tmp1[i];
		}
		Arrays.sort(tmp2, l, m + 1, cmp3);
		Arrays.sort(tmp2, m + 1, r + 1, cmp3);
		int p1, p2;
		for (p1 = l - 1, p2 = m + 1; p2 <= r; p2++) {
			while (p1 + 1 <= m && tmp2[p1 + 1].c <= tmp2[p2].c) {
				p1++;
				if (tmp2[p1].left) {
					more(tmp2[p1].d, dp[tmp2[p1].i]);
				}
			}
			if (!tmp2[p2].left) {
				dp[tmp2[p2].i] = Math.max(dp[tmp2[p2].i], query(tmp2[p2].d) + tmp2[p2].v);
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
			tmp1[i].left = i <= mid;
		}
		Arrays.sort(tmp1, l, r + 1, cmp2);
		cdq2(l, r);
		cdq1(mid + 1, r);
	}

	public static int lower(long num) {
		int l = 1, r = s, m, ans = 1;
		while (l <= r) {
			m = (l + r) / 2;
			if (sortd[m] >= num) {
				ans = m;
				r = m - 1;
			} else {
				l = m + 1;
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
				if (arr[i].v > 0) {
					arr[m].v += arr[i].v;
				}
			} else {
				arr[++m] = arr[i];
			}
		}
		n = m;
		for (int i = 1; i <= n; i++) {
			arr[i].i = i;
			dp[i] = arr[i].v;
		}
		for (int i = 1; i <= s; i++) {
			tree[i] = -INF;
		}
	}

	public static void main(String[] args) throws IOException {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
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
		long ans = -INF;
		for (int i = 1; i <= n; i++) {
			ans = Math.max(ans, dp[i]);
		}
		out.println(ans);
		out.flush();
		out.close();
	}

	// 读写工具类
	static class FastReader {
		private final byte[] buffer = new byte[1 << 20];
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
