package class081;

import java.util.Arrays;

// 好子集的数目
// 给你一个整数数组 nums，好子集的定义如下：
// nums的某个子集，所有元素的乘积可以表示为一个或多个互不相同质数的乘积
// 比如nums = [1, 2, 3, 4]
// [2, 3]，[1, 2, 3]，[1, 3] 是好子集
// 乘积分别为6=2*3，6=2*3，3=3
// [1, 4]和[4]不是好子集，因为乘积分别为4=2*2和4=2*2
// 请你返回nums中不同的好子集的数目对10^9+7取余的结果
// 如果两个子集删除的下标不同，那么它们被视为不同的子集
// 测试链接 : https://leetcode.cn/problems/the-number-of-good-subsets/
public class Code03_TheNumberOfGoodSubsets {

	public static int MAXV = 30;

	public static int LIMIT = (1 << 10);

	public static int MOD = 1000000007;

	// 打个表来加速判断
	// 如果一个数字拥有某一种质数因子不只1个
	// 那么认为这个数字无效，状态全是0，0b0000000000
	// 如果一个数字拥有任何一种质数因子都不超过1个
	// 那么认为这个数字有效，用位信息表示这个数字拥有质数因子的状态
	// 比如12，拥有2这种质数因子不只1个，所以无效，用0b0000000000表示
	// 比如14，拥有2这种质数因子不超过1个，拥有7这种质数因子不超过1个，有效
	// 从高位到低位依次表示：...13 11 7 5 3 2
	// 所以用0b0000001001表示14拥有质数因子的状态
	// 质数: 29 23 19 17 13 11 7 5 3 2
	// 位置: 9 8 7 6 5 4 3 2 1 0
	public static int[] own = { 0b0000000000, // 0
			0b0000000000, // 1
			0b0000000001, // 2
			0b0000000010, // 3
			0b0000000000, // 4
			0b0000000100, // 5
			0b0000000011, // 6
			0b0000001000, // 7
			0b0000000000, // 8
			0b0000000000, // 9
			0b0000000101, // 10
			0b0000010000, // 11
			0b0000000000, // 12
			0b0000100000, // 13
			0b0000001001, // 14
			0b0000000110, // 15
			0b0000000000, // 16
			0b0001000000, // 17
			0b0000000000, // 18
			0b0010000000, // 19
			0b0000000000, // 20
			0b0000001010, // 21
			0b0000010001, // 22
			0b0100000000, // 23
			0b0000000000, // 24
			0b0000000000, // 25
			0b0000100001, // 26
			0b0000000000, // 27
			0b0000000000, // 28
			0b1000000000, // 29
			0b0000000111 // 30
	};

	// 记忆化搜索
	public static int numberOfGoodSubsets1(int[] nums) {
		// 1 ~ 30
		int[] cnt = new int[MAXV + 1];
		for (int num : nums) {
			cnt[num]++;
		}
		int[][] dp = new int[MAXV + 1][LIMIT];
		for (int i = 0; i <= MAXV; i++) {
			Arrays.fill(dp[i], -1);
		}
		int ans = 0;
		for (int s = 1; s < LIMIT; s++) {
			ans = (ans + f1(MAXV, s, cnt, dp)) % MOD;
		}
		return ans;
	}

	// 1....i范围的数字，每种数字cnt[i]个
	// 最终相乘的结果一定要让质因子的状态为s，且每种质因子只能有1个
	// 请问子集的数量是多少
	// s每一位代表的质因子如下
	// 质数: 29 23 19 17 13 11 7 5 3 2
	// 位置: 9 8 7 6 5 4 3 2 1 0
	public static int f1(int i, int s, int[] cnt, int[][] dp) {
		if (dp[i][s] != -1) {
			return dp[i][s];
		}
		int ans = 0;
		if (i == 1) {
			if (s == 0) {
				ans = 1;
				for (int j = 0; j < cnt[1]; j++) {
					ans = (ans << 1) % MOD;
				}
			}
		} else {
			ans = f1(i - 1, s, cnt, dp);
			int cur = own[i];
			int times = cnt[i];
			if (cur != 0 && times != 0 && (s & cur) == cur) {
				// 能要i这个数字
				ans = (int) (((long) f1(i - 1, s ^ cur, cnt, dp) * times + ans) % MOD);
			}
		}
		dp[i][s] = ans;
		return ans;
	}

	// 空间压缩优化
	public static int[] cnt = new int[MAXV + 1];

	public static int[] dp = new int[LIMIT];

	public static int numberOfGoodSubsets2(int[] nums) {
		Arrays.fill(cnt, 0);
		Arrays.fill(dp, 0);
		for (int num : nums) {
			cnt[num]++;
		}
		dp[0] = 1;
		for (int i = 0; i < cnt[1]; i++) {
			dp[0] = (dp[0] << 1) % MOD;
		}
		for (int i = 2, cur, times; i <= MAXV; i++) {
			cur = own[i];
			times = cnt[i];
			if (cur != 0 && times != 0) {
				for (int status = LIMIT - 1; status >= 0; status--) {
					if ((status & cur) == cur) {
						dp[status] = (int) (((long) dp[status ^ cur] * times + dp[status]) % MOD);
					}
				}
			}
		}
		int ans = 0;
		for (int s = 1; s < LIMIT; s++) {
			ans = (ans + dp[s]) % MOD;
		}
		return ans;
	}

}
