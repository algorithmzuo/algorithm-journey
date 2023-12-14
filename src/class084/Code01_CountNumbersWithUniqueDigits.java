package class084;

// 统计各位数字都不同的数字个数
// 给你一个整数n，代表十进制数字最多有n位
// 如果某个数字，每一位都不同，那么这个数字叫做有效数字
// 返回有效数字的个数，不统计负数范围
// 测试链接 : https://leetcode.cn/problems/count-numbers-with-unique-digits/
public class Code01_CountNumbersWithUniqueDigits {

	public static int countNumbersWithUniqueDigits(int n) {
		if (n == 0) {
			return 1;
		}
		int ans = 10;
		// 1 : 10
		// 2 : 9 * 9
		// 3 : 9 * 9 * 8
		// 4 : 9 * 9 * 8 * 7
		// ...都累加起来...
		for (int s = 9, i = 9, k = 2; k <= n; i--, k++) {
			s *= i;
			ans += s;
		}
		return ans;
	}

}
