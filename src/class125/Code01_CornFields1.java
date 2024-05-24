package class125;

// 普通状压dp的版本
// 测试链接 : https://www.luogu.com.cn/problem/P1879
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code01_CornFields1 {

	public static int MAXN = 12;

	public static int MAXM = 12;

	public static int MOD = 100000000;

	public static int[][] grid = new int[MAXN][MAXM];

	public static int n;

	public static int m;

	public static int maxs;

	public static int[][][] dp = new int[MAXN][MAXM][1 << MAXM];

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
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				for (int s = 0; s < maxs; s++) {
					dp[i][j][s] = -1;
				}
			}
		}
		return dp(0, 0, 0);
	}

	public static int dp(int i, int j, int s) {
		if (i == n) {
			return 1;
		}
		if (j == m) {
			return dp(i + 1, 0, s);
		}
		if (dp[i][j][s] != -1) {
			return dp[i][j][s];
		}
		int ans = dp(i, j + 1, s & (~(1 << j)));
		if (grid[i][j] == 1 && (j == 0 || ((s >> (j - 1)) & 1) == 0) && ((s >> j) & 1) == 0) {
			ans = (ans + dp(i, j + 1, s | (1 << j))) % MOD;
		}
		dp[i][j][s] = ans;
		return ans;
	}

}
