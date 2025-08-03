package class175;

// 数组查询，java版
// 给定一个长度为n的数组arr，接下来有q条查询，查询格式如下
// 查询 p k : p 不断变成 p + arr[p] + k，直到 p > n 停止，打印操作次数
// 1 <= n、q <= 10 ^ 5
// 测试链接 : https://www.luogu.com.cn/problem/CF797E
// 测试链接 : https://codeforces.com/problemset/problem/797/E
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code02_ArrayQueries1 {

	public static int MAXN = 100001;
	public static int MAXB = 401;
	public static int n, q, blen;
	public static int[] arr = new int[MAXN];
	public static int[][] dp = new int[MAXN][MAXB];

	public static int query(int p, int k) {
		if (k <= blen) {
			return dp[p][k];
		}
		int ans = 0;
		while (p <= n) {
			ans++;
			p += arr[p] + k;
		}
		return ans;
	}

	public static void prepare() {
		blen = (int) Math.sqrt(n);
		for (int p = n; p >= 1; p--) {
			for (int k = 1; k <= blen; k++) {
				dp[p][k] = 1 + (p + arr[p] + k > n ? 0 : dp[p + arr[p] + k][k]);
			}
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		for (int i = 1; i <= n; i++) {
			arr[i] = in.nextInt();
		}
		prepare();
		q = in.nextInt();
		for (int i = 1, p, k; i <= q; i++) {
			p = in.nextInt();
			k = in.nextInt();
			out.println(query(p, k));
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
