package class125;

// 测试链接 : https://www.luogu.com.cn/problem/P1896
// 提交以下的code，提交时请把类名改成"Main"
// 虽然在线测试无法通过，但是逻辑正确
// 我运行了所有可能的情况，结果都正确

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code03_KingsFighting1 {

	public static int MAXN = 10;

	public static int MAXK = 82;

	public static long[][][][][] dp = new long[MAXN][MAXN][1 << MAXN][MAXK][2];

	public static int n;

	public static int kings;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		n = (int) in.nval;
		in.nextToken();
		kings = (int) in.nval;
		out.println(compute());
		out.flush();
		out.close();
		br.close();
	}

	public static long compute() {
		for (int s = 0; s < (1 << n); s++) {
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					for (int k = 0; k <= kings; k++) {
						dp[i][j][s][k][0] = -1;
						dp[i][j][s][k][1] = -1;
					}
				}
			}
		}
		return dp(0, 0, 0, kings, 0);
	}

	public static long dp(int i, int j, int s, int k, int p) {
		if (i == n) {
			return k == 0 ? 1 : 0;
		}
		if (j == n) {
			return dp(i + 1, 0, next(s, p, n), k, 0);
		}
		if (dp[i][j][s][k][p] != -1) {
			return dp[i][j][s][k][p];
		}
		long ans = 0;
		int nexts = next(s, p, j);
		ans = dp(i, j + 1, nexts, k, 0);
		if (k > 0
			&& p == 0
			&& (j == 0 || ((s >> (j - 1)) & 1) == 0)
			&& ((s >> j) & 1) == 0
			&& ((s >> (j + 1)) & 1) == 0) {
			ans += dp(i, j + 1, nexts, k - 1, 1);
		}
		dp[i][j][s][k][p] = ans;
		return ans;
	}

	public static int next(int s, int p, int j) {
		if (j == 0) {
			return s;
		}
		return p == 0 ? (s & (~(1 << (j - 1)))) : (s | (1 << (j - 1)));
	}

}
