package class133;

// 高斯消元处理异或方程组
// 测试链接 : http://poj.org/problem?id=1681

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Code02_GaussEor {

	public static int MAXN = 230;

	public static int[][] mat = new int[MAXN][MAXN];

	public static int[] dir = { 0, 0, -1, 0, 1, 0 };

	public static int n, m;

	public static void build() {
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < m; j++) {
				mat[i][j] = 0;
			}
		}
		int cur, row, col;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				cur = i * n + j;
				for (int d = 0; d <= 4; d++) {
					row = i + dir[d];
					col = j + dir[d + 1];
					if (row >= 0 && row < n && col >= 0 && col < n) {
						mat[cur][row * n + col] = 1;
					}
				}
			}
		}
	}

	public static void main(String[] args) throws IOException {
		Kattio io = new Kattio();
		int test = io.nextInt();
		char[] line;
		for (int t = 1; t <= test; t++) {
			n = io.nextInt();
			m = n * n;
			build();
			for (int i = 0, s = 0; i < n; i++) {
				line = io.next().toCharArray();
				for (int j = 0; j < n; j++, s++) {
					if (line[j] == 'y') {
						mat[s][m] = 0;
					} else {
						mat[s][m] = 1;
					}
				}
			}
			if (gauss() == 0) {
				io.println("inf");
			} else {
				int ans = 0;
				for (int i = 0; i < m; i++) {
					if (mat[i][m] == 1) {
						ans++;
					}
				}
				io.println(ans);
			}
		}
		io.flush();
		io.close();
	}

	public static int gauss() {
		// size为有效元的大小
		int size = 0;
		for (int j = 0; j < m; j++) {
			for (int i = size; i < m; i++) {
				if (mat[i][j] == 1) {
					swap(size, i);
					break;
				}
			}
			if (mat[size][j] == 1) {
				for (int i = size + 1; i < m; i++) {
					if (mat[i][j] == 1) {
						for (int k = j; k <= m; k++) {
							mat[i][k] ^= mat[size][k];
						}
					}
				}
				size++;
			}
		}
		for (int i = size; i < m; i++) {
			if (mat[i][m] == 1) {
				return 0;
			}
		}
		for (int i = size - 1; i >= 0; i--) {
			for (int j = i + 1; j < m; j++) {
				mat[i][m] ^= mat[i][j] & mat[j][m];
			}
		}
		return 1;
	}

	public static void swap(int a, int b) {
		int[] tmp = mat[a];
		mat[a] = mat[b];
		mat[b] = tmp;
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