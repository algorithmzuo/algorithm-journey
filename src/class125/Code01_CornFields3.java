package class125;

// 轮廓线dp+空间压缩的版本
// 测试链接 : https://www.luogu.com.cn/problem/P1879
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code01_CornFields3 {

	public static int MAXN = 12;

	public static int MAXM = 12;

	public static int MOD = 100000000;

	public static int[][] grid = new int[MAXN][MAXM];

	public static int[][] dp = new int[MAXM + 1][1 << MAXM];

	public static int[] prepare = new int[1 << MAXM];

	public static int n, m, maxs;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		n = (int) in.nval;
		in.nextToken();
		m = (int) in.nval;
		maxs = 1 << m;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
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
		Arrays.fill(prepare, 0, maxs, 1);
		for (int i = n - 1; i >= 0; i--) {
			// j == m
			for (int s = 0; s < maxs; s++) {
				dp[m][s] = prepare[s];
			}
			// 普通位置
			for (int j = m - 1; j >= 0; j--) {
				for (int s = 0; s < maxs; s++) {
					int ans = dp[j + 1][s & (~(1 << j))];
					if (grid[i][j] == 1 && (j == 0 || ((s >> (j - 1)) & 1) == 0) && ((s >> j) & 1) == 0) {
						ans = (ans + dp[j + 1][s | (1 << j)]) % MOD;
					}
					dp[j][s] = ans;

				}
			}
			// 设置prepare
			for (int s = 0; s < maxs; s++) {
				prepare[s] = dp[0][s];
			}
		}
		return dp[0][0];
	}

}
