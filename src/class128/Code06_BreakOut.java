package class128;

// 测试链接 : https://www.luogu.com.cn/problem/P1437
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code06_BreakOut {

	public static int MAXN = 55;

	public static int MAXM = 1300;

	public static int[][] grid = new int[MAXN][MAXN];

	public static int[][] sum = new int[MAXN][MAXN];

	public static int[][][] dp = new int[MAXN][MAXN][MAXM];

	public static int n, m;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		n = (int) in.nval;
		in.nextToken();
		m = (int) in.nval;
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n - i + 1; j++) {
				in.nextToken();
				grid[i][j] = (int) in.nval;
			}
		}
		out.println(compute());
		out.flush();
		out.close();
		br.close();
	}

	public static int compute() {
		for (int j = 1; j <= n; j++) {
			for (int i = 1; i <= n; i++) {
				sum[i][j] = sum[i - 1][j] + grid[i][j];
			}
		}
		for (int j = 1; j <= n; j++) {
			for (int i = 0; i <= n; i++) {
				for (int k = i; k <= m; k++) {
					dp[i][j][k] = 0;
					for (int t = 0; t <= i + 1; t++) {
						dp[i][j][k] = Math.max(dp[i][j][k], dp[t][j - 1][k - i] + sum[i][j]);
					}
				}
			}
		}
		return Math.max(dp[1][n][m], dp[0][n][m]);
	}

}
