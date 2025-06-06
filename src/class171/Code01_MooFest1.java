package class171;

// 奶牛音量和，java版
// 一共有n只奶牛，每只奶牛给定，听力v、坐标x
// 任何一对奶牛产生的音量 = max(vi, vj) * 两只奶牛的距离
// 一共有n * (n - 1) / 2对奶牛，打印音量总和
// 1 <= n、v、x <= 5 * 10^4
// 测试链接 : https://www.luogu.com.cn/problem/P5094
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

public class Code01_MooFest1 {

	public static int MAXN = 50001;
	public static int n;
	// 听力v、位置x
	public static int[][] arr = new int[MAXN][2];
	// 归并排序需要
	public static int[][] tmp = new int[MAXN][2];

	public static void clone(int[] a, int[] b) {
		a[0] = b[0];
		a[1] = b[1];
	}

	public static long merge(int l, int m, int r) {
		int p1, p2;
		long sum1 = 0, sum2 = 0, ans = 0;
		for (p1 = l; p1 <= m; p1++) {
			sum1 += arr[p1][1];
		}
		for (p1 = l - 1, p2 = m + 1; p2 <= r; p2++) {
			while (p1 + 1 <= m && arr[p1 + 1][1] < arr[p2][1]) {
				p1++;
				sum1 -= arr[p1][1];
				sum2 += arr[p1][1];
			}
			ans += (1L * (p1 - l + 1) * arr[p2][1] - sum2 + sum1 - 1L * (m - p1) * arr[p2][1]) * arr[p2][0];
		}
		p1 = l;
		p2 = m + 1;
		int i = l;
		while (p1 <= m && p2 <= r) {
			clone(tmp[i++], arr[p1][1] <= arr[p2][1] ? arr[p1++] : arr[p2++]);
		}
		while (p1 <= m) {
			clone(tmp[i++], arr[p1++]);
		}
		while (p2 <= r) {
			clone(tmp[i++], arr[p2++]);
		}
		for (i = l; i <= r; i++) {
			clone(arr[i], tmp[i]);
		}
		return ans;
	}

	public static long cdq(int l, int r) {
		if (l == r) {
			return 0;
		}
		int mid = (l + r) / 2;
		return cdq(l, mid) + cdq(mid + 1, r) + merge(l, mid, r);
	}

	public static void main(String[] args) throws IOException {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		for (int i = 1; i <= n; i++) {
			arr[i][0] = in.nextInt();
			arr[i][1] = in.nextInt();
		}
		Arrays.sort(arr, 1, n + 1, (a, b) -> a[0] - b[0]);
		out.println(cdq(1, n));
		out.flush();
		out.close();
	}

	// 读写工具类
	static class FastReader {
		private final byte[] buffer = new byte[1 << 20];
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
