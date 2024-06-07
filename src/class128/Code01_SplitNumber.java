package class128;

// 数的划分方法
// 将整数m分成n份，且每份不能为空，任意两个方案不相同(不考虑顺序)
// 比如，m=7、n=3，那么(1, 1, 5) (1, 5, 1) (5, 1, 1)认为是同一种方法
// 返回有多少种不同的划分方法
// 测试链接 : https://www.luogu.com.cn/problem/P1025
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code01_SplitNumber {

	public static int MAXN = 201;

	public static int MAXK = 7;

	public static int[][] dp = new int[MAXN][MAXK];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		int m = (int) in.nval;
		in.nextToken();
		int n = (int) in.nval;
		out.println(compute(m, n));
		out.flush();
		out.close();
		br.close();
	}

	public static int compute(int m, int n) {
		if (m < n) {
			return 0;
		}
		if (m == n) {
			return 1;
		}
		m -= n;
		for (int i = 0; i <= m; i++) {
			for (int j = 0; j <= n; j++) {
				dp[i][j] = -1;
			}
		}
		return f(m, n);
	}

	public static int f(int m, int n) {
		if (m == 0) {
			return 1;
		}
		if (n == 0) {
			return 0;
		}
		if (dp[m][n] != -1) {
			return dp[m][n];
		}
		int ans;
		if (n > m) {
			ans = f(m, m);
		} else {
			ans = f(m, n - 1) + f(m - n, n);
		}
		dp[m][n] = ans;
		return ans;
	}

}