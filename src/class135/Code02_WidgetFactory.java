package class135;

// 工具工厂
// 一共有n种工具，编号1~n，一共有m条记录，其中一条记录格式如下：
// 4 WED SUN 13 18 1 13
// 表示有个工人一共加工了4件工具，从某个星期三开始工作，到某个星期天结束工作
// 加工的工具依次为13号、18号、1号、13号
// 每个工人在工作期间不休息，每件工具都是串行加工的，完成一件后才开始下一件
// 每种工具制作天数是固定的，并且任何工具的制作天数最少3天、最多9天
// 但该数据丢失了，所以现在需要根据记录，推断出每种工具的制作天数
// 如果记录之间存在矛盾，打印"Inconsistent data."
// 如果记录无法确定每种工具的制作天数，打印"Multiple solutions."
// 如果记录能够确定每种工具的制作天数，打印所有结果
// 1 <= n、m <= 300
// 测试链接 : http://poj.org/problem?id=2947
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Code02_WidgetFactory {

	public static int MOD = 7;

	public static int MAXN = 302;

	public static int[][] mat = new int[MAXN][MAXN];

	public static int[] inv = new int[MOD];

	public static String[] days = { "MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN" };

	public static int n, m, s;

	public static void inv() {
		inv[1] = 1;
		for (int i = 2; i < MOD; i++) {
			inv[i] = (int) (MOD - (long) inv[MOD % i] * (MOD / i) % MOD);
		}
	}

	public static int gcd(int a, int b) {
		return b == 0 ? a : gcd(b, a % b);
	}

	public static void prepare() {
		for (int i = 1; i <= s; i++) {
			for (int j = 1; j <= s + 1; j++) {
				mat[i][j] = 0;
			}
		}
	}

	public static int day(String str) {
		for (int i = 0; i < days.length; i++) {
			if (str.equals(days[i])) {
				return i;
			}
		}
		return -1;
	}

	// 高斯消元解决同余方程组模版，保证初始系数没有负数
	public static void gauss(int n) {
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {
				if (j < i && mat[j][j] != 0) {
					continue;
				}
				if (mat[j][i] != 0) {
					swap(i, j);
					break;
				}
			}
			if (mat[i][i] != 0) {
				for (int j = 1; j <= n; j++) {
					if (i != j && mat[j][i] != 0) {
						int gcd = gcd(mat[j][i], mat[i][i]);
						int a = mat[i][i] / gcd;
						int b = mat[j][i] / gcd;
						if (j < i && mat[j][j] != 0) {
							for (int k = j; k < i; k++) {
								mat[j][k] = (mat[j][k] * a) % MOD;
							}
						}
						for (int k = i; k <= n + 1; k++) {
							mat[j][k] = ((mat[j][k] * a - mat[i][k] * b) % MOD + MOD) % MOD;
						}
					}
				}
			}
		}
		for (int i = 1; i <= n; i++) {
			if (mat[i][i] != 0) {
				boolean flag = false;
				for (int j = i + 1; j <= n; j++) {
					if (mat[i][j] != 0) {
						flag = true;
						break;
					}
				}
				if (!flag) {
					mat[i][n + 1] = (mat[i][n + 1] * inv[mat[i][i]]) % MOD;
					mat[i][i] = 1;
				}
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
		n = io.nextInt();
		m = io.nextInt();
		while (n != 0 && m != 0) {
			s = Math.max(n, m);
			prepare();
			for (int i = 1; i <= m; i++) {
				int k = io.nextInt();
				String st = io.next();
				String et = io.next();
				for (int j = 1; j <= k; j++) {
					int tool = io.nextInt();
					mat[i][tool] = (mat[i][tool] + 1) % MOD;
				}
				mat[i][s + 1] = ((day(et) - day(st) + 1) % MOD + MOD) % MOD;
			}
			gauss(s);
			int sign = 1;
			for (int i = 1; i <= s; i++) {
				if (mat[i][i] == 0 && mat[i][s + 1] != 0) {
					sign = -1;
					break;
				}
				if (i <= n && mat[i][i] == 0) {
					sign = 0;
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
				}
				for (int i = 1; i < n; i++) {
					io.print(mat[i][s + 1] + " ");
				}
				io.println(mat[n][s + 1]);
			}
			// 下一组n和m
			n = io.nextInt();
			m = io.nextInt();
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
