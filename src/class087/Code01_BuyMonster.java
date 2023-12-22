package class087;

// 贿赂怪兽
// 开始时你的能力是0，你的目标是从0号怪兽开始，通过所有的n只怪兽
// 如果你当前的能力小于i号怪兽的能力，则必须付出b[i]的钱贿赂这个怪兽
// 然后怪兽就会加入你，他的能力a[i]直接累加到你的能力上
// 如果你当前的能力大于等于i号怪兽的能力，你可以选择直接通过，且能力不会下降
// 但你依然可以选择贿赂这个怪兽，然后怪兽的能力直接累加到你的能力上
// 返回通过所有的怪兽，需要花的最小钱数
// 测试链接 : https://www.nowcoder.com/practice/736e12861f9746ab8ae064d4aae2d5a9
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下的code，提交时请把类名改成"Main"，可以直接通过

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code01_BuyMonster {

	// 讲解本题的目的不仅仅是为了通过这个题，而是进行如下的思考:
	// 假设a[i]数值的范围很大，但是b[i]数值的范围不大，该怎么做？
	// 假设a[i]数值的范围不大，但是b[i]数值的范围很大，又该怎么做？

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			int n = (int) in.nval;
			int[] a = new int[n + 1];
			int[] b = new int[n + 1];
			for (int i = 1; i <= n; i++) {
				in.nextToken();
				a[i] = (int) in.nval;
				in.nextToken();
				b[i] = (int) in.nval;
			}
			out.println(compute1(n, a, b));
		}
		out.flush();
		out.close();
		br.close();
	}

	// 假设a[i]数值的范围很大，但是b[i]数值的范围不大
	// 时间复杂度O(n * 所有怪兽的钱数累加和)
	public static int compute1(int n, int[] a, int[] b) {
		int m = 0;
		for (int money : b) {
			m += money;
		}
		// dp[i][j] : 花的钱不能超过j，通过前i个怪兽，最大能力是多少
		// 如果dp[i][j] == Integer.MIN_VALUE
		// 表示花的钱不能超过j，无论如何都无法通过前i个怪兽
		int[][] dp = new int[n + 1][m + 1];
		for (int i = 1; i <= n; i++) {
			for (int j = 0; j <= m; j++) {
				dp[i][j] = Integer.MIN_VALUE;
				if (dp[i - 1][j] >= a[i]) {
					dp[i][j] = dp[i - 1][j];
				}
				if (j - b[i] >= 0 && dp[i - 1][j - b[i]] != Integer.MIN_VALUE) {
					dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - b[i]] + a[i]);
				}
			}
		}
		int ans = -1;
		for (int j = 0; j <= m; j++) {
			if (dp[n][j] != Integer.MIN_VALUE) {
				ans = j;
				break;
			}
		}
		return ans;
	}

	// 就是方法1的空间压缩版本
	public static int compute2(int n, int[] a, int[] b) {
		int m = 0;
		for (int money : b) {
			m += money;
		}
		int[] dp = new int[m + 1];
		for (int i = 1, cur; i <= n; i++) {
			for (int j = m; j >= 0; j--) {
				cur = Integer.MIN_VALUE;
				if (dp[j] >= a[i]) {
					cur = dp[j];
				}
				if (j - b[i] >= 0 && dp[j - b[i]] != Integer.MIN_VALUE) {
					cur = Math.max(cur, dp[j - b[i]] + a[i]);
				}
				dp[j] = cur;
			}
		}
		int ans = -1;
		for (int j = 0; j <= m; j++) {
			if (dp[j] != Integer.MIN_VALUE) {
				ans = j;
				break;
			}
		}
		return ans;
	}

	// 假设a[i]数值的范围不大，但是b[i]数值的范围很大
	// 时间复杂度O(n * 所有怪兽的能力累加和)
	public static int compute3(int n, int[] a, int[] b) {
		int m = 0;
		for (int ability : a) {
			m += ability;
		}
		// dp[i][j] : 能力正好是j，并且确保能通过前i个怪兽，需要至少花多少钱
		// 如果dp[i][j] == Integer.MAX_VALUE
		// 表示能力正好是j，无论如何都无法通过前i个怪兽
		int[][] dp = new int[n + 1][m + 1];
		for (int j = 1; j <= m; j++) {
			dp[0][j] = Integer.MAX_VALUE;
		}
		for (int i = 1; i <= n; i++) {
			for (int j = 0; j <= m; j++) {
				dp[i][j] = Integer.MAX_VALUE;
				if (j >= a[i] && dp[i - 1][j] != Integer.MAX_VALUE) {
					dp[i][j] = dp[i - 1][j];
				}
				if (j - a[i] >= 0 && dp[i - 1][j - a[i]] != Integer.MAX_VALUE) {
					dp[i][j] = Math.min(dp[i][j], dp[i - 1][j - a[i]] + b[i]);
				}
			}
		}
		int ans = Integer.MAX_VALUE;
		for (int j = 0; j <= m; j++) {
			ans = Math.min(ans, dp[n][j]);
		}
		return ans == Integer.MAX_VALUE ? -1 : ans;
	}

	// 就是方法3的空间压缩版本
	public static int compute4(int n, int[] a, int[] b) {
		int m = 0;
		for (int ability : a) {
			m += ability;
		}
		int[] dp = new int[m + 1];
		for (int j = 1; j <= m; j++) {
			dp[j] = Integer.MAX_VALUE;
		}
		for (int i = 1, cur; i <= n; i++) {
			for (int j = m; j >= 0; j--) {
				cur = Integer.MAX_VALUE;
				if (j >= a[i] && dp[j] != Integer.MAX_VALUE) {
					cur = dp[j];
				}
				if (j - a[i] >= 0 && dp[j - a[i]] != Integer.MAX_VALUE) {
					cur = Math.min(cur, dp[j - a[i]] + b[i]);
				}
				dp[j] = cur;
			}
		}
		int ans = Integer.MAX_VALUE;
		for (int j = 0; j <= m; j++) {
			ans = Math.min(ans, dp[j]);
		}
		return ans == Integer.MAX_VALUE ? -1 : ans;
	}

}
