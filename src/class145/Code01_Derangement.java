package class145;

// 错位排列
// 测试链接 : https://www.luogu.com.cn/problem/P1595

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code01_Derangement {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		int n = (int) in.nval;
		// out.println(ways1(n));
		out.println(ways2(n));
		out.flush();
		out.close();
		br.close();
	}

	// 普通分析的方法
	public static long ways1(int n) {
		long[] dp = new long[n + 1];
		for (int i = 1; i <= n; i++) {
			if (i == 1) {
				dp[i] = 0;
			} else if (i == 2) {
				dp[i] = 1;
			} else {
				dp[i] = (i - 1) * (dp[i - 1] + dp[i - 2]);
			}
		}
		return dp[n];
	}

	// 二项式反演的方法
	public static long ways2(int n) {
		long fac = 1;
		for (int i = 1; i <= n; i++) {
			fac *= i;
		}
		long ans = fac;
		long tmp = 1;
		for (int i = 1; i <= n; i++) {
			tmp = tmp * i;
			if ((i & 1) == 0) {
				ans += fac / tmp;
			} else {
				ans -= fac / tmp;
			}
		}
		return ans;
	}

}
