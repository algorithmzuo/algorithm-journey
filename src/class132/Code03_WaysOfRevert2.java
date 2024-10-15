package class132;

// 还原数组的方法数(优化枚举)
// 原本有一个长度为n的数组arr，下标从1开始，数组中都是<=200的正数
// 并且任意i位置的数字都满足 : arr[i] <= max(arr[i-1], arr[i+1])
// 特别的，arr[1] <= arr[2]，arr[n] <= arr[n-1]
// 但是输入的arr中有些数字丢失了，丢失的数字用0表示
// 返回还原成不违规的arr有多少种方法，答案对 998244353 取模
// 3 <= n <= 10^4
// 测试链接 : https://www.nowcoder.com/practice/49c5284278974cbda474ec13d8bd86a9
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code03_WaysOfRevert2 {

	public static int MOD = 998244353;

	public static int MAXN = 10000;

	public static int[] arr = new int[MAXN + 1];

	public static int n;

	public static int m = 200;

	public static int[][] memo = new int[m + 1][2];

	public static int[][] dp = new int[m + 1][2];

	public static int[] suf0 = new int[m + 1];

	public static int[] pre1 = new int[m + 1];

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
			out.println(compute());
		}
		out.flush();
		out.close();
		br.close();
	}

	// 正式方法
	// 优化枚举 + 空间压缩
	// 时间复杂度O(n * m)，可以通过所有测试用例
	public static int compute() {
		for (int v = 0; v <= m; v++) {
			memo[v][0] = 0;
			memo[v][1] = 1;
		}
		for (int i = 1; i <= n; i++) {
			prepare();
			for (int v = 0; v <= m; v++) {
				for (int s = 0; s <= 1; s++) {
					int ans = 0;
					if (arr[i] != 0) {
						if (arr[i] >= v || s == 1) {
							ans = memo[arr[i]][arr[i] > v ? 0 : 1];
						}
					} else {
						if (v + 1 <= m) {
							ans = (ans + suf0[v + 1]) % MOD;
						}
						if (v != 0) {
							ans = (ans + memo[v][1]) % MOD;
						}
						if (s == 1) {
							if (v - 1 >= 0) {
								ans = (ans + pre1[v - 1]) % MOD;
							}
						}
					}
					dp[v][s] = ans;
				}
			}
			int[][] tmp = memo;
			memo = dp;
			dp = tmp;
		}
		return memo[0][1];
	}

	// 预处理结构优化枚举
	public static void prepare() {
		suf0[m] = memo[m][0];
		for (int v = m - 1; v >= 0; v--) {
			suf0[v] = (suf0[v + 1] + memo[v][0]) % MOD;
		}
		pre1[1] = memo[1][1];
		for (int v = 2; v <= m; v++) {
			pre1[v] = (pre1[v - 1] + memo[v][1]) % MOD;
		}
	}

}
