package class177;

// 只加不减的回滚莫队入门题，java版
// 测试链接 : https://www.luogu.com.cn/problem/AT_joisc2014_c
// 测试链接 : https://atcoder.jp/contests/joisc2014/tasks/joisc2014_c
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;

public class Code01_MoOnlyAdd1 {

	public static int MAXN = 100001;
	public static int MAXB = 401;
	public static int n, m;
	public static int[] arr = new int[MAXN];
	public static int[][] query = new int[MAXN][3];
	public static int[] sorted = new int[MAXN];
	public static int cntv;

	public static int blen, bnum;
	public static int[] bi = new int[MAXN];
	public static int[] br = new int[MAXB];

	public static int[] forceCnt = new int[MAXN];
	public static int[] cnt = new int[MAXN];

	public static long curAns = 0;
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

	public static long force(int l, int r) {
		long ret = 0;
		for (int i = l; i <= r; i++) {
			forceCnt[arr[i]]++;
		}
		for (int i = l; i <= r; i++) {
			ret = Math.max(ret, (long) forceCnt[arr[i]] * sorted[arr[i]]);
		}
		for (int i = l; i <= r; i++) {
			forceCnt[arr[i]]--;
		}
		return ret;
	}

	public static void del(int num) {
		cnt[num]--;
	}

	public static void add(int num) {
		cnt[num]++;
		curAns = Math.max(curAns, (long) cnt[num] * sorted[num]);
	}

	public static void compute() {
		for (int block = 1, qi = 1; block <= bnum && qi <= m; block++) {
			curAns = 0;
			Arrays.fill(cnt, 1, cntv + 1, 0);
			int winl = br[block] + 1;
			int winr = br[block];
			for (; qi <= m && bi[query[qi][0]] == block; qi++) {
				int jobl = query[qi][0];
				int jobr = query[qi][1];
				int id = query[qi][2];
				if (jobr <= br[block]) {
					ans[id] = force(jobl, jobr);
				} else {
					while (winr < jobr) {
						add(arr[++winr]);
					}
					long backup = curAns;
					while (winl > jobl) {
						add(arr[--winl]);
					}
					ans[id] = curAns;
					curAns = backup;
					while (winl <= br[block]) {
						del(arr[winl++]);
					}
				}
			}
		}
	}

	public static void prepare() {
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
		blen = (int) Math.sqrt(n);
		bnum = (n + blen - 1) / blen;
		for (int i = 1; i <= n; i++) {
			bi[i] = (i - 1) / blen + 1;
		}
		for (int i = 1; i <= bnum; i++) {
			br[i] = Math.min(i * blen, n);
		}
		Arrays.sort(query, 1, m + 1, new QueryCmp());
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
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
