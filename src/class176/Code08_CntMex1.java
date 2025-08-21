package class176;

// 次数的mex，java版
// 给定一个长度为n的数组arr，一共有m条操作，操作格式如下
// 操作 1 l r     : arr[l..r]范围上，每种数字出现的次数，假设构成一个集合
//                  打印这个集合中，没出现的最小非负整数，就是打印次数的mex
// 操作 2 pos val : 把arr[pos]的值设置成val
// 1 <= n、m <= 10^5
// 1 <= arr[i]、val <= 10^9
// 测试链接 : https://www.luogu.com.cn/problem/CF940F
// 测试链接 : https://codeforces.com/problemset/problem/940/F
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;

public class Code08_CntMex1 {

	public static int MAXN = 100001;
	public static int n, m;
	public static int[] arr = new int[MAXN];
	public static int[] sorted = new int[MAXN << 1];

	public static int[] bi = new int[MAXN];
	public static int[][] query = new int[MAXN][4];
	public static int[][] update = new int[MAXN][2];
	public static int cntq, cntu;

	// cnt1[i] = j，表示i这种数出现了j次
	// cnt2[i] = j，表示出现次数为i的数有j种
	public static int[] cnt1 = new int[MAXN << 1];
	public static int[] cnt2 = new int[MAXN];

	public static int[] ans = new int[MAXN];

	public static class QueryCmp implements Comparator<int[]> {
		@Override
		public int compare(int[] a, int[] b) {
			if (bi[a[0]] != bi[b[0]]) {
				return bi[a[0]] - bi[b[0]];
			}
			if (bi[a[1]] != bi[b[1]]) {
				return bi[a[1]] - bi[b[1]];
			}
			return a[2] - b[2];
		}
	}

	public static int kth(int len, int num) {
		int left = 1, right = len, mid, ret = 0;
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

	public static void del(int num) {
		cnt2[cnt1[num]]--;
		cnt1[num]--;
		cnt2[cnt1[num]]++;
	}

	public static void add(int num) {
		cnt2[cnt1[num]]--;
		cnt1[num]++;
		cnt2[cnt1[num]]++;
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

	public static void compute() {
		int winl = 1, winr = 0, wint = 0;
		for (int i = 1; i <= cntq; i++) {
			int jobl = query[i][0];
			int jobr = query[i][1];
			int jobt = query[i][2];
			int id = query[i][3];
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
			int ret = 1;
			while (ret <= n && cnt2[ret] > 0) {
				ret++;
			}
			ans[id] = ret;
		}
	}

	public static void prepare() {
		int len = 0;
		for (int i = 1; i <= n; i++) {
			sorted[++len] = arr[i];
		}
		for (int i = 1; i <= cntu; i++) {
			sorted[++len] = update[i][1];
		}
		Arrays.sort(sorted, 1, len + 1);
		int tmp = 1;
		for (int i = 2; i <= len; i++) {
			if (sorted[tmp] != sorted[i]) {
				sorted[++tmp] = sorted[i];
			}
		}
		len = tmp;
		for (int i = 1; i <= n; i++) {
			arr[i] = kth(len, arr[i]);
		}
		for (int i = 1; i <= cntu; i++) {
			update[i][1] = kth(len, update[i][1]);
		}
		int blen = Math.max(1, (int) Math.pow(n, 2.0 / 3));
		for (int i = 1; i <= n; i++) {
			bi[i] = (i - 1) / blen + 1;
		}
		Arrays.sort(query, 1, cntq + 1, new QueryCmp());
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader();
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		for (int i = 1; i <= n; i++) {
			arr[i] = in.nextInt();
		}
		for (int i = 1, op, l, r, pos, val; i <= m; i++) {
			op = in.nextInt();
			if (op == 1) {
				l = in.nextInt();
				r = in.nextInt();
				cntq++;
				query[cntq][0] = l;
				query[cntq][1] = r;
				query[cntq][2] = cntu;
				query[cntq][3] = cntq;
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
		final private int BUFFER_SIZE = 1 << 16;
		private final InputStream in;
		private final byte[] buffer;
		private int ptr, len;

		public FastReader() {
			in = System.in;
			buffer = new byte[BUFFER_SIZE];
			ptr = len = 0;
		}

		private boolean hasNextByte() throws IOException {
			if (ptr < len)
				return true;
			ptr = 0;
			len = in.read(buffer);
			return len > 0;
		}

		private byte readByte() throws IOException {
			if (!hasNextByte())
				return -1;
			return buffer[ptr++];
		}

		public char nextChar() throws IOException {
			byte c;
			do {
				c = readByte();
				if (c == -1)
					return 0;
			} while (c <= ' ');
			char ans = 0;
			while (c > ' ') {
				ans = (char) c;
				c = readByte();
			}
			return ans;
		}

		public int nextInt() throws IOException {
			int num = 0;
			byte b = readByte();
			while (isWhitespace(b))
				b = readByte();
			boolean minus = false;
			if (b == '-') {
				minus = true;
				b = readByte();
			}
			while (!isWhitespace(b) && b != -1) {
				num = num * 10 + (b - '0');
				b = readByte();
			}
			return minus ? -num : num;
		}

		private boolean isWhitespace(byte b) {
			return b == ' ' || b == '\n' || b == '\r' || b == '\t';
		}
	}

}
