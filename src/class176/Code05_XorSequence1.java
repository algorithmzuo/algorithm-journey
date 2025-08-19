package class176;

// 异或序列，java版
// 给定一个长度为n的数组arr，给定一个数字k，一共有m条查询，格式如下
// 查询 l r : arr[l..r]范围上，有多少子数组的异或和为k，打印其数量
// 0 <= n、m、k、arr[i] <= 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P4462
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;

public class Code05_XorSequence1 {

	public static int MAXN = 100001;
	public static int MAXS = 1 << 20;
	public static int n, m, k;
	public static int[] arr = new int[MAXN];
	public static int[][] query = new int[MAXN][3];
	public static int[] bi = new int[MAXN];

	// pre[i] == x，表示前i个数字的前缀异或和为x
	public static int[] pre = new int[MAXN];
	// cnt[x] = a，表示窗口内，前缀异或和x，一共有a个
	public static long[] cnt = new long[MAXS];
	// num表示窗口内，异或和为k的子数组数量
	public static long num;
	public static long[] ans = new long[MAXN];

	public static class QueryCmp implements Comparator<int[]> {

		@Override
		public int compare(int[] a, int[] b) {
			if (bi[a[0]] != bi[b[0]]) {
				return bi[a[0]] - bi[b[0]];
			}
			return a[1] - b[1];
		}

	}

	// 前缀异或和x要删除一次
	public static void del(int x) {
		if (k != 0) {
			num -= cnt[x] * cnt[x ^ k];
		} else {
			num -= (cnt[x] * (cnt[x] - 1)) >> 1;
		}
		cnt[x]--;
		if (k != 0) {
			num += cnt[x] * cnt[x ^ k];
		} else {
			num += (cnt[x] * (cnt[x] - 1)) >> 1;
		}
	}

	// 前缀异或和x要增加一次
	public static void add(int x) {
		if (k != 0) {
			num -= cnt[x] * cnt[x ^ k];
		} else {
			num -= (cnt[x] * (cnt[x] - 1)) >> 1;
		}
		cnt[x]++;
		if (k != 0) {
			num += cnt[x] * cnt[x ^ k];
		} else {
			num += (cnt[x] * (cnt[x] - 1)) >> 1;
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
		Arrays.sort(query, 1, m + 1, new QueryCmp());
	}

	public static void compute() {
		int winl = 1, winr = 0;
		for (int i = 1; i <= m; i++) {
			// 任务范围[jobl, jobr]，但是前缀可能性会多一种
			// 所以左边界-1
			int jobl = query[i][0] - 1;
			int jobr = query[i][1];
			while (winl > jobl) {
				add(pre[--winl]);
			}
			while (winr < jobr) {
				add(pre[++winr]);
			}
			while (winl < jobl) {
				del(pre[winl++]);
			}
			while (winr > jobr) {
				del(pre[winr--]);
			}
			ans[query[i][2]] = num;
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
