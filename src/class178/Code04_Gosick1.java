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

	// l、r、id
	public static int[][] query1 = new int[MAXN][3];
	// pos、id、l、r、op
	public static int[][] query2 = new int[MAXN << 1][5];
	public static int cntq;

	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXF];
	public static int[] fac = new int[MAXF];
	public static int cntf;

	public static int[] bi = new int[MAXN];
	public static int[] fcnt = new int[MAXN];
	public static int[] xcnt = new int[MAXN];
	public static int[] pre = new int[MAXN];

	public static int[] cnt1 = new int[MAXN];
	public static int[] cnt2 = new int[MAXN];

	public static long[] ans = new long[MAXN];

	// 莫队奇偶排序优化常数时间
	public static class Cmp1 implements Comparator<int[]> {
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

	public static class Cmp2 implements Comparator<int[]> {
		@Override
		public int compare(int[] a, int[] b) {
			return a[0] - b[0];
		}
	}

	public static void addFactor(int v, int f) {
		nxt[++cntf] = head[v];
		fac[cntf] = f;
		head[v] = cntf;
	}

	public static void buildFactors(int x) {
		if (head[x] > 0) {
			return;
		}
		for (int f = 1, other; f * f <= x; f++) {
			if (x % f == 0) {
				addFactor(x, f);
				other = x / f;
				if (f != other) {
					addFactor(x, other);
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

	public static void prepare() {
		int blen = Math.max(n / (int) Math.sqrt(m), 1);
		for (int i = 1, num; i <= n; i++) {
			bi[i] = (i - 1) / blen + 1;
			num = arr[i];
			maxv = Math.max(maxv, num);
			buildFactors(num);
			for (int e = head[num], f; e > 0; e = nxt[e]) {
				f = fac[e];
				fcnt[f]++;
				pre[i] += xcnt[f];
			}
			pre[i] += pre[i - 1] + fcnt[num];
			xcnt[num]++;
		}
		Arrays.sort(query1, 1, m + 1, new Cmp1());
	}

	public static void compute() {
		int winl = 1, winr = 0;
		for (int i = 1; i <= m; i++) {
			int jobl = query1[i][0];
			int jobr = query1[i][1];
			int id = query1[i][2];
			if (winr < jobr) {
				ans[id] += pre[jobr] - pre[winr];
				addQuery(winl - 1, id, winr + 1, jobr, -1);
			}
			if (winr > jobr) {
				ans[id] -= pre[winr] - pre[jobr];
				addQuery(winl - 1, id, jobr + 1, winr, 1);
			}
			winr = jobr;
			if (winl > jobl) {
				ans[id] -= pre[winl - 1] - pre[jobl - 1];
				addQuery(winr, id, jobl, winl - 1, 1);
			}
			if (winl < jobl) {
				ans[id] += pre[jobl - 1] - pre[winl - 1];
				addQuery(winr, id, winl, jobl - 1, -1);
			}
			winl = jobl;
		}
		Arrays.sort(query2, 1, cntq + 1, new Cmp2());
		Arrays.fill(fcnt, 0);
		for (int i = 1, j = 1; i <= cntq; i++) {
			int pos = query2[i][0], id = query2[i][1], l = query2[i][2], r = query2[i][3], op = query2[i][4];
			while (j <= pos) {
				int x = arr[j++];
				buildFactors(x);
				for (int e = head[x], f; e > 0; e = nxt[e]) {
					f = fac[e];
					fcnt[f]++;
				}
				if (x > LIMIT) {
					for (int k = x; k <= maxv; k += x) {
						fcnt[k]++;
					}
				}
			}
			for (int k = l; k <= r; k++) {
				ans[id] += (long) op * fcnt[arr[k]];
			}
		}
		for (int i = 1; i <= LIMIT; i++) {
			cnt1[0] = 0;
			cnt2[0] = 0;
			for (int j = 1; j <= n; j++) {
				cnt1[j] = cnt1[j - 1] + (arr[j] == i ? 1 : 0);
				cnt2[j] = cnt2[j - 1] + (arr[j] % i == 0 ? 1 : 0);
			}
			for (int j = 1; j <= cntq; j++) {
				int pos = query2[j][0], id = query2[j][1], l = query2[j][2], r = query2[j][3], op = query2[j][4];
				ans[id] += (long) (cnt2[r] - cnt2[l - 1]) * cnt1[pos] * op;
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
