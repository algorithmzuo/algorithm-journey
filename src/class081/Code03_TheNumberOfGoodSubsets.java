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

	// 2, 3, 5, 6, 7, 10, 11, 13, 14, 15, 17, 19, 21, 22, 23, 26, 29, 30
	public static int[] data = {
			0, // 0 00000000
			0, // 1 00000000
			1, // 2 00000001
			2, // 3 00000010
			0, // 4 00000000
			4, // 5 00000100
			3, // 6 00000011
			8, // 7 00001000
			0, // 8 00000000
			0, // 9 00000000
			5, // 10 00000101
			16, 0, 32, 9, 6, 0, 64, 0, 128, 0, 10, 17, 256, 0, 0, 33, 0, 0, 512, 7 };

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
