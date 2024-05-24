package class125;

// 普通状压dp的版本
// 测试链接 : https://www.luogu.com.cn/problem/P1879
// 提交以下的code，提交时请把类名改成"Main"
// 普通状压dp的版本无法通过所有测试用例
// 有些测试样本会超时，这是dfs过程很费时导致的

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

	public static int[][] dp = new int[MAXN][1 << MAXM];

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
		for (int i = 0; i < n; i++) {
			for (int s = 0; s < maxs; s++) {
				dp[i][s] = -1;
			}
		}
		return dp(0, 0);
	}

	public static int dp(int i, int s) {
		if (i == n) {
			return 1;
		}
		return dfs(i, 0, s, 0);
	}

	public static int dfs(int i, int j, int s, int ss) {
		if (j == m) {
			return dp(i + 1, ss);
		}
		int ans = dfs(i, j + 1, s, ss);
		if (grid[i][j] == 1 && (j == 0 || ((ss >> (j - 1)) & 1) == 0) && ((s >> j) & 1) == 0) {
			ans = (ans + dfs(i, j + 1, s, ss | (1 << j))) % MOD;
		}
		return ans;
	}

}
