package class178;

// 莫队二次离线入门题，java版
// 测试链接 : https://www.luogu.com.cn/problem/P4887
// 提交以下的code，提交时请把类名改成"Main"
// java实现的逻辑一定是正确的，但是本题卡常，无法通过所有测试用例
// 想通过用C++实现，本节课Code01_MoOfflineTwice2文件就是C++的实现
// 两个版本的逻辑完全一样，C++版本可以通过所有测试

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;

public class Code01_MoOfflineTwice1 {

	public static int MAXN = 100001;
	public static int MAXQ = 200001;
	public static int MAXV = 1 << 14;
	public static int n, m, k;
	public static int[] arr = new int[MAXN];
	public static int[][] query = new int[MAXN][3];

	public static int[] kOneArr = new int[MAXV];
	public static int cntk;

	public static int[] bi = new int[MAXN];
	public static int[] pre = new int[MAXN];
	public static int[] cnt = new int[MAXV];

	public static int[] head = new int[MAXN];
	public static int[] next = new int[MAXQ];
	public static int[] qid = new int[MAXQ];
	public static int[] ql = new int[MAXQ];
	public static int[] qr = new int[MAXQ];
	public static int[] qop = new int[MAXQ];
	public static int cntq;

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

	public static int countOne(int num) {
		int ret = 0;
		while (num > 0) {
			ret++;
			num -= num & -num;
		}
		return ret;
	}

	public static void addQuery(int pos, int id, int l, int r, int op) {
		next[++cntq] = head[pos];
		qid[cntq] = id;
		ql[cntq] = l;
		qr[cntq] = r;
		qop[cntq] = op;
		head[pos] = cntq;
	}

	public static void prepare() {
		int blen = (int) Math.sqrt(n);
		for (int i = 1; i <= n; i++) {
			bi[i] = (i - 1) / blen + 1;
		}
		Arrays.sort(query, 1, m + 1, new QueryCmp());
		for (int v = 0; v < MAXV; v++) {
			if (countOne(v) == k) {
				kOneArr[++cntk] = v;
			}
		}
	}

	public static void compute() {
		for (int i = 1; i <= n; i++) {
			pre[i] = cnt[arr[i]];
			for (int j = 1; j <= cntk; j++) {
				cnt[arr[i] ^ kOneArr[j]]++;
			}
		}
		// 莫队
		int winl = 1, winr = 0;
		for (int i = 1; i <= m; i++) {
			int jobl = query[i][0];
			int jobr = query[i][1];
			int id = query[i][2];
			if (winr < jobr) {
				addQuery(winl - 1, id, winr + 1, jobr, -1);
			}
			while (winr < jobr) {
				ans[id] += pre[++winr];
			}
			if (winr > jobr) {
				addQuery(winl - 1, id, jobr + 1, winr, 1);
			}
			while (winr > jobr) {
				ans[id] -= pre[winr--];
			}
			if (winl > jobl) {
				addQuery(winr, id, jobl, winl - 1, 1);
			}
			while (winl > jobl) {
				ans[id] -= pre[--winl];
			}
			if (winl < jobl) {
				addQuery(winr, id, winl, jobl - 1, -1);
			}
			while (winl < jobl) {
				ans[id] += pre[winl++];
			}
		}
		// 第二次离线
		Arrays.fill(cnt, 0);
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= cntk; j++) {
				cnt[arr[i] ^ kOneArr[j]]++;
			}
			for (int q = head[i]; q > 0; q = next[q]) {
				int id = qid[q], l = ql[q], r = qr[q], op = qop[q];
				for (int j = l; j <= r; j++) {
					// 计算j 对 1..i范围的贡献
					// 此时1..i范围上的数字都更新过cnt
					if (j <= i && k == 0) {
						// j在1..i范围上，此时又有k==0
						// 那么arr[j]一定更新过cnt，并且(arr[j], arr[j])一定算进贡献了
						// 但是题目要求的二元组必须是不同位置，所以贡献要进行减1修正
						ans[id] += (long) op * (cnt[arr[j]] - 1);
					} else {
						// 要么j不在1..i范围上，arr[j]没更新过cnt
						// 要么k!=0，(arr[j], arr[j])无法被算成贡献
						// 无论哪种情况，贡献都是正确的，不用进行减1修正
						ans[id] += (long) op * cnt[arr[j]];
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
		for (int i = 2; i <= m; i++) {
			ans[query[i][2]] += ans[query[i - 1][2]];
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
