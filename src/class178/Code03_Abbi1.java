package class178;

// 区间Abbi值，java版
// 给定一个长度为n的数组arr，区间Abbi值的定义如下
// 如果arr[l..r]包含数字v，并且v是第k小，那么这个数字的Abbi值 = v * k
// 区间Abbi值 = 区间内所有数字Abbi值的累加和
// 比如[1, 2, 2, 3]的Abbi值 = 1 * 1 + 2 * 2 + 2 * 2 + 3 * 4 = 21
// 一共有m条查询，格式为 l r : 打印arr[l..r]的区间Abbi值
// 1 <= n、m <= 5 * 10^5
// 1 <= arr[i] <= 10^5
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
	public static long[] preSum = new long[MAXN];

	public static int[] bi = new int[MAXN];
	public static int[] bl = new int[MAXB];
	public static int[] br = new int[MAXB];

	public static int[][] query = new int[MAXN][3];
	public static int[] headq = new int[MAXN];
	public static int[] nextq = new int[MAXN << 1];
	public static int[] ql = new int[MAXN << 1];
	public static int[] qr = new int[MAXN << 1];
	public static int[] qop = new int[MAXN << 1];
	public static int[] qid = new int[MAXN << 1];
	public static int cntq;

	public static long[] treeCnt = new long[MAXV + 1];
	public static long[] treeSum = new long[MAXV + 1];
	public static long[] pre = new long[MAXN];

	public static int[] blockLessCnt = new int[MAXB];
	public static int[] numLessCnt = new int[MAXN];
	public static long[] blockMoreSum = new long[MAXB];
	public static long[] numMoreSum = new long[MAXN];

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

	public static void addOffline(int x, int l, int r, int op, int id) {
		nextq[++cntq] = headq[x];
		headq[x] = cntq;
		ql[cntq] = l;
		qr[cntq] = r;
		qop[cntq] = op;
		qid[cntq] = id;
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
		for (int b = bi[val] + 1; b <= bi[MAXV]; b++) {
			blockLessCnt[b]++;
		}
		for (int i = val + 1; i <= br[bi[val]]; i++) {
			numLessCnt[i]++;
		}
		for (int b = 1; b <= bi[val] - 1; b++) {
			blockMoreSum[b] += val;
		}
		for (int i = bl[bi[val]]; i < val; i++) {
			numMoreSum[i] += val;
		}
	}

	public static int lessCnt(int x) {
		return blockLessCnt[bi[x]] + numLessCnt[x];
	}

	public static long moreSum(int x) {
		return blockMoreSum[bi[x]] + numMoreSum[x];
	}

	public static void prepare() {
		for (int i = 1; i <= n; i++) {
			preSum[i] = preSum[i - 1] + arr[i];
		}
		int blen = (int) Math.sqrt(MAXV);
		int bnum = (MAXV + blen - 1) / blen;
		for (int i = 1; i <= MAXV; i++) {
			bi[i] = (i - 1) / blen + 1;
		}
		for (int i = 1; i <= bnum; i++) {
			bl[i] = (i - 1) * blen + 1;
			br[i] = Math.min(i * blen, MAXV);
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
				addOffline(winl - 1, winr + 1, jobr, -1, id);
				ans[id] += pre[jobr] - pre[winr];
			}
			if (winr > jobr) {
				addOffline(winl - 1, jobr + 1, winr, 1, id);
				ans[id] -= pre[winr] - pre[jobr];
			}
			winr = jobr;
			if (winl > jobl) {
				addOffline(winr, jobl, winl - 1, 1, id);
				ans[id] -= pre[winl - 1] - pre[jobl - 1];
			}
			if (winl < jobl) {
				addOffline(winr, winl, jobl - 1, -1, id);
				ans[id] += pre[jobl - 1] - pre[winl - 1];
			}
			winl = jobl;
		}
		long tmp;
		for (int x = 0; x <= n; x++) {
			if (x >= 1) {
				addVal(arr[x]);
			}
			for (int q = headq[x]; q > 0; q = nextq[q]) {
				int l = ql[q], r = qr[q], op = qop[q], id = qid[q];
				for (int j = l; j <= r; j++) {
					tmp = (long) lessCnt(arr[j]) * arr[j] + moreSum(arr[j]);
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
		// 加工前缀和
		for (int i = 2; i <= m; i++) {
			ans[query[i][2]] += ans[query[i - 1][2]];
		}
		// 贡献是修正过的概念，现在补偿回来
		for (int i = 1; i <= m; i++) {
			ans[query[i][2]] += preSum[query[i][1]] - preSum[query[i][0] - 1];
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