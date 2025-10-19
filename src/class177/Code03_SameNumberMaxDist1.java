package class177;

// 相同数的最远距离，java版
// 给定一个长度为n的数组arr，一共有m条查询，格式如下
// 查询 l r : 打印arr[l..r]范围上，相同的数的最远间隔距离
//            序列中两个元素的间隔距离指的是两个元素下标差的绝对值
// 1 <= n、m <= 2 * 10^5
// 1 <= arr[i] <= 2 * 10^9
// 测试链接 : https://www.luogu.com.cn/problem/P5906
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;

public class Code03_SameNumberMaxDist1 {

	public static int MAXN = 200001;
	public static int MAXB = 501;
	public static int n, m;
	public static int[] arr = new int[MAXN];
	public static int[][] query = new int[MAXN][3];
	public static int[] sorted = new int[MAXN];
	public static int cntv;

	public static int blen, bnum;
	public static int[] bi = new int[MAXN];
	public static int[] br = new int[MAXB];

	// first[x]表示只考虑窗口右扩阶段，数字x首次出现的位置
	public static int[] first = new int[MAXN];
	// mostRight[x]表示窗口中数字x最右出现的位置
	public static int[] mostRight = new int[MAXN];
	// 答案信息，相同的数的最远间隔距离
	public static int maxDist;

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

	public static int kth(int num) {
		int left = 1, right = cntv, mid, ret = 0;
		while (left <= right) {
			mid = (left + right) >> 1;
			if (sorted[mid] <= num) {
				ret = mid;
				left = mid + 1;
			} else {
				right = mid - 1;
			}
		}
		return ret;
	}

	public static int force(int l, int r) {
		int ret = 0;
		for (int i = l; i <= r; i++) {
			if (first[arr[i]] == 0) {
				first[arr[i]] = i;
			} else {
				ret = Math.max(ret, i - first[arr[i]]);
			}
		}
		for (int i = l; i <= r; i++) {
			first[arr[i]] = 0;
		}
		return ret;
	}

	public static void addRight(int idx) {
		int num = arr[idx];
		mostRight[num] = idx;
		if (first[num] == 0) {
			first[num] = idx;
		}
		maxDist = Math.max(maxDist, idx - first[num]);
	}

	public static void addLeft(int idx) {
		int num = arr[idx];
		if (mostRight[num] == 0) {
			mostRight[num] = idx;
		} else {
			maxDist = Math.max(maxDist, mostRight[num] - idx);
		}
	}

	public static void delLeft(int idx) {
		int num = arr[idx];
		if (mostRight[num] == idx) {
			mostRight[num] = 0;
		}
	}

	public static void compute() {
		for (int block = 1, qi = 1; block <= bnum && qi <= m; block++) {
			maxDist = 0;
			Arrays.fill(first, 1, cntv + 1, 0);
			Arrays.fill(mostRight, 1, cntv + 1, 0);
			int winl = br[block] + 1, winr = br[block];
			for (; qi <= m && bi[query[qi][0]] == block; qi++) {
				int jobl = query[qi][0];
				int jobr = query[qi][1];
				int id = query[qi][2];
				if (jobr <= br[block]) {
					ans[id] = force(jobl, jobr);
				} else {
					while (winr < jobr) {
						addRight(++winr);
					}
					int backup = maxDist;
					while (winl > jobl) {
						addLeft(--winl);
					}
					ans[id] = maxDist;
					maxDist = backup;
					while (winl <= br[block]) {
						delLeft(winl++);
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
		for (int i = 1; i <= n; i++) {
			arr[i] = in.nextInt();
		}
		m = in.nextInt();
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
