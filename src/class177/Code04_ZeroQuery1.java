package class177;

// 累加和为0的最长子数组，java版
// 给定一个长度为n的数组arr，其中只有1和-1两种值
// 一共有m条查询，格式 l r : 打印arr[l..r]范围上，累加和为0的最长子数组长度
// 1 <= n、m <= 5 * 10^4
// 测试链接 : https://www.luogu.com.cn/problem/SP20644
// 测试链接 : https://www.spoj.com/problems/ZQUERY/
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;

public class Code04_ZeroQuery1 {

	public static int MAXN = 50002;
	public static int MAXB = 301;
	public static int n, m;
	public static int[] arr = new int[MAXN];
	public static int[][] query = new int[MAXN][3];
	public static int[] sorted = new int[MAXN];
	public static int cntv;

	public static int blen, bnum;
	public static int[] bi = new int[MAXN];
	public static int[] br = new int[MAXB];

	public static int[] forceEd = new int[MAXN];
	public static int[] st = new int[MAXN];
	public static int[] ed = new int[MAXN];

	public static int curAns = 0;
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

	public static int force(int l, int r) {
		int ret = 0;
		for (int i = l; i <= r; i++) {
			if (forceEd[arr[i]] == 0) {
				forceEd[arr[i]] = i;
			} else {
				ret = Math.max(ret, i - forceEd[arr[i]]);
			}
		}
		for (int i = l; i <= r; i++) {
			forceEd[arr[i]] = 0;
		}
		return ret;
	}

	public static void addRight(int idx) {
		int num = arr[idx];
		ed[num] = idx;
		if (st[num] == 0) {
			st[num] = idx;
		}
		curAns = Math.max(curAns, idx - st[num]);
	}

	public static void addLeft(int idx) {
		int num = arr[idx];
		if (ed[num] != 0) {
			curAns = Math.max(curAns, ed[num] - idx);
		} else {
			ed[num] = idx;
		}
	}

	public static void undoLeft(int idx) {
		int num = arr[idx];
		if (ed[num] == idx) {
			ed[num] = 0;
		}
	}

	public static void compute() {
		for (int block = 1, qi = 1; block <= bnum && qi <= m; block++) {
			curAns = 0;
			Arrays.fill(st, 1, cntv + 1, 0);
			Arrays.fill(ed, 1, cntv + 1, 0);
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
					int backup = curAns;
					while (winl > jobl) {
						addLeft(--winl);
					}
					ans[id] = curAns;
					curAns = backup;
					while (winl <= br[block]) {
						undoLeft(winl++);
					}
				}
			}
		}
	}

	public static void prepare() {
		// 生成前缀和数组，下标从1开始，补充一个前缀长度为0的前缀和
		for (int i = 1; i <= n; i++) {
			arr[i] += arr[i - 1];
		}
		for (int i = n; i >= 0; i--) {
			arr[i + 1] = arr[i];
		}
		n++;
		// 离散化
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
		// 分块
		blen = (int) Math.sqrt(n);
		bnum = (n + blen - 1) / blen;
		for (int i = 1; i <= n; i++) {
			bi[i] = (i - 1) / blen + 1;
		}
		for (int i = 1; i <= bnum; i++) {
			br[i] = Math.min(i * blen, n);
		}
		// 原来查询范围 l..r，对应前缀查询范围 l-1..r
		// 但是前缀和平移了，所以对应的前缀查询范围 l..r+1
		for (int i = 1; i <= m; i++) {
			query[i][1]++;
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
