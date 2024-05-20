package class125;

// 测试链接 : http://poj.org/problem?id=2411

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code01_PavingTile {

	public static int MAXN = 12;

	public static int MAXM = (1 << MAXN);

	public static long[][][] dp = new long[MAXN][MAXN][MAXM];

	public static long ways(int n, int m) {
		for (int i = 0; i <= n; i++) {
			for (int j = 0; j <= m; j++) {
				for (int k = 0; k <= (1 << m); k++) {
					dp[i][j][k] = -1;
				}
			}
		}
		return dp(0, 0, 0, n, m);
	}

	public static long dp(int r, int c, int s, int n, int m) {
		if (r == n) {
			return s == 0 ? 1 : 0;
		}
		if (c == m) {
			return dp(r + 1, 0, s, n, m);
		}
		if (dp[r][c][s] != -1) {
			return dp[r][c][s];
		}
		long ans;
		int cur = (s & (1 << c)) == 0 ? 0 : 1;
		if (cur == 1) {
			ans = dp(r, c + 1, s ^ (1 << c), n, m);
		} else {
			long p1 = dp(r, c + 1, s | (1 << c), n, m);
			long next = c + 1 < m && (s & (1 << (c + 1))) == 0 ? 0 : 1;
			long p2 = 0;
			if (next == 0) {
				p2 = dp(r, c + 2, s, n, m);
			}
			ans = p1 + p2;
		}
		dp[r][c][s] = ans;
		return ans;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			int n = (int) in.nval;
			in.nextToken();
			int m = (int) in.nval;
			if (n != 0 || m != 0) {
				long ans = ways(n, m);
				out.println(ans);
			}
		}
		out.flush();
		out.close();
		br.close();
	}

}
