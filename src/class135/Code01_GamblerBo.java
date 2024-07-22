package class135;

// 格子全变成0的操作方案
// 有一个n*m的二维网格，给定每个网格的初始值，一定是0、1、2中的一个
// 如果某个网格获得了一些数值加成，也会用%3的方式变成0、1、2中的一个
// 比如有个网格一开始值是1，获得4的加成之后，值为(1+4)%3 = 2
// 有一个神奇的刷子，一旦在某个网格处刷一下，该网格会获得2的加成
// 并且该网格上、下、左、右的格子，都会获得1的加成
// 最终目标是所有网格都变成0，题目保证一定有解，但不保证唯一解
// 得到哪一种方案都可以，打印一共需要刷几下，并且把操作方案打印出来
// 1 <= n、m <= 30
// 测试链接 : https://acm.hdu.edu.cn/showproblem.php?pid=5755
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code01_GamblerBo {

	public static int MOD = 3;

	public static int MAXS = 1001;

	public static int[][] mat = new int[MAXS][MAXS];

	public static int[] dir = { 0, -1, 0, 1, 0 };

	public static int n, m, s;

	public static int[] inv = new int[MOD];

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

	// 这道题目比较特殊，打印任何一种方案都可以
	// 于是认为所有自由元的操作次数为0
	// 也就是认为消元之后，主元不被任何自由元影响
	// 所以代码可以简化
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
							// 只需要调整，j行主元的系数，也就是j行j列的值
							// 不需要调整，j行，j+1列 ~ i-1列的值，所对应自由元的系数
							// 因为最终方案默认所有自由元都不操作
							mat[j][j] = (mat[j][j] * a) % MOD;
						}
						// 正常消元
						for (int k = i; k <= n + 1; k++) {
							mat[j][k] = ((mat[j][k] * a - mat[i][k] * b) % MOD + MOD) % MOD;
						}
					}
				}
			}
		}
		// 由于本题的特殊性，不需要去管任何自由元的影响
		// 就当自由元不操作，直接求主元的操作次数即可
		for (int i = 1; i <= n; i++) {
			if (mat[i][i] != 0) {
				mat[i][n + 1] = (mat[i][n + 1] * inv[mat[i][i]]) % MOD;
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