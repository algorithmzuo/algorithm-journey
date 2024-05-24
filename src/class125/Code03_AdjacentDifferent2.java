package class125;

// 相邻不同色的染色方法数(轮廓线dp+空间压缩)
// 给定两个参数n和m，表示n行m列的空白区域，一开始所有格子都没有颜色
// 给定参数k，表示有k种颜色，颜色编号0~k-1
// 你需要给每个格子染色，但是相邻的格子颜色不能相同
// 相邻包括上、下、左、右四个方向
// 并且给定了第0行和第n-1行的颜色状况，输入保证一定有效
// 那么你只能在1~n-2行上染色，返回染色的方法数，答案对376544743取模
// 2 <= k <= 4
// k = 2时，1 <= n <= 10^7，1 <= m <= 10^5
// 3 <= k <= 4时，1 <= n <= 100，1 <= m <= 8
// 测试链接 : https://www.luogu.com.cn/problem/P2435
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有用例
// 空间压缩的版本才能通过

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code03_AdjacentDifferent2 {

	public static int LIMIT1 = 100001;

	public static int LIMIT2 = 8;

	public static int MOD = 376544743;

	public static int[] start = new int[LIMIT1];

	public static int[] end = new int[LIMIT1];

	public static int[][] dp = new int[LIMIT2 + 1][1 << (LIMIT2 << 1)];

	public static int[] prepare = new int[1 << (LIMIT2 << 1)];

	public static int startStatus, endStatus;

	public static int n, m, k, maxs;

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
		maxs = 1 << (m << 1);
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
		for (int j = 0; j < m; j++) {
			startStatus = set(startStatus, j, start[j]);
			endStatus = set(endStatus, j, end[j]);
		}
		for (int s = 0; s < maxs; s++) {
			prepare[s] = different(s, endStatus) ? 1 : 0;
		}
		for (int i = n - 2; i >= 1; i--) {
			// j == m
			for (int s = 0; s < maxs; s++) {
				dp[m][s] = prepare[s];
			}
			// 普通位置
			for (int j = m - 1; j >= 0; j--) {
				for (int s = 0; s < maxs; s++) {
					int ans = 0;
					for (int color = 0; color < k; color++) {
						if ((j == 0 || get(s, j - 1) != color) && get(s, j) != color) {
							ans = (ans + dp[j + 1][set(s, j, color)]) % MOD;
						}
					}
					dp[j][s] = ans;
				}
			}
			// 设置prepare
			for (int s = 0; s < maxs; s++) {
				prepare[s] = dp[0][s];
			}
		}
		return dp[0][startStatus];
	}

	public static boolean different(int a, int b) {
		for (int j = 0; j < m; j++) {
			if (get(a, j) == get(b, j)) {
				return false;
			}
		}
		return true;
	}

	public static int get(int s, int j) {
		return (s >> (j << 1)) & 3;
	}

	public static int set(int s, int j, int v) {
		return s & (~(3 << (j << 1))) | (v << (j << 1));
	}

}
