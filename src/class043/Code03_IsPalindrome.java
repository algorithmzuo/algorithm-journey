package class043;

// 超级回文数中的一个小函数，本身也是一道题 : 判断一个数字是不是回文数
// 测试链接 : https://leetcode.cn/problems/palindrome-number/
public class Code03_IsPalindrome {

	public static boolean isPalindrome(int num) {
		if (num < 0) {
			return false;
		}
		int offset = 1;
		// 注意这么写是为了防止溢出
		while (num / offset >= 10) {
			offset *= 10;
		}
		// 首尾判断
		while (num != 0) {
			if (num / offset != num % 10) {
				return false;
			}
			num = (num % offset) / 10;
			offset /= 100;
		}
		return true;
	}

}
