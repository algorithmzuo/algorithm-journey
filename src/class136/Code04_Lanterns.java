package class136;

// 彩灯
// 一共有n个灯泡，开始都是不亮的状态，有m个开关
// 每个开关能改变若干灯泡的状态，改变是指，亮变不亮、不亮变亮
// 比如n=5，某个开关为XXOOO，表示这个开关只能改变后3个灯泡的状态
// 可以随意使用开关，返回有多少种亮灯的组合，全不亮也算一种组合
// 答案可能很大，对 2008 取模
// 1 <= n <= 50
// 1 <= m <= 50
// 测试链接 : https://www.luogu.com.cn/problem/P3857
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Code04_Lanterns {

	public static int MAXN = 51;

	public static int MOD = 2008;

	public static long[] arr = new long[MAXN];

	public static long[] basis = new long[MAXN];

	public static int n, m;

	// 普通消元
	// 计算线性基的大小
	public static int compute() {
		int size = 0;
		for (int i = 1; i <= m; i++) {
			if (insert(arr[i])) {
				size++;
			}
		}
		return size;
	}

	// 线性基里插入num，如果线性基增加了返回true，否则返回false
	public static boolean insert(long num) {
		for (int i = n; i >= 0; i--) {
			if (num >> i == 1) {
				if (basis[i] == 0) {
					basis[i] = num;
					return true;
				}
				num ^= basis[i];
			}
		}
		return false;
	}

	public static void main(String[] args) throws IOException {
		Kattio io = new Kattio();
		n = io.nextInt() - 1;
		m = io.nextInt();
		char[] s;
		for (int i = 1; i <= m; i++) {
			s = io.next().toCharArray();
			long num = 0;
			for (int j = 0; j <= n; j++) {
				if (s[j] == 'O') {
					num |= 1L << j;
				}
			}
			arr[i] = num;
		}
		int size = compute();
		io.println((1L << size) % MOD);
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
