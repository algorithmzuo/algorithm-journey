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
		for (int right = 1, tmp = num, left, cur; tmp != 0; right *= 10, tmp /= 10) {
			left = tmp / 10;
			cur = tmp % 10;
			if (d == 0) {
				left--;
			}
			ans += left * right;
			if (cur > d) {
				ans += right;
			} else if (cur == d) {
				ans += num % right + 1;
			}
		}
		return ans;
	}

}
