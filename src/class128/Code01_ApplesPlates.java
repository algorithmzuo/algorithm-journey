package class128;

// 苹果和盘子
// 有m个苹果，认为苹果之间无差别，有n个盘子，认为盘子之间无差别
// 比如5个苹果如果放进3个盘子，那么(1, 3, 1) (1, 1, 3) (3, 1, 1)认为是同一种方法
// 允许有些盘子是空的，返回有多少种放置方法
// 测试链接 : https://www.nowcoder.com/practice/bfd8234bb5e84be0b493656e390bdebf
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code01_ApplesPlates {

	public static int MAXM = 11;

	public static int MAXN = 11;

	public static int[][] dp = new int[MAXM][MAXN];

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