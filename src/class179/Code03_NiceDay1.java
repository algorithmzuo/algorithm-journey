package class179;

// 美好的每一天，java版
// 给定一个长度为n的字符串str，其中都是小写字母
// 如果一个子串重新排列字符之后能成为回文串，那么该子串叫做达标子串
// 接下来有m条操作，格式为 l r : 打印str[l..r]范围上有多少达标子串
// 1 <= n、m <= 6 * 10^4
// 测试链接 : https://www.luogu.com.cn/problem/P3604
// 提交以下的code，提交时请把类名改成"Main"
// java实现的逻辑一定是正确的，但是本题卡常，无法通过所有测试用例
// 想通过用C++实现，本节课Code03_NiceDay2文件就是C++的实现
// 两个版本的逻辑完全一样，C++版本可以通过所有测试

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;

public class Code03_NiceDay1 {

	public static int MAXN = 60002;
	public static int MAXV = 1 << 26;
	public static int n, m;
	public static int[] arr = new int[MAXN];
	public static int[][] query = new int[MAXN][3];
	public static int[] bi = new int[MAXN];

	public static short[] cnt = new short[MAXV];
	public static long num = 0;
	public static long[] ans = new long[MAXN];

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

	public static void add(int s) {
		num += cnt[s];
		cnt[s]++;
		for (int i = 0; i < 26; i++) {
			num += cnt[s ^ (1 << i)];
		}
	}

	public static void del(int s) {
		cnt[s]--;
		num -= cnt[s];
		for (int i = 0; i < 26; i++) {
			num -= cnt[s ^ (1 << i)];
		}
	}

	public static void compute() {
		int winl = 1, winr = 0;
		for (int i = 1; i <= m; i++) {
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
			ans[id] = num;
		}
	}

	public static void prepare() {
		for (int i = 1; i <= n; i++) {
			arr[i] ^= arr[i - 1];
		}
		for (int i = n; i >= 0; i--) {
			arr[i + 1] = arr[i];
		}
		n++;
		int blen = (int) Math.sqrt(n);
		for (int i = 1; i <= n; i++) {
			bi[i] = (i - 1) / blen + 1;
		}
		for (int i = 1; i <= m; i++) {
			query[i][1]++;
		}
		Arrays.sort(query, 1, m + 1, new QueryCmp());
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader();
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		char c;
		for (int i = 1; i <= n; i++) {
			c = in.nextLowerCase();
			arr[i] = 1 << (c - 'a');
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
