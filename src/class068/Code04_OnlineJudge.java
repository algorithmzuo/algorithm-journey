package class068;

// 有效涂色问题
// 题目4的题意，找到了两个在线测试链接
// 测试链接 : https://acm.hdu.edu.cn/showproblem.php?pid=7808
// 测试链接 : https://onlinejudge.u-aizu.ac.jp/problems/DPL_5_C
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code04_OnlineJudge {

	public static int MAXN = 1001;

	public static int[][] dp = new int[MAXN][MAXN];

	public static int mod = 1000000007;

	public static int compute(int n, int m) {
		for (int i = 1; i <= n; i++) {
			dp[i][1] = m;
		}
		for (int i = 2; i <= n; i++) {
			for (int j = 2; j <= m; j++) {
				dp[i][j] = (int) (((long) dp[i - 1][j] * j) % mod);
				dp[i][j] = (int) ((((long) dp[i - 1][j - 1] * (m - j + 1)) + dp[i][j]) % mod);
			}
		}
		return dp[n][m];
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			int n = (int) in.nval;
			in.nextToken();
			int m = (int) in.nval;
			out.println(compute(n, m));
		}
		out.flush();
		out.close();
		br.close();
	}

}
