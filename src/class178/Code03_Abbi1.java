package class178;

// 区间查询Abbi值，java版
// 测试链接 : https://www.luogu.com.cn/problem/P5501
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;

public class Code03_Abbi1 {

	public static int MAXN = 500001;
	public static int MAXV = 100000;
	public static int MAXB = 401;
	public static int n, m;
	public static int[] arr = new int[MAXN];
	public static long[] presum = new long[MAXN];
	public static int[] bi = new int[MAXN];
	public static int[] br = new int[MAXB];

	public static int[][] query = new int[MAXN][3];

	public static int[] headq = new int[MAXN];
	public static int[] nextq = new int[MAXN << 1];
	public static int[] qid = new int[MAXN << 1];
	public static int[] ql = new int[MAXN << 1];
	public static int[] qr = new int[MAXN << 1];
	public static int[] qop = new int[MAXN << 1];
	public static int cntq;

	public static long[] treeCnt = new long[MAXV + 1];
	public static long[] treeSum = new long[MAXV + 1];

	public static long[] pre = new long[MAXN];
	public static int[] cnt1 = new int[MAXB];
	public static int[] cnt2 = new int[MAXN];
	public static long[] sum1 = new long[MAXB];
	public static long[] sum2 = new long[MAXN];

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

	public static void addOffline(int x, int id, int l, int r, int op) {
		nextq[++cntq] = headq[x];
		headq[x] = cntq;
		qid[cntq] = id;
		ql[cntq] = l;
		qr[cntq] = r;
		qop[cntq] = op;
	}

	public static int lowbit(int x) {
		return x & -x;
	}

	public static void add(long[] tree, int x, int v) {
		while (x <= MAXV) {
			tree[x] += v;
			x += lowbit(x);
		}
	}

	public static long sum(long[] tree, int x) {
		long ret = 0;
		while (x > 0) {
			ret += tree[x];
			x -= lowbit(x);
		}
		return ret;
	}

	public static void addVal(int val) {
		if (val <= 0) {
			return;
		}
		for (int b = bi[val]; b <= bi[MAXV]; b++) {
			cnt1[b]++;
			sum1[b] += val;
		}
		for (int i = val; i <= br[bi[val]]; i++) {
			cnt2[i]++;
			sum2[i] += val;
		}
	}

	public static long getSum(int x) {
		if (x <= 0) {
			return 0;
		}
		return sum1[bi[x] - 1] + sum2[x];
	}

	public static int getCnt(int x) {
		if (x <= 0) {
			return 0;
		}
		return cnt1[bi[x] - 1] + cnt2[x];
	}

	public static void prepare() {
		for (int i = 1; i <= n; i++) {
			presum[i] = presum[i - 1] + arr[i];
		}
		int blen = (int) Math.sqrt(MAXV);
		int bnum = (MAXV + blen - 1) / blen;
		for (int i = 1; i <= MAXV; i++) {
			bi[i] = (i - 1) / blen + 1;
		}
		for (int b = 1; b <= bnum; b++) {
			br[b] = Math.min(b * blen, MAXV);
		}
		Arrays.sort(query, 1, m + 1, new QueryCmp());
	}

	public static void compute() {
		for (int i = 1; i <= n; i++) {
			pre[i] = pre[i - 1] + sum(treeCnt, arr[i] - 1) * arr[i] + sum(treeSum, MAXV) - sum(treeSum, arr[i]);
			add(treeCnt, arr[i], 1);
			add(treeSum, arr[i], arr[i]);
		}
		int winl = 1, winr = 0;
		for (int i = 1; i <= m; i++) {
			int jobl = query[i][0];
			int jobr = query[i][1];
			int id = query[i][2];
			if (winr < jobr) {
				addOffline(winl - 1, id, winr + 1, jobr, -1);
				ans[id] += pre[jobr] - pre[winr];
			}
			if (winr > jobr) {
				addOffline(winl - 1, id, jobr + 1, winr, 1);
				ans[id] -= pre[winr] - pre[jobr];
			}
			winr = jobr;
			if (winl > jobl) {
				addOffline(winr, id, jobl, winl - 1, 1);
				ans[id] -= pre[winl - 1] - pre[jobl - 1];

			}
			if (winl < jobl) {
				addOffline(winr, id, winl, jobl - 1, -1);
				ans[id] += pre[jobl - 1] - pre[winl - 1];
			}
			winl = jobl;
		}
		long sum = 0;
		long tmp;
		for (int i = 0; i <= n; i++) {
			if (i >= 1) {
				addVal(arr[i]);
				sum += arr[i];
			}
			for (int q = headq[i]; q > 0; q = nextq[q]) {
				int id = qid[q], l = ql[q], r = qr[q], op = qop[q];
				for (int j = l; j <= r; j++) {
					tmp = (long) getCnt(arr[j] - 1) * arr[j] + sum - getSum(arr[j]);
					if (op == 1) {
						ans[id] += tmp;
					} else {
						ans[id] -= tmp;
					}
				}
			}
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
		for (int i = 2; i <= m; i++) {
			ans[query[i][2]] += ans[query[i - 1][2]];
		}
		for (int i = 1; i <= m; i++) {
			ans[query[i][2]] += presum[query[i][1]] - presum[query[i][0] - 1];
		}
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