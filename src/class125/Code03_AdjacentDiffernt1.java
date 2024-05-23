package class125;

// 测试链接 : https://www.luogu.com.cn/problem/P2435
// 提交以下的code，提交时请把类名改成"Main"
// 虽然在线测试无法通过，但是逻辑正确
// 我运行了所有可能的情况，结果都正确

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code03_AdjacentDiffernt1 {

	public static int n, m, k;

	public static int LIMIT1 = 100001;

	public static int[] start = new int[LIMIT1];

	public static int[] end = new int[LIMIT1];

	public static int LIMIT2 = 101;

	public static int LIMIT3 = 8;

	public static int[][][] dp = new int[LIMIT2][LIMIT3][1 << (LIMIT3 << 1)];

	public static int startStatus;

	public static int endStatus;

	public static int MOD = 376544743;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		n = (int) in.nval;
		in.nextToken();
		m = (int) in.nval;
		in.nextToken();
		k = (int) in.nval;
		for (int i = 0; i < m; i++) {
			in.nextToken();
			start[i] = (int) in.nval;
		}
		for (int i = 0; i < m; i++) {
			in.nextToken();
			end[i] = (int) in.nval;
		}
		if (k == 2) {
			out.println(special());
		} else {
			out.println(compute());
		}
		out.flush();
		out.close();
		br.close();
	}

	public static int special() {
		if ((n & 1) == 0) {
			for (int i = 0; i < m; i++) {
				if (start[i] == end[i]) {
					return 0;
				}
			}
		} else {
			for (int i = 0; i < m; i++) {
				if (start[i] != end[i]) {
					return 0;
				}
			}
		}
		return 1;
	}

	public static int compute() {
		startStatus = endStatus = 0;
		for (int i = 0, j = 0; i < m; i++, j += 2) {
			startStatus |= start[i] << j;
			endStatus |= end[i] << j;
		}
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				for (int s = 0; s < 1 << (m << 1); s++) {
					dp[i][j][s] = -1;
				}
			}
		}
		return dp(1, 0, startStatus);
	}

	public static int dp(int i, int j, int s) {
		if (i == n - 1) {
			return check(s, endStatus) ? 1 : 0;
		}
		if (j == m) {
			return dp(i + 1, 0, s);
		}
		if (dp[i][j][s] != -1) {
			return dp[i][j][s];
		}
		int ans = 0;
		for (int color = 0; color < k; color++) {
			if ((j == 0 || ((s >> ((j - 1) << 1)) & 3) != color) && ((s >> (j << 1)) & 3) != color) {
				ans = (ans + dp(i, j + 1, setColor(s, color, j))) % MOD;
			}
		}
		dp[i][j][s] = ans;
		return ans;
	}

	public static boolean check(int a, int b) {
		for (int i = 0, j = 0; i < m; i++, j += 2) {
			if (((a >> j) & 3) == ((b >> j) & 3)) {
				return false;
			}
		}
		return true;
	}

	public static int setColor(int s, int c, int j) {
		int i = j << 1;
		return s & (~(3 << i)) | (c << i);
	}

}
