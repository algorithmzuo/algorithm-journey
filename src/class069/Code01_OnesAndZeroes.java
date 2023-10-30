package class069;

// 一和零(多维费用背包)
// 给你一个二进制字符串数组 strs 和两个整数 m 和 n
// 请你找出并返回 strs 的最大子集的长度
// 该子集中 最多 有 m 个 0 和 n 个 1
// 如果 x 的所有元素也是 y 的元素，集合 x 是集合 y 的 子集
// 测试链接 : https://leetcode.cn/problems/ones-and-zeroes/
public class Code01_OnesAndZeroes {

	public static int zeros, ones;

	public static void zerosAndOnes(String str) {
		zeros = 0;
		ones = 0;
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == '0') {
				zeros++;
			} else {
				ones++;
			}
		}
	}

	public static int findMaxForm1(String[] strs, int m, int n) {
		return f1(strs, 0, m, n);
	}

	public static int f1(String[] strs, int i, int z, int o) {
		if (i == strs.length) {
			return 0;
		}
		int p1 = f1(strs, i + 1, z, o);
		int p2 = 0;
		zerosAndOnes(strs[i]);
		if (zeros <= z && ones <= o) {
			p2 = 1 + f1(strs, i + 1, z - zeros, o - ones);
		}
		return Math.max(p1, p2);
	}

	public static int findMaxForm2(String[] strs, int m, int n) {
		int[][][] dp = new int[strs.length][m + 1][n + 1];
		for (int i = 0; i < strs.length; i++) {
			for (int j = 0; j <= m; j++) {
				for (int k = 0; k <= n; k++) {
					dp[i][j][k] = -1;
				}
			}
		}
		return f2(strs, 0, m, n, dp);
	}

	public static int f2(String[] strs, int i, int z, int o, int[][][] dp) {
		if (i == strs.length) {
			return 0;
		}
		if (dp[i][z][o] != -1) {
			return dp[i][z][o];
		}
		int p1 = f2(strs, i + 1, z, o, dp);
		int p2 = 0;
		zerosAndOnes(strs[i]);
		if (zeros <= z && ones <= o) {
			p2 = 1 + f2(strs, i + 1, z - zeros, o - ones, dp);
		}
		int ans = Math.max(p1, p2);
		dp[i][z][o] = ans;
		return ans;
	}

	public static int findMaxForm3(String[] strs, int m, int n) {
		int len = strs.length;
		int[][][] dp = new int[len + 1][m + 1][n + 1];
		for (int i = len - 1; i >= 0; i--) {
			zerosAndOnes(strs[i]);
			for (int z = 0; z <= m; z++) {
				for (int o = 0; o <= n; o++) {
					dp[i][z][o] = dp[i + 1][z][o];
					if (zeros <= z && ones <= o) {
						dp[i][z][o] = Math.max(dp[i][z][o], 1 + dp[i + 1][z - zeros][o - ones]);
					}
				}
			}
		}
		return dp[0][m][n];
	}

	public static int findMaxForm4(String[] strs, int m, int n) {
		int[][] dp = new int[m + 1][n + 1];
		for (String s : strs) {
			zerosAndOnes(s);
			for (int i = m; i >= zeros; i--) {
				for (int j = n; j >= ones; j--) {
					dp[i][j] = Math.max(dp[i][j], dp[i - zeros][j - ones] + 1);
				}
			}
		}
		return dp[m][n];
	}

}
