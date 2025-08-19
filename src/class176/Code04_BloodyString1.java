package class176;

// 大爷的字符串题，java版
// 给定一个长度为n的数组arr，一共有m条查询，格式如下
// 查询 l r : arr[l..r]范围上，众数出现了几次，打印其相反数
// 1 <= n、m <= 2 * 10^5
// 1 <= arr[i] <= 10^9
// 测试链接 : https://www.luogu.com.cn/problem/P3709
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;

public class Code04_BloodyString1 {

	public static int MAXN = 200001;
	public static int n, m;
	public static int[] arr = new int[MAXN];
	public static int[] sorted = new int[MAXN];
	public static int[][] query = new int[MAXN][3];
	public static int[] bi = new int[MAXN];

	// cnt1[i] = j，表示窗口内，i这种数出现的次数为j
	// cnt2[i] = j，表示窗口内，出现次数为i的数，一共有j种
	// maxCnt表示窗口内众数的次数
	public static int[] cnt1 = new int[MAXN];
	public static int[] cnt2 = new int[MAXN];
	public static int maxCnt = 0;

	public static int[] ans = new int[MAXN];

	public static class QueryCmp implements Comparator<int[]> {

		@Override
		public int compare(int[] a, int[] b) {
			if (bi[a[0]] != bi[b[0]]) {
				return bi[a[0]] - bi[b[0]];
			}
			return a[1] - b[1];
		}

	}

	public static int kth(int len, int num) {
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

	public static void del(int num) {
		if (cnt1[num] == maxCnt && cnt2[cnt1[num]] == 1) {
			maxCnt--;
		}
		cnt2[cnt1[num]]--;
		cnt1[num]--;
		cnt2[cnt1[num]]++;
	}

	public static void add(int num) {
		cnt2[cnt1[num]]--;
		cnt1[num]++;
		cnt2[cnt1[num]]++;
		maxCnt = Math.max(maxCnt, cnt1[num]);
	}

	public static void prepare() {
		for (int i = 1; i <= n; i++) {
			sorted[i] = arr[i];
		}
		Arrays.sort(sorted, 1, n + 1);
		int len = 1;
		for (int i = 2; i <= n; i++) {
			if (sorted[len] != sorted[i]) {
				sorted[++len] = sorted[i];
			}
		}
		for (int i = 1; i <= n; i++) {
			arr[i] = kth(len, arr[i]);
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
			ans[query[i][2]] = maxCnt;
		}
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
			out.println(-ans[i]);
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