package class085;

// 数字1的个数
// 给定一个整数n
// 计算所有小于等于n的非负整数中数字1出现的个数
// 测试链接 : https://leetcode.cn/problems/number-of-digit-one/
public class Code04_DigitCount3 {

	public static int countDigitOne(int n) {
		return count(n, 1);
	}

	public static int count(int num, int d) {
		int ans = 0;
		for (int i = 1, tmp = num, high, cur; tmp != 0; i *= 10, tmp /= 10) {
			high = tmp / 10;
			if (d == 0) {
				if (high == 0) {
					break;
				}
				high--;
			}
			ans += high * i;
			cur = tmp % 10;
			if (cur > d) {
				ans += i;
			} else if (cur == d) {
				ans += num % i + 1;
			}
		}
		return ans;
	}

}
