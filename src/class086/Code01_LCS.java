package class086;

// 最长公共子序列其中一个结果
// 给定两个字符串str1和str2
// 输出两个字符串的最长公共子序列
// 如果最长公共子序列为空，则输出-1
// 测试链接 : https://www.nowcoder.com/practice/4727c06b9ee9446cab2e859b4bb86bb8
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下的code，提交时请把类名改成"Main"，可以直接通过

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

// 讲解067 - 题目3，最长公共子序列长度
public class Code01_LCS {

	public static int MAXN = 5001;

	public static int[][] dp = new int[MAXN][MAXN];

	public static char[] ans = new char[MAXN];

	public static char[] s1;

	public static char[] s2;

	public static int n, m, k;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		s1 = br.readLine().toCharArray();
		s2 = br.readLine().toCharArray();
		n = s1.length;
		m = s2.length;
		lcs();
		if (k == 0) {
			out.println(-1);
		} else {
			for (int i = 0; i < k; i++) {
				out.print(ans[i]);
			}
			out.println();
		}
		out.flush();
		out.close();
		br.close();
	}

	public static void lcs() {
		dp();
		k = dp[n][m];
		if (k > 0) {
			for (int len = k, i = n, j = m; len > 0;) {
				if (s1[i - 1] == s2[j - 1]) {
					ans[--len] = s1[i - 1];
					i--;
					j--;
				} else {
					if (dp[i - 1][j] >= dp[i][j - 1]) {
						i--;
					} else {
						j--;
					}
				}
			}
		}
	}

	// 填好dp表
	public static void dp() {
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= m; j++) {
				if (s1[i - 1] == s2[j - 1]) {
					dp[i][j] = 1 + dp[i - 1][j - 1];
				} else {
					dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
				}
			}
		}
	}

}
