package class125;

// 贴瓷砖的方法数(轮廓线dp)
// 给定两个参数n和m，表示n行m列的空白区域
// 有无限多的1*2规格的瓷砖，目标是严丝合缝的铺满所有的空白区域
// 返回有多少种铺满的方法
// 1 <= n, m <= 11
// 测试链接 : http://poj.org/problem?id=2411
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code02_PavingTile1 {

	public static int MAXN = 11;

	public static long[][][] dp = new long[MAXN][MAXN][1 << MAXN];

	public static int n, m, maxs;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			n = (int) in.nval;
			in.nextToken();
			m = (int) in.nval;
			maxs = 1 << m;
			if (n != 0 || m != 0) {
				out.println(compute());
			}
		}
		out.flush();
		out.close();
		br.close();
	}

	public static long compute() {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				for (int s = 0; s < maxs; s++) {
					dp[i][j][s] = -1;
				}
			}
		}
		return dp(0, 0, 0);
	}

	public static long dp(int i, int j, int s) {
		if (i == n) {
			return s == 0 ? 1 : 0;
		}
		if (j == m) {
			return dp(i + 1, 0, s);
		}
		if (dp[i][j][s] != -1) {
			return dp[i][j][s];
		}
		long ans;
		if (get(s, j) == 1) {
			ans = dp(i, j + 1, set(s, j, 0));
		} else {
			ans = dp(i, j + 1, set(s, j, 1));
			if (j + 1 < m && get(s, j + 1) == 0) {
				ans += dp(i, j + 2, s);
			}
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
