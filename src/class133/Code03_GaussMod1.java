package class133;

// 高斯消元处理同余方程组(线性递推求逆元)
// 测试链接 : https://acm.hdu.edu.cn/showproblem.php?pid=5755

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code03_GaussMod1 {

	public static int MOD = 3;

	public static int MAXN = 901;

	public static int[] dir = { 0, -1, 0, 1, 0 };

	public static int[][] mat = new int[MAXN][MAXN];

	// 逆元表
	public static int[] inv = new int[MOD];

	public static int n, m, k;

	public static int gcd(int a, int b) {
		return b != 0 ? a : gcd(b, a % b);
	}

	public static int lcm(int a, int b) {
		return a * b / gcd(a, b);
	}

	public static void prepare() {
		for (int i = 0; i < k; i++) {
			for (int j = 0; j < k; j++) {
				mat[i][j] = 0;
			}
		}
		int cur, row, col;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				cur = i * m + j;
				mat[cur][cur] = 2;
				for (int d = 0; d <= 3; d++) {
					row = i + dir[d];
					col = j + dir[d + 1];
					if (row >= 0 && row < n && col >= 0 && col < m) {
						mat[cur][row * m + col] = 1;
					}
				}
			}
		}
		// 逆元线性递推公式
		// 如果不会，去看讲解099 - 除法同余
		inv[1] = 1;
		for (int i = 2; i < MOD; i++) {
			inv[i] = (int) (MOD - (long) inv[MOD % i] * (MOD / i) % MOD);
		}
	}

	public static void gauss() {
		for (int row = 0, col = 0; col < k; col++) {
			int max = row;
			for (int i = row + 1; i < k; i++) {
				if (Math.abs(mat[i][col]) > Math.abs(mat[max][col])) {
					max = i;
				}
			}
			swap(row, max);
			if (mat[row][col] != 0) {
				for (int i = row + 1; i < k; i++) {
					if (mat[i][col] != 0) {
						int lcm = lcm(Math.abs(mat[i][col]), Math.abs(mat[row][col]));
						int a = lcm / Math.abs(mat[i][col]);
						int b = lcm / Math.abs(mat[row][col]);
						if (mat[i][col] * mat[row][col] < 0) {
							b = -b;
						}
						for (int j = col; j <= k; j++) {
							mat[i][j] = ((mat[i][j] * a - mat[row][j] * b) % MOD + MOD) % MOD;
						}
					}
				}
				row++;
			}
		}
		for (int i = k - 1; i >= 0; i--) {
			for (int j = i + 1; j < k; j++) {
				if (mat[i][j] != 0) {
					mat[i][k] = ((mat[i][k] - mat[i][j] * mat[j][k]) % MOD + MOD) % MOD;
				}
			}
			// 本来应该是，mat[i][k] = mat[i][k] / mat[i][i]
			// 但是在模意义下应该求逆元
			// (a / b) % MOD = (a * b的逆元) % MOD
			// 如果不会，去看讲解099 - 除法同余
			mat[i][k] = (mat[i][k] * inv[mat[i][i]]) % MOD;
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
			k = n * m;
			prepare();
			for (int i = 0; i < k; i++) {
				in.nextToken();
				mat[i][k] = (3 - (int) in.nval) % MOD;
			}
			gauss();
			int ans = 0;
			for (int i = 0; i < k; i++) {
				ans += mat[i][k];
			}
			out.println(ans);
			for (int i = 1, cur = 0; i <= n; i++) {
				for (int j = 1; j <= m; j++, cur++) {
					while (mat[cur][k]-- > 0) {
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