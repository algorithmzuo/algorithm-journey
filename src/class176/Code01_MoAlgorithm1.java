package class176;

// 普通莫队模版题，java版
// 测试链接 : https://www.luogu.com.cn/problem/SP3267
// 测试链接 : https://www.spoj.com/problems/DQUERY/
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;

public class Code01_MoAlgorithm1 {

	// 莫队经典排序
	public static class QueryCmp1 implements Comparator<int[]> {

		@Override
		public int compare(int[] a, int[] b) {
			if (bi[a[0]] != bi[b[0]]) {
				return bi[a[0]] - bi[b[0]];
			}
			return a[1] - b[1];
		}

	}

	// 莫队奇偶排序，玄学优化
	public static class QueryCmp2 implements Comparator<int[]> {

		@Override
		public int compare(int[] a, int[] b) {
			if (bi[a[0]] != bi[b[0]]) {
				return bi[a[0]] - bi[b[0]];
			}
			if ((bi[a[0]] & 1) == 1) {
				return a[1] - b[1];
			}
			return b[1] - a[1];
		}

	}

	public static int MAXN = 30001;
	public static int MAXV = 1000001;
	public static int MAXQ = 200001;
	public static int n, q;
	public static int[] arr = new int[MAXN];
	public static int[][] query = new int[MAXQ][3];

	public static int[] bi = new int[MAXN];
	public static int[] cnt = new int[MAXV];
	public static int kind = 0;
	public static int[] ans = new int[MAXQ];

	public static void del(int num) {
		if (--cnt[num] == 0) {
			kind--;
		}
	}

	public static void add(int num) {
		if (++cnt[num] == 1) {
			kind++;
		}
	}

	public static void prepare() {
		int blen = (int) Math.sqrt(n);
		for (int i = 1; i <= n; i++) {
			bi[i] = (i - 1) / blen + 1;
		}
		// Arrays.sort(query, 1, q + 1, new QueryCmp1());
		Arrays.sort(query, 1, q + 1, new QueryCmp2());
	}

	public static void compute() {
		int winl = 1, winr = 0;
		for (int i = 1; i <= q; i++) {
			int jobl = query[i][0];
			int jobr = query[i][1];
			while (winl > jobl) {
				add(arr[--winl]);
			}
			while (winr < jobr) {
				add(arr[++winr]);
			}
			while (winl < jobl) {
				del(arr[winl++]);
			}
			while (winr > jobr) {
				del(arr[winr--]);
			}
			ans[query[i][2]] = kind;
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		for (int i = 1; i <= n; i++) {
			arr[i] = in.nextInt();
		}
		q = in.nextInt();
		for (int i = 1; i <= q; i++) {
			query[i][0] = in.nextInt();
			query[i][1] = in.nextInt();
			query[i][2] = i;
		}
		prepare();
		compute();
		for (int i = 1; i <= q; i++) {
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
