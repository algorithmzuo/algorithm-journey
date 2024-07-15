package class135;

// 高斯消元解决同余方程组模版(扩展欧几里得算法求逆元)
// 有一个n*m的二维网格，给定每个网格的初始值，一定是0、1、2中的一个
// 如果某个网格获得了一些数值加成，也会用%3的方式变成0、1、2中的一个
// 比如有个网格一开始值是1，获得4的加成之后，值为(1+4)%3 = 2
// 有一个神奇的刷子，一旦在某个网格处刷一下，该网格会获得2的加成
// 并且该网格上、下、左、右的格子，都会获得1的加成
// 最终目标是所有网格都变成0，题目保证一定有解
// 打印一共需要刷几下，并且把操作方案打印出来
// 1 <= n、m <= 30
// 测试链接 : https://acm.hdu.edu.cn/showproblem.php?pid=5755
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code02_GaussModExgcdInv {

	public static int MOD = 3;

	public static int MAXS = 905;

	public static int[][] mat = new int[MAXS][MAXS];

	public static int[] dir = { 0, -1, 0, 1, 0 };

	public static int n, m, s;

	// 扩展欧几里得算法求逆元
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

	public static int gcd(int a, int b) {
		return b == 0 ? a : gcd(b, a % b);
	}

	public static int lcm(int a, int b) {
		return a * b / gcd(a, b);
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

	// 保证初始系数都是非负数
	// 如果是系数a是负数，那么转化为非负数，a = (a % mod + mod) % mod
	public static void gauss(int n) {
		for (int i = 1; i <= n; i++) {
			int max = i;
			for (int j = 1; j <= n; j++) {
				if (j < i && mat[j][j] != 0) {
					continue;
				}
				if (mat[j][i] > mat[max][i]) {
					max = j;
				}
			}
			swap(i, max);
			if (mat[i][i] != 0) {
				for (int j = 1; j <= n; j++) {
					if (i != j && mat[j][i] != 0) {
						int lcm = lcm(mat[j][i], mat[i][i]);
						int a = lcm / mat[j][i];
						int b = lcm / mat[i][i];
						if (j < i) {
							mat[j][j] = (mat[j][j] * a) % MOD;
						}
						for (int k = i; k <= n + 1; k++) {
							mat[j][k] = ((mat[j][k] * a - mat[i][k] * b) % MOD + MOD) % MOD;
						}
					}
				}
			}
		}
		// 本来应该是，mat[i][n + 1] = mat[i][n + 1] / mat[i][i]
		// 但是在模意义下应该求逆元
		// (a / b) % MOD = (a * b的逆元) % MOD
		// 此处为扩展欧几里得算法求逆元
		for (int i = 1; i <= n; i++) {
			if (mat[i][i] != 0) {
				exgcd(mat[i][i], MOD);
				int inv = (x % MOD + MOD) % MOD;
				mat[i][n + 1] = (mat[i][n + 1] * inv) % MOD;
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
			gauss(s);
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