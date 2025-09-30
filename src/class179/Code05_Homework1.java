package class179;

// 作业，java版
// 给定一个长度为n的数组arr，接下来有m条查询，格式如下
// 查询 l r a b : 打印arr[l..r]范围上的两个答案
//                答案1，数值范围在[a, b]的数字个数
//                答案2，数值范围在[a, b]的数字种数
// 1 <= n、m、arr[i] <= 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P4396
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;

public class Code05_Homework1 {

	public static int MAXN = 100001;
	public static int n, m;
	public static int[] arr = new int[MAXN];
	// l、r、a、b、id
	public static int[][] query = new int[MAXN][5];
	public static int[] bi = new int[MAXN];
	public static int[] bl = new int[MAXN];
	public static int[] br = new int[MAXN];

	public static int[] numCnt = new int[MAXN];
	public static int[] blockCnt = new int[MAXN];
	public static int[] blockKind = new int[MAXN];

	public static int[] ans1 = new int[MAXN];
	public static int[] ans2 = new int[MAXN];

	public static class QueryCmp implements Comparator<int[]> {
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

	public static void add(int x) {
		numCnt[x]++;
		blockCnt[bi[x]]++;
		if (numCnt[x] == 1) {
			blockKind[bi[x]]++;
		}
	}

	public static void del(int x) {
		numCnt[x]--;
		blockCnt[bi[x]]--;
		if (numCnt[x] == 0) {
			blockKind[bi[x]]--;
		}
	}

	public static void setAns(int a, int b, int id) {
		if (bi[a] == bi[b]) {
			for (int i = a; i <= b; i++) {
				if (numCnt[i] > 0) {
					ans1[id] += numCnt[i];
					ans2[id]++;
				}
			}
		} else {
			for (int i = a; i <= br[bi[a]]; i++) {
				if (numCnt[i] > 0) {
					ans1[id] += numCnt[i];
					ans2[id]++;
				}
			}
			for (int i = bl[bi[b]]; i <= b; i++) {
				if (numCnt[i] > 0) {
					ans1[id] += numCnt[i];
					ans2[id]++;
				}
			}
			for (int i = bi[a] + 1; i <= bi[b] - 1; i++) {
				ans1[id] += blockCnt[i];
				ans2[id] += blockKind[i];
			}
		}
	}

	public static void compute() {
		int winl = 1, winr = 0;
		for (int i = 1; i <= m; i++) {
			int jobl = query[i][0];
			int jobr = query[i][1];
			int joba = query[i][2];
			int jobb = query[i][3];
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
			setAns(joba, jobb, id);
		}
	}

	public static void prepare() {
		int nv = MAXN - 1;
		int blen = (int) Math.sqrt(nv);
		int bnum = (nv + blen - 1) / blen;
		for (int i = 1; i <= nv; i++) {
			bi[i] = (i - 1) / blen + 1;
		}
		for (int i = 1; i <= bnum; i++) {
			bl[i] = (i - 1) * blen + 1;
			br[i] = Math.min(i * blen, nv);
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
			query[i][2] = in.nextInt();
			query[i][3] = in.nextInt();
			query[i][4] = i;
		}
		prepare();
		compute();
		for (int i = 1; i <= m; i++) {
			out.println(ans1[i] + " " + ans2[i]);
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
