package class125;

// 种草的方法数(轮廓线dp)
// 给定一个n*m的二维网格grid
// 网格里只有0、1两种值，0表示该田地不能种草，1表示该田地可以种草
// 种草的时候，任何两个种了草的田地不能相邻，相邻包括上、下、左、右四个方向
// 你可以随意决定种多少草，只要不破坏上面的规则即可
// 返回种草的方法数，答案对100000000取模
// 1 <= n, m <= 12
// 测试链接 : https://www.luogu.com.cn/problem/P1879
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code01_CornFields2 {

	public static int MAXN = 12;

	public static int MAXM = 12;

	public static int MOD = 100000000;

	public static int[][] grid = new int[MAXN][MAXM];

	public static int[][][] dp = new int[MAXN][MAXM][1 << MAXM];

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

	// 时间复杂度O(n * 2的m次方 * m)
	public static int compute() {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				for (int s = 0; s < maxs; s++) {
					dp[i][j][s] = -1;
				}
			}
		}
		return f(0, 0, 0);
	}

	// 当前来到i行j列
	// i-1行中，[j..m-1]列的种草状况用s[j..m-1]表示
	// i行中，[0..j-1]列的种草状况用s[0..j-1]表示
	// s表示轮廓线的状况
	// 返回后续有几种种草方法
	public static int f(int i, int j, int s) {
		if (i == n) {
			return 1;
		}
		if (j == m) {
			return f(i + 1, 0, s);
		}
		if (dp[i][j][s] != -1) {
			return dp[i][j][s];
		}
		int ans = f(i, j + 1, set(s, j, 0));
		if (grid[i][j] == 1 && (j == 0 || get(s, j - 1) == 0) && get(s, j) == 0) {
			ans = (ans + f(i, j + 1, set(s, j, 1))) % MOD;
		}
		dp[i][j][s] = ans;
		return ans;
	}

	public static int get(int s, int j) {
		return (s >> j) & 1;
	}

	public static int set(int s, int j, int v) {
		return v == 0 ? (s & (~(1 << j))) : (s | (1 << j));
	}

}
