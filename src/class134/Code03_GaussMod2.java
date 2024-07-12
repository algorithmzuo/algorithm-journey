package class134;

// 高斯消元处理同余方程组模版(扩展欧几里得算法求逆元)
// 测试链接 : https://acm.hdu.edu.cn/showproblem.php?pid=5755

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code03_GaussMod2 {

	public static int MOD = 3;

	public static int MAXS = 905;

	public static int[][] mat = new int[MAXS][MAXS];

	public static int[] dir = { 0, -1, 0, 1, 0 };

	public static int n, m, s;

	public static int gcd(int a, int b) {
		return b == 0 ? a : gcd(b, a % b);
	}

	public static int lcm(int a, int b) {
		return a * b / gcd(a, b);
	}

	// 扩展欧几里得算法求逆元，后续课会讲到
	public static int x, y;

	public static void exgcd(int a, int b) {
		int n = 0, m = 1, pn = 1, pm = 0, tmp, q, r;
		while (b != 0) {
			q = a / b;
			r = a % b;
			a = b;
			b = r;
			tmp = n;
			n = pn - q * n;
			pn = tmp;
			tmp = m;
			m = pm - q * m;
			pm = tmp;
		}
		x = pn;
		y = pm;
	}

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
				mat[cur][cur] = 2;
				for (int d = 0; d <= 3; d++) {
					row = i + dir[d];
					col = j + dir[d + 1];
					if (row >= 0 && row < n && col >= 0 && col < m) {
						mat[cur][row * m + col + 1] = 1;
					}
				}
			}
		}
	}

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
		// 本来应该是，mat[i][k + 1] = mat[i][k + 1] / mat[i][i]
		// 但是在模意义下应该求逆元
		// (a / b) % MOD = (a * b的逆元) % MOD
		// 此处为扩展欧几里得算法求逆元，后续课程会讲到
		for (int i = 1; i <= s; i++) {
			if (mat[i][i] != 0) {
				exgcd(mat[i][i], MOD);
				int inv = (x % MOD + MOD) % MOD;
				mat[i][s + 1] = (mat[i][s + 1] * inv) % MOD;
			}
		}
	}

	public static void swap(int a, int b) {
		int[] tmp = mat[a];
		mat[a] = mat[b];
		mat[b] = tmp;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		int test = (int) in.nval;
		for (int t = 1; t <= test; t++) {
			in.nextToken();
			n = (int) in.nval;
			in.nextToken();
			m = (int) in.nval;
			s = n * m;
			prepare();
			for (int i = 1; i <= s; i++) {
				in.nextToken();
				mat[i][s + 1] = (3 - (int) in.nval) % MOD;
			}
			gauss();
			int ans = 0;
			for (int i = 1; i <= s; i++) {
				ans += mat[i][s + 1];
			}
			out.println(ans);
			for (int i = 1, id = 1; i <= n; i++) {
				for (int j = 1; j <= m; j++, id++) {
					while (mat[id][s + 1]-- > 0) {
						out.println(i + " " + j);
					}
				}
			}
		}
		out.flush();
		out.close();
		br.close();
	}

}