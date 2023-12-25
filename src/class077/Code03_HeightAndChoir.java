package class077;

// 合唱队
// 具体描述情打开链接查看
// 测试链接 : https://www.luogu.com.cn/problem/P3205
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下的所有代码，并把主类名改成"Main"，可以直接通过

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code03_HeightAndChoir {

	public static int MAXN = 1001;

	public static int[] nums = new int[MAXN];

	public static int[][] dp = new int[MAXN][2];

	public static int n;

	public static int MOD = 19650827;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			n = (int) in.nval;
			for (int i = 1; i <= n; i++) {
				in.nextToken();
				nums[i] = (int) in.nval;
			}
			if (n == 1) {
				out.println(1);
			} else {
				out.println(compute2());
			}
		}
		out.flush();
		out.close();
		br.close();
	}

	// 时间复杂度O(n^2)
	// 严格位置依赖的动态规划
	public static int compute1() {
		// 人的编号范围 : 1...n
		// dp[l][r][0] : 形成l...r的状况的方法数，同时要求l位置的数字是最后出现的
		// dp[l][r][1] : 形成l...r的状况的方法数，同时要求r位置的数字是最后出现的
		int[][][] dp = new int[n + 1][n + 1][2];
		for (int i = 1; i < n; i++) {
			if (nums[i] < nums[i + 1]) {
				dp[i][i + 1][0] = 1;
				dp[i][i + 1][1] = 1;
			}
		}
		for (int l = n - 2; l >= 1; l--) {
			for (int r = l + 2; r <= n; r++) {
				if (nums[l] < nums[l + 1]) {
					dp[l][r][0] = (dp[l][r][0] + dp[l + 1][r][0]) % MOD;
				}
				if (nums[l] < nums[r]) {
					dp[l][r][0] = (dp[l][r][0] + dp[l + 1][r][1]) % MOD;
				}
				if (nums[r] > nums[l]) {
					dp[l][r][1] = (dp[l][r][1] + dp[l][r - 1][0]) % MOD;
				}
				if (nums[r] > nums[r - 1]) {
					dp[l][r][1] = (dp[l][r][1] + dp[l][r - 1][1]) % MOD;
				}
			}
		}
		return (dp[1][n][0] + dp[1][n][1]) % MOD;
	}

	// 时间复杂度O(n^2)
	// 空间压缩
	public static int compute2() {
		if (nums[n - 1] < nums[n]) {
			dp[n][0] = 1;
			dp[n][1] = 1;
		}
		for (int l = n - 2; l >= 1; l--) {
			if (nums[l] < nums[l + 1]) {
				dp[l + 1][0] = 1;
				dp[l + 1][1] = 1;
			} else {
				dp[l + 1][0] = 0;
				dp[l + 1][1] = 0;
			}
			for (int r = l + 2; r <= n; r++) {
				int a = 0;
				int b = 0;
				if (nums[l] < nums[l + 1]) {
					a = (a + dp[r][0]) % MOD;
				}
				if (nums[l] < nums[r]) {
					a = (a + dp[r][1]) % MOD;
				}
				if (nums[r] > nums[l]) {
					b = (b + dp[r - 1][0]) % MOD;
				}
				if (nums[r] > nums[r - 1]) {
					b = (b + dp[r - 1][1]) % MOD;
				}
				dp[r][0] = a;
				dp[r][1] = b;
			}
		}
		return (dp[n][0] + dp[n][1]) % MOD;
	}

}
