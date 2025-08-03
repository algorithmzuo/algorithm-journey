package class175;

// 直到倒下，java版
// 测试链接 : https://www.luogu.com.cn/problem/CF786C
// 测试链接 : https://codeforces.com/problemset/problem/786/C
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.io.IOException;

public class Code06_TillCollapse1 {

	public static int MAXN = 100001;
	public static int n, blen = 1000;
	public static int[] arr = new int[MAXN];
	public static int[] num = new int[MAXN];
	public static int[] ans = new int[MAXN];

	private static int query(int x) {
		Arrays.fill(num, 0);
		int cnt = 0, lst = 1, tot = 0;
		for (int i = 1; i <= n; i++) {
			if (num[arr[i]] == 0) {
				cnt++;
				num[arr[i]] = 1;
			}
			if (cnt > x) {
				tot++;
				for (int j = lst; j <= i; j++) {
					num[arr[j]] = 0;
				}
				lst = i;
				num[arr[i]] = 1;
				cnt = 1;
			}
		}
		if (cnt > 0) {
			tot++;
		}
		return tot;
	}

	public static void compute() {
		Arrays.fill(ans, 1, n + 1, -1);
		for (int i = 1; i <= blen; i++) {
			ans[i] = query(i);
		}
		for (int i = blen + 1; i <= n;) {
			ans[i] = query(i);
			int l = i, r = n, find = i;
			while (l <= r) {
				int mid = (l + r) >> 1;
				if (query(mid) >= ans[i]) {
					find = mid;
					l = mid + 1;
				} else {
					r = mid - 1;
				}
			}
			i = find + 1;
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(System.out);
		n = in.nextInt();
		for (int i = 1; i <= n; i++) {
			arr[i] = in.nextInt();
		}
		compute();
		for (int i = 1; i <= n; i++) {
			if (ans[i] == -1) {
				ans[i] = ans[i - 1];
			}
			out.print(ans[i] + " ");
		}
		out.println();
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
