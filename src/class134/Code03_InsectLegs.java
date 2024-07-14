package class134;

// 外星千足虫
// 测试链接 : https://www.luogu.com.cn/problem/P2447

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Code03_InsectLegs {

	public static int BIT = 64;

	public static int MAXN = 2002;

	public static int MAXM = MAXN / BIT + 1;

	public static long[][] mat = new long[MAXN][MAXM];

	public static int n, m, s;

	public static int need;

	public static void gauss(int n) {
		need = 0;
		for (int i = 1; i <= n; i++) {
			for (int j = i; j <= n; j++) {
				if (get(j, i) == 1) {
					swap(i, j);
					need = Math.max(need, j);
					break;
				}
			}
			if (get(i, i) == 0) {
				return;
			}
			for (int j = 1; j <= n; j++) {
				if (i != j && get(j, i) == 1) {
					eor(i, j, n + 1);
				}
			}
		}
	}

	public static void set(int row, int col, int v) {
		if (v == 0) {
			mat[row][col / BIT] &= ~(1L << (col % BIT));
		} else {
			mat[row][col / BIT] |= 1L << (col % BIT);
		}
	}

	public static int get(int row, int col) {
		return ((mat[row][col / BIT] >> (col % BIT)) & 1) == 1 ? 1 : 0;
	}

	public static void eor(int row1, int row2, int bits) {
		for (int k = 0; k <= bits / BIT; k++) {
			mat[row2][k] ^= mat[row1][k];
		}
	}

	public static void swap(int a, int b) {
		long[] tmp = mat[a];
		mat[a] = mat[b];
		mat[b] = tmp;
	}

	public static void main(String[] args) throws IOException {
		Kattio io = new Kattio();
		n = io.nextInt();
		m = io.nextInt();
		s = Math.max(n, m);
		for (int i = 1; i <= m; i++) {
			char[] line = io.next().toCharArray();
			for (int j = 1; j <= n; j++) {
				set(i, j, line[j - 1] - '0');
			}
			set(i, s + 1, io.nextInt());
		}
		gauss(s);
		int sign = 1;
		for (int i = 1; i <= n; i++) {
			if (get(i, i) == 0) {
				sign = 0;
				break;
			}
		}
		if (sign == 0) {
			io.println("Cannot Determine");
		} else {
			io.println(need);
			for (int i = 1; i <= n; i++) {
				if (get(i, s + 1) == 1) {
					io.println("?y7M#");
				} else {
					io.println("Earth");
				}
			}
		}
		io.flush();
		io.close();
	}

	// 读取字符串推荐用StringTokenizer
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
