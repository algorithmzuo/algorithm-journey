package class134;

// 工具工厂
// 测试链接 : http://poj.org/problem?id=2947

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Code04_WidgetFactory {

	public static int MOD = 7;

	public static int MAXN = 302;

	public static int[][] mat = new int[MAXN][MAXN];

	public static int[] ans = new int[MAXN];

	public static int n, m, s;

	public static int gcd(int a, int b) {
		return b == 0 ? a : gcd(b, a % b);
	}

	public static int lcm(int a, int b) {
		return a * b / gcd(a, b);
	}

	public static int[] inv = new int[MOD];

	public static void inv() {
		inv[1] = 1;
		for (int i = 2; i < MOD; i++) {
			inv[i] = (int) (MOD - (long) inv[MOD % i] * (MOD / i) % MOD);
		}
	}

	public static int day(String str) {
		if (str.equals("MON"))
			return 1;
		if (str.equals("TUE"))
			return 2;
		if (str.equals("WED"))
			return 3;
		if (str.equals("THU"))
			return 4;
		if (str.equals("FRI"))
			return 5;
		if (str.equals("SAT"))
			return 6;
		if (str.equals("SUN"))
			return 7;
		return 0;
	}

	public static void prepare() {
		for (int i = 1; i <= s; i++) {
			for (int j = 1; j <= s + 1; j++) {
				mat[i][j] = 0;
			}
		}
	}

	// 高斯消元处理同余方程组模版
	public static void gauss() {
		for (int i = 1; i <= s; i++) {
			int max = i;
			for (int j = 1; j <= s; j++) {
				if (j < i && mat[j][j] != 0) {
					continue;
				}
				if (mat[j][i] > mat[max][i]) {
					max = j;
				}
			}
			swap(i, max);
			if (mat[i][i] != 0) {
				for (int j = 1; j <= s; j++) {
					if (i != j && mat[j][i] != 0) {
						int lcm = lcm(mat[j][i], mat[i][i]);
						int a = lcm / mat[j][i];
						int b = lcm / mat[i][i];
						if (mat[j][i] * mat[i][i] < 0) {
							b = -b;
						}
						if (j < i) {
							mat[j][j] = (mat[j][j] * a) % MOD;
						}
						for (int k = i; k <= s + 1; k++) {
							mat[j][k] = ((mat[j][k] * a - mat[i][k] * b) % MOD + MOD) % MOD;
						}
					}
				}
			}
		}
		for (int i = 1; i <= s; i++) {
			if (mat[i][i] != 0) {
				mat[i][s + 1] = (mat[i][s + 1] * inv[mat[i][i]]) % MOD;
			}
		}
	}

	public static void swap(int a, int b) {
		int[] tmp = mat[a];
		mat[a] = mat[b];
		mat[b] = tmp;
	}

	public static void main(String[] args) throws IOException {
		inv();
		Kattio io = new Kattio();
		while (true) {
			n = io.nextInt();
			m = io.nextInt();
			if (n == 0 && m == 0) {
				break;
			}
			s = Math.max(n, m);
			prepare();
			for (int i = 1; i <= m; i++) {
				int k = io.nextInt();
				String st = io.next();
				String et = io.next();
				while (k-- > 0) {
					int type = io.nextInt();
					mat[i][type] = (mat[i][type] + 1) % MOD;
				}
				mat[i][s + 1] = ((day(et) - day(st) + 1) % MOD + MOD) % MOD;
			}
			gauss();
			int sign = 1;
			for (int i = 1; i <= n; i++) {
				if (mat[i][i] == 0) {
					sign = 0;
				}
			}
			for (int i = 1; i <= s; i++) {
				if (mat[i][i] == 0 && mat[i][s + 1] != 0) {
					sign = -1;
				}
			}
			if (sign == -1) {
				io.println("Inconsistent data.");
			} else if (sign == 0) {
				io.println("Multiple solutions.");
			} else {
				for (int i = 1; i <= n; i++) {
					if (mat[i][s + 1] < 3) {
						mat[i][s + 1] += 7;
					}
					io.print(mat[i][s + 1] + (i == n ? "\n" : " "));
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