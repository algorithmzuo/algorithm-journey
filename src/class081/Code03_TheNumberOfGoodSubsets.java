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
	
	// 打个表
	// 如果一个数字拥有某种质数因子不只1个
	// 那么认为这个数字无效，状态全是0，0b0000000000
	// 如果一个数字拥有任何一种质数因子都不超过1个
	// 那么认为这个数字有效，用位信息表示这个数字拥有质数因子的状态
	// 比如12，拥有2这个质数因子不只1个，无效
	// 所以用0b0000000000表示12拥有质数因子的状态
	// 比如14，拥有一个2、一个7，有效
	// 位从低位到高位依次表示2、3、5、7...
	// 所以用0b0000001001表示14拥有质数因子的状态
	// 质: 29 23 19 17 13 11  7  5  3  2
	// 位:  9  8  7  6  5  4  3  2  1  0
	public static int[] data = {
			0b0000000000, // 0
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

	public static int[] cnt = new int[31];

	public static int[] dp = new int[1 << 10];

	public static int mod = 1000000007;

	public static int numberOfGoodSubsets(int[] nums) {
		Arrays.fill(cnt, 0);
		Arrays.fill(dp, 0);
		for (int num : nums) {
			cnt[num]++;
		}
		dp[0] = 1;
		for (int i = 0; i < cnt[1]; i++) {
			dp[0] = (dp[0] << 1) % mod;
		}
		int limit = (1 << 10);
		for (int i = 2, cur, times; i <= 30; i++) {
			cur = data[i];
			times = cnt[i];
			if (cur != 0 && times != 0) {
				for (int status = 1; status < limit; status++) {
					if ((status & cur) == cur) {
						dp[status] = (int) (((long) dp[status ^ cur] * times + dp[status]) % mod);
					}
				}
			}
		}
		int ans = 0;
		for (int s = 1; s < (1 << 10); s++) {
			ans = (ans + dp[s]) % mod;
		}
		return ans;
	}

}
