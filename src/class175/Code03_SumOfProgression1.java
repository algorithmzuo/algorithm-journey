package class175;

// 等差数列求和，java版
// 一共有t组测试，每组测试遵循同样的设定
// 给定一个长度为n的数组arr，接下来有q条查询，查询格式如下
// 查询 s d k : arr[s]作为第1项、arr[s + 1d]作为第2项、arr[s + 2d]作为第3项...
//             每项的值 * 项的编号，一共k项都累加起来，打印累加和
// 1 <= n <= 10^5
// 1 <= q <= 2 * 10^5
// 测试链接 : https://www.luogu.com.cn/problem/CF1921F
// 测试链接 : https://codeforces.com/problemset/problem/1921/F
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code03_SumOfProgression1 {

	public static int MAXN = 100001;
	public static int MAXB = 401;
	public static int t, n, q, blen;
	public static int[] arr = new int[MAXN];
	public static long[][] f = new long[MAXB][MAXN];
	public static long[][] g = new long[MAXB][MAXN];

	public static long query(int s, int d, int k) {
		long ans = 0;
		if (d <= blen) {
			ans = g[d][s];
			if (s + d * k <= n) {
				ans = ans - g[d][s + d * k] - f[d][s + d * k] * k;
			}
		} else {
			for (int i = 1; i <= k; i++) {
				ans += 1L * arr[s + (i - 1) * d] * i;
			}
		}
		return ans;
	}

	public static void prepare() {
		blen = (int) Math.sqrt(n);
		for (int d = 1; d <= blen; d++) {
			for (int s = n; s >= 1; s--) {
				f[d][s] = arr[s] + (s + d > n ? 0 : f[d][s + d]);
			}
		}
		for (int d = 1; d <= blen; d++) {
			for (int s = n; s >= 1; s--) {
				g[d][s] = f[d][s] + (s + d > n ? 0 : g[d][s + d]);
			}
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		t = in.nextInt();
		for (int c = 1; c <= t; c++) {
			n = in.nextInt();
			q = in.nextInt();
			for (int i = 1; i <= n; i++) {
				arr[i] = in.nextInt();
			}
			prepare();
			for (int i = 1, s, d, k; i <= q; i++) {
				s = in.nextInt();
				d = in.nextInt();
				k = in.nextInt();
				out.println(query(s, d, k));
			}
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
