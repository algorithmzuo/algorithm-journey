package class178;

// 区间查倍数，java版
// 测试链接 : https://www.luogu.com.cn/problem/P5398
// 提交以下的code，提交时请把类名改成"Main"
// java实现的逻辑一定是正确的，但是本题卡常，无法通过所有测试用例
// 想通过用C++实现，本节课Code04_Gosick2文件就是C++的实现
// 两个版本的逻辑完全一样，C++版本可以通过所有测试

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;

public class Code04_Gosick1 {

	public static int MAXN = 500001;
	public static int MAXF = 5000001;
	public static int LIMIT = 80;
	public static int n, m, maxv;
	public static int[] arr = new int[MAXN];
	public static int[] bi = new int[MAXN];

	// 每个数的因子表
	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXF];
	public static int[] fac = new int[MAXF];
	public static int cntf;

	// l、r、id
	public static int[][] query1 = new int[MAXN][3];
	// pos、id、l、r、op
	public static int[][] query2 = new int[MAXN << 1][5];
	public static int cntq;

	public static int[] fcnt = new int[MAXN];
	public static int[] xcnt = new int[MAXN];
	public static long[] pre = new long[MAXN];

	public static int[] cnt1 = new int[MAXN];
	public static int[] cnt2 = new int[MAXN];

	public static long[] ans = new long[MAXN];

	public static class Cmp1 implements Comparator<int[]> {
		@Override
		public int compare(int[] a, int[] b) {
			if (bi[a[0]] != bi[b[0]]) {
				return bi[a[0]] - bi[b[0]];
			}
			return a[1] - b[1];
		}
	}

	public static class Cmp2 implements Comparator<int[]> {
		@Override
		public int compare(int[] a, int[] b) {
			return a[0] - b[0];
		}
	}

	public static void addFactors(int num) {
		if (head[num] == 0) {
			for (int f = 1; f * f <= num; f++) {
				if (num % f == 0) {
					nxt[++cntf] = head[num];
					fac[cntf] = f;
					head[num] = cntf;
				}
			}
		}
	}

	public static void addQuery(int pos, int id, int l, int r, int op) {
		query2[++cntq][0] = pos;
		query2[cntq][1] = id;
		query2[cntq][2] = l;
		query2[cntq][3] = r;
		query2[cntq][4] = op;
	}

	public static void compute() {
		for (int i = 1, x; i <= n; i++) {
			x = arr[i];
			for (int e = head[x], f, other; e > 0; e = nxt[e]) {
				f = fac[e];
				other = x / f;
				fcnt[f]++;
				pre[i] += xcnt[f];
				if (other != f) {
					fcnt[other]++;
					pre[i] += xcnt[other];
				}
			}
			pre[i] += fcnt[x] + pre[i - 1];
			xcnt[x]++;
		}
		int winl = 1, winr = 0;
		for (int i = 1; i <= m; i++) {
			int jobl = query1[i][0];
			int jobr = query1[i][1];
			int id = query1[i][2];
			if (winr < jobr) {
				addQuery(winl - 1, id, winr + 1, jobr, -1);
				ans[id] += pre[jobr] - pre[winr];
			}
			if (winr > jobr) {
				addQuery(winl - 1, id, jobr + 1, winr, 1);
				ans[id] -= pre[winr] - pre[jobr];
			}
			winr = jobr;
			if (winl > jobl) {
				addQuery(winr, id, jobl, winl - 1, 1);
				ans[id] -= pre[winl - 1] - pre[jobl - 1];
			}
			if (winl < jobl) {
				addQuery(winr, id, winl, jobl - 1, -1);
				ans[id] += pre[jobl - 1] - pre[winl - 1];
			}
			winl = jobl;
		}
		Arrays.sort(query2, 1, cntq + 1, new Cmp2());
		Arrays.fill(fcnt, 0);
		for (int pos = 0, qi = 1; pos <= n && qi <= cntq; pos++) {
			if (pos >= 1) {
				int num = arr[pos];
				for (int e = head[num], f, other; e > 0; e = nxt[e]) {
					f = fac[e];
					other = num / f;
					fcnt[f]++;
					if (other != f) {
						fcnt[other]++;
					}
				}
				if (num > LIMIT) {
					for (int v = num; v <= maxv; v += num) {
						fcnt[v]++;
					}
				}
			}
			for (; qi <= cntq && query2[qi][0] == pos; qi++) {
				int id = query2[qi][1], l = query2[qi][2], r = query2[qi][3], op = query2[qi][4];
				for (int i = l; i <= r; i++) {
					ans[id] += (long) op * fcnt[arr[i]];
				}
			}
		}
		for (int v = 1; v <= LIMIT; v++) {
			cnt1[0] = cnt2[0] = 0;
			for (int i = 1; i <= n; i++) {
				cnt1[i] = cnt1[i - 1] + (arr[i] == v ? 1 : 0);
				cnt2[i] = cnt2[i - 1] + (arr[i] % v == 0 ? 1 : 0);
			}
			for (int i = 1; i <= cntq; i++) {
				int pos = query2[i][0], id = query2[i][1], l = query2[i][2], r = query2[i][3], op = query2[i][4];
				ans[id] += (long) op * cnt1[pos] * (cnt2[r] - cnt2[l - 1]);
			}
		}
	}

	public static void prepare() {
		int blen = (int) Math.sqrt(n);
		for (int i = 1; i <= n; i++) {
			bi[i] = (i - 1) / blen + 1;
			maxv = Math.max(maxv, arr[i]);
			addFactors(arr[i]);
		}
		Arrays.sort(query1, 1, m + 1, new Cmp1());
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
			query1[i][0] = in.nextInt();
			query1[i][1] = in.nextInt();
			query1[i][2] = i;
		}
		prepare();
		compute();
		for (int i = 2; i <= m; i++) {
			ans[query1[i][2]] += ans[query1[i - 1][2]];
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