package class179;

// 区间LCP达标对，java版
// 测试链接 : https://www.luogu.com.cn/problem/P5112
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;

public class Code06_LCP1 {

	public static int MAXN = 3000001;
	public static int MAXM = 100001;
	public static int n, m, k;
	public static long[] arr = new long[MAXN];
	public static int[][] query = new int[MAXM][3];

	public static int base = 499;
	public static long[] pow = new long[MAXN];
	public static long[] hash = new long[MAXN];

	public static long[] sorted = new long[MAXN];
	public static int[] val = new int[MAXN];
	public static int[] bi = new int[MAXN];

	public static long[] cnt = new long[MAXN];
	public static long curAns;
	public static long[] ans = new long[MAXM];

	public static int kth(int len, long num) {
		int left = 1, right = len, mid, ret = 0;
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

	public static class QueryCmp implements Comparator<int[]> {
		@Override
		public int compare(int[] a, int[] b) {
			if (bi[a[0]] != bi[b[0]]) {
				return bi[a[0]] - bi[b[0]];
			}
			if ((bi[a[0]] & 1) == 1) {
				return a[1] - b[1];
			} else {
				return b[1] - a[1];
			}
		}
	}

	public static void add(int x) {
		curAns -= cnt[x] * cnt[x];
		cnt[x]++;
		curAns += cnt[x] * cnt[x];
	}

	public static void del(int x) {
		curAns -= cnt[x] * cnt[x];
		cnt[x]--;
		curAns += cnt[x] * cnt[x];
	}

	public static void compute() {
		int winl = 1, winr = 0;
		for (int i = 1; i <= m; i++) {
			int jobl = query[i][0];
			int jobr = query[i][1];
			int id = query[i][2];
			if (jobl > jobr) {
				ans[id] = 0;
			} else {
				while (winl > jobl) {
					add(val[--winl]);
				}
				while (winr < jobr) {
					add(val[++winr]);
				}
				while (winl < jobl) {
					del(val[winl++]);
				}
				while (winr > jobr) {
					del(val[winr--]);
				}
				ans[id] = (curAns - (jobr - jobl + 1)) / 2;
			}
		}
	}

	public static void prepare() {
		pow[0] = 1;
		for (int i = 1; i <= n; i++) {
			pow[i] = pow[i - 1] * base;
			hash[i] = hash[i - 1] * base + arr[i];
		}
		for (int l = 1, r = k; r <= n; l++, r++) {
			arr[l] = hash[r] - hash[l - 1] * pow[r - l + 1];
			sorted[l] = arr[l];
		}
		n = n - k + 1;
		Arrays.sort(sorted, 1, n + 1);
		int len = 1;
		for (int i = 2; i <= n; i++) {
			if (sorted[len] != sorted[i]) {
				sorted[++len] = sorted[i];
			}
		}
		for (int i = 1; i <= n; i++) {
			val[i] = kth(len, arr[i]);
		}
		int blen = Math.max(1, (int) ((double) n / Math.sqrt(m)));
		for (int i = 1; i <= n; i++) {
			bi[i] = (i - 1) / blen + 1;
		}
		for (int i = 1; i <= m; i++) {
			query[i][1] = Math.min(query[i][1], n);
		}
		Arrays.sort(query, 1, m + 1, new QueryCmp());
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader();
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		k = in.nextInt();
		char c;
		for (int i = 1; i <= n; i++) {
			c = in.nextLowerCase();
			arr[i] = c - 'a' + 1;
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
		final private int BUFFER_SIZE = 1 << 16;
		private final InputStream in;
		private final byte[] buffer;
		private int ptr, len;

		public FastReader() {
			in = System.in;
			buffer = new byte[BUFFER_SIZE];
			ptr = len = 0;
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

		public char nextLowerCase() throws IOException {
			int c;
			while (true) {
				c = readByte();
				if (c >= 'a' && c <= 'z')
					return (char) c;
			}
		}

		public int nextInt() throws IOException {
			int num = 0;
			byte b = readByte();
			while (isWhitespace(b))
				b = readByte();
			boolean minus = false;
			if (b == '-') {
				minus = true;
				b = readByte();
			}
			while (!isWhitespace(b) && b != -1) {
				num = num * 10 + (b - '0');
				b = readByte();
			}
			return minus ? -num : num;
		}

		private boolean isWhitespace(byte b) {
			return b == ' ' || b == '\n' || b == '\r' || b == '\t';
		}
	}

}
