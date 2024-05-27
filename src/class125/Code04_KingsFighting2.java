package class125;

// 摆放国王的方法数(轮廓线dp+空间压缩)
// 给定两个参数n和k，表示n*n的区域内要摆放k个国王
// 国王可以攻击临近的8个方向，所以摆放时不能让任何两个国王打架
// 返回摆放的方法数
// 1 <= n <= 9
// 1 <= k <= n*n
// 测试链接 : https://www.luogu.com.cn/problem/P1896
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有用例
// 空间压缩的版本一定稳定通过

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code04_KingsFighting2 {

	public static int MAXN = 9;

	public static int MAXK = 82;

	public static long[][][][] dp = new long[MAXN + 1][1 << MAXN][2][MAXK];

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
			for (int k = 0; k <= kings; k++) {
				prepare[s][k] = k == 0 ? 1 : 0;
			}
		}
		for (int i = n - 1; i >= 0; i--) {
			// j == n
			for (int s = 0; s < maxs; s++) {
				for (int k = 0; k <= kings; k++) {
					dp[n][s][0][k] = prepare[s][k];
					dp[n][s][1][k] = prepare[s][k];
				}
			}
			// 普通位置
			for (int j = n - 1; j >= 0; j--) {
				for (int s = 0; s < maxs; s++) {
					for (int leftup = 0; leftup <= 1; leftup++) {
						for (int k = 0; k <= kings; k++) {
							int left = j == 0 ? 0 : get(s, j - 1);
							int up = get(s, j);
							int rightup = get(s, j + 1);
							long ans = dp[j + 1][set(s, j, 0)][up][k];
							if (k > 0 && left == 0 && leftup == 0 && up == 0 && rightup == 0) {
								ans += dp[j + 1][set(s, j, 1)][up][k - 1];
							}
							dp[j][s][leftup][k] = ans;
						}
					}
				}
			}
			// 设置prepare
			for (int s = 0; s < maxs; s++) {
				for (int k = 0; k <= kings; k++) {
					prepare[s][k] = dp[0][s][0][k];
				}
			}
		}
		return dp[0][0][0][kings];
	}

	public static int get(int s, int j) {
		return (s >> j) & 1;
	}

	public static int set(int s, int j, int v) {
		return v == 0 ? (s & (~(1 << j))) : (s | (1 << j));
	}

}
