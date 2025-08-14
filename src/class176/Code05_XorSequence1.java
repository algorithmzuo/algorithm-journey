package class176;

// 异或序列，java版
// 测试链接 : https://www.luogu.com.cn/problem/P4462
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;

public class Code05_XorSequence1 {

	// 莫队经典排序
	public static class QueryCmp1 implements Comparator<int[]> {

		@Override
		public int compare(int[] a, int[] b) {
			if (bi[a[0]] != bi[b[0]]) {
				return a[0] - b[0];
			}
			return a[1] - b[1];
		}

	}

	// 莫队奇偶排序
	public static class QueryCmp2 implements Comparator<int[]> {

		@Override
		public int compare(int[] a, int[] b) {
			if (bi[a[0]] != bi[b[0]]) {
				return a[0] - b[0];
			}
			if ((bi[a[0]] & 1) == 1) {
				return a[1] - b[1];
			}
			return b[1] - a[1];
		}

	}

	public static QueryCmp1 cmp1 = new QueryCmp1();
	public static QueryCmp2 cmp2 = new QueryCmp2();

	public static int MAXN = 100001;
	public static int MAXK = 1 << 20;
	public static int n, m, k;
	public static int[] arr = new int[MAXN];
	public static int[][] query = new int[MAXN][3];

	public static int[] pre = new int[MAXN];
	public static int[] bi = new int[MAXN];

	public static long[] cnt = new long[MAXK];
	public static long[] ans = new long[MAXN];
	public static long cur;

	public static void del(int idx) {
		int x = pre[idx];
		if (k != 0) {
			cur -= cnt[x] * cnt[x ^ k];
		} else {
			cur -= (cnt[x] * (cnt[x] - 1)) >> 1;
		}
		cnt[x]--;
		if (k != 0) {
			cur += cnt[x] * cnt[x ^ k];
		} else {
			cur += (cnt[x] * (cnt[x] - 1)) >> 1;
		}
	}

	public static void add(int idx) {
		int x = pre[idx];
		if (k != 0) {
			cur -= cnt[x] * cnt[x ^ k];
		} else {
			cur -= (cnt[x] * (cnt[x] - 1)) >> 1;
		}
		cnt[x]++;
		if (k != 0) {
			cur += cnt[x] * cnt[x ^ k];
		} else {
			cur += (cnt[x] * (cnt[x] - 1)) >> 1;
		}
	}

	public static void prepare() {
		for (int i = 1; i <= n; i++) {
			pre[i] = pre[i - 1] ^ arr[i];
		}
		int blen = (int) Math.sqrt(n);
		for (int i = 1; i <= n; i++) {
			bi[i] = (i - 1) / blen + 1;
		}
		// Arrays.sort(query, 1, m + 1, cmp1);
		Arrays.sort(query, 1, m + 1, cmp2);
	}

	public static void compute() {
		int winl = 1, winr = 0;
		for (int i = 1; i <= m; i++) {
			// 左边界要-1
			int jobl = query[i][0] - 1;
			int jobr = query[i][1];
			while (winl > jobl) {
				add(--winl);
			}
			while (winr < jobr) {
				add(++winr);
			}
			while (winl < jobl) {
				del(winl++);
			}
			while (winr > jobr) {
				del(winr--);
			}
			ans[query[i][2]] = cur;
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		k = in.nextInt();
		for (int i = 1; i <= n; i++) {
			arr[i] = in.nextInt();
		}
		for (int i = 1; i <= m; i++) {
			query[i][0] = in.nextInt();
			query[i][1] = in.nextInt();
			query[i][2] = i;
		}
		prepare();
		compute();
		for (int i = 1; i <= m; i++) {
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
