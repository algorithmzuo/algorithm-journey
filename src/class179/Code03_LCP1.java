package class179;

// 区间lcp达标对，java版
// 给定一个长度为n的字符串str，还有一个参数k
// 位置a开头和位置b开头的字符串，如果最长公共前缀的长度 >= k，那么(a, b)构成lcp达标对
// 构成lcp达标对，必须是不同的位置，并且(a, b)和(b, a)只算一个达标对，不要重复统计
// 接下来有m条查询，格式为 l r : str[l..r]范围上，可以任选开头位置，打印lcp达标对的数量
// 1 <= n、k <= 3 * 10^6       1 <= m <= 10^5
// 1 <= n * n * m <= 10^15    字符集为 f z o u t s y
// 测试链接 : https://www.luogu.com.cn/problem/P5112
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;

public class Code03_LCP1 {

	public static int MAXN = 3000001;
	public static int MAXM = 100001;
	public static int n, m, k;
	public static int len, cntq, cntv;
	public static char[] str = new char[MAXN];
	public static int[][] query = new int[MAXM][3];

	// 字符串哈希
	public static int base = 499;
	public static long[] basePower = new long[MAXN];
	public static long[] hashValue = new long[MAXN];

	// 哈希值离散化，用哈希值替代字符串，再用排名替代哈希值
	public static long[] val = new long[MAXN];
	public static long[] sorted = new long[MAXN];
	public static int[] arr = new int[MAXN];
	public static int[] bi = new int[MAXN];

	public static int[] cnt = new int[MAXN];
	public static long curAns;
	public static long[] ans = new long[MAXM];

	public static int kth(long num) {
		int left = 1, right = cntv, mid, ret = 0;
		while (left <= right) {
			mid = (left + right) >> 1;
			if (sorted[mid] <= num) {
				ret = mid;
				left = mid + 1;
			} else {
				right = mid - 1;
			}
		}
		return ret;
	}

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
		curAns += cnt[x];
		cnt[x]++;
	}

	public static void del(int x) {
		cnt[x]--;
		curAns -= cnt[x];
	}

	public static void compute() {
		int winl = 1, winr = 0;
		for (int i = 1; i <= cntq; i++) {
			int jobl = query[i][0];
			int jobr = query[i][1];
			int id = query[i][2];
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
			ans[id] = curAns;
		}
	}

	public static void prepare() {
		basePower[0] = 1;
		for (int i = 1; i <= n; i++) {
			basePower[i] = basePower[i - 1] * base;
			hashValue[i] = hashValue[i - 1] * base + (str[i] - 'a' + 1);
		}
		for (int l = 1, r = k; r <= n; l++, r++) {
			val[l] = hashValue[r] - hashValue[l - 1] * basePower[r - l + 1];
		}
		for (int i = 1; i <= len; i++) {
			sorted[i] = val[i];
		}
		Arrays.sort(sorted, 1, len + 1);
		cntv = 1;
		for (int i = 2; i <= len; i++) {
			if (sorted[cntv] != sorted[i]) {
				sorted[++cntv] = sorted[i];
			}
		}
		for (int i = 1; i <= len; i++) {
			arr[i] = kth(val[i]);
		}
		// 优化块长
		int blen = Math.max(1, (int) ((double) n / Math.sqrt(m)));
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
		k = in.nextInt();
		for (int i = 1; i <= n; i++) {
			str[i] = in.nextLowerCase();
		}
		// 有效开头的个数，剩下的开头舍弃
		len = n - k + 1;
		cntq = 0;
		for (int i = 1, l, r; i <= m; i++) {
			l = in.nextInt();
			r = in.nextInt();
			// 过滤查询并调整查询参数
			if (l <= len) {
				query[++cntq][0] = l;
				query[cntq][1] = Math.min(r, len);
				query[cntq][2] = i;
			}
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

		public char nextLowerCase() throws IOException {
			int c;
			while (true) {
				c = readByte();
				if (c >= 'a' && c <= 'z')
					return (char) c;
			}
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
