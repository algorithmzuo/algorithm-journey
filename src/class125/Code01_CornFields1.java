package class125;

// 种草的方法数(普通状压dp)
// 给定一个n*m的二维网格grid
// 网格里只有0、1两种值，0表示该田地不能种草，1表示该田地可以种草
// 种草的时候，任何两个种了草的田地不能相邻，相邻包括上、下、左、右四个方向
// 你可以随意决定种多少草，只要不破坏上面的规则即可
// 返回种草的方法数，答案对100000000取模
// 1 <= n, m <= 12
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

	// 时间复杂度O(n * 2的m次方 * 2的m次方)
	public static int compute() {
		for (int i = 0; i < n; i++) {
			for (int s = 0; s < maxs; s++) {
				dp[i][s] = -1;
			}
		}
		return f(0, 0);
	}

	public static int f(int i, int s) {
		if (i == n) {
			return 1;
		}
		if (dp[i][s] != -1) {
			return dp[i][s];
		}
		int ans = dfs(i, 0, s, 0);
		dp[i][s] = ans;
		return ans;
	}

	// 当前来到i行j列
	// i-1行每列种草的状况s
	// i行每列种草的状况ss
	// 返回后续有几种方法
	public static int dfs(int i, int j, int s, int ss) {
		if (j == m) {
			return f(i + 1, ss);
		}
		int ans = dfs(i, j + 1, s, ss);
		if (grid[i][j] == 1 && (j == 0 || get(ss, j - 1) == 0) && get(s, j) == 0) {
			ans = (ans + dfs(i, j + 1, s, set(ss, j, 1))) % MOD;
		}
		return ans;
	}

	// 得到状态s中j位的状态
	public static int get(int s, int j) {
		return (s >> j) & 1;
	}

	// 状态s中j位的状态设置成v，然后把新的值返回
	public static int set(int s, int j, int v) {
		return v == 0 ? (s & (~(1 << j))) : (s | (1 << j));
	}

}
