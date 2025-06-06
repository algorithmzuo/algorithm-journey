package class171;

// 拦截导弹，java版
// 一共有n个导弹，编号1~n，表示导弹从早到晚依次到达，每个导弹给定，高度h、速度v
// 你有导弹拦截系统，第1次可以拦截任意参数的导弹
// 但是之后拦截的导弹，高度和速度都不能比前一次拦截的导弹大
// 你的目的是尽可能多的拦截导弹，如果有多个最优方案，会随机选一个执行
// 打印最多能拦截几个导弹，并且打印每个导弹被拦截的概率
// 1 <= n <= 5 * 10^4
// 1 <= h、v <= 10^9
// 测试链接 : https://www.luogu.com.cn/problem/P2487
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

public class Code04_Interceptor1 {

	public static int MAXN = 50001;
	public static int n, s;
	public static int[] h = new int[MAXN];
	public static int[] v = new int[MAXN];
	public static int[] sortv = new int[MAXN];

	// 位置i、高度h、速度v
	public static int[][] arr = new int[MAXN][3];

	// 树状数组，维护最大长度、最大长度出现的次数
	public static int[] treelen = new int[MAXN];
	public static double[] treecnt = new double[MAXN];

	public static int[] len1 = new int[MAXN];
	public static double[] cnt1 = new double[MAXN];

	public static int[] len2 = new int[MAXN];
	public static double[] cnt2 = new double[MAXN];

	public static int lowbit(int x) {
		return x & -x;
	}

	public static void add(int i, int len, double cnt) {
		while (i <= s) {
			if (len > treelen[i]) {
				treelen[i] = len;
				treecnt[i] = cnt;
			} else if (len == treelen[i]) {
				treecnt[i] += cnt;
			}
			i += lowbit(i);
		}
	}

	public static int querylen;
	public static double querycnt;

	public static void query(int i) {
		querylen = 0;
		querycnt = 0;
		while (i > 0) {
			if (treelen[i] > querylen) {
				querylen = treelen[i];
				querycnt = treecnt[i];
			} else if (treelen[i] == querylen) {
				querycnt += treecnt[i];
			}
			i -= lowbit(i);
		}
	}

	public static void clear(int i) {
		while (i <= s) {
			treelen[i] = 0;
			treecnt[i] = 0;
			i += lowbit(i);
		}
	}

	public static void merge1(int l, int m, int r) {
		for (int i = l; i <= r; i++) {
			arr[i][0] = i;
			arr[i][1] = h[i];
			arr[i][2] = v[i];
		}
		Arrays.sort(arr, l, m + 1, (a, b) -> b[1] - a[1]);
		Arrays.sort(arr, m + 1, r + 1, (a, b) -> b[1] - a[1]);
		int p1, p2;
		for (p1 = l - 1, p2 = m + 1; p2 <= r; p2++) {
			while (p1 + 1 <= m && arr[p1 + 1][1] >= arr[p2][1]) {
				p1++;
				add(s - arr[p1][2] + 1, len1[arr[p1][0]], cnt1[arr[p1][0]]);
			}
			query(s - arr[p2][2] + 1);
			if (querylen + 1 > len1[arr[p2][0]]) {
				len1[arr[p2][0]] = querylen + 1;
				cnt1[arr[p2][0]] = querycnt;
			} else if (querylen + 1 == len1[arr[p2][0]]) {
				cnt1[arr[p2][0]] += querycnt;
			}
		}
		for (int i = l; i <= p1; i++) {
			clear(s - arr[i][2] + 1);
		}
	}

	public static void cdq1(int l, int r) {
		if (l == r) {
			return;
		}
		int mid = (l + r) / 2;
		cdq1(l, mid);
		merge1(l, mid, r);
		cdq1(mid + 1, r);
	}

	public static void merge2(int l, int m, int r) {
		for (int i = l; i <= r; i++) {
			arr[i][0] = i;
			arr[i][1] = h[i];
			arr[i][2] = v[i];
		}
		Arrays.sort(arr, l, m + 1, (a, b) -> a[1] - b[1]);
		Arrays.sort(arr, m + 1, r + 1, (a, b) -> a[1] - b[1]);
		int p1, p2;
		for (p1 = l - 1, p2 = m + 1; p2 <= r; p2++) {
			while (p1 + 1 <= m && arr[p1 + 1][1] <= arr[p2][1]) {
				p1++;
				add(arr[p1][2], len2[arr[p1][0]], cnt2[arr[p1][0]]);
			}
			query(arr[p2][2]);
			if (querylen + 1 > len2[arr[p2][0]]) {
				len2[arr[p2][0]] = querylen + 1;
				cnt2[arr[p2][0]] = querycnt;
			} else if (querylen + 1 == len2[arr[p2][0]]) {
				cnt2[arr[p2][0]] += querycnt;
			}
		}
		for (int i = l; i <= p1; i++) {
			clear(arr[i][2]);
		}
	}

	public static void cdq2(int l, int r) {
		if (l == r) {
			return;
		}
		int m = (l + r) / 2;
		cdq2(l, m);
		merge2(l, m, r);
		cdq2(m + 1, r);
	}

	public static int lower(int num) {
		int l = 1, r = s, ans = 1;
		while (l <= r) {
			int mid = (l + r) / 2;
			if (sortv[mid] >= num) {
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
			sortv[i] = v[i];
		}
		Arrays.sort(sortv, 1, n + 1);
		s = 1;
		for (int i = 2; i <= n; i++) {
			if (sortv[s] != sortv[i]) {
				sortv[++s] = sortv[i];
			}
		}
		for (int i = 1; i <= n; i++) {
			v[i] = lower(v[i]);
		}
		for (int i = 1; i <= n; i++) {
			len1[i] = len2[i] = 1;
			cnt1[i] = cnt2[i] = 1.0;
		}
	}

	public static void compute() {
		cdq1(1, n);
		for (int l = 1, r = n; l < r; l++, r--) {
			int a = h[l];
			h[l] = h[r];
			h[r] = a;
			int b = v[l];
			v[l] = v[r];
			v[r] = b;
		}
		cdq2(1, n);
		for (int l = 1, r = n; l < r; l++, r--) {
			int a = len2[l];
			len2[l] = len2[r];
			len2[r] = a;
			double b = cnt2[l];
			cnt2[l] = cnt2[r];
			cnt2[r] = b;
		}
	}

	public static void main(String[] args) throws IOException {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		for (int i = 1; i <= n; i++) {
			h[i] = in.nextInt();
			v[i] = in.nextInt();
		}
		prepare();
		compute();
		int len = 0;
		double cnt = 0;
		for (int i = 1; i <= n; i++) {
			len = Math.max(len, len1[i]);
		}
		for (int i = 1; i <= n; i++) {
			if (len1[i] == len) {
				cnt += cnt1[i];
			}
		}
		out.println(len);
		for (int i = 1; i <= n; i++) {
			if (len1[i] + len2[i] - 1 < len) {
				out.print("0 ");
			} else {
				out.printf("%.5f ", cnt1[i] * cnt2[i] / cnt);
			}
		}
		out.println();
		out.flush();
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
