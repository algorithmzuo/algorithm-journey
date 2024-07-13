package class134;

// 高斯消元处理异或方程组模版
// 测试链接 : http://poj.org/problem?id=1681

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Code01_GaussEor {

	public static int MAXS = 230;

	public static int[][] mat = new int[MAXS][MAXS];

	public static int[] dir = { 0, 0, -1, 0, 1, 0 };

	public static int n, m, s;

	public static void prepare() {
		for (int i = 1; i <= s; i++) {
			for (int j = 1; j <= s + 1; j++) {
				mat[i][j] = 0;
			}
		}
		int cur, row, col;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				cur = i * m + j + 1;
				for (int d = 0; d <= 4; d++) {
					row = i + dir[d];
					col = j + dir[d + 1];
					if (row >= 0 && row < n && col >= 0 && col < m) {
						mat[cur][row * m + col + 1] = 1;
					}
				}
			}
		}
	}

	public static void gauss(int n) {
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {
				if (j < i && mat[j][j] == 1) {
					continue;
				}
				if (mat[j][i] == 1) {
					swap(i, j);
					break;
				}
			}
			if (mat[i][i] == 1) {
				for (int j = 1; j <= n; j++) {
					if (i != j && mat[j][i] == 1) {
						for (int k = i; k <= n + 1; k++) {
							mat[j][k] ^= mat[i][k];
						}
					}
				}
			}
		}
	}

	public static void swap(int a, int b) {
		int[] tmp = mat[a];
		mat[a] = mat[b];
		mat[b] = tmp;
	}

	public static boolean check(int i) {
		if (mat[i][i] != 1 || mat[i][s + 1] != 1) {
			return false;
		}
		for (int j = i + 1; j <= s; j++) {
			if (mat[i][j] != 0) {
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) throws IOException {
		Kattio io = new Kattio();
		int test = io.nextInt();
		char[] line;
		for (int t = 1; t <= test; t++) {
			n = io.nextInt();
			m = n;
			s = n * m;
			prepare();
			for (int i = 0, id = 1; i < n; i++) {
				line = io.next().toCharArray();
				for (int j = 0; j < m; j++, id++) {
					if (line[j] == 'y') {
						mat[id][s + 1] = 0;
					} else {
						mat[id][s + 1] = 1;
					}
				}
			}
			gauss(s);
			int sign = 1;
			for (int i = 1; i <= s; i++) {
				if (mat[i][i] == 0 && mat[i][s + 1] == 1) {
					sign = 0;
					break;
				}
			}
			if (sign == 0) {
				io.println("inf");
			} else {
				int ans = 0;
				for (int i = 1; i <= s; i++) {
					if (check(i)) {
						ans++;
					}
				}
				io.println(ans);
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