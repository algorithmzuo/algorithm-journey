package class136;

// 第k小的异或和
// 给定一个长度为n的数组arr，arr中都是long类型的非负数，可能有重复值
// 在这些数中选取任意个，可以得到很多异或和，假设异或和的结果去重
// 返回第k小的异或和
// 1 <= n <= 10^5
// 0 <= arr[i] <= 2^50
// 测试链接 : https://loj.ac/p/114
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Code02_KthXor {

	public static int MAXN = 100001;

	public static int BIT = 50;

	public static long[] arr = new long[MAXN];

	public static int len;

	public static boolean zero;

	public static int n;

	// 高斯消元
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

	// 返回第k小的异或和
	public static long query(long k) {
		if (zero) {
			k--;
		}
		if (k == 0) {
			return 0;
		}
		if (k >= 1L << len) {
			return -1;
		}
		long ans = 0;
		for (int i = len; i >= 1; i--) {
			if ((k & 1) != 0) {
				ans ^= arr[i];
			}
			k >>= 1;
		}
		return ans;
	}

	public static void main(String[] args) throws IOException {
		// 题目会读取10^18范围内的long类型数字
		// 用StreamTokenizer可能无法正确读取，因为先变成double再转成long
		// 这里用Kattio类，具体看讲解019的代码中，Code05_Kattio文件
		// 有详细的说明
		Kattio io = new Kattio();
		n = io.nextInt();
		for (int i = 1; i <= n; i++) {
			arr[i] = io.nextLong();
		}
		compute();
		int q = io.nextInt();
		for (int i = 1; i <= q; i++) {
			long k = io.nextLong();
			io.println(query(k));
		}
		io.flush();
		io.close();
	}

	// Kattio类IO效率很好，但还是不如StreamTokenizer
	// 只有StreamTokenizer无法正确处理时，才考虑使用这个类
	// 参考链接 : https://oi-wiki.org/lang/java-pro/
	public static class Kattio extends PrintWriter {
		private BufferedReader r;
		private StringTokenizer st;

		public Kattio() {
			this(System.in, System.out);
		}

		public Kattio(InputStream i, OutputStream o) {
			super(o);
			r = new BufferedReader(new InputStreamReader(i));
		}

		public Kattio(String intput, String output) throws IOException {
			super(output);
			r = new BufferedReader(new FileReader(intput));
		}

		public String next() {
			try {
				while (st == null || !st.hasMoreTokens())
					st = new StringTokenizer(r.readLine());
				return st.nextToken();
			} catch (Exception e) {
			}
			return null;
		}

		public int nextInt() {
			return Integer.parseInt(next());
		}

		public double nextDouble() {
			return Double.parseDouble(next());
		}

		public long nextLong() {
			return Long.parseLong(next());
		}
	}

}
