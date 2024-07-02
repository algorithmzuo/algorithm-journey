package class132;

// 还原的方法数
// 原本有一个长度为n的数组arr，下标从1开始，数组中都是<=200的正数
// 并且任意i位置的数字都满足 : arr[i] <= max(arr[i-1], arr[i+1])
// 特别的，arr[1] <= arr[2]，arr[n] <= arr[n-1]
// 但是输入的arr中有些数字丢失了，丢失的数字用0表示
// 返回还原成不违规的arr有多少种方法
// 答案可能很大，对998244353取模
// 3 <= n <= 10^4
// 测试链接 : https://www.nowcoder.com/practice/49c5284278974cbda474ec13d8bd86a9
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code02_WaysOfRevert {

	public static int MAXN = 10000;

	public static int MAXV = 200;

	public static int MOD = 998244353;

	public static int n;

	public static int[] arr = new int[MAXN + 1];

	public static int[][][] dp = new int[MAXN + 1][MAXV + 1][2];

	// compute3方法需要用到
	public static int[] suf0 = new int[MAXV + 1];

	// compute3方法需要用到
	public static int[] pre1 = new int[MAXV + 1];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			n = (int) in.nval;
			for (int i = 1; i <= n; i++) {
				in.nextToken();
				arr[i] = (int) in.nval;
			}
			// 调用compute3方法，可以通过所有测试用例
			out.println(compute3());
		}
		out.flush();
		out.close();
		br.close();
	}

	// 递归 + 记忆化搜索
	// 不优化枚举
	// 时间复杂度O(n * v平方)，会超时
	public static int compute1() {
		for (int i = 0; i <= n; i++) {
			for (int j = 0; j <= MAXV; j++) {
				dp[i][j][0] = -1;
				dp[i][j][1] = -1;
			}
		}
		return f1(n, 0, 1);
	}

	// 1...i范围上去决定数字，i+1位置的数字已经变成了v
	// 如果s == 0, 表示arr[i+1] > arr[i+2]
	// 如果s == 1, 表示arr[i+1] <= arr[i+2]
	// 返回还原的方法数
	public static int f1(int i, int v, int s) {
		if (i == 0) {
			return s;
		}
		if (dp[i][v][s] != -1) {
			return dp[i][v][s];
		}
		int ans = 0;
		if (arr[i] != 0) {
			if (arr[i] >= v || s == 1) {
				ans = f1(i - 1, arr[i], arr[i] > v ? 0 : 1);
			}
		} else {
			for (int cur = v + 1; cur <= MAXV; cur++) {
				ans = (ans + f1(i - 1, cur, 0)) % MOD;
			}
			if (v != 0) {
				ans = (ans + f1(i - 1, v, 1)) % MOD;
			}
			if (s == 1) {
				for (int cur = 1; cur < v; cur++) {
					ans = (ans + f1(i - 1, cur, 1)) % MOD;
				}
			}
		}
		dp[i][v][s] = ans;
		return ans;
	}

	// 严格位置依赖的动态规划
	// 不优化枚举
	// 时间复杂度O(n * v平方)，会超时
	public static int compute2() {
		for (int v = 0; v <= MAXV; v++) {
			dp[0][v][0] = 0;
			dp[0][v][1] = 1;
		}
		for (int i = 1; i <= n; i++) {
			for (int v = 0; v <= MAXV; v++) {
				for (int s = 0; s <= 1; s++) {
					int ans = 0;
					if (arr[i] != 0) {
						if (arr[i] >= v || s == 1) {
							ans = dp[i - 1][arr[i]][arr[i] > v ? 0 : 1];
						}
					} else {
						for (int cur = v + 1; cur <= MAXV; cur++) {
							ans = (ans + dp[i - 1][cur][0]) % MOD;
						}
						if (v != 0) {
							ans = (ans + dp[i - 1][v][1]) % MOD;
						}
						if (s == 1) {
							for (int cur = 1; cur < v; cur++) {
								ans = (ans + dp[i - 1][cur][1]) % MOD;
							}
						}
					}
					dp[i][v][s] = ans;
				}
			}
		}
		return dp[n][0][1];
	}

	// 正式方法
	// 优化枚举的动态规划
	// 时间复杂度O(n * v)，可以通过所有测试用例
	public static int compute3() {
		for (int v = 0; v <= MAXV; v++) {
			dp[0][v][0] = 0;
			dp[0][v][1] = 1;
		}
		prepare(0);
		for (int i = 1; i <= n; i++) {
			for (int v = 0; v <= MAXV; v++) {
				for (int s = 0; s <= 1; s++) {
					int ans = 0;
					if (arr[i] != 0) {
						if (arr[i] >= v || s == 1) {
							ans = dp[i - 1][arr[i]][arr[i] > v ? 0 : 1];
						}
					} else {
						if (v + 1 <= MAXV) {
							ans = (ans + suf0[v + 1]) % MOD;
						}
						if (v != 0) {
							ans = (ans + dp[i - 1][v][1]) % MOD;
						}
						if (s == 1) {
							if (v - 1 >= 0) {
								ans = (ans + pre1[v - 1]) % MOD;
							}
						}
					}
					dp[i][v][s] = ans;
				}
			}
			prepare(i);
		}
		return dp[n][0][1];
	}

	// 预处理结构优化枚举
	public static void prepare(int i) {
		suf0[MAXV] = dp[i][MAXV][0];
		for (int v = MAXV - 1; v >= 0; v--) {
			suf0[v] = (suf0[v + 1] + dp[i][v][0]) % MOD;
		}
		pre1[1] = dp[i][1][1];
		for (int v = 2; v <= MAXV; v++) {
			pre1[v] = (pre1[v - 1] + dp[i][v][1]) % MOD;
		}
	}

}
