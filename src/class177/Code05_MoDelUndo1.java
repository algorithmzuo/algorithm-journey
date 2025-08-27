package class177;

// 只删回滚莫队入门题，java版
// 本题最优解为主席树，讲解158，题目2，已经讲述
// 给定一个长度为n的数组arr，一共有m条查询，格式如下
// 查询 l r : 打印arr[l..r]内没有出现过的最小自然数，注意0是自然数
// 0 <= n、m、arr[i] <= 2 * 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P4137
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;

public class Code05_MoDelUndo1 {

	public static int MAXN = 200001;
	public static int MAXB = 501;
	public static int n, m;
	public static int[] arr = new int[MAXN];
	public static int[][] query = new int[MAXN][3];

	public static int blen, bnum;
	public static int[] bi = new int[MAXN];
	public static int[] bl = new int[MAXB];

	public static int[] cnt = new int[MAXN + 1];
	public static int mex;
	public static int[] ans = new int[MAXN];

	public static class QueryCmp implements Comparator<int[]> {

		@Override
		public int compare(int[] a, int[] b) {
			if (bi[a[0]] != bi[b[0]]) {
				return bi[a[0]] - bi[b[0]];
			}
			return b[1] - a[1];
		}

	}

	public static void del(int num) {
		if (--cnt[num] == 0) {
			mex = Math.min(mex, num);
		}
	}

	public static void undo(int num) {
		cnt[num]++;
	}

	public static void compute() {
		for (int i = 1; i <= n; i++) {
			undo(arr[i]);
		}
		mex = 0;
		while (cnt[mex] != 0) {
			mex++;
		}
		int winl = 1, winr = n;
		for (int block = 1, qi = 1; block <= bnum && qi <= m; block++) {
			while (winl < bl[block]) {
				del(arr[winl++]);
			}
			int beforeJob = mex;
			for (; qi <= m && bi[query[qi][0]] == block; qi++) {
				int jobl = query[qi][0];
				int jobr = query[qi][1];
				int id = query[qi][2];
				while (winr > jobr) {
					del(arr[winr--]);
				}
				int backup = mex;
				while (winl < jobl) {
					del(arr[winl++]);
				}
				ans[id] = mex;
				mex = backup;
				while (winl > bl[block]) {
					undo(arr[--winl]);
				}
			}
			while (winr < n) {
				undo(arr[++winr]);
			}
			mex = beforeJob;
		}
	}

	public static void prepare() {
		blen = (int) Math.sqrt(n);
		bnum = (n + blen - 1) / blen;
		for (int i = 1; i <= n; i++) {
			bi[i] = (i - 1) / blen + 1;
		}
		for (int i = 1; i <= bnum; i++) {
			bl[i] = (i - 1) * blen + 1;
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
