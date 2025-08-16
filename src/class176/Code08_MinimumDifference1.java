package class176;

// 最小化极差，java版
// 给定一个长度为n的数组arr，一共有m条操作，操作格式如下
// 操作 1 l r k   : arr[l..r]范围上，选出k个真的出现并且互不相同的数
//                  这些数假设为a1、a2 .. ak，对应的出现次数假设为cnt1、cnt2 .. cntk
//                  打印 max{cnt1 .. cntk} - min{cnt1 .. cntk} 的最小值
//                  如果无法选出k个数，打印-1
// 操作 2 pos val : 把arr[pos]的值设置成val
// 1 <= 所有数据 <= 10^5
// 测试链接 : https://www.luogu.com.cn/problem/CF1476G
// 测试链接 : https://codeforces.com/problemset/problem/1476/G
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;

public class Code08_MinimumDifference1 {

	public static class QueryCmp implements Comparator<int[]> {
		@Override
		public int compare(int[] a, int[] b) {
			if (bi[a[0]] != bi[b[0]]) {
				return bi[a[0]] - bi[b[0]];
			}
			if (bi[a[1]] != bi[b[1]]) {
				return bi[a[1]] - bi[b[1]];
			}
			return a[3] - b[3];
		}
	}

	public static int MAXN = 100001;
	public static int MAXB = 3001;
	public static int n, m;
	public static int[] arr = new int[MAXN];
	public static int[] bi = new int[MAXN];
	public static int[] bl = new int[MAXB];
	public static int[] br = new int[MAXB];

	// query : l, r, k, t, id
	// update : pos, val
	public static int[][] query = new int[MAXN][5];
	public static int[][] update = new int[MAXN][2];
	public static int cntq, cntu;

	public static int[] cnt1 = new int[MAXN];
	public static int[] cnt2 = new int[MAXN];
	public static int[] sum = new int[MAXB];
	public static int[] cntFreq = new int[MAXN];
	public static int[] freqVal = new int[MAXN];
	public static int[] ans = new int[MAXN];

	public static void del(int num) {
		cnt2[cnt1[num]]--;
		sum[bi[cnt1[num]]]--;
		cnt1[num]--;
		cnt2[cnt1[num]]++;
		sum[bi[cnt1[num]]]++;
	}

	public static void add(int num) {
		cnt2[cnt1[num]]--;
		sum[bi[cnt1[num]]]--;
		cnt1[num]++;
		cnt2[cnt1[num]]++;
		sum[bi[cnt1[num]]]++;
	}

	public static void moveTime(int jobl, int jobr, int tim) {
		int pos = update[tim][0];
		int val = update[tim][1];
		if (jobl <= pos && pos <= jobr) {
			del(arr[pos]);
			add(val);
		}
		int tmp = arr[pos];
		arr[pos] = val;
		update[tim][1] = tmp;
	}

	public static int getAns(int k) {
		int size = 0;
		for (int b = 1; b <= bi[n]; b++) {
			if (sum[b] != 0) {
				for (int f = bl[b]; f <= br[b]; f++) {
					if (cnt2[f] > 0) {
						cntFreq[++size] = cnt2[f];
						freqVal[size] = f;
					}
				}
			}
		}
		int minDiff = Integer.MAX_VALUE;
		int cntSum = 0;
		for (int l = 1, r = 0; l <= size; l++) {
			while (cntSum < k && r < size) {
				r++;
				cntSum += cntFreq[r];
			}
			if (cntSum >= k) {
				minDiff = Math.min(minDiff, freqVal[r] - freqVal[l]);
			}
			cntSum -= cntFreq[l];
		}
		return minDiff == Integer.MAX_VALUE ? -1 : minDiff;
	}

	public static void compute() {
		int winl = 1, winr = 0, wint = 0;
		for (int i = 1; i <= cntq; i++) {
			int jobl = query[i][0];
			int jobr = query[i][1];
			int jobk = query[i][2];
			int jobt = query[i][3];
			int id = query[i][4];
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
			while (wint < jobt) {
				moveTime(jobl, jobr, ++wint);
			}
			while (wint > jobt) {
				moveTime(jobl, jobr, wint--);
			}
			ans[id] = getAns(jobk);
		}
	}

	public static void prepare() {
		int blen = Math.max(1, (int) Math.pow(n, 2.0 / 3));
		int bnum = (n + blen - 1) / blen;
		for (int i = 1; i <= n; i++) {
			bi[i] = (i - 1) / blen + 1;
		}
		for (int i = 1; i <= bnum; i++) {
			bl[i] = (i - 1) * blen + 1;
			br[i] = Math.min(i * blen, n);
		}
		Arrays.sort(query, 1, cntq + 1, new QueryCmp());
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		for (int i = 1; i <= n; i++) {
			arr[i] = in.nextInt();
		}
		for (int i = 1, op, l, r, k, pos, val; i <= m; i++) {
			op = in.nextInt();
			if (op == 1) {
				l = in.nextInt();
				r = in.nextInt();
				k = in.nextInt();
				cntq++;
				query[cntq][0] = l;
				query[cntq][1] = r;
				query[cntq][2] = k;
				query[cntq][3] = cntu;
				query[cntq][4] = cntq;
			} else {
				pos = in.nextInt();
				val = in.nextInt();
				cntu++;
				update[cntu][0] = pos;
				update[cntu][1] = val;
			}
		}
		prepare();
		compute();
		for (int i = 1; i <= cntq; i++) {
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
