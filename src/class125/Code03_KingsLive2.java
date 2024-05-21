package class125;

// 测试链接 : https://www.luogu.com.cn/problem/P1896
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有用例
// 空间压缩的版本才能通过，否则报错

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code03_KingsLive2 {

	public static int MAXN = 10;

	public static int MAXK = 82;

	public static long[][][][] dp = new long[MAXN][1 << MAXN][MAXK][2];

	public static long[][] backup = new long[1 << MAXN][MAXK];

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
		for (int s = 0; s < 1 << n; s++) {
			backup[s][0] = 1;
			for (int k = 1; k <= kings; k++) {
				backup[s][k] = 0;
			}
		}
		for (int i = n - 1; i >= 0; i--) {
			// j == n
			for (int s = 0; s < 1 << n; s++) {
				for (int k = 0; k <= kings; k++) {
					for (int p = 0; p <= 1; p++) {
						dp[n][s][k][p] = backup[next(s, p, n)][k];
					}
				}
			}
			// 普通位置
			long ans;
			for (int j = n - 1; j >= 0; j--) {
				for (int s = 0; s < 1 << n; s++) {
					for (int k = 0; k <= kings; k++) {
						for (int p = 0; p <= 1; p++) {
							ans = 0;
							int nexts = next(s, p, j);
							ans = dp[j + 1][nexts][k][0];
							if (k > 0
								&& p == 0
								&& (j == 0 || ((s >> (j - 1)) & 1) == 0)
								&& ((s >> j) & 1) == 0
								&& ((s >> (j + 1)) & 1) == 0) {
								ans += dp[j + 1][nexts][k - 1][1];
							}
							dp[j][s][k][p] = ans;
						}
					}
				}
			}
			// 设置backup
			for (int s = 0; s < 1 << n; s++) {
				for (int k = 0; k <= kings; k++) {
					for (int p = 0; p <= 1; p++) {
						backup[s][k] = dp[0][s][k][0];
					}
				}
			}
		}
		return dp[0][0][kings][0];
	}

	public static int next(int s, int p, int j) {
		if (j == 0) {
			return s;
		}
		return p == 0 ? (s & (~(1 << (j - 1)))) : (s | (1 << (j - 1)));
	}

}
