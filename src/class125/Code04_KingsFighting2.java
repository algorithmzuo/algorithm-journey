package class125;

// 摆放国王的方法数(轮廓线dp+空间压缩)
// 给定两个参数n和k，表示n*n的区域内要摆放k个国王
// 国王可以攻击临近的8个方向，所以摆放时不能让任何两个国王打架
// 返回摆放的方法数
// 1 <= n <= 9
// 1 <= k <= n*n
// 测试链接 : https://www.luogu.com.cn/problem/P1896
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有用例
// 空间压缩的版本才能通过

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code04_KingsFighting2 {

	public static int MAXN = 9;

	public static int MAXK = 82;

	public static long[][][][] dp = new long[MAXN + 1][1 << MAXN][MAXK][2];

	public static long[][] prepare = new long[1 << MAXN][MAXK];

	public static int n, kings, maxs;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		n = (int) in.nval;
		in.nextToken();
		kings = (int) in.nval;
		maxs = 1 << n;
		out.println(compute());
		out.flush();
		out.close();
		br.close();
	}

	public static long compute() {
		for (int s = 0; s < maxs; s++) {
			prepare[s][0] = 1;
			for (int k = 1; k <= kings; k++) {
				prepare[s][k] = 0;
			}
		}
		for (int i = n - 1; i >= 0; i--) {
			// j == n
			for (int s = 0; s < maxs; s++) {
				for (int k = 0; k <= kings; k++) {
					for (int p = 0; p <= 1; p++) {
						dp[n][s][k][p] = prepare[set(s, n - 1, p)][k];
					}
				}
			}
			// 普通位置
			for (int j = n - 1; j >= 0; j--) {
				for (int s = 0; s < maxs; s++) {
					for (int k = 0; k <= kings; k++) {
						for (int p = 0; p <= 1; p++) {
							long ans = 0;
							int nexts = j == 0 ? s : set(s, j - 1, p);
							ans = dp[j + 1][nexts][k][0];
							if (k > 0
								&& p == 0
								&& (j == 0 || get(s, j - 1) == 0)
								&& get(s, j) == 0
								&& get(s, j + 1) == 0) {
								ans += dp[j + 1][nexts][k - 1][1];
							}
							dp[j][s][k][p] = ans;
						}
					}
				}
			}
			// 设置prepare
			for (int s = 0; s < maxs; s++) {
				for (int k = 0; k <= kings; k++) {
					for (int p = 0; p <= 1; p++) {
						prepare[s][k] = dp[0][s][k][0];
					}
				}
			}
		}
		return dp[0][0][kings][0];
	}

	public static int get(int s, int j) {
		return (s >> j) & 1;
	}

	public static int set(int s, int j, int v) {
		return v == 0 ? (s & (~(1 << j))) : (s | (1 << j));
	}

}
