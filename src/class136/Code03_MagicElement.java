package class136;

// 魔法元素
// 测试链接 : https://www.luogu.com.cn/problem/P4570
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Code03_MagicElement {

	public static int MAXN = 1001;

	public static int MAXM = 64;

	public static long[][] arr = new long[MAXN][2];

	public static long[] basis = new long[MAXM];

	public static int n, m;

	public static long ans;

	public static void maxbit() {
		long max = arr[1][0];
		for (int i = 2; i <= n; i++) {
			max = Math.max(max, arr[i][0]);
		}
		m = 0;
		while ((max >> (m + 1)) != 0) {
			m++;
		}
	}

	// 普通消元
	public static void compute() {
		ans = 0;
		Arrays.sort(arr, 1, n + 1, (a, b) -> a[1] >= b[1] ? -1 : 1);
		for (int i = 1; i <= n; i++) {
			long num = arr[i][0];
			for (int j = m; j >= 0; j--) {
				if (num >> j == 1) {
					if (basis[j] == 0) {
						basis[j] = num;
						ans += arr[i][1];
						break;
					}
					num ^= basis[j];
				}
			}
		}
	}

	public static void main(String[] args) throws IOException {
		// 题目会读取10^18范围内的long类型数字
		// 用StreamTokenizer可能无法正确读取，因为先变成double再转成long
		// 这里用Kattio类，具体看讲解019的代码中，Code05_Kattio文件
		// 有详细的说明
		Kattio io = new Kattio();
		n = io.nextInt();
		for (int i = 1; i <= n; i++) {
			arr[i][0] = io.nextLong();
			arr[i][1] = io.nextInt();
		}
		maxbit();
		compute();
		io.println(ans);
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
