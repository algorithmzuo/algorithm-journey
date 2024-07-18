package class134;

// 外星千足虫
// 一共有n种虫子，编号1~n，虫子腿为奇数认为是外星虫，偶数认为是地球虫
// 一共有m条虫子腿的测量记录，记录编号1~m
// 比如其中一条测量记录为，011 1，表示1号虫没参与，2号、3号虫参与了，总腿数为奇数
// 测量记录保证不会有自相矛盾的情况，但是可能有冗余的测量结果
// 也许拥有从第1号到第k号测量记录就够了，k+1~m号测量记录有或者没有都不影响测量结果
// 打印这个k，并且打印每种虫子到底是外星虫还是地球虫
// 如果使用所有的测量结果，依然无法确定每种虫子的属性，打印"Cannot Determine"
// 1 <= n <= 1000
// 1 <= m <= 2000
// 测试链接 : https://www.luogu.com.cn/problem/P2447
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Code03_AlienInsectLegs {

	public static int BIT = 64;

	public static int MAXN = 2002;

	public static int MAXM = MAXN / BIT + 1;

	public static long[][] mat = new long[MAXN][MAXM];

	public static int n, m, s;

	public static int need;

	// 高斯消元解决异或方程组模版 + 位图，很小的改写
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
			// 一旦没有唯一解，可以结束了
			if (get(i, i) == 0) {
				return;
			}
			for (int j = 1; j <= n; j++) {
				if (i != j && get(j, i) == 1) {
					// 因为列从1开始，所以从第1位状态开始才有用
					// 于是1~n+1列的状态，对应1~n+1位
					// 但是位图中永远有0位，只不过从来不使用
					// 于是一共有n+2位状态，都需要异或
					eor(i, j, n + 2);
				}
			}
		}
	}

	// 把row行，col列的状态设置成v
	public static void set(int row, int col, int v) {
		if (v == 0) {
			mat[row][col / BIT] &= ~(1L << (col % BIT));
		} else {
			mat[row][col / BIT] |= 1L << (col % BIT);
		}
	}

	// 得到row行，col列的状态
	public static int get(int row, int col) {
		return ((mat[row][col / BIT] >> (col % BIT)) & 1) == 1 ? 1 : 0;
	}

	// row2行状态 = row2行状态 ^ row1行状态
	// 状态一共有bits位
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
