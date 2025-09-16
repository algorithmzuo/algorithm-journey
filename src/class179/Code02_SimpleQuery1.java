package class179;

// 简单的询问，java版
// 测试链接 : https://www.luogu.com.cn/problem/P5268
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;

public class Code02_SimpleQuery1 {

	public static int MAXN = 50001;
	public static int n, m, cntq;
	public static int[] arr = new int[MAXN];

	// siz1、siz2、op、id
	public static int[][] query = new int[MAXN << 2][4];

	public static int[] bi = new int[MAXN];
	public static int[] cnt1 = new int[MAXN];
	public static int[] cnt2 = new int[MAXN];

	public static long sum = 0;
	public static long[] ans = new long[MAXN];

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

	public static void addQuery(int siz1, int siz2, int op, int id) {
		query[++cntq][0] = siz1;
		query[cntq][1] = siz2;
		query[cntq][2] = op;
		query[cntq][3] = id;
	}

	// win1和win2不代表一段区间，而是代表两个独立区域各自去覆盖arr
	// win1 = 0，win2 = 0，表示两个覆盖区域一开始都没有数字
	// job1和job2也不代表区间，而是代表两个区域各自要覆盖多大
	public static void compute() {
		int win1 = 0, win2 = 0;
		for (int i = 1; i <= cntq; i++) {
			int job1 = query[i][0];
			int job2 = query[i][1];
			int op = query[i][2];
			int id = query[i][3];
			while (win1 < job1) {
				++cnt1[arr[++win1]];
				sum += cnt2[arr[win1]];
			}
			while (win1 > job1) {
				--cnt1[arr[win1]];
				sum -= cnt2[arr[win1--]];
			}
			while (win2 < job2) {
				++cnt2[arr[++win2]];
				sum += cnt1[arr[win2]];
			}
			while (win2 > job2) {
				--cnt2[arr[win2]];
				sum -= cnt1[arr[win2--]];
			}
			ans[id] += sum * op;
		}
	}

	public static void prepare() {
		int blen = (int) Math.sqrt(n);
		for (int i = 1; i <= n; i++) {
			bi[i] = (i - 1) / blen + 1;
		}
		for (int i = 1; i <= cntq; i++) {
			if (query[i][0] > query[i][1]) {
				int tmp = query[i][0];
				query[i][0] = query[i][1];
				query[i][1] = tmp;
			}
		}
		Arrays.sort(query, 1, cntq + 1, new QueryCmp());
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		for (int i = 1; i <= n; i++) {
			arr[i] = in.nextInt();
		}
		m = in.nextInt();
		for (int i = 1, l1, r1, l2, r2; i <= m; i++) {
			l1 = in.nextInt();
			r1 = in.nextInt();
			l2 = in.nextInt();
			r2 = in.nextInt();
			addQuery(r1, r2, 1, i);
			addQuery(r1, l2 - 1, -1, i);
			addQuery(l1 - 1, r2, -1, i);
			addQuery(l1 - 1, l2 - 1, 1, i);
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
