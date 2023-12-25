package class077;

// 完成配对需要的最少字符数量
// 给定一个由'['、']'、'('，')'组成的字符串
// 请问最少插入多少个括号就能使这个字符串的所有括号正确配对
// 例如当前串是 "([[])"，那么插入一个']'即可满足
// 输出最少需要插入多少个字符
// 测试链接 : https://www.nowcoder.com/practice/e391767d80d942d29e6095a935a5b96b
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下的code，提交时请把类名改成"Main"，可以直接通过

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code01_MinimumInsertionsToMatch {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		String str = br.readLine();
		out.println(compute(str));
		out.flush();
		out.close();
		br.close();
	}

	// 时间复杂度O(n^3)
	public static int compute(String str) {
		char[] s = str.toCharArray();
		int n = s.length;
		int[][] dp = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				dp[i][j] = -1;
			}
		}
		return f(s, 0, s.length - 1, dp);
	}

	// 让s[l...r]配对至少需要几个字符
	public static int f(char[] s, int l, int r, int[][] dp) {
		if (l == r) {
			return 1;
		}
		if (l == r - 1) {
			if ((s[l] == '(' && s[r] == ')') || (s[l] == '[' && s[r] == ']')) {
				return 0;
			}
			return 2;
		}
		// l...r字符数量 >= 3
		if (dp[l][r] != -1) {
			return dp[l][r];
		}
		// 可能性1 : [l]、[r]本来就是配对的
		int p1 = Integer.MAX_VALUE;
		if ((s[l] == '(' && s[r] == ')') || (s[l] == '[' && s[r] == ']')) {
			p1 = f(s, l + 1, r - 1, dp);
		}
		// 可能性2 : 基于每个可能的划分点，做左右划分
		int p2 = Integer.MAX_VALUE;
		for (int m = l; m < r; m++) {
			p2 = Math.min(p2, f(s, l, m, dp) + f(s, m + 1, r, dp));
		}
		int ans = Math.min(p1, p2);
		dp[l][r] = ans;
		return ans;
	}

}
