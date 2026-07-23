package class136;

// 第k小的异或和，HUD的题目
// 这道题课上没有讲，核心逻辑和题目2一样，只是数据范围稍有不同
// 注意如下代码中，有注释的部分即可，其他代码都一样
// 给定一个长度为n的数组arr，arr中都是long类型的非负数，可能有重复值
// 在这些数中选取任意个，至少要选一个数字，可以得到很多异或和
// 假设异或和的结果去重，返回第k小的异或和
// 测试链接 : https://acm.hdu.edu.cn/showproblem.php?pid=3949
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code02_XOR {

	public static int MAXN = 100001;

	// 数字最大为10^18，需要更高的二进制位
	public static int BIT = 60;

	public static long[] arr = new long[MAXN];

	public static int len;

	public static boolean zero;

	public static int t, n;

	public static void compute() {
		len = 1;
		for (long i = BIT; i >= 0; i--) {
			for (int j = len; j <= n; j++) {
				if ((arr[j] & (1L << i)) != 0) {
					swap(j, len);
					break;
				}
			}
			if ((arr[len] & (1L << i)) != 0) {
				for (int j = 1; j <= n; j++) {
					if (j != len && (arr[j] & (1L << i)) != 0) {
						arr[j] ^= arr[len];
					}
				}
				len++;
			}
		}
		len--;
		zero = len != n;
	}

	public static void swap(int a, int b) {
		long tmp = arr[a];
		arr[a] = arr[b];
		arr[b] = tmp;
	}

	public static long query(long k) {
		if (zero && k == 1) {
			return 0;
		}
		if (zero) {
			k--;
		}
		if (k >= (1L << len)) {
			return -1;
		}
		long ans = 0;
		for (int i = len, j = 0; i >= 1; i--, j++) {
			if ((k & (1L << j)) != 0) {
				ans ^= arr[i];
			}
		}
		return ans;
	}

	public static void main(String[] args) throws IOException {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		// 注意有多组测试
		t = in.nextInt();
		for (int c = 1; c <= t; c++) {
			n = in.nextInt();
			for (int i = 1; i <= n; i++) {
				arr[i] = in.nextLong();
			}
			// 确保arr[n + 1]是0
			arr[n + 1] = 0;
			compute();
			out.println("Case #" + c + ":");
			int q = in.nextInt();
			for (int i = 1; i <= q; i++) {
				long k = in.nextLong();
				out.println(query(k));
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

		long nextLong() throws IOException {
			int c;
			do {
				c = readByte();
			} while (c <= ' ' && c != -1);
			boolean neg = false;
			if (c == '-') {
				neg = true;
				c = readByte();
			}
			long val = 0;
			while (c > ' ' && c != -1) {
				val = val * 10 + (c - '0');
				c = readByte();
			}
			return neg ? -val : val;
		}

	}

}
