package class085;

// 范围内的数字计数
// 给定两个正整数a和b，求在[a,b]范围上的所有整数中
// 某个数码d出现了多少次
// 测试链接 : https://leetcode.cn/problems/digit-count-in-range/
public class Code04_DigitCount1 {

	public static int digitsCount(int d, int a, int b) {
		return count(b, d) - count(a - 1, d);
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
