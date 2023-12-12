package class085;

// 不含连续1的非负整数
// 给定一个正整数n，请你统计在[0, n]范围的非负整数中
// 有多少个整数的二进制表示中不存在连续的1
// 测试链接 : https://leetcode.cn/problems/non-negative-integers-without-consecutive-ones/
public class Code03_IntegersWithoutConsecutiveOnes {

	public static int findIntegers(int n) {
		int[] f = new int[31];
		f[0] = 1;
		f[1] = 2;
		for (int len = 2; len <= 30; len++) {
			f[len] = f[len - 1] + f[len - 2];
		}
		int ans = 0;
		int pre = 30;
		for (int cur = pre - 1, len = 30; cur >= 0; pre--, cur--, len--) {
			if ((n & (1 << cur)) != 0) {
				ans += f[len - 1];
				if ((n & (1 << pre)) != 0) {
					break;
				}
			}
		}
		if (pre == 0) {
			ans++;
		}
		return ans;
	}

}
